package com.nus.donedeal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ViewExpenditureActivity extends Activity {
    ListView listViewExpense;
    DatabaseHelper1 databaseHelper1;
    String description, paidBy;
    Float amount;
    Cursor data;
    ThreeStrings threeStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_expenditure_layout);
        listViewExpense = findViewById(R.id.listViewExpenses);
        databaseHelper1 = new DatabaseHelper1(this);

        populateListView();
    }
    private void populateListView() {
        data = databaseHelper1.getData(); //cursor
        List<ThreeStrings> threeStringsList = new ArrayList<>();
        while(data.moveToNext()) {
            threeStrings = new ThreeStrings(data.getString(1), data.getString(3), data.getFloat(2));
            threeStringsList.add(threeStrings);
        }
        ThreeHorizontalTextViewsAdapter threeHorizontalTextViewsAdapter = new ThreeHorizontalTextViewsAdapter(this, R.layout.three_columns_listview, threeStringsList);
        listViewExpense.setAdapter(threeHorizontalTextViewsAdapter);
        listViewExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ThreeStrings checker = (ThreeStrings) adapterView.getItemAtPosition(position);
                String name = checker.getLeft();
                Cursor data = databaseHelper1.getItemID(name);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                    description = databaseHelper1.getExpenseRow(itemID)[0];
                    amount = Float.parseFloat(databaseHelper1.getExpenseRow(itemID)[1]);
                    paidBy = databaseHelper1.getExpenseRow(itemID)[2];
                }
                if (itemID > -1) {
                    Intent intent = new Intent(ViewExpenditureActivity.this, DeleteExpenditureActivity.class);
                    finish();
                    intent.putExtra("id", itemID);
                    intent.putExtra("Description", description);
                    intent.putExtra("Amount", amount);
                    intent.putExtra("Paid By", paidBy);
                    startActivity(intent);
                }
                else {
                    toastMessage("Expense doesn't exist");
                }
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
