package ru.geekbrains.lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private static final int SERVER_PORT = 7777;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                System.out.println("Сервер ожидает подключения...");
                try (Socket socket = serverSocket.accept()) {

                    System.out.println("Подключился " + socket.getInetAddress());

                    StreamsManager streamsManager = new StreamsManager(socket);
                    streamsManager.setInputOutput();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
