package com.example.whack_it.extras;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whack_it.Main_Activity;
import com.example.whack_it.game.Difficulty;
import com.example.whack_it.game.Game_Activity;
import com.example.whack_it.game.Game_Instance;
import com.example.whack_it.game.Game_Over_Activity;
import com.example.whack_it.utilities.Sound;
import com.example.whack_it.extras.Stats;
import com.example.whack_it.mk_mole.Mole;
import com.example.whack_it.R;

import java.util.ArrayList;

/**
 * Represents the main game activity for Whack-a-Mole.
 *
 */
public class Tutorial_Activity extends AppCompatActivity
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
    private boolean is_running;
    private int mole_pop_freq;
    private int game_time;
    private int streak_target;
    private int total_points;
    private ImageView chosen_mole;
    private ImageView chosen_mole2;
    private Runnable mole_animation_runn;
    private Handler handler = new Handler();
    private ObjectAnimator mole_animation;
    private int flag_mole_type;
    private int current_index;
    private boolean is_this_mole_up;
    private Button begin_tutorial_btn;
    private Button timer_tutorial_btn;
    private Button points_tutorial_btn;
    private Button mole_tutorial_btn;
    private Button pause_tutorial_btn;
    private Button pase_tutorial_btn2;
    private Button end_tutorial_btn;
    private Button tutorial_step_btns[] = new Button[7];
    private int current_tutorial_index;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initializes the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

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


        //Set point tracker
        this.total_points_txt = findViewById(R.id.points);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Tutorial_Activity.mole_viewsId_list.clear();
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

        // Populates the mole_viewsId_list with mole views from the layout
        populate_mole_views_list();

        // Initializes good and bad moles
        Mole.create_good_moles();
        Mole.create_bad_moles();


        // Starts the tutorial settings and flags
        this.game_difficulty = new Difficulty("tutorial");
        this.flag_mole_type = 0;
        this.current_index = 0;
        this.chosen_mole = findViewById(R.id.mole1);
        this.chosen_mole.setImageResource(R.drawable.bad_mole);
        this.chosen_mole2 = findViewById(R.id.mole4);
        this.chosen_mole2.setImageResource(R.drawable.good_mole);
        this.mole_pop_freq = this.game_difficulty.getMole_pop_freq();
        this.game_time = this.game_difficulty.getInitial_time();
        this.streak_target = this.game_difficulty.getStreak_to_special_ability();


        //Set tutorial
        set_tutorial_btns();

        // Set timer
        timer_text = findViewById(R.id.timer);
        start_timer(this.game_time); // 20 seconds in milliseconds

        //set animation
        this.is_this_mole_up = false;


        start_tutorial();

    }

    private void populate_tutorial_steps()
    {
        this.current_tutorial_index = 0;
        this.tutorial_step_btns = new Button[]{this.begin_tutorial_btn, this.timer_tutorial_btn, this.mole_tutorial_btn, this.points_tutorial_btn, this.pause_tutorial_btn, this.pase_tutorial_btn2, this.end_tutorial_btn};
    }

    private void start_tutorial()
    {
        this.is_running = true;
        this.begin_tutorial_btn.setVisibility(View.VISIBLE);
    }

    private void pause_tutorial()
    {
        this.is_running = false;
        //pause the whole game
        if (timer != null)
        {
            timer.cancel();
        }

    }

    private void resume_tutorial(Button next_tutorial_btn)
    {
        this.is_running = true;
        start_countdown_timer(this.remaining_time);
        next_tutorial_btn.setVisibility(View.VISIBLE);
    }

    private void set_tutorial_btns()
    {
        this.begin_tutorial_btn = findViewById(R.id.begin_tutorial_btn);
        this.timer_tutorial_btn = findViewById(R.id.timer_tutorial_btn);
        this.points_tutorial_btn = findViewById(R.id.points_tutorial_btn);
        this.mole_tutorial_btn = findViewById(R.id.moles_tutorial_btn);
        this.pause_tutorial_btn = findViewById(R.id.pause_tutorial_btn);
        this.pase_tutorial_btn2 = findViewById(R.id.pause_tutorial_btn2);
        this.end_tutorial_btn = findViewById(R.id.endgame_tutorial_btn);

        //set on click actions
        this.begin_tutorial_btn.setOnClickListener(v ->
        {
            v.setVisibility(View.GONE);
            this.timer_tutorial_btn.setVisibility(View.VISIBLE);
        });
        this.timer_tutorial_btn.setOnClickListener(v ->
        {
            v.setVisibility(View.GONE);
            this.mole_tutorial_btn.setVisibility(View.VISIBLE);
            move_mole_up(this.chosen_mole);
            move_mole_up(this.chosen_mole2);
        });
        this.mole_tutorial_btn.setOnClickListener(v ->
        {
            v.setVisibility(View.GONE);
            move_mole_down(this.chosen_mole);
            move_mole_down(this.chosen_mole2);
            this.points_tutorial_btn.setVisibility(View.VISIBLE);
        });
        this.points_tutorial_btn.setOnClickListener(v ->
        {
            this.chosen_mole.setVisibility(View.GONE);
            this.chosen_mole2.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
            this.pause_tutorial_btn.setVisibility(View.VISIBLE);
            spawn_pause();
        });
        this.pause_tutorial_btn.setOnClickListener(v ->
        {
            v.setVisibility(View.GONE);
            this.pase_tutorial_btn2.setVisibility(View.VISIBLE);
        });
        this.pase_tutorial_btn2.setOnClickListener(v ->
        {
            hide_pause();
            v.setVisibility(View.GONE);
            this.end_tutorial_btn.setVisibility(View.VISIBLE);
        });
        this.end_tutorial_btn.setOnClickListener(v ->
        {
            return_to_extras_activities();
        });

    }

    private void return_to_extras_activities()
    {
        // Change activities
        Intent intent = new Intent(Tutorial_Activity.this, Extras_Activity.class);
        startActivity(intent);
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

    public void random_mole_animation()
    {
        this.mole_animation_runn = new Runnable() {
            @Override
            public void run() {
                if (is_running && !is_this_mole_up) {
                    choose_mole();
                    move_mole_up(chosen_mole);
                    handler.postDelayed(mole_animation_runn, mole_pop_freq);
                }
            }
        };

        handler.postDelayed(this.mole_animation_runn, mole_pop_freq);
    }

    private void choose_mole()
    {
        if(this.flag_mole_type == 0)
        {
            this.chosen_mole.setImageResource(Mole.bad_moles.get(this.current_index).getMole_image_id());
            this.flag_mole_type = 1;
            if(this.current_index < 10)
            {
                this.current_index++;
            }
            else
            {
                this.current_index = 0;
            }
        }
        else
        {
            this.chosen_mole.setImageResource(Mole.good_moles.get(current_index).getMole_image_id());
            this.flag_mole_type = 0;
            if(this.current_index < 10)
            {
                this.current_index++;
            }
            else
            {
                this.current_index = 0;
            }
        }
        this.is_this_mole_up = true;
    }

    /**
     * Animates the mole to move up.
     *
     * @param mole_image The ImageView of the mole to move.
     */
    public void move_mole_up(ImageView mole_image)
    {
            mole_image.setVisibility(View.VISIBLE);
            this.is_this_mole_up = true;
            this.mole_animation = ObjectAnimator.ofFloat(mole_image, "translationY", -100);
            this.mole_animation.setDuration(1000);
            this.mole_animation.start();
    }

    private void move_mole_down(ImageView mole_image)
    {
        this.is_this_mole_up = false;
        ObjectAnimator moveDown = ObjectAnimator.ofFloat(mole_image, "translationY", 0);
        moveDown.setDuration(1000);
        moveDown.start();
    }


    /**
     * Adds points to the total score.
     *
     * @param points The points to add.
     */
    public void add_points(int points)
    {
        this.total_points += points;
    }

    /**
     * Removes points from the total score.
     *
     * @param points The points to remove.
     */
    public void remove_points(int points)
    {
        this.total_points -= points;
    }

    /**
     * Gets the total points.
     *
     * @return The total points.
     */
    public int get_total_points()
    {
        return this.total_points;
    }

    /**
     * Gets the total game time.
     *
     * @return The total game time.
     */
    public int get_game_time()
    {
        return this.game_time;
    }

    public void set_is_running(boolean is_running)
    {
        this.is_running = is_running;
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
        Mole mole;
        if(this.flag_mole_type == 0)
        {
            mole = Mole.bad_moles.get(this.current_index);
        }
        else
        {
            mole = Mole.good_moles.get(this.current_index);
        }
        mole.set_is_hidden(true);
        if (mole.is_mole_bad())
        {
            add_points(1);
        }
        else
        {
            remove_points(1);
        }

        this.total_points_txt.setText("" + get_total_points());

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
                random_mole_animation();
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

        this.is_this_mole_up = false;
        // Start the animation
        if(this.is_running)
        {
            moveDown.start();
        }
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
            spawn_pause();
        });
    }

    private void spawn_pause()
    {
        this.is_running = false;
        //Make Everything Visible
        this.pause_view.setVisibility(View.VISIBLE);
        this.pause_text.setVisibility(View.VISIBLE);
        this.mute_btn.setVisibility(View.VISIBLE);
        this.vibration_btn.setVisibility(View.VISIBLE);
        this.continue_btn.setVisibility(View.VISIBLE);
        this.exit_btn.setVisibility(View.VISIBLE);
    }

    private void hide_pause()
    {
        this.is_running = true;
        //Make Everything Visible
        this.pause_view.setVisibility(View.GONE);
        this.pause_text.setVisibility(View.GONE);
        this.mute_btn.setVisibility(View.GONE);
        this.vibration_btn.setVisibility(View.GONE);
        this.continue_btn.setVisibility(View.GONE);
        this.exit_btn.setVisibility(View.GONE);
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

}
