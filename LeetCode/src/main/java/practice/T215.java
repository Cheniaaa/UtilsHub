package practice;

import java.util.Arrays;

/**
 * @Description : 数组中第k个最大元素
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/4 19:21:21
 * @Version : 1.0
 */

public class T215 {
    public int solution1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 使用快速排序
     *
     * @param nums
     * @param k
     * @return
     */
    public int solution2(int[] nums, int k) {
        return quickSort(nums, k, 0, nums.length - 1);
    }

    private int quickSort(int[] nums, int k, int left, int right) {
        int flag = nums[left];
        int i = left, j = right;
        while (i < j) {
            while (i < j && nums[j] <= flag) {
                j--;
            }
            nums[i] = nums[j];
            while (i < j && nums[i] >= flag) {
                i++;
            }
            nums[j] = nums[i];
        }
        nums[i] = flag;
        if (i == k - 1) {
            return nums[i];
        } else if (i < k - 1) {
            return quickSort(nums, k, i + 1, right);
        } else {
            return quickSort(nums, k, left, i - 1);
        }

    }


    public static void main(String[] args) {
        int[] nums = {5, 5, 1, 6, 7, 2, 3, 4};
        T215 t = new T215();
        int res = t.solution2(nums, 4);
        System.out.println(res);

    }
}
