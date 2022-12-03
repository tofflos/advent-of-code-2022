package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Main {

    public static void main(String[] args) throws Exception {

        var sum1 = Files.lines(Paths.get("3.in"))
                .map(line -> intersection(line.substring(0, line.length() / 2), line.substring(line.length() / 2)))
                .mapToInt(item -> priority(item.charAt(0)))
                .sum();

        System.out.println("Day 3a: " + sum1);

        var index = new AtomicInteger();

        var sum2 = Files.lines(Paths.get("3.in"))
                .collect(Collectors.groupingBy(t -> index.getAndIncrement() / 3))
                .values().stream()
                .flatMap(group -> group.stream().reduce(Main::intersection).stream())
                .mapToInt(item -> priority(item.charAt(0)))
                .sum();

        System.out.println("Day 3b: " + sum2);
    }
    
    static int priority(char c) {
        return Character.isLowerCase(c) ? c - 97 + 1 : c - 65 + 27;
    }

    static String intersection(String s1, String s2) {

        var set1 = new HashSet<>(Arrays.asList(s1.split("")));
        var set2 = new HashSet<>(Arrays.asList(s2.split("")));

        set1.retainAll(set2);

        return set1.stream().collect(Collectors.joining());
    }
}
