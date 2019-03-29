package ru.geekbrains.lesson1.course;

import ru.geekbrains.lesson1.Team;
import ru.geekbrains.lesson1.participants.Participant;

/**
 * Класс - полоса препятствий
 */
public class Course {

    private Obstacle[] obstacles;

    public Course(Obstacle... obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team) {
        System.out.println("СТАРТ!");
        System.out.println();
        for (Participant participant : team.getParticipants()) {
            participant.getInfo(true);
            for (Obstacle obstacle : obstacles) {
                obstacle.doIt(participant);

                // если участник сошел с дистанции, то нет смысла двигать его дальше
                if (!participant.isOnDistance()) {
                    break;
                }
            }
            System.out.println();
        }
    }
}
