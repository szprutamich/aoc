package y2019;

import org.apache.commons.lang3.tuple.Triple;
import utils.Util;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    private List<Triple<Integer, Integer, Integer>> positions = new ArrayList<>(4);

    private List<Triple<Integer, Integer, Integer>> velocities = new ArrayList<>(4);

    private Pattern PATTERN = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day12 day = new Day12();
        day.solveA(1000);
        day.solveB();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day12() throws Exception {
        reset();
    }

    private void reset() throws Exception {
        positions = new ArrayList<>();
        velocities = new ArrayList<>();
        Files.lines(Util.loadPath("/day12.txt")).forEach(l -> {
            Matcher matcher = PATTERN.matcher(l);
            if (matcher.find()) {
                positions.add(Triple.of(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))
                ));
                velocities.add(Triple.of(0, 0, 0));
            }
        });
    }

    private void solveA(int steps) {
        for (int s = 0; s < steps; s++) {
            singleStep();
        }
        int totalEnergy = 0;
        for (int i = 0; i < positions.size(); i++) {
            Triple<Integer, Integer, Integer> p = positions.get(i);
            Triple<Integer, Integer, Integer> v = velocities.get(i);
            totalEnergy += (Math.abs(p.getLeft()) + Math.abs(p.getMiddle()) + Math.abs(p.getRight()))
                    * (Math.abs(v.getLeft()) + Math.abs(v.getMiddle()) + Math.abs(v.getRight()));
        }
        System.out.println("A: " + totalEnergy);
    }

    private void solveB() throws Exception {
        reset();
        List<Triple<Integer, Integer, Integer>> start = new ArrayList<>(positions);
        List<Long> cycles = Arrays.asList(0L, 0L, 0L);
        long i = 0;
        do {
            i++;
            singleStep();
            for (int d = 0; d < 3; d++) {
                if (cycles.get(d).equals(0L) && dimensionMatchStart(d, start)) {
                    cycles.set(d, i + 1);
                }
            }
        } while (cycles.stream().anyMatch(v -> v.equals(0L)));
        long gcd = gcd(cycles.get(0), gcd(cycles.get(1), cycles.get(2)));
        System.out.println("B: " + (cycles.get(0) / gcd) * (cycles.get(1) / gcd) * (cycles.get(2) / gcd));
    }

    private boolean dimensionMatchStart(int dimension, List<Triple<Integer, Integer, Integer>> start) {
        for (int i = 0; i < positions.size(); i++) {
            if (!getPosition(dimension, positions.get(i)).equals(getPosition(dimension, start.get(i)))) {
                return false;
            }
        }
        return true;
    }

    private long gcd(long x, long y) {
        while (x != y) {
            if (x > y) {
                x -= y;
            } else {
                y -= x;
            }
        }
        return x;
    }

    private Integer getPosition(int dimension, Triple<Integer, Integer, Integer> position) {
        switch (dimension) {
            case 0:
                return position.getLeft();
            case 1:
                return position.getMiddle();
            case 2:
            default:
                return position.getRight();
        }
    }

    private void singleStep() {
        for (int i = 0; i < positions.size(); i++) {
            Triple<Integer, Integer, Integer> first = positions.get(i);
            for (int j = 0; j < positions.size(); j++) {
                if (i != j) {
                    Triple<Integer, Integer, Integer> second = positions.get(j);
                    Triple<Integer, Integer, Integer> currentVelocity = velocities.get(i);
                    velocities.set(i, Triple.of(
                            currentVelocity.getLeft() + comparePositions(first, second, 0),
                            currentVelocity.getMiddle() + comparePositions(first, second, 1),
                            currentVelocity.getRight() + comparePositions(first, second, 2)
                    ));
                }
            }
        }
        for (int i = 0; i < positions.size(); i++) {
            Triple<Integer, Integer, Integer> currentPosition = positions.get(i);
            Triple<Integer, Integer, Integer> currentVelocity = velocities.get(i);
            positions.set(i, Triple.of(
                    currentPosition.getLeft() + currentVelocity.getLeft(),
                    currentPosition.getMiddle() + currentVelocity.getMiddle(),
                    currentPosition.getRight() + currentVelocity.getRight()
            ));
        }
    }

    private int comparePositions(
            Triple<Integer, Integer, Integer> moon1, Triple<Integer, Integer, Integer> moon2, int dimension) {
        switch (dimension) {
            case 0:
                return compare(moon1.getLeft(), moon2.getLeft());
            case 1:
                return compare(moon1.getMiddle(), moon2.getMiddle());
            case 2:
            default:
                return compare(moon1.getRight(), moon2.getRight());
        }
    }

    private int compare(Integer val1, Integer val2) {
        return val2.compareTo(val1);
    }
}
