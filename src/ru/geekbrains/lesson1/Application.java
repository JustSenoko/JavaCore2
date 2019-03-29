package ru.geekbrains.lesson1;

import ru.geekbrains.lesson1.course.*;
import ru.geekbrains.lesson1.participants.Robot;
import ru.geekbrains.lesson1.participants.animals.*;


/**
 * Класс для запуска приложения - симулятор кросса
 */
public class Application {

    public static void main(String[] args) {
        Team team = new Team("Метеор",
                new Cat("Барсик", 700, 5),
                new Dog("Барбос", 1000, 0.5, 15),
                new Human("Вася", 2000, 0.3, 50),
                new Robot(1, 100, 0.1, 0.2)
        );

        Course course = new Course(
                new Cross(50),
                new Wall(0.3),
                new Cross(90),
                new Water(5)
        );
        team.introduce();
        course.doIt(team);
        team.showResults();
    }
}
