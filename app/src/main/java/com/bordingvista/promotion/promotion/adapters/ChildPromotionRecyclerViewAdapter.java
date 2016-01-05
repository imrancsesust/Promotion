package com.bordingvista.promotion.promotion.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bordingvista.promotion.promotion.R;
import com.bordingvista.promotion.promotion.Util.Constants;
import com.bordingvista.promotion.promotion.models.Promotion;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by imran.zahid on 1/4/2016.
 */
public class ChildPromotionRecyclerViewAdapter extends RecyclerView.Adapter<ChildPromotionRecyclerViewAdapter.PromotionViewHolder>{
    List<Promotion> promotions;
    Context context;
    public ChildPromotionRecyclerViewAdapter(List<Promotion> promotions, Context context) {
        this.promotions = promotions;
        Gson gson = new Gson();
        String response = gson.toJson(promotions);
        Log.d(Constants.LOG_PROMOTION, "In child view:" +response);
        this.context = context;
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {
        TextView promotionId;
        TextView promotionDescription;
        TextView discount;

        PromotionViewHolder(View itemView) {
            super(itemView);
            promotionId = (TextView)itemView.findViewById(R.id.textViewPromotionId);
            promotionDescription = (TextView)itemView.findViewById(R.id.textViewPromotionDescription);
            discount = (TextView) itemView.findViewById(R.id.textViewPromotionDiscount);
        }
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_product_promotion_cardview, viewGroup, false);
        PromotionViewHolder promotionViewHolder = new PromotionViewHolder(view);

        return promotionViewHolder;
    }

    @Override
    public void onBindViewHolder(PromotionViewHolder promotionViewHolder, int i) {
        promotionViewHolder.promotionId.setText("" + promotions.get(i).getId());
        promotionViewHolder.promotionDescription.setText(promotions.get(i).getDescription());
        promotionViewHolder.discount.setText("Discount :" + promotions.get(i).getDiscount());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateData(List<Promotion> promotions){
        this.promotions = promotions;
        notifyDataSetChanged();
    }
}
