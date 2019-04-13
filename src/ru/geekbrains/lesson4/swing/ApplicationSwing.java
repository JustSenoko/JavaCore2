package ru.geekbrains.lesson4.swing;

import ru.geekbrains.lesson4.ChatUser;

import javax.swing.*;

public class ApplicationSwing {

    public static void main(String[] args) {
        ChatUser chatUser = new ChatUser("вы");

        SwingUtilities.invokeLater(() -> new MainWindow(chatUser));
    }
}
