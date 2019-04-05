package ru.geekbrains.lesson3.phonebook;

import java.util.*;

class Phonebook {
    private Map<String, List<Record>> phonebook = new HashMap<>();

    void add(String name, String phone) {
        List<Record> records = new ArrayList<>();
        if (phonebook.containsKey(name)) {
            records = phonebook.get(name);
        }
        records.add(new Record(name, phone));
        phonebook.put(name, records);
    }

    List<Record> get(String name) {
        if (phonebook.containsKey((name))) {
            return phonebook.get(name);
        }
        return Collections.emptyList();
    }
}
