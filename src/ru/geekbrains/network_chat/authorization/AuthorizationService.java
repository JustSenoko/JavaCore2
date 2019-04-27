package ru.geekbrains.network_chat.authorization;

import ru.geekbrains.lesson4.Chat;
import ru.geekbrains.network_chat.ChatUser;
import ru.geekbrains.network_chat.message.AuthorizationMessage;

public interface AuthorizationService {

    boolean authUser(ChatUser user);

    ChatUser checkAuthorization(String authMessage);

    ChatUser checkRegistration(String regMessage);

    boolean addUser(ChatUser user);
}
