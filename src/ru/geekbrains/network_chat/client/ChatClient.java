package ru.geekbrains.network_chat.client;

import ru.geekbrains.network_chat.client.swing.MainWindow;

import javax.swing.*;

public class ChatClient {

    private static MainWindow mainWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> mainWindow = new MainWindow());
    }
}
