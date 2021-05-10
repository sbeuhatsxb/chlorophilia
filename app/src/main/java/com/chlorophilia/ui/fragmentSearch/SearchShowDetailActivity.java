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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * View dedicated to add a plant from the PlantApiShowList
 */
public class SearchShowDetailActivity extends AppCompatActivity {

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
        TextView common_names = findViewById(R.id.list_plant_common_names);

        commonName.setText(jsonPlantFromApiListDetail.getCommon_name());
        family.setText(jsonPlantFromApiListDetail.getFamily());
        familyCommonName.setText(jsonPlantFromApiListDetail.getFamily_common_name());
        genus.setText(jsonPlantFromApiListDetail.getGenus());
        scientificName.setText(jsonPlantFromApiListDetail.getScientific_name());
        common_names.setText(jsonPlantFromApiListDetail.getCommon_names());
        new DownLoadImageTask(plantPictureExample).execute(imgURL);

        backToList = (Button) findViewById(R.id.backToList);

        backToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<JsonPlantFromApiList> jsonPlantFromApiLists;
                Intent intent = getIntent();
                jsonPlantFromApiLists = intent.getParcelableArrayListExtra("ApiPlantList");
                intent = new Intent(getApplicationContext(), SearchApiShowList.class);
                intent.putParcelableArrayListExtra("plants", (ArrayList<? extends Parcelable>) jsonPlantFromApiLists);
                startActivity(intent);
            }
        });


        addPlant = (Button) findViewById(R.id.addPlantButton);

        addPlant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Get plant id  from input
                Integer id = jsonPlantFromApiListDetail.getId();
                Plant plant = tryApiJsonToPlantObject(id);
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

                //Get plant id  from input
                Integer id = jsonPlantFromApiListDetail.getId();
                Plant plant = tryApiJsonToPlantObject(id);
                Intent intent = new Intent(getApplicationContext(), SeePlantDetailsActivity.class);
                intent.putExtra("plant", plant);
                intent.putExtra("imgURL", imgURL);
                startActivity(intent);
            }
        });
    }

    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        /**
         *
         * @param imageView
         */
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

    private Plant tryApiJsonToPlantObject(Integer id) {
        //new API call
        ApiInstance getApi = new ApiInstance();
        if (!id.equals("")) {
            try {
                //Setting token
                getApi.setToken(getResources().getString(R.string.token));
                //Getting answer from the API
                String responseAsString = getApi.getPlantFromId(id);
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

                    //OLD API (for memories !)
                    // String[] items = {"sowing", "days_to_harvest", "ph_maximum", "ph_minimum", "light", "atmospheric_humidity", "growth_months", "bloom_months",
                    //"fruit_months", "soil_nutriments", "soil_salinity", "soil_humidity"};
                    //API V2
                    String[] items = {
                            "plantingSowingDescription", "phMaximum", "phMinimum", "light", "atmosphericHumidity", "growthMonths", "bloomMonths",
                            "fruitMonths", "soilNutriments", "soilSalinity","plantingSpreadCm", "plantingRowSpacingCm", "minimumRootDepthCm",
                            "growthForm", "growthHabit", "growthRate", "ediblePart", "vegetable", "edible", "anaerobicTolerance", "averageHeightCm",
                            "maximumHeightCm", "urlPowo", "urlPlantnet", "urlGbif", "urlWikipediaEn", "groundHumidity", "flowerColor", "flowerConspicuous",
                            "foliageColor", "foliageTexture", "fruitColor", "fruitConspicuous"
                    };

                    //Uppers fields are expected in the RestAPI, there for we're parsing each field and each JsonArray corresponding set the proper value
                    for (int i = 0; i < items.length; i++) {
                        String item = items[i];
                        Object getItem = responseToJsonObject.get(item);
                        if (!getItem.toString().equals("null")) {
                            switch (item) {
                                case "plantingSowingDescription":
                                    bookPlant.setSowing(getItem.toString());
                                    break;
                                case "phMaximum":
                                    bookPlant.setPhMaximum(getItem.toString());
                                    break;
                                case "phMinimum":
                                    bookPlant.setPhMinimum(getItem.toString());
                                    break;
                                case "light":
                                    bookPlant.setLight(getItem.toString());
                                    break;
                                case "atmosphericHumidity":
                                    bookPlant.setAtmosphericHumidity(getItem.toString());
                                    break;
                                case "soilNutriments":
                                    bookPlant.setSoilNutriments(getItem.toString());
                                    break;
                                case "soilSalinity":
                                    bookPlant.setSoilSalinity(getItem.toString());
                                    break;
                                case "plantingSpreadCm":
                                    bookPlant.setSpread(getItem.toString());
                                    break;
                                case "plantingRowSpacingCm":
                                    bookPlant.setRowSpacing(getItem.toString());
                                    break;
                                case "minimumRootDepthCm":
                                    bookPlant.setMinimumRootDepth(getItem.toString());
                                    break;
                                case "growthForm":
                                    bookPlant.setGrowthForm(getItem.toString());
                                    break;
                                case "growthHabit":
                                    bookPlant.setGrowthHabit(getItem.toString());
                                    break;
                                case "growthRate":
                                    bookPlant.setGrowthRate(getItem.toString());
                                    break;
                                case "ediblePart":
                                    bookPlant.setEdiblePart(getItem.toString());
                                    break;
                                case "vegetable":
                                    bookPlant.setVegetable(getItem.toString());
                                    break;
                                case "edible":
                                    bookPlant.setEdible(getItem.toString());
                                    break;
                                case "anaerobicTolerance":
                                    bookPlant.setAnaerobicTolerance(getItem.toString());
                                    break;
                                case "averageHeightCm":
                                    bookPlant.setAverageHeightCm(getItem.toString());
                                    break;
                                case "maximumHeightCm":
                                    bookPlant.setMaximumHeightCm(getItem.toString());
                                    break;
                                case "urlPowo":
                                    bookPlant.setUrlPowo(getItem.toString());
                                    break;
                                case "urlPlantnet":
                                    bookPlant.setUrlPlantnet(getItem.toString());
                                    break;
                                case "urlGbif":
                                    bookPlant.setUrlGbif(getItem.toString());
                                    break;
                                case "urlWikipediaEn":
                                    bookPlant.setUrlWikipediaEn(getItem.toString());
                                    break;
                                case "groundHumidity":
                                    bookPlant.setSoilHumidity(getItem.toString());
                                    break;
                                case "flowerColor":
                                    bookPlant.setFlowerColor(getItem.toString());
                                    break;
                                case "flowerConspicuous":
                                    bookPlant.setFlowerConspicuous(getItem.toString());
                                    break;
                                case "foliageColor":
                                    bookPlant.setFoliageColor(getItem.toString());
                                    break;
                                case "foliageTexture":
                                    bookPlant.setFoliageTexture(getItem.toString());
                                    break;
                                case "fruitColor":
                                    bookPlant.setFruitColor(getItem.toString());
                                    break;
                                case "fruitConspicuous":
                                    bookPlant.setFruitConspicuous(getItem.toString());
                                    break;

                            }
                        }
                    }

                    //Monthes : other logic since those are arrays that need to be recorded as string into the DB
                    //TODO : Technical Debt since ApiV2
                    //TODO : Conversation done according to old def. Nonsense actually.
                    //TODO : Need to convert views as well (the big part)
                    String[] itemsArray = {
                            "growthMonths", "bloomMonths", "fruitMonths"
                    };
                    Hashtable monthStr = new Hashtable();
                    monthStr.put(1, "jan");
                    monthStr.put(2, "feb");
                    monthStr.put(3, "mar");
                    monthStr.put(4, "apr");
                    monthStr.put(5, "may");
                    monthStr.put(6, "jun");
                    monthStr.put(7, "jul");
                    monthStr.put(8, "aug");
                    monthStr.put(9, "sep");
                    monthStr.put(10, "oct");
                    monthStr.put(11, "nov");
                    monthStr.put(12, "dec");
                    for (int i = 0; i < itemsArray.length; i++) {
                        String item = itemsArray[i];
                        Object getItem = responseToJsonObject.get(item);
                        ArrayList<String> stringArray = new ArrayList<String>();
                        if (!getItem.toString().equals("null")) {
                            switch (item) {
                                case "growthMonths":
                                    for (int j = 0; j < ((JSONArray) getItem).length(); j++) {
                                        Object month = ((JSONArray) getItem).get(j);
                                        int monthNumber = ((JSONObject) month).getInt("id");
                                        stringArray.add("\"" + monthStr.get(monthNumber).toString() + "\"");
                                    }
                                    bookPlant.setGrowthMonths(stringArray.toString());
                                    break;
                                case "bloomMonths":
                                    for (int j = 0; j < ((JSONArray) getItem).length(); j++) {
                                        Object month = ((JSONArray) getItem).get(j);
                                        int monthNumber = ((JSONObject) month).getInt("id");
                                        stringArray.add("\"" + monthStr.get(monthNumber).toString() + "\"");
                                    }
                                    bookPlant.setBloomMonths(stringArray.toString());
                                    break;
                                case "fruitMonths":
                                    for (int j = 0; j < ((JSONArray) getItem).length(); j++) {
                                        Object month = ((JSONArray) getItem).get(j);
                                        int monthNumber = ((JSONObject) month).getInt("id");
                                        stringArray.add("\"" + monthStr.get(monthNumber).toString() + "\"");
                                    }
                                    bookPlant.setFruitMonths(stringArray.toString());
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

