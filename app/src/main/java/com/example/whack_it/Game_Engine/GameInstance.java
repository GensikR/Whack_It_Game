package com.example.whack_it.Game_Engine;

import java.util.ArrayList;
import java.util.Random;

import android.animation.ObjectAnimator;
import android.widget.ImageView;


import android.os.Handler;

import com.example.whack_it.Mole;

public class GameInstance
{
    private ArrayList<Mole> regMoles;
    private boolean isRunning;
    private int totalSeconds;

    public GameInstance(ArrayList<Mole> regMoles, int gameTime)
    {
        this.regMoles = regMoles;
        this.totalSeconds = gameTime;
        this.isRunning = false;
    }

    private Handler handler = new Handler();
    private int moleAppearanceInterval = 500; // 5 seconds (in milliseconds)

    public void start()
    {
        randomMoleAnimation();

    }

    public void randomMoleAnimation()
    {
        Random random = new Random();
        this.isRunning = true;

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (isRunning)
                {
                    Random chosenMole = new Random();
                    int randomNum = chosenMole.nextInt(9);
                    regMoles.get(randomNum).moveMoleUp();
                    regMoles.get(randomNum).setVisible();
                    handler.postDelayed(this, moleAppearanceInterval);
                }
            }
        }, moleAppearanceInterval);
    }

    /*
    TODO
        // Schedule a task to stop the game after the specified time
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                stopGame();
            }
        }, moleAppearanceInterval + totalSeconds * 1000);
        }
        public void stopGame()
        {
        this.isRunning = false;
        handler.removeCallbacksAndMessages(null); // Remove all callbacks and messages from the handler
        // Other cleanup code if needed
        }
    */

}



