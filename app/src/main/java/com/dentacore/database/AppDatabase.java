package com.dentacore.database;
import android.content.Context;
import androidx.room.*;
import com.dentacore.models.Patient;
import com.dentacore.models.Prescription;
@Database(entities={Patient.class,Prescription.class},version=1,exportSchema=false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract PatientDao patientDao();
    public abstract PrescriptionDao prescriptionDao();
    public static synchronized AppDatabase getInstance(Context ctx){
        if(instance==null) instance=Room.databaseBuilder(ctx.getApplicationContext(),AppDatabase.class,"dentacore_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return instance;
    }
}
