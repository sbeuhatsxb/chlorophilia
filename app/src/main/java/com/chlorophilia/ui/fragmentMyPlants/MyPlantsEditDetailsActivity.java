package com.chlorophilia.ui.fragmentMyPlants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;
import com.chlorophilia.ui.model.PlantDataHandler;
import com.chlorophilia.ui.toolbox.InputFilterMinMax;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;

import static java.lang.Math.abs;

public class MyPlantsEditDetailsActivity extends AppCompatActivity {

    Plant plant;
    Button updatePlant;
    FloatingActionButton fab;
    HashSet<String> bloomCheckboxes;
    HashSet<String> growthCheckboxes;
    HashSet<String> fruitCheckboxes;

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bloomCheckboxes = new HashSet<String>();
        growthCheckboxes = new HashSet<String>();
        fruitCheckboxes = new HashSet<String>();

        setContentView(R.layout.activity_myplants_edit_details);

        plant = (Plant) getIntent().getSerializableExtra("plant");

        //NON EDITABLE
        TextView plant_edit_detail_scientific_name = findViewById(R.id.myPlantEditScientificName);
        TextView plant_edit_detail_family = findViewById(R.id.myPlantEditFamily);
        TextView plant_detail_common_name = findViewById(R.id.myPlantCommonName);

        if (plant.getCommon_name() != null) {
            plant_detail_common_name.setText(plant.getCommon_name());
        } else {
            plant_detail_common_name.setText("");
        }

        if (plant.getScientific_name() != null) {
            plant_edit_detail_scientific_name.setText(plant.getScientific_name());
        } else {
            plant_edit_detail_scientific_name.setText("");
        }

        if (plant.getFamily() != null) {
            plant_edit_detail_family.setText(plant.getFamily());
        } else {
            plant_edit_detail_family.setText("");
        }

        //CHECKBOXES !!!
        final CheckBox bloom_january = (CheckBox) findViewById(R.id.bloom_january);
        final CheckBox bloom_february = (CheckBox) findViewById(R.id.bloom_february);
        final CheckBox bloom_march = (CheckBox) findViewById(R.id.bloom_march);
        final CheckBox bloom_april = (CheckBox) findViewById(R.id.bloom_april);
        final CheckBox bloom_june = (CheckBox) findViewById(R.id.bloom_june);
        final CheckBox bloom_july = (CheckBox) findViewById(R.id.bloom_july);
        final CheckBox bloom_may = (CheckBox) findViewById(R.id.bloom_may);
        final CheckBox bloom_august = (CheckBox) findViewById(R.id.bloom_august);
        final CheckBox bloom_september = (CheckBox) findViewById(R.id.bloom_september);
        final CheckBox bloom_november = (CheckBox) findViewById(R.id.bloom_november);
        final CheckBox bloom_october = (CheckBox) findViewById(R.id.bloom_october);
        final CheckBox bloom_december = (CheckBox) findViewById(R.id.bloom_december);
        final CheckBox growth_january = (CheckBox) findViewById(R.id.growth_january);
        final CheckBox growth_february = (CheckBox) findViewById(R.id.growth_february);
        final CheckBox growth_march = (CheckBox) findViewById(R.id.growth_march);
        final CheckBox growth_april = (CheckBox) findViewById(R.id.growth_april);
        final CheckBox growth_june = (CheckBox) findViewById(R.id.growth_june);
        final CheckBox growth_july = (CheckBox) findViewById(R.id.growth_july);
        final CheckBox growth_may = (CheckBox) findViewById(R.id.growth_may);
        final CheckBox growth_august = (CheckBox) findViewById(R.id.growth_august);
        final CheckBox growth_september = (CheckBox) findViewById(R.id.growth_september);
        final CheckBox growth_november = (CheckBox) findViewById(R.id.growth_november);
        final CheckBox growth_october = (CheckBox) findViewById(R.id.growth_october);
        final CheckBox growth_december = (CheckBox) findViewById(R.id.growth_december);
        final CheckBox fruit_january = (CheckBox) findViewById(R.id.fruit_january);
        final CheckBox fruit_february = (CheckBox) findViewById(R.id.fruit_february);
        final CheckBox fruit_march = (CheckBox) findViewById(R.id.fruit_march);
        final CheckBox fruit_april = (CheckBox) findViewById(R.id.fruit_april);
        final CheckBox fruit_june = (CheckBox) findViewById(R.id.fruit_june);
        final CheckBox fruit_july = (CheckBox) findViewById(R.id.fruit_july);
        final CheckBox fruit_may = (CheckBox) findViewById(R.id.fruit_may);
        final CheckBox fruit_august = (CheckBox) findViewById(R.id.fruit_august);
        final CheckBox fruit_september = (CheckBox) findViewById(R.id.fruit_september);
        final CheckBox fruit_november = (CheckBox) findViewById(R.id.fruit_november);
        final CheckBox fruit_october = (CheckBox) findViewById(R.id.fruit_october);
        final CheckBox fruit_december = (CheckBox) findViewById(R.id.fruit_december);

