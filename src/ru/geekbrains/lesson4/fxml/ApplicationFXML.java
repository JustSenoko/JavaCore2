package ru.geekbrains.lesson4.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.geekbrains.lesson4.*;

public class ApplicationFXML extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();

        ChatController chatController = loader.getController();
        chatController.setChat(new Chat());
        chatController.setParticipant(new Participant("я"));

        primaryStage.setTitle("Чат");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
