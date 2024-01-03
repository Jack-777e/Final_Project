package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> restaurantList = new ArrayList<>();
    private LinearLayout restaurantLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantLayout = findViewById(R.id.restaurantLayout);
        final EditText editText = findViewById(R.id.editText);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String restaurantName = editText.getText().toString();
                if (!restaurantName.isEmpty()) {
                    restaurantList.add(restaurantName);
                    editText.setText("");

                    final View restaurantView = getLayoutInflater().inflate(R.layout.restaurant_item, null);

                    TextView restaurantNameView = restaurantView.findViewById(R.id.restaurantName);
                    restaurantNameView.setText(restaurantName);
                    restaurantNameView.setTextSize(20);

                    Button deleteButton = restaurantView.findViewById(R.id.deleteButton);
                    deleteButton.setOnClickListener(v1 -> {
                        restaurantLayout.removeView(restaurantView);
                        restaurantList.remove(restaurantName);
                    });

                    restaurantLayout.addView(restaurantView);
                } else {
                    Toast.makeText(MainActivity.this, "請輸入餐廳名稱", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restaurantList.size() < 2) {
                    Toast.makeText(MainActivity.this, "請輸入餐廳名稱，至少兩個", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                    intent.putStringArrayListExtra("restaurantList", restaurantList);
                    startActivity(intent);
                }
            }
        });
    }
}
