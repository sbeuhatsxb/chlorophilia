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

    private static final String CREATE_DATABASE_PLANT =
            "CREATE TABLE plant (id INTEGER PRIMARY KEY ASC, nickname TEXT, common_name TEXT, scientific_name TEXT, family TEXT, bibliography TEXT, defaultImage INT, sowing TEXT, days_to_harvest TEXT, ph_maximum TEXT, ph_minimum TEXT, light TEXT, atmospheric_humidity TEXT, growth_months TEXT, bloom_months TEXT, fruit_months TEXT, soil_nutriments TEXT, soil_salinity TEXT, soil_humidity TEXT, spread TEXT, row_spacing TEXT, minimum_precipitation TEXT, maximum_precipitation TEXT, minimum_temperature TEXT, maximum_temperature TEXT, minimum_root_depth TEXT, root_depth_measure TEXT, spread_measure TEXT, row_spacing_measure TEXT, precipitation_measure TEXT, temperature_measure TEXT, filenameCustomPicture TEXT, created_at TEXT, watered_at TEXT, cancel_watered_at TEXT);";

    private static final String INSERT_DATABASE_PLANT =
            "INSERT INTO plant (nickname, common_name, scientific_name, family, bibliography, defaultImage, sowing, days_to_harvest, ph_maximum, ph_minimum, light, atmospheric_humidity, growth_months, bloom_months, fruit_months, soil_nutriments, soil_salinity, soil_humidity, spread, row_spacing, minimum_precipitation, maximum_precipitation, minimum_temperature, maximum_temperature, minimum_root_depth, root_depth_measure, spread_measure, row_spacing_measure, precipitation_measure, temperature_measure, temperature_measure, created_at, watered_at) VALUES ";

    private static final String DROP_DATABASE_PLANT_TABLE = "DROP TABLE IF EXISTS plant;";

    private static final String COUNT_PLANT_TABLE = "SELECT COUNT(*) FROM ?";

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

        ContentValues values = new ContentValues();
        values.put("nickname", nickname);
        values.put("common_name", common_name);
        values.put("scientific_name", scientific_name);
        values.put("family", family);
        values.put("bibliography", bibliography);
        values.put("sowing", sowing);
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