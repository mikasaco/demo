package com.shenlinqiang.algorithm.sort;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] nums = {1, 4, 5, 2, 3, 2};
        sort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    private static void sort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        sort(nums, l, mid);
        sort(nums, mid + 1, r);
        merge(nums, l, mid, r);
    }

    private static void merge(int[] nums, int l, int mid, int r) {
        int[] temp = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r) {
            if (nums[p1] < nums[p2]) {
                temp[i] = nums[p1];
                p1++;
            } else {
                temp[i] = nums[p2];
                p2++;
            }
            i++;
        }
        while (p1 <= mid) {
            temp[i++] = nums[p1++];
        }
        while (p2 <= r) {
            temp[i++] = nums[p2++];
        }
        for (int j = 0; j < temp.length; j++) {
            nums[l + j] = temp[j];
        }
    }

}
