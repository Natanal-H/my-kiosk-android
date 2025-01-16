package com.example.mykiosk.controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mykiosk.R;
import com.example.mykiosk.data.UserData;
import com.example.mykiosk.features.points.CheckPointsFragment;
import com.example.mykiosk.features.points.EarnPointsFragment;
import com.example.mykiosk.features.points.PointsDialogCompleteListener;
import com.example.mykiosk.features.points.RedeemPointsFragment;
import com.example.mykiosk.utils.GlobalVariable;
import com.example.mykiosk.utils.NumberUtils;

public class PointDialogController implements PointsDialogCompleteListener {

    // 포인트 다이얼로그의 상태값을 정의
    public final int EARN_POINTS = 1; // 포인트 적립 다이얼로그
    public final int CHECK_POINTS = 2; // 포인트 확인 다이얼로그
    public final int REDEEM_POINTS = 3; // 포인트 사용 다이얼로그

    // 포인트 적립 비율 %
    public final float EARN_RATE = 5.0f;

    // 다이얼로그 상태를 저장할 변수
    private static int states;

    // 다이얼로그 객체들
    private EarnPointsFragment d1;
    private CheckPointsFragment d2;
    private RedeemPointsFragment d3;

    // FragmentManager와 Context
    private FragmentManager manager;
    private Context context;

    // 생성자: 초기화와 다이얼로그 상태 설정
    public PointDialogController(Context context) {
        this.context = context;

        // 사용자 전화번호로 상태 설정
        if (GlobalVariable.isPhoneNumberSet() && UserData.getIndexByPhone(GlobalVariable.getPhoneNumberAdded010()) != -1) {
            states = REDEEM_POINTS;
        } else {
            states = EARN_POINTS;
        }

        // FragmentManager 가져오기
        manager = ((AppCompatActivity) context).getSupportFragmentManager();

        // 총 결제 금액에서 적립될 포인트 계산
        int totalPrice = GlobalVariable.getTotalPrice();
        GlobalVariable.setPointsToBeEarned((int)(totalPrice * 0.01 * EARN_RATE));

        // 다이얼로그 보여주기
        showDialog();
    }

    // 상태에 맞는 다이얼로그를 보여주는 메소드
    public void showDialog() {
        switch (states) {
            case EARN_POINTS:
                showEarnPointsFragment(); break;
            case CHECK_POINTS:
                showCheckPointsFragment(); break;
            case REDEEM_POINTS:
                showRedeemPointsFragment(); break;
            default: break;
        }
    }

    // 포인트 적립 다이얼로그를 띄우는 메소드
    private void showEarnPointsFragment() {
        d1 = new EarnPointsFragment(); // 적립 포인트를 전달하여 다이얼로그 생성
        d1.setPointsDialogCompleteListener(this); // 리스너 설정
        d1.show(manager, "EarnPointsDialog"); // 다이얼로그 띄우기
    }

    // 포인트 확인 다이얼로그를 띄우는 메소드
    private void showCheckPointsFragment() {
        // 이미 다이얼로그가 열려있는지 확인 후 열기
        if (d2 != null && (d2.isAdded() || d2.isVisible() || d2.isDialogOpen))
            return;

        d2 = new CheckPointsFragment(); // 확인용 다이얼로그 생성
        d2.setPointsDialogCompleteListener(this); // 리스너 설정
        d2.show(manager, "CheckPointsDialog"); // 다이얼로그 띄우기
    }

    // 포인트 사용 다이얼로그를 띄우는 메소드
    private void showRedeemPointsFragment() {
        d3 = new RedeemPointsFragment(); // 사용 포인트 다이얼로그 생성
        d3.setPointsDialogCompleteListener(this); // 리스너 설정
        d3.show(manager, "RedeemPointsDialog"); // 다이얼로그 띄우기
    }

    @Override
    public void onDialogComplete(String dialogTag) {
        switch (dialogTag) {
            case "EarnPoints":
                onEarnPointsDialogComplete(); break;
            case "CheckPoints":
                onCheckPointsDialogComplete(); break;
            case "RedeemPoints":
                onRedeemPointsDialogComplete(); break;
            default:
                break;
        }
    }

    // 포인트 적립 다이얼로그 완료 후 처리
    private void onEarnPointsDialogComplete() {
        String phoneNumber = GlobalVariable.getPhoneNumberAdded010();
        // 전화번호로 사용자 정보 찾기
        UserData userData = UserData.searchUserByPhone(phoneNumber);

        // 신규 고객인지 아닌지 체크
        if (userData == null) {
            states = CHECK_POINTS;
        } else {
            GlobalVariable.setUserData(userData);
            states = REDEEM_POINTS;
            d1.dismiss();
        }

        showDialog();
    }

    // 포인트 확인 다이얼로그 완료 후 처리
    private void onCheckPointsDialogComplete() {
        UserData user = new UserData(GlobalVariable.getPhoneNumberAdded010());
        UserData.userList.add(user);

        d1.dismiss();
        d2.dismiss();
        states = REDEEM_POINTS;
        showDialog();
    }

    // 포인트 사용 다이얼로그 완료 후 처리
    private void onRedeemPointsDialogComplete() {
        int redeemedPoints = d3.getInputPoints();
        GlobalVariable.setRedeemedPoints(redeemedPoints);

        // UI에 사용 포인트 업데이트
        LinearLayout layout = ((Activity) context).findViewById(R.id.layout_redeemed_points);
        layout.setVisibility(View.VISIBLE);

        TextView text = ((Activity) context).findViewById(R.id.text_redeemed_points);
        text.setText("-" + NumberUtils.formatNumber(redeemedPoints) + "P");

        d3.dismiss();
    }

    // 다이얼로그에서 버튼 클릭 이벤트 처리
    public void onClick(View view) {
        switch (states) {
            case EARN_POINTS:
                onClickedInEarnPoints(view); break;
            case CHECK_POINTS:
                onClickedInCheckPoints(view); break;
            case REDEEM_POINTS:
                onClickedInRedeemPoints(view); break;
            default: break;
        }
    }

    // 포인트 번호 입력에서 버튼 클릭 처리
    private void onClickedInEarnPoints(View view) {
        d1.dismiss();
    }

    // 포인트 신규 가입에서 버튼 클릭 처리
    private void onClickedInCheckPoints(View view) {
        int id = view.getId();

        if (id == R.id.buttonX_check_points) {
            states = EARN_POINTS;
            d2.dismiss();
        } else if (id == R.id.button_earn_points_check) {
            onCheckPointsDialogComplete();
        }
    }

    // 포인트 사용 버튼 클릭 처리
    private void onClickedInRedeemPoints(View view) {
        int id = view.getId();

        if (id == R.id.button_not_use_points) {
            d3.dismiss();
        } else if (id == R.id.button_use_points) {
            onRedeemPointsDialogComplete();
        }
    }
}
