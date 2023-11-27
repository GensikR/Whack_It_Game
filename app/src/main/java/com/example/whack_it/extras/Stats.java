package com.example.whack_it.extras;

import java.util.ArrayList;
import java.util.Collections;

public class Stats
{
    public static int good_taps = 0;
    public static int bad_taps = 0;
    public static double accuracy = 0.0;
    public static ArrayList<Integer> best_scores = new ArrayList<>();

    /**
     * Adds score to best score list and sorts list in descending order
     * @param score score to be added
     */
    public static void add_score_to_list(int score)
    {
        Stats.best_scores.add(score);
        Collections.sort(Stats.best_scores, Collections.reverseOrder());
    }

    public static void set_accuracy()
    {
        Stats.accuracy = Stats.good_taps / Stats.bad_taps;
    }

}
