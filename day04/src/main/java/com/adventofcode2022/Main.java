package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

class Main {

    public static void main(String[] args) throws Exception {

        var assignments = Files.lines(Paths.get("4.in")).map(Assignment::of).toList();

        var count1 = assignments.stream()
                .filter(a -> a.x1 <= a.y1 && a.y2 <= a.x2 || a.y1 <= a.x1 && a.x2 <= a.y2)
                .count();

        System.out.println("Day 4a: " + count1);

        var count2 = assignments.stream()
                .filter(a -> a.x1 <= a.y2 && a.y1 <= a.x2)
                .count();

        System.out.println("Day 4b: " + count2);
    }

    record Assignment(int x1, int x2, int y1, int y2) {

        static Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

        static Assignment of(String s) {
            var matcher = PATTERN.matcher(s);

            matcher.matches();

            return new Assignment(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4))
            );
        }
    }
}
