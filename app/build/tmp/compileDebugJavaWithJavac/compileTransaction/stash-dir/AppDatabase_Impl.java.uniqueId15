package com.dentacore.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PatientDao _patientDao;

  private volatile PrescriptionDao _prescriptionDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `patients` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `phone` TEXT, `email` TEXT, `dob` TEXT, `gender` TEXT, `address` TEXT, `bloodGroup` TEXT, `allergies` TEXT, `medicalHistory` TEXT, `voiceNoteFilePath` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `affectedTeeth` TEXT, `totalBilled` REAL NOT NULL, `totalPaid` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `prescriptions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `patientId` INTEGER NOT NULL, `patientName` TEXT, `doctorName` TEXT, `clinicName` TEXT, `diagnosis` TEXT, `notes` TEXT, `paymentMode` TEXT, `procedureType` TEXT, `medications` TEXT, `treatedTeeth` TEXT, `createdAt` INTEGER NOT NULL, `billAmount` REAL NOT NULL, `paidAmount` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '231515f3539ece567baf00787dae4903')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `patients`");
        db.execSQL("DROP TABLE IF EXISTS `prescriptions`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPatients = new HashMap<String, TableInfo.Column>(16);
        _columnsPatients.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("dob", new TableInfo.Column("dob", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("gender", new TableInfo.Column("gender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("bloodGroup", new TableInfo.Column("bloodGroup", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("allergies", new TableInfo.Column("allergies", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("medicalHistory", new TableInfo.Column("medicalHistory", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("voiceNoteFilePath", new TableInfo.Column("voiceNoteFilePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("affectedTeeth", new TableInfo.Column("affectedTeeth", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("totalBilled", new TableInfo.Column("totalBilled", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatients.put("totalPaid", new TableInfo.Column("totalPaid", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatients = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatients = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPatients = new TableInfo("patients", _columnsPatients, _foreignKeysPatients, _indicesPatients);
        final TableInfo _existingPatients = TableInfo.read(db, "patients");
        if (!_infoPatients.equals(_existingPatients)) {
          return new RoomOpenHelper.ValidationResult(false, "patients(com.dentacore.models.Patient).\n"
                  + " Expected:\n" + _infoPatients + "\n"
                  + " Found:\n" + _existingPatients);
        }
        final HashMap<String, TableInfo.Column> _columnsPrescriptions = new HashMap<String, TableInfo.Column>(14);
        _columnsPrescriptions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("patientId", new TableInfo.Column("patientId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("patientName", new TableInfo.Column("patientName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("doctorName", new TableInfo.Column("doctorName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("clinicName", new TableInfo.Column("clinicName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("diagnosis", new TableInfo.Column("diagnosis", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("paymentMode", new TableInfo.Column("paymentMode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("procedureType", new TableInfo.Column("procedureType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("medications", new TableInfo.Column("medications", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("treatedTeeth", new TableInfo.Column("treatedTeeth", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("billAmount", new TableInfo.Column("billAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrescriptions.put("paidAmount", new TableInfo.Column("paidAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPrescriptions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPrescriptions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPrescriptions = new TableInfo("prescriptions", _columnsPrescriptions, _foreignKeysPrescriptions, _indicesPrescriptions);
        final TableInfo _existingPrescriptions = TableInfo.read(db, "prescriptions");
        if (!_infoPrescriptions.equals(_existingPrescriptions)) {
          return new RoomOpenHelper.ValidationResult(false, "prescriptions(com.dentacore.models.Prescription).\n"
                  + " Expected:\n" + _infoPrescriptions + "\n"
                  + " Found:\n" + _existingPrescriptions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "231515f3539ece567baf00787dae4903", "f965064f289bed1994115ddc374f5fee");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "patients","prescriptions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `patients`");
      _db.execSQL("DELETE FROM `prescriptions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PatientDao.class, PatientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PrescriptionDao.class, PrescriptionDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PatientDao patientDao() {
    if (_patientDao != null) {
      return _patientDao;
    } else {
      synchronized(this) {
        if(_patientDao == null) {
          _patientDao = new PatientDao_Impl(this);
        }
        return _patientDao;
      }
    }
  }

  @Override
  public PrescriptionDao prescriptionDao() {
    if (_prescriptionDao != null) {
      return _prescriptionDao;
    } else {
      synchronized(this) {
        if(_prescriptionDao == null) {
          _prescriptionDao = new PrescriptionDao_Impl(this);
        }
        return _prescriptionDao;
      }
    }
  }
}
