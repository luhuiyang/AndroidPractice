package com.example.personalaccount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personalaccount.R;
import com.example.personalaccount.entity.Accounts;

import java.util.List;

/**
 * Created by Administrator on 2015/3/12.
 */
public class InfoAdapter extends ArrayAdapter<Accounts> {

    private int resourceId;
    public InfoAdapter(Context context, int resource, List<Accounts> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Accounts accounts = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.accountSort = (TextView) view.findViewById(R.id.account_sort);
            viewHolder.accountMoney = (TextView) view.findViewById(R.id.account_money);
            viewHolder.accountTime = (TextView) view.findViewById(R.id.account_time);
            viewHolder.accountLocation = (TextView) view.findViewById(R.id.account_location);
            viewHolder.accountId = (TextView) view.findViewById(R.id.account_id);
            viewHolder.accountMark = (TextView) view.findViewById(R.id.account_mark);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.accountSort.setText(accounts.getType());
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        viewHolder.accountMoney.setText(String.valueOf(df.format(accounts.getMoney())));
        viewHolder.accountTime.setText(accounts.getTime());
        viewHolder.accountLocation.setText(accounts.getLocation());
        viewHolder.accountId.setText(accounts.getId()+"");
        viewHolder.accountMark.setText(accounts.getMark());

    return view;

    }

    class ViewHolder{
        LinearLayout itemInfo;
        TextView accountSort, accountMoney, accountTime, accountLocation, accountId, accountMark;
    }
}
