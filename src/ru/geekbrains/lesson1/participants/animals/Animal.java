package ru.geekbrains.lesson1.participants.animals;

import ru.geekbrains.lesson1.participants.Participant;

public abstract class Animal implements Participant {

    String name;
    private double maxRunLength;
    private double maxJumpHeight;
    private double maxSwimLength;
    boolean onDistance = true;

    Animal(String name, double maxRunLength, double maxJumpHeight, double maxSwimLength) {
        this.name = name;
        this.maxRunLength = maxRunLength;
        this.maxSwimLength = maxSwimLength;
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public boolean isOnDistance() {
        return this.onDistance;
    }

    @Override
    public void getInfo(boolean showSkills) {
        StringBuilder skills = new StringBuilder(" (");
        if (showSkills) {
            skills.append(" (");
            if (this.maxRunLength > 0) {
                skills.append("бегает на ");
                skills.append(this.maxRunLength);
                skills.append(" м");
            } else {
                skills.append("не бегает");
            }
            skills.append(", ");
            if (this.maxJumpHeight > 0) {
                skills.append("прыгает на ");
                skills.append(this.maxJumpHeight);
                skills.append(" м");
            } else {
                skills.append("не прыгает");
            }
            skills.append(", ");
            if (this.maxSwimLength > 0) {
                skills.append("плавает на ");
                skills.append(this.maxSwimLength);
                skills.append(" м");
            } else {
                skills.append("не плавает");
            }
            skills.append(")");
        }
        System.out.println(this.name + skills);
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
                System.out.println(this.name + " сошел с дистанции");
            }
        }
        return false;
    }
}
