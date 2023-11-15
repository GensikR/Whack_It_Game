package com.example.whack_it.create_mole;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whack_it.Mole;
import com.example.whack_it.R;

public class MakeMoleActivity extends AppCompatActivity {
    ImageView imgGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkmole);

        // Initialize UI elements
        Button chooseAppButton = findViewById(R.id.chooseAppBTN);
        View popupMenuAnchor = findViewById(R.id.popupMenuAnchor);

        // Get image from the user using a popup menu
        GetImageMenu imageMenu = new GetImageMenu(chooseAppButton, popupMenuAnchor);
        ImageView userImage = imageMenu.get_mole_image();

        // Get mole name from editText
        EditText nameInput = findViewById(R.id.nameInput);
        String moleName = nameInput.getText().toString();

        // Get the character of the mole (good or bad)
        // TODO: Implement a way to have the user only choose one option here
        CheckBox yesButton = findViewById(R.id.yesCheckBox); // Reference the checkBox by its ID
        CheckBox noButton = findViewById(R.id.noCheckBox); // Reference the checkBox by its ID
        boolean isYesChecked = yesButton.isChecked(); // Check the state of the yes checkbox
        boolean isNoChecked = noButton.isChecked(); // Check the state of the yes checkbox

        // Create Mole Button
        //TODO Test this, create multiple moles
        Button createMoleBTN = findViewById(R.id.createMoleBTN);
        createMoleBTN.setOnClickListener(v -> {
            // Check if required fields are properly filled
            if (moleName.isEmpty() || userImage.getDrawable() == null || (isYesChecked == isNoChecked) || (!isYesChecked && !isNoChecked)) {
                // TODO: add error handling for when one of the fields is not properly filled
                return;
            } else {
                // Get the resource ID of the user-selected image
                int userImageResource = getResources().getIdentifier(getResources().getResourceEntryName(userImage.getId()), "drawable", getPackageName());
                // Use the user-selected image resource ID for the Mole object
                Mole new_mole = new Mole(moleName, isYesChecked, userImageResource);
                Mole.add_mole_to_list(new_mole, isYesChecked);
            }
            // TODO: Add a button or something to show the user the mole was created
        });
    }
}
