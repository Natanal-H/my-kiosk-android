package com.example.mykiosk.features.Coupon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mykiosk.R;

import java.util.ArrayList;

public class Dialog1 extends Dialog {
    ArrayList<UserData> userList = new ArrayList();
    private Context context;
    StringBuilder phoneNumber = new StringBuilder();
    String Sendphone, totalCount;

    int stamp;

    public UserData getUserdata() {
        return userdata;
    }

    UserData userdata;
    DialogListener listener;

    public interface DialogListener{
        void onDialogResult(String state);
    }

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }


    int[] buttonIds = {
            R.id.number_pad_1, R.id.number_pad_2, R.id.number_pad_3,
            R.id.number_pad_4, R.id.number_pad_5, R.id.number_pad_6,
            R.id.number_pad_7, R.id.number_pad_8, R.id.number_pad_9,
            R.id.number_pad_c, R.id.number_pad_0, R.id.number_pad_x
    };

    public Dialog1(Context context, int stamp, String totalCount) {
        super(context);
        this.context = context;
        this.stamp = stamp;
        this.totalCount = totalCount;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamp_main);

        TextView phonNumText = findViewById(R.id.phoneNum); //연락처 받는 텍스트뷰
        Button phoneSearch = findViewById(R.id.phoneSearch); //연락처 갖고 회원정보 갔다오는 버튼
        Button btnCloseDialog = findViewById(R.id.btnClose);
        TextView plusStamp = findViewById(R.id.plusStemp);
        plusStamp.setText(stamp + "");
        Button noStamp = findViewById(R.id.noStamp);


        noStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    stamp = 0;
                    listener.onDialogResult("finish");
                    dismiss();
                }
            }
        });


        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    stamp = 0;
                    listener.onDialogResult("finish");
                    dismiss();
                }
                // 두 번째 대화상자 닫기
            }
        });


        View.OnClickListener numberButtonClickListener = view -> {
            int id = view.getId(); //눌려진놈 찾기
            if (id == R.id.number_pad_c) {
                phoneNumber.setLength(0);
            } else if (id == R.id.number_pad_x) {
                // Backspace 버튼 처리
                if (phoneNumber.length() > 0) {
                    phoneNumber.deleteCharAt(phoneNumber.length() - 1);
                }
            } else {
                Button button = (Button) view;
                phoneNumber.append(button.getText().toString());
            }

            Sendphone = phoneNumber.toString();
            phonNumText.setText(Sendphone);

        };
        for (int id : buttonIds) { //buttonIds를 전역변수 배열로 사용
            Button button = findViewById(id); //다이알로그 2에서 사용가능하도록 붙임
            button.setOnClickListener(numberButtonClickListener);
        }

        phoneSearch.setOnClickListener(c -> {
            initializeUserData();
            userdata = searchUserByPhone(Sendphone); // 전화번호에 맞는 객체 하나 찾아옴
            if (Sendphone != null) {
                if (userdata != null) {
                    if (listener != null) {
                        listener.onDialogResult("member"); //메써드호출
                        dismiss();
                    }
                } else {
                    AlertDialog.Builder joinMemberDialogBuilder = new AlertDialog.Builder(context);
                    joinMemberDialogBuilder.setTitle("회원가입");
                    joinMemberDialogBuilder.setMessage("아래 휴대폰 번호로 가입원하시는게 맞습니까?" + "\n" + Sendphone);
                    joinMemberDialogBuilder.setPositiveButton("yes",
                            new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "회원가입이 완료됐습니다. 첫 가입 후 결제시 웰컴기프트 쿠폰 1개를 드립니다.",
                                            Toast.LENGTH_LONG).show();
                                    userList.add(new UserData(Sendphone, 0, 0, 0, 0, 0, 0, 0));
                                }
                            });
                    joinMemberDialogBuilder.setNegativeButton("NO",
                            new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "다시 휴대폰 번호를 입력해 주세요.",
                                            Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    phoneNumber.setLength(0);
                                    phonNumText.setText("");

                                }
                            });
                    AlertDialog joinMemberDialog = joinMemberDialogBuilder.create();
                    joinMemberDialog.show();
                }
            } else Toast.makeText(context, "휴대폰번호를 입력해주세요",
                    Toast.LENGTH_LONG).show();

        });
    }


    private void initializeUserData() {
        userList.add(new UserData("01050188065", 3, 2, 1, 3, 0, 1, 1));
        userList.add(new UserData("0", 3, 2, 1, 3, 0, 1, 1));
        userList.add(new UserData("01012345678", 5, 3, 0, 1, 2, 1, 0));
        userList.add(new UserData("01026328065", 6, 3, 1, 0, 0, 2, 1));
    }

    private UserData searchUserByPhone(String phoneNumber) { // 휴대폰번호와 맞는 객체 하나 갖고옴
        for (UserData user : userList) {
            if (user.getPhone().equals(phoneNumber)) {

                return user;
            }
        }
        return null;
    }
}
