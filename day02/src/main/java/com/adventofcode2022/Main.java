package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

class Main {

    record Permutation(String opponent, String self, int self_points, String outcome, int outcome_points) {
    }

    static Set<Permutation> permutations = Set.of(
            new Permutation("A", "X", 1, "Y", 3),
            new Permutation("A", "Y", 2, "Z", 6),
            new Permutation("A", "Z", 3, "X", 0),
            new Permutation("B", "X", 1, "X", 0),
            new Permutation("B", "Y", 2, "Y", 3),
            new Permutation("B", "Z", 3, "Z", 6),
            new Permutation("C", "X", 1, "Z", 6),
            new Permutation("C", "Y", 2, "X", 0),
            new Permutation("C", "Z", 3, "Y", 3)
    );

    public static void main(String[] args) throws Exception {

        var score1 = Files.lines(Paths.get("2.in"))
                .mapToInt(line -> {
                    var t = line.split(" ");
                    var p = findByOpponentAndSelf(t[0], t[1]);
                    return p.self_points + p.outcome_points;
                })
                .sum();

        System.out.println("Day 2a: " + score1);

        var score2 = Files.lines(Paths.get("2.in"))
                .mapToInt(line -> {
                    var t = line.split(" ");
                    var p = findByOpponentAndOutcome(t[0], t[1]);
                    return p.self_points + p.outcome_points;
                })
                .sum();
        
        System.out.println("Day 2b: " + score2);
    }

    static Permutation findByOpponentAndSelf(String opponent, String self) {
        return permutations.stream()
                .filter(r -> r.opponent.equals(opponent) && r.self.equals(self))
                .findAny()
                .orElseThrow();
    }

    static Permutation findByOpponentAndOutcome(String opponent, String outcome) {
        return permutations.stream()
                .filter(r -> r.opponent.equals(opponent)&& r.outcome.equals(outcome))
                .findAny()
                .orElseThrow();
    }
}
