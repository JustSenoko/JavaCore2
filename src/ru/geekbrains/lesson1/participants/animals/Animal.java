package ru.geekbrains.lesson1.participants.animals;

import ru.geekbrains.lesson1.participants.Participant;

public abstract class Animal implements Participant {

    private String name;
    double maxRunLength;
    double maxJumpHeight;
    double maxSwimLength;
    boolean onDistance;

    public Animal(String name, double maxRunLength, double maxJumpHeight, double maxSwimLength) {
        this.name = name;
        this.maxRunLength = maxRunLength;
        this.maxSwimLength = maxSwimLength;
        this.maxJumpHeight = maxJumpHeight;
        this.onDistance = true;
    }

    @Override
    public boolean isOnDistance() {
        return this.onDistance;
    }

    @Override
    public void getInfo(boolean showSkills) {
        String skills = "";
        if (showSkills) {
            if (this.maxRunLength > 0) {
                skills += "бегает на " + this.maxRunLength + " м";
            } else {
                skills += "не бегает";
            }
            skills += ", ";
            if (this.maxJumpHeight > 0) {
                skills += "прыгает на " + this.maxJumpHeight + " м";
            } else {
                skills += "не прыгает";
            }
            if (this.maxSwimLength > 0) {
                skills += "плавает на " + this.maxSwimLength + " м";
            } else {
                skills += "не плавает";
            }
            skills = " (" + skills + ")";
        }
        System.out.println(this.name + skills);
    }

    String getName(){
        return this.name;
    }

    @Override
    public void run(double length) {
        if (canDoIt(length, this.maxRunLength, "далеко")) {
            System.out.println("бежит " + length + " м");
        }
    }

    @Override
    public void jump(double height) {
        if (canDoIt(height, this.maxJumpHeight, "высоко")) {
            System.out.println("прыгает на " + height + " м");
        }
    }

    @Override
    public void swim(double length) {
        if (canDoIt(length, this.maxSwimLength, "далеко")) {
            System.out.println("плывет " + length + " м");
        }
    }

    private boolean canDoIt(double length, double maxLength, String errorText) {
        if (this.onDistance) {
            if (length <= maxLength) {
                return true;
            } else {
                System.out.println(length + "м - это слишком " + errorText + " для меня");
                this.onDistance = false;
            }
        }
        return false;
    }
}
