package com.example.mykiosk.features.RV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykiosk.R;
import com.example.mykiosk.data.CoffeeData;

import java.util.List;

public class RvAdapter2 extends RecyclerView.Adapter<MyVIewHolder2> {
    List<CoffeeData> data;

    public RvAdapter2(List<CoffeeData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyVIewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.test_rvitem,parent,false);

        return new MyVIewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder2 holder, int position) {
        holder.tv_Name.setText(data.get(position).getCoffeeN());
        holder.tv_Amount.setText(""+data.get(position).getCoffeeQ());
        holder.tv_Price.setText(""+data.get(position).getCoffeeP());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
