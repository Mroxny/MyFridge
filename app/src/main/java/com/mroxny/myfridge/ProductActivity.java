package com.mroxny.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity {

    Product product;
    ImageView icon;
    EditText etName,etAmount;
    String tempAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

         icon = findViewById(R.id.imageView2);
         etName = findViewById(R.id.etName);
         etAmount = findViewById(R.id.etAmount);

        Product product = (Product) getIntent().getSerializableExtra("Product");
        setProduct(product);

    }

    public void setProduct(Product product){
        this.product = product;
        setInfo(product.getImageResource(),product.getName(),product.getAmount());
        setButtons();
    }

    private void setInfo(int image, String name, int amount){
        icon.setImageResource(image);
        etName.setText(name);
        tempAmount = Integer.toString(amount);
        etAmount.setText(tempAmount);
        setAmountListener();
    }

    private void setAmountListener(){
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0) tempAmount = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setButtons(){
        Button inc = findViewById(R.id.add_button);
        Button dec = findViewById(R.id.remove_button);
        Button back = findViewById(R.id.backButton);
        Button save = findViewById(R.id.saveButton);

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    private void increment(){
        int amount = Integer.parseInt(tempAmount);
        if(amount<999999999) etAmount.setText(Integer.toString(++amount));
    }

    private void decrement(){
        int amount = Integer.parseInt(tempAmount);
        if(amount>0) etAmount.setText(Integer.toString(--amount));
    }

    private void save(){
        product.setName(etName.getText().toString());
        product.setAmount(Integer.parseInt(etAmount.getText().toString()));
        Toast.makeText(this,getString(R.string.UI_saved), Toast.LENGTH_SHORT).show();
        back();
    }

    private void back(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Product",product);
        startActivity(intent);
        finish();

    }

}