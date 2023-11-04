package com.example.whack_it;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

public class Mole
{
    private String name;
    private boolean isBad;
    private ImageView moleImage;
    private boolean isHidden = true;

    public Mole(String name, boolean isBad, ImageView moleImage)
    {
        this.name = name;
        this.isBad = isBad;
        this.moleImage = moleImage;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setBad(boolean bad)
    {
        isBad = bad;
    }

    public void setMoleImage(ImageView moleImage)
    {
        this.moleImage = moleImage;
    }

    public void setHidden(boolean hidden)
    {
        isHidden = hidden;
    }

    public String getName()
    {
        return name;
    }

    public boolean isBad()
    {
        return isBad;
    }

    public ImageView getMoleImage()
    {
        return moleImage;
    }

    public boolean isHidden()
    {
        return isHidden;
    }

    public void moveMoleUp()
    {
        if (this.isHidden)
        {
            ObjectAnimator moleAnimation = ObjectAnimator.ofFloat(moleImage, "translationY", -100);
            moleAnimation.setDuration(1000);
            moleAnimation.start();
        }
        else
        {
            return;
        }
    }
}
