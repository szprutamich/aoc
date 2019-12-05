package y2019;

import utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    private List<Integer> input;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println(new Day2().solveA(12, 2));
        System.out.println(new Day2().solveB());
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day2() throws Exception {
        this.input = Arrays.stream(Util.loadString("/day2.txt").split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private int solveA(int noun, int verb) {
        List<Integer> original = new ArrayList<>(input);
        input.set(1, noun);
        input.set(2, verb);
        for (int i = 0; i < input.size(); i += 4) {
            switch (input.get(i)) {
                case 1:
                    input.set(input.get(i + 3), input.get(input.get(i + 1)) + input.get(input.get(i + 2)));
                    break;
                case 2:
                    input.set(input.get(i + 3), input.get(input.get(i + 1)) * input.get(input.get(i + 2)));
                    break;
                case 99:
                    int result = input.get(0);
                    input = original;
                    return result;
                default:
                    throw new RuntimeException("Wrong input");
            }
        }
        throw new RuntimeException("Wrong input");
    }

    private int solveB() throws Exception {
        int noun;
        int verb;
        for (noun = 0; noun < 100; noun++) {
            for (verb = 0; verb < 100; verb++) {
                if (solveA(noun, verb) == 19690720) {
                    return 100 * noun + verb;
                }
            }
        }
        throw new RuntimeException("Wrong input");
    }
}
