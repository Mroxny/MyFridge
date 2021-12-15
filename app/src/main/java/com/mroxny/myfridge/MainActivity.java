package com.mroxny.myfridge;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AddProductDialog.ProductDialogListener{

    private static final String SHARED_PREFS_FILE = "shared_prefs";
    private static final String SHARED_PREFS_PRODUCTS_KEY = "products";


    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton addProductButton;
    private TextView noProductsSign;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        noProductsSign = findViewById(R.id.no_products_sign);

        addProductButton = (FloatingActionButton) findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddProductDialog();
            }
        });



        loadProducts();

        if(getIntent().getSerializableExtra("Product")!=null){
            Product tempProd = (Product) getIntent().getSerializableExtra("Product");
            editProducts(tempProd);
        }

        addProductsToViewport();

    }

    private void editProducts(Product product){
        int i = 0;
        boolean found=false;
        while(!found){
            if(products.get(i).getId().equals(product.getId())) found = true;
            else {
                found = false;
                i++;
            }
        }
        products.set(i,product);
        saveProducts();
    }


    private void saveProducts(){
        Gson gson = gsonMaker();
        Set<String> set = new HashSet<String>();
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for(Product e: products){
            set.add(gson.toJson(e,Product.class));
        }

        editor.putStringSet(SHARED_PREFS_PRODUCTS_KEY,set);
        editor.apply();
    }

    private void loadProducts() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(SHARED_PREFS_PRODUCTS_KEY, null);
        Gson gson = gsonMaker();

        products.clear();
        if (set != null) {
            for (String e : set) {
                products.add(gson.fromJson(e, Product.class));
            }
        }
        sortArrayList(products);
    }

    private void sortArrayList(ArrayList<Product> products) {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getId().compareToIgnoreCase(o2.getId());
            }
        });
    }


    private void openAddProductDialog(){
        AddProductDialog productDialog = new AddProductDialog();
        productDialog.show(getSupportFragmentManager(),"product dialog");

    }

    private void addProduct(String name, int icon) {
        Date time = Calendar.getInstance().getTime();
        long id=time.getTime();

        products.add(new Product(name,icon,id));
        saveProducts();
        addProductsToViewport();
    }

    private void addProductsToViewport(){
        if(products.size()>0) {
            noProductsSign.setVisibility(View.GONE);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);

            mAdapter = new ProductAdapter(products,this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                    intent.putExtra("Product",products.get(position));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            noProductsSign.setVisibility(View.VISIBLE);
        }
    }

    private String correctName(String name){
        if (name != null && name.length()>0){
            name = name.replace("\n", "").replace("\r", "");
            return name;
        }
        name = getString(R.string.UI_unknown_product_name);
        return name;
    }

    private Gson gsonMaker(){
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();

        return builder.create();
    }

    @Override
    public void applyTexts(String name, int icon) {
        name = correctName(name);
        addProduct(name,icon);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveProducts();
    }

    public void deleteProduct(int i){
        final Dialog dialog = new Dialog(MainActivity.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.remove_product_dialog_layout);

        //Initializing the views of the dialog.
        Button submitButton = dialog.findViewById(R.id.button_yes);
        Button dismissButton = dialog.findViewById(R.id.button_no);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.remove(i);
                mAdapter.notifyItemRemoved(i);
                saveProducts();
                addProductsToViewport();
                dialog.dismiss();
            }
        });
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}