package com.example.whack_it.game;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whack_it.utilities.Sound;
import com.example.whack_it.extras.Stats;
import com.example.whack_it.mk_mole.Mole;
import com.example.whack_it.R;

import java.util.ArrayList;

/**
 * Represents the main game activity for Whack-a-Mole.
 *
 */
public class Game_Activity extends AppCompatActivity
{
    //List to hold the IDs of mole views that will be animated
    public static ArrayList<ImageView> mole_viewsId_list = new ArrayList<>();
    //test
    private Game_Instance game_instance;
    private String difficulty;
    private Difficulty game_difficulty;
    private CountDownTimer timer;
    private long remaining_time;
    private TextView timer_text;
    private EditText total_points_txt;
    private View pause_view;
    private Button pause_btn;
    private Button mute_btn;
    private Button vibration_btn;
    private Button exit_btn;
    private Button continue_btn;
    private AudioManager audioManager;
    private TextView pause_text;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initializes the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent this_intent = getIntent();

        //Set audio and vibration
        this.audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Set Pause Menu Up
        this.pause_view = findViewById(R.id.pause_view);
        this.pause_text = findViewById(R.id.pauseTXT);
        this.pause_btn = findViewById(R.id.pauseBTN);
        this.mute_btn = findViewById(R.id.muteBTN);
        this.vibration_btn = findViewById(R.id.vibrationBTN);
        this.continue_btn = findViewById(R.id.continueBTN);
        this.exit_btn = findViewById(R.id.exitBTN);


        // Starts the game

        // Get Difficulty
        this.difficulty = this_intent.getStringExtra("difficulty");
        this.game_difficulty = new Difficulty(this.difficulty);
        // Create a new instance if none exists
        game_instance = new Game_Instance(this.game_difficulty);

        this.game_instance.start_game();

        // Set timer
        timer_text = findViewById(R.id.timer);
        start_timer(game_instance.get_game_time()); // 20 seconds in milliseconds

        //Set point tracker
        this.total_points_txt = findViewById(R.id.points);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Game_Activity.mole_viewsId_list.clear();
        Mole.good_moles.clear();
        Mole.bad_moles.clear();
        finish();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //Set Buttons Up
        set_pause_btn();
        set_mute_btn();
        set_vibration_btn();
        set_continue_btn();
        set_exit_btn();

        // Populates the mole_viewsId_list with mole views from the layout
        populate_mole_views_list();

        // Initializes good and bad moles
        Mole.create_good_moles();
        Mole.create_bad_moles();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // Cancel the countdown timer to prevent memory leaks
        if (timer != null)
        {
            timer.cancel();
        }
    }

    /*
        Timers Settings
     */
    /**
     * Starts the countdown timer for the game.
     *
     * @param time The total time for the countdown timer.
     */
    private void start_timer(int time)
    {
        int total_time = time * 1000;
        start_countdown_timer(total_time);
    }

    /**
     * Initializes and starts the countdown timer.
     *
     * @param total_time_in_millis The total time in milliseconds for the countdown timer.
     */
    private void start_countdown_timer(long total_time_in_millis)
    {
        timer = new CountDownTimer(total_time_in_millis, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                update_timer_text(millisUntilFinished);
            }

            @Override
            public void onFinish()
            {
                // Handle actions when the timer finishes (e.g., end the game)
                game_instance.stop_game();
                change_to_game_over_activity();
            }
        };

        // Start the countdown timer
        timer.start();
    }

    /**
     * Updates the timer text based on the remaining time.
     *
     * @param millisUntilFinished The time remaining in milliseconds.
     */
    private void update_timer_text(long millisUntilFinished)
    {
        this.remaining_time = millisUntilFinished;
        long minutes = millisUntilFinished / 60000;
        long seconds = (millisUntilFinished % 60000) / 1000;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer_text.setText(timeLeftFormatted);
    }

    /**
     * Handles the click event on a mole view.
     *
     * @param view The clicked mole view.
     */
    public void on_mole_click(View view)
    {
        Mole mole = (Mole) view.getTag();
        mole.set_is_hidden(true);
        if (mole.is_mole_bad())
        {
            game_instance.increase_good_taps_stats();
            game_instance.add_points(1);
        }
        else
        {
            game_instance.increase_bad_taps_stats();
            game_instance.remove_points(1);
        }

        this.total_points_txt.setText("" + game_instance.get_total_points());

        ObjectAnimator moveDown = ObjectAnimator.ofFloat(view, "translationY", 5);
        moveDown.setDuration(1000);

        // Set up an AnimatorListener to handle the visibility change after the animation completes
        moveDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ended, set the view to invisible
                view.setVisibility(View.INVISIBLE);
                int hole_idx = mole.get_current_hole_idx();
                game_instance.set_hole_filled(hole_idx, false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeated
            }
        });

        // Start the animation
        moveDown.start();
    }


    /*
     Pause Menu UI Elements
    */
    private void set_pause_btn()
    {
        this.pause_btn.setOnClickListener( v ->
        {   //pause the whole game
            if (timer != null)
            {
                timer.cancel();
            }
            this.game_instance.stop_game();
            //Make Everything Visible
            this.pause_view.setVisibility(View.VISIBLE);
            this.pause_text.setVisibility(View.VISIBLE);
            this.mute_btn.setVisibility(View.VISIBLE);
            this.vibration_btn.setVisibility(View.VISIBLE);
            this.continue_btn.setVisibility(View.VISIBLE);
            this.exit_btn.setVisibility(View.VISIBLE);
        });
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

    private void set_exit_btn()
    {
        this.exit_btn.setOnClickListener( v ->
        {
            //Changes to game over activity
            Intent intent = new Intent(Game_Activity.this, Game_Over_Activity.class);
            startActivity(intent);
        });
    }

    private void set_continue_btn()
    {
        this.continue_btn.setOnClickListener( v ->
        {
            this.game_instance.set_is_running(true);
            start_countdown_timer(this.remaining_time);
            this.game_instance.start_game();
            //Make Everything Disappear
            this.pause_view.setVisibility(View.GONE);
            this.pause_text.setVisibility(View.GONE);
            this.mute_btn.setVisibility(View.GONE);
            this.vibration_btn.setVisibility(View.GONE);
            this.continue_btn.setVisibility(View.GONE);
            this.exit_btn.setVisibility(View.GONE);
            this.pause_view.setVisibility(View.GONE);
        });
    }

    // Populates the mole_viewsId_list with mole views from the layout
    private void populate_mole_views_list()
    {
        int[] moleViewIds =
                {
                    R.id.mole0, R.id.mole1, R.id.mole2,
                    R.id.mole3, R.id.mole4, R.id.mole5
                };

        for (int i = 0; i < moleViewIds.length; i++)
        {
            ImageView moleView = findViewById(moleViewIds[i]);
            // Set the Mole instance as the tag for the ImageView
            this.mole_viewsId_list.add(moleView);
        }
    }


    /**
     * Changes to the Game Over activity.
     */
    public void change_to_game_over_activity()
    {
        Stats.good_taps = game_instance.get_good_taps();
        Stats.bad_taps = game_instance.get_bad_taps();
        Stats.update_accuracy();

        Intent intent = new Intent(Game_Activity.this, Game_Over_Activity.class);
        intent.putExtra("total points", game_instance.get_total_points());
        intent.putExtra("good taps", game_instance.get_good_taps());
        intent.putExtra("bad taps", game_instance.get_bad_taps());
        finish();
        startActivity(intent);
    }
}
