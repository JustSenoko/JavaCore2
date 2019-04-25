package ru.geekbrains.network_chat;

import ru.geekbrains.network_chat.swing.MainWindow;

import javax.swing.*;

public class ChatClient {

    private static MainWindow mainWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> mainWindow = new MainWindow());
    }
}
