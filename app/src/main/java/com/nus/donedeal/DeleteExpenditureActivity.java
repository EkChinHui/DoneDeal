package com.nus.donedeal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * After submitting an expenditure,
 * the expenditure can be deleted if a mistake was made
 */

public class DeleteExpenditureActivity extends Activity {
    TextView textDescription, textAmount, textPaidBy;
    Button btn_delete;
    DatabaseHelper1 databaseHelper1;
    Integer selectedID;
    String selectedDescription, selectedPaidBy;
    Float selectedAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_expenditure_layout);
        textDescription = findViewById(R.id.textView_show_description);
        textAmount = findViewById(R.id.textView_show_amount);
        textPaidBy = findViewById(R.id.textView_show_paidBy);
        btn_delete = findViewById(R.id.btn_delete_expenditure);
        databaseHelper1 = new DatabaseHelper1(this);

        Intent receiveIntent = getIntent();
        selectedID = receiveIntent.getIntExtra("id", -1); //Note: -1 is just the default value
        selectedDescription = receiveIntent.getStringExtra("Description");
        selectedAmount = receiveIntent.getFloatExtra("Amount", 0);
        selectedPaidBy = receiveIntent.getStringExtra("Paid By");

        textDescription.setText(selectedDescription);
        textAmount.setText(selectedAmount.toString());
        textPaidBy.setText(selectedPaidBy);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper1.deleteEntry(selectedID, selectedDescription);
                toastMessage("Expense deleted");
                finish();
                Intent intent = new Intent(DeleteExpenditureActivity.this, ViewExpenditureActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
