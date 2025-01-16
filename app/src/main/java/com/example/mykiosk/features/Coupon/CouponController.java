package com.example.mykiosk.features.Coupon;

import android.content.Context;
import android.util.Log;

import com.example.mykiosk.activities.CheckOrderActivity;

public class CouponController {

    private static CouponController instance;

    public CouponController() {

    }

    public static CouponController getInstance() {
        if (instance == null) {
            instance = new CouponController();
        }
        return instance;
    }

    Context context;

    int stamp;
    String totalCount;
    Dialog1 dialog1;

    public saveCoupon getSavecoupon() {
        return savecoupon;
    }

    saveCoupon savecoupon;
    String finalCoupon;

    Dialog2 dialog2;

    public String getFinalStamp() {
        return finalStamp;
    }

    public String getFinalCoupon() {
        return finalCoupon;
    }

    String finalStamp;
    int useCoupon = 0;

    public CouponController(Context context, int stamp, int totalCount) {
        this.context = context;
        this.stamp = stamp;
        this.totalCount = totalCount + "";
    }

    public void showDialog() {
        dialog1 = new Dialog1(context, stamp, totalCount);
        dialog1.setDialogListener(state -> { //seton 메써드호출 / onDialogResult 매써드정의
            if (state.equals("member")) {
                dialog2 = new Dialog2(context, dialog1.getUserdata(), stamp, totalCount);
                dialog2.setDialogListener(state2 -> {
                    useCoupon = dialog2.getTotalCoupon();
                    if (useCoupon != 0) ((CheckOrderActivity) context).setCoupon(useCoupon);
                    if (state2.equals("useCoupon")) {//적립하고, 쿠폰 사용하고
                        savecoupon = new saveCoupon(dialog2.getUsed(), stamp, dialog1.getUserdata());
                    } else savecoupon = new saveCoupon(null, stamp, dialog1.getUserdata());

                });
                dialog2.show();
            }
        });
        dialog1.show();
        Log.v("stamp","stamp: "+stamp);
    }

    public void couponStampset() {
        savecoupon.plusStamp();
        finalStamp = savecoupon.getFinalStamp();
        if(dialog2.getUsed()!=null) {
            savecoupon.finalCouponSet();
            finalCoupon = savecoupon.getFinalCoupon();
        }
    }
}