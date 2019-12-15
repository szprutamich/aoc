package y2019;

import utils.Util;

public class Day15 {

    private static int SIZE = Integer.MAX_VALUE / 1024;

    private long[] input = new long[SIZE];

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day15() throws Exception {
        String[] longs = Util.loadString("/day15.txt").split(",");
        for (int i = 0; i < longs.length; i++) {
            input[i] = Long.parseLong(longs[i]);
        }
    }
}
