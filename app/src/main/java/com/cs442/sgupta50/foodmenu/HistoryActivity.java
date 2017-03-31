package com.cs442.sgupta50.foodmenu;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.cs442.sgupta50.foodmenu.data.OrderHistoryDbHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**********************************
 Name : HistoryActivity

 Use: This activity is for displaying the past orders
 ***********************************/

public class HistoryActivity extends Activity {

    ListView list;
    ArrayAdapter<PlacedOrderDetails> listAdapter;
    OrderHistoryDbHelper mDbHelper;
    List<PlacedOrderDetails> placedOrderDetails;
    SQLiteDatabase db;
    String total;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        placedOrderDetails = new ArrayList<>();
        mDbHelper = new OrderHistoryDbHelper(getApplicationContext());
        db = mDbHelper.getReadableDatabase();
        list = (ListView) findViewById(R.id.historyList);

        // Creating the list adapter and populating the list
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, placedOrderDetails) {

            public View getView(int position, View convertView, ViewGroup parent) {
                TwoLineListItem row;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
                } else {
                    row = (TwoLineListItem) convertView;
                }
                PlacedOrderDetails data = placedOrderDetails.get(position);
                row.getText1().setText(data.getTimestamp() + "\nTotal: " + data.getTotal());
                row.getText2().setText(data.displayOrderList());

                return row;
            }
        };

        //retrieving the data from database
        String sql = "select * from orders order by _Id";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() == 0)

        {
            TextView tv = (TextView) findViewById(R.id.NoHistory);
            tv.setVisibility(View.VISIBLE);
        }

        Gson gson = new Gson();
        while (cursor.moveToNext())

        {
            PlacedOrderDetails p = new PlacedOrderDetails();
            p.setTimestamp(cursor.getString(cursor.getColumnIndex("orderDate")));
            String list = cursor.getString(cursor.getColumnIndex("listItems"));
            total = cursor.getString(cursor.getColumnIndex("total"));
            p = gson.fromJson(list, PlacedOrderDetails.class);
            p.setTotal(total);
            placedOrderDetails.add(p);


        }

        list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    ;


}
