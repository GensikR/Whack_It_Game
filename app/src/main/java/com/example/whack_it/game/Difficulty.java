package com.example.whack_it.game;

public class Difficulty
{
    private String difficulty;
    private int mole_pop_freq;
    private int initial_time;
    private int streak_to_special_ability;
    private final static int  E_MOLE_POP_FREQ = 1000;
    private final static int  E_INITIAL_TIME = 40;
    private final static int  E_STREAK_TARGET = 3;
    private final static int  M_MOLE_POP_FREQ = 500;
    private final static int  M_INITIAL_TIME = 30;
    private final static int  M_STREAK_TARGET = 6;
    private final static int  H_MOLE_POP_FREQ = 250;
    private final static int  H_INITIAL_TIME = 20;
    private final static int  H_STREAK_TARGET = 9;
    public Difficulty(String difficulty)
    {
        this.difficulty = difficulty;
        set_game_settings(difficulty);
    }

    private void set_game_settings(String difficulty)
    {
        switch(difficulty)
        {
            case("Easy"):
                this.mole_pop_freq = E_MOLE_POP_FREQ;
                this.initial_time = E_INITIAL_TIME;
                this.streak_to_special_ability = E_STREAK_TARGET;
                break;
            case("Medium"):
                this.mole_pop_freq = M_MOLE_POP_FREQ;
                this.initial_time = M_INITIAL_TIME;
                this.streak_to_special_ability = M_STREAK_TARGET;
                break;
            case("Hard"):
                this.mole_pop_freq = H_MOLE_POP_FREQ;
                this.initial_time = H_INITIAL_TIME;
                this.streak_to_special_ability = H_STREAK_TARGET;
                break;
            default:
                break;
        }
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    public int getMole_pop_freq()
    {
        return mole_pop_freq;
    }

    public int getInitial_time()
    {
        return initial_time;
    }

    public int getStreak_to_special_ability()
    {
        return streak_to_special_ability;
    }
}
