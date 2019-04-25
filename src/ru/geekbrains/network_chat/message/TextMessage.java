package ru.geekbrains.network_chat.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.geekbrains.network_chat.message.MessagePatterns.MESSAGE_PRINT_PATTERN;

public class TextMessage {
    private String userFrom;
    private String userTo;
    private String message;
    private LocalDateTime date;

    public TextMessage(String userTo, String userFrom, String message) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public String getUserFrom() {
        return userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public String getMessage() {
        return message;
    }

    public String getDateFormatted() {
        String formatPattern = "HH:mm";
        return date.format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public String toString() {
        return String.format(MESSAGE_PRINT_PATTERN, userFrom, message, getDateFormatted());
    }
}