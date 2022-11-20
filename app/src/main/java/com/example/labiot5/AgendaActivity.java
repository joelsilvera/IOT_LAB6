package com.example.labiot5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.labiot5.Adapter.ActividadAdapter;
import com.example.labiot5.Entity.Actividad;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    boolean primeraVez = true;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        setTitle("Mi agenda");
        ArrayList<Actividad> listaActividades = new ArrayList<>();

        Actividad actividad1 = new Actividad("Prueba1","Hiiiiiiiiiiiiiii","https://lh5.googleusercontent.com/proxy/SVUzogS-EV4t9SPFU3dqJEOWXn5qV3WGXXV2dECHSXs4-pxtXq8HVKhiofRnIj-3ufx1wL2u-n8_fwbM-7foct9-okXwasW1gmfmyw5a0EqbsrDdAzoyZ2QFnGTTPniIhYJYAvUrCBBgL2Wy41V1jomWzOUY7REinVBtynTa0HEqH41Co4_013M-70KQhP_KIh5rBfHI44K3eA=w1200-h630-p-k-no-nu","21/12/2022","8:00","9:00");
        Actividad actividad2 = new Actividad("Prueba2","Hiiiiiiiiiiiiiii","https://lh5.googleusercontent.com/proxy/SVUzogS-EV4t9SPFU3dqJEOWXn5qV3WGXXV2dECHSXs4-pxtXq8HVKhiofRnIj-3ufx1wL2u-n8_fwbM-7foct9-okXwasW1gmfmyw5a0EqbsrDdAzoyZ2QFnGTTPniIhYJYAvUrCBBgL2Wy41V1jomWzOUY7REinVBtynTa0HEqH41Co4_013M-70KQhP_KIh5rBfHI44K3eA=w1200-h630-p-k-no-nu","21/12/2022","8:00","9:00");
        Actividad actividad3 = new Actividad("Prueba3","Hiiiiiiiiiiiiiii","https://lh5.googleusercontent.com/proxy/SVUzogS-EV4t9SPFU3dqJEOWXn5qV3WGXXV2dECHSXs4-pxtXq8HVKhiofRnIj-3ufx1wL2u-n8_fwbM-7foct9-okXwasW1gmfmyw5a0EqbsrDdAzoyZ2QFnGTTPniIhYJYAvUrCBBgL2Wy41V1jomWzOUY7REinVBtynTa0HEqH41Co4_013M-70KQhP_KIh5rBfHI44K3eA=w1200-h630-p-k-no-nu","21/12/2022","8:00","9:00");


        listaActividades.add(actividad1);
        listaActividades.add(actividad2);
        listaActividades.add(actividad3);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAct);
        ActividadAdapter actividadAdapter = new ActividadAdapter(listaActividades);
        recyclerView.setAdapter(actividadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AgendaActivity.this));

        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference ref = firebaseDatabase.getReference().child("actividad");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (primeraVez){
                    primeraVez =false;
                }
                Actividad actividad = snapshot.getValue(Actividad.class);
                listaActividades.add(actividad);
                actividadAdapter.notifyItemInserted(listaActividades.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void goToInsertarActivity(View view){
        startActivity(new Intent(AgendaActivity.this, InsertarActivity.class));
    }
}