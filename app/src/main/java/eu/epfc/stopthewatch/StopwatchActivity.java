package eu.epfc.stopthewatch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {
    TextView timeTextView;
    private int seconds;
    private boolean running;
    private boolean wasRunning;

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    public void onClickShare(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"My time is "+ seconds+" seconds.");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        timeTextView = findViewById(R.id.timeDisplay);
        if(savedInstanceState != null){
            this.seconds = savedInstanceState.getInt("seconds");
            this.running = savedInstanceState.getBoolean("running");
            this.wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

    }

    /*@Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;


    }

    @Override
    protected void onStart() {
        super.onStart();
        running = wasRunning;
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = wasRunning;
    }

    private void runTimer() {
        final Handler handler = new Handler();

        Runnable stopwatchUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeTextView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        };
        handler.post(stopwatchUpdateRunnable);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("wasRunning", wasRunning);
    }
}
