package ru.geekbrains.lesson1.participants.animals;

public class Cat extends Animal {

    public Cat(String name, double maxRunLength, double maxJumpHeight) {
        super(name, maxRunLength, maxJumpHeight, 0);
    }

    @Override
    public void getInfo(boolean showSkills) {
        System.out.print("Кот ");
        super.getInfo(showSkills);
    }

    @Override
    public void swim(double distance) {
        this.onDistance = false;
        System.out.println("у меня лапки!");
    }
}
