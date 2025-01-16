package com.example.mykiosk.features.points;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mykiosk.R;
import com.example.mykiosk.components.NumberPadView;
import com.example.mykiosk.data.UserData;
import com.example.mykiosk.utils.GlobalVariable;
import com.example.mykiosk.utils.NumberUtils;

// 포인트 사용 다이얼로그를 구현한 클래스
public class RedeemPointsFragment extends DialogFragment {

    private final int MIN_POINTS = 1000; // 최소 사용 포인트

    private Dialog dialog; // 다이얼로그 객체

    private int inputPoints = 0; // 사용자가 입력한 포인트

    private PointsDialogCompleteListener listener; // 다이얼로그 완료 리스너

    // 리스너 설정 메서드
    public void setPointsDialogCompleteListener(PointsDialogCompleteListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 다이얼로그 빌더 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.points_redeem); // 레이아웃 설정
        dialog = builder.create(); // 다이얼로그 생성
        dialog.setCanceledOnTouchOutside(false); // 다이얼로그 외부 터치로 종료 방지

        dialog.setOnShowListener(d -> {
            // 숫자 패드 뷰 추가
            FrameLayout frame = dialog.findViewById(R.id.frame_number_pad);
            NumberPadView numberPadView = new NumberPadView(getActivity());
            frame.addView(numberPadView);

            // 텍스트 초기화 메서드 호출
            setTextRemainingCost(); // 남은 비용 설정
            setTextRemainingPoints(); // 남은 포인트 설정
            setTextPointsToRedeem(); // 사용할 포인트 설정
            setTextMinimumPoints(); // 최소 포인트 텍스트 설정

            // 숫자 패드 클릭 리스너 설정
            numberPadView.setOnNumberPadClickListener(new NumberPadView.OnNumberPadClickListener() {
                @Override
                public void onNumberClicked(String number) {
                    UserData userData = GlobalVariable.getUserData();

                    if (number.length() == 0 || userData == null) {
                        numberPadView.setInput("0"); // 입력이 없으면 0으로 설정
                    } else if (Integer.parseInt(number) > userData.getPoints()) {
                        numberPadView.setInput(userData.getPoints() + ""); // 사용 가능한 포인트 초과 방지
                    }

                    inputPoints = Integer.parseInt(numberPadView.getInput()); // 입력된 포인트 값 저장
                    int totalPrice = GlobalVariable.getTotalPrice();

                    if (inputPoints > totalPrice) {
                        inputPoints = totalPrice; // 총 금액 초과 방지
                        numberPadView.setInput(inputPoints + "");
                    }

                    setTextPointsToRedeem(); // 사용 포인트 텍스트 업데이트
                }
            });

            // "모두 사용" 버튼 클릭 리스너 설정
            ((Button) dialog.findViewById(R.id.button_total_redeemed)).setOnClickListener(v -> {
                UserData userData = GlobalVariable.getUserData();
                int totalPrice = GlobalVariable.getTotalPrice();

                if (userData == null)
                    inputPoints = 0;
                else
                    inputPoints = Math.min(totalPrice, userData.getPoints()); // 가능한 최대 포인트 계산

                numberPadView.setInput(inputPoints + ""); // 입력 필드 업데이트
                setTextPointsToRedeem(); // 사용 포인트 텍스트 업데이트
            });
        });

        return dialog; // 생성된 다이얼로그 반환
    }

    // 남은 비용 텍스트 설정 메서드
    public void setTextRemainingCost() {
        TextView textView = dialog.findViewById(R.id.text_remaining_cost);

        if (textView == null) return;

        String text = NumberUtils.formatNumber(GlobalVariable.getTotalPrice()) + "원";
        textView.setText(text);
    }

    // 남은 포인트 텍스트 설정 메서드
    public void setTextRemainingPoints() {
        TextView textView = dialog.findViewById(R.id.text_remaining_points);

        if (textView == null) return;

        UserData userData = GlobalVariable.getUserData();
        if (userData == null) return;

        String text = NumberUtils.formatNumber(userData.getPoints()) + "P";
        textView.setText(text);
    }

    // 사용할 포인트 텍스트 설정 메서드
    public void setTextPointsToRedeem() {
        TextView textView = dialog.findViewById(R.id.text_payment_points);
        Button button = dialog.findViewById(R.id.button_use_points);

        if (textView == null) return;

        String text = NumberUtils.formatNumber(inputPoints) + "P";
        textView.setText(text);

        if (inputPoints < MIN_POINTS) { // 최소 포인트 미만일 경우
            textView.setTextColor(Color.RED);
            button.setEnabled(false);
        } else { // 최소 포인트 이상일 경우
            textView.setTextColor(Color.BLUE);
            button.setEnabled(true);
        }
    }

    // 최소 포인트 텍스트 설정 메서드
    public void setTextMinimumPoints() {
        TextView textView = dialog.findViewById(R.id.text_min_points);

        if (textView == null) return;

        String str = NumberUtils.formatNumber(MIN_POINTS) + "P";
        String text = "최소 사용 포인트는 " + str + " 입니다.";
        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf(str);
        int end = start + str.length();

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 굵게 설정

        textView.setText(spannableString);
    }

    // 입력된 포인트 반환 메서드
    public int getInputPoints() {
        return inputPoints;
    }
}
