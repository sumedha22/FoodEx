package com.cs442.sgupta50.foodmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;

/**********************************
 Name : MainActivity

 Use: This activity is for the first screen
 ***********************************/

public class MainActivity extends AppCompatActivity implements MenuItemClickImpl {

    private double total = 0.0;
    private HashMap<String, MenuItem> hashMapFoodItem;
    FoodItemListFragment ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hashMapFoodItem = new HashMap<>();
        ft = (FoodItemListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 5) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.i("Sumedha", "");
                MenuItem f = (MenuItem) data.getSerializableExtra("QUANTITY");
                TextView totalTextView = (TextView) findViewById(R.id.total);
                hashMapFoodItem.put(f.getName(), f);
                total = 0;
                if (hashMapFoodItem != null && hashMapFoodItem.size() > 0) {
                    for (MenuItem m : hashMapFoodItem.values()) {
                        total += m.getQuantity() * m.getPrice();

                    }
                }
                totalTextView.setText("" + total);
                ft.modifyCount(f);
            }
        } else if (requestCode == 6) {
            if (data.getBooleanExtra("DO_RESET", false))
                reset();
        }
    }

    public void reset() {
        total = 0;
        TextView totalTextView = (TextView) findViewById(R.id.total);
        totalTextView.setText("" + total);
        ft.resetList();
    }


    @Override
    public void modifyTotal(MenuItem f) {
        TextView totalTextView = (TextView) findViewById(R.id.total);
        hashMapFoodItem.put(f.getName(), f);
        total = 0;
        if (hashMapFoodItem != null && hashMapFoodItem.size() > 0) {
            for (MenuItem m : hashMapFoodItem.values()) {
                total += m.getQuantity() * m.getPrice();

            }
        }
        totalTextView.setText("" + total);
        ft.modifyCount(f);

    }
}
