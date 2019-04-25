package ru.geekbrains.network_chat;

import ru.geekbrains.network_chat.authorization.AuthException;
import ru.geekbrains.network_chat.message.MessagePatterns;
import ru.geekbrains.network_chat.message.MessageReciever;
import ru.geekbrains.network_chat.message.SendMessageException;
import ru.geekbrains.network_chat.message.TextMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

import static ru.geekbrains.network_chat.message.MessagePatterns.*;

public class Network {

    private Socket socket;
    private DataInputStream in;
    public DataOutputStream out;

    private String hostName;
    private int port;
    private MessageReciever messageReciever;

    private String login;

    private Thread receiverThread;

    public Network(String hostName, int port, MessageReciever messageReciever) {
        this.hostName = hostName;
        this.port = port;
        this.messageReciever = messageReciever;

        this.receiverThread = new Thread(() -> {
            while (true) {
                try {
                    String text = in.readUTF();
                    try {
                        Map<String, String> msgMap = MessagePatterns.parseSendMessage(text);
                        TextMessage textMessage = new TextMessage(login, msgMap.get("userFrom"), msgMap.get("message"));
                        messageReciever.submitMessage(textMessage);
                    } catch (SendMessageException ex) {
                        if  (MessagePatterns.isUserListPattern(text)) {
                            messageReciever.updateUserList(text);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    if (socket.isClosed()) {
                        break;
                    }
                }
            }
        });
    }

    public void authorize(String login, String password) throws IOException, AuthException {
        socket = new Socket(hostName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        sendMessage(String.format(AUTH_PATTERN, login, password));
        String response = in.readUTF();
        if (response.equals("/auth successful")) {
            this.login = login;
            receiverThread.start();
        } else {
            throw new AuthException();
        }
    }

    public void addUser(String name, String login, String password) throws IOException, AuthException {
        socket = new Socket(hostName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        sendMessage(String.format(REG_PATTERN, name, login, password));
        String response = in.readUTF();
        if (!response.equals("/reg successful")) {
            throw new AuthException();
        }
    }

    public void sendTextMessage(TextMessage message) {
        sendMessage(String.format(MESSAGE_SEND_PATTERN, message.getUserTo(), message.getUserFrom(), message.getMessage()));
    }

    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    public void updateUsersList() {
        sendMessage(UPDATE_USERS_PATTERN);
    }

    public void userExit() {
        sendMessage(String.format(USER_EXIT_PATTERN, login));
    }

}
