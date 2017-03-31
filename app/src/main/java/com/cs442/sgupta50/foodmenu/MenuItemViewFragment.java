package com.cs442.sgupta50.foodmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**********************************
 Name : MenuItemViewFragment

 Use: This fargment is for the details screen
 ***********************************/

public class MenuItemViewFragment extends Fragment {

    private TextView quantity;
    MenuItemClickImpl menuItemClickImpl;
    MenuItem mItem;
    boolean isTabletLayout = false;


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup vg, Bundle bs) {
        View v = i.inflate(R.layout.fragment_menu_item_view, vg, false);

        Bundle b = getArguments();
        mItem = (MenuItem) b.getSerializable("MENU_ITEM");
        TextView name = (TextView) v.findViewById(R.id.ItemName);
        TextView price = (TextView) v.findViewById(R.id.ItemCost);
        TextView ingre = (TextView) v.findViewById(R.id.IngreDetails);
        Button addItm = (Button) v.findViewById(R.id.AddItem);
        Button backBtn = (Button) v.findViewById(R.id.back);
        final TextView q = (TextView) v.findViewById(R.id.quantity);
        addItm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(q.getText()!=null && q.getText().length()>0) {
                    mItem.setQuantity(Integer.parseInt(q.getText().toString()));

                    if ("MenuItemViewActivity".equals(getActivity().getClass().getSimpleName())) {
                        Intent intent = new Intent();
                        intent.putExtra("QUANTITY", mItem);
                        getActivity().setResult(Activity.RESULT_OK, intent);

                        Toast toast = Toast.makeText(getActivity(), "Your item has been added", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        menuItemClickImpl = (MenuItemClickImpl) getActivity();
                        menuItemClickImpl.modifyTotal(mItem);
                        Toast toast = Toast.makeText(getActivity(), "Your item has been added", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getActivity(), "Please specify some quantity", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        /*Disabling back button in landscape mode*/
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.replaceFrame);
        if (frameLayout != null && frameLayout.getVisibility() == View.VISIBLE)
            isTabletLayout = true;
        if (isTabletLayout) {
            backBtn.setVisibility(View.INVISIBLE);
        }


        quantity = (TextView) v.findViewById(R.id.quantity);

        name.setText(mItem.getName());
        Double p = mItem.getPrice();
        price.setText(Double.toString(p)+" $");
        ingre.setText(mItem.getIngredients());
        return v;

    }


}



