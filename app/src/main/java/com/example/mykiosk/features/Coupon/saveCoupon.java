package com.example.mykiosk.features.Coupon;

import android.util.Log;

import java.util.List;

public class saveCoupon {
    UserData userData;
    List<Coupon> Used;
    int stamp;
    String coupon_plus1;
    String finalCoupon;

    public String getFinalStamp() {
        return finalStamp;
    }

    public String getFinalCoupon() {
        return finalCoupon;
    }

    String finalStamp;


    public saveCoupon(List<Coupon> Used, int stamp, UserData userData) {
        this.userData = userData;
        this.Used = Used;
        this.stamp = stamp;

    }


    protected void finalCouponSet() {

        for (int i = 0; i < Used.size(); i++) {
            if (Used.get(i).getValue() == 1000) {
                userData.setCoupon_1000(userData.getCoupon_1000() - Used.get(i).getUsedQty());
            } else if (Used.get(i).getValue() == 2000) {
                userData.setCoupon_2000(userData.getCoupon_2000() - Used.get(i).getUsedQty());
            } else if (Used.get(i).getValue() == 3000) {
                userData.setCoupon_3000(userData.getCoupon_3000() - Used.get(i).getUsedQty());
            } else if (Used.get(i).getValue() == 4000) {
                userData.setCoupon_4000(userData.getCoupon_4000() - Used.get(i).getUsedQty());
            } else if (Used.get(i).getValue() == 5000) {
                userData.setCoupon_5000(userData.getCoupon_5000() - Used.get(i).getUsedQty());
            }
            finalCoupon = "";
            for (Coupon use : Used) {
                finalCoupon += use.getValue() + "원 쿠폰" + use.getUsedQty() + "개 사용 \n";
            }
            Log.v("5000coupon","5000: "+userData.getCoupon_5000());
        }
    }


    public void plusStamp() {
        int coupon_plus = (userData.getStamp()+stamp) / 10;
        coupon_plus1 = coupon_plus + "";
        if (coupon_plus > 0) finalStamp = "스탬프를 모아 1000원 쿠폰" + coupon_plus1 + "개적립 되었습니다.";
        else finalStamp="잔여스탬프: "+(userData.getStamp()+stamp)+"";
        userData.setStamp(stamp);
    }
}


