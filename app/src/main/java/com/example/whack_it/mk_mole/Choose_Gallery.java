package com.example.whack_it.mk_mole;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whack_it.Main_Activity;
import com.example.whack_it.R;
import com.example.whack_it.game.Game_Activity;

public class Choose_Gallery extends AppCompatActivity
{
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private Button return_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        this.image1 = findViewById(R.id.image1);
        this.image2 = findViewById(R.id.image2);
        this.image3 = findViewById(R.id.image3);
        this.return_btn = findViewById(R.id.return_btn);

        set_image1();
        set_image2();
        set_image3();
        set_return_btn();
    }

    private void set_return_btn()
    {
        this.return_btn.setOnClickListener( v ->
        {
            Intent intent = new Intent(Choose_Gallery.this, MkMole_Activity.class);
            startActivity(intent);
        });
    }

    private void set_image3()
    {
        this.image3.setOnClickListener( v->
        {
            Intent intent = new Intent(Choose_Gallery.this, MkMole_Activity.class);
            startActivity(intent);
        });
    }

    private void set_image2()
    {
        this.image2.setOnClickListener( v ->
        {
            Intent intent = new Intent(Choose_Gallery.this, MkMole_Activity.class);
            startActivity(intent);
        });
    }

    private void set_image1()
    {
        this.image1.setOnClickListener( v ->
        {
            Intent intent = new Intent(Choose_Gallery.this, MkMole_Activity.class);
            startActivity(intent);
        });
    }
}
