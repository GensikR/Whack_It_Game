package com.example.whack_it.mk_mole;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.whack_it.Img_Src;
import com.example.whack_it.Main_Activity;
import com.example.whack_it.R;
import com.example.whack_it.game.Game_Over_Activity;

import android.Manifest;

/**
 * Activity for creating a new mole in the Whack It app.
 */
public class MkMole_Activity extends AppCompatActivity {
    private static final int GALLERY_REQ_CODE = 1000;
    private static final int CAMERA_REQ_CODE = 1001;

    private Bitmap user_bitmap;
    private Uri user_uri;
    private Img_Src img_src;
    private String user_name;
    private Button select_imgBTN;
    private View popup_menu_anchor;
    private Button create_moleBTN;
    private Button return_btn;
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
        this.return_btn = findViewById(R.id.mk_return_btn);

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

        //set return back menu
        set_retur_BTN();
    }

    private void set_retur_BTN()
    {
        this.return_btn.setOnClickListener( v ->
        {
            // Change activities
            Intent intent = new Intent(MkMole_Activity.this, Main_Activity.class);
            startActivity(intent);
        });
    }

    /**
     * Sets the click listener for the "Create Mole" button.
     */
    private void set_create_moleBTN()
    {
        this.create_moleBTN.setOnClickListener(v ->
        {
            // TODO: add error handling for when one of the fields is not properly filled
            //Get user name
            this.user_name = this.name_input.getText().toString();
            if(this.img_src == Img_Src.GALLERY)
            {
                // Use the user-selected image Uri for the Mole object
                Mole new_mole = new Mole(this.user_name, this.yes_box.isChecked(), this.user_uri, this.img_src);
                Mole.add_mole_to_list(new_mole);
            }
            else
            {
                // Use the user-selected image Bitmap for the Mole object
                Mole new_mole = new Mole(this.user_name, this.yes_box.isChecked(), this.user_bitmap, this.img_src);
                Mole.add_mole_to_list(new_mole);
            }


        // TODO: Add a button or something to show the user the mole was created
            //TODO: Return to main menu button
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
                this.img_src = Img_Src.GALLERY;
                gallery();
                break;
            case ("Take Picture"):
                this.img_src = Img_Src.CAMERA;
                start_camera();
                break;
            case ("Choose From Stock"):
                //TODO let the user choose image from stock
                break;
            default:
                break;
        }
    }

    private void gallery()
    {
        open_gallery();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("MkMole_Activity", "READ_EXTERNAL_STORAGE permission granted, opening gallery");
                open_gallery();
            } else
            {
                Log.d("MkMole_Activity", "READ_EXTERNAL_STORAGE permission denied");
                // Handle permission denied case
            }
        }
    }



    /**
     * Opens the gallery for image selection.
     */
    private void open_gallery()
    {

        // Create an intent to pick an image from the gallery
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(gallery, "Select File"), GALLERY_REQ_CODE);

        /*
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            gallery.putExtra(Intent.ACTION_GET_CONTENT, true);
        }
        */

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
            {
                // Set the mole_image to the selected image from the gallery
                this.user_uri = data.getData();
            }
            else if (requestCode == CAMERA_REQ_CODE)
            {   //TODO: Adjust picture size so that the face shows as the mole
                //BitMap is data structure of image file which store the image in memory
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Set the image in imageview for display
                this.user_bitmap = photo;
            }
        }
    }
}



