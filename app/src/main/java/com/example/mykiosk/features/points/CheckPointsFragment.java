package com.example.mykiosk.features.points;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.UnderlineSpan;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mykiosk.R;
import com.example.mykiosk.utils.GlobalVariable;

// 포인트 (신규 고객) 다이얼로그를 구현한 클래스
public class CheckPointsFragment extends DialogFragment {

    public static boolean isDialogOpen = false; // 다이얼로그 열림 상태를 나타내는 플래그

    private Dialog dialog; // 다이얼로그 객체

    private PointsDialogCompleteListener listener; // 다이얼로그 완료 리스너

    // 리스너 설정 메서드
    public void setPointsDialogCompleteListener(PointsDialogCompleteListener listener) {
        this.listener = listener;
    }

    // 생성자: 다이얼로그 열림 상태 설정
    public CheckPointsFragment() {
        isDialogOpen = true;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 다이얼로그 빌더 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setCancelable(false);
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.points_check, null)); // 레이아웃 설정
        dialog = builder.create(); // 다이얼로그 생성

        dialog.setCanceledOnTouchOutside(false); // 다이얼로그 외부 터치로 종료 방지

        dialog.setOnShowListener(d -> {
            // 다이얼로그가 표시될 때 텍스트 설정 메서드 호출
            setTextPhoneNumber(); // 전화번호 설정
            setTextAgreement(); // 약관 텍스트 설정
            setCheckBoxText(); // 체크박스 텍스트 설정
        });

        return dialog; // 생성된 다이얼로그 반환
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isDialogOpen = false; // 다이얼로그가 닫힐 때 플래그 업데이트
    }

    // 전화번호 텍스트를 설정하는 메서드
    public void setTextPhoneNumber() {
        TextView textView = dialog.findViewById(R.id.text_phone_number_check); // 전화번호 텍스트뷰

        if (textView == null) return; // 텍스트뷰가 없으면 종료

        // 전화번호를 포맷팅하여 설정
        String phoneNumber = GlobalVariable.getPhoneNumber();
        String str = "010-" + phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4);

        textView.setText(str);
    }

    // 약관 텍스트를 설정하는 메서드
    public void setTextAgreement() {
        TextView textView = dialog.findViewById(R.id.text_agreement); // 약관 텍스트뷰

        if (textView == null) return; // 텍스트뷰가 없으면 종료

        // 약관 텍스트 설정 및 특정 단어 밑줄 처리
        String text = "휴대폰 번호를 입력하시면 서비스 이용 약관 및 개인정보 수집 및 이용, 제 3자 개인 정보제공에 동의하는 것으로 간주합니다.";
        SpannableString spannableString = new SpannableString(text);
        String[] wordsToUnderline = {"서비스 이용 약관", "개인정보 수집 및 이용", "제 3자 개인 정보제공"};

        for (String word : wordsToUnderline) {
            int start = text.indexOf(word);
            if (start != -1) {
                int end = start + word.length();
                spannableString.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 밑줄 설정
            }
        }

        textView.setText(spannableString); // 텍스트뷰에 설정
    }

    // 체크박스 텍스트를 설정하는 메서드
    public void setCheckBoxText() {
        CheckBox checkBox = dialog.findViewById(R.id.checkBox_agreement); // 체크박스

        if (checkBox == null) return; // 체크박스가 없으면 종료

        // 체크박스 텍스트 설정 및 특정 단어 밑줄, 글씨 크기 변경 처리
        String text = "[선택] 마케팅 정보 수신 동의 내용보기";
        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf("내용보기");
        int end = start + "내용보기".length();

        spannableString.setSpan(new AbsoluteSizeSpan(12, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 글씨 크기 설정
        spannableString.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 밑줄 설정

        checkBox.setText(spannableString); // 체크박스에 텍스트 설정
    }
}
