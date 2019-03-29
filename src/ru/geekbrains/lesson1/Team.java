package ru.geekbrains.lesson1;

import ru.geekbrains.lesson1.participants.Participant;

/**
 * Класс - комманда участников соревнований
 */
public class Team {

    private String name;

    private Participant[] participants;

    // здесь используется конструктор с переменным числом параметров
    public Team(String name, Participant... participants) {
        this.name = name;
        // внутри метода переменное число параметров интерпретируется как массив
        this.participants = participants;
    }

    public Participant[] getParticipants() {
        return participants;
    }

    public void showResults() {
        int winsCount = 0;
        for (Participant participant : participants) {
            if (participant.isOnDistance()) {
                if (winsCount == 0) {
                    System.out.println("Все препятствия успешно прошли:");
                }
                winsCount++;
                participant.getInfo(false);
            }
        }
        if (winsCount == 0) {
            System.out.println("До финиша не дошел никто...");
        }
    }

    public void introduce() {
        System.out.println("Наша команда - " + this.name + ":");
        for (Participant participant : participants) {
            participant.getInfo(true);
        }
        System.out.println();
    }
}
