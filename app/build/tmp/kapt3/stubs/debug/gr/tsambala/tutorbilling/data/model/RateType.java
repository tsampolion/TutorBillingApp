package gr.tsambala.tutorbilling.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.Instant;

/**
 * Enum to represent the different types of rates a student can have.
 * This makes our code more readable and type-safe.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lgr/tsambala/tutorbilling/data/model/RateType;", "", "(Ljava/lang/String;I)V", "HOURLY", "PER_LESSON", "NONE", "app_debug"})
public enum RateType {
    /*public static final*/ HOURLY /* = new HOURLY() */,
    /*public static final*/ PER_LESSON /* = new PER_LESSON() */,
    /*public static final*/ NONE /* = new NONE() */;
    
    RateType() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<gr.tsambala.tutorbilling.data.model.RateType> getEntries() {
        return null;
    }
}