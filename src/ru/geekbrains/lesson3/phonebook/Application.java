package ru.geekbrains.lesson3.phonebook;

import java.util.List;

public class Application {

    private static Phonebook phonebook = new Phonebook();

    public static void main(String[] args) {

        phonebook.add("Иванов", "111-11-11");
        phonebook.add("Петров", "222-22-22");
        phonebook.add("Сидоров", "333-33-33");
        phonebook.add("Иванов", "444-44-44");

        printPhones("Иванов");
        printPhones("Петров");
        printPhones("Кузнецов");
    }

    private static void printPhones(String name) {
        List<Record> records = phonebook.get(name);
        System.out.printf("%s: ", name
        );
        if (records.size() == 0) {
            System.out.println("номер не найден");
            return;
        }
        for (Record record : records) {
            System.out.printf("%s, ", record.getPhone());
        }
        System.out.println();
    }
}
