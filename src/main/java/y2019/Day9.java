package y2019;

import utils.Util;

public class Day9 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("A: " + new Day9().solve(1));
        System.out.println("B: " + new Day9().solve(2));
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day9() throws Exception {
        String[] longs = Util.loadString("/day9.txt").split(",");
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }

    private long solve(long inputNr) {
        long output = -1;
        int relative = 0;
        int i = 0;
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
                    input[getPosition(i, 1, relative)] = inputNr;
                    i += 2;
                    break;
                case 4:
                    output = getNumber(i, 1, relative);
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
        return output;
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
