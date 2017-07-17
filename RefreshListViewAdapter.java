package com.example.qiaowenhao.freshlistapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qiaowenhao on 17-7-17.
 */

class RefreshListViewAdapter extends BaseAdapter {
    List<DataEntity>  mList;
    LayoutInflater mLayoutInflater;

    public RefreshListViewAdapter(Context context, List<DataEntity> list) {
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataEntity entity = mList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_layout, null);
            holder.nameTv = (TextView) convertView.findViewById(R.id.item3_apkname);
            holder.desTv = (TextView) convertView.findViewById(R.id.item3_apkdes);
            holder.infoTv = (TextView) convertView.findViewById(R.id.item3_apkinfo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameTv.setText(entity.getName());
        holder.desTv.setText(entity.getDes());
        holder.infoTv.setText(entity.getInfo());
        return convertView;
    }

    public void onDataChange(List<DataEntity> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
