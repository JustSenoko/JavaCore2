package ru.geekbrains.lesson4.swing;

import ru.geekbrains.lesson4.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatActionListener implements ActionListener {
    private JTextField txtMessage;
    private JTextArea txtChatArea;
    private Chat chat;
    private Participant participant;

    ChatActionListener(JTextField txtMessage, JTextArea txtChatArea, Chat chat, Participant participant) {
        this.txtMessage = txtMessage;
        this.txtChatArea = txtChatArea;
        this.chat = chat;
        this.participant = participant;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = txtMessage.getText();
        if (msg.equals("")) {
            return;
        }
        chat.addMessage(this.participant, msg);
        txtChatArea.setText(chat.getRecordsAsString());
        txtMessage.setText("");
    }
}
