package com.example.tender.application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tender.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

public class EditName extends BottomSheetDialogFragment {

    public EditName() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_dialogsheet,container, false);

        Button sub = view.findViewById(R.id.buttonShare);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText context = view.findViewById(R.id.edit_name);
                EditText context1 = view.findViewById(R.id.edit_about);

                String item1 = context.getText().toString();
                String item2 = context1.getText().toString();
                Intent intent =  new Intent(getContext(), UserPreferencesActivity.class);
                intent.putExtra("name", item1);
                intent.putExtra("about", item2);
                startActivity(intent);
                Toast.makeText(getContext(), "Edit Success", Toast.LENGTH_SHORT).show();



            }
        });

        return  view;
    }
}
