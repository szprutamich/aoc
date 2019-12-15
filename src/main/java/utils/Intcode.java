package utils;

import java.util.concurrent.BlockingQueue;

public class Intcode {

    private long[] input;

    private long[] original;

    public Intcode(long[] input) {
        this.input = input;
        this.original = input.clone();
    }

    public void reset() {
        this.input = original.clone();
    }

    public long[] getInput() {
        return input;
    }

    public boolean run(BlockingQueue<Long> inputs, BlockingQueue<Long> outputs) {
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
