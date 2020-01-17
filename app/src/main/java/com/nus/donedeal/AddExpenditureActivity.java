package com.nus.donedeal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddExpenditureActivity extends Activity {
    Button btn_enter, btn_viewexpenditure;
    EditText editText_description, editText_amount, editText_paidby;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpenditurelayout);
        btn_enter = findViewById(R.id.btn_enter);
        btn_viewexpenditure = findViewById(R.id.btn_viewexpenditure);
        editText_description = findViewById(R.id.editText_description);
        editText_amount = findViewById(R.id.editText_amount);
        editText_paidby = findViewById(R.id.editText_paidby);
        mDatabaseHelper = new DatabaseHelper(this);

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editText_description.getText().toString();
                Float amount = Float.parseFloat(editText_amount.getText().toString());
                String paidBy = editText_paidby.getText().toString();
                addData(description, amount, paidBy);


            }
        });



    }

}
