package com.dentacore.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.dentacore.database.Converters;
import java.util.ArrayList;
import java.util.List;
@Entity(tableName="patients")
@TypeConverters(Converters.class)
public class Patient {
    @PrimaryKey(autoGenerate=true) public int id;
    public String name,phone,email,dob,gender,address,bloodGroup,allergies,medicalHistory,voiceNoteFilePath;
    public long createdAt,updatedAt;
    public List<Integer> affectedTeeth=new ArrayList<>();
    public double totalBilled=0,totalPaid=0;
    public Patient(){createdAt=System.currentTimeMillis();updatedAt=createdAt;}
    public String getBalance(){double b=totalBilled-totalPaid;return b>0?"Rs."+String.format("%.0f",b)+" due":"Paid";}
}
