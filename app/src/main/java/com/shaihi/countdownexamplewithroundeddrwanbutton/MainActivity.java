package com.shaihi.countdownexamplewithroundeddrwanbutton;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CircleTimerView timerButton;
    private CountDownTimer countDownTimer;
    private boolean isFlashing = false;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 30000;  // 30 seconds
    private static final int FLASH_DURATION_MS = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerButton = findViewById(R.id.timerButton);

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    stopFlashing();
                } else {
                    startCountdown();
                }
            }
        });
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int secondsLeft = (int) (timeLeftInMillis / 1000);
                timerButton.updateProgress(secondsLeft);
            }

            @Override
            public void onFinish() {
                // When the timer finishes, make the button start flashing
                startFlashing();
            }
        }.start();
        isTimerRunning = true;
    }

    PorterDuff.Mode bgMode;
    private void startFlashing() {
        isFlashing = true;
        timerButton.setBackgroundColor(3);

        /*new Thread(() -> {
            try {
                while (isFlashing) {
                    runOnUiThread(() -> timerButton.setVisibility(View.INVISIBLE));
                    Thread.sleep(FLASH_DURATION_MS);
                    runOnUiThread(() -> timerButton.setVisibility(View.VISIBLE));
                    Thread.sleep(FLASH_DURATION_MS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/
    }

    private void stopFlashing() {
        isFlashing = false;
        timerButton.setBackgroundColor(getColor(R.drawable.round_button_background));
        timerButton.resetProgress();
        isTimerRunning = false;
        timeLeftInMillis = 30000;  // Reset the timer to 30 seconds
    }
}