package com.cs442.sgupta50.foodmenu;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**********************************
 Name : MenuItemViewActivity

 Use: This activity is for the details screen
 ***********************************/

public class MenuItemViewActivity extends FragmentActivity {

        @Override
        protected void onCreate(Bundle arg0) {
            setContentView(R.layout.activity_menu_item_view);
            super.onCreate(arg0);

            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                finish();
            }
            Intent intent = this.getIntent();

            Bundle bundle = intent.getBundleExtra("INFO");
            Fragment menuItemViewFragment = new MenuItemViewFragment();
            menuItemViewFragment.setArguments(bundle);

            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.menu_item_view_activity,menuItemViewFragment);
            tx.commit();

        }







}
