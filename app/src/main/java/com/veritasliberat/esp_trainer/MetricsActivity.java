package com.veritasliberat.esp_trainer;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MetricsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        populateTable();
    }

    void populateTable() {
        Metrics metrics = MainActivity.metricsDao.getTopMetrics();

        TableLayout table = new TableLayout(this);
        table.setPadding(250, 50, 50, 50);

        table.addView(generateRow(R.string.high_score, Integer.toString(metrics.highScore)));
        table.addView(generateRow(R.string.consecutive_correct,
                Integer.toString(metrics.highConsecutiveCorrect)));
        table.addView(generateRow(R.string.total_sessions,
                Integer.toString(metrics.totalSessions)));
        table.addView(generateRow(R.string.total_trials, Integer.toString(metrics.totalTrials)));
        table.addView(generateRow(R.string.mean_number_of_trials,
                Integer.toString((int) metrics.meanNumberOfTrials)));

        double meanSessionDurationSeconds = metrics.meanSessionDuration / 1000.0;
        table.addView(generateRow(R.string.mean_session_duration,
                String.format("%,.1f", meanSessionDurationSeconds) + " s"));

        double meanTrialDurationSeconds = metrics.meanTrialDuration / 1000.0;
        table.addView(generateRow(R.string.mean_trial_duration,
                String.format("%,.1f", meanTrialDurationSeconds) + " s"));

        LinearLayout activityMetrics = findViewById(R.id.metrics_layout);
        activityMetrics.addView(table);
    }

    TableRow generateRow(int labelId, String value) {
        TableRow row = new TableRow(this);

        TextView labelView = new TextView(this);
        labelView.setText(labelId);
        row.addView(labelView, 500, 100);

        TextView valueView = new TextView(this);
        valueView.setText(value);
        row.addView(valueView, 250, 100);

        return row;
    }
}
