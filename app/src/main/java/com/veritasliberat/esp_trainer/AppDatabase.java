package com.veritasliberat.esp_trainer;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Session.class, Trial.class},
        version = 2,
        autoMigrations = {
            @AutoMigration(from = 1, to = 2)
        })
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract SessionDao sessionDao();
    public abstract TrialDao trialDao();
}
