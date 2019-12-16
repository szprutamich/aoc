package y2019;

import org.apache.commons.lang3.StringUtils;
import utils.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day16 {

    private String digits;

    private static int[] BASE_PATTERN = {0, 1, 0, -1};

    private Map<Integer, List<Integer>> patternCache = new HashMap<>();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day16 day = new Day16();
        System.out.println("A: " + day.solveA());
        day.reset(10000);
        System.out.println("B: " + day.solveB());
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day16() throws Exception {
        reset(1);
    }

    private void reset(int repeat) throws Exception {
        digits = Util.loadString("/day16.txt");
        digits = StringUtils.repeat(digits, repeat);
        patternCache = new HashMap<>();
    }

    private String solveA() {
        int[] array = toArray(digits);
        for (int f = 0; f < 100; f++) {
            buildNewInput(array);
        }
        return result(array);
    }

    private String solveB() {
        int offset = Integer.parseInt(digits.substring(0, 7));
        int[] array = Arrays.stream(toArray(digits)).skip(offset).toArray();
        if (offset < array.length / 2) {
            throw new RuntimeException("The solution is invalid for the input");
        }
        for (int f = 0; f < 100; f++) {
            int[] signal = Arrays.copyOf(array, array.length);
            for (int i = signal.length - 2; i >= 0; i--) {
                signal[i] = (array[i] + signal[i + 1]) % 10;
            }
            array = signal;
        }
        return result(array);
    }

    private String result(int[] array) {
        return Arrays.stream(array).boxed().limit(8).map(Object::toString).collect(Collectors.joining());
    }

    private void buildNewInput(int[] array) {
        for (int row = 0; row < array.length; row++) {
            int sum = 0;
            List<Integer> pattern = calculatePattern(row);
            for (int elem = 0; elem < array.length; elem++) {
                sum += array[elem] * pattern.get(elem);
            }
            array[row] = (Math.abs(sum) % 10);
        }
    }

    private int[] toArray(String digits) {
        int[] input = new int[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            input[i] = Character.getNumericValue(digits.charAt(i));
        }
        return input;
    }

    private List<Integer> calculatePattern(int repeater) {
        if (!patternCache.containsKey(repeater)) {
            List<Integer> pattern = new ArrayList<>();
            int index = 0;
            while (pattern.size() < digits.length() + 1) {
                for (int i = 0; i < repeater + 1; i++) {
                    pattern.add(getBasePatternValue(index));
                }
                index++;
            }
            patternCache.put(repeater, pattern.subList(1, digits.length() + 1));
        }
        return patternCache.get(repeater);
    }

    private int getBasePatternValue(int index) {
        return BASE_PATTERN[index % 4];
    }
}
