package com.shaihi.countdownexamplewithroundeddrwanbutton;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CircleTimerView circleTimerView;
    private Button startButton;
    private EditText inputTime;
    private Handler handler = new Handler();
    private int timeInSeconds = 3; // Default to 3 seconds
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleTimerView = findViewById(R.id.circleTimerView);
        startButton = findViewById(R.id.startButton);
        inputTime = findViewById(R.id.timeInput);

        startButton.setOnClickListener(v -> {
            if (!isTimerRunning) {
                // Get user input time or use default
                startButton.setEnabled(false);
                String input = inputTime.getText().toString();
                if (!input.isEmpty()) {
                    timeInSeconds = Integer.parseInt(input);
                }

                circleTimerView.setMaxProgress(timeInSeconds);  // Set timer based on input
                startCountdown(timeInSeconds);  // Start the timer

                isTimerRunning = true;
                startButton.setText("Restart Timer");
            } else {
                // Reset timer if clicked again
                resetTimer();
            }
        });
    }

    private void startCountdown(int totalSeconds) {
        new Thread(() -> {
            for (int i = totalSeconds; i >= 0; i--) {
                final int secondsLeft = i;
                runOnUiThread(() -> circleTimerView.updateProgress(secondsLeft));
                try {
                    Thread.sleep(1000);  // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isTimerRunning = false;  // Timer has ended, allow restart
            // Enable the button after the countdown finishes
            runOnUiThread(() -> startButton.setEnabled(true));
        }).start();
    }

    private void resetTimer() {
        isTimerRunning = false;
        circleTimerView.resetProgress();
        startButton.setText("Start Timer");
    }
}