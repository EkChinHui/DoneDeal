package com.nus.donedeal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpenditureActivity extends Activity {
    Button btn_enterexpenditure, btn_viewexpenditure;
    EditText editText_description, editText_amount, editText_paidby;
    DatabaseHelper1 mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpenditurelayout);
        btn_enterexpenditure = findViewById(R.id.btn_enterexpenditure);
        btn_viewexpenditure = findViewById(R.id.btn_viewexpenditure);
        editText_description = findViewById(R.id.editText_description);
        editText_amount = findViewById(R.id.editText_amount);
        editText_paidby = findViewById(R.id.editText_paidby);
        mDatabaseHelper = new DatabaseHelper1(this);

        btn_enterexpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editText_description.getText().toString();
                Float price = Float.parseFloat(editText_amount.getText().toString());
                String paidBy = editText_paidby.getText().toString();
                addData(description, price, paidBy);
            }
        });
    }
    public void addData(String description, Float price, String paidBy) {
        boolean insertData = mDatabaseHelper.addData(description, price, paidBy);
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
