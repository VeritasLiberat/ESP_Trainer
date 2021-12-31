package com.veritasliberat.esp_trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int NUMBER_OF_COLOR_SELECTIONS = 4;

    TextView currentTrialView;
    TextView scoreView;
    TextView topMessageView;

    Session currentSession;

    AppDatabase db;
    SessionDao sessionDao;
    TrialDao trialDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTrialView = findViewById(R.id.current_trial);
        scoreView = findViewById(R.id.score);
        topMessageView = findViewById(R.id.top_message);
        TextView currentTrialLabelView = findViewById(R.id.current_trial_label);
        currentTrialLabelView.setText("Current Trial of " + NUMBER_OF_COLOR_SELECTIONS);

        currentSession = new Session(this);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "esp_trainer_db").allowMainThreadQueries().build();
        sessionDao = db.sessionDao();
        trialDao = db.trialDao();
    }

    public void clickGreen(View view) {
        currentSession.currentTrial.processClick(Selection.GREEN);
        currentSession.colorSelections++;
        currentSession.completeTrial();
    }

    public void clickYellow(View view) {
        currentSession.currentTrial.processClick(Selection.YELLOW);
        currentSession.colorSelections++;
        currentSession.completeTrial();
    }

    public void clickRed(View view) {
        currentSession.currentTrial.processClick(Selection.RED);
        currentSession.colorSelections++;
        currentSession.completeTrial();
    }

    public void clickBlue(View view) {
        currentSession.currentTrial.processClick(Selection.BLUE);
        currentSession.colorSelections++;
        currentSession.completeTrial();
    }

    public void pass(View view) {
        currentSession.currentTrial.processClick(Selection.PASS);
        currentSession.completeTrial();
    }

    public void reset(View view) {
        currentSession = new Session(this);
    }

    public void complete() {
        Intent completeIntent = new Intent(this, CompleteActivity.class);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        String completeSession = gson.toJson(currentSession);
        System.out.println("completeSession: " + completeSession);
        completeIntent.putExtra("completeSession", completeSession);

        Handler handler = new Handler();
        handler.postDelayed((Runnable) () -> startActivity(completeIntent), 600);
    }

    public void correct() {
        Intent correctIntent = new Intent(this, CorrectActivity.class);
        startActivity(correctIntent);
    }

    public void highlightCorrectButton(Selection computerSelection) {
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(100); // in ms
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        Button button = (Button) findViewById(R.id.green_button);;
        switch (computerSelection) {
            case GREEN:     button = (Button) findViewById(R.id.green_button); break;
            case YELLOW:    button = (Button) findViewById(R.id.yellow_button); break;
            case RED:       button = (Button) findViewById(R.id.red_button); break;
            case BLUE:      button = (Button) findViewById(R.id.blue_button); break;
        }

        button.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed((Runnable) button::clearAnimation, 600);
    }

}

