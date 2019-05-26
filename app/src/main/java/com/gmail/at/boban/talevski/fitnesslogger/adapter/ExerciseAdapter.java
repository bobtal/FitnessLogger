package com.gmail.at.boban.talevski.fitnesslogger.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.at.boban.talevski.fitnesslogger.model.ExerciseEntry;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<ExerciseEntry> exerciseList;
    private Context context;

    public ExerciseAdapter(List<ExerciseEntry> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutIdForListItem = android.R.layout.simple_list_item_1;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        ExerciseViewHolder holder = new ExerciseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(int i) {
            ExerciseEntry exerciseEntry = exerciseList.get(i);
            textView.setText(exerciseEntry.getName());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
