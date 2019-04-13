package ru.geekbrains.lesson4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatRecord {
    private ChatUser chatUser;
    private String message;
    private LocalDateTime date;

    public ChatRecord(ChatUser chatUser, String message) {
        this.chatUser = chatUser;
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public ChatUser getUser() {
        return chatUser;
    }

    public String getMessage() {
        return message;
    }

    public String getDateFormatted() {
        String formatPattern = "HH:mm";
        return date.format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public String toString() {
        return String.format("%s: %s    %s%n", chatUser.getName(), message, getDateFormatted());
    }
}
