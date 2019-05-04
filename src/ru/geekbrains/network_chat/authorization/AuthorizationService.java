package ru.geekbrains.network_chat.authorization;

public interface AuthorizationService {

    boolean authUser(ChatUser user);

    ChatUser checkAuthorization(String authMessage);

    ChatUser checkRegistration(String regMessage);

    boolean addUser(ChatUser user);
}
