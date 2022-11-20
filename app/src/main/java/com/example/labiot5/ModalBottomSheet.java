package com.example.labiot5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    EditText etFechaInicio;
    EditText etFechaFin;
    EditText etHoraInicio;
    EditText etHoraFin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_filter, container, false);



        view.findViewById(R.id.bottomsheet_button).setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Aca deberían setearse los filtros", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        return view;
    }

    public String TAG = "ModalBottomSheet";
}
