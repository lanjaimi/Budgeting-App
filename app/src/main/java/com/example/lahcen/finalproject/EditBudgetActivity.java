package com.example.lahcen.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditBudgetActivity extends AppCompatActivity {

    private DatabaseReference DBRefrence = FirebaseDatabase.getInstance().getReference("budget");
    private ArrayList<BudgetItem> arrayOfBudgets = new ArrayList<>();


   @Override
    protected void onStart() {
        super.onStart();
        DBRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    BudgetItem budgetItem=itemSnapshot.getValue(BudgetItem.class);
                    arrayOfBudgets.add(budgetItem);
                }
                populateView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);

    }

    public void saveBudget(View view){

       double[] data = getData();

       for(int i = 0; i < 7; i++){
           DatabaseReference updateData = FirebaseDatabase.getInstance()
                   .getReference("budget")
                   .child(arrayOfBudgets.get(i).getId());
           updateData.child("amount").setValue(data[i]);
       }

        Toast.makeText(getApplicationContext(), "Success!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), BudgetActivity.class);

        startActivity(intent);
    }

    public void cancelBudget(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure you want to cancel your changes?")
                .setMessage("")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"The changes have been canceled",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), BudgetActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public double [] getData(){
       double[] data = new double [7];
        TextView housing = findViewById(R.id.editText1);
        TextView utilities = findViewById(R.id.editText2);
        TextView food = findViewById(R.id.editText3);
        TextView transport = findViewById(R.id.editText4);
        TextView fees = findViewById(R.id.editText5);
        TextView entertainment = findViewById(R.id.editText6);
        TextView other = findViewById(R.id.editText7);

        data[0] = Double.parseDouble(housing.getText().toString().trim());
        data[1] = Double.parseDouble(utilities.getText().toString().trim());
        data[2] = Double.parseDouble(food.getText().toString().trim());
        data[3] = Double.parseDouble(transport.getText().toString().trim());
        data[4] = Double.parseDouble(fees.getText().toString().trim());
        data[5] = Double.parseDouble(entertainment.getText().toString().trim());
        data[6] = Double.parseDouble(other.getText().toString().trim());


       return data;
    }

    public void populateView(){
        TextView housing = findViewById(R.id.editText1);
        TextView utilities = findViewById(R.id.editText2);
        TextView food = findViewById(R.id.editText3);
        TextView transport = findViewById(R.id.editText4);
        TextView fees = findViewById(R.id.editText5);
        TextView entertainment = findViewById(R.id.editText6);
        TextView other = findViewById(R.id.editText7);

        housing.setText(Double.toString(arrayOfBudgets.get(0).getAmount()));
        utilities.setText(Double.toString(arrayOfBudgets.get(1).getAmount()));
        food.setText(Double.toString(arrayOfBudgets.get(2).getAmount()));
        transport.setText(Double.toString(arrayOfBudgets.get(3).getAmount()));
        fees.setText(Double.toString(arrayOfBudgets.get(4).getAmount()));
        entertainment.setText(Double.toString(arrayOfBudgets.get(5).getAmount()));
        other.setText(Double.toString(arrayOfBudgets.get(6).getAmount()));
    }

}
