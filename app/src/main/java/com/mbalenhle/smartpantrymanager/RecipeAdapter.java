package com.mbalenhle.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Recipe r = recipes.get(position);
        h.name.setText(r.getName());
        h.steps.setText(r.getInstructions());
    }

    @Override public int getItemCount() { return recipes.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, steps;
        VH(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.recipeName);
            steps = v.findViewById(R.id.recipeSteps);
        }
    }
}