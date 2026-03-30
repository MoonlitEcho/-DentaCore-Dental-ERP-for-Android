package com.dentacore.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dentacore.R;
import com.dentacore.adapters.PrescriptionAdapter;
import com.dentacore.database.AppDatabase;
import com.dentacore.models.Patient;
import com.dentacore.models.Prescription;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PatientDetailActivity extends AppCompatActivity {
    private AppDatabase db;
    private Patient patient;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private boolean isRecording = false;
    private String voiceFilePath;
    private Button btnRecord, btnPlay;
    private TextView tvName, tvPhone, tvDob, tvGender, tvBlood, tvAllergies;
    private TextView tvTotalBilled, tvTotalPaid, tvBalance;
    private RecyclerView rvPrescriptions;
    private PrescriptionAdapter prescAdapter;
    private ChipGroup chipGroupTeeth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);
        int patientId = getIntent().getIntExtra("patientId", -1);

        new Thread(() -> {
            patient = db.patientDao().getById(patientId);
            runOnUiThread(this::populateUI);
        }).start();

        rvPrescriptions = findViewById(R.id.rvPrescriptions);
        prescAdapter = new PrescriptionAdapter(prescription -> {
            Intent i = new Intent(this, PrescriptionActivity.class);
            i.putExtra("prescriptionId", prescription.id);
            i.putExtra("patientId", patientId);
            startActivity(i);
        });
        rvPrescriptions.setLayoutManager(new LinearLayoutManager(this));
        rvPrescriptions.setAdapter(prescAdapter);

        db.prescriptionDao().getForPatient(patientId).observe(this, prescriptions ->
            prescAdapter.setPrescriptions(prescriptions));

        findViewById(R.id.btnNewPrescription).setOnClickListener(v -> {
            Intent i = new Intent(this, PrescriptionActivity.class);
            i.putExtra("patientId", patientId);
            startActivity(i);
        });

        findViewById(R.id.btnEditPatient).setOnClickListener(v -> {
            Intent i = new Intent(this, AddPatientActivity.class);
            i.putExtra("patientId", patientId);
            startActivity(i);
        });

        btnRecord = findViewById(R.id.btnRecord);
        btnPlay = findViewById(R.id.btnPlay);
        chipGroupTeeth = findViewById(R.id.chipGroupTeeth);
        setupVoiceControls();
    }

    private void populateUI() {
        if (patient == null) return;
        getSupportActionBar().setTitle(patient.name);
        ((TextView) findViewById(R.id.tvName)).setText(patient.name);
        ((TextView) findViewById(R.id.tvPhone)).setText(patient.phone);
        ((TextView) findViewById(R.id.tvDob)).setText("DOB: " + (patient.dob != null ? patient.dob : "N/A"));
        ((TextView) findViewById(R.id.tvGender)).setText(patient.gender != null ? patient.gender : "N/A");
        ((TextView) findViewById(R.id.tvBlood)).setText("Blood: " + (patient.bloodGroup != null ? patient.bloodGroup : "N/A"));
        ((TextView) findViewById(R.id.tvAllergies)).setText("Allergies: " + (patient.allergies != null ? patient.allergies : "None"));
        ((TextView) findViewById(R.id.tvTotalBilled)).setText("₹" + String.format("%.0f", patient.totalBilled));
        ((TextView) findViewById(R.id.tvTotalPaid)).setText("₹" + String.format("%.0f", patient.totalPaid));
        ((TextView) findViewById(R.id.tvBalance)).setText(patient.getBalance());

        // Render affected teeth as chips
        chipGroupTeeth.removeAllViews();
        if (patient.affectedTeeth != null) {
            for (int tooth : patient.affectedTeeth) {
                Chip chip = new Chip(this);
                chip.setText("Tooth #" + tooth);
                chip.setCheckable(false);
                chipGroupTeeth.addView(chip);
            }
        }

        voiceFilePath = patient.voiceNoteFilePath;
        btnPlay.setEnabled(voiceFilePath != null && new File(voiceFilePath).exists());
    }

    private void setupVoiceControls() {
        btnRecord.setOnClickListener(v -> {
            if (isRecording) stopRecording();
            else startRecording();
        });

        btnPlay.setOnClickListener(v -> playVoiceNote());
    }

    private void startRecording() {
        voiceFilePath = getExternalFilesDir(null) + "/voice_" + patient.id + "_" +
                System.currentTimeMillis() + ".3gp";
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(voiceFilePath);
        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
            btnRecord.setText("⏹ Stop Recording");
            btnRecord.setBackgroundTintList(getColorStateList(R.color.danger));
        } catch (IOException e) {
            Toast.makeText(this, "Recording failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop(); recorder.release(); recorder = null;
        }
        isRecording = false;
        btnRecord.setText("🎙 Record Voice Note");
        btnRecord.setBackgroundTintList(getColorStateList(R.color.accent));
        btnPlay.setEnabled(true);
        // Save path to patient
        patient.voiceNoteFilePath = voiceFilePath;
        new Thread(() -> db.patientDao().update(patient)).start();
        Toast.makeText(this, "Voice note saved!", Toast.LENGTH_SHORT).show();
    }

    private void playVoiceNote() {
        if (voiceFilePath == null) return;
        if (player != null) { player.release(); player = null; }
        player = new MediaPlayer();
        try {
            player.setDataSource(voiceFilePath);
            player.prepare();
            player.start();
            btnPlay.setText("▶ Playing...");
            player.setOnCompletionListener(mp -> btnPlay.setText("▶ Play Voice Note"));
        } catch (IOException e) {
            Toast.makeText(this, "Playback failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isRecording) stopRecording();
        if (player != null) { player.release(); player = null; }
    }
}
