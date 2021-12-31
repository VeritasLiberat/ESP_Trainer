package com.veritasliberat.esp_trainer;

import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;
import java.util.Random;

@Entity(indices = {@Index(value = "sessionId")})
public class Trial {
    @PrimaryKey(autoGenerate = true)
    @Expose
    public long id = System.currentTimeMillis();

    @Ignore
    public static Random rand = new Random();
    @Ignore
    public MainActivity mainActivity;

    @Expose
    public long sessionId;
    @Expose
    public int trialNumber;
    @Expose
    public Selection computerSelection = getRandomColor();
    @Expose
    public Timestamp computerSelectionTimestamp = new Timestamp(System.currentTimeMillis());
    @Expose
    public Selection userSelection;
    @Expose
    public Timestamp userSelectionTimestamp;
    @Expose
    public boolean isCorrect = false;
    @Expose
    public boolean isPass = false;
    @Expose
    public long trialDuration;

    public Trial(long sessionId, int trialNumber, MainActivity mainActivity) {
        this.sessionId = sessionId;
        this.trialNumber = trialNumber;
        this.mainActivity = mainActivity;
    }

    public Trial() {}

    public Selection getRandomColor() {
        switch (rand.nextInt(4)) {
            case 0: return Selection.GREEN;
            case 1: return Selection.YELLOW;
            case 2: return Selection.RED;
            case 3: return Selection.BLUE;
        }
        return Selection.PASS;
    }

    public void processClick(Selection selection) {
        userSelection = selection;
        userSelectionTimestamp = new Timestamp(System.currentTimeMillis());
        trialDuration = userSelectionTimestamp.getTime() - computerSelectionTimestamp.getTime();

        if (selection.equals(computerSelection)) {
            isCorrect = true;
            mainActivity.correct();
        } else {
            mainActivity.highlightCorrectButton(computerSelection);
            if (selection.equals(Selection.PASS)) {
                isPass = true;
            }
        }
    }


}

