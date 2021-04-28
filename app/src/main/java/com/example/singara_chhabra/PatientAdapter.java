package com.example.singara_chhabra;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PatientAdapter extends ArrayAdapter<Patient> {

    private Activity context;
    private List<Patient> PatientList;

    public PatientAdapter(Activity context, List<Patient> PatientList) {
        super(context, R.layout.list_layout, PatientList);
        this.context = context;
        this.PatientList = PatientList;
    }

    public PatientAdapter(Context context, int resource, List<Patient> objects, Activity context1, List<Patient> studentList) {
        super(context, resource, objects);
        this.context = context1;
        this.PatientList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvName = listViewItem.findViewById(R.id.textView22);
        TextView tvName1 = listViewItem.findViewById(R.id.textView24);
        TextView tvName2 = listViewItem.findViewById(R.id.textView26);
        TextView tvName3 = listViewItem.findViewById(R.id.textView28);

        Patient p = PatientList.get(position);
        tvName.setText(p.getName());
        tvName1.setText(p.getSystolic() + "");
        tvName2.setText(p.getDiastolic() + "");
        tvName3.setText(p.getType());

        return listViewItem;
    }
}
