package com.nus.donedeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditNamesActivity extends AppCompatActivity {
    Button btn_save, btn_delete;
    EditText t_editName;
    DatabaseHelper mDatabaseHelper;
    Integer selectedID;
    String selectedName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnameslayout);
        btn_save = findViewById(R.id.btn_save);
        btn_delete = findViewById(R.id.btn_delete);
        t_editName = findViewById(R.id.t_editName);
        mDatabaseHelper = new DatabaseHelper(this);

        //get intent from NamesActivity
        Intent receiveIntent = getIntent();
        selectedID = receiveIntent.getIntExtra("id", -1); //Note: -1 is just the default value
        selectedName = receiveIntent.getStringExtra("name");

        t_editName.setText(selectedName);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = t_editName.getText().toString();
                if (!item.equals("")) {
                    mDatabaseHelper.updateName(item, selectedID, selectedName);
                    finish();
                    Intent intent = new Intent(EditNamesActivity.this, NamesActivity.class);
                    startActivity(intent);
                    toastMessage("Changes saved");
                }
                else {
                    toastMessage("You must enter a name");
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteName(selectedID, selectedName);
                toastMessage("Member removed");
                finish();
                Intent intent = new Intent(EditNamesActivity.this, NamesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
