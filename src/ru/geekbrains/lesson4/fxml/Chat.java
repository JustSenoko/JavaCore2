package ru.geekbrains.lesson4.fxml;

import java.util.LinkedList;

public class Chat {
    private String name;
    private LinkedList<ChatRecord> chat;

    Chat() {
        this.chat = new LinkedList<>();
        this.name = "Беседа с самим собой";
    }

    public String getName() {
        return name;
    }

    void addMessage(Participant participant, String message) {

        this.chat.add(new ChatRecord(participant, message));
    }

    String getRecordsAsString() {
        StringBuilder result = new StringBuilder();

        for (ChatRecord chatRecord : chat) {
            result.append(chatRecord.toString());
        }
        return result.toString();
    }
}
