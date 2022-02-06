package com.example.tender.ui.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.tender.R;

public class EditAboutYouDialog extends AppCompatDialogFragment {
    private final String initialText;
    private final AboutYouChangeListener dialogInterface;

    private Context context;
    private EditText editText;

    public EditAboutYouDialog(String initialText, AboutYouChangeListener dialogInterface) {
        this.initialText = initialText;
        this.dialogInterface = dialogInterface;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(view)
                .setTitle("About you")
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .setPositiveButton("Submit", (dialog, which) -> {
                    String newText = editText.getText().toString();
                    if (!newText.equals(initialText)) {
                        dialogInterface.onAboutYouChanged(newText);
                    }
                });

        editText = view.findViewById(R.id.edit);
        editText.setText(initialText);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    public interface AboutYouChangeListener {
        void onAboutYouChanged(String aboutYou);
    }
}
