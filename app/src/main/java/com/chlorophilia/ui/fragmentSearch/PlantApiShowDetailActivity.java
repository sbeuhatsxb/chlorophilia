package com.chlorophilia.ui.fragmentSearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chlorophilia.R;
import com.chlorophilia.ui.apiProvider.ApiInstance;
import com.chlorophilia.ui.entities.JsonPlantFromApiList;
import com.chlorophilia.ui.entities.Plant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * View dedicated to add a plant from the PlantApiShowList
 */
public class PlantApiShowDetailActivity extends AppCompatActivity {

    private Button addPlant;
    private Button backToList;
    private Button seeDetails;
    private JsonPlantFromApiList jsonPlantFromApiListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plant_detail_from_api);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        jsonPlantFromApiListDetail = intent.getExtras().getParcelable("ApiPlantDetail");


        // Capture the layout's TextView and set the string as its text
        final ImageView plantPictureExample = (ImageView) findViewById(R.id.plantPictureExample);
        final String imgURL = jsonPlantFromApiListDetail.getImage_url();

        TextView commonName = findViewById(R.id.list_plant_common_name_desc);
        TextView family = findViewById(R.id.list_plant_family_desc);
        TextView familyCommonName = findViewById(R.id.list_plant_family_common_name_desc);
        TextView genus = findViewById(R.id.list_plant_genus_desc);
        TextView scientificName = findViewById(R.id.list_plant_scientific_name_desc);

        commonName.setText(jsonPlantFromApiListDetail.getCommon_name());
        family.setText(jsonPlantFromApiListDetail.getFamily());
        familyCommonName.setText(jsonPlantFromApiListDetail.getFamily_common_name());
        genus.setText(jsonPlantFromApiListDetail.getGenus());
        scientificName.setText(jsonPlantFromApiListDetail.getScientific_name());
        new DownLoadImageTask(plantPictureExample).execute(imgURL);

        backToList = (Button) findViewById(R.id.backToList);

        backToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<JsonPlantFromApiList> jsonPlantFromApiLists;
                Intent intent = getIntent();
                jsonPlantFromApiLists = intent.getParcelableArrayListExtra("ApiPlantList");
                intent = new Intent(getApplicationContext(), PlantApiShowList.class);
                intent.putParcelableArrayListExtra("plants", (ArrayList<? extends Parcelable>) jsonPlantFromApiLists);
                startActivity(intent);
            }
        });


        addPlant = (Button) findViewById(R.id.addPlantButton);

        addPlant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Get plant slug  from input
                String slug = jsonPlantFromApiListDetail.getSlug();
                Plant plant = tryApiJsonToPlantObject(slug);
                Intent intent = new Intent(getApplicationContext(), NicknameActivity.class);
                intent.putExtra("plant", plant);
                startActivity(intent);
                finish();
            }
        });

        seeDetails = findViewById(R.id.seeDetails);

        seeDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Get plant slug  from input
                String slug = jsonPlantFromApiListDetail.getSlug();
                Plant plant = tryApiJsonToPlantObject(slug);
                Intent intent = new Intent(getApplicationContext(), SeePlantDetailsActivity.class);
                intent.putExtra("plant", plant);
                intent.putExtra("imgURL", imgURL);
                startActivity(intent);
            }
        });
    }

    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    /**
     * Get a toString JSON array. Converting it String[] array.
     *
     * @param str
     * @return
     */
    private String[] stringConverter(String str) {
        //remove curly brackets
        str = str.substring(1, str.length() - 1);
        //split the string to create key-value pairs
        String[] keyValuePairs = str.split(":");
        //remove brackets
        keyValuePairs[0] = keyValuePairs[0].substring(1, keyValuePairs[0].length() - 1);
        return keyValuePairs;
    }

    /**
     * Get some toString JSON arrays. Converting them to only return celsius values.
     *
     * @param str
     * @return
     */
    private String[] stringTemperatureConverter(String str) {
        str = str.substring(1, str.length() - 1);
        String[] keyValuePairs = str.split(",");
        String[] valuesReturned = new String[0];
        for (String subPair : keyValuePairs) {
            String[] entry = subPair.split(":");
            //remove brackets
            entry[0] = entry[0].substring(1, entry[0].length() - 1);
            if (entry[0].equals("deg_c")) {
                entry[0] = "celsius";
                valuesReturned = entry;
            }
        }
        return valuesReturned;
    }

    private Plant tryApiJsonToPlantObject(String slug) {
        //new API call
        ApiInstance getApi = new ApiInstance();
        if (!slug.equals("")) {
            try {
                //Setting token
                getApi.setToken(getResources().getString(R.string.token));
                //Getting answer from the API
                String responseAsString = getApi.getPlantSpeciesFromSlug(slug);
                //Building a gson manager
                final Gson gson = new GsonBuilder()
                        .serializeNulls()
                        .disableHtmlEscaping()
                        .create();

                //Converting response to a JSON Object
                JSONObject responseToJsonObject = new JSONObject(responseAsString);
                try {

                    //Setting new plant
                    Plant bookPlant = new Plant();

                    bookPlant.setCommon_name(jsonPlantFromApiListDetail.getCommon_name());
                    bookPlant.setScientific_name(jsonPlantFromApiListDetail.getScientific_name());
                    bookPlant.setFamily(jsonPlantFromApiListDetail.getFamily());

                    //Passing through ["data"]["growth"] objects and adding them to an Hashtable
                    String[] items = {"sowing", "days_to_harvest", "ph_maximum", "ph_minimum", "light", "atmospheric_humidity", "growth_months", "bloom_months",
                            "fruit_months", "soil_nutriments", "soil_salinity", "soil_humidity"
                    };

                    for (int i = 0; i < items.length; i++) {
                        String item = items[i];
                        Object getItem = responseToJsonObject.getJSONObject("data").getJSONObject("growth").get(item);
                        if (!getItem.toString().equals("null")) {
                            switch (item) {
                                case "sowing":
                                    bookPlant.setSowing(getItem.toString());
                                    break;
                                case "days_to_harvest":
                                    bookPlant.setDays_to_harvest(getItem.toString());
                                    break;
                                case "ph_maximum":
                                    bookPlant.setPhMaximum(getItem.toString());
                                    break;
                                case "ph_minimum":
                                    bookPlant.setPhMinimum(getItem.toString());
                                    break;
                                case "light":
                                    bookPlant.setLight(getItem.toString());
                                    break;
                                case "atmospheric_humidity":
                                    bookPlant.setAtmosphericHumidity(getItem.toString());
                                    break;
                                case "growth_months":
                                    bookPlant.setGrowthMonths(getItem.toString());
                                    break;
                                case "bloom_months":
                                    bookPlant.setBloomMonths(getItem.toString());
                                    break;
                                case "fruit_months":
                                    bookPlant.setFruitMonths(getItem.toString());
                                    break;
                                case "soil_nutriments":
                                    bookPlant.setSoilNutriments(getItem.toString());
                                    break;
                                case "soil_salinity":
                                    bookPlant.setSoilSalinity(getItem.toString());
                                    break;
                                case "soil_humidity":
                                    bookPlant.setSoilHumidity(getItem.toString());
                                    break;
                            }

                        }
                    }

                    String[] itemsArray = {
                            "spread", "row_spacing", "minimum_precipitation", "maximum_precipitation",
                            "minimum_temperature", "maximum_temperature", "minimum_root_depth"
                    };

                    for (int i = 0; i < itemsArray.length; i++) {
                        String item = itemsArray[i];
                        Object getItem = responseToJsonObject.getJSONObject("data").getJSONObject("growth").getJSONObject(item);
                        String[] results;
                        if (!getItem.toString().equals("null")) {
                            switch (item) {
                                case "spread":
                                    results = stringConverter(getItem.toString());
                                    if (!results[0].equals(null)) {
                                        bookPlant.setSpreadMeasure(results[0]);
                                    }
                                    if (!results[1].equals("null")) {
                                        bookPlant.setSpread(results[1]);
                                    }
                                    break;
                                case "row_spacing":
                                    results = stringConverter(getItem.toString());
                                    if (!results[0].equals(null)) {
                                        bookPlant.setRowSpacingMeasure(results[0]);
                                    }
                                    if (!results[1].equals("null")) {
                                        bookPlant.setRowSpacing(results[1]);
                                    }
                                    break;
                                case "minimum_precipitation":
                                    results = stringConverter(getItem.toString());
                                    if (!results[0].equals(null)) {
                                        bookPlant.setPrecipitationMeasure(results[0]);
                                    }
                                    if (!results[1].equals("null")) {
                                        bookPlant.setMinimumPrecipitation(results[1]);
                                    }
                                    break;
                                case "minimum_root_depth":
                                    results = stringConverter(getItem.toString());
                                    if (!results[0].equals(null)) {
                                        bookPlant.setRootDepthMeasure(results[0]);
                                    }
                                    if (!results[1].equals("null")) {
                                        bookPlant.setMinimumRootDepth(results[1]);
                                    }
                                    break;
                                case "maximum_precipitation":
                                    results = stringConverter(getItem.toString());
                                    if (!results[0].equals(null)) {
                                        bookPlant.setPrecipitationMeasure(results[0]);
                                    }
                                    if (!results[1].equals("null")) {
                                        bookPlant.setMaximumPrecipitation(results[1]);
                                    }
                                    break;
                                case "minimum_temperature":
                                    results = stringTemperatureConverter(getItem.toString());
                                    if (!results[0].equals(null)) {
                                        bookPlant.setTemperatureMeasure(results[0]);
                                    }
                                    if (!results[1].equals("null")) {
                                        bookPlant.setMinimumTemperature(results[1]);
                                    }
                                    break;
                                case "maximum_temperature":
                                    results = stringTemperatureConverter(getItem.toString());
                                    if (!results[0].equals(null)) {
                                        bookPlant.setTemperatureMeasure(results[0]);
                                    }
                                    if (!results[1].equals("null")) {
                                        bookPlant.setMaximumTemperature(results[1]);
                                    }
                                    break;
                            }
                        }
                    }

                    return bookPlant;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Context context = getApplicationContext();
            CharSequence text = "Something went wrong - please try again later or another plant";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        return null;
    }

}

