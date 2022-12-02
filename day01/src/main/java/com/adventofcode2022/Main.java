package com.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        var lines = Files.readAllLines(Paths.get("1.in"));
        var elves = new ArrayList<Integer>();
        var calories = 0;

        for (var line : lines) {
            if (line.isBlank()) {
                elves.add(calories);
                calories = 0;
            } else {
                calories = calories + Integer.parseInt(line);
            }
        }

        elves.add(calories);

        System.out.println("Day 1a: " + elves.stream()
                .max(Integer::compareTo).orElseThrow());

        System.out.println("Day 1b: " + elves.stream()
                .sorted((a, b) -> Integer.compare(b, a))
                .limit(3)
                .mapToInt(Integer::intValue).sum());
    }
}
