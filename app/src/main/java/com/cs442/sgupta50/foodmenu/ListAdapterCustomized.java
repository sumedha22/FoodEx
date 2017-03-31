package com.cs442.sgupta50.foodmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**********************************
Name : ListAdapterCustomized

 Use: This class is for defining Custom List Adapter which includes a text and button in a row
 ***********************************/

public class ListAdapterCustomized extends ArrayAdapter<MenuItem> {

    Context context;
    FoodItemListFragment foodItemListFragment;

    public ListAdapterCustomized(Context context, ArrayList<MenuItem> menuItem, FoodItemListFragment foodItemListFragment) {
        super(context, 0, menuItem);
        this.context = context;
        this.foodItemListFragment = foodItemListFragment;
    }

    @Override
    public View getView(int p, View view, ViewGroup group) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_row_adapter, group, false);
        final MenuItem menuItem = getItem(p);
        TextView itemName = (TextView) view.findViewById(R.id.listItemName);
        TextView itemButton = (TextView) view.findViewById(R.id.viewListItem);
        itemName.setText(menuItem.toString());

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (foodItemListFragment != null) {
                    foodItemListFragment.listItemClick(menuItem);
                }

            }


        });

        return view;
    }

}
