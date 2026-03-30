package com.dentacore.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.dentacore.database.Converters;
import java.util.ArrayList;
import java.util.List;
@Entity(tableName="prescriptions")
@TypeConverters(Converters.class)
public class Prescription {
    @PrimaryKey(autoGenerate=true) public int id;
    public int patientId;
    public String patientName,doctorName,clinicName,diagnosis,notes,paymentMode,procedureType;
    public List<Medication> medications=new ArrayList<>();
    public List<Integer> treatedTeeth=new ArrayList<>();
    public long createdAt;
    public double billAmount,paidAmount;
    public Prescription(){createdAt=System.currentTimeMillis();}
}
