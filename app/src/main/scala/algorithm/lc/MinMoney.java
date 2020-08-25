package algorithm.lc;

import java.util.Arrays;

//https://www.nowcoder.com/questionTerminal/95329d9a55b94e3fb2da475d3d052164?toCommentId=3377639
public class MinMoney {

    public static void minMoney(int n, int k, int[] arr) {
        Arrays.sort(arr);
        //        int res=0;
        int[] res = new int[21];
        for (int i = 0; i <= k; i++) {
            res[i] = 10000;
        }
        for (int i = 0; i < n; i++) {
            for (int j = k; j >= 0; j--) {
                if (j > arr[i]) {
                    res[j] = Math.min(res[j], res[j - arr[i]] + arr[i]);
                } else {
                    res[j] = Math.min(res[j], arr[i]);

                }
            }
        }
        System.out.println(res[k]);
    }

    public static void main(String[] args) {
        int[] arr = {18, 19, 17, 6, 7};
        int n = 5, k = 20;
        minMoney(n, k, arr);
    }
}
