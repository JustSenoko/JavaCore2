package ru.geekbrains.network_chat.message;

import org.junit.Test;
import ru.geekbrains.network_chat.authorization.ChatUser;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static ru.geekbrains.network_chat.message.MessagePatterns.*;

public class TestParser {

    @Test
    public void parseAuthMessageTest() {
        ChatUser chatUser = parseAuthMessage("/auth sveta 555");
        assertNotNull(chatUser);
        assertEquals("sveta", chatUser.getLogin());
        assertEquals("555", chatUser.getPassword());
    }

    @Test
    public void parseRegMessageTest() {
        ChatUser chatUser = parseRegMessage("/reg sveta 555 Svetlana");
        assertNotNull(chatUser);
        assertEquals("sveta", chatUser.getLogin());
        assertEquals("555", chatUser.getPassword());
        assertEquals("Svetlana", chatUser.getName());
    }

    @Test
    public void parseConnectMessageTest() {
        String login = parseConnectMessage("/connected petr");
        assertEquals("petr", login);
    }

    @Test
    public void parseDisconnectMessageTest() {
        String login = parseDisconnectMessage("/disconnected petr");
        assertEquals("petr", login);
    }

    @Test
    public void parseSendMessageTest() {
        TextMessage textMessage = parseSendMessage("/w ivan petr Hi! Nice to see you!");
        assertNotNull(textMessage);
        assertEquals("ivan", textMessage.getUserTo());
        assertEquals("petr", textMessage.getUserFrom());
        assertEquals("Hi! Nice to see you!", textMessage.getMessage());
    }

    @Test
    public void parseUserListMessageTest() {
        Set<String> userTest = parseUserListMessage("/users [ivan, petr, olga]");

        Set<String> userSet = new HashSet<>();
        userSet.add("ivan");
        userSet.add("petr");
        userSet.add("olga");

        assertEquals(userSet, userTest);
    }
}