package com.nus.donedeal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class AddExpenditureActivity extends AppCompatActivity {
    Button btn_addExpenditure, btn_viewExpenditure;
    EditText editText_description, editText_amount;
    Spinner spinner_paidBy, spinner_method;
    DatabaseHelper1 mDatabaseHelper1;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> allNames;

    DatabaseHelper1 databaseHelper1;
    Cursor data;
    OneString oneString;
    ListView listViewSplit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpenditurelayout);
        Toolbar addExpenditureToolbar = findViewById(R.id.addexpendituretoolbar);
        setSupportActionBar(addExpenditureToolbar);
        btn_addExpenditure = findViewById(R.id.btn_addexpenditure);
        btn_viewExpenditure = findViewById(R.id.btn_viewexpenditure);
        editText_description = findViewById(R.id.editText_description);
        editText_amount = findViewById(R.id.editText_amount);
        spinner_paidBy = findViewById(R.id.spinner_paidBy);
        spinner_method = findViewById(R.id.spinner_method);
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper1 = new DatabaseHelper1(this);
        allNames = mDatabaseHelper.getAllNames();



        btn_addExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editText_description.getText().toString();
                String price = editText_amount.getText().toString();
                String paidBy = spinner_paidBy.getSelectedItem().toString();

                editText_amount.setText("");
                editText_description.setText("");
                if (description.length() != 0 && price.length() != 0 && paidBy.length() != 0) {
                    Float float_price = Float.parseFloat(price);
                    addData(description, float_price, paidBy);
                    addContribution(float_price, paidBy);
                    String method = spinner_method.getSelectedItem().toString();
                    if (method.equals("Equally")) {
                        Integer numberOfMembers = spinner_paidBy.getAdapter().getCount();
                        Float equalExpenditure = float_price / numberOfMembers;
                        addExpenditureEqually(equalExpenditure);
                        toastMessage("Split Equally");
                    } else if (method.equals("Manually")) {
                        toastMessage("Split Manually");
                    }
                }
                else {
                    toastMessage("Fields cannot be empty");
                }
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

    public void addExpenditureEqually(Float expenditure) {
        mDatabaseHelper.addExpenditureEqually(expenditure);
    }

    public void addContribution(Float price, String name) {
        mDatabaseHelper.addContribution(price, name);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
