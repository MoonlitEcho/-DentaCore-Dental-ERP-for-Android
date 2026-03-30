package com.dentacore.models;

public class ToothRecord {
    public int toothNumber;      // 1–32 (Universal numbering)
    public String quadrant;      // UL, UR, LL, LR
    public String condition;     // Cavity, Crown, RCT, Extraction, Filling, etc.
    public String treatment;
    public String notes;
    public String date;
    public double cost;
    public String status;        // Completed, Pending, In-Progress

    public ToothRecord() {}

    public ToothRecord(int toothNumber, String quadrant, String condition,
                       String treatment, String notes, String date,
                       double cost, String status) {
        this.toothNumber = toothNumber;
        this.quadrant = quadrant;
        this.condition = condition;
        this.treatment = treatment;
        this.notes = notes;
        this.date = date;
        this.cost = cost;
        this.status = status;
    }

    public String getDisplayName() {
        return "Tooth #" + toothNumber + " (" + quadrant + ")";
    }
}
