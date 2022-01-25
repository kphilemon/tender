package com.example.tender.application;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.tender.R;

public class Custom_Dialog1 extends AppCompatDialogFragment {

    Custom_dialog dialogInterface;
    EditText edit;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.edit_dialogsheet, null);

        builder.setView(view)
                .setTitle("Enter edit")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cont = edit.getText().toString();
                        dialogInterface.applyText(cont);


                    }
                });

        edit = view.findViewById(R.id.edit_name);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        dialogInterface = (Custom_dialog) context;

    }

    public interface Custom_dialog{
        void applyText(String cont);
    }


}
