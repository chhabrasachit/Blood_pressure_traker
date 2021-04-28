package com.example.singara_chhabra;

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
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import static com.example.singara_chhabra.BloodPressureResults.mCategories;

public class BPFragment extends Fragment {

    private static final String KEY_CATEGORY = "category";
    private EditText mSystolic;
    private EditText mDiastolic;
    private Button mDiagnoseBtn;
    private TextView mCategoryTextView;
    private TextView dtTextView;
    private EditText patientName;
    private Button mViewEntriesBtn;
    private int mCategory = 0;

    DatabaseReference databasePatients;

    public BPFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bloodpressure, container, false);

        patientName = v.findViewById(R.id.patient_name);
        mSystolic = v.findViewById(R.id.systolic_number);
        mDiastolic = v.findViewById(R.id.diastolic_number);
        mDiagnoseBtn = v.findViewById(R.id.diagnose_button);
        mViewEntriesBtn = v.findViewById(R.id.view_entries);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databasePatients = FirebaseDatabase.getInstance().getReference("Patient");
        mDiagnoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int systolic = Integer.parseInt(mSystolic.getText().toString());
                    int diastolic = Integer.parseInt(mDiastolic.getText().toString());
                    mCategory = BloodPressureResults.bp(systolic, diastolic);
                    mCategoryTextView.setText(mCategories[mCategory]);
                    Date currentTime = Calendar.getInstance().getTime();
                    dtTextView.setText(currentTime.toString());
                    String formattedDate = String.format("%1$tb %1$te, %1$tY %1$tI:%1$tM %1$Tp", currentTime);
                    addStudent(diastolic, systolic, formattedDate);
                    if(mCategory == 4)
                    {
                        VoteDialogFragment dialog = new VoteDialogFragment();
                        dialog.show(getActivity().getSupportFragmentManager(), "NoticeDialogFragment");

                    }



                } catch (RuntimeException rtex) {
                    mCategoryTextView.setText(getResources().getString(R.string.input_error));
                    mCategory = 0;
                }
            }
        });

        mViewEntriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                startActivity(intent);

            }
        });

        mCategoryTextView = v.findViewById(R.id.category_text);
        dtTextView = v.findViewById(R.id.record_time_result);


        if (savedInstanceState != null) {
            mCategory = savedInstanceState.getInt(KEY_CATEGORY, 0);
            if (mCategory > 0) {
                mCategoryTextView.setText(mCategories[mCategory]);
                dtTextView.setText(Calendar.getInstance().getTime().toString());
                //mFollowUpTextView.setText(BloodPressure.mRecommendations[mCategory]);
            }
        }
        return v;
    }


    private void addStudent(float diastolic, float systolic, String date) {
        String Name = patientName.getText().toString().trim();
        String type = mCategories[mCategory];


        String id = databasePatients.push().getKey();
        Patient patient = new Patient(id, Name, diastolic, systolic, type, date);

        Task setValueTask = databasePatients.child(id).setValue(patient);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getActivity(),
                        "Patient added.",Toast.LENGTH_LONG).show();

                patientName.setText("");
                mSystolic.setText("");
                mDiastolic.setText("");

            }
        });


        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CATEGORY, mCategory);
    }

}
