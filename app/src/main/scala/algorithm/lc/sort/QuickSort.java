package algorithm.lc.sort;

import java.util.Arrays;

public class QuickSort {
    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = partition(arr, left, right);
            quickSort(arr, left, mid - 1);
            quickSort(arr, mid + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        int i = left + 1;
        int j = right;

        while (true) {
            while (i <= j && arr[i] <= pivot) { i++; }
            while (i <= j && arr[j] >= pivot) { j--; }

            if (i >= j) {
                break;
            }

            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        arr[left] = arr[j];
        arr[j] = pivot;
        return j;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 2};
        quickSort(arr, 0, arr.length - 1);

        Arrays.stream(arr).forEach(System.out::println);
    }
}
