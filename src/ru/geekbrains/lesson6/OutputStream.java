package ru.geekbrains.lesson6;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class OutputStream {

    static void enable(Socket socket) {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                out.writeUTF(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
