package com.veritasliberat.esp_trainer;

import androidx.appcompat.app.AppCompatActivity;

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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public final int NUMBER_OF_COLOR_SELECTIONS = 24;

    public static Random rand = new Random();

    TextView currentTrialView;
    TextView scoreView;
    TextView topMessageView;

    Session currentSession;

    enum Selection {
        GREEN,
        YELLOW,
        RED,
        BLUE,
        PASS
    }

    class Session {
        long sessionNumber = System.currentTimeMillis();
        Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp endTimestamp;
        List<Trial> trials = new ArrayList<>();
        Trial currentTrial = new Trial(1);
        int colorSelections = 0;
        int score = 0;
        String topMessage = "";

        // Metrics
        long sessionDuration = 0;
        int numberOfTrials = 0;

        int greenSelections = 0;
        int yellowSelections = 0;
        int redSelections = 0;
        int blueSelections = 0;
        int passSelections = 0;

        int greenAnswers = 0;
        int yellowAnswers = 0;
        int redAnswers = 0;
        int blueAnswers = 0;

        int greenCorrect = 0;
        int yellowCorrect = 0;
        int redCorrect = 0;
        int blueCorrect = 0;

        Session() {
            updateLabels();
        }

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
            currentTrialView.setText(Integer.toString(colorSelections));
            scoreView.setText(Integer.toString(score));
            setTopMessage();
            topMessageView.setText(topMessage);
        }

        public void completeTrial() {
            if (currentTrial.isCorrect) {score++;}
            updateLabels();
            trials.add(currentTrial);

            if (colorSelections >= NUMBER_OF_COLOR_SELECTIONS) {
                completeSession();
            }

            currentTrial = new Trial(currentTrial.trialNumber + 1);
        }

        void completeSession() {
            calculateMetrics();
            // todo: log to the db
            complete();
        }

        void calculateMetrics() {
            endTimestamp = new Timestamp(System.currentTimeMillis());
            sessionDuration = endTimestamp.getTime() - startTimestamp.getTime();
            numberOfTrials = trials.size();

            for (Trial trial: trials) {
                switch (trial.computerSelection) {
                    case GREEN:     greenAnswers++; break;
                    case YELLOW:    yellowAnswers++; break;
                    case RED:       redAnswers++; break;
                    case BLUE:      blueAnswers++; break;
                }

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
            }
        }
    }

    class Trial {
        int trialNumber;
        Selection computerSelection = getRandomColor();
        Timestamp computerSelectionTimestamp = new Timestamp(System.currentTimeMillis());
        Selection userSelection;
        Timestamp userSelectionTimestamp;
        boolean isCorrect = false;
        boolean isPass = false;
        long trialDuration;

        Trial(int trialNumber) {
            this.trialNumber = trialNumber;
        }

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
                correct();
            } else {
                highlightCorrectButton();
                if (selection.equals(Selection.PASS)) {
                    isPass = true;
                }
            }
        }

        public void highlightCorrectButton() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTrialView = findViewById(R.id.current_trial);
        scoreView = findViewById(R.id.score);
        topMessageView = findViewById(R.id.top_message);

        currentSession = new Session();
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
        currentSession = new Session();
    }

    public void complete() {
        Intent completeIntent = new Intent(this, CompleteActivity.class);

        Gson gson = new Gson();
        String completeSession = gson.toJson(currentSession);
        completeIntent.putExtra("completeSession", completeSession);

        Handler handler = new Handler();
        handler.postDelayed((Runnable) () -> startActivity(completeIntent), 600);
    }

    public void correct() {
        Intent correctIntent = new Intent(this, CorrectActivity.class);
        startActivity(correctIntent);
    }

}

