package com.jerryxgh;

/**
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
 */
public class Problem4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        if (length < 1) {
            return 0D;
        }

        int half = (length - 1) / 2;

        int smallMiddleIndex = findMiddle(nums1, 0, nums1.length, nums2, half, 0);
        int smallMiddleValue = 0;
        if (smallMiddleIndex < 0) {
            smallMiddleIndex = findMiddle(nums2, 0, nums2.length, nums1, half, 0);
            smallMiddleValue = nums2[smallMiddleIndex];
        } else {
            smallMiddleValue = nums1[smallMiddleIndex];
        }

        if ((length & 1) > 0) {
            return Double.valueOf(smallMiddleValue);
        }

        int bigMiddleIndex = findMiddle(nums1, 0, nums1.length, nums2, half, 1);
        int bigMiddleValue = 0;
        if (bigMiddleIndex < 0) {
            bigMiddleIndex = findMiddle(nums2, 0, nums2.length, nums1, half, 0);
            bigMiddleValue = nums2[bigMiddleIndex];
        } else {
            bigMiddleValue = nums1[bigMiddleIndex];
        }

        return (smallMiddleValue + bigMiddleValue) / 2.0;
    }

    private int findMiddle(int[] from, int start, int end, int[] another, int half, int bigOrSmall) {
        if (end - start <= 0) {
            return -1;
        }

        int middleIndex;
        if (bigOrSmall > 0) {
            middleIndex = (start + end) / 2;
        } else {
            middleIndex = (start + end - 1) / 2;
        }

        if (another.length == 0) {
            return middleIndex;
        }

        int middleValue = from[middleIndex - 1];

        int left = half - middleIndex;

        return 0;
    }

//    0 1 2 3 5
//    1 6

    public static void main(String[] args) {
        int[] nums1 =  {1,2,3};
        int[] nums2 =  {4,5,6};
    }
}
