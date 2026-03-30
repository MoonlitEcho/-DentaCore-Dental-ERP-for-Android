package com.dentacore.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.dentacore.R;
import com.dentacore.database.AppDatabase;
import com.dentacore.models.Patient;

public class AddPatientActivity extends AppCompatActivity {
    private AppDatabase db;
    private Patient existingPatient;
    private EditText etName, etPhone, etEmail, etDob, etAddress, etAllergies, etMedHistory;
    private Spinner spGender, spBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etDob = findViewById(R.id.etDob);
        etAddress = findViewById(R.id.etAddress);
        etAllergies = findViewById(R.id.etAllergies);
        etMedHistory = findViewById(R.id.etMedHistory);
        spGender = findViewById(R.id.spGender);
        spBlood = findViewById(R.id.spBlood);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item,
            new String[]{"Male", "Female", "Other"});
        spGender.setAdapter(genderAdapter);

        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item,
            new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        spBlood.setAdapter(bloodAdapter);

        int patientId = getIntent().getIntExtra("patientId", -1);
        if (patientId != -1) {
            getSupportActionBar().setTitle("Edit Patient");
            new Thread(() -> {
                existingPatient = db.patientDao().getById(patientId);
                runOnUiThread(this::populateFields);
            }).start();
        } else {
            getSupportActionBar().setTitle("New Patient");
        }

        findViewById(R.id.btnSavePatient).setOnClickListener(v -> savePatient());
    }

    private void populateFields() {
        etName.setText(existingPatient.name);
        etPhone.setText(existingPatient.phone);
        etEmail.setText(existingPatient.email);
        etDob.setText(existingPatient.dob);
        etAddress.setText(existingPatient.address);
        etAllergies.setText(existingPatient.allergies);
        etMedHistory.setText(existingPatient.medicalHistory);
    }

    private void savePatient() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        if (name.isEmpty()) { etName.setError("Name is required"); return; }
        if (phone.isEmpty()) { etPhone.setError("Phone is required"); return; }

        Patient p = existingPatient != null ? existingPatient : new Patient();
        p.name = name; p.phone = phone; p.email = etEmail.getText().toString();
        p.dob = etDob.getText().toString(); p.address = etAddress.getText().toString();
        p.allergies = etAllergies.getText().toString(); p.medicalHistory = etMedHistory.getText().toString();
        p.gender = spGender.getSelectedItem().toString(); p.bloodGroup = spBlood.getSelectedItem().toString();
        p.updatedAt = System.currentTimeMillis();

        new Thread(() -> {
            if (existingPatient != null) db.patientDao().update(p);
            else db.patientDao().insert(p);
            runOnUiThread(() -> { Toast.makeText(this, "Patient saved!", Toast.LENGTH_SHORT).show(); finish(); });
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
