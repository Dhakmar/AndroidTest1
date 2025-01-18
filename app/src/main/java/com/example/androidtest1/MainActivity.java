package com.example.androidtest1;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private EditText work_duration_field;
    private EditText rest_duration_field;
    private EditText cycle_amount_field;
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
        work_duration_field = findViewById(R.id.work_duration_field);
        rest_duration_field = findViewById(R.id.rest_duration_field);
        cycle_amount_field = findViewById(R.id.cycle_amount_field);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(v);
            }
        });


    }

    private LinkedList<Round> prepareRounds() {
        LinkedList<Round> rounds = new LinkedList<>();

        int cycles_amount = Integer.parseInt(cycle_amount_field.getText().toString());
        if (cycles_amount < 1) {
            cycles_amount = 1;
        }

        for (int i = 0; i < cycles_amount; i++) {
            int work_duration = Integer.parseInt(work_duration_field.getText().toString());
            int rest_duration = Integer.parseInt(rest_duration_field.getText().toString());

            rounds.add(new Round(work_duration * 1000, RoundType.WORK));
            if (rest_duration > 0) {
                rounds.add(new Round(rest_duration * 1000, RoundType.REST));
            }
        }

        return rounds;
    }


    private void startTimer(View v) {
        LinkedList<Round> rounds = prepareRounds();
        if (!rounds.isEmpty()) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            startRound(rounds);
        }
    }

    private void startRound(LinkedList<Round> rounds) {

        Round round = rounds.poll();
        if (round != null && round.getDuration() != 0) {
            countDownTimer = createCountDownTimer(round.getDuration(), 5, rounds);
            countDownTimer.start();
            textViewTimer.setBackgroundColor(Integer.parseInt(round.getType().getColor()));
        }
    }


    private CountDownTimer createCountDownTimer(int duration, int interval, LinkedList<Round> rounds) {
        return new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(formatTimeForTimer(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                startRound(rounds);
            }
        };
    }

    private String formatTimeForTimer(long time) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

        return minutes != 0 ? String.format(Locale.ENGLISH, "%02d:%02d",
                minutes % 60,
                seconds % 60) : String.valueOf(seconds % 60);
    }

    public void increaseWorkDuration(View view) {
        int currentValue = Integer.parseInt(work_duration_field.getText().toString());
        work_duration_field.setText(String.valueOf(++currentValue));
    }

    public void decreaseWorkDuration(View view) {
        int currentValue = Integer.parseInt(work_duration_field.getText().toString());
        if (currentValue > 0) {
            work_duration_field.setText(String.valueOf(--currentValue));
        }
    }

    public void decreaseRestDuration(View view) {
        int currentValue = Integer.parseInt(rest_duration_field.getText().toString());
        if (currentValue > 0) {
            rest_duration_field.setText(String.valueOf(--currentValue));
        }
    }

    public void increaseRestDuration(View view) {
        int currentValue = Integer.parseInt(rest_duration_field.getText().toString());
        rest_duration_field.setText(String.valueOf(++currentValue));
    }

    public void decreaseCycles(View view) {
        int currentValue = Integer.parseInt(cycle_amount_field.getText().toString());
        if (currentValue > 1) {
            cycle_amount_field.setText(String.valueOf(--currentValue));
        }
    }

    public void increaseCycles(View view) {
        int currentValue = Integer.parseInt(cycle_amount_field.getText().toString());
        cycle_amount_field.setText(String.valueOf(++currentValue));
    }
}