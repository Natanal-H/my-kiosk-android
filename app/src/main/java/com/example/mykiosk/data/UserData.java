package com.example.mykiosk.data;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.mykiosk.R;

import java.util.ArrayList;

public class UserData {
    // 모든 사용자 데이터를 저장하는 리스트
    public static ArrayList<UserData> userList = new ArrayList<>();

    // 사용자 정보 필드
    private String phoneNumber;
    private int points; // 사용자가 보유한 포인트
    private int stamp; // 사용자가 보유한 스탬프 수

    // 다양한 금액의 쿠폰 개수
    private int coupon_1000;
    private int coupon_2000;
    private int coupon_3000;
    private int coupon_4000;
    private int coupon_5000;

    // 사용자 데이터를 초기화하는 메서드
    public static void initializeUserData(Context context) {
        Resources res = context.getResources();

        // 리소스에서 데이터를 가져옴
        String[] phoneNumbers = res.getStringArray(R.array.phone_numbers);
        int[] points = res.getIntArray(R.array.point_values);
        String[] coupons = res.getStringArray(R.array.coupons);

        // 데이터를 UserData 객체로 변환하여 리스트에 추가
        for (int i = 0; i < phoneNumbers.length; i++) {
            UserData user = new UserData(phoneNumbers[i]);

            user.points = points[i];

            // 쿠폰 정보를 설정
            String[] coupon = coupons[i].split(",");

            user.stamp = Integer.parseInt(coupon[0]);

            user.coupon_1000 = Integer.parseInt(coupon[1]);
            user.coupon_2000 = Integer.parseInt(coupon[2]);
            user.coupon_3000 = Integer.parseInt(coupon[3]);
            user.coupon_4000 = Integer.parseInt(coupon[4]);
            user.coupon_5000 = Integer.parseInt(coupon[5]);

            userList.add(user);
        }
    }

    // 전화번호를 이용해 사용자를 검색하는 메서드
    public static UserData searchUserByPhone(String phoneNumber) {
        for (UserData user : userList) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user;
            }
        }

        return null; // 사용자를 찾지 못한 경우
    }

    // 전화번호를 이용해 사용자의 인덱스를 반환하는 메서드
    public static int getIndexByPhone(String phoneNumber) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getPhoneNumber().equals(phoneNumber)) {
                return i;
            }
        }

        return -1; // 사용자를 찾지 못한 경우
    }

    // 생성자: 전화번호만 초기화
    public UserData(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        // UserData 객체의 정보를 문자열로 반환
        return "User Info: {" +
                "\n  Phone Number: " + phoneNumber +
                "\n  Points: " + points +
                "\n  Stamps: " + stamp +
                "\n  Coupon 1000: " + coupon_1000 +
                "\n  Coupon 2000: " + coupon_2000 +
                "\n  Coupon 3000: " + coupon_3000 +
                "\n  Coupon 4000: " + coupon_4000 +
                "\n  Coupon 5000: " + coupon_5000 +
                "\n}";
    }

    // Getter 및 Setter 메서드
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    public int getCoupon_1000() {
        return coupon_1000;
    }

    public void setCoupon_1000(int coupon_1000) {
        this.coupon_1000 = coupon_1000;
    }

    public int getCoupon_2000() {
        return coupon_2000;
    }

    public void setCoupon_2000(int coupon_2000) {
        this.coupon_2000 = coupon_2000;
    }

    public int getCoupon_3000() {
        return coupon_3000;
    }

    public void setCoupon_3000(int coupon_3000) {
        this.coupon_3000 = coupon_3000;
    }

    public int getCoupon_4000() {
        return coupon_4000;
    }

    public void setCoupon_4000(int coupon_4000) {
        this.coupon_4000 = coupon_4000;
    }

    public int getCoupon_5000() {
        return coupon_5000;
    }

    public void setCoupon_5000(int coupon_5000) {
        this.coupon_5000 = coupon_5000;
    }
}
