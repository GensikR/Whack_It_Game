package com.example.whack_it.Create_Mole;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.whack_it.R;

public class GetImageMenu extends AppCompatActivity
{
    private static final int GALLERY_REQ_CODE = 1000;
    private Button button;
    private View popupMenuAnchor;

    public ImageView getMoleImage()
    {
        return moleImage;
    }

    private ImageView moleImage;

    public GetImageMenu(Button button, View popupMenuAnchor)
    {
        this.button = button;
        this.popupMenuAnchor = popupMenuAnchor;
    }

    public void start()
    {
        this.button.setOnClickListener(v ->
        {
            PopupMenu popupMenu = new PopupMenu(this, popupMenuAnchor);
            popupMenu.getMenu().add("Choose From Gallery");
            popupMenu.getMenu().add("Take Picture");
            popupMenu.getMenu().add("Choose From Stock");

            popupMenu.setOnMenuItemClickListener(item ->
            {
                // Handle the selected decision here
                String decision = item.getTitle().toString();
                actionOnClick(decision);

                return true;
            });

            popupMenu.show();
        });
    }

    public void actionOnClick(String decision)
    {
        switch (decision) {
            case "Choose From Gallery":
                getImgFromGallery();
                break;
            // ... (other cases)
        }
    }

    private void getImgFromGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQ_CODE);
    }
    /*
        Image from gallery assigned to moleImage after if the user succesfully uploads it
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == GALLERY_REQ_CODE)
            {//set moleImage to image from gallery
                moleImage.setImageURI(data.getData());
            }
        }
    }
}
