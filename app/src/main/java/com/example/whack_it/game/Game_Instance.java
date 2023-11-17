package com.example.whack_it.game;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.whack_it.mk_mole.Mole;

import java.util.Random;

/**
 * Represents an instance of the Whack-a-Mole game.
 */
public class Game_Instance
{
    // Game state variables
    private boolean is_running;
    private int mole_pop_freq;
    private int total_points;
    private int good_taps;
    private int bad_taps;
    private int game_time;

    /**
     * Constructor for initializing a Game_Instance.
     *
     * @param mole_pop_freq The frequency at which moles pop up.
     * @param game_time     The total duration of the game.
     */
    public Game_Instance(int mole_pop_freq, int game_time)
    {
        this.is_running = false;
        this.mole_pop_freq = mole_pop_freq;
        this.total_points = 0;
        this.good_taps = 0;
        this.bad_taps = 0;
        this.game_time = game_time;
    }

    /**
     * Start the game.
     */
    void start_game()
    {
        this.is_running = true;
        this.random_mole_animation();
        //this.schedule_game_stop();
    }

    /**
     * Animation handler for random mole appearance.
     */
    private Handler handler = new Handler();

    /**
     * Initiates a random mole appearance animation.
     */
    public void random_mole_animation()
    {
        Random random = new Random();
        this.is_running = true;

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (is_running)
                {
                    // Chooses a mole and animates its appearance
                    ImageView chosen_mole = choose_mole();
                    move_mole_up(chosen_mole);
                    handler.postDelayed(this, mole_pop_freq);
                }
            }
        }, mole_pop_freq);
    }

    /**
     * Chooses a random mole view and sets its image resource.
     *
     * @return The ImageView of the chosen mole.
     */
    public ImageView choose_mole()
    {
        Random random = new Random();
        int random_idx = random.nextInt(8);
        int random_idx2 = random.nextInt(9);
        Game_Activity.mole_viewsId_list.get(random_idx).setImageResource(Mole.good_moles.get(random_idx2).getMole_image_id());
        return Game_Activity.mole_viewsId_list.get(random_idx);
    }

    /**
     * Animates the mole to move up.
     *
     * @param mole_image The ImageView of the mole to move.
     */
    public void move_mole_up(ImageView mole_image)
    {
        mole_image.setVisibility(View.VISIBLE);
        ObjectAnimator mole_animation = ObjectAnimator.ofFloat(mole_image, "translationY", -100);
        mole_animation.setDuration(1000);
        mole_animation.start();
    }

    /**
     * Stops the game.
     */
    public void stop_game()
    {
        this.is_running = false;
        handler.removeCallbacksAndMessages(null); // Remove all callbacks and messages from the handler
        // TODO Add any other cleanup
    }

    // Methods for points management

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
}
