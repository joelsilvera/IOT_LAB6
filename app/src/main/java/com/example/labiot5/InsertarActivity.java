package com.example.labiot5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

public class InsertarActivity extends AppCompatActivity {


    EditText fecha;
    EditText horainicio;
    EditText horafin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        fecha = findViewById(R.id.editTextDate);
        horainicio = findViewById(R.id.editTextHoraInicio);
        horafin = findViewById(R.id.editTextHoraFin);
        fecha.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.editTextDate:
                    showDatePickerDialog();
                    break;
            }
        }
    });

    }

    private void showDatePickerDialog(){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                fecha.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

}

