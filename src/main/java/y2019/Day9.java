package y2019;

import utils.Intcode;
import utils.Util;

import java.util.concurrent.LinkedBlockingDeque;

public class Day9 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        LinkedBlockingDeque<Long> outputs = new LinkedBlockingDeque<>();
        LinkedBlockingDeque<Long> inputs = new LinkedBlockingDeque<>();
        inputs.add(1L);
        Day9 day = new Day9();
        Intcode intcode = new Intcode(day.input);
        intcode.run(inputs, outputs);
        System.out.println("A: " + outputs.getLast());
        intcode.reset();
        inputs.add(2L);
        intcode.run(inputs, outputs);
        System.out.println("B: " + outputs.getLast());
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day9() throws Exception {
        String[] longs = Util.loadString("/day9.txt").split(",");
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }
}
