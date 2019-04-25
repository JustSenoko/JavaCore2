package ru.geekbrains.network_chat.authorization;

import ru.geekbrains.network_chat.ChatUser;

public interface AuthorizationService {

    boolean authUser(ChatUser user);

    ChatUser checkAuthentication(String authMessage) throws AuthException;
}
