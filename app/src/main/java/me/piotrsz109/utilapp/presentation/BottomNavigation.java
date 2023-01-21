package me.piotrsz109.utilapp.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.piotrsz109.utilapp.R;

public class BottomNavigation extends Fragment {
    public BottomNavigation() {
        // Required empty public constructor
    }

    public static BottomNavigation newInstance(int defaultItemId) {
        BottomNavigation fragment = new BottomNavigation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        int currentItem = R.id.weatherPage;
        if(getActivity() instanceof NotesActivity)
            currentItem = R.id.notesPage;

        BottomNavigationView navigationView = view.findViewById(R.id.bottomMenu);

        navigationView.setSelectedItemId(currentItem);

        int finalCurrentItem = currentItem;
        navigationView.setOnItemSelectedListener(item -> {
            if (finalCurrentItem == item.getItemId()) return true;

            switch (item.getItemId()) {
                case R.id.weatherPage:
                    startActivity(new Intent(view.getContext(), WeatherActivity.class));
                    break;
                case R.id.notesPage:
                    startActivity(new Intent(view.getContext(), NotesActivity.class));
                    break;
            }
            return true;
        });

        return view;
    }
}