package com.nus.donedeal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewExpenditureActivity extends Activity {
    ListView listViewExpense;
    DatabaseHelper1 databaseHelper1;
    String description, paidBy;
    Float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_expenditure_layout);
        databaseHelper1 = new DatabaseHelper1(this);

        populateListView();
    }
    private void populateListView() {
        Cursor data = databaseHelper1.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listViewExpense.setAdapter(adapter);
        listViewExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = adapterView.getItemAtPosition(position).toString();
                Cursor data = databaseHelper1.getItemID(name);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                    description = data.getString(1);
                    amount = data.getFloat(2);
                    paidBy = data.getString(3);
                }
                if (itemID > -1) {
                    Intent intent = new Intent(ViewExpenditureActivity.this, DeleteExpenditureActivity.class);
                    finish();
                    intent.putExtra("id", itemID);
                    intent.putExtra("description", description);
                    intent.putExtra("Amount", amount);
                    intent.putExtra("Paid By", paidBy);
                    startActivity(intent);
                }
                else {
                    toastMessage("No such name");
                }
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
