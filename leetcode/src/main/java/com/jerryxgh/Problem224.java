package com.jerryxgh;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/basic-calculator/
 */
public class Problem224 {
    static class Stream {
        String s;
        int index;

        public Stream(String s, int index) {
            this.s = s;
            this.index = index;
        }

        public char nextChar() {
            return s.charAt(index++);
        }

        public boolean hasNext() {
            return index < s.length();
        }
    }

    public int calculate(String s) {
        Stream stream = new Stream(s, 0);
        return calculate(stream);
    }

    /**
     * 递归方式解决括号
     * @param stream
     * @return
     */
    private int calculate(Stream stream) {
        int preNum = 0;
        char preOp = '+';
        StringBuffer numberBuf = new StringBuffer();
        while (stream.hasNext()) {
            char c = stream.nextChar();

            switch (c) {
                case '+':
                case '-':
                    if (numberBuf.length() > 0) {
                        int curNum = Integer.valueOf(numberBuf.toString());
                        numberBuf.setLength(0);
                        preNum = compute(preNum, preOp, curNum);
                    }
                    preOp = c;
                    break;
                case '(':
                    int curNum = calculate(stream);
                    preNum = compute(preNum, preOp, curNum);
                    break;
                case ')':
                case ' ':
                    break;
                default:
                    numberBuf.append(c);
            }
        }

        if (numberBuf.length() > 0) {
            int curNum = Integer.valueOf(numberBuf.toString());
            numberBuf.setLength(0);
            preNum = compute(preNum, preOp, curNum);
        }

        return preNum;
    }

    private int compute(int preNum, char preOp, int curNum) {
        switch (preOp) {
            case '+':
                preNum += curNum;
                break;
            case '-':
                preNum -= curNum;
                break;
            default:
                break;
        }
        return preNum;
    }

    /**
     * 双栈法，操作数一个栈，操作符一个栈
     * @param s
     * @return
     */
    public int calculate1(String s) {
        Stack<Integer> numberStack = new Stack<Integer>();
        Stack<Character> operatorStack = new Stack<Character>();

        Integer prevNum = null;
        StringBuffer numberBuf = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '+':
                case '-':
                case '(':
                case ')':
                case ' ':
                    if (numberBuf.length() > 0) {
                        Integer value = Integer.valueOf(numberBuf.toString());
                        prevNum = value;
                        numberBuf.setLength(0);
                        generateValue(value, numberStack, operatorStack);
                    }
                    break;
                default:
                    break;
            }

            if (c == '+' || c == '-') {
                if (prevNum == null) {
                    numberStack.push(0);
                }
            }

            switch (c) {
                case '+':
                case '-':
                case '(':
                    prevNum = null;
                    operatorStack.push(c);
                    break;
                case ')':
                case ' ':
                    break;
                default:
                    numberBuf.append(c);
            }

            switch (c) {
                case ')':
                    Integer value = numberStack.pop();
                    operatorStack.pop();
                    generateValue(value, numberStack, operatorStack);
                    break;
                default:
                    break;
            }
        }

        if (numberBuf.length() > 0) {
            Integer value = Integer.valueOf(numberBuf.toString());
            numberBuf.setLength(0);
            generateValue(value, numberStack, operatorStack);
        }

        return numberStack.pop();
    }

    public void generateValue(int value, Stack<Integer> numberStack, Stack<Character> operatorStack) {
        boolean conti = true;
        while (conti && !operatorStack.empty()) {
            switch (operatorStack.peek()) {
                case '+':
                    operatorStack.pop();
                    Integer op1 = numberStack.pop();
                    value = op1 + value;
                    break;
                case '-':
                    operatorStack.pop();
                    op1 = numberStack.pop();
                    value = op1 - value;
                default:
                    conti = false;
                    break;
            }
        }

        numberStack.push(value);
    }

    public static void main(String[] args) {
        Problem224 problem224 = new Problem224();
        System.out.println(problem224.calculate("1 + 2"));
        System.out.println(problem224.calculate1("1 + 2"));
        System.out.println(problem224.calculate("(1+(4+5+2)-3)+(6+8)"));
        System.out.println(problem224.calculate1("(1+(4+5+2)-3)+(6+8)"));
        System.out.println(problem224.calculate(" 2-1 + 2 "));
        System.out.println(problem224.calculate1(" 2-1 + 2 "));
        System.out.println(problem224.calculate("-2+ 1"));
        System.out.println(problem224.calculate1("-2+ 1"));
        System.out.println(problem224.calculate("- (3 + (4 + 5))"));
        System.out.println(problem224.calculate1("- (3 + (4 + 5))"));
    }
}
