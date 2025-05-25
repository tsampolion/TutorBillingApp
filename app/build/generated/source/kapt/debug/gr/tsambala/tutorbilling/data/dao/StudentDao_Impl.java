package gr.tsambala.tutorbilling.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
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
import gr.tsambala.tutorbilling.data.database.DateTimeConverters;
import gr.tsambala.tutorbilling.data.model.Student;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
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
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Student> __insertionAdapterOfStudent;

  private final DateTimeConverters __dateTimeConverters = new DateTimeConverters();

  private final EntityDeletionOrUpdateAdapter<Student> __updateAdapterOfStudent;

  private final SharedSQLiteStatement __preparedStmtOfSoftDeleteStudent;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `students` (`id`,`name`,`hourlyRate`,`perLessonRate`,`createdAt`,`updatedAt`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getHourlyRate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getHourlyRate());
        }
        if (entity.getPerLessonRate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getPerLessonRate());
        }
        final Long _tmp = __dateTimeConverters.fromInstant(entity.getCreatedAt());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __dateTimeConverters.fromInstant(entity.getUpdatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `students` SET `id` = ?,`name` = ?,`hourlyRate` = ?,`perLessonRate` = ?,`createdAt` = ?,`updatedAt` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getHourlyRate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getHourlyRate());
        }
        if (entity.getPerLessonRate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getPerLessonRate());
        }
        final Long _tmp = __dateTimeConverters.fromInstant(entity.getCreatedAt());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __dateTimeConverters.fromInstant(entity.getUpdatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp_2);
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfSoftDeleteStudent = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE students SET isActive = 0, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertStudent(final Student student, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStudent.insertAndReturnId(student);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStudent(final Student student,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfStudent.handle(student);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object softDeleteStudent(final long studentId, final Instant timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDeleteStudent.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateTimeConverters.fromInstant(timestamp);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, studentId);
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
          __preparedStmtOfSoftDeleteStudent.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getStudentById(final long studentId,
      final Continuation<? super Student> $completion) {
    final String _sql = "SELECT * FROM students WHERE id = ? AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfPerLessonRate = CursorUtil.getColumnIndexOrThrow(_cursor, "perLessonRate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Student _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Double _tmpHourlyRate;
            if (_cursor.isNull(_cursorIndexOfHourlyRate)) {
              _tmpHourlyRate = null;
            } else {
              _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            }
            final Double _tmpPerLessonRate;
            if (_cursor.isNull(_cursorIndexOfPerLessonRate)) {
              _tmpPerLessonRate = null;
            } else {
              _tmpPerLessonRate = _cursor.getDouble(_cursorIndexOfPerLessonRate);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp);
            final Instant _tmpUpdatedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_1);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            _result = new Student(_tmpId,_tmpName,_tmpHourlyRate,_tmpPerLessonRate,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Student>> getAllActiveStudents() {
    final String _sql = "SELECT * FROM students WHERE isActive = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"students"}, new Callable<List<Student>>() {
      @Override
      @NonNull
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfPerLessonRate = CursorUtil.getColumnIndexOrThrow(_cursor, "perLessonRate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Double _tmpHourlyRate;
            if (_cursor.isNull(_cursorIndexOfHourlyRate)) {
              _tmpHourlyRate = null;
            } else {
              _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            }
            final Double _tmpPerLessonRate;
            if (_cursor.isNull(_cursorIndexOfPerLessonRate)) {
              _tmpPerLessonRate = null;
            } else {
              _tmpPerLessonRate = _cursor.getDouble(_cursorIndexOfPerLessonRate);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp);
            final Instant _tmpUpdatedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_1);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            _item = new Student(_tmpId,_tmpName,_tmpHourlyRate,_tmpPerLessonRate,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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
  public Flow<List<Student>> getAllStudentsIncludingInactive() {
    final String _sql = "SELECT * FROM students ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"students"}, new Callable<List<Student>>() {
      @Override
      @NonNull
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfPerLessonRate = CursorUtil.getColumnIndexOrThrow(_cursor, "perLessonRate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Double _tmpHourlyRate;
            if (_cursor.isNull(_cursorIndexOfHourlyRate)) {
              _tmpHourlyRate = null;
            } else {
              _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            }
            final Double _tmpPerLessonRate;
            if (_cursor.isNull(_cursorIndexOfPerLessonRate)) {
              _tmpPerLessonRate = null;
            } else {
              _tmpPerLessonRate = _cursor.getDouble(_cursorIndexOfPerLessonRate);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp);
            final Instant _tmpUpdatedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_1);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            _item = new Student(_tmpId,_tmpName,_tmpHourlyRate,_tmpPerLessonRate,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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
  public Flow<List<Student>> searchStudentsByName(final String searchQuery) {
    final String _sql = "SELECT * FROM students WHERE isActive = 1 AND name LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (searchQuery == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchQuery);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"students"}, new Callable<List<Student>>() {
      @Override
      @NonNull
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfPerLessonRate = CursorUtil.getColumnIndexOrThrow(_cursor, "perLessonRate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Double _tmpHourlyRate;
            if (_cursor.isNull(_cursorIndexOfHourlyRate)) {
              _tmpHourlyRate = null;
            } else {
              _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            }
            final Double _tmpPerLessonRate;
            if (_cursor.isNull(_cursorIndexOfPerLessonRate)) {
              _tmpPerLessonRate = null;
            } else {
              _tmpPerLessonRate = _cursor.getDouble(_cursorIndexOfPerLessonRate);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp);
            final Instant _tmpUpdatedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_1);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            _item = new Student(_tmpId,_tmpName,_tmpHourlyRate,_tmpPerLessonRate,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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
  public Object getActiveStudentCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM students WHERE isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object studentHasLessons(final long studentId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM lessons WHERE studentId = ? LIMIT 1)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StudentWithLessonCount>> getStudentsWithLessonCount() {
    final String _sql = "\n"
            + "        SELECT s.*, COUNT(l.id) as lessonCount \n"
            + "        FROM students s \n"
            + "        LEFT JOIN lessons l ON s.id = l.studentId \n"
            + "        WHERE s.isActive = 1 \n"
            + "        GROUP BY s.id \n"
            + "        ORDER BY s.name ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"students",
        "lessons"}, new Callable<List<StudentWithLessonCount>>() {
      @Override
      @NonNull
      public List<StudentWithLessonCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfPerLessonRate = CursorUtil.getColumnIndexOrThrow(_cursor, "perLessonRate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfLessonCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lessonCount");
          final List<StudentWithLessonCount> _result = new ArrayList<StudentWithLessonCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudentWithLessonCount _item;
            final int _tmpLessonCount;
            _tmpLessonCount = _cursor.getInt(_cursorIndexOfLessonCount);
            final Student _tmpStudent;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Double _tmpHourlyRate;
            if (_cursor.isNull(_cursorIndexOfHourlyRate)) {
              _tmpHourlyRate = null;
            } else {
              _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            }
            final Double _tmpPerLessonRate;
            if (_cursor.isNull(_cursorIndexOfPerLessonRate)) {
              _tmpPerLessonRate = null;
            } else {
              _tmpPerLessonRate = _cursor.getDouble(_cursorIndexOfPerLessonRate);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp);
            final Instant _tmpUpdatedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_1);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            _tmpStudent = new Student(_tmpId,_tmpName,_tmpHourlyRate,_tmpPerLessonRate,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
            _item = new StudentWithLessonCount(_tmpStudent,_tmpLessonCount);
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
