package com.example.whack_it.extras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;
import android.media.AudioManager;

import com.example.whack_it.Main_Activity;
import com.example.whack_it.R;
import com.example.whack_it.Sound;

/**
 * Activity displaying game extras
 */
public class Extras_Activity extends AppCompatActivity
{
    private Button mute_btn;
    private Button vibration_btn;
    private Button stats_btn;
    private Button tutorial_btn;
    private Button return_btn;
    private AudioManager audioManager;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        //initialize buttons
        this.mute_btn = findViewById(R.id.mute_BTN);
        this.vibration_btn = findViewById(R.id.vibration_BTN);
        this.stats_btn = findViewById(R.id.stats_BTN);
        this.tutorial_btn = findViewById(R.id.tutorial_BTN);
        this.return_btn = findViewById(R.id.return_BTN);

        //Set audio and vibration
        this.audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Initialize listeners
        set_mute_btn();
        set_vibration_btn();
        set_stats_btn();
        set_tutorial_btn();
        set_return_btn();

    }

    private void set_mute_btn()
    {
        this.mute_btn.setOnClickListener( v ->
        {
            if (Sound.is_muted)
            {
                // Unmute the game
                this.mute_btn.setText("Mute");
                this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.USE_DEFAULT_STREAM_TYPE, 0);
            }
            else
            {
                // Mute the game
                this.mute_btn.setText("Unmute");
                this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            }
            Sound.is_muted = !Sound.is_muted;
        });
    }

    private void set_vibration_btn()
    {
        this.vibration_btn.setOnClickListener( v ->
        {
            if (Sound.is_vibrating)
            {
                // Turn off vibration
                this.vibration_btn.setText("Turn Vibration: ON");
                this.vibrator.cancel();
                Sound.is_vibrating = false;
            }
            else
            {
                // Turn on vibration
                this.vibration_btn.setText("Turn Vibration: OFF");
                long[] pattern = {0, 1000, 1000}; // Vibration pattern (0ms delay, vibrate for 1000ms, pause for 1000ms)
                vibrator.vibrate(pattern, 0);
                Sound.is_vibrating = true;
            }
        });
    }

    private void set_stats_btn()
    {//TODO: Set stats popup window
    }

    private void set_tutorial_btn()
    {
        // Set click listener
        this.return_btn.setOnClickListener(v ->
        {
            // Change activities
            Intent intent = new Intent(Extras_Activity.this, Tutorial_Activity.class);
            startActivity(intent);
        });
    }

    private void set_return_btn()
    {
        // Set click listener
        this.return_btn.setOnClickListener(v ->
        {
            // Change activities
            Intent intent = new Intent(Extras_Activity.this, Main_Activity.class);
            startActivity(intent);
        });
    }
}