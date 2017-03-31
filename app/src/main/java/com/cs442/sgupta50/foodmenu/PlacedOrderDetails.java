package com.cs442.sgupta50.foodmenu;


import java.util.ArrayList;

/**********************************
 Name : PlacedOrderDetails

 Use: This class is for defining all the attributes of the orders that have been placed
 ***********************************/

public class PlacedOrderDetails {

    String id;
    String timestamp;
    String total;
    ArrayList<MenuItem> listOfSelectedItems;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<MenuItem> getListOfSelectedItems() {
        return listOfSelectedItems;
    }

    public void setListOfSelectedItems(ArrayList<MenuItem> listOfSelectedItems) {
        this.listOfSelectedItems = listOfSelectedItems;
    }


    public String displayOrderList() {
        String listOfItem = "";
        for (MenuItem item : listOfSelectedItems) {
            listOfItem += "Ordered " + item.getQuantity() + " " +
                    item.getName() + " at price " + item.getPrice() + ".";

        }
        return listOfItem;
    }

}
