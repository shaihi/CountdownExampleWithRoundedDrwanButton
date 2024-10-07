package com.shaihi.countdownexamplewithroundeddrwanbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleTimerView extends View {

    private Paint circlePaint;
    private Paint backgroundPaint;
    private int progress = 0;  // The progress in seconds
    private int maxProgress = 30;  // Default to 30 seconds
    private RectF circleBounds;  // Define the bounds for the arc
    private int strokeWidth = 30; // Width of the circle stroke

    public CircleTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);  // Color of the progress circle
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth/2); // Stroke width of the circle
        circlePaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.GRAY);  // Background color
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);  // Same stroke width for consistency
        backgroundPaint.setAntiAlias(true);

        // Initialize the bounds for the arc, but only set them when we know the actual size
        circleBounds = new RectF();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        // Calculate the center of the view
        float centerX = width / 2f;
        float centerY = height / 2f;

        // Use the smallest dimension to maintain a perfect circle
        int minDimension = Math.min(width, height);

        // Calculate the radius based on the dimensions and stroke width
        float radius = (minDimension / 2f) - (strokeWidth / 2f);

        // Set the circle bounds using the calculated center and radius
        circleBounds.set(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );
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
        canvas.drawArc(circleBounds,-90, sweepAngle, false, circlePaint);
    }

    // Method to update the progress
    public void updateProgress(int secondsLeft) {
        this.progress = maxProgress - secondsLeft;
        invalidate();  // Force re-draw of the view
    }

    // Method to reset the progress
    public void resetProgress() {
        this.progress = 0;
        invalidate();
    }

    // Method to dynamically set the maximum time
    public void setMaxProgress(int seconds) {
        this.maxProgress = seconds;
        resetProgress(); // Reset the circle when max time is changed
    }
}