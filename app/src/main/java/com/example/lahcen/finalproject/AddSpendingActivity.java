package com.example.lahcen.finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddSpendingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseReference DatabaseReference;

    ArrayList<Item> arrayOfItems = new ArrayList<>();

    Item item = new Item();

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    arrayOfItems.add(item);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending);
        DatabaseReference = FirebaseDatabase.getInstance().getReference("spending");
        createSpinner();
        displayCurrentDate();
        pickDate();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String currentDateSubString = currentDateString.substring(0, currentDateString.length() - 6);

        Button button = findViewById(R.id.datePickerButton);

        button.setText(currentDateSubString);

        item.setDate(currentDateSubString);


    }

    public void pickDate() {
        Button button = findViewById(R.id.datePickerButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFrag();

                datePicker.show(getSupportFragmentManager(), "date Picker");

            }
        });
    }

    public void displayCurrentDate() {
        Calendar c = Calendar.getInstance();
        Button button = findViewById(R.id.datePickerButton);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String currentDateSubString = currentDateString.substring(0, currentDateString.length() - 6);
        button.setText(currentDateSubString);
    }

    public void createSpinner() {

        final Spinner categories = findViewById(R.id.categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddSpendingActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(adapter);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String selectedCategory = categories.getSelectedItem().toString();

                item.setCategory(selectedCategory);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                item.setCategory("Other");
            }
        });
    }

    public boolean createItem() {

        boolean error = false;

        TextView description = findViewById(R.id.description);
        TextView amount = findViewById(R.id.amount);
        TextView descErrorMsg = findViewById(R.id.desc_error_msg);
        TextView amountErrorMsg = findViewById(R.id.amount_error_msg);

        if (description.getText().toString().length() < 3 || description.getText().toString().length() > 12) {
            error = true;
            descErrorMsg.setVisibility(View.VISIBLE);
            description.setHintTextColor(Color.parseColor("#FFCC0000"));

        } else {
            item.setDescription(description.getText().toString());

        }

        if (amount.getText().toString().length() <= 0 || Double.parseDouble(amount.getText().toString()) == 0.0) {
            error = true;
            amountErrorMsg.setVisibility(View.VISIBLE);
            amount.setHintTextColor(Color.parseColor("#FFCC0000"));
        } else {
            item.setAmount(amount.getText().toString());
        }

        return error;

    }

    public void saveItem(View view) {


        createItem();

        if (!createItem()) {
            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SpendingActivity.class);

            String id = DatabaseReference.push().getKey();

            item.setid(id);

            DatabaseReference.child(id).setValue(item);

            startActivity(intent);
        } else {

            Toast.makeText(getApplicationContext(), "Please Correct Your Input!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void cancelItem(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Do you want to discard this item?")
                .setMessage("")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "The Item is Deleted ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SpendingActivity.class);

                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
