package ru.geekbrains.lesson3.phonebook;

class Record {
    private String name;
    private String phone;

    Record(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    String getPhone() {
        return this.phone;
    }
}
