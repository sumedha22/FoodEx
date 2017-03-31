package com.cs442.sgupta50.foodmenu;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.sgupta50.foodmenu.data.OrderHistoryDbHelper;
import com.cs442.sgupta50.foodmenu.data.OrderReaderContract;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


/**********************************
 * Name : PlaceOrderFragment
 *
 * Use: This activity is for Order Summary Screen
 ***********************************/

public class PlaceOrderFragment extends Fragment {

    ArrayAdapter<MenuItem> aa;
    double total;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View placeOrderFragment = inflater.inflate(R.layout.activity_order_confirm, container, false);
        Bundle bundle = getArguments();
        final ArrayList<MenuItem> menuItems = (ArrayList<MenuItem>) bundle.getSerializable("MENU_ITEM");
        final MainActivity main = (MainActivity) getActivity();
        ListView myListView;
        myListView = (ListView) placeOrderFragment.findViewById(R.id.finalListView);
        TextView orderTotal = (TextView) placeOrderFragment.findViewById(R.id.orderTotal);
        Button checkOut = (Button) placeOrderFragment.findViewById(R.id.checkOut);

        checkOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Gson gson = new Gson();
                OrderHistoryDbHelper mDbHelper = new OrderHistoryDbHelper(getContext());
                // Gets the data repository in write mode
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                PlacedOrderDetails placedOrderDetails = new PlacedOrderDetails();
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm a");
                Date date = new Date();
                placedOrderDetails.setTotal("" + total);
                placedOrderDetails.setTimestamp("" + dateFormat.format(date));
                placedOrderDetails.setListOfSelectedItems(menuItems);
                String orderedList = gson.toJson(placedOrderDetails);

                String currentTimeStamp = dateFormat.format(new Date()); // Find todays date
                values.put(OrderReaderContract.OrderReader.COLUMN_NAME_DATE, currentTimeStamp);
                values.put(OrderReaderContract.OrderReader.COLUMN_NAME_SELECTED_ITEMS, orderedList);
                values.put(OrderReaderContract.OrderReader.COLUMN_NAME_TOTAL, total);

                // Insert the new row, returning the primary key value of the new row
                db.insert(OrderReaderContract.OrderReader.TABLE_NAME, null, values);
                Toast toast = Toast.makeText(getContext(), "Your order has been confirmed", Toast.LENGTH_LONG);
                toast.show();
                main.reset();
                finish();
            }
        });

        Iterator<MenuItem> i = menuItems.iterator();
        total = 0;
        while (i.hasNext()) {
            MenuItem m = i.next();
            if (m != null) {
                if (!(m.getQuantity() > 0)) {
                    i.remove();
                }
                total += m.getQuantity() * m.getPrice();
            }
        }

        orderTotal.setText("Total $" + total);


        // Create the Array Adapter to bind the array to the List View
        aa = new ArrayAdapter<MenuItem>(getActivity(), android.R.layout.simple_list_item_1, menuItems);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();

        return placeOrderFragment;
    }

    private void finish() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


}
