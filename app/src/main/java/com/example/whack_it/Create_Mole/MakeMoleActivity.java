package com.example.whack_it.Create_Mole;

import static java.sql.Types.NULL;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.whack_it.Mole;
import com.example.whack_it.R;

public class MakeMoleActivity extends AppCompatActivity
{
    ImageView imgGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkmole);

        /*popup menu for upload interface*/
        Button chooseAppButton = findViewById(R.id.chooseAppBTN);
        View popupMenuAnchor = findViewById(R.id.popupMenuAnchor);

        /*get image from user*/
        GetImageMenu imageMenu = new GetImageMenu(chooseAppButton, popupMenuAnchor);
        ImageView userImage = imageMenu.getMoleImage();

        /*Get Name From editText*/
        EditText nameInput = findViewById(R.id.nameInput);
        String moleName = nameInput.getText().toString();

        /*Get Mole's character is it good or bad*/
        //TODO: Implement a way to have the user only choose one option here
        CheckBox yesButton = findViewById(R.id.yesCheckBox); // Reference the checkBox by its ID
        CheckBox noButton = findViewById(R.id.noCheckBox); // Reference the checkBox by its ID
        boolean isYesChecked = yesButton.isChecked(); // Check the state of the yes checkbox
        boolean isNoChecked = noButton.isChecked(); // Check the state of the yes checkbox

        /*Create Mole Button*/
        Button createMoleBTN = findViewById(R.id.createMoleBTN);
        createMoleBTN.setOnClickListener(v ->
        {
            if(moleName.equals(NULL) || userImage.equals(NULL) || (isYesChecked == isNoChecked) || (!isYesChecked && !isNoChecked))
            {
                //TODO: add error handling for when one of the fields are not properly filled
                return;
            }
            else
            {
                Mole newMole = new Mole(moleName, isYesChecked, userImage);
                if(newMole.isBad())
                {
                    if(newMole.goodMoles.size() < 11)
                    {
                        newMole.badMoles.add(newMole);
                    }
                    else
                    {
                        newMole.removeBadMole();
                        newMole.badMoles.add(newMole);
                    }
                }
            }
            //TODO: Add a button or something to show the user the mole was created
        });

    }

}
