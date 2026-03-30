package com.dentacore.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import com.dentacore.R;
import com.dentacore.database.AppDatabase;
import com.dentacore.models.Medication;
import com.dentacore.models.Patient;
import com.dentacore.models.Prescription;
import com.dentacore.utils.PdfGenerator;
import java.io.File;
import java.util.*;

public class PrescriptionActivity extends AppCompatActivity {
    private AppDatabase db;
    private Prescription prescription;
    private Patient patient;
    private int patientId;
    private List<Medication> medications = new ArrayList<>();
    private List<Integer> treatedTeeth = new ArrayList<>();
    private LinearLayout llMedications;
    private EditText etDiagnosis, etNotes, etDoctorName, etClinicName, etBillAmount, etPaidAmount, etProcedure;
    private Spinner spPaymentMode;
    private TextView tvTeethSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);
        patientId = getIntent().getIntExtra("patientId", -1);
        int prescriptionId = getIntent().getIntExtra("prescriptionId", -1);

        etDiagnosis = findViewById(R.id.etDiagnosis);
        etNotes = findViewById(R.id.etNotes);
        etDoctorName = findViewById(R.id.etDoctorName);
        etClinicName = findViewById(R.id.etClinicName);
        etBillAmount = findViewById(R.id.etBillAmount);
        etPaidAmount = findViewById(R.id.etPaidAmount);
        etProcedure = findViewById(R.id.etProcedure);
        llMedications = findViewById(R.id.llMedications);
        tvTeethSelected = findViewById(R.id.tvTeethSelected);
        spPaymentMode = findViewById(R.id.spPaymentMode);

        ArrayAdapter<String> payAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item,
            new String[]{"Cash", "UPI", "Card", "Insurance", "Pending"});
        spPaymentMode.setAdapter(payAdapter);

        new Thread(() -> {
            patient = db.patientDao().getById(patientId);
            if (prescriptionId != -1) {
                prescription = db.prescriptionDao().getById(prescriptionId);
            }
            runOnUiThread(this::initUI);
        }).start();

        findViewById(R.id.btnAddMedication).setOnClickListener(v -> showAddMedicationDialog());
        findViewById(R.id.btnSelectTeeth).setOnClickListener(v -> showTeethSelector());
        findViewById(R.id.btnSavePrescription).setOnClickListener(v -> savePrescription(false));
        findViewById(R.id.btnDownloadPdf).setOnClickListener(v -> generateAndSharePdf());
    }

    private void initUI() {
        if (patient != null && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(patient.name + "'s Prescription");
            etDoctorName.setText("Dr. ");
            etClinicName.setText("DentaCore Clinic");
        }
        if (prescription != null) {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("View Prescription");
            etDiagnosis.setText(prescription.diagnosis);
            etNotes.setText(prescription.notes);
            etDoctorName.setText(prescription.doctorName);
            etClinicName.setText(prescription.clinicName);
            etBillAmount.setText(String.valueOf(prescription.billAmount));
            etPaidAmount.setText(String.valueOf(prescription.paidAmount));
            etProcedure.setText(prescription.procedureType);
            medications = prescription.medications != null ? prescription.medications : new ArrayList<>();
            treatedTeeth = prescription.treatedTeeth != null ? prescription.treatedTeeth : new ArrayList<>();
            renderMedications();
            updateTeethLabel();
        }
    }

    private void showAddMedicationDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_medication, null);
        EditText etMedName = dialogView.findViewById(R.id.etMedName);
        EditText etDosage = dialogView.findViewById(R.id.etDosage);
        EditText etFreq = dialogView.findViewById(R.id.etFrequency);
        EditText etDuration = dialogView.findViewById(R.id.etDuration);
        EditText etInstructions = dialogView.findViewById(R.id.etInstructions);

        new AlertDialog.Builder(this, R.style.DarkDialog)
            .setTitle("Add Medication")
            .setView(dialogView)
            .setPositiveButton("Add", (d, w) -> {
                Medication med = new Medication(
                    etMedName.getText().toString(),
                    etDosage.getText().toString(),
                    etFreq.getText().toString(),
                    etDuration.getText().toString(),
                    etInstructions.getText().toString());
                medications.add(med);
                renderMedications();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void renderMedications() {
        llMedications.removeAllViews();
        for (int i = 0; i < medications.size(); i++) {
            Medication med = medications.get(i);
            View row = LayoutInflater.from(this).inflate(R.layout.item_medication_row, llMedications, false);
            ((TextView) row.findViewById(R.id.tvMedName)).setText(med.name);
            ((TextView) row.findViewById(R.id.tvMedDetail)).setText(
                med.dosage + " | " + med.frequency + " | " + med.duration);
            int finalI = i;
            row.findViewById(R.id.btnRemoveMed).setOnClickListener(v -> {
                medications.remove(finalI); renderMedications();
            });
            llMedications.addView(row);
        }
    }

    private void showTeethSelector() {
        String[] teeth = new String[32];
        boolean[] checked = new boolean[32];
        int[] toothNumbers = {18,17,16,15,14,13,12,11,21,22,23,24,25,26,27,28,
                               48,47,46,45,44,43,42,41,31,32,33,34,35,36,37,38};
        for (int i = 0; i < 32; i++) {
            teeth[i] = "Tooth " + toothNumbers[i];
            checked[i] = treatedTeeth.contains(toothNumbers[i]);
        }
        new AlertDialog.Builder(this, R.style.DarkDialog)
            .setTitle("Select Treated Teeth")
            .setMultiChoiceItems(teeth, checked, (dialog, which, isChecked) -> {
                if (isChecked) { if (!treatedTeeth.contains(toothNumbers[which])) treatedTeeth.add(toothNumbers[which]); }
                else treatedTeeth.remove(Integer.valueOf(toothNumbers[which]));
            })
            .setPositiveButton("Done", (d, w) -> updateTeethLabel())
            .show();
    }

    private void updateTeethLabel() {
        if (treatedTeeth.isEmpty()) tvTeethSelected.setText("No teeth selected");
        else tvTeethSelected.setText("Teeth: " + treatedTeeth.toString().replace("[","").replace("]",""));
    }

    private void savePrescription(boolean silent) {
        Prescription p = prescription != null ? prescription : new Prescription();
        p.patientId = patientId;
        p.patientName = patient != null ? patient.name : "";
        p.doctorName = etDoctorName.getText().toString();
        p.clinicName = etClinicName.getText().toString();
        p.diagnosis = etDiagnosis.getText().toString();
        p.notes = etNotes.getText().toString();
        p.medications = medications;
        p.treatedTeeth = treatedTeeth;
        p.procedureType = etProcedure.getText().toString();
        p.paymentMode = spPaymentMode.getSelectedItem().toString();
        try { p.billAmount = Double.parseDouble(etBillAmount.getText().toString()); } catch (Exception e) { p.billAmount = 0; }
        try { p.paidAmount = Double.parseDouble(etPaidAmount.getText().toString()); } catch (Exception e) { p.paidAmount = 0; }

        new Thread(() -> {
            if (prescription != null) db.prescriptionDao().update(p);
            else db.prescriptionDao().insert(p);

            if (patient != null) {
                patient.totalBilled += p.billAmount;
                patient.totalPaid += p.paidAmount;
                if (treatedTeeth != null) {
                    for (int t : treatedTeeth) {
                        if (patient.affectedTeeth == null) patient.affectedTeeth = new ArrayList<>();
                        if (!patient.affectedTeeth.contains(t)) patient.affectedTeeth.add(t);
                    }
                }
                patient.updatedAt = System.currentTimeMillis();
                db.patientDao().update(patient);
            }
            if (!silent) {
                runOnUiThread(() -> Toast.makeText(this, "Prescription saved!", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void generateAndSharePdf() {
        savePrescription(true);
        
        Prescription pForPdf = new Prescription();
        pForPdf.patientName = patient != null ? patient.name : "";
        pForPdf.doctorName = etDoctorName.getText().toString();
        pForPdf.clinicName = etClinicName.getText().toString();
        pForPdf.diagnosis = etDiagnosis.getText().toString();
        pForPdf.notes = etNotes.getText().toString();
        pForPdf.medications = new ArrayList<>(medications);
        pForPdf.treatedTeeth = new ArrayList<>(treatedTeeth);
        try { pForPdf.billAmount = Double.parseDouble(etBillAmount.getText().toString()); } catch (Exception e) { pForPdf.billAmount = 0; }
        try { pForPdf.paidAmount = Double.parseDouble(etPaidAmount.getText().toString()); } catch (Exception e) { pForPdf.paidAmount = 0; }
        pForPdf.paymentMode = spPaymentMode.getSelectedItem().toString();
        pForPdf.createdAt = System.currentTimeMillis();
        pForPdf.procedureType = etProcedure.getText().toString();

        new Thread(() -> {
            try {
                File pdfFile = PdfGenerator.generate(this, pForPdf, patient);
                runOnUiThread(() -> {
                    Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("application/pdf");
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(share, "Share Prescription"));
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "PDF error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
