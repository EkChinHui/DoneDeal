package com.nus.donedeal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * this code is unused, originally intended to implement split
 * manually function
 */

public class OneHorizontalTextViewAdapter extends ArrayAdapter<OneString> {

    private int layoutResource;

    public OneHorizontalTextViewAdapter(Context context, int layoutResource, List<OneString> OneStringList) {
        super(context, layoutResource, OneStringList);
        this.layoutResource = layoutResource;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View view = convertView;
//
//        if (view == null) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//            view = layoutInflater.inflate(layoutResource, null);
//        }
//
//        OneString oneString = getItem(position);
//
//        if (oneString != null) {
//            TextView name = view.findViewById(R.id.textView_splitManualName);
//            if (name != null) {
//                name.setText(oneString.getName());
//            }
//        }
//        return view;
//    }
@Override
public View getView(final int position, View convertView
        , ViewGroup parent) {
    View row;
    final ListViewHolder listViewHolder;
    if(convertView == null)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        row = layoutInflater.inflate(layoutResource, null);
        listViewHolder = new ListViewHolder();
        listViewHolder.LVHname = row.findViewById(R.id.textView_splitManualName);
        listViewHolder.LVHamount_manual = row.findViewById(R.id.editText_splitManually);
        row.setTag(listViewHolder);
    }
    else
    {
        row=convertView;
        listViewHolder= (ListViewHolder) row.getTag();
    }
    final OneString oneString = getItem(position);

//    listViewHolder.LVHamount_manual.setText(oneString.getPrice().toString());
    TextView name = row.findViewById(R.id.textView_splitManualName);
    if (name != null) {
        name.setText(oneString.getName());
    }
    EditText test = row.findViewById(R.id.editText_splitManually);
    String test1 = test.getText().toString();
    String new_price = listViewHolder.LVHamount_manual.getText().toString();
    Log.d("TESTTTT", "getView: " + new_price + "  " + test1);
    listViewHolder.LVHamount_manual.setText(new_price);
    try {
        updateAmount(position, Float.parseFloat(new_price));
    } catch (Exception e) {
        e.printStackTrace();
    }


//    listViewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v)
//        {
//            updateQuantity(position,listViewHolder.edTextQuantity,1);
//        }
//    });
//    listViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            updateQuantity(position,listViewHolder.edTextQuantity,-1);
//
//        }
//    });
    return row;
}
    private void updateAmount(int position, float value) {
        OneString oneString = getItem(position);
        if(value > 0)
        {
            oneString.setPrice(value);
        }
//        edTextQuantity.setText(products.CartQuantity+"");
    }
}