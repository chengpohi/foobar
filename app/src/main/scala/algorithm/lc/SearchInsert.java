package algorithm.lc;

public class SearchInsert {
    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] < target) {
                l = mid + 1;
            }

            if (nums[mid] > target) {
                r = mid - 1;
            }
        }

        return l;
    }
}
