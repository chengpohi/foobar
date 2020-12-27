package algorithm.lc.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TwoSum {
    public static List<Integer> twoSum(List<Integer> nums, Integer target) {
        HashMap<Integer, Integer> res = new HashMap<>();

        for (int i = 0; i < nums.size() - 1; i++) {
            Integer v = nums.get(i);
            int k = target - v;
            Integer j = res.get(k);

            if (Objects.nonNull(j)) {
                return Arrays.asList(j, i);
            }

            res.put(v, i);
        }

        return Collections.emptyList();
    }

    public static void main(String[] args) {
        twoSum(Arrays.asList(1, 2, 3, 5), 5).stream()
            .forEach(System.out::println);
    }
}
