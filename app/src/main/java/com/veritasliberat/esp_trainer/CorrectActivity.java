package com.veritasliberat.esp_trainer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CorrectActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);

        handleCorrect();
    }

    @Override
    public void finish() {
        mediaPlayer.release();
        super.finish();
    }

    public void handleCorrect() {
        Vibrator v;
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mediaPlayer = MediaPlayer.create(this, R.raw.winner);
        mediaPlayer.start();
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

        Random rand = new Random();
        int randomInt = rand.nextInt(17) + 1;
        String randomImage = "correct_" + randomInt;
        ImageView img = (ImageView) findViewById(R.id.correct_image);
        int resID = getResources().getIdentifier(randomImage, "drawable",  getPackageName());
        img.setImageResource(resID);

        Handler handler = new Handler();
        handler.postDelayed(this::finish, 1693);
    }
}