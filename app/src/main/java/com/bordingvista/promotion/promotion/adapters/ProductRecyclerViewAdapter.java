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
import com.bordingvista.promotion.promotion.Util.CustomLinearLayoutManager;
import com.bordingvista.promotion.promotion.models.Product;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by imran.zahid on 1/4/2016.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {
    List<Product> products;
    Context context;

    public ProductRecyclerViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productId;
        TextView productDescription;
        RecyclerView promotionListView;

        ProductViewHolder(View itemView) {
            super(itemView);
            productId = (TextView) itemView.findViewById(R.id.textViewProductId);
            productDescription = (TextView) itemView.findViewById(R.id.textViewProductDescription);
            promotionListView = (RecyclerView) itemView.findViewById(R.id.recyclerViewPromotions);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_product_cardview, viewGroup, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);

        //Add Custom Layout Manager to child promotion list
        CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(context);
        productViewHolder.promotionListView.setLayoutManager(customLinearLayoutManager);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder productViewHolder, int i) {
        productViewHolder.productId.setText("" + products.get(i).getId());
        productViewHolder.productDescription.setText(products.get(i).getDescription());
        Gson gson = new Gson();
        String response = gson.toJson(products.get(i));
        Log.d(Constants.LOG_PROMOTION, response);
        if (products.get(i).getPromotionList() != null ) {
            productViewHolder.promotionListView.setAdapter(new ChildPromotionRecyclerViewAdapter(products.get(i).getPromotionList(), context));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
