package y2019;

import utils.Intcode;
import utils.Util;

import java.util.concurrent.LinkedBlockingDeque;

public class Day19 {

    private LinkedBlockingDeque<Long> inputs = new LinkedBlockingDeque<>();
    private LinkedBlockingDeque<Long> outputs = new LinkedBlockingDeque<>();

    private long[] input;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day19 day = new Day19();
        day.solve();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day19() throws Exception {
        String[] longs = Util.loadString("/day19.txt").split(",");
        input = new long[1024];
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }

    private void solve() {
        Intcode intcode = new Intcode(input);
        System.out.println("A: " + solve(intcode, 50, 50));
        System.out.println("B: " + solve(intcode, 5000, 5000));
    }

    private long solve(Intcode intcode, long maxX, long maxY) {
        long result = 0;
        long minX = 0L;
        for (long y = 0; y < maxY; y++) {
            long firstX = 0L;
            for (long x = minX; x < maxX; x++) {
                boolean beam = isBeam(x, y, intcode);
                if (beam) {
                    result++;
                    if (firstX == 0L) {
                        firstX = x;
                        minX = x;
                    }
                } else if (firstX != 0L || y == 0L) {
                    if (y > 100L && santaFit(firstX, y, intcode)) {
                        return firstX * 10000 + y - 99;
                    }
                    break;
                }
            }
        }
        return result;
    }

    private boolean santaFit(long x, long y, Intcode intcode) {
        return isBeam(x, y, intcode) && isBeam(x + 99, y, intcode)
                && isBeam(x + 99, y - 99, intcode) && isBeam(x, y - 99, intcode);
    }

    private boolean isBeam(long x, long y, Intcode intcode) {
        inputs.add(x);
        inputs.add(y);
        intcode.run(inputs, outputs);
        intcode.reset();
        return outputs.remove() == 1L;
    }
}
