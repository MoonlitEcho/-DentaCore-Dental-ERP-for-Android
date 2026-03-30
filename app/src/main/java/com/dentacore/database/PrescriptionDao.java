package com.dentacore.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dentacore.models.Prescription;
import java.util.List;

@Dao
public interface PrescriptionDao {
    @Insert long insert(Prescription p);
    @Update void update(Prescription p);
    @Delete void delete(Prescription p);

    @Query("SELECT * FROM prescriptions WHERE patientId=:pid ORDER BY createdAt DESC") 
    LiveData<List<Prescription>> getForPatient(int pid);

    @Query("SELECT * FROM prescriptions ORDER BY createdAt DESC") 
    LiveData<List<Prescription>> getAll();

    @Query("SELECT * FROM prescriptions WHERE id=:id") 
    Prescription getById(int id);

    @Query("SELECT COALESCE(SUM(billAmount), 0.0) FROM prescriptions WHERE createdAt>=:from AND createdAt<=:to") 
    double revenueInRange(long from, long to);

    @Query("SELECT COUNT(*) FROM prescriptions WHERE createdAt>=:from AND createdAt<=:to") 
    int countInRange(long from, long to);

    @Query("SELECT * FROM prescriptions ORDER BY createdAt DESC LIMIT 10") 
    List<Prescription> recentPrescriptions();

    @Query("SELECT procedureType, COUNT(*) as cnt FROM prescriptions GROUP BY procedureType") 
    List<ProcedureCount> procedureCounts();
}
