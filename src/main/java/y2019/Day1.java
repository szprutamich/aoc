package y2019;

import utils.Util;

import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

    private List<Integer> input;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        new Day1().solveA();
        new Day1().solveB();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day1() throws Exception {
        this.input = Files.lines(Util.loadPath("/day1.txt")).map(Integer::parseInt).collect(Collectors.toList());
    }

    private void solveA() {
        int result = input.stream().mapToInt(this::getFuelA).reduce(0, Integer::sum);
        System.out.println(result);
    }

    private void solveB() {
        int result = input.stream().mapToInt(this::getFuelB).reduce(0, Integer::sum);
        System.out.println(result);
    }

    private int getFuelB(int i) {
        int result = getFuelA(i);
        return result > 0 ? result + getFuelB(result) : result < 0 ? 0 : result;
    }

    private int getFuelA(int i) {
        return i / 3 - 2;
    }

}
