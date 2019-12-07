package y2019;

import utils.Permutations;
import utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    private List<Integer> input;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day7 day7 = new Day7();
        System.out.println("A: " + day7.solveA());
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day7() throws Exception {
        this.input = Arrays.stream(Util.loadString("/day7.txt").split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private int solveA() {
        List<List<Integer>> permutations = new ArrayList<>();
        Permutations.of(Arrays.asList(0, 1, 2, 3, 4)).collect(Collectors.toList())
                .forEach(s -> permutations.add(s.collect(Collectors.toList())));
        int max = 0;
        for (List<Integer> per : permutations) {
            int output = 0;
            for (int i = 0; i < 5; i++) {
                List<Integer> copy = new ArrayList<>(input);
                Day5 day5 = new Day5(copy);
                day5.solve(per.get(i));
                output = day5.solve(output);
            }
            max = Math.max(output, max);
        }
        return max;
    }
}
