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
import androidx.annotation.Nullable;
import java.util.ArrayList;

/**
 * displays a list of members
 * clicking a name will bring up the EditNamesActivity
 */

public class NamesActivity extends Activity {
    ListView listViewNames;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nameslayout);
        listViewNames = findViewById(R.id.listViewNames);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    private void populateListView() { //fills up the listview with names from database
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listViewNames.setAdapter(adapter);
        listViewNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = adapterView.getItemAtPosition(position).toString();
                Cursor data = mDatabaseHelper.getItemID(name);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1) {
                    finish();
                    Intent intent = new Intent(NamesActivity.this, EditNamesActivity.class);
                    intent.putExtra("id", itemID);
                    intent.putExtra("name", name);
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

