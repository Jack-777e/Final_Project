package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    // 創建一個 Handler 對象
    private Handler handler = new Handler();
    // 創建一個 Runnable 對象
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(ResultActivity.this, VideoMainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String restaurantName = getIntent().getStringExtra("restaurantName");
        String resultText = "你選擇的餐廳是: " + restaurantName;

        Spannable spannable = new SpannableString(resultText);
        spannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 9, resultText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView resultView = findViewById(R.id.resultView);
        resultView.setText(spannable);

        Button reAddButton = findViewById(R.id.reAddButton);
        reAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button reChooseButton = findViewById(R.id.reChooseButton);
        reChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 延遲 5 秒執行 Runnable
        handler.postDelayed(runnable, 5000);
    }

}
