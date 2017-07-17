package com.example.qiaowenhao.freshlistapp;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RefreshListener{
    List<DataEntity> mList = new ArrayList<>();
    RefreshListViewAdapter mRefreshListViewAdapter;
    RefreshListView mRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        showRefreshList(mList);
    }

    private void showRefreshList(List list) {
        if (mRefreshListViewAdapter == null) {
            mRefreshListViewAdapter = new RefreshListViewAdapter(this, list);
            mRefreshListView = (RefreshListView) findViewById(R.id.refreshListView);
            mRefreshListView.setAdapter(mRefreshListViewAdapter);
            mRefreshListView.setOnRefreshListener(this);
        } else {
            //attention
            mRefreshListViewAdapter.onDataChange(list);
        }
    }

    private void setData() {
        for (int i=0; i <10; i++) {
            DataEntity entity = new DataEntity();
            entity.setName("hello world " + i);
            entity.setDes("Android Developer");
            entity.setInfo("BUPT");
            mList.add(entity);
        }
    }

    private void setRefreshData() {
        for (int i = 0; i < 2; i++) {
            DataEntity entity = new DataEntity();
            entity.setName("hahha world " + i);
            entity.setDes("Miui Developer");
            entity.setInfo("jlu");
            mList.add(0, entity);
        }
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshData();
                showRefreshList(mList);
                mRefreshListView.refreshComplete();
            }
        }, 2000);
    }
}
