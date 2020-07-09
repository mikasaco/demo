package com.shenlinqiang.algorithm.sort;

import java.util.Arrays;

/**
 * 快排
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] nums = {1, 4, 5, 2, 3, 2};
        sort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums, int l, int r) {
        if (l > r) {
            return;
        }
        int p = patition(nums, l, r);
        sort(nums, l, p - 1);
        sort(nums, p + 1, r);
    }

    private static int patition(int[] nums, int l, int r) {
        int base = nums[r];
        while (l < r) {
            while (l < r && nums[l] <= base) {
                l++;
            }
            if (l < r) {
                change(nums, l, r);
                r--;
            }
            while (r > l && nums[r] >= base) {
                r--;
            }
            if (l < r) {
                change(nums, l, r);
                l++;
            }
        }
        return l;
    }

    private static void change(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
