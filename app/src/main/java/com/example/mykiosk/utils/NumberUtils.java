package com.example.mykiosk.utils;

public class NumberUtils {
    // 숫자를 "#,###" 형식으로 변환하는 메소드
    public static String formatNumber(int number) {
        return String.format("%,d", number);
    }
}
