package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {

    public static void main(String[] args) throws Exception {

        var lines = Files.readAllLines(Paths.get("5.in"));
        var moves = lines.stream().skip(10).map(Move::of).toList();

        System.out.println("Day 5a: " + part1(stacks(lines), moves));
        System.out.println("Day 5b: " + part2(stacks(lines), moves));
    }

    static List<ArrayDeque<String>> stacks(List<String> lines) {

        var stacks = IntStream.range(0, 9).mapToObj(i -> new ArrayDeque<String>()).toList();

        for (var line : lines) {
            if (line.startsWith(" 1")) {
                break;
            }

            var n = 0;

            for (int i = 1; i < line.length(); i += 4) {
                var crate = line.substring(i, i + 1);

                if (!crate.isBlank()) {
                    stacks.get(n).addLast(crate);
                }

                n++;
            }
        }

        return stacks;
    }

    static String part1(List<ArrayDeque<String>> stacks, List<Move> moves) {

        for (var move : moves) {
            for (int i = 0; i < move.quantity; i++) {
                var crate = stacks.get(move.from - 1).removeFirst();
                stacks.get(move.to - 1).addFirst(crate);
            }
        }

        return stacks.stream().map(Deque::getFirst).collect(Collectors.joining());
    }

    static String part2(List<ArrayDeque<String>> stacks, List<Move> moves) {

        for (var move : moves) {
            var t = new ArrayDeque<String>();

            for (int i = 0; i < move.quantity; i++) {
                var crate = stacks.get(move.from - 1).removeFirst();
                t.addFirst(crate);
            }

            while (!t.isEmpty()) {
                stacks.get(move.to - 1).addFirst(t.removeFirst());
            }
        }

        return stacks.stream().map(Deque::getFirst).collect(Collectors.joining());
    }

    record Move(int quantity, int from, int to) {

        static final Pattern PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        static Move of(String s) {

            var matcher = PATTERN.matcher(s);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Unable to parse Move: " + s);
            }

            return new Move(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)));
        }
    }
}
