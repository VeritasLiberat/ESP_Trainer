package com.veritasliberat.esp_trainer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        Button search = (Button) findViewById(R.id.menu_train);
//        Drawable drawable = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_send);
//        search.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
    }

    public void train(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void metrics(View view) {
        Intent intent = new Intent(this, MetricsActivity.class);
        startActivity(intent);
    }

    public void history(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void info(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
}
