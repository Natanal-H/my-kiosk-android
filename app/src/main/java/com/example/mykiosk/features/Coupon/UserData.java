package com.example.mykiosk.features.Coupon;

public class UserData {


    private String phone;



    private int coupon_1000;
    private int coupon_2000;
    private int coupon_3000;
    private int coupon_4000;
    private int coupon_5000 ;



    private int coupon_welcome ;

    private int stamp;



    public UserData(String phone, int stamp, int coupon_1000, int coupon_2000, int coupon_3000, int coupon_4000, int coupon_5000, int coupon_welcome){
        this.phone=phone;
        this.stamp=stamp;
        this.coupon_1000=coupon_1000;
        this.coupon_2000=coupon_2000;
        this.coupon_3000=coupon_3000;
        this.coupon_4000=coupon_4000;
        this.coupon_5000=coupon_5000;
        this.coupon_welcome=coupon_welcome;
    }

    public String getPhone() {
        return phone;
    }

    public int getStamp() {
        return stamp;
    }
    public void setStamp(int plusStamp) {
        setCoupon_1000(getCoupon_1000()+plusStamp/10);
        this.stamp=plusStamp%10;

    }
    public int getCoupon_5000() {
        return coupon_5000;
    }
    public int getCoupon_4000() {
        return coupon_4000;
    }

    public int getCoupon_3000() {
        return coupon_3000;
    }

    public int getCoupon_2000() {
        return coupon_2000;
    }

    public int getCoupon_1000() {
        return coupon_1000;
    }
    public int getCoupon_welcome() {
        return coupon_welcome;
    }
    public void setCoupon_1000(int coupon_1000) {
        this.coupon_1000 = coupon_1000;
    }
    public void setCoupon_2000(int coupon_2000) {
        this.coupon_2000 = coupon_2000;
    }

    public void setCoupon_3000(int coupon_3000) {
        this.coupon_3000 = coupon_3000;
    }

    public void setCoupon_4000(int coupon_4000) {
        this.coupon_4000 = coupon_4000;
    }

    public void setCoupon_5000(int coupon_5000) {
        this.coupon_5000 = coupon_5000;
    }
}
