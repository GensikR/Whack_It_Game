package com.example.whack_it;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

import java.util.ArrayList;

public class Mole
{
    private String name;
    private boolean isBad;
    private ImageView moleImage;
    private boolean isHidden = true;
    private boolean isUserMade = false;
    public static ArrayList<Mole>  goodMoles = new ArrayList<>();
    public static ArrayList<Mole>  badMoles = new ArrayList<>();

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

    public void setVisible()
    {
        moleImage.setVisibility(moleImage.VISIBLE);
    }

    public void setInvisible()
    {
        moleImage.setVisibility(moleImage.INVISIBLE);
    }

    public void addGoodMole(Mole goodMole)
    {
        this.goodMoles.add(goodMole);
    }

    public void addbadMole(Mole badMole)
    {
        this.badMoles.add(badMole);
    }

    public void removeGoodMole()
    {
        int i;
        for(i = 0; i < this.goodMoles.size(); i++)
        {
            if(!this.goodMoles.get(i).isUserMade)
            {
                this.goodMoles.remove(i);
                return;
            }
        }
        //TODO: error handling when all moles in the list are goodMoles
    }

    public void removeBadMole()
    {
        int i;
        for(i = 0; i < this.badMoles.size(); i++)
        {
            if(!this.badMoles.get(i).isUserMade)
            {
                this.badMoles.remove(i);
                return;
            }
        }
        //TODO: error handling when all moles in the list are goodMoles
    }



}
