package com.example.myquizapp.Sets;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myquizapp.Questions.QuestionActivity;
import com.example.myquizapp.R;

public class GridAdapter extends BaseAdapter {

    private int sets = 0;
    private String category;

    // Constructor
    public GridAdapter(int sets, String category) {
        this.sets = sets;
        this.category = category;
    }

    // return total no of sets
    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertview, final ViewGroup parent) {

        View view;
        if (convertview == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item, parent, false);
        } else {
            view = convertview;
        }

        // OnclickListener for Different Sets
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), QuestionActivity.class);
                intent.putExtra("category",category);
                intent.putExtra("setNo",position+1);
                parent.getContext().startActivity(intent);
            }
        });

        // Set sets number from 1
        ((TextView) view.findViewById(R.id.settextview)).setText(String.valueOf(position + 1));

        return view;
    }
}
