package com.shaihi.countdownexamplewithroundeddrwanbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleTimerView extends View {

    private Paint circlePaint;
    private Paint backgroundPaint;
    private int progress = 0;  // The progress in seconds
    private int maxProgress = 30;  // Total time of 30 seconds

    public CircleTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.GREEN);  // Color of the circle
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(15);
        circlePaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.GRAY);  // Background color
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(15);
        backgroundPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 20; // Radius of the circle

        // Draw the background circle
        canvas.drawCircle(width / 2, height / 2, radius, backgroundPaint);

        // Draw the progress circle (portion of the full circle)
        float sweepAngle = 360f * progress / maxProgress;
        canvas.drawArc(20, 20, width - 20, height - 20, -90, sweepAngle, false, circlePaint);
    }

    // Method to update the progress
    public void updateProgress(int secondsLeft) {
        this.progress = maxProgress - secondsLeft;
        invalidate();  // Force re-draw of the view
    }

    // Method to reset progress
    public void resetProgress() {
        this.progress = 0;
        invalidate();
    }
}