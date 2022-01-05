package com.veritasliberat.esp_trainer;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Session.class, Trial.class, Metrics.class},
        version = 4,
        autoMigrations = {
            @AutoMigration(from = 1, to = 2), @AutoMigration(from = 2, to = 3),
                @AutoMigration(from = 3, to = 4)
        })
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract SessionDao sessionDao();
    public abstract TrialDao trialDao();
    public abstract MetricsDao metricsDao();
}
