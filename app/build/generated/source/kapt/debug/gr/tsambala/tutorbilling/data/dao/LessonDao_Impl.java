package gr.tsambala.tutorbilling.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import gr.tsambala.tutorbilling.data.database.DateTimeConverters;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.Student;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
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

  private final DateTimeConverters __dateTimeConverters = new DateTimeConverters();

  private final EntityDeletionOrUpdateAdapter<Lesson> __deletionAdapterOfLesson;

  private final EntityDeletionOrUpdateAdapter<Lesson> __updateAdapterOfLesson;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLessonById;

  public LessonDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLesson = new EntityInsertionAdapter<Lesson>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `lessons` (`id`,`studentId`,`date`,`startTime`,`durationMinutes`,`notes`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        final Long _tmp = __dateTimeConverters.fromLocalDate(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Integer _tmp_1 = __dateTimeConverters.fromLocalTime(entity.getStartTime());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        statement.bindLong(5, entity.getDurationMinutes());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        final Long _tmp_2 = __dateTimeConverters.fromInstant(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
        final Long _tmp_3 = __dateTimeConverters.fromInstant(entity.getUpdatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_3);
        }
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
        return "UPDATE OR ABORT `lessons` SET `id` = ?,`studentId` = ?,`date` = ?,`startTime` = ?,`durationMinutes` = ?,`notes` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Lesson entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        final Long _tmp = __dateTimeConverters.fromLocalDate(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Integer _tmp_1 = __dateTimeConverters.fromLocalTime(entity.getStartTime());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        statement.bindLong(5, entity.getDurationMinutes());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        final Long _tmp_2 = __dateTimeConverters.fromInstant(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
        final Long _tmp_3 = __dateTimeConverters.fromInstant(entity.getUpdatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_3);
        }
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteLessonById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM lessons WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertLesson(final Lesson lesson, final Continuation<? super Long> $completion) {
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
  public Object deleteLesson(final Lesson lesson, final Continuation<? super Unit> $completion) {
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
  public Object updateLesson(final Lesson lesson, final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total += __updateAdapterOfLesson.handle(lesson);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLessonById(final long lessonId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLessonById.acquire();
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
          __preparedStmtOfDeleteLessonById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getLessonById(final long lessonId, final Continuation<? super Lesson> $completion) {
    final String _sql = "SELECT * FROM lessons WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, lessonId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Lesson>() {
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
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Lesson _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final LocalDate _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __dateTimeConverters.toLocalDate(_tmp);
            final LocalTime _tmpStartTime;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __dateTimeConverters.toLocalTime(_tmp_1);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp_2);
            final Instant _tmpUpdatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_3);
            _result = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Lesson>> getLessonsForStudent(final long studentId) {
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
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final LocalDate _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __dateTimeConverters.toLocalDate(_tmp);
            final LocalTime _tmpStartTime;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __dateTimeConverters.toLocalTime(_tmp_1);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp_2);
            final Instant _tmpUpdatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_3);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Lesson>> getLessonsForStudentInDateRange(final long studentId,
      final LocalDate startDate, final LocalDate endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM lessons \n"
            + "        WHERE studentId = ? \n"
            + "        AND date >= ? \n"
            + "        AND date <= ? \n"
            + "        ORDER BY date DESC, startTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    _argIndex = 2;
    final Long _tmp = __dateTimeConverters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __dateTimeConverters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
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
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final LocalDate _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __dateTimeConverters.toLocalDate(_tmp_2);
            final LocalTime _tmpStartTime;
            final Integer _tmp_3;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getInt(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __dateTimeConverters.toLocalTime(_tmp_3);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp_4);
            final Instant _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_5);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Lesson>> getAllLessonsInDateRange(final LocalDate startDate,
      final LocalDate endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM lessons \n"
            + "        WHERE date >= ? \n"
            + "        AND date <= ? \n"
            + "        ORDER BY date DESC, startTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __dateTimeConverters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __dateTimeConverters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
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
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final LocalDate _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __dateTimeConverters.toLocalDate(_tmp_2);
            final LocalTime _tmpStartTime;
            final Integer _tmp_3;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getInt(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __dateTimeConverters.toLocalTime(_tmp_3);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp_4);
            final Instant _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_5);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Lesson>> getTodaysLessons(final LocalDate today) {
    final String _sql = "SELECT * FROM lessons WHERE date = ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __dateTimeConverters.fromLocalDate(today);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
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
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Lesson> _result = new ArrayList<Lesson>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Lesson _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final LocalDate _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __dateTimeConverters.toLocalDate(_tmp_1);
            final LocalTime _tmpStartTime;
            final Integer _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getInt(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __dateTimeConverters.toLocalTime(_tmp_2);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp_3);
            final Instant _tmpUpdatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_4);
            _item = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getLessonCountForStudent(final long studentId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM lessons WHERE studentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
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
  public Object getTotalDurationForStudentInRange(final long studentId, final LocalDate startDate,
      final LocalDate endDate, final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT SUM(durationMinutes) \n"
            + "        FROM lessons \n"
            + "        WHERE studentId = ? \n"
            + "        AND date >= ? \n"
            + "        AND date <= ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    _argIndex = 2;
    final Long _tmp = __dateTimeConverters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __dateTimeConverters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp_2;
            if (_cursor.isNull(0)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getInt(0);
            }
            _result = _tmp_2;
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
  public Object getLastLessonDateForStudent(final long studentId,
      final Continuation<? super LocalDate> $completion) {
    final String _sql = "SELECT MAX(date) FROM lessons WHERE studentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<LocalDate>() {
      @Override
      @Nullable
      public LocalDate call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final LocalDate _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = __dateTimeConverters.toLocalDate(_tmp);
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
  public Flow<List<LessonWithStudent>> getLessonsWithStudentsInDateRange(final LocalDate startDate,
      final LocalDate endDate) {
    final String _sql = "\n"
            + "        SELECT l.* FROM lessons l\n"
            + "        INNER JOIN students s ON l.studentId = s.id\n"
            + "        WHERE s.isActive = 1\n"
            + "        AND l.date >= ? \n"
            + "        AND l.date <= ?\n"
            + "        ORDER BY l.date DESC, l.startTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __dateTimeConverters.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __dateTimeConverters.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, true, new String[] {"students",
        "lessons"}, new Callable<List<LessonWithStudent>>() {
      @Override
      @NonNull
      public List<LessonWithStudent> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
            final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final LongSparseArray<Student> _collectionStudent = new LongSparseArray<Student>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfStudentId);
              _collectionStudent.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipstudentsAsgrTsambalaTutorbillingDataModelStudent(_collectionStudent);
            final List<LessonWithStudent> _result = new ArrayList<LessonWithStudent>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final LessonWithStudent _item;
              final Lesson _tmpLesson;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final long _tmpStudentId;
              _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
              final LocalDate _tmpDate;
              final Long _tmp_2;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
              }
              _tmpDate = __dateTimeConverters.toLocalDate(_tmp_2);
              final LocalTime _tmpStartTime;
              final Integer _tmp_3;
              if (_cursor.isNull(_cursorIndexOfStartTime)) {
                _tmp_3 = null;
              } else {
                _tmp_3 = _cursor.getInt(_cursorIndexOfStartTime);
              }
              _tmpStartTime = __dateTimeConverters.toLocalTime(_tmp_3);
              final int _tmpDurationMinutes;
              _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final Instant _tmpCreatedAt;
              final Long _tmp_4;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_4 = null;
              } else {
                _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
              }
              _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp_4);
              final Instant _tmpUpdatedAt;
              final Long _tmp_5;
              if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
                _tmp_5 = null;
              } else {
                _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
              }
              _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_5);
              _tmpLesson = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
              final Student _tmpStudent;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfStudentId);
              _tmpStudent = _collectionStudent.get(_tmpKey_1);
              _item = new LessonWithStudent(_tmpLesson,_tmpStudent);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LessonWithStudent>> getLessonsWithStudentForStudent(final long studentId) {
    final String _sql = "\n"
            + "        SELECT l.* FROM lessons l\n"
            + "        WHERE l.studentId = ?\n"
            + "        ORDER BY l.date DESC, l.startTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"students",
        "lessons"}, new Callable<List<LessonWithStudent>>() {
      @Override
      @NonNull
      public List<LessonWithStudent> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
            final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final LongSparseArray<Student> _collectionStudent = new LongSparseArray<Student>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfStudentId);
              _collectionStudent.put(_tmpKey, null);
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipstudentsAsgrTsambalaTutorbillingDataModelStudent(_collectionStudent);
            final List<LessonWithStudent> _result = new ArrayList<LessonWithStudent>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final LessonWithStudent _item;
              final Lesson _tmpLesson;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final long _tmpStudentId;
              _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
              final LocalDate _tmpDate;
              final Long _tmp;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmp = null;
              } else {
                _tmp = _cursor.getLong(_cursorIndexOfDate);
              }
              _tmpDate = __dateTimeConverters.toLocalDate(_tmp);
              final LocalTime _tmpStartTime;
              final Integer _tmp_1;
              if (_cursor.isNull(_cursorIndexOfStartTime)) {
                _tmp_1 = null;
              } else {
                _tmp_1 = _cursor.getInt(_cursorIndexOfStartTime);
              }
              _tmpStartTime = __dateTimeConverters.toLocalTime(_tmp_1);
              final int _tmpDurationMinutes;
              _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final Instant _tmpCreatedAt;
              final Long _tmp_2;
              if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
              }
              _tmpCreatedAt = __dateTimeConverters.toInstant(_tmp_2);
              final Instant _tmpUpdatedAt;
              final Long _tmp_3;
              if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
                _tmp_3 = null;
              } else {
                _tmp_3 = _cursor.getLong(_cursorIndexOfUpdatedAt);
              }
              _tmpUpdatedAt = __dateTimeConverters.toInstant(_tmp_3);
              _tmpLesson = new Lesson(_tmpId,_tmpStudentId,_tmpDate,_tmpStartTime,_tmpDurationMinutes,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
              final Student _tmpStudent;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfStudentId);
              _tmpStudent = _collectionStudent.get(_tmpKey_1);
              _item = new LessonWithStudent(_tmpLesson,_tmpStudent);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
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

  private void __fetchRelationshipstudentsAsgrTsambalaTutorbillingDataModelStudent(
      @NonNull final LongSparseArray<Student> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipstudentsAsgrTsambalaTutorbillingDataModelStudent(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`hourlyRate`,`perLessonRate`,`createdAt`,`updatedAt`,`isActive` FROM `students` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfHourlyRate = 2;
      final int _cursorIndexOfPerLessonRate = 3;
      final int _cursorIndexOfCreatedAt = 4;
      final int _cursorIndexOfUpdatedAt = 5;
      final int _cursorIndexOfIsActive = 6;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final Student _item_1;
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
          _item_1 = new Student(_tmpId,_tmpName,_tmpHourlyRate,_tmpPerLessonRate,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
