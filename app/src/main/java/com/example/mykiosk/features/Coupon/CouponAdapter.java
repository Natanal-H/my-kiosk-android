package com.example.mykiosk.features.Coupon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykiosk.R;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    public interface RecyclerViewClickListener { //밖에서 쓸수있게 인터페이스로 만듦
        void onItemClciked(int position, Coupon coupon); //내부에서 쓸놈
    }

    private RecyclerViewClickListener mListener;


    public void setOnClickListener(RecyclerViewClickListener listener) {  //외부에서 쓸놈
        mListener = listener;
    }

    private List<Coupon> couponList;
    Context context;


    public CouponAdapter(Context context, List<Coupon> couponList) {
        this.context = context;
        this.couponList = couponList;

    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coupon_item2, parent, false);
        return new CouponViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        Coupon coupon = couponList.get(position);
        if (coupon.getQty() > 0) {
            holder.bind(coupon);
            holder.itemView.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setVisibility(View.GONE);
        }
        final int pos = position;

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClciked(pos, coupon);

            }//위치값을 전달한다.
        });

    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }


    public class CouponViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView PeriodTextView;
        private TextView availableTextView;
        private TextView qty;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.couponName);
            descriptionTextView = itemView.findViewById(R.id.coupondescription);
            PeriodTextView = itemView.findViewById(R.id.couponPeriod);
            availableTextView = itemView.findViewById(R.id.couponavailable);
            qty = itemView.findViewById((R.id.qty));

        }

        public void bind(Coupon coupon) {
            nameTextView.setText(coupon.getName());
            descriptionTextView.setText(coupon.getDescription());
            PeriodTextView.setText(coupon.getPeriod());
            availableTextView.setText(coupon.getAvailable());
            String qtyString = coupon.getQty() + "";
            qty.setText(qtyString + "개");
        }
    }


}

