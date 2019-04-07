package ru.geekbrains.lesson3;

import java.util.*;

public class Words {
    public static void main(String[] args) {
        String[] words = {
                "понедельник",
                "вторник",
                "среда",
                "четверг",
                "пятница",
                "суббота",
                "воскресенье",
                "понедельник",
                "вторник",
                "среда"
        };

        Set<String> wordsSet = new HashSet<>();
        Collections.addAll(wordsSet, words);
        System.out.println("Уникальные значения: " + wordsSet);

        Map<String, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            wordsMap.putIfAbsent(word, 0);
            Integer wordCount = wordsMap.get(word);
            wordsMap.put(word, ++wordCount);
            //wordsMap.computeIfPresent(word, (k, count) -> count + 1);
        }
        System.out.println("Количество вхождений: " + wordsMap);
    }
}
