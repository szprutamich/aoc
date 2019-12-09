package y2019;

import utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    private List<Integer> input;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("A: " + new Day5().solve(1));
        System.out.println("B: " + new Day5().solve(5));
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day5() throws Exception {
        this.input = Arrays.stream(Util.loadString("/day5.txt").split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private int solve(int inputNr) {
        List<Integer> original = new ArrayList<>(input);
        int output = -1;
        for (int i = 0; i < input.size(); ) {
            int instruction = input.get(i);

            switch (instruction % 100) {
                case 1:
                    input.set(input.get(i + 3),
                            (instruction / 100 % 10 == 0 ? input.get(input.get(i + 1)) : input.get(i + 1))
                                    + (instruction / 1000 % 10 == 0 ? input.get(input.get(i + 2)) : input.get(i + 2)));
                    i += 4;
                    break;
                case 2:
                    input.set(input.get(i + 3),
                            (instruction / 100 % 10 == 0 ? input.get(input.get(i + 1)) : input.get(i + 1))
                                    * (instruction / 1000 % 10 == 0 ? input.get(input.get(i + 2)) : input.get(i + 2)));
                    i += 4;
                    break;
                case 3:
                    input.set(input.get(i + 1), inputNr);
                    i += 2;
                    break;
                case 4:
                    output = input.get(input.get(i + 1));
                    i += 2;
                    break;
                case 5:
                    if (((instruction / 100) % 10 == 0 ? input.get(input.get(i + 1)) : input.get(i + 1)) != 0) {
                        i = ((instruction / 1000) % 10 == 0 ? input.get(input.get(i + 2)) : input.get(i + 2));
                    } else {
                        i += 3;
                    }
                    break;
                case 6:
                    if (((instruction / 100) % 10 == 0 ? input.get(input.get(i + 1)) : input.get(i + 1)) == 0) {
                        i = ((instruction / 1000) % 10 == 0 ? input.get(input.get(i + 2)) : input.get(i + 2));
                    } else {
                        i += 3;
                    }
                    break;
                case 7:
                    if (((instruction / 100) % 10 == 0 ? input.get(input.get(i + 1)) : input.get(i + 1)) <
                            ((instruction / 1000) % 10 == 0 ? input.get(input.get(i + 2)) : input.get(i + 2))) {
                        input.set(input.get(i + 3), 1);
                    } else {
                        input.set(input.get(i + 3), 0);
                    }
                    i += 4;
                    break;
                case 8:
                    if (((instruction / 100) % 10 == 0 ? input.get(input.get(i + 1)) : input.get(i + 1))
                            .equals((instruction / 1000) % 10 == 0 ? input.get(input.get(i + 2)) : input.get(i + 2))) {
                        input.set(input.get(i + 3), 1);
                    } else {
                        input.set(input.get(i + 3), 0);
                    }
                    i += 4;
                    break;
                case 99:
                    input = original;
                    return output;
                default:
                    throw new RuntimeException("Wrong input");
            }
        }
        return output;
    }
}
