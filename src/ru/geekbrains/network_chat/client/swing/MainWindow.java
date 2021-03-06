package ru.geekbrains.network_chat.client.swing;

import ru.geekbrains.network_chat.client.Network;
import ru.geekbrains.network_chat.client.MessageReceiver;
import ru.geekbrains.network_chat.client.UnreadMessage;
import ru.geekbrains.network_chat.message.TextMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainWindow extends JFrame implements MessageReceiver {

    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 7777;

    private final JList<TextMessage> msgList;
    private Map<String, DefaultListModel<TextMessage>> userMsgModelMap = new HashMap<>();
    private Map<String, UnreadMessage> userUnreadMsgMap = new HashMap<>();

    private final JTextField messageField;
    private final JList<UnreadMessage> userList;
    private final DefaultListModel<UnreadMessage> userListModel;

    private Network network;
    private String userTo = "";

    public MainWindow() {

        setTitle("Чат");
        setBounds(200,200, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (network != null) {
                    network.close();
                }
                super.windowClosing(e);
            }
        });

        setLayout(new BorderLayout());

        userList = new JList<>();
        userListModel = new DefaultListModel<>();
        userList.setModel(userListModel);
        userList.setPreferredSize(new Dimension(50, 0));
        userList.setMaximumSize(new Dimension(50, 0));
        UserListCellRenderer userListCellRenderer = new UserListCellRenderer();
        userList.setCellRenderer(userListCellRenderer);
        userList.addListSelectionListener(e -> setUserTo());

        JScrollPane scrollUsers = new JScrollPane(userList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollUsers, BorderLayout.WEST);

        msgList = new JList<>();

        JScrollPane scrollMessages = new JScrollPane(msgList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollMessages, BorderLayout.CENTER);

        JPanel sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        messageField.addActionListener(e -> sendMessage());
        sendMessagePanel.add(messageField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Отправить");
        sendButton.isDefaultButton();
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
        setTitle(String.format("Чат (%s)", network.getLogin()));
        TextMessageCellRenderer msgListCellRenderer = new TextMessageCellRenderer(network.getLogin());
        msgList.setCellRenderer(msgListCellRenderer);
    }

    private void setUserTo() {

        if (userList.getSelectedValue() == null) {
            return;
        }

        userTo = userList.getSelectedValue().getLogin();
        if (userTo == null && userListModel.size() > 0) {
            userTo = userListModel.elementAt(0).getLogin();
        }
        if (userTo != null) {
            if (userMsgModelMap.containsKey(userTo)) {
                msgList.setModel(userMsgModelMap.get(userTo));
            }
            if (userUnreadMsgMap.containsKey(userTo)) {
                userUnreadMsgMap.get(userTo).resetUnreadCount();
            }
        }
    }

    private void sendMessage() {
        String msg = messageField.getText();
        if (msg == null || msg.trim().isEmpty() || userTo.isEmpty()) {
            return;
        }

        TextMessage textMessage = new TextMessage(userTo, network.getLogin(), msg);
        DefaultListModel<TextMessage> msgListModel = userMsgModelMap.get(userTo);
        msgListModel.add(msgListModel.size(), textMessage);
        messageField.setText("");
        network.sendTextMessage(textMessage);
    }

    @Override
    public void submitMessage(TextMessage message) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<TextMessage> msgListModel = userMsgModelMap.get(message.getUserFrom());
            msgListModel.add(msgListModel.size(), message);
            if (userTo.equals(message.getUserFrom())) {
                msgList.ensureIndexIsVisible(msgListModel.size() - 1);
            } else {
                userUnreadMsgMap.get(message.getUserFrom()).incUnreadCount();
            }
        });
    }

    @Override
    public void updateUserList(Set<String> userSet) {

        String login = network.getLogin();
        SwingUtilities.invokeLater(() -> {
            userListModel.removeAllElements();
            for (String userLogin : userSet) {
                if (userLogin.equals(login)) {
                    continue;
                }
                addUser(userLogin);
            }
            updateUserListView();
        });
    }

    private void addUser(String login) {
        UnreadMessage newUser = new UnreadMessage(login);
        userListModel.addElement(newUser);
        userMsgModelMap.putIfAbsent(login, new DefaultListModel<>());
        userUnreadMsgMap.putIfAbsent(login, newUser);
    }

    private void updateUserListView() {
        if (userListModel.size() == 1
                || (userListModel.size() > 1 && userList.getSelectedValue() == null)) {
            userList.setSelectedIndex(0);
        }
        setUserTo();
    }

    @Override
    public void userConnected(String login) {
        SwingUtilities.invokeLater(() -> {
            int ix = userListModel.indexOf(userUnreadMsgMap.get(login));
            if (ix == -1) {
                addUser(login);
            }
            updateUserListView();
        });
    }

    @Override
    public void userDisconnected(String login) {
        SwingUtilities.invokeLater(() -> {
            int ix = userListModel.indexOf(userUnreadMsgMap.get(login));
            if (ix >= 0) {
                userListModel.remove(ix);
                //из модели ссобщений не удаляем - если он переподключится, сохранится история
            }
            updateUserListView();
        });
    }


}
