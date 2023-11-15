package com.example.whack_it;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mole {
    private String name;
    private boolean is_bad;
    private int mole_image_id;
    private boolean is_hidden = true;
    private boolean is_user_made = false;

    // Lists to hold good and bad moles
    public static ArrayList<Mole> good_moles = new ArrayList<>();
    public static ArrayList<Mole> bad_moles = new ArrayList<>();

    // Constructor to initialize mole attributes
    public Mole(String name, boolean is_bad, int mole_image_id) {
        this.name = name;
        this.is_bad = is_bad;
        this.mole_image_id = mole_image_id;
    }

    // Adds a mole to the appropriate list (good or bad)
    public static void add_mole_to_list(Mole new_mole, boolean is_bad) {
        ArrayList<Mole> mole_list = is_bad ? new_mole.bad_moles : new_mole.good_moles;

        if (mole_list.size() < 11) {
            mole_list.add(new_mole);
        } else {
            new_mole.remove_mole(is_bad);
            mole_list.add(new_mole);
        }
    }

    // Gets the mole image ID
    public int getMole_image_id() {
        return this.mole_image_id;
    }

    // Removes a mole from the list of good moles
    public void remove_mole(boolean is_bad) {
        ArrayList<Mole> mole_list = is_bad ? this.bad_moles : this.good_moles;

        for (int i = 0; i < mole_list.size(); i++) {
            if (!mole_list.get(i).is_user_made) {
                mole_list.remove(i);
                return;
            }
        }
        // TODO: Add error handling when all moles in the list are user-made
    }

    // Creates 10 good moles with random names
    public static void create_good_moles() {
        List<String> good_names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack");

        for (int i = 0; i < good_names.size(); i++) {
            Mole.add_mole_to_list(new Mole(good_names.get(i), false, R.drawable.mole), false);
        }
    }

    // Creates 10 bad moles with random names
    public static void create_bad_moles() {
        List<String> bad_names = Arrays.asList("John", "Kate", "Leo", "Mia", "Noah", "Olivia", "Peter", "Quinn", "Rose", "Sam");

        for (int i = 0; i < bad_names.size(); i++) {
            Mole.add_mole_to_list(new Mole(bad_names.get(i), true, R.drawable.mole), true);
        }
    }
}
