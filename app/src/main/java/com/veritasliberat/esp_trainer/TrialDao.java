package com.veritasliberat.esp_trainer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrialDao {
    @Insert
    public void insertTrials(List<Trial> trials);

    @Delete
    public void deleteTrials(List<Trial> trials);

    @Query("SELECT * FROM Trial WHERE sessionId = :sessionId")
    public Trial[] getSessionsTrials(long sessionId);
}
