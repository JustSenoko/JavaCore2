package ru.geekbrains.lesson4;

import java.util.LinkedList;

public class Chat {
    private String name;
    private LinkedList<ChatRecord> chat;

    public Chat() {
        this.chat = new LinkedList<>();
        this.name = "Беседа с самим собой";
    }

    public String getName() {
        return name;
    }

    public void addMessage(Participant participant, String message) {

        this.chat.add(new ChatRecord(participant, message));
    }

    public String getRecordsAsString() {
        StringBuilder result = new StringBuilder();

        for (ChatRecord chatRecord : chat) {
            result.append(chatRecord.toString());
        }
        return result.toString();
    }
}
