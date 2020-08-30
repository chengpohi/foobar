package algorithm.lc.sort;

import java.util.Arrays;

/**
 * 排序不改变其元素的先后顺序
 *
 * 稳定排序编辑
 * 如：插入排序，基数排序，归并排序，冒泡排序，计数排序。
 * 不稳定排序编辑
 * 不稳定的排序算法有：快速排序，希尔排序，简单选择排序，堆排序。 [1]
 */
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
            while (i <=j && arr[i] <= pivot) i++;
            while (i <=j && arr[j] >= pivot) j--;

            if (i >= j) {
                break;
            }

            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
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
