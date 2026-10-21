package com.mbalenhle.smartpantrymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout we just created
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        SwitchMaterial switchAlerts = v.findViewById(R.id.switchAlerts);

        // Use SharedPreferences to save the toggle state
        SharedPreferences prefs = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isChecked = prefs.getBoolean("alerts", true);
        switchAlerts.setChecked(isChecked);

        switchAlerts.setOnCheckedChangeListener((buttonView, isCheckedNow) -> {
            prefs.edit().putBoolean("alerts", isCheckedNow).apply();
        });

        return v;
    }
}