package com.example.myquizapp.Categories;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myquizapp.R;
import com.example.myquizapp.subcategories.SubCategoriesActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 2;

    private static final int VIEW_TYPE_REC = 1;
    private static final int VIEW_TYPE_GRID = 2;

    private List<CategoryModel> modelList;
    GridLayoutManager gridLayoutManager;

    // Constructor
    public CategoryAdapter(List<CategoryModel> modelList, GridLayoutManager gridLayoutManager) {
        this.modelList = modelList;
        this.gridLayoutManager = gridLayoutManager;
    }

    //getViewType
    @Override
    public int getItemViewType(int position) {
        int spanCount = gridLayoutManager.getSpanCount();
        if(spanCount == SPAN_COUNT_ONE){
            return VIEW_TYPE_REC;
        }else{
            return VIEW_TYPE_GRID;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_REC){
             view =LayoutInflater.from(parent.getContext()).inflate(R.layout.category_itemsss, parent, false);
        }
        else{
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_grid, parent, false);
        }
        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setData(modelList.get(position).getLogo(),modelList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imageview;
        private TextView maincategotyname;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if(viewType==VIEW_TYPE_REC){
            imageview = itemView.findViewById(R.id.imageView);
            maincategotyname = itemView.findViewById(R.id.maincategorytitle);
            }else{
                imageview = itemView.findViewById(R.id.gridimageView);
                maincategotyname = itemView.findViewById(R.id.gridmaincategorytitle);
            }
        }

        // setData method called in onBindViewHolder()
        private void setData(String url, final String title) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .into(imageview);

            this.maincategotyname.setText(title);

            // OnClickListener for Differet Categories
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // goto Subcategories Activity
                    Intent intent = new Intent(itemView.getContext(), SubCategoriesActivity.class);
                    intent.putExtra("categorytitle", title);
                    intent.putExtra("categoryposition",getAdapterPosition());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
