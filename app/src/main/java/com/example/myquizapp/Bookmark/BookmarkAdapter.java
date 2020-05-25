package com.example.myquizapp.Bookmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myquizapp.Questions.QuestionModel;
import com.example.myquizapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private List<QuestionModel> modelList;

    public BookmarkAdapter(List<QuestionModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(modelList.get(position).getQuestion(), modelList.get(position).getCorrectANS(),position);
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView question, answer;
        private ImageButton deletebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            deletebtn = itemView.findViewById(R.id.deletebtn);
        }

        // setData method called in onBindViewHolder()
        private void setData(String question, String answer,final int position) {
            this.question.setText(question);
            this.answer.setText(answer);

            // Item Delete From Bookmark
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Remove from list
                    modelList.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }

    }

}
