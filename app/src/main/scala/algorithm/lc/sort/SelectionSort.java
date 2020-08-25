package algorithm.lc.sort;

import java.util.Arrays;

public class SelectionSort {
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }

        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 2};
        selectionSort(arr);

        Arrays.stream(arr).forEach(System.out::println);
    }
}