        //POPULATE CHECKBOXES
        if(plant.getBloomMonths() != null){
            ArrayList<String> bloomMonths = jsonArrayToArrayListConverter(plant.getBloomMonths());
            ListIterator<String> li = bloomMonths.listIterator();
            while (li.hasNext()){
                switch (li.next()) {
                    case "jan":
                        populateCheckboxes(bloom_january);
                        break;
                    case "feb":
                        populateCheckboxes(bloom_february);
                        break;
                    case "mar":
                        populateCheckboxes(bloom_march);
                        break;
                    case "apr":
                        populateCheckboxes(bloom_april);
                        break;
                    case "may":
                        populateCheckboxes(bloom_may);
                        break;
                    case "jun":
                        populateCheckboxes(bloom_june);
                        break;
                    case "jul":
                        populateCheckboxes(bloom_july);
                        break;
                    case "aug":
                        populateCheckboxes(bloom_august);
                        break;
                    case "sep":
                        populateCheckboxes(bloom_september);
                        break;
                    case "oct":
                        populateCheckboxes(bloom_october);
                        break;
                    case "nov":
                        populateCheckboxes(bloom_november);
                        break;
                    case "dec":
                        populateCheckboxes(bloom_december);
                        break;
                }
            }
        }
        if(plant.getFruitMonths() != null){
            ArrayList<String> fruitMonths = jsonArrayToArrayListConverter(plant.getFruitMonths());
            ListIterator<String> li = fruitMonths.listIterator();
            while (li.hasNext()){
                switch (li.next()) {
                    case "jan":
                        populateCheckboxes(fruit_january);
                        break;
                    case "feb":
                        populateCheckboxes(fruit_february);
                        break;
                    case "mar":
                        populateCheckboxes(fruit_march);
                        break;
                    case "apr":
                        populateCheckboxes(fruit_april);
                        break;
                    case "may":
                        populateCheckboxes(fruit_may);
                        break;
                    case "jun":
                        populateCheckboxes(fruit_june);
                        break;
                    case "jul":
                        populateCheckboxes(fruit_july);
                        break;
                    case "aug":
                        populateCheckboxes(fruit_august);
                        break;
                    case "sep":
                        populateCheckboxes(fruit_september);
                        break;
                    case "oct":
                        populateCheckboxes(fruit_october);
                        break;
                    case "nov":
                        populateCheckboxes(fruit_november);
                        break;
                    case "dec":
                        populateCheckboxes(fruit_december);
                        break;
                }
            }
        }
        if(plant.getGrowthMonths() != null){
            ArrayList<String> growthMonths = jsonArrayToArrayListConverter(plant.getGrowthMonths());
            ListIterator<String> li = growthMonths.listIterator();
            while (li.hasNext()){
                switch (li.next()) {
                    case "jan":
                        populateCheckboxes(growth_january);
                        break;
                    case "feb":
                        populateCheckboxes(growth_february);
                        break;
                    case "mar":
                        populateCheckboxes(growth_march);
                        break;
                    case "apr":
                        populateCheckboxes(growth_april);
                        break;
                    case "may":
                        populateCheckboxes(growth_may);
                        break;
                    case "jun":
                        populateCheckboxes(growth_june);
                        break;
                    case "jul":
                        populateCheckboxes(growth_july);
                        break;
                    case "aug":
                        populateCheckboxes(growth_august);
                        break;
                    case "sep":
                        populateCheckboxes(growth_september);
                        break;
                    case "oct":
                        populateCheckboxes(growth_october);
                        break;
                    case "nov":
                        populateCheckboxes(growth_november);
                        break;
                    case "dec":
                        populateCheckboxes(growth_december);
                        break;
                }
            }
        }


