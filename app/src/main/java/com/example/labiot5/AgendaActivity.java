package com.example.labiot5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AgendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);


    }

    public void goToInsertarActivity(View view){
        startActivity(new Intent(AgendaActivity.this, InsertarActivity.class));
    }
}