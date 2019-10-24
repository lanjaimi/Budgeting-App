package com.example.lahcen.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public void goToSpending (View view){

        Intent intent = new Intent(getApplicationContext(),SpendingActivity.class);

        startActivity(intent);
    }

    public void goToBudget (View view){

        Intent intent = new Intent(getApplicationContext(),BudgetActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
