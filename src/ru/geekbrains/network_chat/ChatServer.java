package ru.geekbrains.network_chat;

import ru.geekbrains.network_chat.authorization.AuthException;
import ru.geekbrains.network_chat.authorization.AuthorizationService;
import ru.geekbrains.network_chat.authorization.AuthorizationServiceImpl;
import ru.geekbrains.network_chat.message.MessagePatterns;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static ru.geekbrains.network_chat.message.MessagePatterns.USER_LIST_PATTERN;

public class ChatServer {

    private static final int SERVER_PORT = 7777;
    private AuthorizationService authService = new AuthorizationServiceImpl();
    private Map<String, ClientHandler> clientHandlerMap = new HashMap<>();


    public static void main(String[] args) {

        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("New client connected!");

                ChatUser user = null;
                try {
                    String authMessage = inp.readUTF();
                    user = authService.checkAuthentication(authMessage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (AuthException ex) {
                    out.writeUTF(MessagePatterns.authResult(false));
                    out.flush();
                    socket.close();
                }
                //TODO обработать данные регистрации нового пользователя
                if (user != null && authService.authUser(user)) {
                    System.out.printf("User %s authorized successful!%n", user.getLogin());
                    clientHandlerMap.put(user.getLogin(), new ClientHandler(user.getLogin(), socket, this));
                    out.writeUTF(MessagePatterns.authResult(true));
                    out.flush();
                } else {
                    if (user != null) {
                        System.out.printf("Wrong authorization for user %s%n", user.getLogin());
                    }
                    out.writeUTF(MessagePatterns.authResult(false));
                    out.flush();
                    socket.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void sendMessage(String userTo, String userFrom, String msg) {

        if (!userIsOnline(userTo)) {
            msg = String.format("Не удалось отправить сообщение. Пользователь %s не в сети.", userTo);
            userTo = userFrom;
        }
        if (userTo == null || userTo.trim().isEmpty()) {
            return;
        }

        ClientHandler userToClientHandler = clientHandlerMap.get(userTo);
        try {
            userToClientHandler.sendMessage(userFrom, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean userIsOnline(String login) {
        return clientHandlerMap.containsKey(login);
    }

    void sendUpdUserListMessage() {
        Set<String> users = clientHandlerMap.keySet();
        String userList = String.format(USER_LIST_PATTERN, users.toString());

        for (String login : users) {
            ClientHandler userToClientHandler = clientHandlerMap.get(login);
            try {
                userToClientHandler.sendMessage(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void userExit(String login) {
        clientHandlerMap.remove(login);
        sendUpdUserListMessage();
    }

}
