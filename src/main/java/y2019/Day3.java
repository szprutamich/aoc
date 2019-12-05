package y2019;

import org.apache.commons.lang3.tuple.Pair;
import utils.Util;

import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Day3 {

    private Map<Pair<Integer, Integer>, Integer> firstMap;

    private Map<Pair<Integer, Integer>, Integer> secondMap;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day3 day3 = new Day3();
        System.out.println(day3.solveA());
        System.out.println(day3.solveB());
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day3() throws Exception {
        List<String> input = Files.lines(Util.loadPath("/day3.txt")).collect(Collectors.toList());
        List<String> first = asList(input.get(0).split(","));
        List<String> second = asList(input.get(1).split(","));
        firstMap = produceMap(first);
        secondMap = produceMap(second);
    }

    private int solveA() {
        int result = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> key : firstMap.keySet()) {
            if (secondMap.containsKey(key)) {
                result = Math.min(result, Math.abs(key.getLeft()) + Math.abs(key.getRight()));
            }
        }
        return result;
    }

    private int solveB() {
        int result = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> key : firstMap.keySet()) {
            if (secondMap.containsKey(key)) {
                result = Math.min(result, Math.abs(firstMap.get(key)) + Math.abs(secondMap.get(key)));
            }
        }
        return result;
    }

    private Map<Pair<Integer, Integer>, Integer> produceMap(List<String> input) {
        Map<Pair<Integer, Integer>, Integer> result = new HashMap<>();
        int distanceSum = 0;
        int x = 0;
        int y = 0;
        for (String s : input) {
            int distance = Integer.parseInt(s.substring(1));
            for (int i = 0; i < distance; i++) {
                distanceSum++;
                switch (s.charAt(0)) {
                    case 'R':
                        x++;
                        break;
                    case 'L':
                        x--;
                        break;
                    case 'U':
                        y++;
                        break;
                    case 'D':
                        y--;
                        break;
                }
                result.put(Pair.of(x, y), distanceSum);
            }
        }
        return result;
    }

}
