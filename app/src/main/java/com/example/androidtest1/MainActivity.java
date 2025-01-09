package com.example.androidtest1;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewTimer = findViewById(R.id.textViewTimer);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(v);
            }
        });


    }

    private void startTimer(View v) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = createCountDownTimer(50000, 50);
        countDownTimer.start();
    }

    private CountDownTimer createCountDownTimer(int ms, int interval) {
        return new CountDownTimer(ms, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.format("%02d:%02d:%02d", millisUntilFinished / 1000 / 3600, millisUntilFinished / 1000 / 60 % 60, millisUntilFinished / 1000 % 60));
            }

            @Override
            public void onFinish() {

            }
        };
    }
}