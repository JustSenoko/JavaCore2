package ru.geekbrains.lesson2;

class MyArraySizeException extends Exception {

    MyArraySizeException(int requiredSize) {
        super(String.format("Размерность переданного массива отличается от %d%n", requiredSize));
    }

    MyArraySizeException(int requiredSize, Throwable cause) {
        super(String.format("Размерность переданного массива отличается от %d%n", requiredSize), cause);
    }
}
