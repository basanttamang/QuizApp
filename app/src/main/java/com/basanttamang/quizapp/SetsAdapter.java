package com.basanttamang.quizapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class SetsAdapter extends BaseAdapter{
    private Context context;
    private int numOfSets = 0;

    public SetsAdapter(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    @Override
    public int getCount() {
        return numOfSets;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view;
        if (convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_items,parent,false);
        }else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(),QuestionsActivity.class);
                intent.putExtra("SETNO",position+1);
                parent.getContext().startActivity(intent);
            }
        });
        ((TextView) view.findViewById(R.id.setNo)).setText(String.valueOf(position+1));
        return view;
    }
}
