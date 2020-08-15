package algorithm.lc;

import java.util.Arrays;

public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    int tmp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }



    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 2};
        bubbleSort(arr);

        Arrays.stream(arr).forEach(System.out::println);
    }
}
