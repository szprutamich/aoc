package y2019;

import utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {

    private String input;

    private int size = 25 * 6;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day8 day8 = new Day8();
        System.out.println("A: " + day8.solveA());
        System.out.println("B: \n" + day8.solveB());
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day8() throws Exception {
        input = Util.loadString("/day8.txt");
    }

    private int solveA() {
        int result = 0;

        int minZeros = Integer.MAX_VALUE;
        for (int i = 0; i < input.length() / size; i++) {
            Map<Character, Integer> counter = new HashMap<>();
            for (int j = 0; j < size; j++) {
                char c = input.charAt((size * i) + j);
                counter.put(c, counter.getOrDefault(c, 0) + 1);
            }
            if (minZeros > counter.getOrDefault('0', 0)) {
                minZeros = counter.getOrDefault('0', 0);
                result = counter.getOrDefault('1', 0) * counter.getOrDefault('2', 0);
            }
        }

        return result;
    }

    private String solveB() {
        List<String> layers = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length() / size; i++) {
            layers.add(input.substring(i * size, size * (i + 1)));
        }

        for (int i = 0; i < size; i++) {
            boolean added = false;
            for (String layer : layers) {
                if (layer.charAt(i) != '2') {
                    result.append(layer.charAt(i));
                    added = true;
                    break;
                }
            }
            if (!added) {
                result.append('2');
            }
            if ((i + 1) % 25 == 0) {
                result.append("\n");
            }
        }

        return result.toString().replace("0", " ");
    }

}
