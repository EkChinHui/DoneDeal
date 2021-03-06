package com.nus.donedeal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * Activity to enter trip name and add members
 * after which the members can be viewed
 * when all members have been added
 * trip can be confirmed and expenditure can be added
 * (members can no longer be added after confirmation)
 */

public class HomeActivity extends Activity {
    DatabaseHelper mDatabaseHelper;
    Button btn_add, btn_view, btn_confirm;
    EditText t_name, t_tripName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
        btn_add = findViewById(R.id.btn_add);
        t_name = findViewById(R.id.t_name);
        t_tripName = findViewById(R.id.editText_tripName);
        mDatabaseHelper = new DatabaseHelper(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = t_name.getText().toString();
                if (newEntry.length() != 0) {
                    addData(newEntry);
                    t_name.setText("");
                }
                else {
                    toastMessage("Field cannot be empty");
                }
            }
        });
        btn_view = findViewById(R.id.btn_view);
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NamesActivity.class);
                startActivity(intent);
            }
        });
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = t_tripName.getText().toString();
                if(tripName.length() != 0) {
                    changeStatus(tripName);
                    finish();
                    Intent intent = new Intent(HomeActivity.this, AddExpenditureActivity.class);
                    startActivity(intent);
                } else {
                    toastMessage("Please enter a trip name.");
                }
            }
        });
    }

    public void addData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if (insertData) {
            toastMessage("Added member");
        }
        else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void changeStatus(String tripName) {
        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Status", 1); //switch so that plus button in main page no longer leads to this activity
        editor.putString("TripName", tripName); // changes the trip name in main page
        editor.apply();
    }

}
