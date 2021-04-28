package com.example.singara_chhabra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            if (findViewById(R.id.bp_fragment) != null) {
                BPFragment bp_Fragment = new BPFragment();
                fm.beginTransaction().add(R.id.bp_fragment, bp_Fragment).commit();
            }
        }
    }
}
