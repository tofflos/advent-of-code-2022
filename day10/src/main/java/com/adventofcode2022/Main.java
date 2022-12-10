package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.IntStream;

class Main {

    public static void main(String[] args) throws Exception {

        var lines = new ArrayDeque<String>(Files.readAllLines(Paths.get("10.in")));
        var signals = new ArrayList<Integer>();

        int n = 0, x = 1;

        while (!lines.isEmpty()) {

            var t = lines.removeFirst().split(" ");

            if (t[0].equals("noop")) {
                n++;
                signals.add(x);
                continue;
            }

            for (int i = 0; i < 2; i++) {
                n++;
                signals.add(x);
            }

            x += Integer.parseInt(t[1]);
        }

        signals.add(x);

        var part1 = IntStream.of(20, 60, 100, 140, 180, 220)
                .map(cycle -> cycle * signals.get(cycle - 1))
                .sum();

        System.out.println("Day 10: " + part1);

        var crt = new char[6][40];

        for (n = 0; n < 240; n++) {
            x = signals.get(n);

            var c = n % 40;
            var r = n / 40;

            crt[r][c] = c == x - 1 || c == x || c == x + 1 ? '#' : '.';
        }

        for (var row : crt) {
            System.out.println(new String(row));
        }
    }
}
