package com.example.mykiosk.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mykiosk.R;
import com.example.mykiosk.data.CoffeeData;
import com.example.mykiosk.data.UserData;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 스플래시 화면의 레이아웃 설정
        setContentView(R.layout.activity_splash);

        // 초기 데이터 설정 함수 호출
        dataInit();

        // 1초 후에 ServiceChoiceActivity로 이동
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // ServiceChoiceActivity로 화면 전환
                Intent intent = new Intent(SplashActivity.this, ServiceChoiceActivity.class);
                startActivity(intent);
                // 스플래시 화면을 종료하여 뒤로가기 버튼을 눌러도 스플래시 화면으로 돌아가지 않도록 설정
                finish();
            }
        }, 1000); // 1000ms = 1초
    }

    // 초기 데이터 설정 함수
    void dataInit() {
        // CoffeeData의 리스트를 초기화
        CoffeeData.coffeeList = new ArrayList<>();
        // CoffeeData 초기화 메서드 호출
        CoffeeData.initializeCoffeeData(this);

        // UserData 초기화 메서드 호출
        UserData.initializeUserData(this);
    }
}
