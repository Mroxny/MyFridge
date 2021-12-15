package com.mroxny.myfridge;

import android.content.Context;
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
    public Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView name;
        public TextView amount;

        private Button inc;
        private Button dec;


        public ProductViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            amount = itemView.findViewById(R.id.textView2);
            inc = itemView.findViewById(R.id.add_button);
            dec = itemView.findViewById(R.id.remove_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public ProductAdapter(ArrayList<Product> exampleList, Context context) {
        mExampleList = exampleList;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v, mListener);
        return pvh;
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product currentItem = mExampleList.get(position);
        holder.icon.setImageResource(currentItem.getImageResource());
        holder.name.setText(correctName(currentItem.getName()));
        holder.amount.setText(String.valueOf(currentItem.getAmount()));

        setButtons(holder,currentItem, position);

    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    private String correctName(String name){
        if (name != null && name.length()>0){
            name = name.replace("\n", "").replace("\r", "");
            if(name.length()>17){
                name = name.substring(0,16) + "...";
            }
            return name;
        }
        return name;
    }

    private void setButtons(ProductViewHolder holder, Product currentItem, int pos){

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
                if(currentItem.getAmount()>1){

                    currentItem.decrementAmount();
                    holder.amount.setText(String.valueOf(currentItem.getAmount()));
                }
                else{
                    MainActivity ma = (MainActivity) context;
                    ma.deleteProduct(pos);
                    currentItem.setAmount(0);
                    holder.amount.setText(String.valueOf(currentItem.getAmount()));
                }

            }
        });
    }
}

