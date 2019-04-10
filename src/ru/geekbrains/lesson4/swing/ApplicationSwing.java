package ru.geekbrains.lesson4.swing;

import ru.geekbrains.lesson4.Chat;
import ru.geekbrains.lesson4.Participant;

import javax.swing.*;

public class ApplicationSwing {

    public static void main(String[] args) {
        Chat chat = new Chat();
        Participant participant = new Participant("я");

        SwingUtilities.invokeLater(() -> new MainWindow(chat, participant));
    }
}
