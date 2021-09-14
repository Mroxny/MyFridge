package com.mroxny.myfridge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private Product product;
    private FloatingActionButton addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addProductButton = (FloatingActionButton) findViewById(R.id.add_product_button);
        LinearLayout layout = findViewById(R.id.linearLayout);

        product = new Product();
        product.SetAmount(1);
        product.SetName("Jajka");
        product.SetId(1);

        View productView = View.inflate(this,R.layout.product,layout);
        TextView nameView = productView.findViewById(R.id.textView1);
        nameView.setText(product.GetName());
        TextView amountView = productView.findViewById(R.id.textView2);
        amountView.setText(String.valueOf(product.GetAmount()));
        Button inc = productView.findViewById(R.id.add_button);
        Button dec = productView.findViewById(R.id.remove_button);

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.IncrementAmount();
                amountView.setText(String.valueOf(product.GetAmount()));
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.DecrementAmount();
                amountView.setText(String.valueOf(product.GetAmount()));
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Dodaj produkt",Toast.LENGTH_SHORT).show();
            }
        });



    }
}