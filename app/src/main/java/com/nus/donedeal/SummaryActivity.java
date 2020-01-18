package com.nus.donedeal;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SummaryActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    String[] names_arr;
    Float[] expenditures_arr, contributions_arr, net;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summarylayout);
        ArrayList<String> names = mDatabaseHelper.getAllNames();
        ArrayList<Float> expenditures = mDatabaseHelper.getAllExpenditure();
        ArrayList<Float> contributions = mDatabaseHelper.getAllContribution();
        int size = names.size();
        names_arr = new String[size];
        expenditures_arr = new Float[size];
        contributions_arr = new Float[size];
        for (int i = 0; i < size; i++) {
            names_arr[i] = names.get(i);
            expenditures_arr[i] = expenditures.get(i);
            contributions_arr[i] = contributions.get(i);
        }
        net = getNet(expenditures_arr, contributions_arr);
        int[] debtorsIndex = getDebtorsIndex(net);
        Float[] debtorsValue = getValues(debtorsIndex);
        int[] creditorsIndex = getCreditorsIndex(net);
        Float[] creditorsValue = getValues(creditorsIndex);
        sort(debtorsValue, debtorsIndex);
        sort(creditorsValue, creditorsIndex);
        Log.d("debtorsvalue", Arrays.toString(debtorsValue));
        Log.d("creditorsvalue", Arrays.toString(creditorsValue));
        ArrayList<String> log = settle(debtorsValue, debtorsIndex, creditorsValue, creditorsIndex);
        for (int i = 0; i < log.size(); i++) {
            Log.d("fuck", log.get(i));
        }
    }
    //Owes money if >0 (debtors)
    public Float[] getNet(Float[] expenditures_arr, Float[] contributions_arr) {
        int size = expenditures_arr.length;
        Float[] result = new Float[size];
        for (int i = 0; i < size; i++) {
            result[i] = expenditures_arr[i] - contributions_arr[i];
        }
        return result;
    }

    public int[] getDebtorsIndex(Float[] arr) {
        int len = arr.length;
        int counter = 0;
        int size = getPositive(arr);
        int[] result = new int[size];
        for (int i = 0; i < len; i++) {
            if (arr[i] > 0) {
                result[counter] = i;
                counter++;
            }
        }
        return result;
    }

    public int[] getCreditorsIndex(Float[] arr) {
        int len = arr.length;
        int counter = 0;
        int size = getNonPositive(arr);
        int[] result = new int[size];
        for (int i = 0; i < len; i++) {
            if (arr[i] <= 0) {
                result[counter] = i;
                counter++;
            }
        }
        return result;
    }

    public int getPositive(Float[] arr) {
        int counter = 0;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            if (arr[i] > 0) {
                counter++;
            }
        }
        return counter;
    }

    public int getNonPositive(Float[] arr) {
        int counter = 0;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            if (arr[i] <= 0) {
                counter++;
            }
        }
        return counter;
    }

    public Float[] getValues(int[] arr) {
        int len = arr.length;
        Float[] res = new Float[len];
        for (int i = 0; i < len; i++) {
            res[i] = this.net[arr[i]];
        }
        return res;
    }

    public void sort(Float[] value, int[] index) {
        int len = value.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (value[j] > value[i]) {
                    float temp = value[j];
                    value[j] = value[i];
                    value[i] = temp;
                    int temp1 = index[j];
                    index[j] = index[i];
                    index[i] = temp1;
                }
            }
        }
    }
    public ArrayList<String> settle(Float[] debtorsValue, int[] debtorsIndex, Float[] creditorsValue, int[] creditorsIndex) {
        ArrayList<String> result = new ArrayList<>();
        int lenDebt = debtorsValue.length;
        int lenCred = creditorsValue.length;
        for (int i = 0; i < lenDebt; i++) {
            if (debtorsValue[i] != 0) {
                for (int j = lenCred - 1; j >= 0; j--) {
                    if (creditorsValue[j] != 0) {
                        if (-creditorsValue[j] > debtorsValue[i]) {
                            Log.d("Value", debtorsValue[i].toString());
                            String log = names_arr[debtorsIndex[i]] + " pays " + names_arr[creditorsIndex[j]] + " " + debtorsValue[i];
                            creditorsValue[j] += debtorsValue[i];
                            debtorsValue[i] -= debtorsValue[i];
                            result.add(log);
                            break;
                        }
                        else if (-creditorsValue[j] < debtorsValue[i]) {
                            String log = names_arr[debtorsIndex[i]] + " pays " + names_arr[creditorsIndex[j]] + " " + (-creditorsValue[j]);
                            debtorsValue[i] += creditorsValue[j];
                            creditorsValue[j] -= creditorsValue[j];
                            result.add(log);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
}
