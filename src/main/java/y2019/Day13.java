package y2019;

import utils.Util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Day13 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        LinkedBlockingDeque<Long> outputs = new LinkedBlockingDeque<>();
        new Day13().solve(new LinkedBlockingDeque<>(), outputs);
        int counter = 0;
        while (!outputs.isEmpty()) {
            outputs.take();
            outputs.take();
            if (outputs.take() == 2L) {
                counter++;
            }
        }
        System.out.println("A: " + counter);

        outputs = new LinkedBlockingDeque<>();
        LinkedBlockingDeque<Long> inputs = new LinkedBlockingDeque<>();
        Day13 day = new Day13();
        day.input[0] = 2;
        long ballX = 0L;
        long paddleX = 0L;
        long result = 0L;
        boolean finished;
        do {
            finished = day.solve(inputs, outputs);
            while (!outputs.isEmpty()) {
                Long x = outputs.take();
                Long y = outputs.take();
                Long tile = outputs.take();
                if (tile == 3L) {
                    paddleX = x;
                }
                if (tile == 4L) {
                    ballX = x;
                }
                if (x == -1L && y == 0L) {
                    result = tile;
                }
            }
            inputs.add((long) Long.compare(ballX, paddleX));
        } while (!finished);
        System.out.println("B: " + result);
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day13() throws Exception {
        String[] longs = Util.loadString("/day13.txt").split(",");
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }

    private boolean solve(BlockingQueue<Long> inputs, BlockingQueue<Long> outputs) {
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
                    if (inputs.isEmpty()) {
                        return false;
                    }
                    input[getPosition(i, 1, relative)] = inputs.remove();
                    i += 2;
                    break;
                case 4:
                    outputs.add(getNumber(i, 1, relative));
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
        return true;
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
