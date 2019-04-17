package ru.geekbrains.lesson6;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputStream implements Runnable{

    private Socket socket;

    InputStream(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            while (true) {
                try {
                    System.out.println("Новое сообщение > " + in.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
