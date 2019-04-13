package ru.geekbrains.lesson4.swing;

import ru.geekbrains.lesson4.ChatRecord;
import ru.geekbrains.lesson4.ChatUser;

import javax.swing.*;
import java.awt.*;

class MainWindow extends JFrame {

    private final ChatUser chatUser;
    private final JList<ChatRecord> msgList;
    private final DefaultListModel<ChatRecord> msgListModel;
    private final TextMessageCellRenderer  msgListCellRenderer;
    private final JPanel sendMessagePanel;
    private final JTextField messageField;
    private final JScrollPane scroll;
    private final JButton sendButton;

    MainWindow(ChatUser chatUser) {

        this.chatUser = chatUser;

        setTitle("Чат");
        setBounds(200,200, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        msgList = new JList<>();

        msgListModel = new DefaultListModel<>();
        msgList.setModel(msgListModel);

        msgListCellRenderer = new TextMessageCellRenderer();
        msgList.setCellRenderer(msgListCellRenderer);

        scroll = new JScrollPane(msgList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);


        sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        messageField.addActionListener(e -> sendMessage());

        sendMessagePanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Отправить");
        sendButton.addActionListener(e -> sendMessage());
        sendMessagePanel.add(sendButton, BorderLayout.EAST);

        add(sendMessagePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendMessage() {
        String msg = messageField.getText();
        if (msg == null || msg.trim().isEmpty()) {
            return;
        }
        msgListModel.add(msgListModel.size(), new ChatRecord(chatUser, msg));
        messageField.setText("");
    }
}
