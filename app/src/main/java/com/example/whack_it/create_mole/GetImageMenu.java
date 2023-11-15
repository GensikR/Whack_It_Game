package com.example.whack_it.create_mole;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

public class GetImageMenu extends AppCompatActivity {
    // Constant for the gallery request code
    private static final int GALLERY_REQ_CODE = 1000;

    // Button to trigger image selection
    private Button button;

    // View to anchor the popup menu
    private View popupMenuAnchor;

    // ImageView to display the selected image
    private ImageView mole_image;

    public ImageView get_mole_image()
    {
        return mole_image;
    }

    // Constructor to initialize the button and popup menu anchor
    public GetImageMenu(Button button, View popupMenuAnchor) {
        this.button = button;
        this.popupMenuAnchor = popupMenuAnchor;
    }

    // Method to start the image selection process
    public void start() {
        // Set a click listener on the button
        this.button.setOnClickListener(v -> {
            // Create a popup menu for image selection
            PopupMenu popupMenu = new PopupMenu(this, popupMenuAnchor);
            popupMenu.getMenu().add("Choose From Gallery");
            popupMenu.getMenu().add("Take Picture");
            popupMenu.getMenu().add("Choose From Stock");

            // Handle the selected decision
            popupMenu.setOnMenuItemClickListener(item -> {
                String decision = item.getTitle().toString();
                action_on_click(decision);

                return true;
            });

            // Show the popup menu
            popupMenu.show();
        });
    }

    // Method to handle the user's decision in the popup menu
    public void action_on_click(String decision) {
        switch (decision) {
            case "Choose From Gallery":
                // Start the process to get an image from the gallery
                get_img_from_gallery();
                break;
            // TODO: Handle other cases if needed
        }
    }

    // Method to initiate image selection from the gallery
    private void get_img_from_gallery() {
        // Create an intent to pick an image from the gallery
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQ_CODE);
    }

    // Method to handle the result of the image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                // Set the mole_image to the selected image from the gallery
                mole_image.setImageURI(data.getData());
            }
        }
    }
}
