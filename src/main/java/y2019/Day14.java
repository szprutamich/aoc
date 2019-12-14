package y2019;

import org.apache.commons.lang3.tuple.Pair;
import utils.Util;

import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {

    private Map<String, Pair<Long, List<Pair<String, Long>>>> input = new HashMap<>();

    private long counter;

    private Pattern NAME_VALUE = Pattern.compile("(\\d+)\\s+([A-Z]+)");

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day14 day = new Day14("/day14.txt");
        day.solve(new HashMap<>(), "FUEL", 1);
        System.out.println("A: " + day.counter);
        long b = 0;
        long step = 10000000L;
        long max = 1000000000000L;
        while (step != 1L) {
            if (day.counter > max) {
                b -= step;
                step /= 2;
            }
            day.reset();
            b += step;
            day.solve(new HashMap<>(), "FUEL", b);
        }
        System.out.println("B: " + b);
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day14(String path) throws Exception {
        Files.lines(Util.loadPath(path)).forEach(l -> {
            String[] split = l.split(" => ");
            String[] ingredients = split[0].split(",");
            List<Pair<String, Long>> required = new ArrayList<>();
            for (String i : ingredients) {
                Matcher matcher = NAME_VALUE.matcher(i);
                if (matcher.find()) {
                    required.add(Pair.of(matcher.group(2), Long.parseLong(matcher.group(1))));
                }
            }
            Matcher matcher = NAME_VALUE.matcher(split[1]);
            if (matcher.find()) {
                input.put(matcher.group(2), Pair.of(Long.parseLong(matcher.group(1)), required));
            }
        });
    }

    private void reset() {
        counter = 0;
    }

    private void solve(Map<String, Long> leftovers, String product, long requiredAmount) {
        Pair<Long, List<Pair<String, Long>>> recipe = input.get(product);
        if (product.equals("ORE")) {
            counter += requiredAmount;
            return;
        }
        Long recipeAmount = recipe.getLeft();
        Long existing = leftovers.getOrDefault(product, 0L);
        Long multiplier = (long) Math.max(0, Math.ceil((double)(requiredAmount - existing) / recipeAmount));
        Long producedAmount = multiplier * recipeAmount;
        if (producedAmount > requiredAmount) { // store leftovers
            leftovers.put(product, existing + producedAmount - requiredAmount);
        } else {
            leftovers.put(product, existing - (requiredAmount - producedAmount));
        }
        for (Pair<String, Long> required : recipe.getRight()) {
            String ingredient = required.getKey();
            Long requiredIngAmount = multiplier * required.getValue();
            solve(leftovers, ingredient, requiredIngAmount);
        }
    }
}
