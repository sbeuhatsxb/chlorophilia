package com.chlorophilia.ui.fragmentSearch;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chlorophilia.ui.entities.JsonPlantFromApiList;

import java.util.ArrayList;

/**
 * Displays a list of plants gathered by the API.
 */
public class PlantApiShowList extends ListActivity {

    ArrayList<JsonPlantFromApiList> jsonPlantFromApiLists;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        jsonPlantFromApiLists = intent.getParcelableArrayListExtra("plants");
        ArrayList<String> plantsName = new ArrayList<String>();
        for (int i = 0; i < jsonPlantFromApiLists.size(); i++) {
            if (jsonPlantFromApiLists.get(i).getCommon_name() != null) {
                plantsName.add(
                        i + 1 + ". " + jsonPlantFromApiLists.get(i).getCommon_name() + " - " + jsonPlantFromApiLists.get(i).getScientific_name() + " (" + jsonPlantFromApiLists.get(i).getFamily() + ")");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, plantsName);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, PlantApiShowDetailActivity.class);
        JsonPlantFromApiList selectedJsonPlantFromApiList = jsonPlantFromApiLists.get(position);
        intent.putExtra("ApiPlantDetail", selectedJsonPlantFromApiList);
        //Sending list to next view to eventually come back from next view
        intent.putParcelableArrayListExtra("ApiPlantList", jsonPlantFromApiLists);
        startActivity(intent);
        finish();
    }


}
