package com.veritasliberat.esp_trainer;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MetricsActivity extends AppCompatActivity {
    static final int OVERVIEW_TEXT_SIZE = 18;
    static final int DETAIL_TEXT_SIZE = 14;
    static final int COLUMN_1_WIDTH = 600;
    static final int COLUMN_2_WIDTH = 200;
    static final int ROW_HEIGHT = 100;

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
        table.setPadding(100, 75, 50, 0);

        buildOverviewTable(table, metrics);
        addDivider(table);
        buildDetailedTable(table, metrics);

        LinearLayout activityMetrics = findViewById(R.id.metrics_layout);
        activityMetrics.addView(table);
    }

    TableRow generateRow(int labelId, String value, boolean isOverview) {
        TableRow row = new TableRow(this);

        TextView labelView = new TextView(this);
        labelView.setText(labelId);

        TextView valueView = new TextView(this);
        valueView.setText(value);

        if (isOverview) {
            labelView.setTextSize(OVERVIEW_TEXT_SIZE);
            valueView.setTextSize(OVERVIEW_TEXT_SIZE);
        } else {
            labelView.setTextSize(DETAIL_TEXT_SIZE);
            valueView.setTextSize(DETAIL_TEXT_SIZE);
        }

        row.addView(labelView, COLUMN_1_WIDTH, ROW_HEIGHT);
        row.addView(valueView, COLUMN_2_WIDTH, ROW_HEIGHT);

        return row;
    }

    void buildOverviewTable(TableLayout table, Metrics metrics) {
        table.addView(generateRow(R.string.mean_score, String.format("%,.2f", metrics.meanScore),
                true));
        table.addView(generateRow(R.string.total_sessions,
                Integer.toString(metrics.totalSessions), true));
        table.addView(generateRow(R.string.high_score, Integer.toString(metrics.highScore),
                true));
        table.addView(generateRow(R.string.consecutive_correct,
                Integer.toString(metrics.highConsecutiveCorrect), true));
    }

    void addDivider(TableLayout table) {
        TableRow blankRow1 = new TableRow(this);
        TextView blankView1 = new TextView(this);
        blankView1.setText("");
        blankRow1.addView(blankView1, COLUMN_1_WIDTH, 55);
        table.addView(blankRow1);

        View topLine = new View(this);
        topLine.setBackgroundColor(getResources().getColor(R.color.button_grey));
        topLine.setLayoutParams(new LinearLayout.LayoutParams(750, 2));
        table.addView(topLine);

        TableRow row = new TableRow(this);
        TextView breakView = new TextView(this);
        breakView.setText(R.string.metrics_table_divider);
        breakView.setTextSize(20);
        row.addView(breakView, COLUMN_1_WIDTH, 77);
        table.addView(row);

        View bottomLine = new View(table.getContext());
        bottomLine.setBackgroundColor(getResources().getColor(R.color.button_grey));
        bottomLine.setLayoutParams(new LinearLayout.LayoutParams(750, 2));
        table.addView(bottomLine);

        TableRow blankRow2 = new TableRow(this);
        TextView blankView2 = new TextView(this);
        blankView2.setText("");
        blankRow2.addView(blankView2, COLUMN_1_WIDTH, 55);
        table.addView(blankRow2);
    }

    void buildDetailedTable(TableLayout table, Metrics metrics) {
        table.addView(generateRow(R.string.total_trials, Integer.toString(metrics.totalTrials),
                false));
        table.addView(generateRow(R.string.mean_number_of_trials,
                Integer.toString((int) metrics.meanNumberOfTrials), false));

        double meanSessionDurationSeconds = metrics.meanSessionDuration / 1000.0;
        if (meanSessionDurationSeconds > 60) {
            double meanSessionDurationMinutes = meanSessionDurationSeconds / 60.0;
            table.addView(generateRow(R.string.mean_session_duration,
                    String.format("%,.1f", meanSessionDurationMinutes) + " Mins", false));
        } else {
            table.addView(generateRow(R.string.mean_session_duration,
                    String.format("%,.1f", meanSessionDurationSeconds) + " Secs", false));
        }

        double meanTrialDurationSeconds = metrics.meanTrialDuration / 1000.0;
        table.addView(generateRow(R.string.mean_trial_duration,
                String.format("%,.1f", meanTrialDurationSeconds) + " Secs", false));

        table.addView(generateRow(R.string.total_green_user_selections,
                Integer.toString(metrics.totalGreenSelections), false));
        table.addView(generateRow(R.string.total_yellow_user_selections,
                Integer.toString(metrics.totalYellowSelections), false));
        table.addView(generateRow(R.string.total_red_user_selections,
                Integer.toString(metrics.totalRedSelections), false));
        table.addView(generateRow(R.string.total_blue_user_selections,
                Integer.toString(metrics.totalBlueSelections), false));
        table.addView(generateRow(R.string.total_pass_selections,
                Integer.toString(metrics.totalPassSelections), false));

        table.addView(generateRow(R.string.total_green_computer_selections,
                Integer.toString(metrics.totalGreenAnswers), false));
        table.addView(generateRow(R.string.total_yellow_computer_selections,
                Integer.toString(metrics.totalYellowAnswers), false));
        table.addView(generateRow(R.string.total_red_computer_selections,
                Integer.toString(metrics.totalRedAnswers), false));
        table.addView(generateRow(R.string.total_blue_computer_selections,
                Integer.toString(metrics.totalBlueAnswers), false));

        table.addView(generateRow(R.string.total_green_correct,
                Integer.toString(metrics.totalGreenCorrect), false));
        table.addView(generateRow(R.string.total_yellow_correct,
                Integer.toString(metrics.totalYellowCorrect), false));
        table.addView(generateRow(R.string.total_red_correct,
                Integer.toString(metrics.totalRedCorrect), false));
        table.addView(generateRow(R.string.total_blue_correct,
                Integer.toString(metrics.totalBlueCorrect), false));
    }
}
