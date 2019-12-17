package utils;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PairHelper {

    public static Map<Pair<Integer, Integer>, Pair<Integer, Integer>> LEFT = new HashMap<>();
    public static Map<Pair<Integer, Integer>, Pair<Integer, Integer>> RIGHT = new HashMap<>();

    static {
        LEFT.put(Pair.of(0, -1), Pair.of(-1, 0)); // up -> left
        LEFT.put(Pair.of(-1, 0), Pair.of(0, 1)); // left -> down
        LEFT.put(Pair.of(0, 1), Pair.of(1, 0)); // down -> right
        LEFT.put(Pair.of(1, 0), Pair.of(0, -1)); // right -> up
    }

    static {
        RIGHT.put(Pair.of(0, -1), Pair.of(1, 0)); // up -> right
        RIGHT.put(Pair.of(1, 0), Pair.of(0, 1)); // right -> down
        RIGHT.put(Pair.of(0, 1), Pair.of(-1, 0)); // down -> left
        RIGHT.put(Pair.of(-1, 0), Pair.of(0, -1)); // left -> up
    }

    public static Pair<Integer, Integer> upPoint(Pair<Integer, Integer> point) {
        return Pair.of(point.getLeft(), point.getRight() - 1);
    }

    public static Pair<Integer, Integer> downPoint(Pair<Integer, Integer> point) {
        return Pair.of(point.getLeft(), point.getRight() + 1);
    }

    public static Pair<Integer, Integer> leftPoint(Pair<Integer, Integer> point) {
        return Pair.of(point.getLeft() - 1, point.getRight());
    }

    public static Pair<Integer, Integer> rightPoint(Pair<Integer, Integer> point) {
        return Pair.of(point.getLeft() + 1, point.getRight());
    }

    public static List<Pair<Integer, Integer>> tangents(Pair<Integer, Integer> point) {
        return Arrays.asList(upPoint(point), downPoint(point), leftPoint(point), rightPoint(point));
    }

    public static Pair<Integer, Integer> turnLeft(Pair<Integer, Integer> point, Pair<Integer, Integer> dir) {
        return sum(point, LEFT.get(dir));
    }

    public static Pair<Integer, Integer> turnRight(Pair<Integer, Integer> point, Pair<Integer, Integer> dir) {
        return sum(point, RIGHT.get(dir));
    }

    public static Pair<Integer, Integer> sum(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return Pair.of(p1.getLeft() + p2.getLeft(), p1.getRight() + p2.getRight());
    }

    public static Pair<Integer, Integer> minus(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return Pair.of(p1.getLeft() - p2.getLeft(), p1.getRight() - p2.getRight());
    }

    public static Pair<Integer, Integer> getDirection(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return minus(p2, p1);
    }

    public static boolean isLeft(Pair<Integer, Integer> dir, Pair<Integer, Integer> newDir) {
        return LEFT.get(dir).equals(newDir);
    }

    public static boolean isRight(Pair<Integer, Integer> dir, Pair<Integer, Integer> newDir) {
        return RIGHT.get(dir).equals(newDir);
    }
}
