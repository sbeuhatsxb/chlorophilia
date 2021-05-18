package com.chlorophilia.ui.fragmentMyPlants;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;
import com.chlorophilia.ui.model.PlantDataHandler;

/**
 * This fragment calls the plant "carousel" listview
 */
public class MyPlantsFragment extends Fragment {

    MyPlantsListAdapter adapter;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_myplants_list, container, false);

        ListView listView = (ListView) root.findViewById(R.id.list);

        PlantDataHandler db = new PlantDataHandler(getActivity());
        adapter = new MyPlantsListAdapter(db.getPlants(), getActivity().getApplicationContext());

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant plant = db.getPlants().get(position);

                Intent intent = new Intent(getActivity(), MyPlantsShowDetails.class);
                intent.putExtra("plant", plant);
                startActivity(intent);
            }
        });

        return root;
    }
}
