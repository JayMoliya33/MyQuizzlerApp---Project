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

    // Constructor
    public GridAdapter(int sets) {
        this.sets = sets;
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
    public View getView(int position, View convertview, final ViewGroup parent) {

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
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.settextview)).setText(String.valueOf(position + 1));

        return view;
    }
}
