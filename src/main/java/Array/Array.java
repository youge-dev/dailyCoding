package Array;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * leetcode 数组 （java）
 *
 * @author yaquan.zhou
 */
public class Array {
    /**
     * 两数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

    /**
     * 剑指 Offer 42. 连续子数组的最大和
     * 动态规划
     * nums[i]表示以nums[i]为结尾的连续数组最大和
     *
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] += Math.max(nums[i - 1], 0);
            res = Math.max(res, nums[i]);
        }
        return res;
    }

    /**
     * 三数之和  (排序+去重)
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去掉重复情况
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }

    /**
     * 46. 全排列
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        for (int num : nums) {
            output.add(num);
        }
        int n = nums.length;
        backTrace(0, n, output, res); //回溯法
        return res;
    }

    /**
     * @param first  填写的第n个位置
     * @param n      总共的长度
     * @param output
     * @param res
     */
    public static void backTrace(int first, int n, List<Integer> output, List<List<Integer>> res) {
        if (first == n) {
            res.add(new ArrayList<>(output));
        }
        for (int i = first; i < n; i++) {
            Collections.swap(output, first, i);
            backTrace(first + 1, n, output, res);
            Collections.swap(output, first, i);
        }
    }

    /**
     * 归并排序
     */
    public static int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    public static void mergeSort(int[] input, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(input, left, mid);
        mergeSort(input, mid + 1, right);
        merge(input, left, mid, right);
    }

    public static void merge(int[] input, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (input[i] < input[j]) {
                tmp[k++] = input[i++];
            } else {
                tmp[k++] = input[j++];
            }
        }
        while (i <= mid) {
            tmp[k++] = input[i++];
        }
        while (j <= right) {
            tmp[k++] = input[j++];
        }
        for (int ii = 0; ii < tmp.length; ii++) {
            input[ii + left] = tmp[ii];
        }
    }

    /**
     * 718. 最长重复子数组
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int findLength(int[] nums1, int[] nums2) {
        int maxRes = 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = nums1.length - 1; i >= 0; i--) {
            for (int j = nums2.length - 1; j >= 0; j--) {
                dp[i][j] = nums1[i] == nums2[j] ? dp[i + 1][j + 1] + 1 : 0;
                maxRes = Math.max(dp[i][j], maxRes);
            }
        }

        return maxRes;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 2, 1};
        int[] b = {3, 2, 1, 4, 7};
        System.out.println(findLength(a, b));
    }
}
