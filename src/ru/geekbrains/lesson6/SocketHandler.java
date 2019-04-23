package ru.geekbrains.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class SocketHandler {

    private final Socket socket;
    private Thread receiveThread;
    private Thread sendThread;

    SocketHandler(Socket socket) {
        this.socket = socket;
    }

    void start() {

        receiveThread = new Thread(() -> {
            try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
                while (true) {
                    String line = in.readUTF();
                    if (isEndMessage(line)) {
                        break;
                    }
                    System.out.printf("%nНовое сообщение > %s%n", line);
                    System.out.print("Введите сообщение > ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                do {
                    System.out.print("Введите сообщение > ");
                    String line = scanner.nextLine();
                    out.writeUTF(line);
                    if (isEndMessage(line)) {
                        break;
                    }
                } while (scanner.hasNextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        receiveThread.start();
        sendThread.start();
    }

    void join() {
        try {
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private boolean isEndMessage(String msg) {
        return msg.equalsIgnoreCase("/end");
    }
}