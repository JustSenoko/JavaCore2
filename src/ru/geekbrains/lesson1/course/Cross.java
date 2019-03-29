package ru.geekbrains.lesson1.course;

import ru.geekbrains.lesson1.participants.Participant;

public class Cross extends Obstacle {

    public Cross(double distance) {
        super(distance);
    }

    @Override
    public void doIt(Participant participant) {
        participant.run(this.size);
    }
}