        //EDITABLE
        EditText plant_edit_detail_nickname = (EditText) findViewById(R.id.myPlantEditNickname);
        EditText plant_edit_detail_Precipitation_min = (EditText) findViewById(R.id.myPlantEditPrecipitationMin);
        EditText plant_edit_detail_Precipitation_max = (EditText) findViewById(R.id.myPlantEditPrecipitationMax);
        EditText plant_edit_detail_ph_min = (EditText) findViewById(R.id.myPlantEditPhMin);
        EditText plant_edit_detail_ph_max = (EditText) findViewById(R.id.myPlantEditPhMax);
        EditText plant_edit_detail_TemperatureMin = (EditText) findViewById(R.id.myPlantEditTemperatureMin);
        EditText plant_edit_detail_TemperatureMax = (EditText) findViewById(R.id.myPlantEditTemperatureMax);
        EditText plant_edit_detail_spread = (EditText) findViewById(R.id.myPlantEditSpread);
        EditText plant_edit_detail_minimumRootDepth = (EditText) findViewById(R.id.myPlantEditMinRootDepth);
        EditText plant_edit_detail_rowSpacing = (EditText) findViewById(R.id.myPlantEditRowSpacing);
        EditText plant_edit_detail_harvestDays = (EditText) findViewById(R.id.myPlantEditDaysToHarvest);
        EditText plant_edit_detail_sowing = (EditText) findViewById(R.id.myPlantEditSowing);
        EditText plant_edit_detail_bibliography = (EditText) findViewById(R.id.myPlantEditBibliography);

