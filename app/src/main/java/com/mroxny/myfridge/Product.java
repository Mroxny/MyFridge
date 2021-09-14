package com.mroxny.myfridge;

public class Product {

    private String id;
    private String name;
    private int amount = 1;
    private String category;

    public void SetId(int idNumber){
        this.id = "id:"+idNumber;
    }

    public String GetId(){
        return this.id;
    }

    public void SetName(String name){
        this.name = name;
    }

    public String GetName(){
        return this.name;
    }

    public void SetAmount(int number){
        this.amount = number;
    }

    public int GetAmount(){
        return this.amount;
    }

    public void IncrementAmount(int number){
        this.amount+=number;
    }

    public void IncrementAmount(){
        this.amount+=1;
    }

    public void DecrementAmount(int number){
        if(this.amount>=number) {
            this.amount -= number;
        }
    }

    public void DecrementAmount(){
        if(this.amount>0) {
            this.amount -= 1;
        }
    }

    public void SetCategory(String category){
        this.category = category;
    }

    public String GetCategory(){
        return this.category;
    }
}
