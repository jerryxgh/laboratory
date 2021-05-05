package com.jerryxgh;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
public class Problem3 {
    public static void main(String[] args) {
        System.out.println(new Problem3().lengthOfLongestSubstring("pwwkew"));
    }

    public int lengthOfLongestSubstring(String s) {
        int maxIndex = 1;
        int maxLength = 0;
        int prevIndex = -1;

        int len = s.length();

        for (int i = 0; i < len;) {
            for (; maxIndex < len; maxIndex++) {
                prevIndex = indexOf(s, i, maxIndex);
                if (prevIndex >= 0) {
                    break;
                }
            }

            int length = maxIndex - i;
            if (length >= maxLength) {
                maxLength = length;
            }

            if (maxIndex >= len) {
                return maxLength;
            }

            i = prevIndex + 1;
            maxIndex++;
        }

        return maxLength;
    }

    private int indexOf(String s, int i, int j) {
        for (; i < j; ++i) {
            if (s.charAt(i) == s.charAt(j)) {
                return i;
            }
        }

        return -1;
    }
}
