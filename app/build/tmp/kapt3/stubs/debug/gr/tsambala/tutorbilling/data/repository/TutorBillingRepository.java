package gr.tsambala.tutorbilling.data.repository;

import gr.tsambala.tutorbilling.data.dao.LessonDao;
import gr.tsambala.tutorbilling.data.dao.StudentDao;
import gr.tsambala.tutorbilling.data.dao.LessonWithStudent;
import gr.tsambala.tutorbilling.data.dao.StudentWithLessonCount;
import gr.tsambala.tutorbilling.data.model.Lesson;
import gr.tsambala.tutorbilling.data.model.Student;
import kotlinx.coroutines.flow.Flow;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * TutorBillingRepository is the single source of truth for all data operations.
 *
 * This class sits between the UI layer (ViewModels) and the data layer (DAOs),
 * providing a clean API that hides the complexity of data operations. It's like
 * having a skilled assistant who knows exactly where to find information and
 * how to present it in the most useful way.
 *
 * Benefits of the Repository pattern:
 * 1. Single source of truth - all data flows through here
 * 2. Abstraction - ViewModels don't need to know about DAOs or database details
 * 3. Business logic - complex calculations and data combinations happen here
 * 4. Testability - easy to mock for testing
 * 5. Future flexibility - could add caching, API calls, etc. without changing ViewModels
 *
 * @Inject tells Hilt to automatically provide the DAOs when creating this repository
 * @Singleton ensures we only have one repository instance in the app
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u000201B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0012\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u001a0\u0019J\u0018\u0010\u001b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001a\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u001a0\u00192\u0006\u0010\u0012\u001a\u00020\bJ\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001a0\u00192\u0006\u0010\u0012\u001a\u00020\bJ\u0018\u0010\u001f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\u0012\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0012\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u001a0\u0019J\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001a0\u0019J\u001c\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00192\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)J\u001a\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u001a0\u00192\u0006\u0010,\u001a\u00020-J\u0016\u0010.\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010/\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository;", "", "studentDao", "Lgr/tsambala/tutorbilling/data/dao/StudentDao;", "lessonDao", "Lgr/tsambala/tutorbilling/data/dao/LessonDao;", "(Lgr/tsambala/tutorbilling/data/dao/StudentDao;Lgr/tsambala/tutorbilling/data/dao/LessonDao;)V", "addLesson", "", "lesson", "Lgr/tsambala/tutorbilling/data/model/Lesson;", "(Lgr/tsambala/tutorbilling/data/model/Lesson;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addStudent", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "(Lgr/tsambala/tutorbilling/data/model/Student;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "canDeleteStudent", "", "studentId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteLesson", "", "lessonId", "deleteStudent", "getAllActiveStudents", "Lkotlinx/coroutines/flow/Flow;", "", "getLessonById", "getLessonsForStudent", "getLessonsWithStudentData", "Lgr/tsambala/tutorbilling/data/dao/LessonWithStudent;", "getStudent", "getStudentDetailedReport", "Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository$StudentDetailedReport;", "getStudentFinancialSummaries", "Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository$StudentFinancialSummary;", "getStudentsWithLessonCount", "Lgr/tsambala/tutorbilling/data/dao/StudentWithLessonCount;", "getTotalEarningsForDateRange", "", "startDate", "Ljava/time/LocalDate;", "endDate", "searchStudents", "query", "", "updateLesson", "updateStudent", "StudentDetailedReport", "StudentFinancialSummary", "app_debug"})
public final class TutorBillingRepository {
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.StudentDao studentDao = null;
    @org.jetbrains.annotations.NotNull()
    private final gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao = null;
    
    @javax.inject.Inject()
    public TutorBillingRepository(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.StudentDao studentDao, @org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.dao.LessonDao lessonDao) {
        super();
    }
    
