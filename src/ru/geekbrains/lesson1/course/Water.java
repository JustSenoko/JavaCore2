package ru.geekbrains.lesson1.course;


import ru.geekbrains.lesson1.participants.Participant;

public class Water extends Obstacle {

    public Water(double distance) {
        super(distance);
    }

    @Override
    public void doIt(Participant participant) {
        participant.swim(this.size);
    }
}
