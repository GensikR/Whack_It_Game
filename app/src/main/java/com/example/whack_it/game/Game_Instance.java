package com.example.whack_it.game;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.whack_it.Img_Src;
import com.example.whack_it.mk_mole.Mole;

import java.util.Random;

/**
 * Represents an instance of the Whack-a-Mole game.
 */
public class Game_Instance
{

    // Game state variables
    private Difficulty game_difficulty;
    private boolean is_running;
    private int mole_pop_freq;
    private int game_time;
    private int streak_target;
    private int total_points;
    private int good_taps;
    private int bad_taps;

    private Runnable mole_animation_runn;
    private Handler handler = new Handler();
    private ObjectAnimator mole_animation;

    /**
     * Constructor for initializing a Game_Instance.
     *
     * @param difficulty chosen difficulty
     */
    public Game_Instance(Difficulty difficulty)
    {
        set_is_running(false);
        this.game_difficulty = difficulty;
        set_game_settings();
        init_stats();
    }

    private void init_stats()
    {
        this.total_points = 0;
        this.good_taps = 0;
        this.bad_taps = 0;
    }

    private void set_game_settings()
    {
        this.mole_pop_freq = this.game_difficulty.getMole_pop_freq();
        this.game_time = this.game_difficulty.getInitial_time();
        this.streak_target = this.game_difficulty.getStreak_to_special_ability();
    }
    /**
     * Start the game.
     */
    public void start_game()
    {
        set_is_running(true);
        this.random_mole_animation();
    }

    /**
     * Initiates a random mole appearance animation.
     */
    public void random_mole_animation()
    {
        mole_animation_runn = new Runnable() {
            @Override
            public void run() {
                if (is_running) {
                    ImageView chosen_mole = choose_mole();
                    move_mole_up(chosen_mole);
                    handler.postDelayed(mole_animation_runn, mole_pop_freq);
                }
            }
        };

        handler.postDelayed(mole_animation_runn, mole_pop_freq);
    }


    /**
     * Chooses a random mole view and sets its image resource.
     *
     * @return The ImageView of the chosen mole.
     */
    public ImageView choose_mole()
    {
        Random random = new Random();
        int random_idx = random.nextInt(9);
        int random_idx2 = random.nextInt(9);
        //Checks if is user made to get either Bitmap or resource ID
        if(Mole.good_moles.get(random_idx2).get_img_src() == Img_Src.CAMERA)
        {
            Game_Activity.mole_viewsId_list.get(random_idx).setImageBitmap(Mole.good_moles.get(random_idx2).get_mole_bitmap());
            //send Mole so that we can verify if is good or bad
            Game_Activity.mole_viewsId_list.get(random_idx).setTag(Mole.good_moles.get(random_idx2));
            return Game_Activity.mole_viewsId_list.get(random_idx);
        }
        else if(Mole.good_moles.get(random_idx2).get_img_src() == Img_Src.GALLERY)
        {
            Game_Activity.mole_viewsId_list.get(random_idx).setImageURI(Mole.good_moles.get(random_idx2).get_mole_uri());
            Game_Activity.mole_viewsId_list.get(random_idx).setTag(Mole.good_moles.get(random_idx2));
            return Game_Activity.mole_viewsId_list.get(random_idx);
        }
        else
        {
            Game_Activity.mole_viewsId_list.get(random_idx).setImageResource(Mole.good_moles.get(random_idx2).getMole_image_id());
            Game_Activity.mole_viewsId_list.get(random_idx).setTag(Mole.good_moles.get(random_idx2));
            return Game_Activity.mole_viewsId_list.get(random_idx);
        }
    }

    /**
     * Animates the mole to move up.
     *
     * @param mole_image The ImageView of the mole to move.
     */
    public void move_mole_up(ImageView mole_image)
    {
        mole_image.setVisibility(View.VISIBLE);
        this.mole_animation = ObjectAnimator.ofFloat(mole_image, "translationY", -100);
        mole_animation.setDuration(1000);
        mole_animation.start();
    }

    /**
     * Stops the game.
     */
    public void stop_game()
    {
        set_is_running(false);
        this.mole_animation.cancel();
        handler.removeCallbacksAndMessages(this.mole_animation_runn); // Remove all callbacks and messages from the handler
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

    public void set_is_running(boolean is_running)
    {
        this.is_running = is_running;
    }

    public void reset_game() {
        set_is_running(false);
        total_points = 0;
        handler.removeCallbacksAndMessages(mole_animation_runn); // Remove all callbacks and messages from the handler
        // Reset any other game state variables as needed
    }

}
