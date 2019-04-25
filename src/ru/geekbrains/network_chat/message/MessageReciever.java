package ru.geekbrains.network_chat.message;

public interface MessageReciever {

    void submitMessage(TextMessage message);

    void updateUserList(String message);
}
