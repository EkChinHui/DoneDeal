package com.nus.donedeal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class HomeActivity extends Activity {
    DatabaseHelper mDatabaseHelper;
    Button btn_add, btn_view, btn_confirm;
    EditText t_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
        btn_add = findViewById(R.id.btn_add);
        t_name = findViewById(R.id.t_name);
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
                finish();
                Intent intent = new Intent(HomeActivity.this, AddExpenditureActivity.class);
                startActivity(intent);
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
}
