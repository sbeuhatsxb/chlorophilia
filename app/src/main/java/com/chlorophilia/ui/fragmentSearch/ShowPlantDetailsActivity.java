package com.chlorophilia.ui.fragmentSearch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import static java.lang.Math.abs;

/**
 * A short Plant detailed preview
 */
public class ShowPlantDetailsActivity extends AppCompatActivity {

    Plant plant;
    ExtendedFloatingActionButton fab;
    final String powo = "http://powo.science.kew.org/taxon/urn:lsid:ipni.org:names:";
    final String plantNet = "https://identify.plantnet.org/species/the-plant-list/";
    final String gbif = "https://www.gbif.org/species/";
    final String wikipedia = "https://en.wikipedia.org/wiki/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] ANAEROBICTOLERANCE = getResources().getStringArray(R.array.anaerobic_tolerance);
        final String[] TEXTUREFOLIAGE = getResources().getStringArray(R.array.texture_foliage);
        setContentView(R.layout.activity_search_show_detail);

        plant = (Plant) getIntent().getSerializableExtra("plant");
        String imgURL = getIntent().getExtras().getString("imgURL");
        final ImageView plantPictureExample = (ImageView) findViewById(R.id.myPlantPicture);
        new DownLoadImageTask(plantPictureExample).execute(imgURL);

        TextView plant_detail_common_name = findViewById(R.id.myPlantCommonName);
        TextView plant_detail_scientific_name = findViewById(R.id.myPlantScientificName);
        TextView plant_detail_family = findViewById(R.id.myPlantFamily);
        TextView plant_detail_light = findViewById(R.id.myPlantLight);
        TextView plant_detail_ph_min = findViewById(R.id.myPlantPhMin);
        TextView plant_detail_ph_max = findViewById(R.id.myPlantPhMax);
        TextView plant_detail_atmosphericHumidity = findViewById(R.id.myPlantAtmospheric);
        //API V2
        TextView plant_detail_flowerColor = findViewById(R.id.myPlantFlowerColor);
        TextView plant_detail_flowerConspicuous = findViewById(R.id.myPlantFlowerConspicuous);
        TextView plant_detail_foliageTexture = findViewById(R.id.myPlantFoliageTexture);
        TextView plant_detail_foliageColor = findViewById(R.id.myPlantFoliageColor);
        TextView plant_detail_fruitColor = findViewById(R.id.myPlantFruitColor);
        TextView plant_detail_fruitConspicuous = findViewById(R.id.myPlantFruitConspicuous);
        TextView plant_detail_urlPowo = findViewById(R.id.myPlanturlPowo);
        TextView plant_detail_urlPlantnet = findViewById(R.id.myPlanturlPlantNet);
        TextView plant_detail_urlGbif = findViewById(R.id.myPlanturlGbif);
        TextView plant_detail_urlWikipediaEn = findViewById(R.id.myPlanturlWikipedia);

        if (plant.getCommon_name() != null) {
            plant_detail_common_name.setText(plant.getCommon_name());
        } else {
            plant_detail_common_name.setText("");
        }

        if (plant.getScientific_name() != null) {
            plant_detail_scientific_name.setText(plant.getScientific_name());
        } else {
            plant_detail_scientific_name.setText("");
        }

        if (plant.getFamily() != null) {
            plant_detail_family.setText(plant.getFamily());
        } else {
            plant_detail_family.setText("");
        }

        if (plant.getLight() != null) {
            int sun = Integer.parseInt("2600", 16); // it will be 128013
            int cloud = Integer.parseInt("2601", 16); // it will be 128013
            int lightQuantity = Integer.parseInt(plant.getLight());
            StringBuilder sunnyString = new StringBuilder();
            if (lightQuantity >= 0) {
                for (int i = 0; i < lightQuantity; i++) {
                    sunnyString.append(new String(new int[]{sun}, 0, 1));
                }

                for (int i = 0; i < abs(lightQuantity - 10); i++) {
                    sunnyString.append(new String(new int[]{cloud}, 0, 1));
                }
            }

            plant_detail_light.setText(sunnyString.toString());
        } else {
            plant_detail_light.setText("");
        }

        if (plant.getPhMinimum() != null) {
            plant_detail_ph_min.setText("min. " + plant.getPhMinimum());
        } else {
            plant_detail_ph_min.setText("");
        }

        if (plant.getPhMaximum() != null) {
            plant_detail_ph_max.setText("max. " + plant.getPhMaximum());
        } else {
            plant_detail_ph_max.setText("");
        }