        //SET LIMIT TO EDIT TEXT
        plant_edit_detail_ph_min.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "14")});
        plant_edit_detail_ph_max.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "14")});

        //DESCRIPTIONS
        TextView editRowSpacingText = findViewById(R.id.editRowSpacingText);
        TextView editSpreadText = findViewById(R.id.editSpreadText);
        TextView editPrecipitationTextMax = findViewById(R.id.editPrecipitationTextMax);
        TextView editPrecipitationTextMin = findViewById(R.id.editPrecipitationTextMin);
        plant.setPrecipitationMeasure("mm");
        plant.setRowSpacingMeasure("cm");
        plant.setSpreadMeasure("cm");

        editPrecipitationTextMax.setText(getResources().getString(R.string.plant_edit_detail_precipitation_max) + " " + plant.getPrecipitationMeasure() + ". (min.) :");
        editPrecipitationTextMin.setText(getResources().getString(R.string.plant_edit_detail_precipitation_min) + " " + plant.getPrecipitationMeasure() + ". (max.) :");
        editRowSpacingText.setText(getResources().getString(R.string.plant_edit_detail_rowSpacing) + " (in " +plant.getRowSpacingMeasure()+".) :");
        editSpreadText.setText(getResources().getString(R.string.plant_edit_detail_spread) + " (in " +plant.getSpreadMeasure()+".) :");


        //SPINNERS - Find spinners 0 -> 10
        Spinner plant_edit_detail_light = (Spinner) findViewById(R.id.light_spinner);
        Spinner plant_edit_detail_atmosphericHumidity = (Spinner) findViewById(R.id.atmospheric_humidity_spinner);
        Spinner plant_edit_detail_soilNutriments = (Spinner) findViewById(R.id.soil_nutriments_spinner);
        Spinner plant_edit_detail_soilSalinity = (Spinner) findViewById(R.id.soil_salinity_spinner);
        Spinner plant_edit_detail_soilHumidity = (Spinner) findViewById(R.id.soil_humidity_spinner);

        // SPINNERS : Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterUpToTen = ArrayAdapter.createFromResource(this,
                R.array.upToTen, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterUpToTen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        plant_edit_detail_light.setAdapter(adapterUpToTen);
        // Set default value from DB
        if (plant.getLight() != null) {
            plant_edit_detail_light.setSelection(Integer.parseInt(plant.getLight()));
        }

        //SPINNER RICHNESS
        ArrayAdapter<CharSequence> adapterRichness = ArrayAdapter.createFromResource(this,
                R.array.soilrichness, android.R.layout.simple_spinner_item);
        adapterRichness.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plant_edit_detail_soilNutriments.setAdapter(adapterRichness);
        plant_edit_detail_soilSalinity.setAdapter(adapterRichness);
        if (plant.getSoilNutriments() != null) {
            plant_edit_detail_soilNutriments.setSelection(Integer.parseInt(plant.getSoilNutriments()));
        }
        if (plant.getSoilSalinity() != null) {
            plant_edit_detail_soilSalinity.setSelection(Integer.parseInt(plant.getSoilSalinity()));
        }

        //SPINNER PERCENTAGES
        ArrayAdapter<CharSequence> adapterPercent = ArrayAdapter.createFromResource(this,
                R.array.percent, android.R.layout.simple_spinner_item);
        adapterPercent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plant_edit_detail_atmosphericHumidity.setAdapter(adapterPercent);
        plant_edit_detail_soilHumidity.setAdapter(adapterPercent);
        if (plant.getAtmosphericHumidity() != null) {
            plant_edit_detail_atmosphericHumidity.setSelection(Integer.parseInt(plant.getAtmosphericHumidity()));
        }
        if (plant.getSoilHumidity() != null) {
            plant_edit_detail_soilHumidity.setSelection(Integer.parseInt(plant.getSoilHumidity()));
        }

        if (plant.getNickname() != null) {
            plant_edit_detail_nickname.setText(plant.getNickname());
        } else {
            plant_edit_detail_nickname.setText("");
        }

        if (plant.getPhMinimum() != null) {
            plant_edit_detail_ph_min.setText(plant.getPhMinimum());
        } else {
            plant_edit_detail_ph_min.setText("");
        }

        if (plant.getPhMaximum() != null) {
            plant_edit_detail_ph_max.setText(plant.getPhMaximum());
        } else {
            plant_edit_detail_ph_max.setText("");
        }

        if (plant.getMinimumPrecipitation() != null) {

            plant_edit_detail_Precipitation_min.setText(plant.getMinimumPrecipitation());
        } else {
            plant_edit_detail_Precipitation_min.setText("");
        }

        if (plant.getMaximumPrecipitation() != null) {
            plant_edit_detail_Precipitation_max.setText(plant.getMaximumPrecipitation());
        } else {
            plant_edit_detail_Precipitation_max.setText("");
        }

        if (plant.getMinimumTemperature() != null) {
            plant_edit_detail_TemperatureMin.setText(plant.getMinimumTemperature());
        } else {
            plant_edit_detail_TemperatureMin.setText("");
        }

        if (plant.getMaximumTemperature() != null) {
            plant_edit_detail_TemperatureMax.setText(plant.getMaximumTemperature());
        } else {
            plant_edit_detail_TemperatureMax.setText("");
        }

        if (plant.getSpread() != null) {
            plant_edit_detail_spread.setText(plant.getSpread());
        } else {
            plant_edit_detail_spread.setText("");
        }

        if (plant.getMinimumRootDepth() != null) {
            plant_edit_detail_minimumRootDepth.setText(plant.getMinimumRootDepth());
        } else {
            plant_edit_detail_minimumRootDepth.setText("");
        }

        if (plant.getSowing() != null) {
            plant_edit_detail_sowing.setText(plant.getSowing());
        } else {
            plant_edit_detail_sowing.setText("");
        }

        if (plant.getRowSpacing() != null) {
            plant_edit_detail_rowSpacing.setText(plant.getRowSpacing());
        } else {
            plant_edit_detail_rowSpacing.setText("");
        }

        if (plant.getBibliography() != null) {
            plant_edit_detail_bibliography.setText(plant.getBibliography());
        } else {
            plant_edit_detail_bibliography.setText("");
        }

        if (plant.getDays_to_harvest() != null) {
            plant_edit_detail_harvestDays.setText(plant.getDays_to_harvest());
        } else {
            plant_edit_detail_harvestDays.setText("");
        }

        updatePlant = (Button) findViewById(R.id.button_update);
        updatePlant.setText(getResources().getString(R.string.plant_update_plant) + " " + plant.getNickname());
        updatePlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant.setBibliography(plant_edit_detail_bibliography.getText().toString());
                plant.setCommon_name(plant_edit_detail_nickname.getText().toString());
                plant.setDays_to_harvest(plant_edit_detail_harvestDays.getText().toString());
                plant.setMaximumPrecipitation(plant_edit_detail_Precipitation_max.getText().toString());
                plant.setMaximumTemperature(plant_edit_detail_TemperatureMax.getText().toString());
                plant.setMinimumPrecipitation(plant_edit_detail_Precipitation_min.getText().toString());
                plant.setMinimumRootDepth(plant_edit_detail_minimumRootDepth.getText().toString());
                plant.setMinimumTemperature(plant_edit_detail_TemperatureMin.getText().toString());
                plant.setNickname(plant_edit_detail_nickname.getText().toString());
                plant.setPhMaximum(plant_edit_detail_ph_min.getText().toString());
                plant.setPhMinimum(plant_edit_detail_ph_max.getText().toString());
                plant.setRowSpacing(plant_edit_detail_rowSpacing.getText().toString());
                plant.setSowing(plant_edit_detail_sowing.getText().toString());
                plant.setSpread(plant_edit_detail_spread.getText().toString());
                plant.setBloomMonths(jsonConstructor(bloomCheckboxes));
                plant.setFruitMonths(jsonConstructor(growthCheckboxes));
                plant.setGrowthMonths(jsonConstructor(fruitCheckboxes));

                if(plant_edit_detail_light.getSelectedItemPosition() != 0){
                    plant.setLight(String.valueOf(plant_edit_detail_light.getSelectedItemPosition()));
                } else {
                    plant.setLight("");
                }
                if(plant_edit_detail_soilHumidity.getSelectedItemPosition() != 0){
                    plant.setSoilHumidity(String.valueOf(plant_edit_detail_soilHumidity.getSelectedItemPosition()));
                } else {
                    plant.setSoilHumidity("");
                }
                if(plant_edit_detail_atmosphericHumidity.getSelectedItemPosition() != 0){
                    plant.setAtmosphericHumidity(String.valueOf(plant_edit_detail_atmosphericHumidity.getSelectedItemPosition()));
                } else {
                    plant.setAtmosphericHumidity("");
                }
                if(plant_edit_detail_soilNutriments.getSelectedItemPosition() != 0){
                    plant.setSoilNutriments(String.valueOf(plant_edit_detail_soilNutriments.getSelectedItemPosition()));
                } else {
                    plant.setSoilNutriments("");
                }
                if(plant_edit_detail_soilSalinity.getSelectedItemPosition() != 0){
                    plant.setSoilSalinity(String.valueOf(plant_edit_detail_soilSalinity.getSelectedItemPosition()));
                } else {
                    plant.setSoilSalinity("");
                }


                //Form validation
                boolean validForm = true;
                if(!plant_edit_detail_Precipitation_max.getText().toString().equals("") && !plant_edit_detail_Precipitation_min.getText().toString().equals("")){
                    if(Integer.parseInt(plant_edit_detail_Precipitation_max.getText().toString()) < Integer.parseInt(plant_edit_detail_Precipitation_min.getText().toString())){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.minMaxPrec), Toast.LENGTH_LONG).show();
                        validForm = false;
                    }
                }
                if(!plant_edit_detail_ph_min.getText().toString().equals("") && !plant_edit_detail_ph_max.getText().toString().equals("")){
                    if(Integer.parseInt(plant_edit_detail_ph_max.getText().toString()) < Integer.parseInt(plant_edit_detail_ph_min.getText().toString())){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.minMaxPh), Toast.LENGTH_LONG).show();
                        validForm = false;
                    }
                }
                if(!plant_edit_detail_TemperatureMax.getText().toString().equals("") && !plant_edit_detail_TemperatureMin.getText().toString().equals("")){
                    if(Integer.parseInt(plant_edit_detail_TemperatureMax.getText().toString()) < Integer.parseInt(plant_edit_detail_TemperatureMin.getText().toString())){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.minMaxTemp), Toast.LENGTH_LONG).show();
                        validForm = false;
                    }
                }

                if(validForm){
                    PlantDataHandler db = new PlantDataHandler(getApplicationContext());
                    db.updatePlant(plant);
                    finish();
                }

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab_update);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant.setBibliography(plant_edit_detail_bibliography.getText().toString());
                plant.setCommon_name(plant_edit_detail_nickname.getText().toString());
                plant.setDays_to_harvest(plant_edit_detail_harvestDays.getText().toString());
                plant.setMaximumPrecipitation(plant_edit_detail_Precipitation_max.getText().toString());
                plant.setMaximumTemperature(plant_edit_detail_TemperatureMax.getText().toString());
                plant.setMinimumPrecipitation(plant_edit_detail_Precipitation_min.getText().toString());
                plant.setMinimumRootDepth(plant_edit_detail_minimumRootDepth.getText().toString());
                plant.setMinimumTemperature(plant_edit_detail_TemperatureMin.getText().toString());
                plant.setNickname(plant_edit_detail_nickname.getText().toString());
                plant.setPhMaximum(plant_edit_detail_ph_min.getText().toString());
                plant.setPhMinimum(plant_edit_detail_ph_max.getText().toString());
                plant.setRowSpacing(plant_edit_detail_rowSpacing.getText().toString());
                plant.setSowing(plant_edit_detail_sowing.getText().toString());
                plant.setSpread(plant_edit_detail_spread.getText().toString());
                plant.setBloomMonths(jsonConstructor(bloomCheckboxes));
                plant.setFruitMonths(jsonConstructor(growthCheckboxes));
                plant.setGrowthMonths(jsonConstructor(fruitCheckboxes));

                if(plant_edit_detail_light.getSelectedItemPosition() != 0){
                    plant.setLight(String.valueOf(plant_edit_detail_light.getSelectedItemPosition()));
                } else {
                    plant.setLight("");
                }
                if(plant_edit_detail_soilHumidity.getSelectedItemPosition() != 0){
                    plant.setSoilHumidity(String.valueOf(plant_edit_detail_soilHumidity.getSelectedItemPosition()));
                } else {
                    plant.setSoilHumidity("");
                }
                if(plant_edit_detail_atmosphericHumidity.getSelectedItemPosition() != 0){
                    plant.setAtmosphericHumidity(String.valueOf(plant_edit_detail_atmosphericHumidity.getSelectedItemPosition()));
                } else {
                    plant.setAtmosphericHumidity("");
                }
                if(plant_edit_detail_soilNutriments.getSelectedItemPosition() != 0){
                    plant.setSoilNutriments(String.valueOf(plant_edit_detail_soilNutriments.getSelectedItemPosition()));
                } else {
                    plant.setSoilNutriments("");
                }
                if(plant_edit_detail_soilSalinity.getSelectedItemPosition() != 0){
                    plant.setSoilSalinity(String.valueOf(plant_edit_detail_soilSalinity.getSelectedItemPosition()));
                } else {
                    plant.setSoilSalinity("");
                }


                //Form validation
                boolean validForm = true;
                if(!plant_edit_detail_Precipitation_max.getText().toString().equals("") && !plant_edit_detail_Precipitation_min.getText().toString().equals("")){
                    if(Integer.parseInt(plant_edit_detail_Precipitation_max.getText().toString()) < Integer.parseInt(plant_edit_detail_Precipitation_min.getText().toString())){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.minMaxPrec), Toast.LENGTH_LONG).show();
                        validForm = false;
                    }
                }
                if(!plant_edit_detail_ph_min.getText().toString().equals("") && !plant_edit_detail_ph_max.getText().toString().equals("")){
                    if(Integer.parseInt(plant_edit_detail_ph_max.getText().toString()) < Integer.parseInt(plant_edit_detail_ph_min.getText().toString())){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.minMaxPh), Toast.LENGTH_LONG).show();
                        validForm = false;
                    }
                }
                if(!plant_edit_detail_TemperatureMax.getText().toString().equals("") && !plant_edit_detail_TemperatureMin.getText().toString().equals("")){
                    if(Integer.parseInt(plant_edit_detail_TemperatureMax.getText().toString()) < Integer.parseInt(plant_edit_detail_TemperatureMin.getText().toString())){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.minMaxTemp), Toast.LENGTH_LONG).show();
                        validForm = false;
                    }
                }

                if(validForm){
                    PlantDataHandler db = new PlantDataHandler(getApplicationContext());
                    db.updatePlant(plant);
                    finish();
                }

            }
        });

