package com.dentacore.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.dentacore.models.Prescription;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PrescriptionDao_Impl implements PrescriptionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Prescription> __insertionAdapterOfPrescription;

  private final EntityDeletionOrUpdateAdapter<Prescription> __deletionAdapterOfPrescription;

  private final EntityDeletionOrUpdateAdapter<Prescription> __updateAdapterOfPrescription;

  public PrescriptionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPrescription = new EntityInsertionAdapter<Prescription>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `prescriptions` (`id`,`patientId`,`patientName`,`doctorName`,`clinicName`,`diagnosis`,`notes`,`paymentMode`,`procedureType`,`medications`,`treatedTeeth`,`createdAt`,`billAmount`,`paidAmount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Prescription entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.patientId);
        if (entity.patientName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.patientName);
        }
        if (entity.doctorName == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.doctorName);
        }
        if (entity.clinicName == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.clinicName);
        }
        if (entity.diagnosis == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.diagnosis);
        }
        if (entity.notes == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.notes);
        }
        if (entity.paymentMode == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.paymentMode);
        }
        if (entity.procedureType == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.procedureType);
        }
        final String _tmp = Converters.fromMedList(entity.medications);
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        final String _tmp_1 = Converters.fromIntList(entity.treatedTeeth);
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_1);
        }
        statement.bindLong(12, entity.createdAt);
        statement.bindDouble(13, entity.billAmount);
        statement.bindDouble(14, entity.paidAmount);
      }
    };
    this.__deletionAdapterOfPrescription = new EntityDeletionOrUpdateAdapter<Prescription>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `prescriptions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Prescription entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfPrescription = new EntityDeletionOrUpdateAdapter<Prescription>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `prescriptions` SET `id` = ?,`patientId` = ?,`patientName` = ?,`doctorName` = ?,`clinicName` = ?,`diagnosis` = ?,`notes` = ?,`paymentMode` = ?,`procedureType` = ?,`medications` = ?,`treatedTeeth` = ?,`createdAt` = ?,`billAmount` = ?,`paidAmount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Prescription entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.patientId);
        if (entity.patientName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.patientName);
        }
        if (entity.doctorName == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.doctorName);
        }
        if (entity.clinicName == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.clinicName);
        }
        if (entity.diagnosis == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.diagnosis);
        }
        if (entity.notes == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.notes);
        }
        if (entity.paymentMode == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.paymentMode);
        }
        if (entity.procedureType == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.procedureType);
        }
        final String _tmp = Converters.fromMedList(entity.medications);
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        final String _tmp_1 = Converters.fromIntList(entity.treatedTeeth);
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_1);
        }
        statement.bindLong(12, entity.createdAt);
        statement.bindDouble(13, entity.billAmount);
        statement.bindDouble(14, entity.paidAmount);
        statement.bindLong(15, entity.id);
      }
    };
  }

  @Override
  public long insert(final Prescription p) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfPrescription.insertAndReturnId(p);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Prescription p) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPrescription.handle(p);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Prescription p) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPrescription.handle(p);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Prescription>> getForPatient(final int pid) {
    final String _sql = "SELECT * FROM prescriptions WHERE patientId=? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, pid);
    return __db.getInvalidationTracker().createLiveData(new String[] {"prescriptions"}, false, new Callable<List<Prescription>>() {
      @Override
      @Nullable
      public List<Prescription> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfDoctorName = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorName");
          final int _cursorIndexOfClinicName = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicName");
          final int _cursorIndexOfDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "diagnosis");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPaymentMode = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMode");
          final int _cursorIndexOfProcedureType = CursorUtil.getColumnIndexOrThrow(_cursor, "procedureType");
          final int _cursorIndexOfMedications = CursorUtil.getColumnIndexOrThrow(_cursor, "medications");
          final int _cursorIndexOfTreatedTeeth = CursorUtil.getColumnIndexOrThrow(_cursor, "treatedTeeth");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfBillAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "billAmount");
          final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
          final List<Prescription> _result = new ArrayList<Prescription>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Prescription _item;
            _item = new Prescription();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.patientId = _cursor.getInt(_cursorIndexOfPatientId);
            if (_cursor.isNull(_cursorIndexOfPatientName)) {
              _item.patientName = null;
            } else {
              _item.patientName = _cursor.getString(_cursorIndexOfPatientName);
            }
            if (_cursor.isNull(_cursorIndexOfDoctorName)) {
              _item.doctorName = null;
            } else {
              _item.doctorName = _cursor.getString(_cursorIndexOfDoctorName);
            }
            if (_cursor.isNull(_cursorIndexOfClinicName)) {
              _item.clinicName = null;
            } else {
              _item.clinicName = _cursor.getString(_cursorIndexOfClinicName);
            }
            if (_cursor.isNull(_cursorIndexOfDiagnosis)) {
              _item.diagnosis = null;
            } else {
              _item.diagnosis = _cursor.getString(_cursorIndexOfDiagnosis);
            }
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _item.notes = null;
            } else {
              _item.notes = _cursor.getString(_cursorIndexOfNotes);
            }
            if (_cursor.isNull(_cursorIndexOfPaymentMode)) {
              _item.paymentMode = null;
            } else {
              _item.paymentMode = _cursor.getString(_cursorIndexOfPaymentMode);
            }
            if (_cursor.isNull(_cursorIndexOfProcedureType)) {
              _item.procedureType = null;
            } else {
              _item.procedureType = _cursor.getString(_cursorIndexOfProcedureType);
            }
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfMedications)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfMedications);
            }
            _item.medications = Converters.toMedList(_tmp);
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTreatedTeeth)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfTreatedTeeth);
            }
            _item.treatedTeeth = Converters.toIntList(_tmp_1);
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.billAmount = _cursor.getDouble(_cursorIndexOfBillAmount);
            _item.paidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Prescription>> getAll() {
    final String _sql = "SELECT * FROM prescriptions ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"prescriptions"}, false, new Callable<List<Prescription>>() {
      @Override
      @Nullable
      public List<Prescription> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfDoctorName = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorName");
          final int _cursorIndexOfClinicName = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicName");
          final int _cursorIndexOfDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "diagnosis");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPaymentMode = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMode");
          final int _cursorIndexOfProcedureType = CursorUtil.getColumnIndexOrThrow(_cursor, "procedureType");
          final int _cursorIndexOfMedications = CursorUtil.getColumnIndexOrThrow(_cursor, "medications");
          final int _cursorIndexOfTreatedTeeth = CursorUtil.getColumnIndexOrThrow(_cursor, "treatedTeeth");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfBillAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "billAmount");
          final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
          final List<Prescription> _result = new ArrayList<Prescription>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Prescription _item;
            _item = new Prescription();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.patientId = _cursor.getInt(_cursorIndexOfPatientId);
            if (_cursor.isNull(_cursorIndexOfPatientName)) {
              _item.patientName = null;
            } else {
              _item.patientName = _cursor.getString(_cursorIndexOfPatientName);
            }
            if (_cursor.isNull(_cursorIndexOfDoctorName)) {
              _item.doctorName = null;
            } else {
              _item.doctorName = _cursor.getString(_cursorIndexOfDoctorName);
            }
            if (_cursor.isNull(_cursorIndexOfClinicName)) {
              _item.clinicName = null;
            } else {
              _item.clinicName = _cursor.getString(_cursorIndexOfClinicName);
            }
            if (_cursor.isNull(_cursorIndexOfDiagnosis)) {
              _item.diagnosis = null;
            } else {
              _item.diagnosis = _cursor.getString(_cursorIndexOfDiagnosis);
            }
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _item.notes = null;
            } else {
              _item.notes = _cursor.getString(_cursorIndexOfNotes);
            }
            if (_cursor.isNull(_cursorIndexOfPaymentMode)) {
              _item.paymentMode = null;
            } else {
              _item.paymentMode = _cursor.getString(_cursorIndexOfPaymentMode);
            }
            if (_cursor.isNull(_cursorIndexOfProcedureType)) {
              _item.procedureType = null;
            } else {
              _item.procedureType = _cursor.getString(_cursorIndexOfProcedureType);
            }
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfMedications)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfMedications);
            }
            _item.medications = Converters.toMedList(_tmp);
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTreatedTeeth)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfTreatedTeeth);
            }
            _item.treatedTeeth = Converters.toIntList(_tmp_1);
            _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.billAmount = _cursor.getDouble(_cursorIndexOfBillAmount);
            _item.paidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Prescription getById(final int id) {
    final String _sql = "SELECT * FROM prescriptions WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
      final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
      final int _cursorIndexOfDoctorName = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorName");
      final int _cursorIndexOfClinicName = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicName");
      final int _cursorIndexOfDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "diagnosis");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfPaymentMode = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMode");
      final int _cursorIndexOfProcedureType = CursorUtil.getColumnIndexOrThrow(_cursor, "procedureType");
      final int _cursorIndexOfMedications = CursorUtil.getColumnIndexOrThrow(_cursor, "medications");
      final int _cursorIndexOfTreatedTeeth = CursorUtil.getColumnIndexOrThrow(_cursor, "treatedTeeth");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfBillAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "billAmount");
      final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
      final Prescription _result;
      if (_cursor.moveToFirst()) {
        _result = new Prescription();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.patientId = _cursor.getInt(_cursorIndexOfPatientId);
        if (_cursor.isNull(_cursorIndexOfPatientName)) {
          _result.patientName = null;
        } else {
          _result.patientName = _cursor.getString(_cursorIndexOfPatientName);
        }
        if (_cursor.isNull(_cursorIndexOfDoctorName)) {
          _result.doctorName = null;
        } else {
          _result.doctorName = _cursor.getString(_cursorIndexOfDoctorName);
        }
        if (_cursor.isNull(_cursorIndexOfClinicName)) {
          _result.clinicName = null;
        } else {
          _result.clinicName = _cursor.getString(_cursorIndexOfClinicName);
        }
        if (_cursor.isNull(_cursorIndexOfDiagnosis)) {
          _result.diagnosis = null;
        } else {
          _result.diagnosis = _cursor.getString(_cursorIndexOfDiagnosis);
        }
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _result.notes = null;
        } else {
          _result.notes = _cursor.getString(_cursorIndexOfNotes);
        }
        if (_cursor.isNull(_cursorIndexOfPaymentMode)) {
          _result.paymentMode = null;
        } else {
          _result.paymentMode = _cursor.getString(_cursorIndexOfPaymentMode);
        }
        if (_cursor.isNull(_cursorIndexOfProcedureType)) {
          _result.procedureType = null;
        } else {
          _result.procedureType = _cursor.getString(_cursorIndexOfProcedureType);
        }
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfMedications)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfMedications);
        }
        _result.medications = Converters.toMedList(_tmp);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfTreatedTeeth)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfTreatedTeeth);
        }
        _result.treatedTeeth = Converters.toIntList(_tmp_1);
        _result.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.billAmount = _cursor.getDouble(_cursorIndexOfBillAmount);
        _result.paidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double revenueInRange(final long from, final long to) {
    final String _sql = "SELECT COALESCE(SUM(billAmount), 0.0) FROM prescriptions WHERE createdAt>=? AND createdAt<=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    _argIndex = 2;
    _statement.bindLong(_argIndex, to);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countInRange(final long from, final long to) {
    final String _sql = "SELECT COUNT(*) FROM prescriptions WHERE createdAt>=? AND createdAt<=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    _argIndex = 2;
    _statement.bindLong(_argIndex, to);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Prescription> recentPrescriptions() {
    final String _sql = "SELECT * FROM prescriptions ORDER BY createdAt DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPatientId = CursorUtil.getColumnIndexOrThrow(_cursor, "patientId");
      final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
      final int _cursorIndexOfDoctorName = CursorUtil.getColumnIndexOrThrow(_cursor, "doctorName");
      final int _cursorIndexOfClinicName = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicName");
      final int _cursorIndexOfDiagnosis = CursorUtil.getColumnIndexOrThrow(_cursor, "diagnosis");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfPaymentMode = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMode");
      final int _cursorIndexOfProcedureType = CursorUtil.getColumnIndexOrThrow(_cursor, "procedureType");
      final int _cursorIndexOfMedications = CursorUtil.getColumnIndexOrThrow(_cursor, "medications");
      final int _cursorIndexOfTreatedTeeth = CursorUtil.getColumnIndexOrThrow(_cursor, "treatedTeeth");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfBillAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "billAmount");
      final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
      final List<Prescription> _result = new ArrayList<Prescription>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Prescription _item;
        _item = new Prescription();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.patientId = _cursor.getInt(_cursorIndexOfPatientId);
        if (_cursor.isNull(_cursorIndexOfPatientName)) {
          _item.patientName = null;
        } else {
          _item.patientName = _cursor.getString(_cursorIndexOfPatientName);
        }
        if (_cursor.isNull(_cursorIndexOfDoctorName)) {
          _item.doctorName = null;
        } else {
          _item.doctorName = _cursor.getString(_cursorIndexOfDoctorName);
        }
        if (_cursor.isNull(_cursorIndexOfClinicName)) {
          _item.clinicName = null;
        } else {
          _item.clinicName = _cursor.getString(_cursorIndexOfClinicName);
        }
        if (_cursor.isNull(_cursorIndexOfDiagnosis)) {
          _item.diagnosis = null;
        } else {
          _item.diagnosis = _cursor.getString(_cursorIndexOfDiagnosis);
        }
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _item.notes = null;
        } else {
          _item.notes = _cursor.getString(_cursorIndexOfNotes);
        }
        if (_cursor.isNull(_cursorIndexOfPaymentMode)) {
          _item.paymentMode = null;
        } else {
          _item.paymentMode = _cursor.getString(_cursorIndexOfPaymentMode);
        }
        if (_cursor.isNull(_cursorIndexOfProcedureType)) {
          _item.procedureType = null;
        } else {
          _item.procedureType = _cursor.getString(_cursorIndexOfProcedureType);
        }
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfMedications)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfMedications);
        }
        _item.medications = Converters.toMedList(_tmp);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfTreatedTeeth)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfTreatedTeeth);
        }
        _item.treatedTeeth = Converters.toIntList(_tmp_1);
        _item.createdAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.billAmount = _cursor.getDouble(_cursorIndexOfBillAmount);
        _item.paidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ProcedureCount> procedureCounts() {
    final String _sql = "SELECT procedureType, COUNT(*) as cnt FROM prescriptions GROUP BY procedureType";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfProcedureType = 0;
      final int _cursorIndexOfCnt = 1;
      final List<ProcedureCount> _result = new ArrayList<ProcedureCount>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProcedureCount _item;
        _item = new ProcedureCount();
        if (_cursor.isNull(_cursorIndexOfProcedureType)) {
          _item.procedureType = null;
        } else {
          _item.procedureType = _cursor.getString(_cursorIndexOfProcedureType);
        }
        _item.cnt = _cursor.getInt(_cursorIndexOfCnt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
