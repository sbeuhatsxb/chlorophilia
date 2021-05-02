package com.chlorophilia.ui.entities;

import java.io.Serializable;
import java.util.Date;

public class Plant implements Serializable {

    private long id;
    private String nickname;
    private String common_name;
    private String scientific_name;
    private String family;
    private String bibliography;
    private int img;
    private String sowing;
    private String days_to_harvest;
    private String phMaximum;
    private String phMinimum;
    private String light;
    private String atmosphericHumidity;
    private String growthMonths;
    private String bloomMonths;
    private String fruitMonths;
    private String soilNutriments;
    private String soilSalinity;
    private String soilHumidity;
    private String spread;
    private String rowSpacing;
    private String minimumRootDepth;
    private String minimumPrecipitation;
    private String maximumPrecipitation;
    private String minimumTemperature;
    private String maximumTemperature;
    private String rootDepthMeasure;
    private String spreadMeasure;
    private String rowSpacingMeasure;
    private String precipitationMeasure;
    private String temperatureMeasure;
    private String filenameCustomPicture;
    private Date createdAt;
    private Date wateredAt;

    public Plant() {
    }

    public Plant(String nickname, String common_name, int img) {
        this.nickname = nickname;
        this.common_name = common_name;
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }


    public String getScientific_name() {
        return scientific_name;
    }

    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getSowing() {
        return sowing;
    }

    public void setSowing(String sowing) {
        this.sowing = sowing;
    }

    public String getDays_to_harvest() {
        return days_to_harvest;
    }

    public void setDays_to_harvest(String days_to_harvest) {
        this.days_to_harvest = days_to_harvest;
    }

    public String getPhMaximum() {
        return phMaximum;
    }

    public void setPhMaximum(String phMaximum) {
        this.phMaximum = phMaximum;
    }

    public String getPhMinimum() {
        return phMinimum;
    }

    public void setPhMinimum(String phMinimum) {
        this.phMinimum = phMinimum;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getAtmosphericHumidity() {
        return atmosphericHumidity;
    }

    public void setAtmosphericHumidity(String atmosphericHumidity) {
        this.atmosphericHumidity = atmosphericHumidity;
    }

    public String getGrowthMonths() {
        return growthMonths;
    }

    public void setGrowthMonths(String growthMonths) {
        this.growthMonths = growthMonths;
    }

    public String getBloomMonths() {
        return bloomMonths;
    }

    public void setBloomMonths(String bloomMonths) {
        this.bloomMonths = bloomMonths;
    }

    public String getFruitMonths() {
        return fruitMonths;
    }

    public void setFruitMonths(String fruitMonths) {
        this.fruitMonths = fruitMonths;
    }

    public String getSoilNutriments() {
        return soilNutriments;
    }

    public void setSoilNutriments(String soilNutriments) {
        this.soilNutriments = soilNutriments;
    }

    public String getSoilSalinity() {
        return soilSalinity;
    }

    public void setSoilSalinity(String soilSalinity) {
        this.soilSalinity = soilSalinity;
    }

    public String getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(String soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public String getSpread() {
        return spread;
    }

    public void setSpread(String spread) {
        this.spread = spread;
    }

    public String getRowSpacing() {
        return rowSpacing;
    }

    public void setRowSpacing(String rowSpacing) {
        this.rowSpacing = rowSpacing;
    }

    public String getMinimumPrecipitation() {
        return minimumPrecipitation;
    }

    public void setMinimumPrecipitation(String minimumPrecipitation) {
        this.minimumPrecipitation = minimumPrecipitation;
    }

    public String getMaximumPrecipitation() {
        return maximumPrecipitation;
    }

    public void setMaximumPrecipitation(String maximumPrecipitation) {
        this.maximumPrecipitation = maximumPrecipitation;
    }

    public String getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(String minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public String getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(String maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public String getSpreadMeasure() {
        return spreadMeasure;
    }

    public void setSpreadMeasure(String spreadMeasure) {
        this.spreadMeasure = spreadMeasure;
    }

    public String getRowSpacingMeasure() {
        return rowSpacingMeasure;
    }

    public void setRowSpacingMeasure(String rowSpacingMeasure) {
        this.rowSpacingMeasure = rowSpacingMeasure;
    }

    public String getPrecipitationMeasure() {
        return precipitationMeasure;
    }

    public void setPrecipitationMeasure(String precipitationMeasure) {
        this.precipitationMeasure = precipitationMeasure;
    }

    public String getTemperatureMeasure() {
        return temperatureMeasure;
    }

    public void setTemperatureMeasure(String temperatureMeasure) {
        this.temperatureMeasure = temperatureMeasure;
    }

    public String getMinimumRootDepth() {
        return minimumRootDepth;
    }

    public void setMinimumRootDepth(String minimumRootDepth) {
        this.minimumRootDepth = minimumRootDepth;
    }

    public String getRootDepthMeasure() {
        return rootDepthMeasure;
    }

    public void setRootDepthMeasure(String rootDepthMeasure) {
        this.rootDepthMeasure = rootDepthMeasure;
    }

    public String getFilenameCustomPicture() {
        return filenameCustomPicture;
    }

    public void setFilenameCustomPicture(String filenameCustomPicture) {
        this.filenameCustomPicture = filenameCustomPicture;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getWateredAt() {
        return wateredAt;
    }

    public void setWateredAt(Date wateredAt) {
        this.wateredAt = wateredAt;
    }
}
