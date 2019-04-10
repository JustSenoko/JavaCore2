package ru.geekbrains.lesson4.fxml;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.geekbrains.lesson4.Chat;
import ru.geekbrains.lesson4.Participant;

public class ChatController {
    public TextArea txtChatArea;
    public TextField txtMessage;
    public Label lblChatName;
    public Button btnSend;

    private Chat chat;
    private Participant participant;

    void setChat(Chat chat) {

        this.chat = chat;
        lblChatName.setText(chat.getName());
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void sendMessage(ActionEvent actionEvent) {

        sendMessageToChat();
    }

    private void sendMessageToChat() {
        String msg = txtMessage.getText();
        if (msg.equals("")) {
            return;
        }
        chat.addMessage(this.participant, msg);
        txtChatArea.setText(chat.getRecordsAsString());
        txtMessage.setText("");
    }

}
