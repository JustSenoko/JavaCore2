package ru.geekbrains.lesson1.participants;

public class Robot implements Participant {

    private int number;
    private double maxRunLength;
    private double maxJumpHeight;
    private double maxSwimLength;
    private boolean onDistance;

    public Robot(int number, double maxRunLength, double maxJumpHeight, double maxSwimLength) {
        this.number = number;
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
        StringBuilder skills = new StringBuilder();
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
        System.out.println("Робот №" + this.number + skills);
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
                System.out.println("Робот №" + this.number + " сошел с дистанции");
            }
        }
        return false;
    }
}
