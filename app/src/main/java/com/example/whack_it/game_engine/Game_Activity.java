package com.example.whack_it.game_engine;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.example.whack_it.Mole;
import com.example.whack_it.R;
import java.util.ArrayList;

public class Game_Activity extends AppCompatActivity {
    // List to hold the IDs of mole views that will be animated
    public static ArrayList<ImageView> mole_viewsId_list = new ArrayList<>();
    private Game_Instance game_instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initializes the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Populates the mole_viewsId_list with mole views from the layout
        populate_mole_views_list();

        // Initializes good and bad moles
        Mole.create_good_moles();
        Mole.create_bad_moles();

        // Starts the game
        //TODO implement way to vary game difficulty. start with pop frequency
        int mole_pop_freq = 500;
        int game_time = 10;
        this.game_instance = new Game_Instance(mole_pop_freq, game_time);
        game_instance.start_game();
    }

    // Populates the mole_viewsId_list with mole views from the layout
    private void populate_mole_views_list() {
        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 0:
                    this.mole_viewsId_list.add(findViewById(R.id.mole0));
                    break;
                case 1:
                    this.mole_viewsId_list.add(findViewById(R.id.mole1));
                    break;
                case 2:
                    this.mole_viewsId_list.add(findViewById(R.id.mole2));
                    break;
                case 3:
                    this.mole_viewsId_list.add(findViewById(R.id.mole3));
                    break;
                case 4:
                    this.mole_viewsId_list.add(findViewById(R.id.mole4));
                    break;
                case 5:
                    this.mole_viewsId_list.add(findViewById(R.id.mole5));
                    break;
                case 6:
                    this.mole_viewsId_list.add(findViewById(R.id.mole6));
                    break;
                case 7:
                    this.mole_viewsId_list.add(findViewById(R.id.mole7));
                    break;
                case 8:
                    this.mole_viewsId_list.add(findViewById(R.id.mole8));
                    break;
                default:
                    break;
            }
        }
    }

    // Handles click event on a mole view
    public void on_mole_click(View view) {
        game_instance.add_points(1);    // TODO: Check if it is a good or bad mole to add or remove points
        ObjectAnimator moveDown = ObjectAnimator.ofFloat(view, "translationY", 5);
        moveDown.setDuration(1000);
        moveDown.start();
    }
}
