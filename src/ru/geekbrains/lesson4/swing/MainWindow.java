package ru.geekbrains.lesson4.swing;

import ru.geekbrains.lesson4.Chat;
import ru.geekbrains.lesson4.Participant;

import javax.swing.*;
import java.awt.*;

class MainWindow extends JFrame {

    MainWindow(Chat chat, Participant participant) {
        setTitle("Чат");
        setBounds(200,200, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JTextArea messagesArea = new JTextArea();
        messagesArea.setLineWrap(true);
        messagesArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(messagesArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);



        JPanel sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new BorderLayout());

        JTextField messageField = new JTextField();

        ChatActionListener chatActionListener = new ChatActionListener(messageField, messagesArea, chat, participant);
        messageField.addActionListener(chatActionListener);
        sendMessagePanel.add(messageField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(chatActionListener);
        sendMessagePanel.add(sendButton, BorderLayout.EAST);

        add(sendMessagePanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
