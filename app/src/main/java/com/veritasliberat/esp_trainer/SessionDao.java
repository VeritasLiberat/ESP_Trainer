package com.veritasliberat.esp_trainer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SessionDao {
    @Insert
    public void insertSession(Session session);

    @Delete
    public void deleteSessions(List<Session> sessions);

    @Query("SELECT * FROM Session")
    public Session[] getAllSessions();
}
