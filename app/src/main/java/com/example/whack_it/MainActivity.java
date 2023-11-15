package com.example.whack_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.whack_it.create_mole.MakeMoleActivity;
import com.example.whack_it.game_engine.Game_Activity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons and anchor view in the layout
        Button playButton = findViewById(R.id.playBTN);
        Button uploadButton = findViewById(R.id.uploadBTN);
        View popupMenuAnchor = findViewById(R.id.popupMenuAnchor);

        // Set click listener for the playButton
        playButton.setOnClickListener(v -> {
            // Create a PopupMenu anchored to the specified view
            PopupMenu popupMenu = new PopupMenu(this, popupMenuAnchor);

            // Add difficulty options to the PopupMenu
            popupMenu.getMenu().add("Easy");
            popupMenu.getMenu().add("Medium");
            popupMenu.getMenu().add("Hard");

            // Set item click listener for the PopupMenu
            popupMenu.setOnMenuItemClickListener(item -> {
                // Handle the selected difficulty here
                String difficulty = item.getTitle().toString();

                // Create an Intent to start the Game_Activity with the selected difficulty
                if (difficulty.equals("Easy") || difficulty.equals("Medium") || difficulty.equals("Hard")) {
                    Intent intent = new Intent(MainActivity.this, Game_Activity.class);
                    intent.putExtra("difficulty", difficulty);
                    startActivity(intent);
                }
                return true;
            });

            // Show the PopupMenu
            popupMenu.show();
        });

        // Set click listener for the uploadButton
        uploadButton.setOnClickListener(v -> {
            // Create an Intent to start the MakeMoleActivity
            Intent intent = new Intent(MainActivity.this, MakeMoleActivity.class);
            startActivity(intent);
        });
    }
}
