package com.vairon.baymax;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vairon on 1/4/2016.
 */
public class TimerView {

    private String TAG = this.getClass().getName();

    //Used to display a toast
    private Context context;
    private View view;
    private TextView titleTV, timerTV;
    private ProgressBar progressBar;
    //Modifies the color of the progress bar
    private GradientDrawable bgShape;
    private CountDownTimer timer;
    private int seconds;
    private long millis;
    private boolean pausedFlag;
    private long lastMillis;
    private int percentage = 0;
    private long newMillis = 0;
    //used to create notification for each timer
    private int timerID;
    //Used to keep track of a running timer
    private boolean timerRunning;
    private int progressLoadingColor, progressFinishedColor;


    public TimerView(View timerView, Context _context, int _timerID) {
        view = timerView;

        titleTV = (TextView) timerView.findViewById(R.id.timerTitleTV);
        timerTV = (TextView) timerView.findViewById(R.id.timerTV);
        progressBar = (ProgressBar) timerView.findViewById(R.id.progressBar);
        bgShape = (GradientDrawable) progressBar.getProgressDrawable();

        timerID = _timerID;

        context = _context;

        seconds = 0;
        lastMillis = 0;

        pausedFlag = false;
        timerRunning = false;

        progressLoadingColor = ContextCompat.getColor(context, R.color.progressBarLoading);
        progressFinishedColor = ContextCompat.getColor(context, R.color.progressBarFinished);


    }

    public void setTime(int _seconds) {
        seconds = _seconds;

        //Convert to millis
        millis = seconds * 1000;

        //Set the hours, minutes, and seconds.
        int textViewSeconds = (int) (getMillis() / 1000) % 60;

        progressBar.setProgress(0);
        String time;
        if (seconds * 1000 == 86400000) {
            time = String.format("%02d:%02d:%02d", 23, 59, 59);
        } else {
            time = String.format("%02d", textViewSeconds);
        }

        timerTV.setText(time);

        initTimer();
    }

    public void setTitleTV(String title) {
        titleTV.setText(title);
    }

    public void initTimer() {

        timer = new CountDownTimer(getMillis(), 10) {

            @Override
            public void onTick(long millisUntilFinished) {

                int hours, minutes, seconds;

                lastMillis = millisUntilFinished;

                if (pausedFlag) {

                    long currentMillis = millisUntilFinished - newMillis;

                    lastMillis = currentMillis;

                    //Convert the milliseconds left into hours, minutes, and seconds respectively
                    seconds = (int) (currentMillis / 1000) % 60;
                    percentage = (int) ((getMillis() - currentMillis) * 100 / getMillis());

                    if (currentMillis <= 0) {
                        this.onFinish();
                        stopTimer();
                    }

                } else {

                    //Convert the milliseconds left into hours, minutes, and seconds respectively
                    seconds = (int) (millisUntilFinished / 1000) % 60;
                    percentage = (int) ((getMillis() - millisUntilFinished) * 100 / getMillis());

                }

                //Save the lastMillis if it was not paused
                //newMillis = getMillis() - millisUntilFinished;

                String time = String.format("%02d", seconds);
                timerTV.setText(time);
                progressBar.setProgress(percentage);
            }

            @Override
            public void onFinish() {
                //Reset the time of the timer
                timerTV.setText("0");

                //Set the progress to 100 and set the color of the progress bar to green
                progressBar.setProgress(100);
                bgShape.setColor(progressFinishedColor);
                timerRunning = false;

            }

        };
    }

    public void startTimer() {
        if (timer != null) {
            if (timerRunning) {
                //Don't do anything if the timer is already running
            } else {
                bgShape.setColor(progressLoadingColor);
                timerRunning = true;
                timer.start();
            }
        } else {
            Toast.makeText(context, "Please enter new values for the timer.", Toast.LENGTH_LONG).show();
        }
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timerRunning = false;
        }
    }

    public void pauseTimer() {
        //Check for last millis otherwise the timer will immediately finish
        if (lastMillis != 0) {

            pausedFlag = true;
            newMillis = getMillis() - lastMillis;
            this.stopTimer();
        }
    }

    public View getView() {
        return view;
    }

    public void clearTimer() {
        titleTV.setText(null);

        seconds = 0;
        millis = 0;
        pausedFlag = false;
        lastMillis = 0;
        percentage = 0;
        newMillis = 0;

        timerTV.setText("0");
        if (timer != null) {
            stopTimer();
        }
        progressBar.setProgress(0);
        timer = null;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    public long getMillis() {
        return millis;
    }
}
