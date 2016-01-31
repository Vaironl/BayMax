package com.vairon.baymax;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by vaironl on 1/31/16.
 */
public class CustomBillAdapter extends BaseAdapter {

    private Bill[] bills;
    private static LayoutInflater inflater;
    private Context context;

    public CustomBillAdapter(MainActivity mainActivity, Bill[] _bills) {
        bills = _bills;
        context = mainActivity;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bills.length;
    }

    @Override
    public Object getItem(int position) {
        return bills[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {

        TextView titleTV, descriptionTV;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.bills_row, null);
        holder.titleTV = (TextView) rowView.findViewById(R.id.billTitleTV);
        holder.descriptionTV = (TextView) rowView.findViewById(R.id.descriptionTV);

        holder.titleTV.setText(bills[position].getTitle());
        holder.descriptionTV.setText(bills[position].getDescription());

        return rowView;
    }
}
