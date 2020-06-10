package com.example.myquizapp.Categories;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myquizapp.R;
import com.example.myquizapp.Sets.SetsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> modelList;

    public CategoryAdapter(List<CategoryModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setData(modelList.get(position).getUrl(), modelList.get(position).getName(),modelList.get(position).getSets());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageview;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
        }

        // setData method called in onBindViewHolder()
        private void setData(String url, final String title,final int sets) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .into(imageview);

            this.title.setText(title);

            // OnClickListener for Differet Categories
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // goto Sets Activity
                    Intent intent = new Intent(itemView.getContext(), SetsActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("sets", sets);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
