package com.example.whack_it.utilities;

import androidx.appcompat.widget.PopupMenu;

import java.util.ArrayList;

public class Popup_Menu
{
    private PopupMenu popupMenu;
    private ArrayList<String> list_of_options;

    public Popup_Menu(PopupMenu popupMenu, ArrayList<String> list_of_options)
    {
        this.popupMenu = popupMenu;
        this.list_of_options = list_of_options;
        // Add difficulty options to the PopupMenu
        for(int i = 0; i < list_of_options.size(); i++)
        {
            this.popupMenu.getMenu().add(list_of_options.get(i));
        }
    }

    public void show_popup_menu()
    {
        this.popupMenu.show();
    }
}
