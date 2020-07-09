package com.shenlinqiang.algorithm.huawei;

import java.util.Scanner;

/**
 * 翻转文章片段
 * 输入：I am a developer. \n 1 \n 2
 * 输出：I a am developer.
 */
public class String02 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int s = getNum(sc.nextLine());
        int e = getNum(sc.nextLine());
        if (s == -1 || e == -1) {
            return;
        }
        String[] split = str.split(" ");
        if (s < 0 || e >= split.length) {
            System.out.println("数字范围异常");
            return;
        }
        while (s < e) {
            String t = split[s];
            split[s] = split[e];
            split[e] = t;
            s++;
            e--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            sb.append(split[i]).append(" ");
        }
        sb.append(split[split.length - 1]);
        System.out.println(sb.toString());

    }

    private static int getNum(String s) {
        try {
            Integer i = Integer.valueOf(s);
            return i;
        } catch (NumberFormatException e) {
            System.out.println("转换为数字失败");
        }
        return -1;
    }
}
