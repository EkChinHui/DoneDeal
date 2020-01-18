package com.nus.donedeal;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddExpenditureActivity extends Activity {
    Button btn_enterExpenditure, btn_viewExpenditure;
    EditText editText_description, editText_amount;
    Spinner spinner_paidBy;
    DatabaseHelper1 mDatabaseHelper1;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> allNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpenditurelayout);
        btn_enterExpenditure = findViewById(R.id.btn_enterexpenditure);
        btn_viewExpenditure = findViewById(R.id.btn_viewexpenditure);
        editText_description = findViewById(R.id.editText_description);
        editText_amount = findViewById(R.id.editText_amount);
        spinner_paidBy = findViewById(R.id.spinner_paidBy);
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper1 = new DatabaseHelper1(this);
        allNames = mDatabaseHelper.getAllNames();

        btn_enterExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editText_description.getText().toString();
                Float price = Float.parseFloat(editText_amount.getText().toString());
                String paidBy = spinner_paidBy.getSelectedItem().toString();
                addData(description, price, paidBy);
            }
        });

        btn_viewExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(AddExpenditureActivity.this, ViewExpenditureActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_paidBy.setAdapter(adapter);
    }
    public void addData(String description, Float price, String paidBy) {
        boolean insertData = mDatabaseHelper1.addData(description, price, paidBy);
        if (insertData) {
            toastMessage("Added expenditure");
        }
        else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
