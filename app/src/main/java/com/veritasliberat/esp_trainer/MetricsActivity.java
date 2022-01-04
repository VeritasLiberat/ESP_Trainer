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

        if (metrics == null) {return;}

        TableLayout table = new TableLayout(this);
        table.setPadding(150, 75, 50, 0);

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

        table.addView(generateRow(R.string.total_green_user_selections,
                Integer.toString(metrics.totalGreenSelections)));
        table.addView(generateRow(R.string.total_yellow_user_selections,
                Integer.toString(metrics.totalYellowSelections)));
        table.addView(generateRow(R.string.total_red_user_selections,
                Integer.toString(metrics.totalRedSelections)));
        table.addView(generateRow(R.string.total_blue_user_selections,
                Integer.toString(metrics.totalBlueSelections)));
        table.addView(generateRow(R.string.total_pass_selections,
                Integer.toString(metrics.totalPassSelections)));

        table.addView(generateRow(R.string.total_green_computer_selections,
                Integer.toString(metrics.totalGreenAnswers)));
        table.addView(generateRow(R.string.total_yellow_computer_selections,
                Integer.toString(metrics.totalYellowAnswers)));
        table.addView(generateRow(R.string.total_red_computer_selections,
                Integer.toString(metrics.totalRedAnswers)));
        table.addView(generateRow(R.string.total_blue_computer_selections,
                Integer.toString(metrics.totalBlueAnswers)));

        table.addView(generateRow(R.string.total_green_correct,
                Integer.toString(metrics.totalGreenCorrect)));
        table.addView(generateRow(R.string.total_yellow_correct,
                Integer.toString(metrics.totalYellowCorrect)));
        table.addView(generateRow(R.string.total_red_correct,
                Integer.toString(metrics.totalRedCorrect)));
        table.addView(generateRow(R.string.total_blue_correct,
                Integer.toString(metrics.totalBlueCorrect)));

        LinearLayout activityMetrics = findViewById(R.id.metrics_layout);
        activityMetrics.addView(table);
    }

    TableRow generateRow(int labelId, String value) {
        TableRow row = new TableRow(this);

        TextView labelView = new TextView(this);
        labelView.setText(labelId);
        row.addView(labelView, 600, 100);

        TextView valueView = new TextView(this);
        valueView.setText(value);
        row.addView(valueView, 150, 100);

        return row;
    }
}
