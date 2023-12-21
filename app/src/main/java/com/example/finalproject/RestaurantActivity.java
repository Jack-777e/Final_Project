package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RestaurantActivity extends AppCompatActivity {
    private ArrayList<String> restaurantList;
    private ArrayList<SeekBar> seekBars = new ArrayList<>();
    private Map<SeekBar, Integer> seekBarSpeeds = new HashMap<>();
    private Random random = new Random();
    private Handler handler = new Handler();
    private boolean isRaceOver = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        restaurantList = getIntent().getStringArrayListExtra("restaurantList");
        LinearLayout layout = findViewById(R.id.restaurantLayout);

        for (String restaurant : restaurantList) {
            TextView textView = new TextView(this);
            textView.setText(restaurant);
            textView.setTextSize(20);
            SeekBar seekBar = new SeekBar(this);
            seekBars.add(seekBar);
            layout.addView(textView);
            layout.addView(seekBar);
        }

        final Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRaceOver) {
                    isRaceOver = false;
                    for (SeekBar seekBar : seekBars) {
                        seekBar.setProgress(0);
                        seekBarSpeeds.put(seekBar, random.nextInt(3) + 1);
                        updateSeekBar(seekBar);
                    }
                    startButton.setEnabled(false);
                }
            }
        });
    }

    private void updateSeekBar(final SeekBar seekBar) {
        if (!isRaceOver) {
            seekBar.setProgress(seekBar.getProgress() + seekBarSpeeds.get(seekBar));
            if (seekBar.getProgress() == seekBar.getMax()) {
                isRaceOver = true;
                int index = seekBars.indexOf(seekBar);
                String restaurantName = restaurantList.get(index);
                Intent intent = new Intent(RestaurantActivity.this, ResultActivity.class);
                intent.putExtra("restaurantName", restaurantName);
                startActivity(intent);
                Button startButton = findViewById(R.id.startButton);
                startButton.setEnabled(true);
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar(seekBar);
                    }
                }, 7000 / seekBar.getMax());
            }
        }
    }
}
