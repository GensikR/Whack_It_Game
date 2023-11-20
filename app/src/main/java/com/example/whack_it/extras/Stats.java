package com.example.whack_it.extras;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Stats
{
    public static int good_taps = 0;
    public static int bad_taps = 0;
    private double accuracy = 0.0;
    public static ArrayList<Integer> best_scores = new ArrayList<>();

    /**
     * Adds score to best score list and sorts list in descending order
     * @param score score to be added
     */
    public void add_score_to_list(int score)
    {
        Stats.best_scores.add(score);
        Collections.sort(Stats.best_scores, Collections.reverseOrder());
    }
    public void update_accuracy()
    {
        this.accuracy = (Stats.good_taps / Stats.bad_taps);
    }
    public double get_accuracy()
    {
        return this.accuracy;
    }
    public void reset_accuracy()
    {
        this.accuracy = 0.0;
    }

}
