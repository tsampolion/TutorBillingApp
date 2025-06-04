package gr.tsambala.tutorbilling.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import gr.tsambala.tutorbilling.data.model.Lesson;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LessonDao_Impl implements LessonDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Lesson> __insertionAdapterOfLesson;

  private final EntityDeletionOrUpdateAdapter<Lesson> __deletionAdapterOfLesson;

  private final EntityDeletionOrUpdateAdapter<Lesson> __updateAdapterOfLesson;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public LessonDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLesson = new EntityInsertionAdapter<Lesson>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `lessons` (`id`,`studentId`,`date`,`startTime`,`durationMinutes`,`notes`,`rateType`,`rateAmount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDate());
        }
        if (entity.getStartTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStartTime());
        }
        statement.bindLong(5, entity.getDurationMinutes());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        if (entity.getRateType() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRateType());
        }
        statement.bindDouble(8, entity.getRateAmount());
      }
    };
    this.__deletionAdapterOfLesson = new EntityDeletionOrUpdateAdapter<Lesson>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `lessons` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfLesson = new EntityDeletionOrUpdateAdapter<Lesson>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `lessons` SET `id` = ?,`studentId` = ?,`date` = ?,`startTime` = ?,`durationMinutes` = ?,`notes` = ?,`rateType` = ?,`rateAmount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDate());
        }
        if (entity.getStartTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStartTime());
        }
        statement.bindLong(5, entity.getDurationMinutes());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        if (entity.getRateType() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRateType());
        }
        statement.bindDouble(8, entity.getRateAmount());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM lessons WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Lesson lesson, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLesson.insertAndReturnId(lesson);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Lesson lesson, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfLesson.handle(lesson);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Lesson lesson, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLesson.handle(lesson);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long lessonId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lessonId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Lesson> getLessonById(final long lessonId) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, lessonId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lessons"}, new Callable<Lesson>() {
      @Override
      @Nullable
      public Lesson call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRateType = CursorUtil.getColumnIndexOrThrow(_cursor, "rateType");
          final int _cursorIndexOfRateAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "rateAmount");
          final Lesson _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpRateType;
            if (_cursor.isNull(_cursorIndexOfRateType)) {
              _tmpRateType = null;
            } else {
              _tmpRateType = _cursor.getString(_cursorIndexOfRateType);
            }
            final double _tmpRateAmount;
            _tmpRateAmount = _cursor.getDouble(_cursorIndexOfRateAmount);
            _result = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpRateType,_tmpRateAmount);
          } else {
            _result = null;
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
  public Flow<List<Lesson>> getLessonsByStudentId(final long studentId) {
    final String _sql = "SELECT * FROM lessons WHERE studentId = ? ORDER BY date DESC, startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lessons"}, new Callable<List<Lesson>>() {
      @Override
      @NonNull
      public List<Lesson> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRateType = CursorUtil.getColumnIndexOrThrow(_cursor, "rateType");
          final int _cursorIndexOfRateAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "rateAmount");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpRateType;
            if (_cursor.isNull(_cursorIndexOfRateType)) {
              _tmpRateType = null;
            } else {
              _tmpRateType = _cursor.getString(_cursorIndexOfRateType);
            }
            final double _tmpRateAmount;
            _tmpRateAmount = _cursor.getDouble(_cursorIndexOfRateAmount);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpRateType,_tmpRateAmount);
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
  public Flow<List<Lesson>> getAllLessons() {
    final String _sql = "SELECT * FROM lessons ORDER BY date DESC, startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lessons"}, new Callable<List<Lesson>>() {
      @Override
      @NonNull
      public List<Lesson> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRateType = CursorUtil.getColumnIndexOrThrow(_cursor, "rateType");
          final int _cursorIndexOfRateAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "rateAmount");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpRateType;
            if (_cursor.isNull(_cursorIndexOfRateType)) {
              _tmpRateType = null;
            } else {
              _tmpRateType = _cursor.getString(_cursorIndexOfRateType);
            }
            final double _tmpRateAmount;
            _tmpRateAmount = _cursor.getDouble(_cursorIndexOfRateAmount);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpRateType,_tmpRateAmount);
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
  public Flow<List<Lesson>> getLessonsByDate(final String date) {
    final String _sql = "SELECT * FROM lessons WHERE date = ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lessons"}, new Callable<List<Lesson>>() {
      @Override
      @NonNull
      public List<Lesson> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRateType = CursorUtil.getColumnIndexOrThrow(_cursor, "rateType");
          final int _cursorIndexOfRateAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "rateAmount");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpRateType;
            if (_cursor.isNull(_cursorIndexOfRateType)) {
              _tmpRateType = null;
            } else {
              _tmpRateType = _cursor.getString(_cursorIndexOfRateType);
            }
            final double _tmpRateAmount;
            _tmpRateAmount = _cursor.getDouble(_cursorIndexOfRateAmount);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpRateType,_tmpRateAmount);
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
  public Flow<List<Lesson>> getLessonsInDateRange(final String startDate, final String endDate) {
    final String _sql = "SELECT * FROM lessons WHERE date BETWEEN ? AND ? ORDER BY date DESC, startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (startDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, startDate);
    }
    _argIndex = 2;
    if (endDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, endDate);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"lessons"}, new Callable<List<Lesson>>() {
      @Override
      @NonNull
      public List<Lesson> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRateType = CursorUtil.getColumnIndexOrThrow(_cursor, "rateType");
          final int _cursorIndexOfRateAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "rateAmount");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpRateType;
            if (_cursor.isNull(_cursorIndexOfRateType)) {
              _tmpRateType = null;
            } else {
              _tmpRateType = _cursor.getString(_cursorIndexOfRateType);
            }
            final double _tmpRateAmount;
            _tmpRateAmount = _cursor.getDouble(_cursorIndexOfRateAmount);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpRateType,_tmpRateAmount);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
