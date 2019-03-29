package ru.geekbrains.lesson1.course;

import ru.geekbrains.lesson1.participants.Participant;

/**
 * Класс абстрактное препятствие
 */
public abstract class Obstacle {
    double size;

    Obstacle(double size) {
        this.size = size;
    }

    public abstract void doIt(Participant participant);
}
