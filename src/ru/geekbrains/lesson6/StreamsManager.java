package ru.geekbrains.lesson6;

import java.net.Socket;

class StreamsManager {

    private Socket socket;

    StreamsManager(Socket socket) {
        this.socket = socket;
    }

    void setInputOutput() throws InterruptedException {

        Thread input = new Thread(new ChatInputStream(socket));
        input.start();

        Thread output = new Thread(new ChatOutputStream(socket));
        output.start();

        input.join();

    }
}