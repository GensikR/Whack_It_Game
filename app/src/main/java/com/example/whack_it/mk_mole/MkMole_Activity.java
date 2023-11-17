package com.example.whack_it.mk_mole;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.whack_it.R;
import android.Manifest;

/**
 * Activity for creating a new mole in the Whack It app.
 */
public class MkMole_Activity extends AppCompatActivity {
    private static final int GALLERY_REQ_CODE = 1000;
    private static final int CAMERA_REQ_CODE = 1001;

    private ImageView user_image;
    private String user_name;
    private Button select_imgBTN;
    private View popup_menu_anchor;
    private Button create_moleBTN;
    private CheckBox yes_box;
    private CheckBox no_box;
    private EditText name_input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkmole);

        // Initialize UI elements
        this.select_imgBTN = findViewById(R.id.chooseAppBTN);
        this.popup_menu_anchor = findViewById(R.id.popupMenuAnchor);
        this.create_moleBTN = findViewById(R.id.createMoleBTN);

        // Get image from the user using a popup menu
        set_img_menu();

        // Set the create mole button
        set_create_moleBTN();

        // Get mole name from editText
        this.name_input = findViewById(R.id.nameInput);

        // Get the character of the mole (good or bad)
        //TODO change to another View that only allows one choice to be made. radio button I think is called
        this.yes_box = findViewById(R.id.yesCheckBox); // Reference the checkBox by its ID
        this.no_box = findViewById(R.id.noCheckBox); // Reference the checkBox by its ID
    }

    /**
     * Sets the click listener for the "Create Mole" button.
     */
    private void set_create_moleBTN()
    {
        this.create_moleBTN.setOnClickListener(v ->
        {
            // Check if required fields are properly filled
            if (this.name_input.getText().toString().isEmpty() || user_image.getDrawable() == null || (this.yes_box.isChecked() == this.no_box.isChecked()) || (!this.yes_box.isChecked() && this.no_box.isChecked()))
            {
                // TODO: add error handling for when one of the fields is not properly filled
                return;
            }
            else
            {
                //TODO: test that the moles are created and added to their respective list
                //Get user name
                this.user_name = this.name_input.getText().toString();
                //Get the resource ID of the user-selected image
                int userImageResource = getResources().getIdentifier(getResources().getResourceEntryName(user_image.getId()), "drawable", getPackageName());
                // Use the user-selected image resource ID for the Mole object
                Mole new_mole = new Mole(this.user_name, this.yes_box.isChecked(), userImageResource);
                Mole.add_mole_to_list(new_mole);
            }
            // TODO: Add a button or something to show the user the mole was created
        });
    }

    /**
     * Sets the click listener for image selection button.
     */
    private void set_img_menu()
    {
        // Set a click listener on the button
        this.select_imgBTN.setOnClickListener(v ->
        {
            // Create a popup menu for image selection
            PopupMenu popupMenu = new PopupMenu(this, this.popup_menu_anchor);
            popupMenu.getMenu().add("Choose From Gallery");
            popupMenu.getMenu().add("Take Picture");
            popupMenu.getMenu().add("Choose From Stock");

            // Handle the selected decision
            popupMenu.setOnMenuItemClickListener(item ->
            {
                String decision = item.getTitle().toString();
                start_photo_interface(decision);
                //TODO: show preview picture on the screen
                return true;
            });

            // Show the popup menu
            popupMenu.show();
        });
    }

    /**
     * Starts the appropriate photo interface based on the user's decision.
     *
     * @param decision The user's decision on how to select a photo.
     */
    private void start_photo_interface(String decision)
    {
        switch (decision)
        {
            case ("Choose From Gallery"):
                gallery();
                break;
            case ("Take Picture"):
                start_camera();
                break;
            case ("Choose From Stock"):
                //TODO let the user choose image from stock
                break;
            default:
                break;
        }
    }

    /**
     * Opens the gallery for image selection.
     */
    private void gallery()
    {
        // Create an intent to pick an image from the gallery
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQ_CODE);
    }

    /**
     * Starts the camera for taking a picture.
     */
    private void start_camera()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQ_CODE);
            //TODO: make it so that the camera launches after receiving permission(currently we need to click the button again)
        }
        else
        {
            // Permission is already granted
            // Start camera activity here
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, CAMERA_REQ_CODE);
        }
    }

    /**
     * Handles the result of the gallery activity and camera activity.
     *
     * @param requestCode The request code.
     * @param resultCode  The result code.
     * @param data        The data from the activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == GALLERY_REQ_CODE)
            {//TODO: test that we are correctly getting the image
                // Set the mole_image to the selected image from the gallery
                this.user_image.setImageURI(data.getData());
            }
            else if (requestCode == CAMERA_REQ_CODE)
            {   //TODO: App is failing to recover result
                // BitMap is data structure of image file which store the image in memory
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Set the image in imageview for display
                this.user_image.setImageBitmap(photo);
            }
        }
    }
}