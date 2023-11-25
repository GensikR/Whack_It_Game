package com.example.whack_it.mk_mole;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.whack_it.Img_Src;
import com.example.whack_it.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Mole in the Whack It app.
 */
public class Mole {
    private String name;
    private boolean is_bad;
    private int mole_image_id;
    private Bitmap mole_bitmap;
    private Uri mole_uri;
    private boolean is_hidden = true;
    private Img_Src img_src;

    // Lists to hold good and bad moles
    public static ArrayList<Mole> good_moles = new ArrayList<>();
    public static ArrayList<Mole> bad_moles = new ArrayList<>();


    /**
     * Constructor to initialize mole attributes with resource ID number
     *
     * @param name           The name of the mole.
     * @param is_bad         Whether the mole is "bad" or not.
     * @param mole_image_id  The resource ID of the mole image.
     */
    public Mole(String name, boolean is_bad, int mole_image_id, Img_Src src)
    {
        this.name = name;
        this.is_bad = is_bad;
        this.mole_image_id = mole_image_id;
        this.img_src = src;
    }
    /**
     * Constructor to initialize mole attributes with bitmap image
     *
     * @param name           The name of the mole.
     * @param is_bad         Whether the mole is "bad" or not.
     * @param mole_bitmap  The resource ID of the mole image.
     */
    public Mole(String name, boolean is_bad, Bitmap mole_bitmap, Img_Src src)
    {
        this.name = name;
        this.is_bad = is_bad;
        this.mole_bitmap = mole_bitmap;
        this.img_src = src;
    }

    /**
     * Constructor to initialize mole attributes with bitmap image
     *
     * @param name           The name of the mole.
     * @param is_bad         Whether the mole is "bad" or not.
     * @param mole_uri  The Uri ID of the mole image.
     */
    public Mole(String name, boolean is_bad, Uri mole_uri, Img_Src src)
    {
        this.name = name;
        this.is_bad = is_bad;
        this.mole_uri = mole_uri;
        this.img_src = src;
    }

    /**
     * Checks if the mole is bad.
     *
     * @return True if the mole is bad, false otherwise.
     */
    public boolean is_mole_bad()
    {
        return this.is_bad;
    }

    /**
     * Gets the resource ID of the mole image.
     *
     * @return The resource ID of the mole image.
     */
    public int getMole_image_id()
    {
        return this.mole_image_id;
    }

    public Img_Src get_img_src()
    {
        return this.img_src;
    }

    public Bitmap get_mole_bitmap()
    {
        return mole_bitmap;
    }

    public Uri get_mole_uri()
    {
        return mole_uri;
    }

    /**
     * Adds a mole to the appropriate list (good or bad).
     *
     * @param new_mole The mole to be added.
     */
    public static void add_mole_to_list(Mole new_mole)
    {
        ArrayList<Mole> mole_list = new_mole.is_mole_bad() ? new_mole.bad_moles : new_mole.good_moles;
        if (mole_list.size() < 11)
        {
            mole_list.add(new_mole);
        }
        else
        {
            new_mole.remove_mole(new_mole.is_mole_bad());
            mole_list.add(new_mole);
        }
    }

    /**
     * Removes a mole from the list of good moles.
     *
     * @param is_bad Whether the mole is "bad" or not.
     */
    public void remove_mole(boolean is_bad)
    {
        ArrayList<Mole> mole_list = is_bad ? this.bad_moles : this.good_moles;
        int i;
        for (i = 0; i < mole_list.size(); i++)
        {
            if (mole_list.get(i).get_img_src() == Img_Src.DEFAULT)
            {
                mole_list.remove(i);
                return;
            }
        }
        mole_list.remove(i);
    }

    /**
     * Creates 10 good moles with random names.
     */
    public static void create_good_moles()
    {
        List<String> good_names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack");
        for (int i = 0; i < good_names.size(); i++)
        {
            Mole.add_mole_to_list(new Mole(good_names.get(i), false, R.drawable.mole, Img_Src.DEFAULT));
        }
    }

    /**
     * Creates 10 bad moles with random names.
     */
    public static void create_bad_moles()
    {
        List<String> bad_names = Arrays.asList("John", "Kate", "Leo", "Mia", "Noah", "Olivia", "Peter", "Quinn", "Rose", "Sam");

        for (int i = 0; i < bad_names.size(); i++)
        {
            Mole.add_mole_to_list(new Mole(bad_names.get(i), true, R.drawable.mole, Img_Src.DEFAULT));
        }
    }
}
