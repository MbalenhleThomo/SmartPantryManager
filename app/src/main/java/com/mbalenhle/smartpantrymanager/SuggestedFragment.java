package com.mbalenhle.smartpantrymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuggestedFragment extends Fragment {
    private RecyclerView recycler;
    private TextView emptyState;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_suggested, container, false);
        recycler = v.findViewById(R.id.recyclerSuggested);
        emptyState = v.findViewById(R.id.emptySuggested);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Call the RecipeMatcher!
        List<Recipe> suggested = RecipeMatcher.getSuggestedRecipes(getContext());

        if (suggested.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            recycler.setAdapter(new RecipeAdapter(suggested));
        }
    }
}