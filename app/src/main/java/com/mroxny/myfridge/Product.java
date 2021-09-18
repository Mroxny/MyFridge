package com.mroxny.myfridge;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.Expose;

public class Product {

    @Expose private String id;
    @Expose private String name;
    @Expose private int amount = 1;
    @Expose private int imageResource;
    @Expose private String category;

    public Product (String name, int iconResource){
        setName(name);
        setImageResource(iconResource);
    }

    public void setId(int idNumber){
        this.id = "id:"+idNumber;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAmount(int number){
        this.amount = number;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setImageResource(int imgId){
        this.imageResource = imgId;
    }

    public int getImageResource(){
        return this.imageResource;
    }

    public void incrementAmount(int number){
        this.amount+=number;
    }

    public void incrementAmount(){
        this.amount+=1;
    }

    public void decrementAmount(int number){
        if(this.amount>=number) {
            this.amount -= number;
        }
    }

    public void decrementAmount(){
        if(this.amount>0) {
            this.amount -= 1;
        }
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String setCategory(){
        return this.category;
    }
}
