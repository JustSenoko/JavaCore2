package ru.geekbrains.lesson1.participants.animals;

public class Human extends Animal {

    public Human(String name, double maxRunLength, double maxJumpHeight, double maxSwimLength) {
        super(name, maxRunLength, maxJumpHeight, maxSwimLength);
    }

    @Override
    public void getInfo(boolean showSkills) {
        System.out.print("Человек ");
        super.getInfo(showSkills);
    }
}
