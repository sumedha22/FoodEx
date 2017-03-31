package com.cs442.sgupta50.foodmenu;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
 Name : PlaceOrderActivity

 Use: This activity is for Order Summary Screen
 ***********************************/

public class PlaceOrderActivity extends AppCompatActivity {

    ArrayAdapter<MenuItem> aa;
    double total;

    Activity my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        my = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        final Intent intent = getIntent();
        final ArrayList<MenuItem> menuItems = (ArrayList<MenuItem>) intent.getSerializableExtra("MENU_ITEM");
        intent.putExtra("DO_RESET", false);
        my.setResult(6, intent);

        ListView myListView;
        myListView = (ListView) findViewById(R.id.finalListView);
        TextView orderTotal = (TextView) findViewById(R.id.orderTotal);
        Button checkOut = (Button) findViewById(R.id.checkOut);

        checkOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Gson gson = new Gson();
                OrderHistoryDbHelper mDbHelper = new OrderHistoryDbHelper(getApplicationContext());
                // Gets the data repository in write mode
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                PlacedOrderDetails placedOrderDetails = new PlacedOrderDetails();
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm a");
                Date date = new Date();
                placedOrderDetails.setTotal(""+total);
                placedOrderDetails.setTimestamp("" + dateFormat.format(date));
                placedOrderDetails.setListOfSelectedItems(menuItems);
                String orderedList = gson.toJson(placedOrderDetails);

                String currentTimeStamp = dateFormat.format(new Date()); // Find todays date
                values.put(OrderReaderContract.OrderReader.COLUMN_NAME_DATE, currentTimeStamp);
                values.put(OrderReaderContract.OrderReader.COLUMN_NAME_SELECTED_ITEMS, orderedList);
                values.put(OrderReaderContract.OrderReader.COLUMN_NAME_TOTAL, total);

                // Insert the new row, returning the primary key value of the new row
                db.insert(OrderReaderContract.OrderReader.TABLE_NAME, null, values);
                Toast toast = Toast.makeText(getApplicationContext(), "Your order has been confirmed", Toast.LENGTH_LONG);
                toast.show();

//                }
                intent.putExtra("DO_RESET", true);
                my.setResult(6, intent);

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

        orderTotal.setText(""+total);


        // Create the Array Adapter to bind the array to the List View
        aa = new ArrayAdapter<MenuItem>(this, android.R.layout.simple_list_item_1, menuItems);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();


    }


}