//
//        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.baseline_add_20);
//        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#FFFFFF"));
//
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Get plant slug  from input
//                Intent intent = new Intent(getApplicationContext(), NicknameActivity.class);
//                intent.putExtra("plant", plant);
//                startActivity(intent);
//                finish();
//            }
//        });
    }


    @SuppressLint("NonConstantResourceId")
    public void onCheckboxClicked(View view){
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.bloom_january:
                if (checked)
                    bloomCheckboxes.add("jan");
                else
                    bloomCheckboxes.remove("jan");
                break;
            case R.id.bloom_february:
                if (checked)
                    bloomCheckboxes.add("feb");
                else
                    bloomCheckboxes.remove("feb");
                break;
            case R.id.bloom_march:
                if (checked)
                    bloomCheckboxes.add("mar");
                else
                    bloomCheckboxes.remove("mar");
                break;
            case R.id.bloom_april:
                if (checked)
                    bloomCheckboxes.add("apr");
                else
                    bloomCheckboxes.remove("apr");
                break;
            case R.id.bloom_june:
                if (checked)
                    bloomCheckboxes.add("jun");
                else
                    bloomCheckboxes.remove("jun");
                break;
            case R.id.bloom_july:
                if (checked)
                    bloomCheckboxes.add("jul");
                else
                    bloomCheckboxes.remove("jul");
                break;
            case R.id.bloom_may:
                if (checked)
                    bloomCheckboxes.add("may");
                else
                    bloomCheckboxes.remove("may");
                break;
            case R.id.bloom_august:
                if (checked)
                    bloomCheckboxes.add("aug");
                else
                    bloomCheckboxes.remove("aug");
                break;
            case R.id.bloom_september:
                if (checked)
                    bloomCheckboxes.add("sep");
                else
                    bloomCheckboxes.remove("sep");
                break;
            case R.id.bloom_november:
                if (checked)
                    bloomCheckboxes.add("nov");
                else
                    bloomCheckboxes.remove("nov");
                break;
            case R.id.bloom_october:
                if (checked)
                    bloomCheckboxes.add("oct");
                else
                    bloomCheckboxes.remove("oct");
                break;
            case R.id.bloom_december:
                if (checked)
                    bloomCheckboxes.add("dec");
                else
                    bloomCheckboxes.remove("dec");
                break;
            case R.id.growth_january:
                if (checked)
                    growthCheckboxes.add("jan");
                else
                    growthCheckboxes.remove("jan");
                break;
            case R.id.growth_february:
                if (checked)
                    growthCheckboxes.add("feb");
                else
                    growthCheckboxes.remove("feb");
                break;
            case R.id.growth_march:
                if (checked)
                    growthCheckboxes.add("mar");
                else
                    growthCheckboxes.remove("mar");
                break;
            case R.id.growth_april:
                if (checked)
                    growthCheckboxes.add("apr");
                else
                    growthCheckboxes.remove("apr");
                break;
            case R.id.growth_june:
                if (checked)
                    growthCheckboxes.add("jun");
                else
                    growthCheckboxes.remove("jun");
                break;
            case R.id.growth_july:
                if (checked)
                    growthCheckboxes.add("jul");
                else
                    growthCheckboxes.remove("jul");
                break;
            case R.id.growth_may:
                if (checked)
                    growthCheckboxes.add("may");
                else
                    growthCheckboxes.remove("may");
                break;
            case R.id.growth_august:
                if (checked)
                    growthCheckboxes.add("aug");
                else
                    growthCheckboxes.remove("aug");
                break;
            case R.id.growth_september:
                if (checked)
                    growthCheckboxes.add("sep");
                else
                    growthCheckboxes.remove("sep");
                break;
            case R.id.growth_november:
                if (checked)
                    growthCheckboxes.add("nov");
                else
                    growthCheckboxes.remove("nov");
                break;
            case R.id.growth_october:
                if (checked)
                    growthCheckboxes.add("oct");
                else
                    growthCheckboxes.remove("oct");
                break;
            case R.id.growth_december:
                if (checked)
                    growthCheckboxes.add("dec");
                else
                    growthCheckboxes.remove("dec");
                break;
            case R.id.fruit_january:
                if (checked)
                    fruitCheckboxes.add("jan");
                else
                    fruitCheckboxes.remove("jan");
                break;
            case R.id.fruit_february:
                if (checked)
                    fruitCheckboxes.add("feb");
                else
                    fruitCheckboxes.remove("feb");
                break;
            case R.id.fruit_march:
                if (checked)
                    fruitCheckboxes.add("mar");
                else
                    fruitCheckboxes.remove("mar");
                break;
            case R.id.fruit_april:
                if (checked)
                    fruitCheckboxes.add("apr");
                else
                    fruitCheckboxes.remove("apr");
                break;
            case R.id.fruit_june:
                if (checked)
                    fruitCheckboxes.add("jun");
                else
                    fruitCheckboxes.remove("jun");
                break;
            case R.id.fruit_july:
                if (checked)
                    fruitCheckboxes.add("jul");
                else
                    fruitCheckboxes.remove("jul");
                break;
            case R.id.fruit_may:
                if (checked)
                    fruitCheckboxes.add("may");
                else
                    fruitCheckboxes.remove("may");
                break;
            case R.id.fruit_august:
                if (checked)
                    fruitCheckboxes.add("aug");
                else
                    fruitCheckboxes.remove("aug");
                break;
            case R.id.fruit_september:
                if (checked)
                    fruitCheckboxes.add("sep");
                else
                    fruitCheckboxes.remove("sep");
                break;
            case R.id.fruit_november:
                if (checked)
                    fruitCheckboxes.add("nov");
                else
                    fruitCheckboxes.remove("nov");
                break;
            case R.id.fruit_october:
                if (checked)
                    fruitCheckboxes.add("oct");
                else
                    fruitCheckboxes.remove("oct");
                break;
            case R.id.fruit_december:
                if (checked)
                    fruitCheckboxes.add("dec");
                else
                    fruitCheckboxes.remove("dec");
                break;
        }
    }

    private String jsonConstructor(HashSet hashSet){
        String jsonString = "";
        if(hashSet.size() > 0){
            jsonString = "[";
            Iterator<String> it = hashSet.iterator();
            while (it.hasNext()){
                jsonString += "\"" + it.next() + "\",";
            }
            jsonString = removeLastComma(jsonString);
            jsonString += "]";
        }
        return jsonString;
    }

    private String removeLastComma(String str){
        return new StringBuilder(str)
                .deleteCharAt(str.length() - 1)
                .toString();
    }

    private ArrayList<String> jsonArrayToArrayListConverter(String json){
        ArrayList<String> stringArray = new ArrayList<String>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                stringArray.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringArray;
    }

    private void populateCheckboxes(CheckBox checkBox){
        checkBox.setChecked(true);
    }
}
