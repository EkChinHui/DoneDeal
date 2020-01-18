package com.nus.donedeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class OneHorizontalTextViewAdapter extends ArrayAdapter<OneString> {

    private int layoutResource;

    public OneHorizontalTextViewAdapter(Context context, int layoutResource, List<OneString> OneStringList) {
        super(context, layoutResource, OneStringList);
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        OneString oneString = getItem(position);
/*
        if (oneString != null) {
            EditText centreTextView = view.findViewById(R.id.split_amount);

            if (centreTextView != null) {
                centreTextView.setText(oneString.getCentre().toString());
            }
        }
        */
        return view;
    }
}