        if (plant.getAtmosphericHumidity() != null) {
            if(plant.getAtmosphericHumidity().equals("0")){
                plant_detail_atmosphericHumidity.setText(getResources().getString(R.string.up_to_ten_percent));
            } else {
                double atmosphericHumidityDouble = Double.parseDouble(plant.getAtmosphericHumidity()) * 10;
                int atmosphericHumidity = (int) Math.round(atmosphericHumidityDouble);
                plant_detail_atmosphericHumidity.setText(atmosphericHumidity + " %");
            }
        } else {
            plant_detail_atmosphericHumidity.setText("");
        }

        if (plant.getFlowerColor() != null) {
            String[] colorIntArray = plant.getFlowerColor().split(",");
            String translatedColors = getTranslatedColors(colorIntArray);
            plant_detail_flowerColor.setText(translatedColors);
        } else {
            plant_detail_flowerColor.setText("");
        }

        if (plant.getFoliageTexture() != null) {
            plant_detail_foliageTexture.setText(TEXTUREFOLIAGE[Integer.parseInt(plant.getFoliageTexture())]);
        } else {
            plant_detail_foliageTexture.setText("");
        }

        if (plant.getFoliageColor() != null) {
            String[] colorIntArray = plant.getFoliageColor().split(",");
            String translatedColors = getTranslatedColors(colorIntArray);
            plant_detail_foliageColor.setText(translatedColors);
        } else {
            plant_detail_foliageColor.setText("");
        }

        if (plant.getFruitColor() != null) {
            String[] colorIntArray = plant.getFruitColor().split(",");
            String translatedColors = getTranslatedColors(colorIntArray);
            plant_detail_fruitColor.setText(translatedColors);
        } else {
            plant_detail_fruitColor.setText("");
        }

        if (plant.getUrlPowo() != null) {
            String url = powo + plant.getUrlPowo();
            String linkedText = String.format("<a href=\"%s\">Powo</a> ", url);
            plant_detail_urlPowo.setText(HtmlCompat.fromHtml(linkedText, HtmlCompat.FROM_HTML_MODE_LEGACY));
            plant_detail_urlPowo.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            plant_detail_urlPowo.setText("");
        }

        if (plant.getUrlPlantnet() != null) {
            String url = plantNet + plant.getUrlPlantnet();
            String linkedText = String.format("<a href=\"%s\">PlantNet</a> ", url);
            plant_detail_urlPlantnet.setText(HtmlCompat.fromHtml(linkedText, HtmlCompat.FROM_HTML_MODE_LEGACY));
            plant_detail_urlPlantnet.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            plant_detail_urlPlantnet.setText("");
        }

        if (plant.getUrlGbif() != null) {
            String url = gbif + plant.getUrlGbif();
            String linkedText = String.format("<a href=\"%s\">Gbif</a> ", url);
            plant_detail_urlGbif.setText(HtmlCompat.fromHtml(linkedText, HtmlCompat.FROM_HTML_MODE_LEGACY));
            plant_detail_urlGbif.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            plant_detail_urlGbif.setText("");
        }

        if (plant.getUrlWikipediaEn() != null) {
            String url = wikipedia + plant.getUrlWikipediaEn();
            String linkedText = String.format("<a href=\"%s\">Wikipedia</a> ", url);
            plant_detail_urlWikipediaEn.setText(HtmlCompat.fromHtml(linkedText, HtmlCompat.FROM_HTML_MODE_LEGACY));
            plant_detail_urlWikipediaEn.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            plant_detail_urlWikipediaEn.setText("");
        }

        fab = (ExtendedFloatingActionButton) findViewById(R.id.fab);
        fab.setText(getResources().getString(R.string.addFab));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get plant slug  from input
                Intent intent = new Intent(getApplicationContext(), NicknameActivity.class);
                intent.putExtra("plant", plant);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getTranslatedColors(String[] colorIntArray) {
        ArrayList colorTranslated = new ArrayList();
        for(int i = 0; i < colorIntArray.length; i++){
            String colorString = "";
            switch(colorIntArray[i]) {
                case "1":
                    colorString = getResources().getString(R.string.green);
                    break;
                case "2":
                    colorString = getResources().getString(R.string.red);
                    break;
                case "3":
                    colorString = getResources().getString(R.string.yellow);
                    break;
                case "4":
                    colorString = getResources().getString(R.string.grey);
                    break;
                case "5":
                    colorString = getResources().getString(R.string.brown);
                    break;
                case "6":
                    colorString = getResources().getString(R.string.green_brown);
                    break;
                case "7":
                    colorString = getResources().getString(R.string.orange);
                    break;
                case "8":
                    colorString = getResources().getString(R.string.blue);
                    break;
                case "9":
                    colorString = getResources().getString(R.string.purple);
                    break;
                case "10":
                    colorString = getResources().getString(R.string.white);
                    break;
                case "11":
                    colorString = getResources().getString(R.string.black);
                    break;
            }
            colorTranslated.add(colorString);
        }

        StringBuilder sb = new StringBuilder();
        for (Object s : colorTranslated)
        {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }



    private static class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
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
}
