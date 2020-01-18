package com.nus.donedeal;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends Activity {
    ListView listViewShow;
    DatabaseHelper mDatabaseHelper;
    Cursor c;
    TwoStrings twoStrings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlayout);
        listViewShow = findViewById(R.id.listViewShow);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }
    private void populateListView() {
        List<TwoStrings> twoStringsList = new ArrayList<>();
        c = mDatabaseHelper.getData();
        while (c.moveToNext()) {
            twoStrings = new TwoStrings(c.getString(1), c.getFloat(2));
            twoStringsList.add(twoStrings);
        }
        TwoHorizontalTextViewsAdapter twoHorizontalTextViewsAdapter = new TwoHorizontalTextViewsAdapter(this, R.layout.two_columns_listview, twoStringsList);
        listViewShow.setAdapter(twoHorizontalTextViewsAdapter);
    }
}
