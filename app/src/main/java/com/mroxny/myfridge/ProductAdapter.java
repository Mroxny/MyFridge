package com.mroxny.myfridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> mExampleList;
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView name;
        public TextView amount;

        private Button inc;
        private Button dec;

        public ProductViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            amount = itemView.findViewById(R.id.textView2);
            inc = itemView.findViewById(R.id.add_button);
            dec = itemView.findViewById(R.id.remove_button);
        }
    }
    public ProductAdapter(ArrayList<Product> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product currentItem = mExampleList.get(position);
        holder.icon.setImageResource(currentItem.getImageResource());
        holder.name.setText(currentItem.getName());
        holder.amount.setText(String.valueOf(currentItem.getAmount()));

        setButtons(holder,currentItem);

    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    private void setButtons(ProductViewHolder holder, Product currentItem){

        holder.inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItem.incrementAmount();
                holder.amount.setText(String.valueOf(currentItem.getAmount()));
            }
        });

        holder.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItem.decrementAmount();
                holder.amount.setText(String.valueOf(currentItem.getAmount()));
            }
        });
    }
}

