package y2019;

import utils.Permutations;
import utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Day7 {

    private int[] input;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day7 day7 = new Day7();
        System.out.println("A: " + day7.solveA());
        System.out.println("B: " + day7.solveB());
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day7() throws Exception {
        String[] ints = Util.loadString("/day7.txt").split(",");
        input = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            input[i] = Integer.parseInt(ints[i]);
        }
    }

    private int solveA() throws Exception {
        List<List<Integer>> permutations = new ArrayList<>();
        Permutations.of(Arrays.asList(0, 1, 2, 3, 4)).collect(Collectors.toList())
                .forEach(s -> permutations.add(s.collect(Collectors.toList())));
        int max = 0;
        for (List<Integer> per : permutations) {
            BlockingQueue<Integer> inputs = new LinkedBlockingDeque<>();
            BlockingQueue<Integer> outputs = new LinkedBlockingDeque<>();
            int output = 0;
            for (int i = 0; i < 5; i++) {
                Amplifier amplifier = new Amplifier(Arrays.copyOf(input, input.length), inputs, outputs);
                inputs.add(per.get(i));
                inputs.add(output);
                amplifier.solve();
                output = outputs.take();
            }
            max = Math.max(output, max);
        }
        return max;
    }

    private int solveB() throws Exception {
        List<List<Integer>> permutations = new ArrayList<>();
        Permutations.of(Arrays.asList(5, 6, 7, 8, 9)).collect(Collectors.toList())
                .forEach(s -> permutations.add(s.collect(Collectors.toList())));
        int max = 0;
        for (List<Integer> per : permutations) {
            List<BlockingQueue<Integer>> data = new ArrayList<>();
            List<Amplifier> amplifiers = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                data.add(new LinkedBlockingQueue<>());
            }
            for (int i = 0; i < 5; i++) {
                amplifiers.add(new Amplifier(Arrays.copyOf(input, input.length), data.get(i), data.get((i + 1) % 5)));
                data.get(i).add(per.get(i));
            }
            data.get(0).add(0);
            Thread[] threads = new Thread[5];
            for (int i = 0; i < 5; i++) {
                int finalI = i;
                threads[i] = new Thread(() -> {
                    Amplifier amplifier = amplifiers.get(finalI);
                    try {
                        amplifier.solve();
                    } catch (InterruptedException ignored) {
                    }
                });
                threads[i].start();
            }
            for (Thread t : threads) {
                t.join();
            }
            max = Math.max(data.get(0).take(), max);
        }
        return max;
    }

    class Amplifier {

        private int[] input;

        BlockingQueue<Integer> inputs;
        BlockingQueue<Integer> outputs;

        Amplifier(int[] input, BlockingQueue<Integer> inputs, BlockingQueue<Integer> outputs) {
            this.input = input;
            this.inputs = inputs;
            this.outputs = outputs;
        }

        void solve() throws InterruptedException {
            int i = 0;
            while (input[i] != 99) {
                switch (input[i] % 100) {
                    case 1:
                        input[getPosition(i, 3)] = getNumber(i, 1) + getNumber(i, 2);
                        i += 4;
                        break;
                    case 2:
                        input[getPosition(i, 3)] = getNumber(i, 1) * getNumber(i, 2);
                        i += 4;
                        break;
                    case 3:
                        input[getPosition(i, 1)] = inputs.take();
                        i += 2;
                        break;
                    case 4:
                        outputs.add(getNumber(i, 1));
                        i += 2;
                        break;
                    case 5:
                        if (getNumber(i, 1) != 0L) {
                            i = getNumber(i, 2);
                        } else {
                            i += 3;
                        }
                        break;
                    case 6:
                        if (getNumber(i, 1) == 0L) {
                            i = getNumber(i, 2);
                        } else {
                            i += 3;
                        }
                        break;
                    case 7:
                        if (getNumber(i, 1) < getNumber(i, 2)) {
                            input[getPosition(i, 3)] = 1;
                        } else {
                            input[getPosition(i, 3)] = 0;
                        }
                        i += 4;
                        break;
                    case 8:
                        if (getNumber(i, 1) == getNumber(i, 2)) {
                            input[getPosition(i, 3)] = 1;
                        } else {
                            input[getPosition(i, 3)] = 0;
                        }
                        i += 4;
                        break;
                    default:
                        throw new RuntimeException("Wrong input");
                }
            }
        }

        private int getPosition(int i, int arg) {
            int mode = input[i] / (int) Math.pow(10, arg + 1) % 10;
            switch (mode) {
                case 0:
                    return input[i + arg];
                case 1:
                default:
                    return i + arg;
            }
        }

        private int getNumber(int i, int arg) {
            return input[getPosition(i, arg)];
        }
    }
}
