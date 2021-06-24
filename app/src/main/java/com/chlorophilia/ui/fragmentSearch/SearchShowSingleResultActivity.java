package com.chlorophilia.ui.fragmentSearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

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
import java.util.Collections;

/**
 * View dedicated to add a plant from the PlantApiShowList
 */
public class SearchShowSingleResultActivity extends AppCompatActivity {

    private JsonPlantFromApiList jsonPlantFromApiListDetail;
    int translationIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_single_result);

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
        if(!imgURL.equals("")){
            new DownLoadImageTask(plantPictureExample).execute(imgURL);
        } else {
            ColorFilter filter = new LightingColorFilter( Color.LTGRAY, Color.LTGRAY);
            Drawable notFound = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_organic_search_symbol_of_magnification_tool_with_leaves_svgrepo_com);
            notFound.setColorFilter(filter);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) plantPictureExample.getLayoutParams();
            params.height = (int) Math.abs(params.height *0.7);
            params.width = (int) Math.abs(params.height *0.7);
            plantPictureExample.setLayoutParams(params);
            plantPictureExample.setImageDrawable(notFound);
    }
        Button addPlant = (Button) findViewById(R.id.addPlantButton);
        Button backToList = (Button) findViewById(R.id.backToList);
        Button seeDetails = findViewById(R.id.seeDetails);

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

        seeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get plant id  from input
                Integer id = jsonPlantFromApiListDetail.getId();
                Plant plant = tryApiJsonToPlantObject(id);
                Intent intent = new Intent(getApplicationContext(), ShowPlantDetailsActivity.class);
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
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
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

                    //API V2
                    String[] items = {
                            "plantingSowingDescription", "phMaximum", "phMinimum", "light", "atmosphericHumidity", "growthMonths", "bloomMonths",
                            "fruitMonths", "soilNutriments", "soilSalinity","plantingSpreadCm", "plantingRowSpacingCm", "minimumRootDepthCm",
                            "growthForm", "growthHabit", "growthRate", "ediblePart", "vegetable", "edible", "anaerobicTolerance", "averageHeightCm",
                            "maximumHeightCm", "urlPowo", "urlPlantnet", "urlGbif", "urlWikipediaEn", "groundHumidity", "flowerColor", "flowerConspicuous",
                            "foliageColor", "foliageTexture", "fruitColor", "fruitConspicuous"
                    };

                    //Uppers fields are expected in the RestAPI, therefore we're parsing each field and each JsonArray corresponding set the proper value
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

                    //TEXTURE_FOLIAGE
                    translationIndex = 0;
                    if(bookPlant.getFoliageTexture() != null){
                        switch(bookPlant.getFoliageTexture()) {
                            case "fine":
                                translationIndex = 1;
                                break;
                            case "medium":
                                translationIndex = 2;
                                break;
                            case "coarse":
                                translationIndex = 3;
                                break;
                        }
                    }
                    bookPlant.setFoliageTexture(null);
                    if(translationIndex != 0){
                        bookPlant.setFoliageTexture(String.valueOf(translationIndex));
                    }
                    //ANAEROBIC_TOLERANCE
                    translationIndex = 0;
                    if(bookPlant.getAnaerobicTolerance() != null){
                        switch(bookPlant.getAnaerobicTolerance()) {
                            case "Low":
                                translationIndex = 1;
                                break;
                            case "Medium":
                                translationIndex = 2;
                                break;
                            case "High":
                                translationIndex = 3;
                                break;
                        }
                    }
                    bookPlant.setAnaerobicTolerance(null);
                    if(translationIndex != 0){
                        bookPlant.setAnaerobicTolerance(String.valueOf(translationIndex));
                    }
                    //GROWTH_FORM
                    translationIndex = 0;
                    if(bookPlant.getGrowthForm() != null){
                        switch(bookPlant.getGrowthForm()) {
                            case "Single Stem":
                                translationIndex = 1;
                                break;
                            case "Multiple Stem":
                                translationIndex = 2;
                                break;
                            case "Single Crown":
                                translationIndex = 3;
                                break;
                            case "Rhizomatous":
                                translationIndex = 4;
                                break;
                            case "Bunch":
                                translationIndex = 5;
                                break;
                            case "Stoloniferous":
                                translationIndex = 6;
                                break;
                            case "Thicket":
                                translationIndex = 7;
                                break;
                            case "Colonizing":
                                translationIndex = 8;
                                break;
                            case "Erect":
                                translationIndex = 9;
                                break;
                        }
                        bookPlant.setGrowthForm(null);
                        if(translationIndex != 0){
                            bookPlant.setGrowthForm(String.valueOf(translationIndex));
                        }
                    }
                    //GROWTH_HABIT
                    translationIndex = 0;
                    if (bookPlant.getGrowthHabit() != null) {
                        String[] growthHabitArray = bookPlant.getGrowthHabit().split(", ", -1);
                        ArrayList growthHabitTranslated = new ArrayList();

                        for(int i = 0; i < growthHabitArray.length; i++){
                            switch(growthHabitArray[i]) {
                                case "Tree":
                                    translationIndex = 1;
                                    break;
                                case "Nonvascular":
                                    translationIndex = 2;
                                    break;
                                case "Forb/herb":
                                    translationIndex = 3;
                                    break;
                                case "Vine":
                                    translationIndex = 4;
                                    break;
                                case "Subshrub":
                                    translationIndex = 5;
                                    break;
                                case "Shrub":
                                    translationIndex = 6;
                                    break;
                                case "Graminoid":
                                    translationIndex = 7;
                                    break;
                                case "Lichenous":
                                    translationIndex = 8;
                                    break;
                            }
                            growthHabitTranslated.add(translationIndex);
                        }

                        StringBuilder sb = new StringBuilder();
                        for (Object s : growthHabitTranslated)
                        {
                            sb.append(s);
                            sb.append(",");
                        }
                        bookPlant.setGrowthHabit(sb.toString().substring(0, sb.toString().length() - 1));
                    }
                    //GROWTH_RATE
                    translationIndex = 0;
                    if(bookPlant.getGrowthRate() != null){
                        switch(bookPlant.getGrowthRate()) {
                            case "Slow":
                                translationIndex = 1;
                                break;
                            case "Moderate":
                                translationIndex = 2;
                                break;
                            case "Fast":
                                translationIndex = 3;
                                break;
                        }
                    }
                    bookPlant.setGrowthRate(null);
                    if(translationIndex != 0){
                        bookPlant.setGrowthRate(String.valueOf(translationIndex));
                    }
                    //COLORS
                    if(bookPlant.getFoliageColor() != null){
                        String translatedColor = colorConvertor(bookPlant.getFoliageColor());
                        bookPlant.setFoliageColor(translatedColor);
                    }
                    if(bookPlant.getFruitColor() != null) {
                        String translatedColor = colorConvertor(bookPlant.getFruitColor());
                        bookPlant.setFruitColor(translatedColor);
                    }
                    if(bookPlant.getFlowerColor() != null) {
                        String translatedColor = colorConvertor(bookPlant.getFlowerColor());
                        bookPlant.setFlowerColor(translatedColor);
                    }

                    //MONTHS
                    String[] itemsArray = {
                            "growthMonths", "bloomMonths", "fruitMonths"
                    };

                    for (int i = 0; i < itemsArray.length; i++) {
                        String item = itemsArray[i];
                        Object getItem = responseToJsonObject.get(item);
                        ArrayList<Integer> stringArray = new ArrayList<Integer>();
                        if (!getItem.toString().equals("null")) {
                            switch (item) {
                                case "growthMonths":
                                    for (int j = 0; j < ((JSONArray) getItem).length(); j++) {
                                        Object month = ((JSONArray) getItem).get(j);
                                        int monthNumber = ((JSONObject) month).getInt("id");
                                        stringArray.add(monthNumber);
                                    }
                                    Collections.sort(stringArray);
                                    bookPlant.setGrowthMonths(stringArray.toString());
                                    break;
                                case "bloomMonths":
                                    for (int j = 0; j < ((JSONArray) getItem).length(); j++) {
                                        Object month = ((JSONArray) getItem).get(j);
                                        int monthNumber = ((JSONObject) month).getInt("id");
                                        stringArray.add(monthNumber);
                                    }
                                    Collections.sort(stringArray);
                                    bookPlant.setBloomMonths(stringArray.toString());
                                    break;
                                case "fruitMonths":
                                    for (int j = 0; j < ((JSONArray) getItem).length(); j++) {
                                        Object month = ((JSONArray) getItem).get(j);
                                        int monthNumber = ((JSONObject) month).getInt("id");
                                        stringArray.add(monthNumber);
                                    }
                                    Collections.sort(stringArray);
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
            CharSequence text = getResources().getString(R.string.unknownError);
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        return null;
    }
    private String colorConvertor(String color){
        int colorCode = 0;
        String[] colorArray = color.split(",");
        ArrayList colorIntArray = new ArrayList();

        for(int i = 0; i < colorArray.length; i++){
            switch(colorArray[i]) {
                case "green":
                    colorCode = 1;
                    break;
                case "red":
                    colorCode = 2;
                    break;
                case "yellow":
                    colorCode = 3;
                    break;
                case "grey":
                    colorCode = 4;
                    break;
                case "brown":
                    colorCode = 5;
                    break;
                case "brown-green":
                    colorCode = 6;
                    break;
                case "orange":
                    colorCode = 7;
                    break;
                case "blue":
                    colorCode = 8;
                    break;
                case "purple":
                    colorCode = 9;
                    break;
                case "white":
                    colorCode = 10;
                    break;
                case "black":
                    colorCode = 11;
                    break;
            }
            colorIntArray.add(colorCode);
        }
        StringBuilder sb = new StringBuilder();
        for (Object s : colorIntArray)
        {
            sb.append(s);
            sb.append(",");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}

