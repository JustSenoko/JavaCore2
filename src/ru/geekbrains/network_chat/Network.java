package ru.geekbrains.network_chat;

import ru.geekbrains.network_chat.authorization.AuthException;
import ru.geekbrains.network_chat.message.MessagePatterns;
import ru.geekbrains.network_chat.message.MessageReciever;
import ru.geekbrains.network_chat.message.ParseMessageException;
import ru.geekbrains.network_chat.message.TextMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

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
                    TextMessage textMessage = MessagePatterns.parseSendMessage(text);
                    if (textMessage != null) {
                        messageReciever.submitMessage(textMessage);
                        continue;
                    }
                    if (MessagePatterns.isUserListPattern(text)) {
                        messageReciever.updateUserList(text);
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

        sendMessage(String.format(AUTH_SEND_PATTERN , login, password));
        String response = in.readUTF();
        if (response.equals(MessagePatterns.authResult(true))) {
            this.login = login;
            receiverThread.start();
        } else {
            throw new AuthException("Неверный логин/пароль");
        }
    }

    public void addUser(String login, String password, String name) throws IOException, AuthException {
        socket = new Socket(hostName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        sendMessage(String.format(REG_SEND_PATTERN, login, password, name));
        String response = in.readUTF();
        if (!response.equals(MessagePatterns.regResult(true))) {
            throw new AuthException("Пользователь с таким логином уже зарегистрирован");
        }
        response = in.readUTF();
        if (response.equals(MessagePatterns.authResult(true))) {
            this.login = login;
            receiverThread.start();
        } else {
            throw new AuthException("Неверный логин/пароль");
        }
    }

    public void sendTextMessage(TextMessage message) {
        sendMessage(String.format(MESSAGE_SEND_PATTERN, message.getUserTo(), login, message.getMessage()));
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
