package com.example.labiot5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InsertarActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    StorageReference storageRef;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    final static LocalTime HORA_MIN = LocalTime.of(6,0);
    final static LocalTime HORA_MAX = LocalTime.of(23,30);

    EditText etFecha;
    EditText etHorainicio;
    EditText etHorafin;
    ImageView imageView;

    LocalTime timeInicio;
    LocalTime timeFin;
    LocalDate selectedLocalDate;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        firebaseAuth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());

        MaterialTimePicker pickerHoraInicio = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(6)
                .setMinute(0)
                .setTitleText("Selecciona la hora de inicio")
                .build();
        pickerHoraInicio.addOnCancelListener(dialogInterface -> {
            timeInicio = null;
            etHorainicio.setText("");
        });
        pickerHoraInicio.addOnNegativeButtonClickListener(dialogInterface -> {
            timeInicio = null;
            etHorainicio.setText("");
        });
        pickerHoraInicio.addOnPositiveButtonClickListener(dialogInterface -> {
            timeInicio = LocalTime.of(pickerHoraInicio.getHour(),pickerHoraInicio.getMinute());
            if(timeInicio.isBefore(HORA_MIN) || timeInicio.isAfter(HORA_MAX)){
                Toast.makeText(this, "Solo se pueden agendar actividades entre las 6:00 am y las 11:30 pm", Toast.LENGTH_SHORT).show();
                timeInicio = null;
                etHorainicio.setText("");
                return;
            }
            if(selectedLocalDate.isEqual(LocalDate.now()) && !timeInicio.isAfter(LocalTime.now())){
                Toast.makeText(this, "Ingresa una hora posterior a la actual", Toast.LENGTH_SHORT).show();
                timeInicio = null;
                etHorainicio.setText("");
                return;
            }
            if (timeFin!=null && timeInicio.isAfter(timeFin)){
                Toast.makeText(this, "La hora de inicio no puede ser posterior a la hora fin", Toast.LENGTH_SHORT).show();
                timeInicio = null;
                etHorainicio.setText("");
                return;
            }
            etHorainicio.setText(timeInicio.format(timeFormatter));
        });

        MaterialTimePicker pickerHoraFin= new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(23)
                .setMinute(30)
                .setTitleText("Selecciona la hora de fin")
                .build();
        pickerHoraFin.addOnCancelListener(dialogInterface -> {
            timeFin = null;
            etHorafin.setText("");
        });
        pickerHoraFin.addOnNegativeButtonClickListener(dialogInterface -> {
            timeFin = null;
            etHorafin.setText("");
        });
        pickerHoraFin.addOnPositiveButtonClickListener(dialogInterface -> {
            timeFin = LocalTime.of(pickerHoraFin.getHour(),pickerHoraFin.getMinute());
            if(timeFin.isBefore(HORA_MIN) || timeFin.isAfter(HORA_MAX)){
                Toast.makeText(this, "Solo se pueden agendar actividades entre las 6:00 am y las 11:30 pm", Toast.LENGTH_SHORT).show();
                timeFin = null;
                etHorafin.setText("");
                return;
            }
            if(selectedLocalDate.isEqual(LocalDate.now()) && !timeFin.isAfter(LocalTime.now())){
                Toast.makeText(this, "Ingresa una hora posterior a la actual", Toast.LENGTH_SHORT).show();
                timeFin = null;
                etHorafin.setText("");
                return;
            }
            if (timeInicio!=null && timeFin.isBefore(timeInicio)){
                Toast.makeText(this, "La hora de fin debe ser posterior a la hora de inicio", Toast.LENGTH_SHORT).show();
                timeFin = null;
                etHorafin.setText("");
                return;
            }
            etHorafin.setText(timeFin.format(timeFormatter));
        });

        etFecha = findViewById(R.id.editTextDate);
        etHorainicio = findViewById(R.id.editTextHoraInicio);
        etHorafin = findViewById(R.id.editTextHoraFin);
        imageView = findViewById(R.id.ivInsertar);
        etFecha.setOnClickListener(view -> showDatePickerDialog());
        etHorainicio.setOnClickListener(view -> {
            if(etFecha.getText().toString().isEmpty()){
                etFecha.setError("Indica una fecha");
                Toast.makeText(InsertarActivity.this, "Inidica una fecha", Toast.LENGTH_SHORT).show();
                return;
            }
            pickerHoraInicio.show(getSupportFragmentManager(),"HoraInicio");
        });
        etHorafin.setOnClickListener(view -> {
            if(etFecha.getText().toString().isEmpty()){
                etFecha.setError("Indica una fecha");
                Toast.makeText(InsertarActivity.this, "Indica una fecha", Toast.LENGTH_SHORT).show();
                return;
            }
            pickerHoraFin.show(getSupportFragmentManager(),"HoraFin");
        });



    }

    private void showDatePickerDialog(){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                selectedLocalDate = LocalDate.parse(selectedDate, dateFormatter);
                if (!selectedLocalDate.isBefore(LocalDate.now())){
                    etFecha.setText(selectedDate);
                    etFecha.setError(null);
                }else{
                    etFecha.setText("");
                    Toast.makeText(InsertarActivity.this, "Ingresa una fecha posterior a la actual", Toast.LENGTH_SHORT).show();
                }
            }
        });
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }


    public void subirImagen(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        launcherPhotos.launch(intent);
    }

    ActivityResultLauncher<Intent> launcherPhotos = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    StorageReference child = storageRef.child(uri.getLastPathSegment());

                    child.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            child.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                imageUrl = uri1.toString();
                                Log.d("msg-test", "ruta archivo: " + imageUrl);
                                updateImageView();
                            }).addOnFailureListener(e -> {
                                imageUrl = "";
                                updateImageView();
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("msg", "Fallo subida",e);
                            imageUrl = "";
                            updateImageView();
                        }
                    });
                } else {
                    Toast.makeText(InsertarActivity.this, "Debe seleccionar un archivo", Toast.LENGTH_SHORT).show();
                    imageUrl = "";
                    updateImageView();
                }
            }
    );

    public void updateImageView(){
        if(!imageUrl.isEmpty()){
            Glide.with(InsertarActivity.this).load(imageUrl).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.placeholder_image);
        }
    }
}

