package com.example.whack_it.game;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whack_it.mk_mole.Mole;
import com.example.whack_it.R;

import java.util.ArrayList;

/**
 * Represents the main game activity for Whack-a-Mole.
 */
public class Game_Activity extends AppCompatActivity
{
    //TODO: Sound buttons to mute/unmute game same for vibration
    //TODO: Maybe Button to pause game/return to menu
    //List to hold the IDs of mole views that will be animated
    public static ArrayList<ImageView> mole_viewsId_list = new ArrayList<>();
    private Game_Instance game_instance;
    private CountDownTimer timer;
    private TextView timer_text;
    private EditText total_points_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initializes the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Populates the mole_viewsId_list with mole views from the layout
        populate_mole_views_list();

        // Initializes good and bad moles
        Mole.create_good_moles();
        Mole.create_bad_moles();

        // Starts the game
        // TODO implement a way to vary game difficulty
        int mole_pop_freq = 500;
        int game_time = 20; // Changed to 20 seconds
        this.game_instance = new Game_Instance(mole_pop_freq, game_time);
        game_instance.start_game();

        // Set timer
        timer_text = findViewById(R.id.timer);
        start_timer(game_instance.get_game_time()); // 20 seconds in milliseconds

        //Set point tracker
        this.total_points_txt = findViewById(R.id.points);
    }

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
     * @param totalTimeInMillis The total time in milliseconds for the countdown timer.
     */
    private void start_countdown_timer(long totalTimeInMillis)
    {
        timer = new CountDownTimer(totalTimeInMillis, 1000)
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
        long minutes = millisUntilFinished / 60000;
        long seconds = (millisUntilFinished % 60000) / 1000;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer_text.setText(timeLeftFormatted);
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

    // Populates the mole_viewsId_list with mole views from the layout
    private void populate_mole_views_list()
    {
        for (int i = 0; i < 9; i++)
        {
            switch (i)
            {
                case 0:
                    this.mole_viewsId_list.add(findViewById(R.id.mole0));
                    break;
                case 1:
                    this.mole_viewsId_list.add(findViewById(R.id.mole1));
                    break;
                case 2:
                    this.mole_viewsId_list.add(findViewById(R.id.mole2));
                    break;
                case 3:
                    this.mole_viewsId_list.add(findViewById(R.id.mole3));
                    break;
                case 4:
                    this.mole_viewsId_list.add(findViewById(R.id.mole4));
                    break;
                case 5:
                    this.mole_viewsId_list.add(findViewById(R.id.mole5));
                    break;
                case 6:
                    this.mole_viewsId_list.add(findViewById(R.id.mole6));
                    break;
                case 7:
                    this.mole_viewsId_list.add(findViewById(R.id.mole7));
                    break;
                case 8:
                    this.mole_viewsId_list.add(findViewById(R.id.mole8));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles the click event on a mole view.
     *
     * @param view The clicked mole view.
     */
    public void on_mole_click(View view)
    {
        game_instance.add_points(1);    // TODO: Check if it is a good or bad mole to add or remove points
        this.total_points_txt.setText("" + game_instance.get_total_points());
        ObjectAnimator moveDown = ObjectAnimator.ofFloat(view, "translationY", 5);
        moveDown.setDuration(1000);
        moveDown.start();
    }

    /**
     * Changes to the Game Over activity.
     */
    public void change_to_game_over_activity()
    {
        Intent intent = new Intent(Game_Activity.this, Game_Over_Activity.class);
        startActivity( intent);
    }
}
