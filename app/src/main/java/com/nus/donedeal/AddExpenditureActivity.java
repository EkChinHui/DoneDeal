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
    Button btn_addExpenditure, btn_viewExpenditure, btn_splitmanual;
    EditText editText_description, editText_amount;
    Spinner spinner_paidBy, spinner_method;
    DatabaseHelper1 mDatabaseHelper1;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> allNames;

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
        btn_splitmanual = findViewById(R.id.btn_splitmanually);
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
                        populateSplitListView();
                        btn_splitmanual.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Integer numberOfMembers = spinner_paidBy.getAdapter().getCount();
                                try {
                                    for (int i = 1; i <= numberOfMembers; i++) {
                                        OneString checker = (OneString) listViewSplit.getItemAtPosition(i-1);
                                        Log.d("TEST", "onClick: " + checker.toString());
                                        Float expenses = checker.getPrice();
                                        Log.d("TEST", "onClick: " + expenses.toString());
                                        addExpendtitureManually(expenses, i);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
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
                Intent intent = new Intent(AddExpenditureActivity.this, ViewExpenditureActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_paidBy.setAdapter(adapter);
    }

    private void populateSplitListView() {
        listViewSplit = findViewById(R.id.EditTextListView);
        data = mDatabaseHelper.getData(); //cursor
        List<OneString> oneStringList = new ArrayList<>();
        while(data.moveToNext()) {
            oneString = new OneString(data.getString(1), (float) 0);
            oneStringList.add(oneString);
        }
        OneHorizontalTextViewAdapter oneHorizontalTextViewAdapter = new OneHorizontalTextViewAdapter(this, R.layout.split_manually_edittext, oneStringList);
        listViewSplit.setAdapter(oneHorizontalTextViewAdapter);



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

    public void addExpendtitureManually(Float expenditure, int id) {
        mDatabaseHelper.addExpenditureManually(expenditure, id);
    }

    public void addContribution(Float price, String name) {
        mDatabaseHelper.addContribution(price, name);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
