package ru.geekbrains.lesson4.swing;

import ru.geekbrains.lesson4.ChatRecord;

import javax.swing.*;
import java.awt.*;

class TextMessageCellRenderer extends JPanel implements ListCellRenderer<ChatRecord> {

    private final JLabel msgUser;
    private final JTextArea msgText;
    private final JLabel msgData;

    TextMessageCellRenderer() {

        setLayout(new BorderLayout());
        setEnabled(false);

        msgUser = new JLabel();
        Font f = msgUser.getFont();
        msgUser.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        add(msgUser, BorderLayout.WEST);

        msgData = new JLabel();

        add(msgData, BorderLayout.EAST);

        msgText = new JTextArea();
        msgText.setLineWrap(true);
        msgText.setWrapStyleWord(true);

        add(msgText, BorderLayout.SOUTH);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ChatRecord> list,
                                                  ChatRecord chatRecord, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setBackground(list.getBackground());
        msgData.setText(chatRecord.getDateFormatted());
        msgUser.setOpaque(true);
        msgUser.setText(chatRecord.getUser().getName());
        msgText.setText(chatRecord.getMessage());
        return this;
    }
}
