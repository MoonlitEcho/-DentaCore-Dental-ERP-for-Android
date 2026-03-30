package com.dentacore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dentacore.R;
import com.dentacore.adapters.PatientAdapter;
import com.dentacore.database.AppDatabase;
import com.dentacore.models.Patient;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PatientAdapter adapter;
    private AppDatabase db;
    private TextView tvPatientCount, tvRevenue, tvDue;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerPatients);
        etSearch = findViewById(R.id.etSearch);
        tvPatientCount = findViewById(R.id.tvPatientCount);
        tvRevenue = findViewById(R.id.tvRevenue);
        tvDue = findViewById(R.id.tvDue);

        adapter = new PatientAdapter(new ArrayList<>(), patient -> {
            Intent i = new Intent(this, PatientDetailActivity.class);
            i.putExtra("patientId", patient.id);
            startActivity(i);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        db.patientDao().getAll().observe(this, patients -> {
            adapter.setPatients(patients);
            updateStats(patients);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void onTextChanged(CharSequence s, int st, int b, int c) { filterPatients(s.toString()); }
            public void afterTextChanged(Editable s) {}
        });

        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fabAddPatient);
        fabAdd.setOnClickListener(v -> startActivity(new Intent(this, AddPatientActivity.class)));

        FloatingActionButton fabAnalytics = findViewById(R.id.fabAnalytics);
        fabAnalytics.setOnClickListener(v -> startActivity(new Intent(this, AnalyticsActivity.class)));
    }

    private void updateStats(List<Patient> patients) {
        tvPatientCount.setText(String.valueOf(patients.size()));
        double rev = 0, due = 0;
        for (Patient p : patients) { rev += p.totalPaid; due += (p.totalBilled - p.totalPaid); }
        tvRevenue.setText("₹" + String.format("%.0f", rev));
        tvDue.setText("₹" + String.format("%.0f", Math.max(0, due)));
    }

    private void filterPatients(String query) {
        new Thread(() -> {
            List<Patient> results = query.isEmpty() ? null : db.patientDao().search(query);
            runOnUiThread(() -> {
                if (results != null) adapter.setPatients(results);
            });
        }).start();
    }
}
