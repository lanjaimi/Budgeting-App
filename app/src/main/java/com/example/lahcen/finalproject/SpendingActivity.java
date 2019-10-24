package com.example.lahcen.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpendingActivity extends AppCompatActivity {
    DatabaseReference DatabaseReferenceSpending;
    DatabaseReference DatabaseReferenceBudget;

    ArrayList<Item> arrayOfItems=new ArrayList<>();
    ArrayList<BudgetItem> arrayOfBudgets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);

        DatabaseReferenceSpending = FirebaseDatabase.getInstance().getReference("spending");
        DatabaseReferenceBudget = FirebaseDatabase.getInstance().getReference("budget");


    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReferenceSpending.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    Item item=itemSnapshot.getValue(Item.class);
                    arrayOfItems.add(item);
                }
                ExpensesListAdapter adapter = new ExpensesListAdapter(SpendingActivity.this, arrayOfItems);
                ListView listView = findViewById(R.id.ExpensesList);

                listView.setAdapter(adapter);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReferenceBudget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    BudgetItem budgetItem = itemSnapshot.getValue(BudgetItem.class);
                    arrayOfBudgets.add(budgetItem);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void addNewExpense (View view){

        Intent intent = new Intent(getApplicationContext(), AddSpendingActivity.class);

        startActivity(intent);
    }

    public void goHome (View view){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        updateAmountsSpent();

        startActivity(intent);
    }

    public double[] amountsSpent(ArrayList<Item> items){
        double[] amountsSpent = new double[7];
        for(int i = 0; i < items.size(); i++){
            switch (items.get(i).getCategory().trim()){
                case"Housing": amountsSpent[0] += Double.parseDouble(items.get(i).getAmount());
                    break;
                case"Bills, Utilities":amountsSpent[1] += Double.parseDouble(items.get(i).getAmount());
                    break;
                case"Food, Dining": amountsSpent[2] += Double.parseDouble(items.get(i).getAmount());
                    break;
                case"Auto, Transport":amountsSpent[3] += Double.parseDouble(items.get(i).getAmount());
                    break;
                case"Fees, Charges":amountsSpent[4] += Double.parseDouble(items.get(i).getAmount());
                    break;
                case"Entertainment": amountsSpent[5] += Double.parseDouble(items.get(i).getAmount());
                    break;
                case"Other":amountsSpent[6] += Double.parseDouble(items.get(i).getAmount());
                    break;
                default: amountsSpent[0] += Double.parseDouble(items.get(i).getAmount());
                    break;

            }//end switch
        }


        return amountsSpent;
    }

    public void updateAmountsSpent(){

        double[] amountsSpent = amountsSpent(arrayOfItems);

        for(int i = 0; i < 7; i++){
            DatabaseReference updateData = FirebaseDatabase.getInstance()
                    .getReference("budget")
                    .child(arrayOfBudgets.get(i).getId());
            updateData.child("amountSpent").setValue(amountsSpent[i]);
        }

    }

}
