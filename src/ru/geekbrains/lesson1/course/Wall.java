package ru.geekbrains.lesson1.course;

import ru.geekbrains.lesson1.participants.Participant;

public class Wall extends Obstacle {

    public Wall(double height) {
        super(height);
    }

    @Override
    public void doIt(Participant participant) {
        participant.jump(this.size);
    }
}
