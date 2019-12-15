package y2019;

import utils.Intcode;
import utils.Util;

import java.util.concurrent.LinkedBlockingDeque;

public class Day13 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        LinkedBlockingDeque<Long> outputs = new LinkedBlockingDeque<>();
        Day13 day = new Day13();
        Intcode intcode = new Intcode(day.input);
        intcode.run(new LinkedBlockingDeque<>(), outputs);
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
        intcode.reset();
        intcode.getInput()[0] = 2;
        long ballX = 0L;
        long paddleX = 0L;
        long result = 0L;
        boolean finished;
        do {
            finished = intcode.run(inputs, outputs);
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
}
