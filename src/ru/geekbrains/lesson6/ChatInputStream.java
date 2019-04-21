package ru.geekbrains.lesson6;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatInputStream implements Runnable{

    private Socket socket;

    ChatInputStream(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            while (true) {
                try {
                    String line = in.readUTF();
                    if (line.equals("/end")) {
                        break;
                    }
                    System.out.println("Новое сообщение > " + line);
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
