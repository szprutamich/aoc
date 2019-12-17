package y2019;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import utils.Intcode;
import utils.PairHelper;
import utils.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import static utils.PairHelper.*;

public class Day17 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    private Map<Pair<Integer, Integer>, Character> map;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day17 day = new Day17();
        day.solve();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day17() throws Exception {
        String[] longs = Util.loadString("/day17.txt").split(",");
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }

    private void solve() {
        LinkedBlockingDeque<Long> outputs = new LinkedBlockingDeque<>();
        Intcode intcode = new Intcode(input);
        intcode.run(new LinkedBlockingDeque<>(), outputs);
        map = buildMap(outputs);
        int sum = map.keySet().stream()
                .filter(this::isScaffold)
                .filter(e -> isScaffold(Pair.of(e.getLeft() + 1, e.getRight())))
                .filter(e -> isScaffold(Pair.of(e.getLeft() - 1, e.getRight())))
                .filter(e -> isScaffold(Pair.of(e.getLeft(), e.getRight() + 1)))
                .filter(e -> isScaffold(Pair.of(e.getLeft(), e.getRight() - 1)))
                .mapToInt(e -> e.getLeft() * e.getRight()).sum();
        System.out.println("A: " + sum);

        StringBuilder fullRoute = new StringBuilder();
        Pair<Integer, Integer> current = map.entrySet().stream().filter(e -> e.getValue() == '^')
                .map(Map.Entry::getKey).findAny().orElseThrow(RuntimeException::new);
        Pair<Integer, Integer> direction = Pair.of(0, -1);
        int counter = 0;
        Pair<Integer, Integer> nextPoint;
        while ((nextPoint = nextPoint(current, direction)) != null) {
            Pair<Integer, Integer> newDirection = PairHelper.getDirection(current, nextPoint);
            boolean turnLeft = isLeft(direction, newDirection);
            boolean turnRight = isRight(direction, newDirection);
            if (turnLeft || turnRight) {
                if (counter != 0) {
                    fullRoute.append(counter).append(",");
                }
                if (turnLeft) {
                    fullRoute.append("L,");
                }
                if (turnRight) {
                    fullRoute.append("R,");
                }
                counter = 0;
            }
            current = PairHelper.sum(newDirection, current);
            direction = newDirection;
            counter++;
        }
        if (counter != 0) {
            fullRoute.append(counter);
        }
        intcode.reset();
        intcode.getInput()[0] = 2;
        intcode.run(buildInputs(fullRoute.toString()), outputs);
        System.out.println("B: " + outputs.getLast());
    }

    private LinkedBlockingDeque<Long> buildInputs(String route) {
        LinkedBlockingDeque<Long> inputs = new LinkedBlockingDeque<>();
        String[] parts = new String[3];
        String tempRoute = route;
        for (int i = 0; i < parts.length; i++) {
            String c = Character.toString((char) (i + 65));
            tempRoute = tempRoute.replaceFirst("([A-C],)*", "");
            int length = tempRoute.length();
            String temp;
            do {
                temp = tempRoute.substring(0, --length);
            } while (StringUtils.countMatches(tempRoute, temp) == 1
                    || !temp.matches(".*\\d+,") || temp.matches(".*[A-C]+.*"));
            tempRoute = tempRoute.replaceAll(temp, c + ",");
            parts[i] = temp.substring(0, temp.length() - 1);
        }
        String instruction = String.format("%s\n%s\n%s\n%s\nn\n", route.replaceAll(parts[0], "A")
                .replaceAll(parts[1], "B").replaceAll(parts[2], "C"), parts[0], parts[1], parts[2]);
        for (int i = 0; i < instruction.length(); i++) {
            inputs.add((long) instruction.charAt(i));
        }
        return inputs;
    }

    private Pair<Integer, Integer> nextPoint(Pair<Integer, Integer> currentPoint, Pair<Integer, Integer> direction) {
        Pair<Integer, Integer> result = sum(currentPoint, direction);
        if (isScaffold(result)) {
            return result;
        }
        Pair<Integer, Integer> left = turnLeft(currentPoint, direction);
        if (isScaffold(left)) {
            return left;
        }
        Pair<Integer, Integer> right = turnRight(currentPoint, direction);
        if (isScaffold(right)) {
            return right;
        }
        return null;
    }

    private boolean isScaffold(Pair<Integer, Integer> point) {
        return map.getOrDefault(point, '?') == '#';
    }

    private Map<Pair<Integer, Integer>, Character> buildMap(LinkedBlockingDeque<Long> outputs) {
        int x = 0;
        int y = 0;
        Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
        while (!outputs.isEmpty()) {
            char val = (char) outputs.removeFirst().intValue();
            if (val == '\n') {
                y++;
                x = 0;
            } else {
                map.put(Pair.of(x++, y), val);
            }
            System.out.print(val);
        }
        return map;
    }
}
