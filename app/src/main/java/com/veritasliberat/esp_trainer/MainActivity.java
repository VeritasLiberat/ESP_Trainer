package com.veritasliberat.esp_trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
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
 * todo:    add history activity (shows all past sessions, click on one to go to result view)
 *              add "remove session history" button, with confirmation box
 *          add metrics activity (show key metrics for the user)
 *          change 'complete' activity to the SessionResultsActivity (show info for that session)
 *              this is called after the session is complete, or from the history activity
 *          add info page
 *              detail more about Targ and this app (explain guest button)
 */

public class MainActivity extends AppCompatActivity {
    public static final int NUMBER_OF_COLOR_SELECTIONS = 4;

    TextView currentTrialView;
    TextView scoreView;
    TextView topMessageView;
    TextView guestSwitch;
    TextView guestLabel;

    Session currentSession;

    AppDatabase db;
    SessionDao sessionDao;
    TrialDao trialDao;

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
        TextView currentTrialLabelView = findViewById(R.id.current_trial_label);
        currentTrialLabelView.setText("Current Trial of " + NUMBER_OF_COLOR_SELECTIONS);
        guestSwitch = findViewById(R.id.guest_switch);
        guestLabel = findViewById(R.id.guest_label);
    }

    public void setupDatabase() {
        currentSession = new Session(this);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "esp_trainer_db").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        sessionDao = db.sessionDao();
        trialDao = db.trialDao();
    }

    public void guestMode() {
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch guestSwitch = (Switch) findViewById(R.id.guest_switch);
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

