package com.example.whack_it.mk_mole;

import android.graphics.Bitmap;

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
    private boolean is_hidden = true;
    private boolean user_made;

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
    public Mole(String name, boolean is_bad, int mole_image_id, boolean user_made)
    {
        this.name = name;
        this.is_bad = is_bad;
        this.mole_image_id = mole_image_id;
        this.user_made = user_made;
    }
    /**
     * Constructor to initialize mole attributes with bitmap image
     *
     * @param name           The name of the mole.
     * @param is_bad         Whether the mole is "bad" or not.
     * @param mole_bitmap  The resource ID of the mole image.
     */
    public Mole(String name, boolean is_bad, Bitmap mole_bitmap, boolean user_made)
    {
        this.name = name;
        this.is_bad = is_bad;
        this.mole_bitmap = mole_bitmap;
        this.user_made = user_made;
    }

    /**
     * Checks if the mole is bad.
     *
     * @return True if the mole is bad, false otherwise.
     */
    private boolean is_mole_bad()
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

    public boolean is_user_made()
    {
        return user_made;
    }

    public Bitmap get_mole_bitmap()
    {
        return mole_bitmap;
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
        for (int i = 0; i < mole_list.size(); i++)
        {
            if (!mole_list.get(i).user_made)
            {
                mole_list.remove(i);
                return;
            }
        }
        // TODO: Add error handling when all moles in the list are user-made
    }

    /**
     * Creates 10 good moles with random names.
     */
    public static void create_good_moles()
    {
        List<String> good_names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack");
        for (int i = 0; i < good_names.size(); i++)
        {
            Mole.add_mole_to_list(new Mole(good_names.get(i), false, R.drawable.mole, false));
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
            Mole.add_mole_to_list(new Mole(bad_names.get(i), true, R.drawable.mole, false));
        }
    }
}
