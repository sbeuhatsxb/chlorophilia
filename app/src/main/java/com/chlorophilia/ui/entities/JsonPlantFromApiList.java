package com.chlorophilia.ui.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class ONLY dedicated to automaticly convert a Json object gotten from API to a Java Parcelable object
 */
public class JsonPlantFromApiList implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("scientific_name")
    @Expose
    private String scientific_name;

    @SerializedName("common_name")
    @Expose
    private String common_name;

    @SerializedName("genus")
    @Expose
    private String genus;

    @SerializedName("family")
    @Expose
    private String family;

    @Expose
    private String family_common_name;

    @SerializedName("common_names")
    @Expose
    private String common_names;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("edible")
    @Expose
    private String edible;

    @SerializedName("vegetable")
    @Expose
    private String vegetable;

    public JsonPlantFromApiList() {
    }

    protected JsonPlantFromApiList(Parcel in) {
        id = in.readInt();
        scientific_name = in.readString();
        family_common_name = in.readString();
        common_name = in.readString();
        genus = in.readString();
        family = in.readString();
        common_names = in.readString();
        vegetable = in.readString();
        edible = in.readString();
        image_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(scientific_name);
        dest.writeString(family_common_name);
        dest.writeString(common_name);
        dest.writeString(genus);
        dest.writeString(family);
        dest.writeString(common_names);
        dest.writeString(vegetable);
        dest.writeString(edible);
        if(!image_url.equals("")){
            StringBuilder sb = new StringBuilder(image_url);
            //Removing http"S" causing certificate errors
            if(sb.charAt(4) == 115){
                sb.deleteCharAt(4);
                dest.writeString(sb.toString());
            } else {
                dest.writeString(image_url);
            }
        } else {
            dest.writeString(image_url);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JsonPlantFromApiList> CREATOR = new Creator<JsonPlantFromApiList>() {
        @Override
        public JsonPlantFromApiList createFromParcel(Parcel in) {
            return new JsonPlantFromApiList(in);
        }

        @Override
        public JsonPlantFromApiList[] newArray(int size) {
            return new JsonPlantFromApiList[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFamily_common_name() {
        return family_common_name;
    }

    public void setFamily_common_name(String family_common_name) {
        this.family_common_name = family_common_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {

        this.image_url = image_url;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getCommon_names() {
        return common_names;
    }

    public void setCommon_names(String common_names) {
        this.common_names = common_names;
    }

    public String getEdible() {
        return edible;
    }

    public void setEdible(String edible) {
        this.edible = edible;
    }

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }
}
