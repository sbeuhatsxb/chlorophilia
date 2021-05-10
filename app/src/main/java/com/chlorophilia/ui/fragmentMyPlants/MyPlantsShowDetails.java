package com.chlorophilia.ui.fragmentMyPlants;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.HtmlCompat;

import com.chlorophilia.R;
import com.chlorophilia.ui.dialogs.MyPlantsRemoveDialog;
import com.chlorophilia.ui.dialogs.MyPlantsShowDialog;
import com.chlorophilia.ui.entities.Plant;
import com.chlorophilia.ui.model.PlantDataHandler;
import com.chlorophilia.ui.sensorProvider.SensorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;

import static java.lang.Math.abs;


/**
 * Detailed view of plants properties
 */
public class MyPlantsShowDetails extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String currentPhotoPath;
    Plant plant;
    FloatingActionButton fab;
    final String powo = "http://powo.science.kew.org/taxon/urn:lsid:ipni.org:names:";
    final String plantNet = "https://identify.plantnet.org/species/the-plant-list/";
    final String gbif = "https://www.gbif.org/species/";
    final String wikipedia = "https://en.wikipedia.org/wiki/";
    @SuppressLint("SetTextI18n")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] SOILRICHNESS = {"", "extremely poor", "poor", "fairly poor", "lower middle", "middle", "higher middle", "fairly rich", "rich", "very rich", "extremely rich"};

        setContentView(R.layout.activity_myplants_show_detail);
        PlantDataHandler db = new PlantDataHandler(getApplicationContext());

        plant = (Plant) getIntent().getSerializableExtra("plant");

        Button deleteButton = findViewById(R.id.deletePlantButton);
        Button editButton = findViewById(R.id.editDetails);
        ImageView plant_detail_img = (ImageView) findViewById(R.id.myPlantPicture);
        TextView plant_detail_nickname = findViewById(R.id.myPlantNickname);
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
        TextView plant_textImageView = findViewById(R.id.textImageView);
        //API V2
        TextView plant_detail_anaerobic = findViewById(R.id.myPlantAnaerobic);
        TextView plant_detail_growthForm = findViewById(R.id.myPlantGrowthForm);
        TextView plant_detail_growthHabit = findViewById(R.id.myPlantGrowthHabit);
        TextView plant_detail_growthRate = findViewById(R.id.myPlantGrowthRate);
        TextView plant_detail_averageHeightCm = findViewById(R.id.myPlantAverageHeightCm);
        TextView plant_detail_maximumHeight = findViewById(R.id.myPlantMaxHeightCm);
        TextView plant_detail_ediblePart = findViewById(R.id.myPlantEdiblePart);
        TextView plant_detail_vegetable = findViewById(R.id.myPlantVegetable);
        TextView plant_detail_edible = findViewById(R.id.myPlantEdible);
        TextView plant_detail_flowerColor = findViewById(R.id.myPlantFlowerColor);
        TextView plant_detail_flowerConspicuous = findViewById(R.id.myPlantFlowerConspicuous);
        TextView plant_detail_foliageTexture = findViewById(R.id.myPlantFoliageTexture);
        TextView plant_detail_fruitColor = findViewById(R.id.myPlantFruitColor);
        TextView plant_detail_fruitConspicuous = findViewById(R.id.myPlantFruitConspicuous);
        TextView plant_detail_urlPowo = findViewById(R.id.myPlanturlPowo);
        TextView plant_detail_urlPlantnet = findViewById(R.id.myPlanturlPlantNet);
        TextView plant_detail_urlGbif = findViewById(R.id.myPlanturlGbif);
        TextView plant_detail_urlWikipediaEn = findViewById(R.id.myPlanturlWikipedia);


        if (plant.getFilenameCustomPicture() != null) {

            plant_textImageView.setVisibility(View.INVISIBLE);
            try {
                File f = new File(plant.getFilenameCustomPicture());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                plant_detail_img.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            plant_textImageView.setVisibility(View.VISIBLE);
            if (plant.getImg() != 0) {
                plant_detail_img.setImageResource(plant.getImg());
            }
        }

        if (plant.getNickname() != null) {
            plant_detail_nickname.setText(plant.getNickname());
            deleteButton.setText("Say goodbye to " + plant.getNickname() + " ! :'(");
        } else {
            plant_detail_nickname.setText("");
        }

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
            plant_detail_Precipitation_min.setText("min. " + plant.getMinimumPrecipitation() + "cm");
        } else {
            plant_detail_Precipitation_min.setText("");
        }

        if (plant.getMaximumPrecipitation() != null) {
            plant_detail_Precipitation_max.setText("max. " + plant.getMaximumPrecipitation() + "cm");
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

            if(plant.getSoilHumidity().equals("0")){
                plant_detail_soilHumidity.setText(getResources().getString(R.string.up_to_ten_percent));
            } else {
                double soilHumidityDouble = Double.parseDouble(plant.getAtmosphericHumidity()) * 10;
                int soilHumidity = (int) Math.round(soilHumidityDouble);
                plant_detail_soilHumidity.setText(soilHumidity + " %");
            }
        } else {
            plant_detail_soilHumidity.setText("");
        }

        if (plant.getSpread() != null) {
            plant_detail_spread.setText(plant.getSpread() + " cm");
        } else {
            plant_detail_spread.setText("");
        }

        if (plant.getMinimumRootDepth() != null) {
            plant_detail_minimumRootDepth.setText(plant.getMinimumRootDepth() + " cm");
        } else {
            plant_detail_minimumRootDepth.setText("");
        }

        if (plant.getSowing() != null) {
            plant_detail_sowing.setText(plant.getSowing());
        } else {
            plant_detail_sowing.setText("");
        }

        if (plant.getRowSpacing() != null) {
            plant_detail_rowSpacing.setText(plant.getRowSpacing() + " cm");
        } else {
            plant_detail_rowSpacing.setText("");
        }

        if (plant.getDays_to_harvest() != null) {
            plant_detail_days_to_harvest.setText(plant.getDays_to_harvest() + " days");
        } else {
            plant_detail_days_to_harvest.setText("");
        }

        if (plant.getGrowthMonths() != null) {
            ArrayList<String> arrayMonths = jsonArrayToStringConverter(plant.getGrowthMonths());
            String growth = "";
            for(String month : arrayMonths) {
                growth += month+" ";
            }
            plant_detail_growthMonths.setText(growth);
        } else {
            plant_detail_growthMonths.setText("");
        }
        if (plant.getBloomMonths() != null) {
            ArrayList<String> arrayMonths = jsonArrayToStringConverter(plant.getBloomMonths());
            String growth = "";
            for(String month : arrayMonths) {
                growth += month+" ";
            }
            plant_detail_bloomMonths.setText(growth);
        } else {
            plant_detail_bloomMonths.setText("");
        }

        if (plant.getFruitMonths() != null) {
            ArrayList<String> arrayMonths = jsonArrayToStringConverter(plant.getFruitMonths());
            String growth = "";
            for(String month : arrayMonths) {
                growth += month+" ";
            }
            plant_detail_fruitMonths.setText(growth);
        } else {
            plant_detail_fruitMonths.setText("");
        }

        if (plant.getBibliography() != null) {
            plant_detail_bibliography.setText(plant.getBibliography());
        } else {
            plant_detail_bibliography.setText("");
        }

        if (plant.getAnaerobicTolerance() != null) {
            plant_detail_anaerobic.setText(plant.getAnaerobicTolerance());
        } else {
            plant_detail_anaerobic.setText("");
        }

        if (plant.getGrowthForm() != null) {
            plant_detail_growthForm.setText(plant.getGrowthForm());
        } else {
            plant_detail_growthForm.setText("");
        }

        if (plant.getGrowthHabit() != null) {
            plant_detail_growthHabit.setText(plant.getGrowthHabit());
        } else {
            plant_detail_growthHabit.setText("");
        }

        if (plant.getGrowthRate() != null) {
            plant_detail_growthRate.setText(plant.getGrowthRate());
        } else {
            plant_detail_growthRate.setText("");
        }

        if (plant.getAverageHeightCm() != null) {
            plant_detail_averageHeightCm.setText(plant.getAverageHeightCm());
        } else {
            plant_detail_averageHeightCm.setText("");
        }

        if (plant.getMaximumHeightCm() != null) {
            plant_detail_maximumHeight.setText(plant.getMaximumHeightCm());
        } else {
            plant_detail_maximumHeight.setText("");
        }

        if (plant.getEdiblePart() != null) {
            plant_detail_ediblePart.setText(plant.getEdiblePart());
        } else {
            plant_detail_ediblePart.setText("");
        }

        if (plant.getVegetable() != null) {
            plant_detail_vegetable.setText(plant.getVegetable());
        } else {
            plant_detail_vegetable.setText("");
        }

        if (plant.getEdible() != null) {
            plant_detail_edible.setText(plant.getEdible());
        } else {
            plant_detail_edible.setText("");
        }

        if (plant.getFlowerColor() != null) {
            plant_detail_flowerColor.setText(plant.getFlowerColor());
        } else {
            plant_detail_flowerColor.setText("");
        }

        if (plant.getFlowerConspicuous() != null) {
            plant_detail_flowerConspicuous.setText(plant.getFlowerConspicuous());
        } else {
            plant_detail_flowerConspicuous.setText("");
        }

        if (plant.getFoliageTexture() != null) {
            plant_detail_foliageTexture.setText(plant.getFoliageTexture());
        } else {
            plant_detail_foliageTexture.setText("");
        }

        if (plant.getFruitColor() != null) {
            plant_detail_fruitColor.setText(plant.getFruitColor());
        } else {
            plant_detail_fruitColor.setText("");
        }

        if (plant.getFruitConspicuous() != null) {
            plant_detail_fruitConspicuous.setText(plant.getFruitConspicuous());
        } else {
            plant_detail_fruitConspicuous.setText("");
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

        // text2 has links specified by putting <a> tags in the string
        // resource.  By default these links will appear but not
        // respond to user input.  To make them active, you need to
        // call setMovementMethod() on the TextView object.




        if (plant.getUrlWikipediaEn() != null) {
            String url = wikipedia + plant.getUrlWikipediaEn();
            String linkedText = String.format("<a href=\"%s\">Wikipedia</a> ", url);
            plant_detail_urlWikipediaEn.setText(Html.fromHtml(linkedText));
            plant_detail_urlWikipediaEn.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            plant_detail_urlWikipediaEn.setText("");
        }

        //Floating button
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.baseline_brightness_medium_24);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#FFD700"));

        fab = (FloatingActionButton) findViewById(R.id.fab_sun);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lightIntent = new Intent(getApplicationContext(), SensorActivity.class);
                if (plant.getLight() != null) {
                    lightIntent.putExtra("plantLight", Integer.parseInt(plant.getLight()));
                } else {
                    lightIntent.putExtra("plantLight", -1);
                }
                startActivity(lightIntent);
            }
        });

        //DELETE PLANT
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPlantsRemoveDialog.class);
                intent.putExtra("plant", plant);
                startActivity(intent);
            }
        });

        //EDIT PLANT
        editButton.setText(getResources().getString(R.string.plant_update_plant_show));
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPlantsEditDetailsActivity.class);
                intent.putExtra("plant", plant);
                startActivity(intent);
                finish();
            }
        });

        //TAKE PICTURE
        plant_detail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                dispatchTakePictureIntent();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.chlorophilia.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    //Case deny
                    Intent intent = new Intent(getApplicationContext(), MyPlantsShowDialog.class);
                    intent.putExtra("message", getResources().getString(R.string.home_request_camera));
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
                }
            }
        }
    }

    /**
     * The Android Camera application encodes the photo in the return Intent delivered to onActivityResult()
     * as a small Bitmap in the extras, under the key "data".
     * The following code retrieves this image and displays it in an ImageView.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            PlantDataHandler db = new PlantDataHandler(getApplicationContext());
            //Adding picture here
            db.updatePlant(plant.getId(), "filenameCustomPicture", currentPhotoPath.toString());
            plant.setFilenameCustomPicture(currentPhotoPath);
            TextView plant_textImageView = findViewById(R.id.textImageView);
            ImageView plant_detail_img = (ImageView) findViewById(R.id.myPlantPicture);
            new MyPlantsListAdapter(db.getPlants(), getApplicationContext()).notifyDataSetChanged();

            try {
                File f = new File(currentPhotoPath);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                plant_detail_img.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

//    private void setPic() {
//        // Get the dimensions of the View
//        int targetW = plant_detail_img.getWidth();
//        int targetH = plant_detail_img.getHeight();
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//
//        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//        plant_detail_img.setImageBitmap(bitmap);
//    }

    /**
     * Convert and sort months for display
     * @param json
     * @return
     */
    private ArrayList<String> jsonArrayToStringConverter(String json){
        ArrayList<String> stringArray = new ArrayList<String>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] text = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                text[i] = jsonArray.getString(i);
                String str = jsonArray.getString(i);
                stringArray.add(str.substring(0, 1).toUpperCase() + str.substring(1)+".");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Ordering monthes
        Hashtable monthInt = new Hashtable();
        monthInt.put("Jan.", 1);
        monthInt.put("Feb.", 2);
        monthInt.put("Mar.", 3);
        monthInt.put("Apr.", 4);
        monthInt.put("May.", 5);
        monthInt.put("Jun.", 6);
        monthInt.put("Jul.", 7);
        monthInt.put("Aug.", 8);
        monthInt.put("Sep.", 9);
        monthInt.put("Oct.", 10);
        monthInt.put("Nov.", 11);
        monthInt.put("Dec.", 12);

        ArrayList<Integer> disorderNumberedMonthList = new ArrayList<>();
        for( String month : stringArray){
            disorderNumberedMonthList.add(Integer.valueOf(monthInt.get(month).toString()));
        }
        Collections.sort(disorderNumberedMonthList);

        Hashtable monthStr = new Hashtable();
        monthStr.put(1, "Jan.");
        monthStr.put(2, "Feb.");
        monthStr.put(3, "Mar.");
        monthStr.put(4, "Apr.");
        monthStr.put(5, "May.");
        monthStr.put(6, "Jun.");
        monthStr.put(7, "Jul.");
        monthStr.put(8, "Aug.");
        monthStr.put(9, "Sep.");
        monthStr.put(10, "Oct.");
        monthStr.put(11, "Nov.");
        monthStr.put(12, "Dec.");

        stringArray.clear();
        for( Integer month : disorderNumberedMonthList){
            stringArray.add(monthStr.get(month).toString());
        }
        return stringArray;
    }
}


