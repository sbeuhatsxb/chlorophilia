package com.chlorophilia.ui.model;

import android.annotation.SuppressLint;
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
            "CREATE TABLE plant (id INTEGER PRIMARY KEY ASC, nickname TEXT, common_name TEXT, scientific_name TEXT, family TEXT, bibliography TEXT, defaultImage INT, sowing TEXT, days_to_harvest TEXT, ph_maximum TEXT, ph_minimum TEXT, light TEXT, atmospheric_humidity TEXT, growth_months TEXT, bloom_months TEXT, fruit_months TEXT, soil_nutriments TEXT, soil_salinity TEXT, soil_humidity TEXT, spread TEXT, row_spacing TEXT, minimum_precipitation TEXT, maximum_precipitation TEXT, minimum_temperature TEXT, maximum_temperature TEXT, minimum_root_depth TEXT, filenameCustomPicture TEXT, created_at TEXT, watered_at TEXT, cancel_watered_at TEXT, growthForm TEXT, growthHabit TEXT, growthRate TEXT, ediblePart TEXT, vegetable TEXT, edible TEXT, anaerobicTolerance TEXT, averageHeightCm TEXT, maximumHeightCm TEXT, urlPowo TEXT, urlPlantnet TEXT, urlGbif TEXT, urlWikipediaEn TEXT, flowerColor TEXT, flowerConspicuous TEXT, foliageColor TEXT, foliageTexture TEXT, fruitColor TEXT, fruitConspicuous TEXT);";

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
        String flowerColor = plant.getFlowerColor();
        String flowerConspicuous = plant.getFlowerConspicuous();
        String foliageColor = plant.getFoliageColor();
        String foliageTexture = plant.getFoliageTexture();
        String fruitColor = plant.getFruitColor();
        String fruitConspicuous = plant.getFruitConspicuous();

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
        values.put("flowerColor", flowerColor);
        values.put("flowerConspicuous", flowerConspicuous);
        values.put("foliageColor", foliageColor);
        values.put("foliageTexture", foliageTexture);
        values.put("fruitColor", fruitColor);
        values.put("fruitConspicuous", fruitConspicuous);
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
                plant.setFlowerColor(cursor.getString(cursor.getColumnIndex("flowerColor")));
                plant.setFlowerConspicuous(cursor.getString(cursor.getColumnIndex("flowerConspicuous")));
                plant.setFoliageColor(cursor.getString(cursor.getColumnIndex("foliageColor")));
                plant.setFoliageTexture(cursor.getString(cursor.getColumnIndex("foliageTexture")));
                plant.setFruitColor(cursor.getString(cursor.getColumnIndex("fruitColor")));
                plant.setFruitConspicuous(cursor.getString(cursor.getColumnIndex("fruitConspicuous")));

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

        ContentValues values = new ContentValues();
        if(plant.getNickname() != null){
            values.put("nickname", plant.getNickname());
        } else {
            values.putNull("nickname");
        }
        if(plant.getCommon_name() != null){
            values.put("common_name", plant.getCommon_name());
        } else {
            values.putNull("common_name");
        }
        if(plant.getScientific_name() != null){
            values.put("scientific_name", plant.getScientific_name());
        } else {
            values.putNull("scientific_name");
        }
        if(plant.getFamily() != null){
            values.put("family", plant.getFamily());
        } else {
            values.putNull("family");
        }
        if(plant.getBibliography() != null){
            values.put("bibliography", plant.getBibliography());
        } else {
            values.putNull("bibliography");
        }
        if(plant.getSowing() != null){
            values.put("sowing", plant.getSowing());
        } else {
            values.putNull("sowing");
        }
        if(plant.getDays_to_harvest() != null){
            values.put("days_to_harvest", plant.getDays_to_harvest());
        } else {
            values.putNull("days_to_harvest");
        }
        if(plant.getPhMinimum() != null){
            values.put("ph_minimum", plant.getPhMinimum());
        } else {
            values.putNull("ph_minimum");
        }
        if(plant.getPhMaximum() != null){
            values.put("ph_maximum", plant.getPhMaximum());
        } else {
            values.putNull("ph_maximum");
        }
        if(plant.getLight() != null){
            values.put("light", plant.getLight());
        } else {
            values.putNull("light");
        }
        if(plant.getAtmosphericHumidity() != null){
            values.put("atmospheric_humidity", plant.getAtmosphericHumidity());
        } else {
            values.putNull("atmospheric_humidity");
        }
        if(plant.getGrowthMonths() != null){
            values.put("growth_months", plant.getGrowthMonths());
        } else {
            values.putNull("growth_months");
        }
        if(plant.getBloomMonths() != null){
            values.put("bloom_months", plant.getBloomMonths());
        } else {
            values.putNull("bloom_months");
        }
        if(plant.getFruitMonths() != null){
            values.put("fruit_months", plant.getFruitMonths());
        } else {
            values.putNull("fruit_months");
        }
        if(plant.getSoilNutriments() != null){
            values.put("soil_nutriments", plant.getSoilNutriments());
        } else {
            values.putNull("soil_nutriments");
        }
        if(plant.getSoilSalinity() != null){
            values.put("soil_salinity", plant.getSoilSalinity());
        } else {
            values.putNull("soil_salinity");
        }
        if(plant.getSoilHumidity() != null){
            values.put("soil_humidity", plant.getSoilHumidity());
        } else {
            values.putNull("soil_humidity");
        }
        if(plant.getSpread() != null){
            values.put("spread", plant.getSpread());
        } else {
            values.putNull("spread");
        }
        if(plant.getRowSpacing() != null){
            values.put("row_spacing", plant.getRowSpacing());
        } else {
            values.putNull("row_spacing");
        }
        if(plant.getMinimumPrecipitation() != null){
            values.put("minimum_precipitation", plant.getMinimumPrecipitation());
        } else {
            values.putNull("minimum_precipitation");
        }
        if(plant.getMaximumPrecipitation() != null){
            values.put("maximum_precipitation", plant.getMaximumPrecipitation());
        } else {
            values.putNull("maximum_precipitation");
        }
        if(plant.getMinimumTemperature() != null){
            values.put("minimum_temperature", plant.getMinimumTemperature());
        } else {
            values.putNull("minimum_temperature");
        }
        if(plant.getMaximumTemperature() != null){
            values.put("maximum_temperature", plant.getMaximumTemperature());
        } else {
            values.putNull("maximum_temperature");
        }
        if(plant.getMinimumRootDepth() != null){
            values.put("minimum_root_depth", plant.getMinimumRootDepth());
        } else {
            values.putNull("minimum_root_depth");
        }
        //API V2
        if(plant.getGrowthForm() != null) {
            values.put("growthForm", plant.getGrowthForm());
        } else {
            values.putNull("growthForm");
        }
        if(plant.getGrowthHabit() != null) {
            values.put("growthHabit", plant.getGrowthHabit());
        } else {
            values.putNull("growthHabit");
        }
        if(plant.getGrowthRate() != null) {
            values.put("growthRate", plant.getGrowthRate());
        } else {
            values.putNull("growthRate");
        }
        if(plant.getEdiblePart() != null) {
            values.put("ediblePart", plant.getEdiblePart());
        } else {
            values.putNull("ediblePart");
        }
        if(plant.getVegetable() != null) {
            values.put("vegetable", plant.getVegetable());
        } else {
            values.putNull("vegetable");
        }
        if(plant.getEdible() != null) {
            values.put("edible", plant.getEdible());
        } else {
            values.putNull("edible");
        }
        if(plant.getAnaerobicTolerance() != null) {
            values.put("anaerobicTolerance", plant.getAnaerobicTolerance());
        } else {
            values.putNull("anaerobicTolerance");
        }
        if(plant.getAverageHeightCm() != null) {
            values.put("averageHeightCm", plant.getAverageHeightCm());
        } else {
            values.putNull("averageHeightCm");
        }
        if(plant.getMaximumHeightCm() != null) {
            values.put("maximumHeightCm", plant.getMaximumHeightCm());
        } else {
            values.putNull("maximumHeightCm");
        }
        if(plant.getUrlPowo() != null) {
            values.put("urlPowo", plant.getUrlPowo());
        } else {
            values.putNull("urlPowo");
        }
        if(plant.getUrlPlantnet() != null) {
            values.put("urlPlantnet", plant.getUrlPlantnet());
        } else {
            values.putNull("urlPlantnet");
        }
        if(plant.getUrlGbif() != null) {
            values.put("urlGbif", plant.getUrlGbif());
        } else {
            values.putNull("urlGbif");
        }
        if(plant.getUrlWikipediaEn() != null) {
            values.put("urlWikipediaEn", plant.getUrlWikipediaEn());
        } else {
            values.putNull("urlWikipediaEn");
        }
        if(plant.getFlowerColor() != null) {
            values.put("flowerColor", plant.getFlowerColor());
        } else {
            values.putNull("flowerColor");
        }
        if(plant.getFlowerConspicuous() != null) {
            values.put("flowerConspicuous", plant.getFlowerConspicuous());
        } else {
            values.putNull("flowerConspicuous");
        }
        if(plant.getFoliageColor() != null) {
            values.put("foliageColor", plant.getFoliageColor());
        } else {
            values.putNull("foliageColor");
        }
        if(plant.getFoliageTexture() != null) {
            values.put("foliageTexture", plant.getFoliageTexture());
        } else {
            values.putNull("foliageTexture");
        }
        if(plant.getFruitColor() != null) {
            values.put("fruitColor", plant.getFruitColor());
        } else {
            values.putNull("fruitColor");
        }
        if(plant.getFruitConspicuous() != null) {
            values.put("fruitConspicuous", plant.getFlowerConspicuous());
        } else {
            values.putNull("fruitConspicuous");
        }

        long id = plant.getId();
        db.update("plant", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void dateUpdatePlant(long id, String field, Date fieldValue) {
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        String date = sdf.format(new Date());
        values.put("created_at", date);
        db.update("plant", values, "id = ?", new String[]{String.valueOf(id)});

        db.close();
    }


}