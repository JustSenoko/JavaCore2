package ru.geekbrains.lesson6;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 7777;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDR, SERVER_PORT)) {

            System.out.printf("Подключились к %s%n", SERVER_ADDR);

            SocketHandler socketHandler = new SocketHandler(socket);
            socketHandler.start();
            socketHandler.join();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
