package com.veritasliberat.esp_trainer;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class CompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        displayCompletion();
    }

    protected void displayCompletion() {
        Gson gson = new Gson();
        MainActivity.Session session = gson.fromJson(
                getIntent().getStringExtra("completeSession"), MainActivity.Session.class);

        String score = Integer.toString(session.score);
        String resultMessage = session.topMessage;
        if (resultMessage.equals("Select the Correct Square")) {
            resultMessage = "Better Luck Next Time";
        }

        TextView scoreView = findViewById(R.id.complete_score);
        scoreView.setText(score);

        TextView resultMessageView = findViewById(R.id.result_message);
        resultMessageView.setText(resultMessage);

        // Selections
        TextView greenSelectionsView = findViewById(R.id.green_selections);
        greenSelectionsView.setText(Integer.toString(session.greenSelections));
        greenSelectionsView.setShadowLayer(24,4,4, Color.BLACK);

        TextView yellowSelectionsView = findViewById(R.id.yellow_selections);
        yellowSelectionsView.setText(Integer.toString(session.yellowSelections));
        yellowSelectionsView.setShadowLayer(24,4,4, Color.BLACK);

        TextView redSelectionsView = findViewById(R.id.red_selections);
        redSelectionsView.setText(Integer.toString(session.redSelections));
        redSelectionsView.setShadowLayer(24,4,4, Color.BLACK);

        TextView blueSelectionsView = findViewById(R.id.blue_selections);
        blueSelectionsView.setText(Integer.toString(session.blueSelections));
        blueSelectionsView.setShadowLayer(24,4,4, Color.BLACK);

        // Answers
        TextView greenAnswersView = findViewById(R.id.green_answers);
        greenAnswersView.setText(Integer.toString(session.greenAnswers));
        greenAnswersView.setShadowLayer(24,4,4, Color.BLACK);

        TextView yellowAnswersView = findViewById(R.id.yellow_answers);
        yellowAnswersView.setText(Integer.toString(session.yellowAnswers));
        yellowAnswersView.setShadowLayer(24,4,4, Color.BLACK);

        TextView redAnswersView = findViewById(R.id.red_answers);
        redAnswersView.setText(Integer.toString(session.redAnswers));
        redAnswersView.setShadowLayer(24,4,4, Color.BLACK);

        TextView blueAnswersView = findViewById(R.id.blue_answers);
        blueAnswersView.setText(Integer.toString(session.blueAnswers));
        blueAnswersView.setShadowLayer(24,4,4, Color.BLACK);

        // Correct
        TextView greenCorrectView = findViewById(R.id.green_correct);
        greenCorrectView.setText(Integer.toString(session.greenCorrect));
        greenCorrectView.setShadowLayer(24,4,4, Color.BLACK);

        TextView yellowCorrectView = findViewById(R.id.yellow_correct);
        yellowCorrectView.setText(Integer.toString(session.yellowCorrect));
        yellowCorrectView.setShadowLayer(24,4,4, Color.BLACK);

        TextView redCorrectView = findViewById(R.id.red_correct);
        redCorrectView.setText(Integer.toString(session.redCorrect));
        redCorrectView.setShadowLayer(24,4,4, Color.BLACK);

        TextView blueCorrectView = findViewById(R.id.blue_correct);
        blueCorrectView.setText(Integer.toString(session.blueCorrect));
        blueCorrectView.setShadowLayer(24,4,4, Color.BLACK);

    }
}
