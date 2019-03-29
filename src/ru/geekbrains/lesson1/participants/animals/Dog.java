package ru.geekbrains.lesson1.participants.animals;

public class Dog extends Animal {

    public Dog(String name, double maxRunLength, double maxJumpHeight, double maxSwimLength) {
        super(name, maxRunLength, maxJumpHeight, maxSwimLength);
    }

    @Override
    public void getInfo(boolean showSkills) {
        System.out.print("Собака ");
        super.getInfo(showSkills);
    }
}
