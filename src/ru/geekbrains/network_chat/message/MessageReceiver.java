package ru.geekbrains.network_chat.message;

public interface MessageReceiver {

    void submitMessage(TextMessage message);

    void updateUserList(String message);
}
