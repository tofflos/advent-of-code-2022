package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

class Main {

    record Monkey(List<Long> items, Function<Long, Long> operation, int divisor, int positive, int negative) {

    }

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Paths.get("11.in"));
        var monkeys = parse(lines);
        var product = monkeys.stream().map(Monkey::divisor).reduce(Math::multiplyExact).orElseThrow();

        System.out.println("Day 11a: " + calcuclate(parse(lines), 20, (worry) -> worry / 3));
        System.out.println("Day 11b: " + calcuclate(parse(lines), 10000, (worry) -> worry % product));
    }

    static long calcuclate(List<Monkey> monkeys, int rounds, Function<Long, Long> relaxer) {
        var counters = new int[monkeys.size()];

        for (int n = 0; n < rounds; n++) {
            for (int i = 0; i < monkeys.size(); i++) {
                var monkey = monkeys.get(i);

                monkey.items.sort(Long::compareTo);

                for (var item : monkey.items) {
                    var worry = monkey.operation.apply(item);
                    worry = relaxer.apply(worry);

                    var next = monkeys.get(worry % monkey.divisor == 0 ? monkey.positive : monkey.negative);

                    next.items.add(worry);
                    counters[i]++;
                }

                monkey.items.clear();
            }
        }

        return Arrays.stream(counters)
                .boxed()
                .sorted(Collections.reverseOrder(Integer::compareTo))
                .limit(2)
                .map(i -> (long) i)
                .reduce(Math::multiplyExact)
                .orElseThrow();
    }

    static List<Monkey> parse(List<String> lines) {
        var monkeys = new ArrayList<Monkey>();

        List<Long> items = null;
        Function<Long, Long> operation = null;
        int divisor = -1, positive = -1, negative = -1;

        for (var line : lines) {

            if (line.startsWith("Monkey")) {
                continue;
            }

            if (line.startsWith("  Starting items: ")) {
                items = Arrays.stream(line.replace("  Starting items: ", "").split(","))
                        .map(String::strip)
                        .map(Long::parseLong)
                        .collect(ArrayList::new, List::add, (l, r) -> l.addAll(r));
            }

            if (line.startsWith("  Operation: ")) {
                var t = line.replace("  Operation: ", "");

                if (t.equals("new = old * old")) {
                    operation = (worry) -> Math.multiplyExact(worry, worry);
                } else if (t.startsWith("new = old *")) {
                    operation = (worry) -> Math.multiplyExact(worry, Long.parseLong(t.replace("new = old * ", "")));
                } else if (t.startsWith("new = old + ")) {
                    operation = (worry) -> Math.addExact(worry, Long.parseLong(t.replace("new = old + ", "")));
                } else {
                    throw new IllegalArgumentException("Unable to parse operation: " + t);
                }
            }

            if (line.startsWith("  Test: divisible by ")) {
                divisor = Integer.parseInt(line.replace("  Test: divisible by ", ""));
            }

            if (line.startsWith("    If true: throw to monkey ")) {
                positive = Integer.parseInt(line.replace("    If true: throw to monkey ", ""));
            }

            if (line.startsWith("    If false: throw to monkey ")) {
                negative = Integer.parseInt(line.replace("    If false: throw to monkey ", ""));
            }

            if (line.isBlank()) {
                monkeys.add(new Monkey(items, operation, divisor, positive, negative));
            }
        }

        return monkeys;
    }
}
