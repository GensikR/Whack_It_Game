package com.example.whack_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.net.Uri;

public class MakeMoleActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkmole);

        Button playButton = findViewById(R.id.chooseAppBTN);
        View popupMenuAnchor = findViewById(R.id.popupMenuAnchor);

        playButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, popupMenuAnchor);
            popupMenu.getMenu().add("Upload Picture");
            popupMenu.getMenu().add("Choose From Stock");

            popupMenu.setOnMenuItemClickListener(item -> {
                // Handle the selected decision here
                String decision = item.getTitle().toString();

                if (decision.equals("Upload Picture")) {
                    // Create an Intent to pick an image from the gallery
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
                return true;
            });

            popupMenu.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            // You can use the selectedImage URI to load and display the image
            // Handle the selected image here
        }
    }
}
