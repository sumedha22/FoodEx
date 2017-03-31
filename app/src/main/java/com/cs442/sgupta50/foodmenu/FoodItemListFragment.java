package com.cs442.sgupta50.foodmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**********************************
 Name : FoodItemListFragment

 Use: This Fragment is the main fragmnet and has all the button listeners from the first screen
 ***********************************/

public class FoodItemListFragment extends Fragment {

    int id = 0;
    boolean isTabletLayout = false;
    ListView myListView;
    ArrayAdapter<MenuItem> aa;
    ArrayList<MenuItem> menuItems;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View foodItemListFragment = inflater.inflate(R.layout.food_item_list_fragment, container, false);
        myListView = (ListView) foodItemListFragment.findViewById(R.id.myListView);
        myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        final TextView totalTextView = (TextView) foodItemListFragment.findViewById(R.id.total);

        // Create the Array List of to do items
        menuItems = new ArrayList<MenuItem>();

        // Create the Array Adapter to bind the array to the Customized List View
        aa = new ListAdapterCustomized(getActivity(), menuItems, this);

        //Order Button Listener
        Button order = (Button) foodItemListFragment.findViewById(R.id.Order);
        order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String total = String.valueOf(totalTextView.getText());
                double t = Double.parseDouble(total);
                if (t > 0) {

                    if (isTabletLayout) {

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("MENU_ITEM", menuItems);
                        PlaceOrderFragment placeOrderFragment = new PlaceOrderFragment();
                        placeOrderFragment.setArguments(bundle);
                        FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.replaceFrame, placeOrderFragment);
                        tx.addToBackStack(null);
                        tx.commit();

                    } else {
                        Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                        intent.putExtra("MENU_ITEM", menuItems);
                        getActivity().startActivityForResult(intent, 6);
                    }

                } else {
                    Toast toast = Toast.makeText(getActivity(), "Your cart is empty", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //History Button Listener
        Button orderHistory = (Button) foodItemListFragment.findViewById(R.id.viewHistory);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        setFoodItems(aa);
        myListView.setAdapter(aa);
        return foodItemListFragment;
    }

    //After order reset the list
    public void resetList() {
        for (MenuItem menuItem : menuItems) {
            menuItem.setQuantity(0);
        }
        aa.notifyDataSetChanged();
    }

    public void listItemClick(MenuItem menuItem) {

        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.replaceFrame);
        if (frameLayout != null && frameLayout.getVisibility() == View.VISIBLE)
            isTabletLayout = true;
        if (isTabletLayout) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("MENU_ITEM", menuItem);
            MenuItemViewFragment menuItemViewFragment = new MenuItemViewFragment();
            menuItemViewFragment.setArguments(bundle);
            FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.replaceFrame, menuItemViewFragment);
            tx.addToBackStack(null);
            tx.commit();

        } else {
            Intent intent = new Intent(getContext(), MenuItemViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("MENU_ITEM", menuItem);
            intent.putExtra("INFO", bundle);
            getActivity().startActivityForResult(intent, 5);
        }


    }


    /*set the menu list*/
    private void setFoodItems(ArrayAdapter<MenuItem> foodItemArrayAdapter) {
        MenuItem burger = new MenuItem();
        burger.setName("Black Bean Burger");
        burger.setId(++id);
        burger.setPrice(6.50);
        burger.setIngredients("Black beans are a perfect substitute for the meat in a hamburger recipe because they're high in protein and have a great consistency for " +
                "mashing together into patties." +
                "Contents: Black Bean Patty, Cheese, Jalapenos, Onion Rings, Tomato, Mayo Spicy");
        burger.setCuisine("American");
        MenuItem pizza = new MenuItem();
        pizza.setName("Veggie Delight Pizza");
        pizza.setId(++id);
        pizza.setPrice(19);
        pizza.setCuisine("Italian");
        pizza.setIngredients("a flatbread generally topped with tomato sauce and cheese and baked in an oven. It is commonly topped with a selection of meats, vegetables and condiments" +
                "Contents: Baby Corn, Bell Peppers, mozzarella cheese,Thin crust");
        MenuItem rajmaRice = new MenuItem();
        rajmaRice.setName(" Rajma Rice");
        rajmaRice.setId(++id);
        rajmaRice.setPrice(10);
        rajmaRice.setIngredients("Rajma is a popular Indian vegetarian dish consisting of red kidney beans in a thick gravy with many Indian whole spices and usually served with rice" +
                "Contents: Red Beans,Onion, Tomato, Basmati Rice");
        rajmaRice.setCuisine("Indian");
        MenuItem hakkaNoodles = new MenuItem();
        hakkaNoodles.setName(" Hakka Noodles");
        hakkaNoodles.setId(++id);
        hakkaNoodles.setPrice(15);
        hakkaNoodles.setIngredients("Hakka noodles is a Chinese preparation where boiled noodles are stir fried with sauces and vegetables or meats" +
                "Contents: Rice noodles, Soya sauce, Vinegar, veggies");
        hakkaNoodles.setCuisine("Chinese");
        MenuItem shahiPaneer = new MenuItem();
        shahiPaneer.setName(" Shahi Paneer");
        shahiPaneer.setId(++id);
        shahiPaneer.setPrice(24);
        shahiPaneer.setIngredients("Shahi paneer is a typical North Indian dish eaten with roti or naan.Shahi paneer is preparation of paneer pieces in a thick, creamy and spicy gravy prepared in tomato, onion and cashewnuts paste" +
                "Contents: Cheese,Tomato gravy,Onions,Butter");
        shahiPaneer.setCuisine("Indian");
        foodItemArrayAdapter.addAll(burger, pizza, rajmaRice, hakkaNoodles, shahiPaneer);

    }

    //modifying the count after adding
    public void modifyCount(MenuItem item) {
        for (MenuItem m : menuItems) {
            if (item.getName().equals(m.getName())) {
                m.setQuantity(item.getQuantity());
                break;
            }
        }
        aa.notifyDataSetChanged();

    }

}
