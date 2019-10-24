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

public class BudgetActivity extends AppCompatActivity {
     private DatabaseReference DatabaseReference;
       ArrayList<BudgetItem> arrayOfBudgets = new ArrayList<>();


    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    BudgetItem budgetItem = itemSnapshot.getValue(BudgetItem.class);
                    arrayOfBudgets.add(budgetItem);
                }

                BudgetListAdapter adapter = new BudgetListAdapter(BudgetActivity.this, arrayOfBudgets);
                ListView listView = findViewById(R.id.budgetList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goToEditBudget (View view){

        Intent intent = new Intent(getApplicationContext(),EditBudgetActivity.class);

        startActivity(intent);
    }

    public void goToHome (View view){

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        DatabaseReference = FirebaseDatabase.getInstance().getReference("budget");


    }
}
