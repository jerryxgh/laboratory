package com.jerryxgh;

public class Problem7 {
    public int reverse(int x) {
        int sign = x < 0 ? -1 : 1;
        x = x < 0 ? -x : x;

        long result = 0;
        while (x > 0) {
            long newResult = result * 10 + (x % 10);
            if (newResult > Integer.MAX_VALUE) {
                return 0;
            }
            result = newResult;
            x = x / 10;
        }

        return (int)result * sign;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(new Problem7().reverse(1534236469));
    }
}
