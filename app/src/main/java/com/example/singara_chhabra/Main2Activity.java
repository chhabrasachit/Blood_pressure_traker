package com.example.singara_chhabra;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ListView lvPatients;
    List<Patient> PatientList;
    DatabaseReference databasePatients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_main2);

        lvPatients = findViewById(R.id.lvPatients);
        PatientList = new ArrayList<Patient>();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databasePatients = FirebaseDatabase.getInstance().getReference("Patient");

        lvPatients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Patient p = PatientList.get(position);

                showUpdateDialog(p.getPatientId(),
                        p.getName(),
                        p.getDiastolic(),
                        p.getSystolic(),
                        p.getType());

                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databasePatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PatientList.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Patient p = studentSnapshot.getValue(Patient.class);
                    PatientList.add(p);
                }

                PatientAdapter adapter = new PatientAdapter(Main2Activity.this, PatientList);
                lvPatients.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void updateStudent(String id, String Name, float diastolic, float systolic, String type, String date) {
        DatabaseReference dbRef = databasePatients.child(id);

        Patient p = new Patient(id, Name, diastolic, systolic, type, date);

        Task setValueTask = dbRef.setValue(p);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(Main2Activity.this,
                        "Patient Updated.",Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Main2Activity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateDialog(final String id, String Name, float diastolic, float systolic, String type) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        editTextName.setText(Name);

        final EditText editTextDiastolic = dialogView.findViewById(R.id.editTextDiastolic);
        editTextDiastolic.setText(Float.toString(diastolic));

        final EditText editTextSystolic = dialogView.findViewById(R.id.editTextSystolic);
        editTextSystolic.setText(Float.toString(systolic));

        final Spinner spinnerSchool = dialogView.findViewById(R.id.spinnerSchool);
        spinnerSchool.setSelection(((ArrayAdapter<String>)spinnerSchool.getAdapter()).getPosition(type));

        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        dialogBuilder.setTitle("Update Patient " + Name);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = editTextName.getText().toString().trim();
                float diastolic = Float.parseFloat(editTextDiastolic.getText().toString().trim());
                float systolic = Float.parseFloat(editTextSystolic.getText().toString().trim());
                String school = spinnerSchool.getSelectedItem().toString().trim();
                Date currentTime = Calendar.getInstance().getTime();


                updateStudent(id, Name, diastolic, systolic, school, currentTime.toString());

                alertDialog.dismiss();
            }
        });

        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(id);

                alertDialog.dismiss();
            }
        });
    }

    private void deleteStudent(String id) {
        DatabaseReference dbRef = databasePatients.child(id);

        Task setRemoveTask = dbRef.removeValue();
        setRemoveTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(Main2Activity.this,
                        "Student Deleted.",Toast.LENGTH_LONG).show();
            }
        });

        setRemoveTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Main2Activity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
