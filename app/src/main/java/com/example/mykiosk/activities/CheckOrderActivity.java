package com.example.mykiosk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykiosk.R;
import com.example.mykiosk.controllers.PointDialogController;
import com.example.mykiosk.data.CoffeeData;
import com.example.mykiosk.data.UserData;
import com.example.mykiosk.features.Coupon.CouponController;
import com.example.mykiosk.features.check_order.CheckOrderAdapter;
import com.example.mykiosk.utils.GlobalVariable;
import com.example.mykiosk.utils.NumberUtils;

public class CheckOrderActivity extends AppCompatActivity {

    // 리사이클러뷰 사용하기 위한 어댑터
    CheckOrderAdapter adapter;
    // 포인트 적립 다이얼로그 컨트롤러
    PointDialogController pointDialogController;
    // 쿠폰 사용 다이얼로그 컨트롤러
    CouponController couponController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);

        // 뷰 초기화 메서드 호출
        viewInit();
    }

    // 화면에 필요한 뷰 초기화
    void viewInit() {
        // 주문 상세 내역 RecyclerView 설정
        RecyclerView recyclerView = findViewById(R.id.order_detail_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CheckOrderAdapter(this, CoffeeData.coffeeList);
        recyclerView.setAdapter(adapter);

        // 주문 총 개수와 총 금액 표시
        TextView textCount = findViewById(R.id.text_total_coffee_count);
        TextView textPrice = findViewById(R.id.text_total_price);

        int price = CoffeeData.getTotalPrice();
        GlobalVariable.setTotalPrice(price);

        textCount.setText(CoffeeData.getTotalCount() + "개");
        textPrice.setText(NumberUtils.formatNumber(price) + "원");

        // 할인 금액 설정
        setDiscountPrice();
    }

    // 할인된 최종 금액 표시
    void setDiscountPrice() {
        TextView textView = findViewById(R.id.text_final_price);
        textView.setText(NumberUtils.formatNumber(Math.max(getFinalDiscountPrice(), 0)) + "원");
    }

    // 최종 할인 금액 계산
    int getFinalDiscountPrice() {
        int price = GlobalVariable.getTotalPrice();
        int points = GlobalVariable.getRedeemedPoints();
        int coupon = GlobalVariable.getCouponPrice();
        int discountedPrice = price - points - coupon;
        GlobalVariable.setDiscountedPrice(discountedPrice);
        return discountedPrice;
    }

    // 포인트 다이얼로그에서 클릭 이벤트 처리
    public void onClickedInPointsDialog(View view) {
        pointDialogController.onClick(view);

        // 포인트가 사용되었을 경우 할인 금액 업데이트
        if (GlobalVariable.getRedeemedPoints() != 0) setDiscountPrice();
    }

    // 포인트 적립 및 사용 다이얼로그 호출
    public void onClickPointsRedeem(View view) {
        pointDialogController = new PointDialogController(this);
    }

    // 쿠폰 사용 다이얼로그 호출
    public void onClickCouponRedeem(View view) {
        couponController=couponController.getInstance();
        couponController = new CouponController(this,CoffeeData.getTotalCount(),GlobalVariable.getTotalPrice());
        couponController.showDialog() ;    //

    }

    // 쿠폰 적용 후 UI 업데이트
    public void setCoupon(int coupon){
        GlobalVariable.setCouponPrice(coupon);
        TextView textCoupon =findViewById(R.id.text_redeemed_coupon);
        LinearLayout couponLayout=findViewById(R.id.layout_redeemed_coupon);
        if(coupon!=0) {
            couponLayout.setVisibility(View.VISIBLE);
            textCoupon.setText("-"+coupon + "원");
        }
        setDiscountPrice();

    }

    // 카드 결제 버튼 클릭 이벤트 처리
    public void onClickCardPayment(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.payment_card, null);

        // 결제 총액 표시
        TextView text = dialogView.findViewById(R.id.text_payment_total);
        text.setText(NumberUtils.formatNumber(getFinalDiscountPrice()) + "원");

        builder.setView(dialogView)
                .setTitle("")
                .setPositiveButton("OK", (dialog, which) -> {
                    completePayment();
                    showPaymentCompletionDialog();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    // 간편 결제 버튼 클릭 이벤트 처리
    public void onClickEasyPayment(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // 다이얼로그 생성
        LayoutInflater inflater = getLayoutInflater(); // 레이아웃 인플레이터 생성
        View dialogView = inflater.inflate(R.layout.payment_easy, null); // 레이아웃 인플레이트

        // 결제 총액 표시
        TextView text = dialogView.findViewById(R.id.text_payment_total);
        text.setText(NumberUtils.formatNumber(getFinalDiscountPrice()) + "원");

        builder.setView(dialogView) // 다이얼로그 뷰 설정
                .setTitle("") // 다이얼로그 타이틀 설정
                .setPositiveButton("OK", (dialog, which) -> { // OK 버튼 클릭 시
                    completePayment();
                    showPaymentCompletionDialog();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog alertDialog = builder.create(); // 다이얼로그 생성
        alertDialog.show();

    }

    // 결제 완료 후 다이얼로그 표시
    public void showPaymentCompletionDialog() { // 마지막 결제 완료된 화면
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // 다이얼로그 생성
        LayoutInflater inflater = getLayoutInflater(); // 레이아웃 인플레이터 생성
        View dialogView = inflater.inflate(R.layout.payment_final, null); // 레이아웃 인플레이트

        // 쿠폰 사용 정보를 표시
        if(GlobalVariable.getCouponPrice()!=0) {
            TextView couponUse=dialogView.findViewById(R.id.couponText); //쿠폰 추가
            TextView stampPlus=dialogView.findViewById(R.id.StampText);
            LinearLayout couponFinalLayout=dialogView.findViewById(R.id.couponFinalLayout);
            couponFinalLayout.setVisibility(View.VISIBLE);
            String finalCoupon=couponController.getFinalCoupon();
            String finalStamp =couponController.getFinalStamp();
            couponUse.setText(finalCoupon);
            stampPlus.setText(finalStamp);
        }

        // 사용자의 포인트 정보를 업데이트하여 표시
        if (GlobalVariable.isPhoneNumberSet()) {
            Log.v("MYTAG", GlobalVariable.isPhoneNumberSet() + "");
            UserData userData;
            if (GlobalVariable.isUserDataSet()) {
                userData = GlobalVariable.getUserData();
            } else {
                userData = UserData.searchUserByPhone(GlobalVariable.getPhoneNumberAdded010());
            }

            LinearLayout layout = dialogView.findViewById(R.id.layout_earned_points);
            layout.setVisibility(View.VISIBLE);

            TextView textView = dialogView.findViewById(R.id.text_earned_points);
            textView.setText(NumberUtils.formatNumber(userData.getPoints()) + "P");
        }

        // 시작 화면으로 이동 버튼 설정
        Button button = dialogView.findViewById(R.id.button_go_start);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, ServiceChoiceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 이전 액티비티 모두 제거
            startActivity(intent); // 액티비티 시작
        });

        builder.setCancelable(false);
        builder.setView(dialogView); // 다이얼로그 뷰 설정

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // 결제 완료 처리
    public void completePayment() {
        // 사용자 포인트 업데이트
        if (GlobalVariable.isPhoneNumberSet()) {
            int index = UserData.getIndexByPhone(GlobalVariable.getPhoneNumberAdded010());
            int earnedPoints = UserData.userList.get(index).getPoints()
                    + GlobalVariable.getPointsToBeEarned()
                    - GlobalVariable.getRedeemedPoints();
            UserData.userList.get(index).setPoints(earnedPoints);
        }

        // 쿠폰 및 스탬프 데이터 저장
        if (couponController.getSavecoupon()!=null) couponController.couponStampset();//쿠폰추가
    }
}
