package com.veritasliberat.esp_trainer;

import android.view.View;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Session {
    @Ignore
    public static final String SESSION_EXTRA_KEY = "session";

    @PrimaryKey(autoGenerate = true)
    @Expose
    public long sessionNumber = System.currentTimeMillis();

    @Expose
    public Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
    @Expose
    public Timestamp endTimestamp;

    @Ignore
    @Expose
    public List<Trial> trials = new ArrayList<>();
    @Ignore
    public MainActivity mainActivity;
    @Ignore
    public Trial currentTrial;

    @Expose
    public int colorSelections = 0;
    @Expose
    public int score = 0;
    @Expose
    public String topMessage = "";
    @Ignore
    @Expose
    public boolean guestMode = false;

    // Session Metrics
    @Expose
    public long sessionDuration = 0;
    @Expose
    public int numberOfTrials = 0;
    @Expose
    public int mostConsecutiveCorrect = 0;
    @Expose
    @ColumnInfo(defaultValue = "0")
    public long meanTrialDuration = 0;

    @Expose
    public int greenSelections = 0;
    @Expose
    public int yellowSelections = 0;
    @Expose
    public int redSelections = 0;
    @Expose
    public int blueSelections = 0;
    @Expose
    public int passSelections = 0;

    @Expose
    public int greenAnswers = 0;
    @Expose
    public int yellowAnswers = 0;
    @Expose
    public int redAnswers = 0;
    @Expose
    public int blueAnswers = 0;

    @Expose
    public int greenCorrect = 0;
    @Expose
    public int yellowCorrect = 0;
    @Expose
    public int redCorrect = 0;
    @Expose
    public int blueCorrect = 0;

    public Session(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        currentTrial = new Trial(sessionNumber, 1, mainActivity);
        mainActivity.guestSwitch.setVisibility(View.VISIBLE);
        mainActivity.guestLabel.setVisibility(View.GONE);
        updateLabels();
    }

    public Session() {}

    public void setTopMessage() {
        if (score < 6) {
            topMessage = "Select the Correct Square";
        } else if (score < 8) {
            topMessage = "A Good Beginning";
        } else if (score < 10) {
            topMessage = "ESP Ability Present";
        } else if (score < 12) {
            topMessage = "Outstanding";
        } else if (score < 14) {
            topMessage = "Useful for Las Vegas";
        } else {
            topMessage = "Psychic, Medium, Oracle!";
        }
    }

    public void updateLabels() {
        mainActivity.currentTrialView.setText(Integer.toString(colorSelections));
        mainActivity.scoreView.setText(Integer.toString(score));
        setTopMessage();
        mainActivity.topMessageView.setText(topMessage);
    }

    public void completeTrial() {
        if (currentTrial.isCorrect) {score++;}
        updateLabels();
        trials.add(currentTrial);

        if (currentTrial.trialNumber == 1) {
            mainActivity.guestSwitch.setVisibility(View.GONE);
            if (guestMode) {
                mainActivity.guestLabel.setVisibility(View.VISIBLE);
            }
        }

        if (colorSelections >= MainActivity.NUMBER_OF_COLOR_SELECTIONS) {
            completeSession();
        }

        currentTrial = new Trial(sessionNumber,currentTrial.trialNumber + 1, mainActivity);
    }

    void completeSession() {
        calculateSessionMetrics();

        if (!guestMode) {
            saveSession();
        }

        mainActivity.complete();
    }

    void saveSession() {
        dbTest();

        MainActivity.sessionDao.insertSession(this);
        MainActivity.trialDao.insertTrials(trials);
        MainActivity.metricsDao.insertMetrics(new Metrics(this));
    }

    void dbTest() {
        Session[] allSessions = MainActivity.sessionDao.getAllSessions();
        for (Session session : allSessions) {
            System.out.println("sessionNumber: " + session.sessionNumber +
                    " endTimestamp: " + session.endTimestamp +
                    " score: " + session.score);
            Trial[] trials = MainActivity.trialDao.getSessionsTrials(session.sessionNumber);
            MainActivity.trialDao.deleteTrials(Arrays.asList(trials));
        }
        MainActivity.sessionDao.deleteSessions(Arrays.asList(allSessions));
    }

    void calculateSessionMetrics() {
        endTimestamp = new Timestamp(System.currentTimeMillis());
        sessionDuration = endTimestamp.getTime() - startTimestamp.getTime();
        numberOfTrials = trials.size();
        meanTrialDuration = sessionDuration / numberOfTrials;

        int consecutiveCorrect = 0;

        for (Trial trial: trials) {
            // Summing Answers (Computer Selections)
            switch (trial.computerSelection) {
                case GREEN:     greenAnswers++; break;
                case YELLOW:    yellowAnswers++; break;
                case RED:       redAnswers++; break;
                case BLUE:      blueAnswers++; break;
            }

            // Summing User Selections and Correct/Pass Selections
            switch (trial.userSelection) {
                case GREEN:     greenSelections++;
                    if (trial.isCorrect) {greenCorrect++;}
                    break;
                case YELLOW:    yellowSelections++;
                    if (trial.isCorrect) {yellowCorrect++;}
                    break;
                case RED:       redSelections++;
                    if (trial.isCorrect) {redCorrect++;}
                    break;
                case BLUE:      blueSelections++;
                    if (trial.isCorrect) {blueCorrect++;}
                    break;
                case PASS:      passSelections++;
                    break;
            }

            // Determine Most Consecutive Correct Selections
            if (!trial.isPass) {
                if (trial.isCorrect) {
                    consecutiveCorrect++;
                    if (consecutiveCorrect > mostConsecutiveCorrect) {
                        mostConsecutiveCorrect = consecutiveCorrect;
                    }
                } else {
                    consecutiveCorrect = 0;
                }

            }
        }
    }

}

