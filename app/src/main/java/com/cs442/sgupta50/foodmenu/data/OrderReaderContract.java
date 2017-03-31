package com.cs442.sgupta50.foodmenu.data;

import android.provider.BaseColumns;

/**********************************
 Name : OrderReaderContract

 Use: This class defines the table contents
 ***********************************/

public class OrderReaderContract implements BaseColumns {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private OrderReaderContract() {}

    /* Inner class that defines the table contents */
    public static class OrderReader implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_NAME_DATE = "orderDate";
        public static final String COLUMN_NAME_SELECTED_ITEMS = "listItems";
        public static final String COLUMN_NAME_TOTAL = "total";


    }
}
