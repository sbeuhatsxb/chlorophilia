package com.chlorophilia.ui.fragmentSearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chlorophilia.R;
import com.chlorophilia.ui.apiProvider.ApiInstance;
import com.chlorophilia.ui.entities.JsonPlantFromApiList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Class dedicated to call the API with a slug
 */
public class SearchFragment extends Fragment {

    private EditText plantName;
    ArrayList<JsonPlantFromApiList> jsonPlantFromApiLists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        final TextView textView = root.findViewById(R.id.title_search);

        //Plant slug input
        plantName = (EditText) root.findViewById(R.id.plantNameInput);
        Button validateButton = (Button) root.findViewById(R.id.dashboardSearchPlantButton);

        validateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //new API call
                ApiInstance getApi = new ApiInstance();
                //Get plant slug  from input
                String param = plantName.getText().toString();

                //Checking if Input is set
                if (!param.equals("")) {
                    try {
                        //Setting token
                        getApi.setToken(getResources().getString(R.string.token));
                        //Getting answer from the API
                        String responseAsString = getApi.getPlantListFromSlug(param);

                        if(responseAsString.equals("")){
                            Toast.makeText(getContext(), getResources().getString(R.string.remote_server_failed), Toast.LENGTH_LONG).show();
                        } else {
                            //Building a gson manager
                            final Gson gson = new GsonBuilder()
                                    .serializeNulls()
                                    .disableHtmlEscaping()
                                    .create();

                            //Converting response to a JSON Object
                            JSONObject responseToJsonObject = new JSONObject(responseAsString);
                            try {
                                //Check if API found something
                                if (responseToJsonObject.getJSONArray("plants").length() > 0) {
                                    //Passing through ["data"] object and turning this json into a java array
                                    JSONArray responseAsJson = responseToJsonObject.getJSONArray("plants");
                                    //Parsing response as an array
                                    jsonPlantFromApiLists = new ArrayList<JsonPlantFromApiList>();
                                    for (int i = 0; i < responseAsJson.length(); i++) {
                                        //Plant Class correlated to the json format
                                        JsonPlantFromApiList jsonPlantFromApiList = new JsonPlantFromApiList();
                                        jsonPlantFromApiList = gson.fromJson(responseAsJson.getJSONObject(i).toString(), (Type) JsonPlantFromApiList.class);
                                        //Adding plant to collection
                                        jsonPlantFromApiLists.add(jsonPlantFromApiList);
                                    }

                                    Intent intent = new Intent(getActivity(), SearchApiShowList.class);
                                    intent.putParcelableArrayListExtra("plants", (ArrayList<? extends Parcelable>) jsonPlantFromApiLists);
                                    startActivity(intent);
                                } else {
                                    CharSequence text = "Sorry, no plant having this name was found";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(getContext(), text, duration);
                                    toast.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Please enter a plant name";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });

        Button translateButton = (Button) root.findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://translate.google.com/?hl=en"));
                startActivity(browserIntent);
            }

        });

        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

}