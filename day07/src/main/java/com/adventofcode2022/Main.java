package com.adventofcode2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static void main(String[] args) throws Exception {

        var lines = Files.readAllLines(Paths.get("7.in"));

        var root = Directory.of("/", null);
        var current = root;

        for (var line : lines) {
            var t = line.split(" ");

            if (line.startsWith("$")) {
                if ("cd".equals(t[1]) && "..".equals(t[2])) {
                    current = current.parent();
                } else if ("cd".equals(t[1]) && "/".equals(t[2])) {
                    current = root;
                } else if ("cd".equals(t[1])) {
                    current = (Directory) current.children().stream()
                            .filter(node -> node instanceof Directory && t[2].equals(node.name()))
                            .findAny()
                            .orElseThrow();
                }
            } else if (line.startsWith("dir")) {
                current.children().add(Directory.of(t[1], current));
            } else {
                current.children().add(new File(t[1], current, Integer.parseInt(t[0])));
            }
        }

        var part1 = traverse(root).stream()
                .mapToInt(Node::size)
                .filter(size -> size <= 100000)
                .sum();

        System.out.println("Day 7a: " + part1);

        var total = 70000000;
        var unused = total - root.size();

        var part2 = traverse(root).stream()
                .mapToInt(Node::size)
                .filter(size -> unused + size > 30000000)
                .sorted()
                .findFirst()
                .orElseThrow();

        System.out.println("Day 7b: " + part2);
    }

    static List<Directory> traverse(Directory directory) {
        return directory.children().stream()
                .filter(Directory.class::isInstance)
                .map(Directory.class::cast)
                .<Directory>mapMulti((dir, consumer) -> {
                    consumer.accept(dir);
                    traverse(dir).forEach(consumer::accept);
                }).toList();
    }
}

sealed interface Node permits Directory, File {

    String name();

    Directory parent();

    int size();
}

record Directory(String name, Directory parent, List<Node> children) implements Node {

    static Directory of(String name, Directory parent) {
        return new Directory(name, parent, new ArrayList<>());
    }

    @Override
    public int size() {
        return children.stream().mapToInt(Node::size).sum();
    }
}

record File(String name, Directory parent, int size) implements Node {

}
