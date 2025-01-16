package com.example.mykiosk.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.mykiosk.R;

public class NumberPadView extends LinearLayout {
    private String input = ""; // 사용자가 입력한 숫자를 저장하는 변수

    // 숫자 패드 버튼이 클릭될 때 호출되는 리스너 인터페이스
    public interface OnNumberPadClickListener {
        void onNumberClicked(String number);
    }

    private OnNumberPadClickListener listener;

    // 기본 생성자
    public NumberPadView(Context context) {
        super(context);
        init(context);
    }

    // XML 속성값을 사용할 수 있는 생성자
    public NumberPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 뷰 초기화 메서드
    private void init(Context context) {
        // number_pad.xml 레이아웃을 이 뷰에 인플레이트
        inflate(context, R.layout.component_number_pad, this);
        // 버튼 클릭 리스너 설정
        setButtonListeners();
    }

    // 숫자 버튼과 특수 버튼에 리스너를 설정하는 메서드
    private void setButtonListeners() {
        // 숫자 버튼 클릭 시 input 값에 숫자를 추가하고 리스너를 호출
        findViewById(R.id.number_pad_0).setOnClickListener(v -> {
            input += "0";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_1).setOnClickListener(v -> {
            input += "1";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_2).setOnClickListener(v -> {
            input += "2";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_3).setOnClickListener(v -> {
            input += "3";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_4).setOnClickListener(v -> {
            input += "4";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_5).setOnClickListener(v -> {
            input += "5";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_6).setOnClickListener(v -> {
            input += "6";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_7).setOnClickListener(v -> {
            input += "7";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_8).setOnClickListener(v -> {
            input += "8";
            if (listener != null) listener.onNumberClicked(input);
        });
        findViewById(R.id.number_pad_9).setOnClickListener(v -> {
            input += "9";
            if (listener != null) listener.onNumberClicked(input);
        });

        // 클리어 버튼
        findViewById(R.id.number_pad_c).setOnClickListener(v -> {
            input = "";
            if (listener != null) listener.onNumberClicked(input);
        });

        // 삭제 버튼
        findViewById(R.id.number_pad_x).setOnClickListener(v -> {
            if (input.length() > 0) {
                input = input.substring(0, input.length() - 1);
                if (listener != null) listener.onNumberClicked(input);
            }
        });
    }

    // 현재 입력값을 반환하는 메서드
    public String getInput() {
        return input;
    }

    // 입력값을 설정하는 메서드
    public void setInput(String input) {
        this.input = input;
    }

    // NumberPadView에 리스너를 설정하는 메서드
    public void setOnNumberPadClickListener(OnNumberPadClickListener listener) {
        this.listener = listener;
    }
}