package com.veritasliberat.esp_trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * todo:    add binaural beats with 'music' icon to play or stop
 *          add P value calculation
**/


@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MainActivity extends AppCompatActivity {
    public static final int NUMBER_OF_COLOR_SELECTIONS = 24;

    TextView currentTrialLabelView;
    TextView currentTrialView;
    TextView scoreView;
    TextView topMessageView;
    TextView guestSwitchView;
    TextView guestLabel;

    Session currentSession;

    public static AppDatabase db;
    public static SessionDao sessionDao;
    public static TrialDao trialDao;
    public static MetricsDao metricsDao;
    public Switch guestSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleViews();
        setupDatabase();
        guestMode();
    }

    public void handleViews() {
        currentTrialView = findViewById(R.id.current_trial);
        scoreView = findViewById(R.id.score);
        topMessageView = findViewById(R.id.top_message);
        currentTrialLabelView = findViewById(R.id.current_trial_label);
        currentTrialLabelView.setText(R.string.current_trial_label);
        guestSwitchView = findViewById(R.id.guest_switch);
        guestLabel = findViewById(R.id.guest_label);
    }

    public void setupDatabase() {
        currentSession = new Session(this);
        if (db == null) {
            db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "esp_trainer_db").fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        sessionDao = db.sessionDao();
        trialDao = db.trialDao();
        metricsDao = db.metricsDao();
        }
    }

    public void guestMode() {
        guestSwitch = (Switch) findViewById(R.id.guest_switch);
        guestSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> currentSession.guestMode = isChecked);
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
        guestSwitch.setChecked(false);
        currentSession = new Session(this);
    }

    public void complete() {
        Intent completeIntent = new Intent(this, SessionResultsActivity.class);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        String completeSession = gson.toJson(currentSession);
        completeIntent.putExtra(Session.SESSION_EXTRA_KEY, completeSession);

        Handler handler = new Handler();
        handler.postDelayed((Runnable) () -> startActivity(completeIntent), 600);
    }

    public void correct() {
        Intent correctIntent = new Intent(this, CorrectActivity.class);
        startActivity(correctIntent);
    }

    public void menu(View view) {
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
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

