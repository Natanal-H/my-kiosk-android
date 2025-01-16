package com.example.mykiosk.features.check_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykiosk.R;
import com.example.mykiosk.data.CoffeeData;
import com.example.mykiosk.utils.NumberUtils;

import java.util.ArrayList;

// RecyclerView 어댑터 클래스: 주문 확인 화면에서 커피 리스트를 표시
public class CheckOrderAdapter extends RecyclerView.Adapter<CheckOrderAdapter.ViewHolder> {

    private ArrayList<CoffeeData> coffeeList; // 커피 데이터 리스트
    private LayoutInflater inflater; // 레이아웃 인플레이터 객체

    // 생성자: Context와 커피 데이터를 받아 초기화
    public CheckOrderAdapter(Context context, ArrayList<CoffeeData> coffeeList) {
        this.inflater = LayoutInflater.from(context);
        this.coffeeList = coffeeList;
    }

    @NonNull
    @Override
    // ViewHolder 생성: 아이템 레이아웃을 인플레이트하여 ViewHolder 반환
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.check_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // ViewHolder에 데이터를 바인딩: 각 커피 항목의 데이터를 설정
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoffeeData coffee = coffeeList.get(position); // 현재 위치의 커피 데이터 가져오기

        // 커피 이름, 수량, 가격 설정
        holder.coffeeName.setText(coffee.getCoffeeName());
        holder.coffeeQuantity.setText(coffee.getCoffeeQuantity() + "");
        holder.coffeePrice.setText(NumberUtils.formatNumber(coffee.getCoffeePrice()) + "원");
    }

    @Override
    // 아이템의 개수 반환
    public int getItemCount() {
        return coffeeList.size();
    }

    // ViewHolder 클래스: 각 아이템의 뷰를 보유
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView coffeeName, coffeeQuantity, coffeePrice; // 아이템의 텍스트뷰

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 레이아웃에서 뷰 초기화
            coffeeName = itemView.findViewById(R.id.coffee_name);
            coffeeQuantity = itemView.findViewById(R.id.coffee_quantity);
            coffeePrice = itemView.findViewById(R.id.coffee_price);
        }
    }

    // 특정 위치의 커피 데이터를 반환하는 메서드
    public CoffeeData getCoffeeData(int id) {
        return coffeeList.get(id);
    }
}
