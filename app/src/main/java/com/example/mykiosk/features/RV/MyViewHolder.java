package com.example.mykiosk.features.RV;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mykiosk.R;

public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name,tv_price,tv_qu;



    public MyViewHolder(View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.coffee_name);
        tv_price = itemView.findViewById(R.id.sum_price);
        tv_qu = itemView.findViewById(R.id.coffee_amount);


    }



}
