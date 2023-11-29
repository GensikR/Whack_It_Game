package com.example.whack_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.whack_it.extras.Extras_Activity;
import com.example.whack_it.extras.Stats;
import com.example.whack_it.game.Game_Activity;
import com.example.whack_it.mk_mole.MkMole_Activity;
import com.example.whack_it.utilities.Popup_Menu;

import java.util.ArrayList;

/**
 * The main activity of the Whack It app.
 */
public class Main_Activity extends AppCompatActivity
{

    private Button playBTN;
    private Button mk_moleBTN;
    private Button extrasBTN;
    private View popup_menu_anchor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find buttons and anchor view in the layout
        this.playBTN = findViewById(R.id.playBTN);
        this.mk_moleBTN = findViewById(R.id.mk_moleBTN);
        this.extrasBTN = findViewById(R.id.extrasBTN);
        this.popup_menu_anchor = findViewById(R.id.popupMenuAnchor);

        // Set click listeners for buttons
        setExtrasBTN();
        setMkMoleBTN();
        setPlayBTN();
    }

    /**
     * Sets the click listener for the "Extras" button.
     * Opens the Extras_Activity when the button is clicked.
     */
    private void setExtrasBTN()
    {
        this.extrasBTN.setOnClickListener(v ->
        {
            // Create an Intent to start the Extras_Activity
            Intent intent = new Intent(Main_Activity.this, Extras_Activity.class);
            startActivity(intent);
        });
    }

    /**
     * Sets the click listener for the "Make Mole" button.
     * Opens the MkMole_Activity when the button is clicked.
     */
    private void setMkMoleBTN()
    {
        this.mk_moleBTN.setOnClickListener(v ->
        {
            // Create an Intent to start the MkMole_Activity
            Intent intent = new Intent(Main_Activity.this, MkMole_Activity.class);
            startActivity(intent);
        });
    }

    /**
     * Sets the click listener for the "Play" button.
     * Shows a PopupMenu with difficulty options and starts the game based on the selected difficulty.
     */
    private void setPlayBTN()
    {
        this.playBTN.setOnClickListener(v ->
        {
            // Create a PopupMenu anchored to the specified view
            PopupMenu popupMenu = new PopupMenu(this, this.popup_menu_anchor);
            ArrayList<String> options = new ArrayList<>();
            options.add("Easy");
            options.add("Medium");
            options.add("Hard");

            Popup_Menu difficulty_pop_menu = new Popup_Menu(popupMenu,options );
            difficulty_pop_menu.show_popup_menu();

            // Set item click listener for the PopupMenu
            popupMenu.setOnMenuItemClickListener(item ->
            {
                // Handle the selected difficulty here
                String difficulty = item.getTitle().toString();
                // Changes to game activity and starts the game
                startGame(difficulty);
                return true;
            });
        });
    }

    /**
     * Starts the game activity with the specified difficulty.
     *
     * @param difficulty The difficulty level chosen by the user.
     */
    public void startGame(String difficulty)
    {
        Intent intent = new Intent(Main_Activity.this, Game_Activity.class);
        intent.putExtra("difficulty", difficulty);  // Puts the difficulty string in the data of the intent to later be retrieved
        startActivity(intent);
    }
}
