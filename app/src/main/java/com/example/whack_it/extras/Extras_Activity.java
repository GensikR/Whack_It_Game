package com.example.whack_it.extras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whack_it.Main_Activity;
import com.example.whack_it.R;
import com.example.whack_it.utilities.Sound;

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
    private View stats_view;
    private Button close_btn;
    private TextView top_list_txt;
    private EditText score1;
    private EditText score2;
    private EditText score3;
    private EditText score4;
    private EditText score5;
    private TextView good_taps_txt;
    private EditText good_taps;
    private TextView bad_taps_txt;
    private EditText bad_taps;
    private TextView acc_txt;
    private  EditText accuracy;

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

        //Set Stats View
        this.stats_view = findViewById(R.id.stats_view);
        this.close_btn = findViewById(R.id.close_btn);
        set_close_btn();
        this.top_list_txt = findViewById(R.id.top_list_txt);
        this.score1 = findViewById(R.id.score_txt1);
        this.score2 = findViewById(R.id.score_txt2);
        this.score3 = findViewById(R.id.score_txt3);
        this.score4 = findViewById(R.id.score_txt4);
        this.score5 = findViewById(R.id.score_txt5);
        this.good_taps_txt = findViewById(R.id.good_txt);
        this.good_taps = findViewById(R.id.good_taps);
        this.bad_taps_txt = findViewById(R.id.bad_txt);
        this.bad_taps = findViewById(R.id.bad_taps);
        this.acc_txt = findViewById(R.id.acc_txt);
        this.accuracy = findViewById(R.id.accuracy);

       //TODO: set_all_stats_txt(); to whenever we get stats implemented to the game activity

    }

    private void set_close_btn()
    {
        this.close_btn.setOnClickListener(v ->
        {
            hide_stats_view();
        });
    }

    private void set_all_stats_txt()
    {
        this.score1.setText(Stats.best_scores.get(0));
        this.score2.setText(Stats.best_scores.get(1));
        this.score3.setText(Stats.best_scores.get(2));
        this.score4.setText(Stats.best_scores.get(3));
        this.score5.setText(Stats.best_scores.get(4));
        this.good_taps.setText(Stats.good_taps);
        this.bad_taps.setText(Stats.bad_taps);
        this.accuracy.setText(Stats.good_taps / Stats.bad_taps);
    }

    private void spawn_stats_view()
    {
        this.stats_view.setVisibility(View.VISIBLE);
        this.close_btn.setVisibility(View.VISIBLE);
        this.top_list_txt.setVisibility(View.VISIBLE);
        this.score1.setVisibility(View.VISIBLE);
        this.score2.setVisibility(View.VISIBLE);
        this.score3.setVisibility(View.VISIBLE);
        this.score4.setVisibility(View.VISIBLE);
        this.score5.setVisibility(View.VISIBLE);
        this.good_taps_txt.setVisibility(View.VISIBLE);
        this.good_taps.setVisibility(View.VISIBLE);
        this.bad_taps_txt.setVisibility(View.VISIBLE);
        this.bad_taps.setVisibility(View.VISIBLE);
        this.acc_txt.setVisibility(View.VISIBLE);
        this.accuracy.setVisibility(View.VISIBLE);
    }
    private void hide_stats_view()
    {
        this.stats_view.setVisibility(View.GONE);
        this.close_btn.setVisibility(View.GONE);
        this.top_list_txt.setVisibility(View.GONE);
        this.score1.setVisibility(View.GONE);
        this.score2.setVisibility(View.GONE);
        this.score3.setVisibility(View.GONE);
        this.score4.setVisibility(View.GONE);
        this.score5.setVisibility(View.GONE);
        this.good_taps_txt.setVisibility(View.GONE);
        this.good_taps.setVisibility(View.GONE);
        this.bad_taps_txt.setVisibility(View.GONE);
        this.bad_taps.setVisibility(View.GONE);
        this.acc_txt.setVisibility(View.GONE);
        this.accuracy.setVisibility(View.GONE);
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
    {
        this.stats_btn.setOnClickListener(v ->
        {
            spawn_stats_view();
        });
    }

    private void set_tutorial_btn()
    {
        // Set click listener
        this.tutorial_btn.setOnClickListener(v ->
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