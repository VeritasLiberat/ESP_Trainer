package com.veritasliberat.esp_trainer;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "sessionId")})
public class Metrics {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long sessionId;

    public int highScore = 0;
    public int highConsecutiveCorrect = 0;
    public int totalSessions = 0;
    public int totalTrials = 0;
    public double meanNumberOfTrials = 0;
    public long meanSessionDuration = 0;
    public long meanTrialDuration = 0;

    public int totalGreenSelections = 0;
    public int totalYellowSelections = 0;
    public int totalRedSelections = 0;
    public int totalBlueSelections = 0;
    public int totalPassSelections = 0;

    public int totalGreenAnswers = 0;
    public int totalYellowAnswers = 0;
    public int totalRedAnswers = 0;
    public int totalBlueAnswers = 0;

    public int totalGreenCorrect = 0;
    public int totalYellowCorrect = 0;
    public int totalRedCorrect = 0;
    public int totalBlueCorrect = 0;

    public Metrics(Session session) {
        this.sessionId = session.sessionNumber;

        Metrics topMetrics = MainActivity.metricsDao.getTopMetrics();
        if (topMetrics == null) {
            topMetrics = this;
        }

        if (session.score > topMetrics.highScore) {
            this.highScore = session.score;
        } else {
            this.highScore = topMetrics.highScore;
        }

        if (session.mostConsecutiveCorrect > topMetrics.highConsecutiveCorrect) {
            this.highConsecutiveCorrect = session.mostConsecutiveCorrect;
        } else {
            this.highConsecutiveCorrect = topMetrics.highConsecutiveCorrect;
        }

        this.totalSessions = topMetrics.totalSessions + 1;
        this.totalTrials = topMetrics.totalTrials + session.numberOfTrials;
        this.meanNumberOfTrials = this.totalSessions * 1.0 / this.totalTrials;

        this.meanSessionDuration = (topMetrics.meanSessionDuration * topMetrics.totalSessions +
                session.sessionDuration) / this.totalSessions;
        this.meanTrialDuration = (topMetrics.meanTrialDuration * topMetrics.totalTrials +
                session.meanTrialDuration * session.numberOfTrials) / this.totalTrials;

        this.totalGreenSelections = topMetrics.totalGreenSelections + session.greenSelections;
        this.totalYellowSelections = topMetrics.totalYellowSelections + session.yellowSelections;
        this.totalRedSelections = topMetrics.totalRedSelections + session.redSelections;
        this.totalBlueSelections = topMetrics.totalBlueSelections + session.blueSelections;
        this.totalPassSelections = topMetrics.totalPassSelections + session.passSelections;

        this.totalGreenAnswers = topMetrics.totalGreenAnswers + session.greenAnswers;
        this.totalYellowAnswers = topMetrics.totalYellowAnswers + session.yellowAnswers;
        this.totalRedAnswers = topMetrics.totalRedAnswers + session.redAnswers;
        this.totalBlueAnswers = topMetrics.totalBlueAnswers + session.blueAnswers;

        this.totalGreenCorrect = topMetrics.totalGreenCorrect + session.greenCorrect;
        this.totalYellowCorrect = topMetrics.totalYellowCorrect + session.yellowCorrect;
        this.totalRedCorrect = topMetrics.totalRedCorrect + session.redCorrect;
        this.totalBlueCorrect = topMetrics.totalBlueCorrect + session.blueCorrect;
    }

    public Metrics() {}
}
