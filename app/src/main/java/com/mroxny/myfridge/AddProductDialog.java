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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class AddProductDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private EditText editTextName;
    private int image = 0;
    private ProductDialogListener listener;
    private View view;
    private ArrayList<Button> buttons = new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_product_layout, null);

        setUpIcons();

        builder.setView(view)
                .setTitle(getString(R.string.UI_add_product))
                .setNegativeButton(getString(R.string.UI_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(getString(R.string.UI_add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editTextName.getText().toString();
                        int icon;
                        if(image!=0){
                            icon = image;
                        }
                        else{
                            icon = R.drawable.ic_round_default_icon;
                        }



                        //tutaj dodaj nowe zmienne produktu

                        listener.applyTexts(name,icon);
                    }
                });

        editTextName = view.findViewById(R.id.edit_name);

        return builder.create();
    }

    private void setUpIcons(){
        int buttonsNum = 5;
        for(int i = 1;i<=buttonsNum; i++){
            String name = "icon"+i;
            int resId = getResId(name, R.id.class);
            Button b = (Button) view.findViewById(resId);
            buttons.add(b);
            buttons.get(i-1).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int icon = 0;
        switch (v.getId()) {
            case R.id.icon1:
                icon = R.drawable.ic_round_default_icon;
                break;
            case R.id.icon2:
                icon = R.drawable.ic_bottle;
                break;
            case R.id.icon3:
                icon = R.drawable.ic_egg;
                break;
            case R.id.icon4:
                icon = R.drawable.ic_wege;
                break;
            case R.id.icon5:
                icon = R.drawable.ic_chicken;
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
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
