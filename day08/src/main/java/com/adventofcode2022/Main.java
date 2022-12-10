package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class Main {

    public static void main(String[] args) throws Exception {

        var plantation = Files.lines(Paths.get("8.in"))
                .map(line -> Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);

        var visible = 0;

        for (var y = 0; y < plantation.length; y++) {
            for (var x = 0; x < plantation[y].length; x++) {
                if (isVisible(plantation, x, y)) {
                    visible++;
                }
            }
        }

        System.out.println("Day 8a: " + visible);

        var max = 0;

        for (var y = 0; y < plantation.length; y++) {
            for (var x = 0; x < plantation[y].length; x++) {
                var score = calculate(plantation, x, y);

                if (score > max) {
                    max = score;
                }
            }
        }

        System.out.println("Day 8b: " + max);
    }

    static int calculate(int[][] plantation, int x, int y) {
        var directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        return Arrays.stream(directions)
                .mapToInt(direction -> {
                    var dx = direction[0];
                    var dy = direction[1];
                    var px = x;
                    var py = y;
                    var count = 0;

                    while (0 < px && px < plantation[y].length - 1 && 0 < py && py < plantation.length - 1) {
                        count++;

                        if (plantation[y][x] <= plantation[py + dy][px + dx]) {
                            break;
                        }

                        px = px + dx;
                        py = py + dy;
                    }

                    return count;
                }).reduce(Math::multiplyExact).orElseThrow();
    }

    static boolean isVisible(int[][] plantation, int x, int y) {
        var directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        return Arrays.stream(directions)
                .map(direction -> {
                    var dx = direction[0];
                    var dy = direction[1];
                    var px = x;
                    var py = y;

                    while (0 < px && px < plantation[y].length - 1 && 0 < py && py < plantation.length - 1) {
                        if (plantation[y][x] <= plantation[py + dy][px + dx]) {
                            return false;
                        }

                        px = px + dx;
                        py = py + dy;
                    }

                    return true;
                }).anyMatch(Boolean::valueOf);
    }
}
