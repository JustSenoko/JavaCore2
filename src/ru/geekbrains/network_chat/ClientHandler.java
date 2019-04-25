package ru.geekbrains.network_chat;

import ru.geekbrains.network_chat.message.ExitMessageException;
import ru.geekbrains.network_chat.message.MessagePatterns;
import ru.geekbrains.network_chat.message.SendMessageException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import static ru.geekbrains.network_chat.message.MessagePatterns.MESSAGE_SEND_PATTERN;
import static ru.geekbrains.network_chat.message.MessagePatterns.UPDATE_USERS_PATTERN;

class ClientHandler {

    private final Socket socket;
    private final String user;
    private final ChatServer chatServer;
    private DataOutputStream out;
    private Thread receiveThread;

    ClientHandler(String user, Socket socket, ChatServer chatServer) throws IOException {

        this.user = user;
        this.socket = socket;
        this.chatServer = chatServer;

        start();
    }

    private void start() throws IOException {

        receiveThread = new Thread(() -> {
            try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
                while (true) {
                    String msg = in.readUTF();
                    if (isEndMessage(msg)) {
                        break;
                    }
                    try {
                        // если это сообщение от одного пользователя другому
                        Map<String, String> msgMap = MessagePatterns.parseSendMessage(msg);
                        String message = msgMap.get("message");
                        chatServer.sendMessage(msgMap.get("userTo"), msgMap.get("userFrom"), message);

                    } catch (SendMessageException e) {
                        // если это запрос на обновление списка пользователей
                        if (msg.equals(UPDATE_USERS_PATTERN)) {
                            chatServer.sendUpdUserListMessage();
                        } else {
                            try {
                                chatServer.userExit(MessagePatterns.parseExitMessage(msg));
                            } catch (ExitMessageException ex) {
                                System.out.printf("Incorrect message %s%n", msg);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        out = new DataOutputStream(socket.getOutputStream());
        receiveThread.start();
    }

    private boolean isEndMessage(String msg) {
        return msg.equalsIgnoreCase("/end");
    }

    void sendMessage(String userFrom, String msg) throws IOException {
        out.writeUTF(String.format(MESSAGE_SEND_PATTERN, user, userFrom, msg));
        System.out.printf(MESSAGE_SEND_PATTERN, user, userFrom, msg);
    }

    void sendMessage(String msg) throws IOException {
        out.writeUTF(msg);
        System.out.println(msg);
    }
}