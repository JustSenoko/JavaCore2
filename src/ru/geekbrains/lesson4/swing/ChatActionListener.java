package ru.geekbrains.lesson4.swing;

import ru.geekbrains.lesson4.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatActionListener implements ActionListener {
    private JTextField msgField;
    private JTextArea chatArea;
    private Chat chat;
    private Participant participant;

    ChatActionListener(JTextField msgField, JTextArea chatArea, Chat chat, Participant participant) {
        this.msgField = msgField;
        this.chatArea = chatArea;
        this.chat = chat;
        this.participant = participant;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = msgField.getText();
        if (msg.equals("")) {
            return;
        }
        chat.addMessage(this.participant, msg);
        chatArea.setText(chat.getRecordsAsString());
        msgField.setText("");
    }
}
