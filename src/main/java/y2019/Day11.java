package y2019;

import org.apache.commons.lang3.tuple.Pair;
import utils.Util;

import java.util.HashMap;
import java.util.Map;

public class Day11 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    private static final int BLACK = 0;

    private static final int WHITE = 1;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day11 day = new Day11();
        day.solve();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day11() throws Exception {
        String[] longs = Util.loadString("/day11.txt").split(",");
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }

    private void solve() {
        Program program = new Program();
        Map<Pair<Integer, Integer>, Integer> result = program.solve(BLACK);
        System.out.println("A: " + result.size());
        program = new Program();
        result = program.solve(WHITE);

        System.out.println("B: \n");
        for (int y = program.minY; y <= program.maxY; y++) {
            for (int x = program.minX; x <= program.maxX; x++) {
                System.out.print(result.getOrDefault(Pair.of(x, y), 0) == 1 ? "#" : " ");
            }
            System.out.println();
        }
    }

    class Program {

        long lastOut = 0;

        Map<Pair<Integer, Integer>, Integer> pointsMap = new HashMap<>();
        Pair<Integer, Integer> currentPoint = Pair.of(0, 0);
        int mode = 0;
        Pair<Integer, Integer> direction = Pair.of(0, -1);
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;

        Map<Pair<Integer, Integer>, Integer> solve(int firstColor) {
            int i = 0;
            int relative = 0;
            pointsMap.put(currentPoint, firstColor);
            while (input[i] != 99) {
                int instruction = (int) input[i];

                switch (instruction % 100) {
                    case 1:
                        input[getPosition(i, 3, relative)] = getNumber(i, 1, relative) + getNumber(i, 2, relative);
                        i += 4;
                        break;
                    case 2:
                        input[getPosition(i, 3, relative)] = getNumber(i, 1, relative) * getNumber(i, 2, relative);
                        i += 4;
                        break;
                    case 3:
                        int inputNr = pointsMap.getOrDefault(currentPoint, 0);
                        input[getPosition(i, 1, relative)] = inputNr;
                        i += 2;
                        break;
                    case 4:
                        long output = getNumber(i, 1, relative);
                        mode = (++mode) % 2;
                        if (mode == 0) {
                            long color = lastOut;
                            // paint
                            pointsMap.put(currentPoint, (int) color);
                            // change direction
                            direction = output == 1 ? RIGHT.get(direction) : LEFT.get(direction);
                            // move
                            currentPoint = Pair.of(
                                    currentPoint.getLeft() + direction.getLeft(),
                                    currentPoint.getRight() + direction.getRight()
                            );
                            minX = Math.min(currentPoint.getLeft(), minX);
                            maxX = Math.max(currentPoint.getLeft(), maxX);
                            minY = Math.min(currentPoint.getLeft(), minY);
                            maxY = Math.max(currentPoint.getLeft(), maxY);
                        }
                        lastOut = output;
                        i += 2;
                        break;
                    case 5:
                        if (getNumber(i, 1, relative) != 0L) {
                            i = (int) getNumber(i, 2, relative);
                        } else {
                            i += 3;
                        }
                        break;
                    case 6:
                        if (getNumber(i, 1, relative) == 0L) {
                            i = (int) getNumber(i, 2, relative);
                        } else {
                            i += 3;
                        }
                        break;
                    case 7:
                        if (getNumber(i, 1, relative) < getNumber(i, 2, relative)) {
                            input[getPosition(i, 3, relative)] = 1L;
                        } else {
                            input[getPosition(i, 3, relative)] = 0L;
                        }
                        i += 4;
                        break;
                    case 8:
                        if (getNumber(i, 1, relative) == getNumber(i, 2, relative)) {
                            input[getPosition(i, 3, relative)] = 1L;
                        } else {
                            input[getPosition(i, 3, relative)] = 0L;
                        }
                        i += 4;
                        break;
                    case 9:
                        relative += getNumber(i, 1, relative);
                        i += 2;
                        break;
                    default:
                        throw new RuntimeException("Wrong input");
                }
            }
            return pointsMap;
        }

        private int getPosition(int i, int arg, long relativeBase) {
            int mode = (int) (input[i] / (int) Math.pow(10, arg + 1) % 10);
            switch (mode) {
                case 0:
                    return (int) input[i + arg];
                case 1:
                    return i + arg;
                case 2:
                default:
                    return (int) (relativeBase + input[i + arg]);
            }
        }

        private long getNumber(int i, int arg, long relativeBase) {
            return input[getPosition(i, arg, relativeBase)];
        }
    }

    private static Map<Pair<Integer, Integer>, Pair<Integer, Integer>> LEFT = new HashMap<>();
    private static Map<Pair<Integer, Integer>, Pair<Integer, Integer>> RIGHT = new HashMap<>();

    static {
        LEFT.put(Pair.of(0, -1), Pair.of(-1, 0)); // up -> left
        LEFT.put(Pair.of(-1, 0), Pair.of(0, 1)); // left -> down
        LEFT.put(Pair.of(0, 1), Pair.of(1, 0)); // down -> right
        LEFT.put(Pair.of(1, 0), Pair.of(0, -1)); // right -> up
    }

    static {
        RIGHT.put(Pair.of(0, -1), Pair.of(1, 0)); // up -> right
        RIGHT.put(Pair.of(1, 0), Pair.of(0, 1)); // right -> down
        RIGHT.put(Pair.of(0, 1), Pair.of(-1, 0)); // down -> left
        RIGHT.put(Pair.of(-1, 0), Pair.of(0, -1)); // left -> up
    }
}
