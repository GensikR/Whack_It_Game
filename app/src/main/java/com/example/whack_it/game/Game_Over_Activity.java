package com.example.whack_it.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whack_it.Main_Activity;
import com.example.whack_it.R;
import com.example.whack_it.extras.Stats;

/**
 * Activity displaying game over information and options to play again or return to the main menu.
 */
public class Game_Over_Activity extends AppCompatActivity
{
    private Button play_againBTN;
    private Button return_menuBTN;
    private TextView total_points_txt;
    private TextView best_score_txt;
    private int total_points;
    private int good_taps;
    private int bad_taps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Get intent extras that holds stats
        Intent this_intent = getIntent();
        this.total_points = this_intent.getIntExtra("total points", 0);
        this.good_taps = this_intent.getIntExtra("good taps",0);
        this.bad_taps = this_intent.getIntExtra("bad taps", 0);

        //Set the buttons up to return to the main menu or play again
        this.play_againBTN = findViewById(R.id.play_againBTN);
        this.return_menuBTN = findViewById(R.id.main_menuBTN);
        this.total_points_txt = findViewById(R.id.total_points_txt);
        this.best_score_txt = findViewById(R.id.best_score_txt);
        set_play_againBTN(play_againBTN);
        set_return_menuBTN(return_menuBTN);

        set_total_points();
        set_best_score();
    }

    private void set_best_score()
    {
        this.best_score_txt.setText("" + Stats.best_scores.get(0));
    }

    private void set_total_points()
    {
        this.total_points_txt.setText("" + this.total_points);
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
