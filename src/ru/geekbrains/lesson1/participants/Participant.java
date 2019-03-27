package ru.geekbrains.lesson1.participants;

/**
 * Интерфейс - участник соревнований
 */
public interface Participant {

    boolean isOnDistance();

    void run(double distance);

    void jump(double height);

    void swim(double distance);
}
