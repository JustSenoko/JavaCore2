package ru.geekbrains.network_chat;

import ru.geekbrains.network_chat.message.MessagePatterns;
import ru.geekbrains.network_chat.message.TextMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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

                    // если это сообщение от одного пользователя другому
                    TextMessage textMessage = MessagePatterns.parseSendMessage(msg);
                    if (textMessage != null) {
                        chatServer.sendTextMessage(textMessage);
                        continue;
                    }

                    // если это запрос на обновление списка пользователей
                    if (msg.equals(UPDATE_USERS_PATTERN)) {
                        chatServer.sendUpdUserListMessage();
                        continue;
                    }

                    String disconnect = MessagePatterns.parseDisconnectMessage(msg);
                    if (disconnect != null) {
                        chatServer.userExit(MessagePatterns.parseDisconnectMessage(msg));
                        break;
                    }

                    System.out.printf(MessagePatterns.USER_EXIT_PATTERN, msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out = new DataOutputStream(socket.getOutputStream());
        receiveThread.start();
    }

    void sendMessage(String msg) throws IOException {
        out.writeUTF(msg);
        System.out.println(msg);
    }
}