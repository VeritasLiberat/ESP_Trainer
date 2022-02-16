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
    public static final int NUMBER_OF_IMAGES = 42;

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
        vibrate();
        playSound();
        showImage();

        Handler handler = new Handler();
        handler.postDelayed(this::finish, MainActivity.CORRECT_DELAY);
    }

    void vibrate() {
        Vibrator v;
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(MainActivity.VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    void playSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.winner);
        mediaPlayer.start();
    }

    void showImage() {
        Random rand = new Random();
        int randomInt = rand.nextInt(NUMBER_OF_IMAGES) + 1;
        String randomImage = "correct_" + randomInt;
        ImageView img = findViewById(R.id.correct_image);
        int resID = getResources().getIdentifier(randomImage, "drawable",  getPackageName());
        img.setImageResource(resID);
    }
}
