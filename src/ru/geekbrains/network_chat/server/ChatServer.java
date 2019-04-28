package ru.geekbrains.network_chat.server;

import ru.geekbrains.network_chat.authorization.ChatUser;
import ru.geekbrains.network_chat.authorization.AuthorizationService;
import ru.geekbrains.network_chat.authorization.AuthorizationServiceImpl;
import ru.geekbrains.network_chat.message.MessagePatterns;
import ru.geekbrains.network_chat.message.TextMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static ru.geekbrains.network_chat.message.MessagePatterns.USERS_PATTERN;

public class ChatServer {

    private static final int SERVER_PORT = 7777;
    private AuthorizationService authService = new AuthorizationServiceImpl();
    private Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

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
                ChatUser user;
                try {
                    String inpMessage = inp.readUTF();
                    user = authService.checkAuthorization(inpMessage);
                    if (user == null && registerNewUser(inpMessage)) {
                        out.writeUTF(MessagePatterns.regResult(true));
                        out.flush();
                        socket.close();
                        continue;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    continue;
                }

                if (user == null) {
                    out.writeUTF(MessagePatterns.authResult(false));
                    out.flush();
                    socket.close();
                    continue;
                }

                if (authService.authUser(user)) {
                    System.out.printf("User %s authorized successful!%n", user.getLogin());
                    out.writeUTF(MessagePatterns.authResult(true));
                    out.flush();
                    subscribe(user.getLogin(), socket);
                } else {
                    System.out.printf("Wrong authorization for user %s%n", user.getLogin());
                    out.writeUTF(MessagePatterns.authResult(false));
                    out.flush();
                    socket.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean registerNewUser(String regMessage) {
        ChatUser user = authService.checkRegistration(regMessage);
        if (user != null) {
            return authService.addUser(user);
        }
        return false;
    }

    private void sendMessageFromUserToUser(String userTo, String userFrom, String msg) {

        if (!userIsOnline(userTo)) {
            msg = String.format("Не удалось отправить сообщение. Пользователь %s не в сети.%n", userTo);
            userTo = userFrom;
        }
        if (userTo == null || userTo.trim().isEmpty()) {
            return;
        }
        String msgText = String.format(MessagePatterns.MESSAGE_SEND_PATTERN, userTo, userFrom, msg);
        sendMessage(userTo, msgText);
    }

    private void sendMessage(String userTo, String msg) {
        ClientHandler userToClientHandler = clientHandlerMap.get(userTo);
        try {
            userToClientHandler.sendMessage(msg);
//            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean userIsOnline(String login) {
        return clientHandlerMap.containsKey(login);
    }

    private void sendUserListMessage(String login) {
        Set<String> users = clientHandlerMap.keySet();
        String userList = String.format(USERS_PATTERN, users.toString());

        sendMessage(login, userList);
    }

    void sendTextMessage(TextMessage textMessage) {
        sendMessageFromUserToUser(textMessage.getUserTo(), textMessage.getUserFrom(), textMessage.getMessage());
    }

    private void subscribe(String login, Socket socket) throws IOException {
        // TODO Проверить, подключен ли уже пользователь. Если да, то отправить клиенту ошибку
        if (userIsOnline(login)) {
            String msg = String.format("User %s is already connected", login);
            sendMessage(login, msg);
            return;
        }
        clientHandlerMap.put(login, new ClientHandler(login, socket, this));
        String msg = String.format(MessagePatterns.CONNECTED_SEND, login);
        sendToAllUsersExceptLogin(login, msg);

        sendUserListMessage(login);
    }

    void unsubscribe(String login) {
        clientHandlerMap.remove(login);
        // TODO Отправить всем подключенным пользователям сообщение, что данный пользователь отключился
        String msg = String.format(MessagePatterns.DISCONNECTED_SEND, login);
        sendToAllUsersExceptLogin(login, msg);
    }

    private void sendToAllUsersExceptLogin(String login, String msg) {
        Set<String> users = clientHandlerMap.keySet();
        for (String userLogin : users) {
            if (userLogin.equals(login)) {
                continue;
            }
            sendMessage(userLogin, msg);
        }
    }
}
