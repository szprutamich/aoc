package y2019;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;
import utils.Intcode;
import utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class Day15 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    private Map<Pair<Integer, Integer>, Character> worldMap = new HashMap<>();

    private Pair<Integer, Integer> currentPoint = Pair.of(0, 0);

    private List<Integer> distancesA = new ArrayList<>();
    private List<Integer> distancesB = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day15 day = new Day15();
        day.solve();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day15() throws Exception {
        String[] longs = Util.loadString("/day15.txt").split(",");
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }

    private void solve() {
        LinkedBlockingDeque<Long> inputs = new LinkedBlockingDeque<>();
        LinkedBlockingDeque<Long> outputs = new LinkedBlockingDeque<>();
        Intcode intcode = new Intcode(input);
        boolean findO = false;
        worldMap.put(currentPoint, 'X');
        long worldPoints = 1660; // found experimentally
        while (worldMap.size() < worldPoints || !findO) {
            long input = RandomUtils.nextLong(1L, 5L);
            inputs.add(input);
            intcode.run(inputs, outputs);
            long out = outputs.remove();
            switch ((int) out) {
                case 0:
                    worldMap.putIfAbsent(calculateMove(currentPoint, input), '#');
                    break;
                case 1:
                    currentPoint = calculateMove(currentPoint, input);
                    worldMap.putIfAbsent(currentPoint, ' ');
                    break;
                case 2:
                    currentPoint = calculateMove(currentPoint, input);
                    worldMap.putIfAbsent(currentPoint, 'O');
                    findO = true;
                    break;
            }
        }
        printMap();

        System.out.println("SIZE: " + worldMap.size());
        System.out.println("EMPTY: " + worldMap.entrySet().stream().filter(e -> e.getValue().equals(' ')).count());

        Pair<Integer, Integer> oxygen = worldMap.entrySet().stream().filter(e -> e.getValue().equals('O'))
                .map(Map.Entry::getKey).findAny().orElseThrow(RuntimeException::new);
        while (worldMap.entrySet().stream().anyMatch(e -> e.getValue().equals(' '))) {
            spread(oxygen, 0);
        }
        System.out.println("A: " + distancesA.stream().mapToInt(Integer::intValue).min()
                .orElseThrow(RuntimeException::new));
        System.out.println("B: " + distancesB.stream().mapToInt(Integer::intValue).max()
                .orElseThrow(RuntimeException::new));
    }

    private void spread(Pair<Integer, Integer> from, int start) {
        start++;
        for (int i = 1; i < 5; i++) {
            Pair<Integer, Integer> point = calculateMove(from, i);
            Character value = worldMap.getOrDefault(point, '?');
            if (value.equals('X')) {
                distancesA.add(start);
            }
            if (value.equals(' ') || value.equals('X')) {
                worldMap.put(point, 'O');
                spread(point, start);
                distancesB.add(start);
            }
        }
    }

    private void printMap() {
        int minX = worldMap.keySet().stream().mapToInt(Pair::getLeft).min().orElseThrow(RuntimeException::new);
        int maxX = worldMap.keySet().stream().mapToInt(Pair::getLeft).max().orElseThrow(RuntimeException::new);
        int minY = worldMap.keySet().stream().mapToInt(Pair::getRight).min().orElseThrow(RuntimeException::new);
        int maxY = worldMap.keySet().stream().mapToInt(Pair::getRight).max().orElseThrow(RuntimeException::new);
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                System.out.print(worldMap.getOrDefault(Pair.of(x, y), '?'));
            }
            System.out.println();
        }
    }

    private Pair<Integer, Integer> calculateMove(Pair<Integer, Integer> point, long where) {
        //north (1), south (2), west (3), and east (4)
        switch ((int) where) {
            case 1:
                return Pair.of(point.getLeft(), point.getRight() - 1);
            case 2:
                return Pair.of(point.getLeft(), point.getRight() + 1);
            case 3:
                return Pair.of(point.getLeft() - 1, point.getRight());
            case 4:
            default:
                return Pair.of(point.getLeft() + 1, point.getRight());
        }
    }
}
