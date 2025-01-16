package com.example.mykiosk.data;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.mykiosk.R;

import java.util.ArrayList;

public class CoffeeData {
    // 모든 커피 데이터를 저장하는 리스트
    public static ArrayList<CoffeeData> coffeeList = new ArrayList<>();

    // 선택된 커피 데이터를 저장하는 리스트
    public static ArrayList<CoffeeData> selectedCoffeeList = new ArrayList<>();

    // 커피 정보 필드
    private String coffeeName;
    private int coffeePrice, coffeeQuantity;

    // 커피 옵션 필드
    boolean isAddedIce, isSizeUp, isAddedOneShot, isAddedLowFatMilk;

    // 커피 데이터를 초기화하는 메서드
    public static void initializeCoffeeData(Context context){
        Resources res = context.getResources();

        // 리소스에서 데이터를 가져옴
        String[] coffeeNames = res.getStringArray(R.array.coffee_names);
        int[] coffeePrices = res.getIntArray(R.array.coffee_price);
        int[] coffeeQuantities = res.getIntArray(R.array.coffee_quantity);
        String[] options = res.getStringArray(R.array.coffee_options);

        // 데이터를 CoffeeData 객체로 변환하여 리스트에 추가
        for (int i = 0; i < coffeeNames.length; i++) {
            CoffeeData coffee = new CoffeeData(coffeeNames[i]);
            coffee.coffeePrice = coffeePrices[i];
            coffee.coffeeQuantity = coffeeQuantities[i];

            // 옵션을 설정
            String[] option = options[i].split(",");

            coffee.isAddedIce = option[0].equals("1");
            coffee.isSizeUp = option[1].equals("1");
            coffee.isAddedOneShot = option[2].equals("1");
            coffee.isAddedLowFatMilk = option[3].equals("1");

            coffeeList.add(coffee);
        }
    }

    // 기본 생성자
    public CoffeeData(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    // 모든 필드를 초기화하는 생성자
    public CoffeeData(String coffeeName, int coffeePrice, int coffeeQuantity) {
        this.coffeeName = coffeeName;
        this.coffeePrice = coffeePrice;
        this.coffeeQuantity = coffeeQuantity;
    }

    @NonNull
    @Override
    public String toString() {
        // CoffeeData 객체의 정보를 문자열로 반환
        return "Coffee Info: {" +
                "\n  Coffee Name: " + coffeeName +
                "\n  Coffee Price: " + coffeePrice +
                "\n  Coffee Quantity: " + coffeeQuantity +
                "\n}";
    }

    // Getter 및 Setter 메서드
    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public int getCoffeePrice() {
        return coffeePrice;
    }

    public void setCoffeePrice(int coffeePrice) {
        this.coffeePrice = coffeePrice;
    }

    public int getCoffeeQuantity() {
        return coffeeQuantity;
    }

    public void setCoffeeQuantity(int coffeeQuantity) {
        this.coffeeQuantity = coffeeQuantity;
    }

    // 총 커피 수량을 반환하는 메서드
    public static int getTotalCount() {
        int total = 0;
        for (CoffeeData coffee : coffeeList)
            total += coffee.coffeeQuantity;
        return total;
    }

    // 총 커피 가격을 반환하는 메서드
    public static int getTotalPrice() {
        int total = 0;
        for (CoffeeData coffee : coffeeList) {
            total += coffee.coffeePrice;
            //total += coffee.coffeePrice * coffee.coffeeQuantity;
        }
        return total;
    }

    // 추가적인 Getter 및 Setter 메서드
    public String getCoffeeN() {
        return coffeeName;
    }

    public int getCoffeeQ() {
        return coffeeQuantity;
    }

    public int getCoffeeP() {
        return coffeePrice;
    }

    public void setCoffeeQ(int coffeeQ) {
        this.coffeeQuantity = coffeeQ;
    }

    public void setCoffeeP(int coffeeP) {
        this.coffeePrice = coffeeP;
    }
}
