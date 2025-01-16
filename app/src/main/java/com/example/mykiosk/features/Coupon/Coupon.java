
package com.example.mykiosk.features.Coupon;
public class Coupon {
    private String name;
    private String description;
    private String Period;

    private String available;

    private int UsedQty;

    private int value;

    private int qty;

    public Coupon(String name, String description, String Period, String available, int qty, int value) {
        this.name = name;
        this.description = description;
        this.Period=Period ;
        this.available=available;
        this.qty=qty;
        this.value=value;
    }
    public Coupon(int UsedQty, int value) {
        this.UsedQty=UsedQty;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getPeriod() {
        return Period;
    }

    public String getAvailable() {
        return available;
    }
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public int getValue() {
        return value;
    }
    public int getUsedQty() {
        return UsedQty;
    }
}
