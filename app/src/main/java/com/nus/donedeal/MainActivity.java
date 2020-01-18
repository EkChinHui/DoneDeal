package com.nus.donedeal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button btn_enter, btn_addexpenditure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        btn_enter = findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStatus() == 1) {
                    Intent intent = new Intent(getApplicationContext(), AddExpenditureActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_addexpenditure = findViewById(R.id.btnaddexpenditure);
        btn_addexpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddExpenditureActivity.class);
                startActivity(intent);
            }
        });
    }

    private Integer getStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        Integer status = sharedPreferences.getInt("Status", 0);
        return status;
    }
}
