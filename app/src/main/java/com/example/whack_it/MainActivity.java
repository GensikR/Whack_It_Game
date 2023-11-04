package com.example.whack_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playBTN);
        Button uploadButton = findViewById(R.id.uploadBTN);
        View popupMenuAnchor = findViewById(R.id.popupMenuAnchor);

        playButton.setOnClickListener(v ->
        {
            PopupMenu popupMenu = new PopupMenu(this, popupMenuAnchor);
            popupMenu.getMenu().add("Easy");
            popupMenu.getMenu().add("Medium");
            popupMenu.getMenu().add("Hard");

            popupMenu.setOnMenuItemClickListener(item ->
            {
                // Handle the selected difficulty here
                String difficulty = item.getTitle().toString();

                // Create an Intent to start the GameActivity
                if (difficulty.equals("Easy") || difficulty.equals("Medium") || difficulty.equals("Hard"))
                {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("difficulty", difficulty);
                    startActivity(intent);
                }
                return true;
            });

            popupMenu.show();
        });

        uploadButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this,   MakeMoleActivity.class);
            startActivity(intent);
        });
    }
}