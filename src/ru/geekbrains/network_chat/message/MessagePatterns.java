package ru.geekbrains.network_chat.message;

import ru.geekbrains.network_chat.authorization.AuthException;

import java.util.HashMap;
import java.util.Map;

public final class MessagePatterns {
    public static final String AUTH_PATTERN = "/auth %s %s"; // /auth login password
    public static final String AUTH_RESULT_PATTERN = "/auth %s"; // /auth login successful
    public static final String REG_PATTERN = "/reg %s %s %s"; // /reg name login password
    public static final String MESSAGE_SEND_PATTERN = "/w %s %s %s"; // /w loginTo loginFrom message
    public static final String MESSAGE_PRINT_PATTERN = "%s %s     %s"; // login message
    public static final String UPDATE_USERS_PATTERN = "/updusr";
    public static final String USER_LIST_PATTERN = "/users %s"; // login
    public static final String USER_EXIT_PATTERN = "/exit %s"; // login

    public static Map<String, String> parseAuthMessage(String msg) throws AuthException {
        Map<String, String> result = new HashMap<>();
        String[] parts = msg.split(" ");
        String commandName = AUTH_PATTERN.split(" ")[0];

        if (parts.length != 3 || !parts[0].equals(commandName)) {
            System.out.printf("Incorrect authorization message %s%n", msg);
            throw new AuthException();
        }
        result.put("login", parts[1]);
        result.put("password", parts[2]);

        return result;
    }

    public static Map<String, String> parseSendMessage(String msg) throws SendMessageException {
        Map<String, String> result = new HashMap<>();
        String[] parts = msg.split(" ");
        String commandName = MESSAGE_SEND_PATTERN.split(" ")[0];

        if (parts.length < 4 || !parts[0].equals(commandName)) {
            System.out.printf("Incorrect message %s%n", msg);
            throw new SendMessageException();
        }
        result.put("userTo", parts[1]);
        result.put("userFrom", parts[2]);
        int length = commandName.length() + parts[1].length() + parts[2].length() + 3;
        result.put("message", msg.substring(length - 1));

        return result;
    }

    public static String parseExitMessage(String msg) throws ExitMessageException {

        String[] parts = msg.split(" ");
        String commandName = USER_EXIT_PATTERN.split(" ")[0];

        if (parts.length < 2 || !parts[0].equals(commandName)) {
            throw new ExitMessageException();
        }
        return parts[1];
    }

    public static String authResult(boolean result) {
        String res = (result ? "successful" : "fails");
        return String.format(AUTH_RESULT_PATTERN, res);
    }

    public static boolean isUserListPattern(String msg) {
        String commandName = USER_LIST_PATTERN.split(" ")[0];
        String[] parts = msg.split(" ");
        return parts[0].equals(commandName);
    }
}
