package ru.geekbrains.network_chat.message;

public class AuthorizationMessage {

    private String login;
    private String password;

    public AuthorizationMessage(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
