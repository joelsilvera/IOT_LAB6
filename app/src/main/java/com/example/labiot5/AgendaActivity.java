package com.example.labiot5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.labiot5.Adapter.ActividadAdapter;
import com.example.labiot5.Entity.Actividad;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;

    private LocalDate filtroFechaInicio = LocalDate.now();
    private LocalDate filtroFechaFin = LocalDate.now();
    private LocalTime filtroHoraInicio = LocalTime.of(6,0);
    private LocalTime filtroHoraFin = LocalTime.of(11,30);

    private ModalBottomSheet modalBottomSheet = new ModalBottomSheet();

    boolean primeraVez = true;
    int i;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.filterMenu:
                Log.d("msg", "llevarlo a filtros");
                modalBottomSheet.show(getSupportFragmentManager(), modalBottomSheet.TAG);
                return true;
            case R.id.logoutMenu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AgendaActivity.this, OnboardingMainActivity.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

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

    }

    public void goToInsertarActivity(View view){
        startActivity(new Intent(AgendaActivity.this, InsertarActivity.class));
    }

    public static class ModalBottomSheet extends BottomSheetDialogFragment {
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


}