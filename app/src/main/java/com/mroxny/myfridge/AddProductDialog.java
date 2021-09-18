package com.mroxny.myfridge;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class AddProductDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private EditText editTextName;
    private int image = 0;
    private ProductDialogListener listener;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_product_layout, null);

        setUpIcons();

        builder.setView(view)
                .setTitle("Dodaj Produkt")
                .setNegativeButton("anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editTextName.getText().toString();
                        int icon;
                        if(image!=0){
                            icon = image;
                        }
                        else{
                            icon = R.drawable.ic_fridge_icon;
                        }



                        //tutaj dodaj nowe zmienne produktu

                        listener.applyTexts(name,icon);
                    }
                });

        editTextName = view.findViewById(R.id.edit_name);

        return builder.create();
    }

    private void setUpIcons(){
        Button b1 = (Button) view.findViewById(R.id.icon1);
        b1.setOnClickListener(this);
        Button b2 = (Button) view.findViewById(R.id.icon2);
        b2.setOnClickListener(this);
        Button b3 = (Button) view.findViewById(R.id.icon3);
        b3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int icon = 0;
        switch (v.getId()) {
            case R.id.icon1:
                icon = R.drawable.ic_fridge_icon;
                break;
            case R.id.icon2:
                icon = R.drawable.ic_round_add;
                break;
            case R.id.icon3:
                icon = R.drawable.ic_round_add_product;
                break;
        }
        image = icon;
        setTempIcon(icon);
    }

    private void setTempIcon(int id){
        ImageView iv = view.findViewById(R.id.tempIcon);
        iv.setImageResource(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ProductDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ProductDialogListener");
        }
    }

    public interface ProductDialogListener {
        void applyTexts(String name, int icon);
    }
}
