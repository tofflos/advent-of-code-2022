package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

class Main {

    public static void main(String[] args) throws Exception {

        var motions = Files.lines(Paths.get("9.in"))
                .<Character>mapMulti((line, consumer) -> {
                    var d = line.charAt(0);
                    var q = Integer.parseInt(line.substring(2));

                    for (var i = 0; i < q; i++) {
                        consumer.accept(d);
                    }
                })
                .toArray(Character[]::new);

        System.out.println("Day 9b: " + simulate(motions, 2));
        System.out.println("Day 9b: " + simulate(motions, 10));
    }

    static int simulate(Character[] motions, int count) {
        var knots = IntStream.range(0, count).mapToObj(i -> new Knot(0, 0)).toArray(Knot[]::new);

        var visited = new HashSet<Knot>(List.of(knots[knots.length - 1]));

        for (var motion : motions) {

            for (int i = 0; i < knots.length - 1; i++) {
                var current = i == 0 ? knots[i].move(motion) : knots[i];
                var next = knots[i + 1];

                knots[i] = current;

                if (current.isAdjacent(next)) {
                    break;
                }

                var directions = current.x != next.x && current.y != next.y ? new int[][]{{1, 1}, {1, -1}, {-1, -1}, {-1, 1}} : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

                var knot = Arrays.stream(directions)
                        .map(direction -> new Knot(next.x + direction[0], next.y + direction[1]))
                        .filter(other -> current.isAdjacent(other))
                        .findFirst()
                        .orElseThrow();

                knots[i + 1] = knot;

                if (i + 1 == knots.length - 1) {
                    visited.add(knot);
                }
            }
        }

        return visited.size();
    }

    record Knot(int x, int y) {

        Knot move(char direction) {
            return switch (direction) {
                case 'U' -> new Knot(x, y + 1);
                case 'R' -> new Knot(x + 1, y);
                case 'D' -> new Knot(x, y - 1);
                case 'L' -> new Knot(x - 1, y);
                default  -> throw new IllegalArgumentException();
            };
        }

        boolean isAdjacent(Knot other) {
            var directions = new int[][]{{0, 0}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

            return Arrays.stream(directions)
                    .anyMatch(direction -> this.x + direction[0] == other.x && this.y + direction[1] == other.y);
        }
    }
}
