package ru.geekbrains.network_chat.swing;

import ru.geekbrains.network_chat.Network;
import ru.geekbrains.network_chat.message.MessageReciever;
import ru.geekbrains.network_chat.message.TextMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static ru.geekbrains.network_chat.message.MessagePatterns.USER_EXIT_PATTERN;

public class MainWindow extends JFrame implements MessageReciever {

    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 7777;

    private final JList<TextMessage> msgList;
    private final DefaultListModel<TextMessage> msgListModel;
    private final TextMessageCellRenderer  msgListCellRenderer;
    private final JPanel sendMessagePanel;
    private final JTextField messageField;
    private final JScrollPane scroll;
    private final JButton sendButton;
    private final JTextField tfUserTo;
    private final JComboBox<String> cbUsers;

    private Network network;

    public MainWindow() {

        setTitle("Чат");
        setBounds(200,200, 500, 500);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                network.userExit();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

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

        cbUsers = new JComboBox<>(); //TODO пока не совсем правильно работает, доделать позже
//        cbUsers.setMinimumSize(new Dimension(30, 5));
//        sendMessagePanel.add(cbUsers, BorderLayout.WEST);

        tfUserTo = new JTextField(10);
        tfUserTo.setSize(15, 5);
        sendMessagePanel.add(tfUserTo, BorderLayout.WEST);


        messageField = new JTextField();
        messageField.addActionListener(e -> sendMessage());

        sendMessagePanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Отправить");
        sendButton.addActionListener(e -> sendMessage());
        sendMessagePanel.add(sendButton, BorderLayout.EAST);

        add(sendMessagePanel, BorderLayout.SOUTH);

        setVisible(true);

        this.network = new Network(SERVER_ADDR, SERVER_PORT, this);

        LoginDialog loginDialog = new LoginDialog(this, network);
        loginDialog.setVisible(true);

        if (!loginDialog.isConnected()) {
            System.exit(0);
        }
        this.network.updateUsersList();

        setTitle(String.format("Чат (%s)", network.getLogin()));

    }

    private void sendMessage() {
        String msg = messageField.getText();
        if (msg == null || msg.trim().isEmpty()) {
            return;
        }

        String userTo = tfUserTo.getText();
//        String userTo = cbUsers.getSelectedItem().toString();
        if (userTo == null || msg.trim().isEmpty()) {
            return;
        }

        TextMessage textMessage = new TextMessage(userTo, network.getLogin(), msg);
        msgListModel.add(msgListModel.size(), textMessage);
        messageField.setText("");
        network.sendTextMessage(textMessage);
    }

    @Override
    public void submitMessage(TextMessage message) {
        SwingUtilities.invokeLater(() -> {
            msgListModel.add(msgListModel.size(), message);
            msgList.ensureIndexIsVisible(msgListModel.size() - 1);
        });
    }

    @Override
    public void updateUserList(String message) {
        cbUsers.removeAllItems();
        String login = network.getLogin();
        message = message.replaceAll("\\[", "");
        message = message.replaceAll("]", "");
        String[] userList = message.split(", ");
        for (int i = 1; i < userList.length; i++) {
            if (userList[i].equals(login)) {
                continue;
            }
            cbUsers.addItem(userList[i]);
        }


    }
}
