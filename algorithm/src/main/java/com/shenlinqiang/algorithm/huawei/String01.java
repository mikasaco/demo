package com.shenlinqiang.algorithm.huawei;

import java.util.Scanner;
import java.util.Stack;

/**
 * 算出括号的深度
 * 输入：([]{()})
 * 输出：3
 */
public class String01 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if ("".equals(str)) {
            System.out.println(0);
        }
        Stack<Character> stack = new Stack<>();
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (left(c)) {
                stack.add(c);
                if (stack.size() > max) {
                    max = stack.size();
                }
            } else {
                if (!match(c, stack.peek())) {
                    System.out.println(0);
                    return;
                } else {
                    stack.pop();
                }
            }
        }
        System.out.println(max);
    }

    private static boolean left(char c) {
        if (c == '{' || c == '(' || c == '[') {
            return true;
        }
        return false;
    }

    private static boolean match(char c1, char c2) {
        if (c2 == '(' && c1 == ')') {
            return true;
        } else if (c2 == '{' && c1 == '}') {
            return true;
        } else if (c2 == '[' && c1 == ']') {
            return true;
        }
        return false;
    }
}
