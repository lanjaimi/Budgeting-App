package com.example.lahcen.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpensesListAdapter extends ArrayAdapter<Item> {

    public ExpensesListAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_in_list, parent, false);
        }

        Item item = getItem(position);

        TextView date = convertView.findViewById(R.id.date);
        TextView description = convertView.findViewById(R.id.description);
        TextView amount = convertView.findViewById(R.id.amount);
        ImageView iconIV = convertView.findViewById(R.id.img);
        date.setText(item.getDate());
        description.setText(item.getDescription());
        amount.setText("$" + item.getAmount());

        switch (item.getCategory()) {
            case "Other"://description.setTextColor(Color.parseColor("#74d680"));
                iconIV.setImageResource(R.drawable.money);
                //  amount.setTextColor(Color.parseColor("#74d680"));
                break;
            case "Housing": //description.setTextColor(Color.parseColor("#77aaff"));
                iconIV.setImageResource(R.drawable.house);
                // amount.setTextColor(Color.parseColor("#77aaff"));
                break;
            case "Bills, Utilities":// description.setTextColor(Color.parseColor("#f9d70b"));
                iconIV.setImageResource(R.drawable.smartphone);
                //  amount.setTextColor(Color.parseColor("#f9d70b"));
                break;
            case "Food, Dining": //description.setTextColor(Color.parseColor("#edc951"));
                iconIV.setImageResource(R.drawable.dish);
                // amount.setTextColor(Color.parseColor("#edc951"));
                break;
            case "Auto, Transport":// description.setTextColor(Color.parseColor("#cc2a36"));
                iconIV.setImageResource(R.drawable.car);
                //  amount.setTextColor(Color.parseColor("#cc2a36"));
                break;
            case "Fees, Charges":// description.setTextColor(Color.parseColor("#005073"));
                iconIV.setImageResource(R.drawable.bank);
                //  amount.setTextColor(Color.parseColor("#005073"));
                break;
            case "Entertainment": //description.setTextColor(Color.parseColor("#ff8000"));
                iconIV.setImageResource(R.drawable.tickets);
                //  amount.setTextColor(Color.parseColor("#ff8000"));
                break;
            default: //description.setTextColor(Color.parseColor("#74d680"));
                iconIV.setImageResource(R.drawable.money);
                //  amount.setTextColor(Color.parseColor("#74d680"));
                break;


        }


        return convertView;


    }
}
