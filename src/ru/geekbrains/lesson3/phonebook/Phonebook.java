package ru.geekbrains.lesson3.phonebook;

import java.util.*;

class Phonebook {
    private Map<String, Set<String>> phonebook = new HashMap<>();

    void add(String name, String phone) {
        Set<String> records = new HashSet<>();
        phonebook.putIfAbsent(name, records);
        records = phonebook.get(name);
        records.add(phone);

        phonebook.put(name, records);
    }

    Set<String> get(String name) {
        if (phonebook.containsKey((name))) {
            return phonebook.get(name);
        }
        return Collections.emptySet();
    }
}
