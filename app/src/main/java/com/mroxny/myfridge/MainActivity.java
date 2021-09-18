package com.mroxny.myfridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AddProductDialog.ProductDialogListener{

    private static final String SHARED_PREFS_FILE = "shared_prefs";
    private static final String SHARED_PREFS_PRODUCTS_KEY = "products";


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton addProductButton;
    private TextView noProductsSign;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        addProductsToViewport();

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

    private void loadProducts(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(SHARED_PREFS_PRODUCTS_KEY,null);
        Gson gson = gsonMaker();

        products.clear();
        if(set!=null) {
            for (String e : set) {
                products.add(gson.fromJson(e, Product.class));
            }
        }
    }

    private void openAddProductDialog(){
        AddProductDialog productDialog = new AddProductDialog();
        productDialog.show(getSupportFragmentManager(),"product dialog");

    }

    private void addProduct(String name, int icon) {
        products.add(new Product(name,icon));
        saveProducts();
        addProductsToViewport();
    }

    private void addProductsToViewport(){
        if(products.size()>0) {
            noProductsSign.setVisibility(View.GONE);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);

            mAdapter = new ProductAdapter(products);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
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
        name = "Nieznany proukt";
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
}