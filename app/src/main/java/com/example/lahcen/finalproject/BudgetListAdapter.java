package com.example.lahcen.finalproject;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BudgetListAdapter extends ArrayAdapter<BudgetItem> {

    public BudgetListAdapter(Context context, ArrayList<BudgetItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.budget_in_list, parent, false);
        }

        BudgetItem item = getItem(position);

        TextView name = convertView.findViewById(R.id.cat);
        TextView left =  convertView.findViewById(R.id.left);
        TextView spent =  convertView.findViewById(R.id.spent);
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        double difference = (item.getAmount() - item.getAmountSpent());
        int progress = (int) ((100 * item.getAmountSpent()) / item.getAmount());

        name.setText(item.getName());
        spent.setText(Double.toString(item.getAmountSpent()) + " of " +Double.toString(item.getAmount()));
        left.setText("$"+ Double.toString(difference) + " Left");
        progressBar.setProgress(progress);



        if(progress>= 75 && progress <= 100){
            progressBar.setProgressDrawable(ContextCompat.getDrawable(getContext(), R.drawable.progressbaryellow));

        }else if (progress > 100) {
            progressBar.setProgressDrawable(ContextCompat.getDrawable(getContext(), R.drawable.progressbarred));
            left.setText("$"+Double.toString((-1)*(difference)) + " Over");
            progressBar.setProgress(99);

        }


        return convertView;

    }
}
