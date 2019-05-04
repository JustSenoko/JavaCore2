package ru.geekbrains.network_chat.client;

import ru.geekbrains.network_chat.message.TextMessage;

import java.util.Set;

public interface MessageReceiver {

    void submitMessage(TextMessage message);

    void updateUserList(Set<String> userList);

    void userConnected(String login);

    void userDisconnected(String login);
}
