package com.example.mykiosk.features.Coupon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykiosk.R;

import java.util.ArrayList;
import java.util.List;

public class Dialog2 extends Dialog implements CouponAdapter.RecyclerViewClickListener {
    Context context;
    UserData userData ;
    String totalCount;
    int stamp;
    int plusStamp;

    Dialog1.DialogListener listener;
    public void setDialogListener(Dialog1.DialogListener listener){
        this.listener=listener;
    }

    public int getTotalCoupon() {
        return totalCoupon;
    }

    int totalCoupon;
    int count;

    public List<Coupon> getUsed() {
        return used;
    }

    List<Coupon> used;
    TextView totalCount_dial;
    List<Coupon> couponList;
    String state;


    public Dialog2(@NonNull Context context, UserData userData, int stamp, String totalCount) {
        super(context);
        this.context=context;
        this.stamp=stamp;
        this.userData=userData;
        this.totalCount=totalCount;
        used= new ArrayList<>();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_main);
        Window window = this.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        Button noUseCoupon= findViewById(R.id.noUse);
        Button finish= findViewById(R.id.finish);


        noUseCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stampPlus();
                if(listener!=null){
                    listener.onDialogResult("noUseCoupon");
                dismiss();
                // 두 번째 대화상자 닫기
                }
            }
        });
        finish.setOnClickListener(v-> {
                stampPlus();
                if(listener!=null) {
                    listener.onDialogResult("useCoupon");
                    dismiss();
                }
        });




        TextView gift=findViewById(R.id.gift);
        TextView plusStemp_2=findViewById(R.id.plusStemp_2);
        totalCount_dial=findViewById(R.id.totalCount_dial);

        totalCount_dial.setText(totalCount);

        String result= "보유 스탬프: "+userData.getStamp();
        gift.setText(result);
        plusStemp_2.setText("적립될 스탬프: "+stamp+"");

        couponList=new ArrayList<>();
        couponList.add(new Coupon("1000원 쿠폰","결제금액 1000원에 대해 쿠폰으로 사용합니다.","2024-12-01~2025-11-30","사용가능",userData.getCoupon_1000(),1000));
        couponList.add(new Coupon("2000원 쿠폰","결제금액 2000원에 대해 쿠폰으로 사용합니다.","2024-12-01~2025-11-30","사용가능",userData.getCoupon_2000(),2000));
        couponList.add(new Coupon("3000원 쿠폰","결제금액 3000원에 대해 쿠폰으로 사용합니다.","2024-12-01~2025-11-30","사용가능",userData.getCoupon_3000(),3000));
        couponList.add(new Coupon("4000원 쿠폰","결제금액 4000원에 대해 쿠폰으로 사용합니다.","2024-12-01~2025-11-30","사용가능",userData.getCoupon_4000(),4000));
        couponList.add(new Coupon("5000원 쿠폰","결제금액 5000원에 대해 쿠폰으로 사용합니다.","2024-12-01~2025-11-30","사용가능",userData.getCoupon_5000(),5000));
        couponList.add(new Coupon("웰컴 기프티콘","결제금액 1000원에 대해 쿠폰으로 사용합니다.(회원가입시 발급) ","2024-12-01~2025-11-30","사용가능",userData.getCoupon_welcome(),1000));


        RecyclerView recyclerView= findViewById(R.id.rView);
        CouponAdapter adapter= new CouponAdapter(context,couponList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setOnClickListener(this);

    }
    public void stampPlus(){
        plusStamp=stamp+userData.getStamp();
        int ToastCoupon=plusStamp/10 ;
        if(ToastCoupon>=1){
            Toast.makeText(context,"스탬프 10회가"+ToastCoupon+"번 누적되어 결제시 1000원쿠폰 "+(ToastCoupon)+"개가 추가됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClciked(int position,Coupon coupon) {

        LinearLayout dialogLayout=(LinearLayout)getLayoutInflater().inflate(R.layout.use_coupon,null);
        TextView anableCoupon=dialogLayout.findViewById(R.id.anableCoupon);
        EditText inputCoupon=dialogLayout.findViewById(R.id.couponInput);
        int useableCoupon= coupon.getQty();
        anableCoupon.setText("사용 가능한 쿠폰: "+useableCoupon+"개");
        String hint= useableCoupon+"개 사용가능";
        inputCoupon.setHint(hint);
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());

        builder.setTitle("쿠폰 사용")
                            .setView(dialogLayout)
                                    .setPositiveButton("확인",null)
                                            .setNegativeButton("취소",(dialog,which)->dialog.dismiss());
        AlertDialog dialog= builder.create();

        //확인버튼 리스너 설정
        dialog.setOnShowListener(dialogInterface->{
            count=0;
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v->{
                String inputText= inputCoupon.getText().toString();
                if(inputText.isEmpty()){
                    Toast.makeText(context,"쿠폰 갯수를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int useCoupon=Integer.parseInt(inputText);

                if(useCoupon<=useableCoupon){
                    int value=coupon.getValue(); // 전체 쿠폰수
                    int totalDiscount= useCoupon*value; // 사용할 쿠폰 금액
                    totalCoupon+=totalDiscount; // 전체 쿠폰 금액
                    int intCount=Integer.parseInt(totalCount); // 액티비티에서 넘어온거 인트로만들어줌
                    if(totalCoupon>intCount) { //전체금액보다 쿠폰 전체 사용금액이 크면 사용안된다.
                        Toast.makeText(context, "쿠폰 사용금액이 결제금액: " + totalCount + "원을 초과했습니다.", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context,"총 할인 금액: "+totalCoupon+ "원", Toast.LENGTH_LONG).show();
                         count= intCount - totalCoupon;   // 사용할 쿠폰 할인들 빼줌
                        String StrCount = count + "";
                        totalCount_dial.setText(StrCount);
                        int remain=(coupon.getQty()-useCoupon); //남은 쿠폰갯수
                        coupon.setQty(remain);
                        used.add(new Coupon (useCoupon,coupon.getValue())); //객체 배열은 없다. 직접 하나씩 추가해야함 결제에서 취소됐을경우 원복을위해

//                        if (position >= 0 && position < couponList.size()) {
//                            adapter.notifyItemChanged(position);
//                        } else {
//                            Log.e("RecyclerView", "Invalid position: " + position);
//                        }

//                        adapter.notifyItemChanged(position);

                        dialog.dismiss();
                    }
                }else {
                    Toast.makeText(context, "사용 가능한 쿠폰보다 많습니다. 다시 입력해주세요.",Toast.LENGTH_SHORT).show();
                }

                    });
        });
        dialog.show();
    }
}
