package com.example.whack_it.game;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.whack_it.utilities.Img_Src;
import com.example.whack_it.extras.Stats;
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
    private int flag_mole_type;
    private int[] is_hole_filled = new int[10];

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
        this.flag_mole_type = 0;
        for(int i = 0; i < 10; i++)
        {
            this.is_hole_filled[i] = 0;
        }
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
        ImageView image;
        Random random = new Random();
        int hole_idx = random.nextInt(6);

        this.is_hole_filled[hole_idx] = 1;
        int mole_idx = random.nextInt(9);
        int choice = random.nextInt(1);

        if(flag_mole_type == 1)
        {
            this.is_hole_filled[hole_idx] = 1;
            image = get_good_mole_img(hole_idx, mole_idx);
            this.flag_mole_type = 0;
        }
        else
        {
            this.is_hole_filled[hole_idx] = 1;
            image = get_bad_mole_img(hole_idx, mole_idx);
            this.flag_mole_type = 1;
        }


        return image;
    }

    private ImageView get_bad_mole_img(int hole_idx, int mole_idx)
    {
        if(Mole.bad_moles.get(mole_idx).get_img_src() == Img_Src.CAMERA)
        {
            Game_Activity.mole_viewsId_list.get(hole_idx).setImageBitmap(Mole.bad_moles.get(mole_idx).get_mole_bitmap());
            //send Mole so that we can verify if is good or bad
            Game_Activity.mole_viewsId_list.get(hole_idx).setTag(Mole.bad_moles.get(mole_idx));
            return Game_Activity.mole_viewsId_list.get(hole_idx);
        }
        else if(Mole.bad_moles.get(mole_idx).get_img_src() == Img_Src.GALLERY)
        {
            Game_Activity.mole_viewsId_list.get(hole_idx).setImageURI(Mole.bad_moles.get(mole_idx).get_mole_uri());
            Game_Activity.mole_viewsId_list.get(hole_idx).setTag(Mole.bad_moles.get(mole_idx));
            return Game_Activity.mole_viewsId_list.get(hole_idx);
        }
        else
        {
            Game_Activity.mole_viewsId_list.get(hole_idx).setImageResource(Mole.bad_moles.get(mole_idx).getMole_image_id());
            Game_Activity.mole_viewsId_list.get(hole_idx).setTag(Mole.bad_moles.get(mole_idx));
            return Game_Activity.mole_viewsId_list.get(hole_idx);
        }
    }

    private ImageView get_good_mole_img(int hole_idx, int mole_idx)
    {
        if(Mole.good_moles.get(mole_idx).get_img_src() == Img_Src.CAMERA)
        {
            Game_Activity.mole_viewsId_list.get(hole_idx).setImageBitmap(Mole.good_moles.get(mole_idx).get_mole_bitmap());
            //send Mole so that we can verify if is good or bad
            Game_Activity.mole_viewsId_list.get(hole_idx).setTag(Mole.good_moles.get(mole_idx));
            return Game_Activity.mole_viewsId_list.get(hole_idx);
        }
        else if(Mole.good_moles.get(mole_idx).get_img_src() == Img_Src.GALLERY)
        {
            Game_Activity.mole_viewsId_list.get(hole_idx).setImageURI(Mole.good_moles.get(mole_idx).get_mole_uri());
            Game_Activity.mole_viewsId_list.get(hole_idx).setTag(Mole.good_moles.get(mole_idx));
            return Game_Activity.mole_viewsId_list.get(hole_idx);
        }
        else
        {
            Game_Activity.mole_viewsId_list.get(hole_idx).setImageResource(Mole.good_moles.get(mole_idx).getMole_image_id());
            Game_Activity.mole_viewsId_list.get(hole_idx).setTag(Mole.good_moles.get(mole_idx));
            return Game_Activity.mole_viewsId_list.get(hole_idx);
        }
    }

    /**
     * Animates the mole to move up.
     *
     * @param mole_image The ImageView of the mole to move.
     */
    public void move_mole_up(ImageView mole_image)
    {
        Mole this_mole = (Mole)mole_image.getTag();
        if(this_mole.isIs_hidden())
        {
            this_mole.set_is_hidden(false);
            mole_image.setVisibility(View.VISIBLE);
            this.mole_animation = ObjectAnimator.ofFloat(mole_image, "translationY", -100);
            mole_animation.setDuration(1000);
            mole_animation.start();
            // Delay for 3 seconds and then start the down animation

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(is_running) {
                        ObjectAnimator moveDown = ObjectAnimator.ofFloat(mole_image, "translationY", 0);
                        moveDown.setDuration(1000);
                        moveDown.start();
                    }
                }
            }, 3000); // 3000 milliseconds (3 seconds)


        }
        else if (!this_mole.isIs_hidden())
        {
            ObjectAnimator moveDown = ObjectAnimator.ofFloat(mole_image, "translationY", 0);
            moveDown.setDuration(100);
            moveDown.start();
            this_mole.set_is_hidden(true);
            mole_image.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Stops the game.
     */
    public void stop_game()
    {
        set_is_running(false);
        Stats.update_stats(get_total_points(), get_good_taps(), get_bad_taps());
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

    public void increase_good_taps_stats()
    {
        this.good_taps++;
    }

    public void increase_bad_taps_stats()
    {
        this.bad_taps++;
    }

    public int get_good_taps()
    {
        return this.good_taps;
    }

    public int get_bad_taps()
    {
        return this.bad_taps;
    }

    public void set_is_running(boolean is_running)
    {
        this.is_running = is_running;
    }

    public void reset_game()
    {
        set_is_running(false);
        total_points = 0;
        handler.removeCallbacksAndMessages(mole_animation_runn); // Remove all callbacks and messages from the handler
        // Reset any other game state variables as needed
    }

}
