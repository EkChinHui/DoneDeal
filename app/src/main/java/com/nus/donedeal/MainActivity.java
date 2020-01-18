package com.nus.donedeal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class MainActivity extends Activity {
    Button btn_enter, btn_addexpenditure;
    TextView tripName;
    Button btn_reset;

    @Override
    protected void onResume() {
        super.onResume();
        if (getStatus() == 0) {
            btn_reset.setVisibility(View.GONE);
        }
    }

    Button btn_enter, btn_addexpenditure, btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        btn_enter = findViewById(R.id.btn_enter);
        tripName = findViewById(R.id.textView_home_tripName);

        if(!getTripName().equals("")) { // set trip name if it is keyed in
            tripName.setText(getTripName());
        }

        btn_reset = findViewById(R.id.btnreset);
        if (getStatus() == 0) {
            btn_reset.setVisibility(View.GONE);
        }

        btn_enter = findViewById(R.id.btn_addexpenditure);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStatus() == 1) {
                    Intent intent = new Intent(getApplicationContext(), AddExpenditureActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

//        btn_addexpenditure = findViewById(R.id.btn_addexpenditure);
//        btn_addexpenditure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public  void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AddExpenditureActivity.class);
//                startActivity(intent);
//            }
//        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus();
                Toast.makeText(MainActivity.this, "Trip Completed", Toast.LENGTH_SHORT).show();
            }
        });

        btn_show = findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                startActivity(intent);
            }
        });
    }

    private Integer getStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        Integer status = sharedPreferences.getInt("Status", 0);
        return status;
    }

    private String getTripName() {
        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        String tripName = sharedPreferences.getString("TripName", "");
        return tripName;
    }

    private void setStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Status", 0);
        editor.apply();
    }
}
