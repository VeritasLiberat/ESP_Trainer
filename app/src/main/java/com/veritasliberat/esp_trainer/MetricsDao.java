package com.veritasliberat.esp_trainer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MetricsDao {
    @Insert
    public void insertMetrics(Metrics metrics);

    @Delete
    public void deleteMetrics(List<Metrics> metrics);

    @Query("SELECT * FROM Metrics")
    public Metrics[] getAllMetrics();

    @Query("SELECT * FROM Metrics ORDER BY id DESC LIMIT 1")
    public Metrics getTopMetrics();
}
