package ru.home.mynote.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.home.mynote.R;

public class BottomSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private OnDialogListener dialogListener;

    public static BottomSheetDialogFragment newInstance(){
        return new BottomSheetDialogFragment();
    }

    public void setOnDialogListener(OnDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragnent_dialog, container, false);
        view.findViewById(R.id.dialog_button_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (dialogListener != null) dialogListener.onDialogYes();
            }
        });

        return view;
    }
}
