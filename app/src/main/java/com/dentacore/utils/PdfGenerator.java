package com.dentacore.utils;

import android.content.Context;
import com.dentacore.models.Medication;
import com.dentacore.models.Patient;
import com.dentacore.models.Prescription;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PdfGenerator {
    public static File generate(Context ctx, Prescription p, Patient patient) throws Exception {
        File folder = ctx.getExternalFilesDir(null);
        if (folder != null && !folder.exists()) folder.mkdirs();

        String fileName = "Rx_" + (patient != null ? patient.name.replaceAll("\\s+", "_") : "Patient") + "_" + System.currentTimeMillis() + ".pdf";
        File out = new File(folder, fileName);

        PdfWriter writer = new PdfWriter(new FileOutputStream(out));
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf, PageSize.A4);

        Color accent = new DeviceRgb(0, 212, 255);
        Color white = new DeviceRgb(226, 232, 240);
        Color grey = new DeviceRgb(100, 116, 139);
        Color surface = new DeviceRgb(26, 34, 54);

        // Header
        doc.add(new Paragraph()
                .add(tx(p.clinicName != null ? p.clinicName : "DentaCore Clinic", accent, 22, true))
                .add(tx("\n" + (p.doctorName != null ? p.doctorName : "") + "\n", white, 14, false))
                .add(tx("PRESCRIPTION\n", grey, 11, false)));

        doc.add(new LineSeparator(new SolidLine(1)));

        // Patient Info
        doc.add(new Paragraph()
                .add(tx("Patient: ", grey, 11, true))
                .add(tx(p.patientName, white, 11, false))
                .add(tx("   Date: ", grey, 11, true))
                .add(tx(new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date(p.createdAt)), white, 11, false)));

        if (patient != null) {
            doc.add(new Paragraph()
                    .add(tx("Phone: ", grey, 11, true))
                    .add(tx(patient.phone, white, 11, false))
                    .add(tx("  Blood: ", grey, 11, true))
                    .add(tx(patient.bloodGroup != null ? patient.bloodGroup : "N/A", white, 11, false)));
        }

        if (p.diagnosis != null && !p.diagnosis.isEmpty()) {
            doc.add(sp());
            doc.add(sh("DIAGNOSIS", accent));
            doc.add(new Paragraph(p.diagnosis).setFontColor(white).setFontSize(11));
        }

        if (p.treatedTeeth != null && !p.treatedTeeth.isEmpty()) {
            doc.add(sp());
            doc.add(sh("TREATED TEETH (FDI)", accent));
            doc.add(new Paragraph("Teeth: " + p.treatedTeeth.toString().replace("[", "").replace("]", "")).setFontColor(white).setFontSize(11));
        }

        // Medications Table
        if (p.medications != null && !p.medications.isEmpty()) {
            doc.add(sp());
            doc.add(sh("Rx — MEDICATIONS", accent));
            Table t = new Table(new float[]{3, 2, 2, 2, 3}).useAllAvailableWidth();
            String[] headers = {"Medicine", "Dosage", "Frequency", "Duration", "Instructions"};
            for (String h : headers) {
                t.addHeaderCell(new Cell().add(new Paragraph(h).setBold().setFontColor(accent).setFontSize(10)).setBackgroundColor(surface));
            }
            for (Medication m : p.medications) {
                t.addCell(new Cell().add(new Paragraph(m.name != null ? m.name : "").setFontColor(white).setFontSize(10)));
                t.addCell(new Cell().add(new Paragraph(m.dosage != null ? m.dosage : "").setFontColor(white).setFontSize(10)));
                t.addCell(new Cell().add(new Paragraph(m.frequency != null ? m.frequency : "").setFontColor(white).setFontSize(10)));
                t.addCell(new Cell().add(new Paragraph(m.duration != null ? m.duration : "").setFontColor(white).setFontSize(10)));
                t.addCell(new Cell().add(new Paragraph(m.instructions != null ? m.instructions : "").setFontColor(white).setFontSize(10)));
            }
            doc.add(t);
        }

        if (p.notes != null && !p.notes.isEmpty()) {
            doc.add(sp());
            doc.add(sh("NOTES", accent));
            doc.add(new Paragraph(p.notes).setFontColor(white).setFontSize(11));
        }

        // Billing
        doc.add(sp());
        doc.add(sh("BILLING", accent));
        doc.add(new Paragraph().add(tx("Procedure: ", grey, 11, true)).add(tx(p.procedureType != null ? p.procedureType : "General", white, 11, false)));
        doc.add(new Paragraph().add(tx("Total: Rs." + String.format(Locale.getDefault(), "%.0f", p.billAmount) + "   Paid: Rs." + String.format(Locale.getDefault(), "%.0f", p.paidAmount) + "   Balance: Rs." + String.format(Locale.getDefault(), "%.0f", p.billAmount - p.paidAmount), white, 11, false)));
        doc.add(new Paragraph().add(tx("Payment Mode: ", grey, 11, true)).add(tx(p.paymentMode != null ? p.paymentMode : "Cash", white, 11, false)));

        doc.add(sp());
        doc.add(new Paragraph("-- Generated by DentaCore ERP --")
                .setFontColor(grey)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER));

        doc.close();
        return out;
    }

    private static Text tx(String s, Color c, int sz, boolean b) {
        Text t = new Text(s != null ? s : "").setFontColor(c).setFontSize(sz);
        if (b) t.setBold();
        return t;
    }

    private static Paragraph sh(String ti, Color c) {
        return new Paragraph(ti).setBold().setFontColor(c).setFontSize(13);
    }

    private static Paragraph sp() {
        return new Paragraph(" ").setFontSize(6);
    }
}
