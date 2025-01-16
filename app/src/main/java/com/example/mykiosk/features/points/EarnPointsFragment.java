package com.example.mykiosk.features.points;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mykiosk.R;
import com.example.mykiosk.components.NumberPadView;
import com.example.mykiosk.utils.GlobalVariable;

// 포인트 적립 (번호 입력) 다이얼로그를 구현한 클래스
public class EarnPointsFragment extends DialogFragment {

    private PointsDialogCompleteListener listener; // 다이얼로그 완료 리스너

    // 리스너 설정 메서드
    public void setPointsDialogCompleteListener(PointsDialogCompleteListener listener) {
        this.listener = listener;
    }

    private Dialog dialog; // 다이얼로그 객체
    private TextView text1, text2; // 전화번호 표시를 위한 텍스트뷰

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 다이얼로그 빌더 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.points_earn); // 레이아웃 설정
        dialog = builder.create(); // 다이얼로그 생성
        dialog.setCanceledOnTouchOutside(false); // 다이얼로그 외부 터치로 종료 방지

        dialog.setOnShowListener(d -> {
            // 숫자 패드 뷰를 동적으로 추가
            FrameLayout frame = dialog.findViewById(R.id.frame_number_pad);
            NumberPadView numberPadView = new NumberPadView(getActivity());
            frame.addView(numberPadView);

            // 예상 적립 포인트 표시
            TextView textView = dialog.findViewById(R.id.text_expected_earn_points);
            textView.setText(GlobalVariable.getPointsToBeEarned() + "P");

            // 숫자 패드 클릭 리스너 설정
            numberPadView.setOnNumberPadClickListener(new NumberPadView.OnNumberPadClickListener() {
                @Override
                public void onNumberClicked(String number) {
                    // 입력 길이가 8자를 초과하면 잘라냄
                    if (number.length() > 8)
                        numberPadView.setInput(numberPadView.getInput().substring(0, 8));

                    // 입력된 전화번호를 업데이트
                    String phoneNumber = numberPadView.getInput();
                    setTextPhoneNumber(phoneNumber);

                    // 전화번호가 8자리에 도달했을 때 완료 리스너 호출
                    if (phoneNumber.length() == 8 && listener != null) {
                        GlobalVariable.setPhoneNumber(phoneNumber);
                        listener.onDialogComplete("EarnPoints");
                    }
                }
            });
        });

        return dialog; // 생성된 다이얼로그 반환
    }

    // 전화번호 텍스트뷰를 업데이트하는 메서드
    public void setTextPhoneNumber(String phoneNumber) {
        text1 = dialog.findViewById(R.id.text_phone_number_1); // 첫 번째 텍스트뷰
        text2 = dialog.findViewById(R.id.text_phone_number_2); // 두 번째 텍스트뷰

        String str = phoneNumber;
        for (int i = phoneNumber.length(); i < 8; i++)
            str += " "; // 길이를 8자로 맞추기 위해 공백 추가

        // 전화번호를 두 부분으로 나누어 설정
        text1.setText(str.substring(0, 4));
        text2.setText(str.substring(4));
    }
}
