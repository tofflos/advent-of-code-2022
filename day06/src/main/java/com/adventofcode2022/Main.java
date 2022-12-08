package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collectors;

class Main {

    public static void main(String[] args) throws Exception {

        var part1 = Files.lines(Paths.get("6.in"))
                .map(line -> {
                    for (int i = 0; i < line.length() - 4; i++) {
                        if (isMarker(line.substring(i, i + 4))) {
                            return i + 4;
                        }
                    }

                    throw new IllegalArgumentException();
                }).findAny().orElseThrow();

        System.out.println("Part 6a: " + part1);

        var part2 = Files.lines(Paths.get("6.in"))
                .map(line -> {
                    for (int i = 0; i < line.length() - 14; i++) {
                        if (isMarker(line.substring(i, i + 14))) {
                            return i + 14;
                        }
                    }

                    throw new IllegalArgumentException();
                }).findAny().orElseThrow();

        System.out.println("Part 6b: " + part2);
    }

    static boolean isMarker(String s) {
        return s.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values()
                .stream().allMatch(count -> count == 1);
    }
}
