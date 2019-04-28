package ru.geekbrains.network_chat.client.swing;

import ru.geekbrains.network_chat.client.Network;
import ru.geekbrains.network_chat.client.MessageReceiver;
import ru.geekbrains.network_chat.message.TextMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Set;

public class MainWindow extends JFrame implements MessageReceiver {

    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 7777;

    private final JList<TextMessage> msgList;
    private final DefaultListModel<TextMessage> msgListModel;
    private final JTextField messageField;
    //private final JTextField tfUserTo;
    //private final JComboBox<String> cbUsers;
    private final JList<String> userList;
    private final DefaultListModel<String> userListModel;

    private Network network;

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
        userList.setPreferredSize(new Dimension(100, 0));
        add(userList, BorderLayout.WEST);

        JScrollPane scrollUsers = new JScrollPane(userList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollUsers, BorderLayout.WEST);

        msgList = new JList<>();
        msgListModel = new DefaultListModel<>();
        msgList.setModel(msgListModel);

        TextMessageCellRenderer msgListCellRenderer = new TextMessageCellRenderer();
        msgList.setCellRenderer(msgListCellRenderer);

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

    }

    private void sendMessage() {
        String msg = messageField.getText();
        if (msg == null || msg.trim().isEmpty()) {
            return;
        }

//        String userTo = tfUserTo.getText();
        String userTo = userList.getSelectedValue();
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
    public void updateUserList(Set<String> userSet) {

        String login = network.getLogin();
        SwingUtilities.invokeLater(() -> {
            userListModel.removeAllElements();
            Iterator iterator = userSet.iterator();
            while (iterator.hasNext()) {
                String user = (String) iterator.next();
                if (user.equals(login)) {
                    continue;
                }
                userListModel.addElement(user);
            }
            updateUserListView();
        });
    }

    private void updateUserListView() {
        if (userListModel.size() == 1) {
            userList.setSelectedIndex(0);
        }
    }

    @Override
    public void userConnected(String login) {
        SwingUtilities.invokeLater(() -> {
            int ix = userListModel.indexOf(login);
            if (ix == -1) {
                userListModel.add(userListModel.size(), login);
            }
        });
    }

    @Override
    public void userDisconnected(String login) {
        SwingUtilities.invokeLater(() -> {
            int ix = userListModel.indexOf(login);
            if (ix >= 0) {
                userListModel.remove(ix);
            }
        });
    }
}
