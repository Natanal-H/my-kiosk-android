package com.example.mykiosk.utils;

import com.example.mykiosk.data.UserData;

public class GlobalVariable {
    // 핸드폰 번호
    private static String phoneNumber;
    // 유저 정보
    private static UserData userData;
    // 총 가격, 할인된 가격
    private static int totalPrice, discountedPrice;
    // 적립 예정 포인트, 적립 포인트, 사용할 포인트
    private static int pointsToBeEarned, earnedPoints, redeemedPoints;

    public static int getCouponPrice() {
        return couponPrice;
    }

    public static void setCouponPrice(int couponPrice) {
        GlobalVariable.couponPrice = couponPrice;
    }

    private static int couponPrice;

    /*
        데이터 초기화
     */
    public static void init() {
        phoneNumber = "";
        userData = null;
        totalPrice = 0; discountedPrice = 0;
        pointsToBeEarned = 0; earnedPoints = 0; redeemedPoints = 0;
        couponPrice = 0;
    }

    /*
        핸드폰 번호
     */
    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static String getPhoneNumberAdded010() {
        return "010" + phoneNumber;
    }

    public static void setPhoneNumber(String number) {
        phoneNumber = number;
    }

    public static boolean isPhoneNumberSet() {
        return phoneNumber != null && !phoneNumber.isEmpty();
    }

    /*
        유저 정보
     */
    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData data) {
        userData = data;
    }

    public static boolean isUserDataSet() {
        return userData != null;
    }

    /*
        총 가격
     */
    public static int getTotalPrice() {
        return totalPrice;
    }

    public static void setTotalPrice(int price) {
        totalPrice = price;
    }

    /*
        할인된 가격
     */
    public static int getDiscountedPrice() {
        return discountedPrice;
    }

    public static void setDiscountedPrice(int price) {
        discountedPrice = price;
    }

    /*
        적립 예정 포인트
     */
    public static int getPointsToBeEarned() {
        return pointsToBeEarned;
    }

    public static void setPointsToBeEarned(int points) {
        pointsToBeEarned = points;
    }

    /*
        적립 포인트
     */
    public static int getEarnedPoints() {
        return earnedPoints;
    }

    public static void setEarnedPoints(int points) {
        earnedPoints = points;
    }

    /*
        사용할 포인트
     */
    public static int getRedeemedPoints() { return redeemedPoints; }

    public static void setRedeemedPoints(int points) { redeemedPoints = points; }
}
