package y2019;

import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import utils.Util;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Day6 {

    private ListValuedMap<String, String> input = new ArrayListValuedHashMap<>();

    private Map<String, String> reverted = new HashMap<>();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Day6 day6 = new Day6();
        System.out.println(day6.solveA("COM", 0, 0));
        System.out.println(day6.solveB("YOU", "SAN"));
        System.out.println(String.format("Time: %sms", System.currentTimeMillis() - start));
    }

    private Day6() throws Exception {
        Files.lines(Util.loadPath("/day6.txt")).forEach(l -> {
            String[] arr = l.split("\\)");
            input.put(arr[0], arr[1]);
            reverted.put(arr[1], arr[0]);
        });
    }

    private int solveA(String root, int result, int deep) {
        deep++;
        for (String node: input.get(root)) {
            result = result + deep;
            result = solveA(node, result, deep);
        }
        return result;
    }

    private int solveB(String node1, String node2) {
        Map<String, Integer> distances = new HashMap<>();
        String k = reverted.get(node1);
        int distance = 0;
        while ((k = reverted.get(k)) != null) {
            distances.put(k, ++distance);
        }
        k = reverted.get(node2);
        distance = 0;
        while ((k = reverted.get(k)) != null) {
            distance++;
            if (distances.containsKey(k)) {
                return distances.get(k) + distance;
            }
        }
        return 0;
    }
}
