package ru.geekbrains.lesson4.fxml;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.geekbrains.lesson4.Chat;
import ru.geekbrains.lesson4.ChatUser;

public class ChatController {
    public TextArea txtChatArea;
    public TextField txtMessage;
    public Label lblChatName;
    public Button btnSend;

    private Chat chat;
    private ChatUser chatUser;

    void setChat(Chat chat) {

        this.chat = chat;
        lblChatName.setText(chat.getName());
    }

    public void setChatUser(ChatUser chatUser) {
        this.chatUser = chatUser;
    }

    public void sendMessage(ActionEvent actionEvent) {

        sendMessageToChat();
    }

    private void sendMessageToChat() {
        String msg = txtMessage.getText();
        if (msg.equals("")) {
            return;
        }
        chat.addMessage(this.chatUser, msg);
        txtChatArea.setText(chat.getRecordsAsString());
        txtMessage.setText("");
    }

}