    /**
     * Adds a new student to the database.
     * Validates the student data before saving.
     *
     * @return The ID of the newly created student
     * @throws IllegalArgumentException if student data is invalid
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addStudent(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    /**
     * Updates an existing student's information.
     * Automatically updates the timestamp.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateStudent(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Student student, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Soft deletes a student (marks as inactive).
     * Preserves the data for historical records.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteStudent(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Gets a single student by ID.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getStudent(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super gr.tsambala.tutorbilling.data.model.Student> $completion) {
        return null;
    }
    
    /**
     * Gets all active students as a Flow.
     * The UI will automatically update when students are added/edited/deleted.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Student>> getAllActiveStudents() {
        return null;
    }
    
    /**
     * Gets students with their lesson count.
     * Useful for showing statistics on the home screen.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.dao.StudentWithLessonCount>> getStudentsWithLessonCount() {
        return null;
    }
    
    /**
     * Adds a new lesson to the database.
     * Validates that the student exists and is active.
     *
     * @return The ID of the newly created lesson
     * @throws IllegalArgumentException if data is invalid
     * @throws IllegalStateException if student doesn't exist or is inactive
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addLesson(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    /**
     * Updates an existing lesson.
     * Automatically updates the timestamp.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateLesson(@org.jetbrains.annotations.NotNull()
    gr.tsambala.tutorbilling.data.model.Lesson lesson, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Deletes a lesson permanently.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteLesson(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Gets lessons for a specific student.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Lesson>> getLessonsForStudent(long studentId) {
        return null;
    }
    
    /**
     * Gets lessons with full student data for a specific student.
     * This is more efficient than loading them separately when you need both.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.dao.LessonWithStudent>> getLessonsWithStudentData(long studentId) {
        return null;
    }
    
    /**
     * Gets financial summaries for all active students.
     * This is the main data source for the home screen, showing each student
     * with their weekly and monthly earnings.
     *
     * This method demonstrates the power of the repository pattern - it combines
     * data from multiple sources and performs complex calculations, but presents
     * a simple interface to the ViewModel.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.repository.TutorBillingRepository.StudentFinancialSummary>> getStudentFinancialSummaries() {
        return null;
    }
    
    /**
     * Gets total earnings for a date range across all students.
     * Useful for reports and analytics.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Double> getTotalEarningsForDateRange(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate) {
        return null;
    }
    
    /**
     * Generates a detailed report for a student.
     * This kind of complex data aggregation is perfect for the repository layer.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getStudentDetailedReport(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super gr.tsambala.tutorbilling.data.repository.TutorBillingRepository.StudentDetailedReport> $completion) {
        return null;
    }
    
    /**
     * Gets a single lesson by ID.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getLessonById(long lessonId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super gr.tsambala.tutorbilling.data.model.Lesson> $completion) {
        return null;
    }
    
    /**
     * Checks if a student can be safely deleted.
     * A student can be deleted if they have no lessons.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object canDeleteStudent(long studentId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Searches for students by name.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<gr.tsambala.tutorbilling.data.model.Student>> searchStudents(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    /**
     * Gets detailed financial report for a specific student.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JG\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006$"}, d2 = {"Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository$StudentDetailedReport;", "", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "totalLessons", "", "totalHours", "", "totalEarnings", "averageLessonDuration", "lastLessonDate", "Ljava/time/LocalDate;", "(Lgr/tsambala/tutorbilling/data/model/Student;IDDDLjava/time/LocalDate;)V", "getAverageLessonDuration", "()D", "getLastLessonDate", "()Ljava/time/LocalDate;", "getStudent", "()Lgr/tsambala/tutorbilling/data/model/Student;", "getTotalEarnings", "getTotalHours", "getTotalLessons", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class StudentDetailedReport {
        @org.jetbrains.annotations.NotNull()
        private final gr.tsambala.tutorbilling.data.model.Student student = null;
        private final int totalLessons = 0;
        private final double totalHours = 0.0;
        private final double totalEarnings = 0.0;
        private final double averageLessonDuration = 0.0;
        @org.jetbrains.annotations.Nullable()
        private final java.time.LocalDate lastLessonDate = null;
        
        public StudentDetailedReport(@org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.Student student, int totalLessons, double totalHours, double totalEarnings, double averageLessonDuration, @org.jetbrains.annotations.Nullable()
        java.time.LocalDate lastLessonDate) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.Student getStudent() {
            return null;
        }
        
        public final int getTotalLessons() {
            return 0;
        }
        
        public final double getTotalHours() {
            return 0.0;
        }
        
        public final double getTotalEarnings() {
            return 0.0;
        }
        
        public final double getAverageLessonDuration() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.time.LocalDate getLastLessonDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.Student component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        public final double component4() {
            return 0.0;
        }
        
        public final double component5() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.time.LocalDate component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.repository.TutorBillingRepository.StudentDetailedReport copy(@org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.Student student, int totalLessons, double totalHours, double totalEarnings, double averageLessonDuration, @org.jetbrains.annotations.Nullable()
        java.time.LocalDate lastLessonDate) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    /**
     * Data class to hold financial summary information.
     * This bundles all the calculations needed for the home screen.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r\u00a8\u0006\u001c"}, d2 = {"Lgr/tsambala/tutorbilling/data/repository/TutorBillingRepository$StudentFinancialSummary;", "", "student", "Lgr/tsambala/tutorbilling/data/model/Student;", "weekTotal", "", "monthTotal", "lessonCount", "", "(Lgr/tsambala/tutorbilling/data/model/Student;DDI)V", "getLessonCount", "()I", "getMonthTotal", "()D", "getStudent", "()Lgr/tsambala/tutorbilling/data/model/Student;", "getWeekTotal", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class StudentFinancialSummary {
        @org.jetbrains.annotations.NotNull()
        private final gr.tsambala.tutorbilling.data.model.Student student = null;
        private final double weekTotal = 0.0;
        private final double monthTotal = 0.0;
        private final int lessonCount = 0;
        
        public StudentFinancialSummary(@org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.Student student, double weekTotal, double monthTotal, int lessonCount) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.Student getStudent() {
            return null;
        }
        
        public final double getWeekTotal() {
            return 0.0;
        }
        
        public final double getMonthTotal() {
            return 0.0;
        }
        
        public final int getLessonCount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.model.Student component1() {
            return null;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final gr.tsambala.tutorbilling.data.repository.TutorBillingRepository.StudentFinancialSummary copy(@org.jetbrains.annotations.NotNull()
        gr.tsambala.tutorbilling.data.model.Student student, double weekTotal, double monthTotal, int lessonCount) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}