package y2019;

import utils.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

    private List<Integer> input;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        new Day4().solve();
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day4() throws Exception {
        this.input = Arrays.stream(Util.loadString("/day4.txt").split("-"))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private void solve() {
        int counterA = 0;
        int counterB = 0;
        for (int i = input.get(0); i <= input.get(1); i++) {
            String s = String.valueOf(i);
            if (repeated(s) && increasing(s)) {
                if (anyCharRepeatedExactlyTwice(s)) {
                    counterB++;
                }
                counterA++;
            }
        }
        System.out.println("RESULT_A: " + counterA);
        System.out.println("RESULT_B: " + counterB);
    }

    private boolean repeated(String string) {
        return string.length() != string.chars().distinct().count();
    }

    private boolean increasing(String string) {
        for (int i = 1; i < string.length(); i++) {
            if (string.charAt(i) < string.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean anyCharRepeatedExactlyTwice(String string) {
        for (char i = 0; i <= 9; i++) {
            int finalI = i;
            if (string.chars().filter(ch -> Character.getNumericValue(ch) == finalI).count() == 2) {
                return true;
            }
        }
        return false;
    }
}
