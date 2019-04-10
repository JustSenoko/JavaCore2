package ru.geekbrains.lesson4;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatRecord {
    private Participant participant;
    private String message;
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    ChatRecord(Participant participant, String message) {
        this.participant = participant;
        this.message = message;
        this.date = new Date();
    }

    public String toString() {
        return String.format("%s: %s    %s%n", participant.getName(), message, dateFormat.format(date));
    }
}
