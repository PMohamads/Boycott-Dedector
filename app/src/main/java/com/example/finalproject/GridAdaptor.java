package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridAdaptor extends BaseAdapter {
    private ArrayList<Object> dataList;
    private Context context;
    LayoutInflater layoutInflater;

    @Override
    public int getCount() {
        return dataList.size();
    }

    public GridAdaptor(ArrayList<Object> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_item,null);
        }
        ImageView gridImage = convertView.findViewById(R.id.gridimage);
        TextView gridtext = convertView.findViewById(R.id.gridtext);

        Glide.with(context).load(dataList.get(position));
        return null;
    }
}
