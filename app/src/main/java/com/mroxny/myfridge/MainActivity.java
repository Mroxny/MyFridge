package com.mroxny.myfridge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddProductDialog.ProductDialogListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton addProductButton;
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addProductButton = (FloatingActionButton) findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddProductDialog();
            }
        });

        LoadProducts();
    }

    private void SaveProducts(){

        
    }

    private void LoadProducts(){

    }

    private void openAddProductDialog(){
        AddProductDialog productDialog = new AddProductDialog();
        productDialog.show(getSupportFragmentManager(),"product dialog");

    }

    private void addProduct(String name, int icon) {
        products.add(new Product(name,icon));
        SaveProducts();
        addProductsToViewport();
    }

    private void addProductsToViewport(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new ProductAdapter(products);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private String correctName(String name){
        if (name != null && name.length()>0){
            name = name.replace("\n", "").replace("\r", "");
            return name;
        }
        name = "Nieznany proukt";
        return name;
    }

    @Override
    public void applyTexts(String name, int icon) {
        name = correctName(name);
        addProduct(name,icon);
    }

}