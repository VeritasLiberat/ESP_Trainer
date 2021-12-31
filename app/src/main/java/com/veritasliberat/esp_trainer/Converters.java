package com.veritasliberat.esp_trainer;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class Converters {
    @TypeConverter
    public static Timestamp fromLongToTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long fromTimestampToLong(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }
}