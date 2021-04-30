package com.chlorophilia.ui.fragmentSearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;
import com.chlorophilia.ui.model.PlantDataHandler;

import java.io.InputStream;
import java.net.URL;

import static java.lang.Math.abs;

public class SeePlantDetailsActivity extends AppCompatActivity {

    Plant plant;
    Button addPlant;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] SOILRICHNESS = {"extremely poor", "very poor", "poor", "fairly poor", "lower middle", "middle", "higher middle", "fairly rich", "rich", "very rich", "extremely rich"};

        setContentView(R.layout.activity_search_show_detail);
        PlantDataHandler db = new PlantDataHandler(getApplicationContext());

        plant = (Plant) getIntent().getSerializableExtra("plant");
        String imgURL = getIntent().getExtras().getString("imgURL");
        final ImageView plantPictureExample = (ImageView) findViewById(R.id.myPlantPicture);
        new SeePlantDetailsActivity.DownLoadImageTask(plantPictureExample).execute(imgURL);

        TextView plant_detail_common_name = findViewById(R.id.myPlantCommonName);
        TextView plant_detail_scientific_name = findViewById(R.id.myPlantScientificName);
        TextView plant_detail_family = findViewById(R.id.myPlantFamily);
        TextView plant_detail_Precipitation_min = findViewById(R.id.myPlantPrecipitationMin);
        TextView plant_detail_Precipitation_max = findViewById(R.id.myPlantPrecipitationMax);
        TextView plant_detail_light = findViewById(R.id.myPlantLight);
        TextView plant_detail_ph_min = findViewById(R.id.myPlantPhMin);
        TextView plant_detail_ph_max = findViewById(R.id.myPlantPhMax);
        TextView plant_detail_atmosphericHumidity = findViewById(R.id.myPlantAtmospheric);
        TextView plant_detail_TemperatureMin = findViewById(R.id.myPlantTemperatureMin);
        TextView plant_detail_TemperatureMax = findViewById(R.id.myPlantTemperatureMax);
        TextView plant_detail_soilNutriments = findViewById(R.id.myPlantSoilNutriments);
        TextView plant_detail_soilSalinity = findViewById(R.id.myPlantSoilSalinity);
        TextView plant_detail_soilHumidity = findViewById(R.id.myPlantSoilHumidity);
        TextView plant_detail_spread = findViewById(R.id.myPlantSpread);
        TextView plant_detail_minimumRootDepth = findViewById(R.id.myPlantMinRootDepth);
        TextView plant_detail_sowing = findViewById(R.id.myPlantSowing);
        TextView plant_detail_rowSpacing = findViewById(R.id.myPlantRowSpacing);
        TextView plant_detail_days_to_harvest = findViewById(R.id.myPlantDaysToHarvest);
        TextView plant_detail_growthMonths = findViewById(R.id.myPlantGrowsMonths);
        TextView plant_detail_bloomMonths = findViewById(R.id.myPlantBloomMonths);
        TextView plant_detail_fruitMonths = findViewById(R.id.myPlantFruitMonths);
        TextView plant_detail_bibliography = findViewById(R.id.myPlantBibliography);

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

        if (plant.getMinimumPrecipitation() != null) {
            plant_detail_Precipitation_min.setText("min. " + plant.getMinimumPrecipitation() + plant.getPrecipitationMeasure());
        } else {
            plant_detail_Precipitation_min.setText("");
        }

        if (plant.getMaximumPrecipitation() != null) {
            plant_detail_Precipitation_max.setText("max. " + plant.getMaximumPrecipitation() + plant.getPrecipitationMeasure());
        } else {
            plant_detail_Precipitation_max.setText("");
        }

        if (plant.getLight() != null) {
            int sun = Integer.parseInt("2600", 16); // it will be 128013
            int cloud = Integer.parseInt("2601", 16); // it will be 128013
            int lightQuantity = Integer.parseInt(plant.getLight());
            String sunnyString = "";
            if (lightQuantity >= 0) {
                for (int i = 0; i < lightQuantity; i++) {
                    sunnyString += new String(new int[]{sun}, 0, 1);
                }

                for (int i = 0; i < abs(lightQuantity - 10); i++) {
                    sunnyString += new String(new int[]{cloud}, 0, 1);
                }

            }

            plant_detail_light.setText(sunnyString);
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
            double atmosphericHumidityDouble = Double.parseDouble(plant.getAtmosphericHumidity()) * 10;
            int atmosphericHumidity = (int) Math.round(atmosphericHumidityDouble);
            plant_detail_atmosphericHumidity.setText(atmosphericHumidity + " %");
        } else {
            plant_detail_atmosphericHumidity.setText("");
        }

        if (plant.getMinimumTemperature() != null) {
            double fahrenheit = Integer.parseInt(plant.getMinimumTemperature()) * 1.8 + 32;
            plant_detail_TemperatureMin.setText("min. " + plant.getMinimumTemperature() + "°C / " + fahrenheit + "°F");
        } else {
            plant_detail_TemperatureMin.setText("");
        }

        if (plant.getMaximumTemperature() != null) {
            double fahrenheit = Integer.parseInt(plant.getMaximumTemperature()) * 1.8 + 32;
            plant_detail_TemperatureMax.setText("max. " + plant.getMaximumTemperature() + "°C / " + fahrenheit + "°F");
        } else {
            plant_detail_TemperatureMax.setText("");
        }

        if (plant.getSoilNutriments() != null) {
            plant_detail_soilNutriments.setText(SOILRICHNESS[Integer.parseInt(plant.getSoilNutriments())]);
        } else {
            plant_detail_soilNutriments.setText("");
        }

        if (plant.getSoilSalinity() != null) {
            plant_detail_soilSalinity.setText(SOILRICHNESS[Integer.parseInt(plant.getSoilSalinity())]);
        } else {
            plant_detail_soilSalinity.setText("");
        }

        if (plant.getSoilHumidity() != null) {
            double soilHumidityDouble = Double.parseDouble(plant.getAtmosphericHumidity()) * 10;
            int soilHumidity = (int) Math.round(soilHumidityDouble);
            plant_detail_soilHumidity.setText(soilHumidity + " %");
        } else {
            plant_detail_soilHumidity.setText("");
        }

        if (plant.getSpread() != null) {
            plant_detail_spread.setText(plant.getSpread() + " " + plant.getSpreadMeasure());
        } else {
            plant_detail_spread.setText("");
        }

        if (plant.getMinimumRootDepth() != null) {
            plant_detail_minimumRootDepth.setText(plant.getMinimumRootDepth() + " " + plant.getRootDepthMeasure());
        } else {
            plant_detail_minimumRootDepth.setText("");
        }

        if (plant.getSowing() != null) {
            plant_detail_sowing.setText(plant.getSowing());
        } else {
            plant_detail_sowing.setText("");
        }

        if (plant.getRowSpacing() != null) {
            plant_detail_rowSpacing.setText(plant.getRowSpacing() + " " + plant.getRowSpacingMeasure());
        } else {
            plant_detail_rowSpacing.setText("");
        }

        if (plant.getDays_to_harvest() != null) {
            plant_detail_days_to_harvest.setText(plant.getDays_to_harvest() + " days");
        } else {
            plant_detail_days_to_harvest.setText("");
        }

        if (plant.getGrowthMonths() != null) {
            plant_detail_growthMonths.setText(plant.getGrowthMonths());
        } else {
            plant_detail_growthMonths.setText("");
        }

        if (plant.getBloomMonths() != null) {
            plant_detail_bloomMonths.setText(plant.getBloomMonths());
        } else {
            plant_detail_bloomMonths.setText("");
        }

        if (plant.getFruitMonths() != null) {
            plant_detail_fruitMonths.setText(plant.getFruitMonths());
        } else {
            plant_detail_fruitMonths.setText("");
        }

        if (plant.getBibliography() != null) {
            plant_detail_bibliography.setText(plant.getBibliography());
        } else {
            plant_detail_bibliography.setText("");
        }

        addPlant = (Button) findViewById(R.id.detailAddPlantButton);

        addPlant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Get plant slug  from input
                Intent intent = new Intent(getApplicationContext(), NicknameActivity.class);
                intent.putExtra("plant", plant);
                startActivity(intent);
                finish();
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
}