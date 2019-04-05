package ru.geekbrains.lesson2;

class MyArrayDataException extends Exception{
    MyArrayDataException(int row, int col) {
        super(String.format("В элементе массива [%d, %d] неправильный тип данных%n", row, col));
    }

    MyArrayDataException(int row, int col, Throwable cause) {
        super(String.format("В элементе массива [%d, %d] неправильный тип данных%n", row, col), cause);
    }
}
