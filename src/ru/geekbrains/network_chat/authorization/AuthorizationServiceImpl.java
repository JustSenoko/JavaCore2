package ru.geekbrains.network_chat.authorization;

import ru.geekbrains.network_chat.ChatUser;
import ru.geekbrains.network_chat.message.MessagePatterns;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationServiceImpl implements AuthorizationService {

    public Map<String, String> users = new HashMap<>();

    public AuthorizationServiceImpl() {
        users.put("ivan", "123");
        users.put("petr", "345");
        users.put("julia", "789");
    }

    @Override
    public boolean authUser(ChatUser user) {
        String pwd = users.get(user.getLogin());
        return pwd != null && pwd.equals(user.getPassword());
    }

    @Override
    public ChatUser checkAuthentication(String authMessage) throws AuthException {
        Map<String, String> auth = MessagePatterns.parseAuthMessage(authMessage);
        return new ChatUser(auth.get("login"), auth.get("password"), auth.get("name"));
    }


}
