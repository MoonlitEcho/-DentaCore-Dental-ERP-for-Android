package com.dentacore.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dentacore.models.Patient;
import java.util.List;

@Dao
public interface PatientDao {
    @Insert long insert(Patient p);
    @Update void update(Patient p);
    @Delete void delete(Patient p);
    @Query("SELECT * FROM patients ORDER BY updatedAt DESC") LiveData<List<Patient>> getAll();
    @Query("SELECT * FROM patients WHERE id=:id") Patient getById(int id);
    @Query("SELECT * FROM patients WHERE name LIKE '%'||:q||'%' OR phone LIKE '%'||:q||'%' ORDER BY name ASC") List<Patient> search(String q);
    @Query("SELECT COUNT(*) FROM patients") int count();
    @Query("SELECT COALESCE(SUM(totalBilled), 0.0) FROM patients") double totalRevenue();
    @Query("SELECT COALESCE(SUM(totalPaid), 0.0) FROM patients") double totalCollected();
}
