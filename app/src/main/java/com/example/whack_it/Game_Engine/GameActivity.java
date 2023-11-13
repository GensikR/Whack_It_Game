package com.example.whack_it.Game_Engine;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import com.example.whack_it.Mole;
import com.example.whack_it.R;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ArrayList<Mole> regMoles = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        for(int i = 0; i < 9; i++)
        {
            String moleId = "mole" + i;
            int resourceId = getResources().getIdentifier(moleId, "id", getPackageName());
            regMoles.add(new Mole(moleId, true, findViewById(resourceId)));
        }
        GameInstance gameInstance = new GameInstance(regMoles, 60);
        gameInstance.start();


    }

    int goodTaps = 0;
    int badTaps = 0;

    public void onMoleClick(View view)
    {
        ObjectAnimator moveDown = ObjectAnimator.ofFloat(view, "translationY", 5);
        moveDown.setDuration(1000);
        moveDown.start();
    }
    /*
        Makes 10 good moles
    */
    public void createGoodArray()
    {

    }
    /*
        Makes 10 bad moles
    */
    public void createBadArray()
    {

    }

}