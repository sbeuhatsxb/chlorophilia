package com.chlorophilia.ui.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chlorophilia.R;
import com.chlorophilia.ui.entities.Plant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class PlantDataHandler extends SQLiteOpenHelper {

    //Since 0 is a value for this Database, and since Java manages 0 as NULL, we assume to record everything in string
    //Except for data managed by Android or Java directly
    private static final String CREATE_DATABASE_PLANT =
            "CREATE TABLE plant (id INTEGER PRIMARY KEY ASC, nickname TEXT, common_name TEXT, scientific_name TEXT, family TEXT, bibliography TEXT, defaultImage INT, sowing TEXT, days_to_harvest TEXT, ph_maximum TEXT, ph_minimum TEXT, light TEXT, atmospheric_humidity TEXT, growth_months TEXT, bloom_months TEXT, fruit_months TEXT, soil_nutriments TEXT, soil_salinity TEXT, soil_humidity TEXT, spread TEXT, row_spacing TEXT, minimum_precipitation TEXT, maximum_precipitation TEXT, minimum_temperature TEXT, maximum_temperature TEXT, minimum_root_depth TEXT, root_depth_measure TEXT, spread_measure TEXT, row_spacing_measure TEXT, precipitation_measure TEXT, temperature_measure TEXT, filenameCustomPicture TEXT, created_at TEXT, watered_at TEXT, cancel_watered_at TEXT, growthForm TEXT, growthHabit TEXT, growthRate TEXT, ediblePart TEXT, vegetable TEXT, edible TEXT, anaerobicTolerance TEXT, averageHeightCm TEXT, maximumHeightCm TEXT, urlPowo TEXT, urlPlantnet TEXT, urlGbif TEXT, urlWikipediaEn TEXT);";

    private static final String DROP_DATABASE_PLANT_TABLE = "DROP TABLE IF EXISTS plant;";

    //private static final String COUNT_PLANT_TABLE = "SELECT COUNT(*) FROM ?";

    private static final String DELETE_PLANT_FROM_DATABASE = "DELETE FROM plant WHERE id=";

    private static final String UPDATE_PLANT_FROM_DATABASE = "UPDATE plant SET ";


    public PlantDataHandler(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE_PLANT);
    }

    public void destroyAndRecreate() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(DROP_DATABASE_PLANT_TABLE);
        this.onCreate(db);
    }

    public long insertInto(Plant plant) {

        String nickname = plant.getNickname();
        String common_name = plant.getCommon_name();
        String bibliography = plant.getBibliography();
        String scientific_name = plant.getScientific_name();
        String family = plant.getFamily();
        String sowing = plant.getSowing();
        String days_to_harvest = plant.getDays_to_harvest();
        String ph_maximum = plant.getPhMaximum();
        String ph_minimum = plant.getPhMinimum();
        String light = plant.getLight();
        String atmospheric_humidity = plant.getAtmosphericHumidity();
        String growth_months = plant.getGrowthMonths();
        String bloom_months = plant.getBloomMonths();
        String fruit_months = plant.getFruitMonths();
        String soil_nutriments = plant.getSoilNutriments();
        String soil_salinity = plant.getSoilSalinity();
        String soil_humidity = plant.getSoilHumidity();
        String spread = plant.getSpread();
        String row_spacing = plant.getRowSpacing();
        String minimum_precipitation = plant.getMinimumPrecipitation();
        String maximum_precipitation = plant.getMaximumPrecipitation();
        String minimum_temperature = plant.getMinimumTemperature();
        String maximum_temperature = plant.getMaximumTemperature();
        String minimum_root_depth = plant.getMinimumRootDepth();
        String root_depth_measure = plant.getRootDepthMeasure();
        String spread_measure = plant.getSpreadMeasure();
        String row_spacing_measure = plant.getRowSpacingMeasure();
        String precipitation_measure = plant.getPrecipitationMeasure();
        String temperature_measure = plant.getTemperatureMeasure();
        //API V2
        String growthForm = plant.getGrowthForm();
        String growthHabit = plant.getGrowthHabit();
        String growthRate = plant.getGrowthRate();
        String ediblePart = plant.getEdiblePart();
        String vegetable = plant.getVegetable();
        String edible = plant.getEdible();
        String anaerobicTolerance = plant.getAnaerobicTolerance();
        String averageHeightCm = plant.getAverageHeightCm();
        String maximumHeightCm = plant.getMaximumHeightCm();
        String urlPowo = plant.getUrlPowo();
        String urlPlantnet = plant.getUrlPlantnet();
        String urlGbif = plant.getUrlGbif();
        String urlWikipediaEn = plant.getUrlWikipediaEn();

        int[] drawables = {
                R.drawable.rand_plant_1,
                R.drawable.rand_plant_2,
                R.drawable.rand_plant_3,
                R.drawable.rand_plant_4,
                R.drawable.rand_plant_5,
                R.drawable.rand_plant_6,
                R.drawable.rand_plant_7,
                R.drawable.rand_plant_8
        };
        int rand = new Random().nextInt(7);
        int defaultImage = drawables[rand];
        //For next intent
        plant.setImg(defaultImage);

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nickname", nickname);
        values.put("common_name", common_name);
        values.put("scientific_name", scientific_name);
        values.put("family", family);
        values.put("bibliography", bibliography);
        values.put("sowing", sowing);
        values.put("defaultImage", defaultImage);
        values.put("days_to_harvest", days_to_harvest);
        values.put("ph_minimum", ph_minimum);
        values.put("ph_maximum", ph_maximum);
        values.put("light", light);
        values.put("atmospheric_humidity", atmospheric_humidity);
        values.put("growth_months", growth_months);
        values.put("bloom_months", bloom_months);
        values.put("fruit_months", fruit_months);
        values.put("soil_nutriments", soil_nutriments);
        values.put("soil_salinity", soil_salinity);
        values.put("soil_humidity", soil_humidity);
        values.put("spread", spread);
        values.put("row_spacing", row_spacing);
        values.put("minimum_precipitation", minimum_precipitation);
        values.put("maximum_precipitation", maximum_precipitation);
        values.put("minimum_temperature", minimum_temperature);
        values.put("maximum_temperature", maximum_temperature);
        values.put("minimum_root_depth", minimum_root_depth);
        values.put("root_depth_measure", root_depth_measure);
        values.put("spread_measure", spread_measure);
        values.put("row_spacing_measure", row_spacing_measure);
        values.put("precipitation_measure", precipitation_measure);
        values.put("temperature_measure", temperature_measure);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        values.put("created_at", date);
        //API V2
        values.put("growthForm", growthForm);
        values.put("growthHabit", growthHabit);
        values.put("growthRate", growthRate);
        values.put("ediblePart", ediblePart);
        values.put("vegetable", vegetable);
        values.put("edible", edible);
        values.put("anaerobicTolerance", anaerobicTolerance);
        values.put("averageHeightCm", averageHeightCm);
        values.put("maximumHeightCm", maximumHeightCm);
        values.put("urlPowo", urlPowo);
        values.put("urlPlantnet", urlPlantnet);
        values.put("urlGbif", urlGbif);
        values.put("urlWikipediaEn", urlWikipediaEn);

        long id = database.insert("plant", null, values);
        database.close();
        return id;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_DATABASE_PLANT_TABLE);
        onCreate(db);
    }

    public ArrayList<Plant> getPlants() {
        ArrayList<Plant> plantList = new ArrayList<Plant>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM plant";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            do {
                Plant plant = new Plant();
                plant.setId(cursor.getInt(cursor.getColumnIndex("id")));
                plant.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
                plant.setCommon_name(cursor.getString(cursor.getColumnIndex("common_name")));
                plant.setScientific_name(cursor.getString(cursor.getColumnIndex("scientific_name")));
                plant.setFamily(cursor.getString(cursor.getColumnIndex("family")));
                plant.setBibliography(cursor.getString(cursor.getColumnIndex("bibliography")));
                plant.setImg(cursor.getInt(cursor.getColumnIndex("defaultImage")));
                plant.setSowing(cursor.getString(cursor.getColumnIndex("sowing")));
                plant.setDays_to_harvest(cursor.getString(cursor.getColumnIndex("days_to_harvest")));
                plant.setPhMaximum(cursor.getString(cursor.getColumnIndex("ph_maximum")));
                plant.setPhMinimum(cursor.getString(cursor.getColumnIndex("ph_minimum")));
                plant.setLight(cursor.getString(cursor.getColumnIndex("light")));
                plant.setAtmosphericHumidity(cursor.getString(cursor.getColumnIndex("atmospheric_humidity")));
                plant.setGrowthMonths(cursor.getString(cursor.getColumnIndex("growth_months")));
                plant.setBloomMonths(cursor.getString(cursor.getColumnIndex("bloom_months")));
                plant.setFruitMonths(cursor.getString(cursor.getColumnIndex("fruit_months")));
                plant.setSoilNutriments(cursor.getString(cursor.getColumnIndex("soil_nutriments")));
                plant.setSoilSalinity(cursor.getString(cursor.getColumnIndex("soil_salinity")));
                plant.setSoilHumidity(cursor.getString(cursor.getColumnIndex("soil_humidity")));
                plant.setSpread(cursor.getString(cursor.getColumnIndex("spread")));
                plant.setRowSpacing(cursor.getString(cursor.getColumnIndex("row_spacing")));
                plant.setMinimumPrecipitation(cursor.getString(cursor.getColumnIndex("minimum_precipitation")));
                plant.setMaximumPrecipitation(cursor.getString(cursor.getColumnIndex("maximum_precipitation")));
                plant.setMinimumTemperature(cursor.getString(cursor.getColumnIndex("minimum_temperature")));
                plant.setMaximumTemperature(cursor.getString(cursor.getColumnIndex("maximum_temperature")));
                plant.setMinimumRootDepth(cursor.getString(cursor.getColumnIndex("minimum_root_depth")));
                plant.setRootDepthMeasure(cursor.getString(cursor.getColumnIndex("root_depth_measure")));
                plant.setSpreadMeasure(cursor.getString(cursor.getColumnIndex("spread_measure")));
                plant.setRowSpacingMeasure(cursor.getString(cursor.getColumnIndex("row_spacing_measure")));
                plant.setPrecipitationMeasure(cursor.getString(cursor.getColumnIndex("precipitation_measure")));
                plant.setTemperatureMeasure(cursor.getString(cursor.getColumnIndex("temperature_measure")));
                plant.setFilenameCustomPicture(cursor.getString(cursor.getColumnIndex("filenameCustomPicture")));
                //API V2
                plant.setGrowthForm(cursor.getString(cursor.getColumnIndex("growthForm")));
                plant.setGrowthHabit(cursor.getString(cursor.getColumnIndex("growthHabit")));
                plant.setGrowthRate(cursor.getString(cursor.getColumnIndex("growthRate")));
                plant.setEdiblePart(cursor.getString(cursor.getColumnIndex("ediblePart")));
                plant.setVegetable(cursor.getString(cursor.getColumnIndex("vegetable")));
                plant.setEdible(cursor.getString(cursor.getColumnIndex("edible")));
                plant.setAnaerobicTolerance(cursor.getString(cursor.getColumnIndex("anaerobicTolerance")));
                plant.setAverageHeightCm(cursor.getString(cursor.getColumnIndex("averageHeightCm")));
                plant.setMaximumHeightCm(cursor.getString(cursor.getColumnIndex("maximumHeightCm")));
                plant.setUrlPowo(cursor.getString(cursor.getColumnIndex("urlPowo")));
                plant.setUrlPlantnet(cursor.getString(cursor.getColumnIndex("urlPlantnet")));
                plant.setUrlGbif(cursor.getString(cursor.getColumnIndex("urlGbif")));
                plant.setUrlWikipediaEn(cursor.getString(cursor.getColumnIndex("urlWikipediaEn")));

                String dateFromSqlite = cursor.getString(cursor.getColumnIndex("created_at"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(dateFromSqlite);
                    plant.setCreatedAt(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                plantList.add(plant);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return plantList;
    }

    public void deletePlant(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(DELETE_PLANT_FROM_DATABASE + id);
        db.close();
    }

    public void oldUpdatePlant(long id, String field, String fieldValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = UPDATE_PLANT_FROM_DATABASE + field + " = " + "'" + fieldValue + "'" + " WHERE id=" + id;
        db.execSQL(query);
        db.close();
    }

    public void updatePlant(long id, String field, String fieldValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(field, fieldValue);
        db.update("plant", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updatePlant(Plant plant) {
        SQLiteDatabase db = this.getWritableDatabase();
        String nickname = plant.getNickname();
        String common_name = plant.getCommon_name();
        String bibliography = plant.getBibliography();
        String scientific_name = plant.getScientific_name();
        String family = plant.getFamily();
        String sowing = plant.getSowing();
        String days_to_harvest = plant.getDays_to_harvest();
        String ph_maximum = plant.getPhMaximum();
        String ph_minimum = plant.getPhMinimum();
        String light = plant.getLight();
        String atmospheric_humidity = plant.getAtmosphericHumidity();
        String growth_months = plant.getGrowthMonths();
        String bloom_months = plant.getBloomMonths();
        String fruit_months = plant.getFruitMonths();
        String soil_nutriments = plant.getSoilNutriments();
        String soil_salinity = plant.getSoilSalinity();
        String soil_humidity = plant.getSoilHumidity();
        String spread = plant.getSpread();
        String row_spacing = plant.getRowSpacing();
        String minimum_precipitation = plant.getMinimumPrecipitation();
        String maximum_precipitation = plant.getMaximumPrecipitation();
        String minimum_temperature = plant.getMinimumTemperature();
        String maximum_temperature = plant.getMaximumTemperature();
        String minimum_root_depth = plant.getMinimumRootDepth();
        String root_depth_measure = plant.getRootDepthMeasure();
        String spread_measure = plant.getSpreadMeasure();
        String row_spacing_measure = plant.getRowSpacingMeasure();
        String precipitation_measure = plant.getPrecipitationMeasure();
        String temperature_measure = plant.getTemperatureMeasure();
        //API V2
        String growthForm = plant.getGrowthForm();
        String growthHabit = plant.getGrowthHabit();
        String growthRate = plant.getGrowthRate();
        String ediblePart = plant.getEdiblePart();
        String vegetable = plant.getVegetable();
        String edible = plant.getEdible();
        String anaerobicTolerance = plant.getAnaerobicTolerance();
        String averageHeightCm = plant.getAverageHeightCm();
        String maximumHeightCm = plant.getMaximumHeightCm();
        String urlPowo = plant.getUrlPowo();
        String urlPlantnet = plant.getUrlPlantnet();
        String urlGbif = plant.getUrlGbif();
        String urlWikipediaEn = plant.getUrlWikipediaEn();


        ContentValues values = new ContentValues();
        if(!nickname.equals("")){
            values.put("nickname", nickname);
        } else {
            values.putNull("nickname");
        }
        if(!common_name.equals("")){
            values.put("common_name", common_name);
        } else {
            values.putNull("common_name");
        }
        if(!scientific_name.equals("")){
            values.put("scientific_name", scientific_name);
        } else {
            values.putNull("scientific_name");
        }
        if(!family.equals("")){
            values.put("family", family);
        } else {
            values.putNull("family");
        }
        if(!bibliography.equals("")){
            values.put("bibliography", bibliography);
        } else {
            values.putNull("bibliography");
        }
        if(!sowing.equals("")){
            values.put("sowing", sowing);
        } else {
            values.putNull("sowing");
        }
        if(!days_to_harvest.equals("")){
            values.put("days_to_harvest", days_to_harvest);
        } else {
            values.putNull("days_to_harvest");
        }
        if(!ph_minimum.equals("")){
            values.put("ph_minimum", ph_minimum);
        } else {
            values.putNull("ph_minimum");
        }
        if(!ph_maximum.equals("")){
            values.put("ph_maximum", ph_maximum);
        } else {
            values.putNull("ph_maximum");
        }
        if(!light.equals("")){
            values.put("light", light);
        } else {
            values.putNull("light");
        }
        if(!atmospheric_humidity.equals("")){
            values.put("atmospheric_humidity", atmospheric_humidity);
        } else {
            values.putNull("atmospheric_humidity");
        }
        if(!growth_months.equals("")){
            values.put("growth_months", growth_months);
        } else {
            values.putNull("growth_months");
        }
        if(!bloom_months.equals("")){
            values.put("bloom_months", bloom_months);
        } else {
            values.putNull("bloom_months");
        }
        if(!fruit_months.equals("")){
            values.put("fruit_months", fruit_months);
        } else {
            values.putNull("fruit_months");
        }
        if(!soil_nutriments.equals("")){
            values.put("soil_nutriments", soil_nutriments);
        } else {
            values.putNull("soil_nutriments");
        }
        if(!soil_salinity.equals("")){
            values.put("soil_salinity", soil_salinity);
        } else {
            values.putNull("soil_salinity");
        }
        if(!soil_humidity.equals("")){
            values.put("soil_humidity", soil_humidity);
        } else {
            values.putNull("soil_humidity");
        }
        if(!spread.equals("")){
            values.put("spread", spread);
        } else {
            values.putNull("spread");
        }
        if(!row_spacing.equals("")){
            values.put("row_spacing", row_spacing);
        } else {
            values.putNull("row_spacing");
        }
        if(!minimum_precipitation.equals("")){
            values.put("minimum_precipitation", minimum_precipitation);
        } else {
            values.putNull("minimum_precipitation");
        }
        if(!maximum_precipitation.equals("")){
            values.put("maximum_precipitation", maximum_precipitation);
        } else {
            values.putNull("maximum_precipitation");
        }
        if(!minimum_temperature.equals("")){
            values.put("minimum_temperature", minimum_temperature);
        } else {
            values.putNull("minimum_temperature");
        }
        if(!maximum_temperature.equals("")){
            values.put("maximum_temperature", maximum_temperature);
        } else {
            values.putNull("maximum_temperature");
        }
        if(!minimum_root_depth.equals("")){
            values.put("minimum_root_depth", minimum_root_depth);
        } else {
            values.putNull("minimum_root_depth");
        }
        if(!root_depth_measure.equals("")){
            values.put("root_depth_measure", root_depth_measure);
        } else {
            values.putNull("root_depth_measure");
        }
        if(!spread_measure.equals("")){
            values.put("spread_measure", spread_measure);
        } else {
            values.putNull("spread_measure");
        }
        if(!row_spacing_measure.equals("")){
            values.put("row_spacing_measure", row_spacing_measure);
        } else {
            values.putNull("row_spacing_measure");
        }
        if(!precipitation_measure.equals("")){
            values.put("precipitation_measure", precipitation_measure);
        } else {
            values.putNull("precipitation_measure");
        }
        if(!temperature_measure.equals("")){
            values.put("temperature_measure", temperature_measure);
        } else {
            values.putNull("temperature_measure");
        }
        //API V2
        if(!growthForm.equals("")) {
            values.put("growthForm", growthForm);
        } else {
            values.putNull("growthForm");
        }
        if(!growthHabit.equals("")) {
            values.put("growthHabit", growthHabit);
        } else {
            values.putNull("growthHabit");
        }
        if(!growthRate.equals("")) {
        values.put("growthRate", growthRate);
        } else {
            values.putNull("growthRate");
        }
        if(!ediblePart.equals("")) {
        values.put("ediblePart", ediblePart);
        } else {
            values.putNull("ediblePart");
        }
        if(!vegetable.equals("")) {
        values.put("vegetable", vegetable);
        } else {
            values.putNull("vegetable");
        }
        if(!edible.equals("")) {
        values.put("edible", edible);
        } else {
            values.putNull("edible");
        }
        if(!anaerobicTolerance.equals("")) {
        values.put("anaerobicTolerance", anaerobicTolerance);
        } else {
            values.putNull("anaerobicTolerance");
        }
        if(!averageHeightCm.equals("")) {
        values.put("averageHeightCm", averageHeightCm);
        } else {
            values.putNull("averageHeightCm");
        }
        if(!maximumHeightCm.equals("")) {
        values.put("maximumHeightCm", maximumHeightCm);
        } else {
            values.putNull("maximumHeightCm");
        }
        if(!urlPowo.equals("")) {
        values.put("urlPowo", urlPowo);
        } else {
            values.putNull("urlPowo");
        }
        if(!urlPlantnet.equals("")) {
        values.put("urlPlantnet", urlPlantnet);
        } else {
            values.putNull("urlPlantnet");
        }
        if(!urlGbif.equals("")) {
            values.put("urlGbif", urlGbif);
        } else {
            values.putNull("urlGbif");
        }
        if(!urlWikipediaEn.equals("")) {
            values.put("urlWikipediaEn", urlWikipediaEn);
        } else {
            values.putNull("urlWikipediaEn");
        }

        long id = plant.getId();
        db.update("plant", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void dateUpdatePlant(long id, String field, Date fieldValue) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        String date = sdf.format(new Date());
        values.put("created_at", date);
        db.update("plant", values, "id = ?", new String[]{String.valueOf(id)});

        db.close();
    }


}