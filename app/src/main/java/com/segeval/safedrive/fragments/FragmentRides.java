package com.segeval.safedrive.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.segeval.safedrive.R;
import com.segeval.safedrive.model.Model;
import com.segeval.safedrive.utils.RidesAdapter;


public class FragmentRides extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rides, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recordings History");
        RidesAdapter ridesAdapter = new RidesAdapter(Model.getInstance().getAllDriverRides(), getActivity());
        ListView listView = (ListView) view.findViewById(R.id.rides_lv);
        listView.setAdapter(ridesAdapter);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!menu.hasVisibleItems()) {
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.rides_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
        return true;
    }
}
