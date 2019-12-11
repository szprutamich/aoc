package y2019;

import org.apache.commons.lang3.tuple.Pair;
import utils.Util;

import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    private Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> data = new HashMap<>();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day10 day10 = new Day10();
        Pair<Integer, Integer> station = day10.solveA();
        System.out.println("A: " + day10.data.get(station).size());
        System.out.println("B: " + day10.solveB(station));
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day10() throws Exception {
        final int[] y = {0};
        Files.lines(Util.loadPath("/day10.txt")).forEach(l -> {
            for (int x = 0; x < l.length(); x++) {
                if (l.charAt(x) == '#') {
                    data.put(Pair.of(x, y[0]), new ArrayList<>());
                }
            }
            ++y[0];
        });
    }

    private Pair<Integer, Integer> solveA() {
        for (Pair<Integer, Integer> first : data.keySet()) {
            for (Pair<Integer, Integer> second : data.keySet()) {
                if (!first.equals(second) && canSeeEachOther(first, second)) {
                    data.get(first).add(second);
                }
            }
        }
        int max = 0;
        Pair<Integer, Integer> station = null;
        for (Pair<Integer, Integer> pair : data.keySet()) {
            int value = data.get(pair).size();
            if (value > max) {
                max = value;
                station = pair;
            }
        }
        return station;
    }

    private int solveB(Pair<Integer, Integer> station) {
        Pair<Integer, Integer> result = find200(station);
        return result.getLeft() * 100 + result.getRight();
    }

    private double angle(Pair<Integer, Integer> station, Pair<Integer, Integer> point) {
        double theta = Math.atan2(point.getRight() - station.getRight(), point.getLeft() - station.getLeft());
        theta += Math.PI / 2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    private Pair<Integer, Integer> find200(Pair<Integer, Integer> station) {
        List<Pair<Integer, Integer>> points = data.get(station);
        List<Pair<Integer, Integer>> sorted = points.stream()
                .sorted(Comparator.comparingDouble(p -> angle(station, p))).collect(Collectors.toList());
        return sorted.get(199);
    }

    private boolean canSeeEachOther(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        int minX = Math.min(first.getLeft(), second.getLeft());
        int maxX = Math.max(first.getLeft(), second.getLeft());
        int minY = Math.min(first.getRight(), second.getRight());
        int maxY = Math.max(first.getRight(), second.getRight());
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Pair<Integer, Integer> between = Pair.of(x, y);
                if (!first.equals(between) && !second.equals(between)
                        && data.containsKey(between) && collinear(first, second, between)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean collinear(Pair<Integer, Integer> f, Pair<Integer, Integer> s, Pair<Integer, Integer> b) {
        return f.getLeft() * (s.getRight() - b.getRight()) +
                s.getLeft() * (b.getRight() - f.getRight()) +
                b.getLeft() * (f.getRight() - s.getRight()) == 0;
    }
}
