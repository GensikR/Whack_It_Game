package com.example.whack_it.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.whack_it.Main_Activity;
import com.example.whack_it.R;

/**
 * Activity displaying game over information and options to play again or return to the main menu.
 */
public class Game_Over_Activity extends AppCompatActivity
{
    private Button play_againBTN;
    private Button return_menuBTN;
    private TextView total_pointsTXT;
    private TextView last_pointsTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Set the buttons up to return to the main menu or play again
        this.play_againBTN = findViewById(R.id.play_againBTN);
        this.return_menuBTN = findViewById(R.id.main_menuBTN);
        set_play_againBTN(play_againBTN);
        set_return_menuBTN(return_menuBTN);
    }

    /**
     * Sets the click listener for the "Play Again" button.
     *
     * @param btn The "Play Again" button.
     */
    private void set_play_againBTN(Button btn)
    {
        // Set click listener
        btn.setOnClickListener(v ->
        {
            // Change activities
            Intent intent = new Intent(Game_Over_Activity.this, Game_Activity.class);
            startActivity(intent);
        });
    }

    /**
     * Sets the click listener for the "Return to Main Menu" button.
     *
     * @param btn The "Return to Main Menu" button.
     */
    private void set_return_menuBTN(Button btn)
    {
        // Set click listener
        btn.setOnClickListener(v ->
        {
            // Change activities
            Intent intent = new Intent(Game_Over_Activity.this, Main_Activity.class);
            startActivity(intent);
        });
    }
}
