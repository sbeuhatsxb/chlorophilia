package com.chlorophilia.ui.fragmentMyPlants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MyPlantsEditDetailsActivity extends AppCompatActivity {

    Plant plant;
    ExtendedFloatingActionButton fab;
    HashSet<Integer> bloomCheckboxes;
    HashSet<Integer> growthCheckboxes;
    HashSet<Integer> fruitCheckboxes;

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bloomCheckboxes = new HashSet<Integer>();
        growthCheckboxes = new HashSet<Integer>();
        fruitCheckboxes = new HashSet<Integer>();

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
        //MORE CHECKBOXES !!
        final CheckBox vegetableBox = (CheckBox) findViewById(R.id.myPlantVegetable);
        final CheckBox edibleBox = (CheckBox) findViewById(R.id.myPlantEdible);
        final CheckBox flowerConspBox = (CheckBox) findViewById(R.id.myPlantEditFlowerConspicuous);
        final CheckBox fruitConspBox = (CheckBox) findViewById(R.id.myPlantEditFruitConspicuous);
        //GROWTH HABIT CHECKBOXES
        final CheckBox treeBox = (CheckBox) findViewById(R.id.tree);
        final CheckBox forbBox = (CheckBox) findViewById(R.id.forb_herb);
        final CheckBox vineBox = (CheckBox) findViewById(R.id.vine);
        final CheckBox subshrubBox = (CheckBox) findViewById(R.id.subshrub);
        final CheckBox shrubBox = (CheckBox) findViewById(R.id.shrub);
        final CheckBox graminoidBox = (CheckBox) findViewById(R.id.graminoid);
        final CheckBox lichenousBox = (CheckBox) findViewById(R.id.lichenous);
        final CheckBox nonvascularBox = (CheckBox) findViewById(R.id.nonvascular);
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
        EditText plant_edit_detail_averageHeight = (EditText) findViewById(R.id.myPlantEditAverageHeight);
        EditText plant_edit_detail_maxHeight = (EditText) findViewById(R.id.myPlantEditMaxHeight);
        EditText plant_edit_detail_foliage_color = (EditText) findViewById(R.id.myPlantEditFoliageColor);
        EditText plant_edit_detail_flower_color = (EditText) findViewById(R.id.myPlantEditFlowerColor);
        EditText plant_edit_detail_fruit_color = (EditText) findViewById(R.id.myPlantEditFruitColor);
        EditText plant_edit_detail_ediblePart = (EditText) findViewById(R.id.myPlantEditEdiblePart);
        //DESCRIPTIONS
        TextView editRowSpacingText = findViewById(R.id.editRowSpacingText);
        TextView editSpreadText = findViewById(R.id.editSpreadText);
        TextView editPrecipitationTextMax = findViewById(R.id.editPrecipitationTextMax);
        TextView editPrecipitationTextMin = findViewById(R.id.editPrecipitationTextMin);
        //SPINNERS
        Spinner plant_edit_detail_light = (Spinner) findViewById(R.id.light_spinner);
        Spinner plant_edit_detail_atmosphericHumidity = (Spinner) findViewById(R.id.atmospheric_humidity_spinner);
        Spinner plant_edit_detail_soilNutriments = (Spinner) findViewById(R.id.soil_nutriments_spinner);
        Spinner plant_edit_detail_soilSalinity = (Spinner) findViewById(R.id.soil_salinity_spinner);
        Spinner plant_edit_detail_soilHumidity = (Spinner) findViewById(R.id.soil_humidity_spinner);
        Spinner plant_edit_detail_growthForm = (Spinner) findViewById(R.id.growthFormSpinner);
        Spinner plant_edit_detail_foliage_texture = (Spinner) findViewById(R.id.myPlantEditFoliageTexture);
        Spinner plant_edit_detail_anaerobic = (Spinner) findViewById(R.id.myPlantEditAnaerobic);
        Spinner plant_edit_detail_growthRate = (Spinner) findViewById(R.id.myPlantEditGrowthRate);

        if (plant.getGrowthHabit() != null) {
            String[] growthHabitArray = plant.getGrowthHabit().split(", ", -1);
            for (int i = 0; i < growthHabitArray.length; i++) {
                switch (growthHabitArray[i]) {
                    case "Tree":
                        populateCheckboxes(treeBox);
                        break;
                    case "Nonvascular":
                        populateCheckboxes(nonvascularBox);
                        break;
                    case "Forb/herb":
                        populateCheckboxes(forbBox);
                        break;
                    case "Vine":
                        populateCheckboxes(vineBox);
                        break;
                    case "Subshrub":
                        populateCheckboxes(subshrubBox);
                        break;
                    case "Shrub":
                        populateCheckboxes(shrubBox);
                        break;
                    case "Graminoid":
                        populateCheckboxes(graminoidBox);
                        break;
                    case "Lichenous":
                        populateCheckboxes(lichenousBox);
                        break;
                }
            }
        }

        if(plant.getVegetable() != null) {
            if(plant.getVegetable().equals("true")){
                populateCheckboxes(vegetableBox);
            }
        }

        if(plant.getEdible() != null) {
            if(plant.getEdible().equals("true")){
                populateCheckboxes(edibleBox);
            }
        }

        if(plant.getFruitConspicuous() != null) {
            if(plant.getFruitConspicuous().equals("true")){
                populateCheckboxes(fruitConspBox);
            }
        }

        if(plant.getFlowerConspicuous() != null) {
            if(plant.getFlowerConspicuous().equals("true")){
                populateCheckboxes(flowerConspBox);
            }
        }

        //POPULATE CHECKBOXES
        if(plant.getBloomMonths() != null){
            ArrayList<Integer> bloomMonths = jsonArrayToArrayListConverter(plant.getBloomMonths());
            for (Integer bloomMonth : bloomMonths) {
                switch (bloomMonth) {
                    case 1:
                        populateCheckboxes(bloom_january);
                        bloomCheckboxes.add(1);
                        break;
                    case 2:
                        populateCheckboxes(bloom_february);
                        bloomCheckboxes.add(2);
                        break;
                    case 3:
                        populateCheckboxes(bloom_march);
                        bloomCheckboxes.add(3);
                        break;
                    case 4:
                        populateCheckboxes(bloom_april);
                        bloomCheckboxes.add(4);
                        break;
                    case 5:
                        populateCheckboxes(bloom_may);
                        bloomCheckboxes.add(5);
                        break;
                    case 6:
                        populateCheckboxes(bloom_june);
                        bloomCheckboxes.add(6);
                        break;
                    case 7:
                        populateCheckboxes(bloom_july);
                        bloomCheckboxes.add(7);
                        break;
                    case 8:
                        populateCheckboxes(bloom_august);
                        bloomCheckboxes.add(8);
                        break;
                    case 9:
                        populateCheckboxes(bloom_september);
                        bloomCheckboxes.add(9);
                        break;
                    case 10:
                        populateCheckboxes(bloom_october);
                        bloomCheckboxes.add(10);
                        break;
                    case 11:
                        populateCheckboxes(bloom_november);
                        bloomCheckboxes.add(11);
                        break;
                    case 12:
                        populateCheckboxes(bloom_december);
                        bloomCheckboxes.add(12);
                        break;
                }
            }
        }
        if(plant.getFruitMonths() != null){
            ArrayList<Integer> fruitMonths = jsonArrayToArrayListConverter(plant.getFruitMonths());
            for (Integer fruitMonth : fruitMonths) {
                switch (fruitMonth) {
                    case 1:
                        populateCheckboxes(fruit_january);
                        fruitCheckboxes.add(1);
                        break;
                    case 2:
                        populateCheckboxes(fruit_february);
                        fruitCheckboxes.add(2);
                        break;
                    case 3:
                        populateCheckboxes(fruit_march);
                        fruitCheckboxes.add(3);
                        break;
                    case 4:
                        populateCheckboxes(fruit_april);
                        fruitCheckboxes.add(4);
                        break;
                    case 5:
                        populateCheckboxes(fruit_may);
                        fruitCheckboxes.add(5);
                        break;
                    case 6:
                        populateCheckboxes(fruit_june);
                        fruitCheckboxes.add(6);
                        break;
                    case 7:
                        populateCheckboxes(fruit_july);
                        fruitCheckboxes.add(7);
                        break;
                    case 8:
                        populateCheckboxes(fruit_august);
                        fruitCheckboxes.add(8);
                        break;
                    case 9:
                        populateCheckboxes(fruit_september);
                        fruitCheckboxes.add(9);
                        break;
                    case 10:
                        populateCheckboxes(fruit_october);
                        fruitCheckboxes.add(10);
                        break;
                    case 11:
                        populateCheckboxes(fruit_november);
                        fruitCheckboxes.add(11);
                        break;
                    case 12:
                        populateCheckboxes(fruit_december);
                        fruitCheckboxes.add(12);
                        break;
                }
            }
        }
        if(plant.getGrowthMonths() != null){
            ArrayList<Integer> growthMonths = jsonArrayToArrayListConverter(plant.getGrowthMonths());
            for (Integer growthMonth : growthMonths) {
                switch (growthMonth) {
                    case 1:
                        populateCheckboxes(growth_january);
                        growthCheckboxes.add(1);
                        break;
                    case 2:
                        populateCheckboxes(growth_february);
                        growthCheckboxes.add(2);
                        break;
                    case 3:
                        populateCheckboxes(growth_march);
                        growthCheckboxes.add(3);
                        break;
                    case 4:
                        populateCheckboxes(growth_april);
                        growthCheckboxes.add(4);
                        break;
                    case 5:
                        populateCheckboxes(growth_may);
                        growthCheckboxes.add(5);
                        break;
                    case 6:
                        populateCheckboxes(growth_june);
                        growthCheckboxes.add(6);
                        break;
                    case 7:
                        populateCheckboxes(growth_july);
                        growthCheckboxes.add(7);
                        break;
                    case 8:
                        populateCheckboxes(growth_august);
                        growthCheckboxes.add(8);
                        break;
                    case 9:
                        populateCheckboxes(growth_september);
                        growthCheckboxes.add(9);
                        break;
                    case 10:
                        populateCheckboxes(growth_october);
                        growthCheckboxes.add(10);
                        break;
                    case 11:
                        populateCheckboxes(growth_november);
                        growthCheckboxes.add(11);
                        break;
                    case 12:
                        populateCheckboxes(growth_december);
                        growthCheckboxes.add(12);
                        break;
                }
            }
        }

        //SET LIMIT TO EDIT TEXT
        plant_edit_detail_ph_min.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "14")});
        plant_edit_detail_ph_max.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "14")});

        editPrecipitationTextMax.setText(getResources().getString(R.string.plant_edit_detail_precipitation_max));
        editPrecipitationTextMin.setText(getResources().getString(R.string.plant_edit_detail_precipitation_min));
        editRowSpacingText.setText(getResources().getString(R.string.plant_edit_detail_rowSpacing));
        editSpreadText.setText(getResources().getString(R.string.plant_edit_detail_spread));

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
                R.array.editSoilRichness, android.R.layout.simple_spinner_item);
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
            plant_edit_detail_atmosphericHumidity.setSelection(Integer.parseInt(plant.getAtmosphericHumidity())+1);
        }
        if (plant.getSoilHumidity() != null) {
            plant_edit_detail_soilHumidity.setSelection(Integer.parseInt(plant.getSoilHumidity())+1);
        }

        //SPINNER GROWTHFORM
        ArrayAdapter<CharSequence> adapterGrowthForm = ArrayAdapter.createFromResource(this,
                R.array.growth_form, android.R.layout.simple_spinner_item);
        adapterGrowthForm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plant_edit_detail_growthForm.setAdapter(adapterGrowthForm);
        if (plant.getGrowthForm() != null) {
            plant_edit_detail_growthForm.setSelection(Integer.parseInt(plant.getGrowthForm()));
        }

        //SPINNER ANAEROBIC TOLERANCE
        ArrayAdapter<CharSequence> adapterAnaerobicTolerance = ArrayAdapter.createFromResource(this,
                R.array.anaerobic_tolerance, android.R.layout.simple_spinner_item);
        adapterAnaerobicTolerance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plant_edit_detail_anaerobic.setAdapter(adapterAnaerobicTolerance);
        if (plant.getAnaerobicTolerance() != null) {
            plant_edit_detail_anaerobic.setSelection(Integer.parseInt(plant.getAnaerobicTolerance()));
        }

        //SPINNER GROWTH RATE
        ArrayAdapter<CharSequence> adapaterGrowthRate = ArrayAdapter.createFromResource(this,
                R.array.growth_rate, android.R.layout.simple_spinner_item);
        adapaterGrowthRate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plant_edit_detail_growthRate.setAdapter(adapaterGrowthRate);
        if (plant.getGrowthRate() != null) {
            plant_edit_detail_growthRate.setSelection(Integer.parseInt(plant.getGrowthRate()));
        }

        //SPINNER FOLIAGE TEXTURE
        ArrayAdapter<CharSequence> adapaterFoliage = ArrayAdapter.createFromResource(this,
                R.array.texture_foliage, android.R.layout.simple_spinner_item);
        adapaterFoliage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plant_edit_detail_foliage_texture.setAdapter(adapaterFoliage);
        if (plant.getFoliageTexture() != null) {
            plant_edit_detail_foliage_texture.setSelection(Integer.parseInt(plant.getFoliageTexture()));
        }

        //TEXT VALUES
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

        if (plant.getAverageHeightCm() != null) {
            plant_edit_detail_averageHeight.setText(plant.getAverageHeightCm());
        } else {
            plant_edit_detail_averageHeight.setText("");
        }

        if (plant.getMaximumHeightCm() != null) {
            plant_edit_detail_maxHeight.setText(plant.getMaximumHeightCm());
        } else {
            plant_edit_detail_maxHeight.setText("");
        }

        if (plant.getFlowerColor() != null) {
            plant_edit_detail_flower_color.setText(plant.getFoliageTexture());
        } else {
            plant_edit_detail_flower_color.setText("");
        }

        if (plant.getFruitColor() != null) {
            plant_edit_detail_fruit_color.setText(plant.getFruitColor());
        } else {
            plant_edit_detail_fruit_color.setText("");
        }

        if (plant.getEdiblePart() != null) {
            plant_edit_detail_ediblePart.setText(plant.getEdiblePart());
        } else {
            plant_edit_detail_ediblePart.setText("");
        }

        fab = (ExtendedFloatingActionButton) findViewById(R.id.fab_update);
        fab.setText(getResources().getString(R.string.updateFab));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!plant_edit_detail_bibliography.getText().toString().equals("")){
                    plant.setBibliography(plant_edit_detail_bibliography.getText().toString());
                }
                if(!plant_edit_detail_nickname.getText().toString().equals("")) {
                    plant.setNickname(plant_edit_detail_nickname.getText().toString());
                }
                if(!plant_edit_detail_harvestDays.getText().toString().equals("")) {
                    plant.setDays_to_harvest(plant_edit_detail_harvestDays.getText().toString());
                }
                if(!plant_edit_detail_Precipitation_max.getText().toString().equals("")) {
                    plant.setMaximumPrecipitation(plant_edit_detail_Precipitation_max.getText().toString());
                }
                if(!plant_edit_detail_TemperatureMax.getText().toString().equals("")) {
                    plant.setMaximumTemperature(plant_edit_detail_TemperatureMax.getText().toString());
                }
                if(!plant_edit_detail_Precipitation_min.getText().toString().equals("")) {
                    plant.setMinimumPrecipitation(plant_edit_detail_Precipitation_min.getText().toString());
                }
                if(!plant_edit_detail_minimumRootDepth.getText().toString().equals("")) {
                    plant.setMinimumRootDepth(plant_edit_detail_minimumRootDepth.getText().toString());
                }
                if(!plant_edit_detail_TemperatureMin.getText().toString().equals("")) {
                    plant.setMinimumTemperature(plant_edit_detail_TemperatureMin.getText().toString());
                }
                if(!plant_edit_detail_ph_min.getText().toString().equals("")) {
                    plant.setPhMaximum(plant_edit_detail_ph_min.getText().toString());
                }
                if(!plant_edit_detail_ph_max.getText().toString().equals("")) {
                    plant.setPhMinimum(plant_edit_detail_ph_max.getText().toString());
                }
                if(!plant_edit_detail_rowSpacing.getText().toString().equals("")) {
                    plant.setRowSpacing(plant_edit_detail_rowSpacing.getText().toString());
                }
                if(!plant_edit_detail_sowing.getText().toString().equals("")) {
                    plant.setSowing(plant_edit_detail_sowing.getText().toString());
                }
                if(!plant_edit_detail_spread.getText().toString().equals("")) {
                    plant.setSpread(plant_edit_detail_spread.getText().toString());
                }
                if(!plant_edit_detail_averageHeight.getText().toString().equals("")) {
                    plant.setAverageHeightCm(plant_edit_detail_averageHeight.getText().toString());
                }
                if(!plant_edit_detail_maxHeight.getText().toString().equals("")) {
                    plant.setMaximumHeightCm(plant_edit_detail_maxHeight.getText().toString());
                }
                if(!plant_edit_detail_flower_color.getText().toString().equals("")) {
                    plant.setFlowerColor(plant_edit_detail_flower_color.getText().toString());
                }
                if(!plant_edit_detail_fruit_color.getText().toString().equals("")) {
                    plant.setFruitColor(plant_edit_detail_fruit_color.getText().toString());
                }
                if(!plant_edit_detail_ediblePart.getText().toString().equals("")) {
                    plant.setEdiblePart(plant_edit_detail_ediblePart.getText().toString());
                }

                StringBuilder sb = new StringBuilder();
                plant.setGrowthHabit(sb.toString());
                if(treeBox.isChecked()){
                    sb.append("Tree, ");
                }

                if(forbBox.isChecked()){
                    sb.append("Forb/herb, ");
                }

                if(vineBox.isChecked()){
                    sb.append("Vine, ");
                }

                if(subshrubBox.isChecked()){
                    sb.append("Subshrub, ");
                }

                if(shrubBox.isChecked()){
                    sb.append("Shrub, ");
                }

                if(graminoidBox.isChecked()){
                    sb.append("Graminoid, ");
                }

                if(lichenousBox.isChecked()){
                    sb.append("Lichenous, ");
                }

                if(nonvascularBox.isChecked()){
                    sb.append("Nonvascular, ");
                }

                if(!sb.toString().equals("")){
                    plant.setGrowthHabit(sb.toString().substring(0, sb.toString().length() - 2));
                } else {
                    plant.setGrowthHabit(null);
                }

                if(vegetableBox.isChecked()){
                    plant.setVegetable("true");
                } else {
                    plant.setVegetable(null);
                }

                if(edibleBox.isChecked()){
                    plant.setEdible("true");
                } else {
                    plant.setEdible(null);
                }

                if(flowerConspBox.isChecked()){
                    plant.setFlowerConspicuous("true");
                } else {
                    plant.setFlowerConspicuous(null);
                }

                if(fruitConspBox.isChecked()){
                    plant.setFruitConspicuous("true");
                } else {
                    plant.setFruitConspicuous(null);
                }

                plant.setBloomMonths(jsonConstructor(bloomCheckboxes));
                plant.setFruitMonths(jsonConstructor(fruitCheckboxes));
                plant.setGrowthMonths(jsonConstructor(growthCheckboxes));

                if(plant_edit_detail_light.getSelectedItemPosition() != 0){
                    plant.setLight(String.valueOf(plant_edit_detail_light.getSelectedItemPosition()));
                } else {
                    plant.setLight(null);
                }

                if(plant_edit_detail_soilHumidity.getSelectedItemPosition() != 0){
                    plant.setSoilHumidity(String.valueOf(plant_edit_detail_soilHumidity.getSelectedItemPosition()-1));
                } else {
                    plant.setSoilHumidity(null);
                }

                if(plant_edit_detail_atmosphericHumidity.getSelectedItemPosition() != 0){
                    plant.setAtmosphericHumidity(String.valueOf(plant_edit_detail_atmosphericHumidity.getSelectedItemPosition()-1));
                } else {
                    plant.setAtmosphericHumidity(null);
                }

                if(plant_edit_detail_soilNutriments.getSelectedItemPosition() != 0){
                    plant.setSoilNutriments(String.valueOf(plant_edit_detail_soilNutriments.getSelectedItemPosition()));
                } else {
                    plant.setSoilNutriments(null);
                }

                if(plant_edit_detail_soilSalinity.getSelectedItemPosition() != 0){
                    plant.setSoilSalinity(String.valueOf(plant_edit_detail_soilSalinity.getSelectedItemPosition()));
                } else {
                    plant.setSoilSalinity(null);
                }

                if(plant_edit_detail_growthForm.getSelectedItemPosition() != 0){
                    plant.setGrowthForm(String.valueOf(plant_edit_detail_growthForm.getSelectedItemPosition()));
                } else {
                    plant.setGrowthForm(null);
                }

                if(plant_edit_detail_anaerobic.getSelectedItemPosition() != 0){
                    plant.setAnaerobicTolerance(String.valueOf(plant_edit_detail_anaerobic.getSelectedItemPosition()));
                } else {
                    plant.setAnaerobicTolerance(null);
                }

                if(plant_edit_detail_growthRate.getSelectedItemPosition() != 0){
                    plant.setGrowthRate(String.valueOf(plant_edit_detail_growthRate.getSelectedItemPosition()));
                } else {
                    plant.setGrowthRate(null);
                }

                if(plant_edit_detail_foliage_texture.getSelectedItemPosition() != 0){
                    plant.setFoliageTexture(String.valueOf(plant_edit_detail_foliage_texture.getSelectedItemPosition()));
                } else {
                    plant.setFoliageTexture(null);
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
        boolean unchecked = !((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.bloom_january:
                if (checked)
                    bloomCheckboxes.add(1);
                if(unchecked)
                    bloomCheckboxes.remove(1);
                break;
            case R.id.bloom_february:
                if (checked)
                    bloomCheckboxes.add(2);
                if(unchecked)
                    bloomCheckboxes.remove(2);
                break;
            case R.id.bloom_march:
                if (checked)
                    bloomCheckboxes.add(3);
                if(unchecked)
                    bloomCheckboxes.remove(3);
                break;
            case R.id.bloom_april:
                if (checked)
                    bloomCheckboxes.add(4);
                if(unchecked)
                    bloomCheckboxes.remove(4);
                break;
            case R.id.bloom_may:
                if (checked)
                    bloomCheckboxes.add(5);
                if(unchecked)
                    bloomCheckboxes.remove(5);
                break;
            case R.id.bloom_june:
                if (checked)
                    bloomCheckboxes.add(6);
                if(unchecked)
                    bloomCheckboxes.remove(6);
                break;
            case R.id.bloom_july:
                if (checked)
                    bloomCheckboxes.add(7);
                if(unchecked)
                    bloomCheckboxes.remove(7);
                break;
            case R.id.bloom_august:
                if (checked)
                    bloomCheckboxes.add(8);
                if(unchecked)
                    bloomCheckboxes.remove(8);
                break;
            case R.id.bloom_september:
                if (checked)
                    bloomCheckboxes.add(9);
                if(unchecked)
                    bloomCheckboxes.remove(9);
                break;
            case R.id.bloom_october:
                if (checked)
                    bloomCheckboxes.add(10);
                if(unchecked)
                    bloomCheckboxes.remove(10);
                break;
            case R.id.bloom_november:
                if (checked)
                    bloomCheckboxes.add(11);
                if(unchecked)
                    bloomCheckboxes.remove(11);
                break;
            case R.id.bloom_december:
                if (checked)
                    bloomCheckboxes.add(12);
                if(unchecked)
                    bloomCheckboxes.remove(12);
                break;
            case R.id.growth_january:
                if (checked)
                    growthCheckboxes.add(1);
                if(unchecked)
                    growthCheckboxes.remove(1);
                break;
            case R.id.growth_february:
                if (checked)
                    growthCheckboxes.add(2);
                if(unchecked)
                    growthCheckboxes.remove(2);
                break;
            case R.id.growth_march:
                if (checked)
                    growthCheckboxes.add(3);
                if(unchecked)
                    growthCheckboxes.remove(3);
                break;
            case R.id.growth_april:
                if (checked)
                    growthCheckboxes.add(4);
                if(unchecked)
                    growthCheckboxes.remove(4);
                break;
            case R.id.growth_may:
                if (checked)
                    growthCheckboxes.add(5);
                if(unchecked)
                    growthCheckboxes.remove(5);
                break;
            case R.id.growth_june:
                if (checked)
                    growthCheckboxes.add(6);
                if(unchecked)
                    growthCheckboxes.remove(6);
                break;
            case R.id.growth_july:
                if (checked)
                    growthCheckboxes.add(7);
                if(unchecked)
                    growthCheckboxes.remove(7);
                break;
            case R.id.growth_august:
                if (checked)
                    growthCheckboxes.add(8);
                if(unchecked)
                    growthCheckboxes.remove(8);
                break;
            case R.id.growth_september:
                if (checked)
                    growthCheckboxes.add(9);
                if(unchecked)
                    growthCheckboxes.remove(9);
                break;
            case R.id.growth_october:
                if (checked)
                    growthCheckboxes.add(10);
                if(unchecked)
                    growthCheckboxes.remove(10);
                break;
            case R.id.growth_november:
                if (checked)
                    growthCheckboxes.add(11);
                if(unchecked)
                    growthCheckboxes.remove(11);
                break;
            case R.id.growth_december:
                if (checked)
                    growthCheckboxes.add(12);
                if(unchecked)
                    growthCheckboxes.remove(12);
                break;
            case R.id.fruit_january:
                if (checked)
                    fruitCheckboxes.add(1);
                if(unchecked)
                    fruitCheckboxes.remove(1);
                break;
            case R.id.fruit_february:
                if (checked)
                    fruitCheckboxes.add(2);
                if(unchecked)
                    fruitCheckboxes.remove(2);
                break;
            case R.id.fruit_march:
                if (checked)
                    fruitCheckboxes.add(3);
                if(unchecked)
                    fruitCheckboxes.remove(3);
                break;
            case R.id.fruit_april:
                if (checked)
                    fruitCheckboxes.add(4);
                if(unchecked)
                    fruitCheckboxes.remove(4);
                break;
            case R.id.fruit_may:
                if (checked)
                    fruitCheckboxes.add(5);
                if(unchecked)
                    fruitCheckboxes.remove(5);
                break;
            case R.id.fruit_june:
                if (checked)
                    fruitCheckboxes.add(6);
                if(unchecked)
                    fruitCheckboxes.remove(6);
                break;
            case R.id.fruit_july:
                if (checked)
                    fruitCheckboxes.add(7);
                if(unchecked)
                    fruitCheckboxes.remove(7);
                break;
            case R.id.fruit_august:
                if (checked)
                    fruitCheckboxes.add(8);
                if(unchecked)
                    fruitCheckboxes.remove(8);
                break;
            case R.id.fruit_september:
                if (checked)
                    fruitCheckboxes.add(9);
                if(unchecked)
                    fruitCheckboxes.remove(9);
                break;
            case R.id.fruit_october:
                if (checked)
                    fruitCheckboxes.add(10);
                if(unchecked)
                    fruitCheckboxes.remove(10);
                break;
            case R.id.fruit_november:
                if (checked)
                    fruitCheckboxes.add(11);
                if(unchecked)
                    fruitCheckboxes.remove(11);
                break;
            case R.id.fruit_december:
                if (checked)
                    fruitCheckboxes.add(12);
                if(unchecked)
                    fruitCheckboxes.remove(12);
                break;
        }
    }

    private String jsonConstructor(HashSet hashSet){
        String jsonString = "";
        if(hashSet.size() > 0){
            jsonString = "[";
            Iterator<Integer> it = hashSet.iterator();
            while (it.hasNext()){
                jsonString += it.next()+",";
            }
        } else {
            return "[]";
        }
        jsonString = removeLastComma(jsonString);
        return jsonString += "]";
    }

    private String removeLastComma(String str){
        return new StringBuilder(str)
                .deleteCharAt(str.length() - 1)
                .toString();
    }

    private ArrayList<Integer> jsonArrayToArrayListConverter(String json){
        ArrayList<Integer> stringArray = new ArrayList<Integer>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                stringArray.add(jsonArray.getInt(i));
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
