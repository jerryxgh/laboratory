package com.jerryxgh;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
 */
public class Problem150 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<Integer>();
        for (String c : tokens) {
            if ("+".equals(c)) {
                Integer op1 = stack.pop();
                Integer op2 = stack.pop();
                int value = Integer.valueOf(op1) + Integer.valueOf(op2);
                stack.push(value);
            } else if ("-".equals(c)) {
                Integer op1 = stack.pop();
                Integer op2 = stack.pop();
                int value = Integer.valueOf(op2) - Integer.valueOf(op1);
                stack.push(value);
            } else if ("*".equals(c)) {
                Integer op1 = stack.pop();
                Integer op2 = stack.pop();
                int value = Integer.valueOf(op1) * Integer.valueOf(op2);
                stack.push(value);
            } else if ("/".equals(c)) {
                Integer op1 = stack.pop();
                Integer op2 = stack.pop();
                int value = Integer.valueOf(op2) / Integer.valueOf(op1);
                stack.push(value);
            } else {
                stack.push(Integer.valueOf(c));
            }
        }

        return Integer.valueOf(stack.pop());
    }

    public static void main(String[] args) {
        Problem150 p = new Problem150();
        String[] input1 = {"1", "2", "-"};
        System.out.println(p.evalRPN(input1));

        String[] input2 = {"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        System.out.println(p.evalRPN(input2));
    }
}
