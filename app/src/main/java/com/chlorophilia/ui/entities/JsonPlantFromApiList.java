package com.chlorophilia.ui.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class dedidacted to automaticly convert a Json object gotten from API to a Java Parcelable object
 */
public class JsonPlantFromApiList implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("common_name")
    @Expose
    private String common_name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("scientific_name")
    @Expose
    private String scientific_name;
    @SerializedName("year")
    @Expose
    private int year;
    @SerializedName("bibliography")
    @Expose
    private String bibliography;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("family_common_name")
    @Expose
    private String family_common_name;
    @SerializedName("common_names")
    @Expose
    private String common_names;
    @SerializedName("genus_id")
    @Expose
    private int genus_id;
    @SerializedName("image_url")
    @Expose
    private String image_url;
    @SerializedName("synonyms")
    @Expose
    private List synonyms;
    @SerializedName("genus")
    @Expose
    private String genus;
    @SerializedName("family")
    @Expose
    private String family;
    @SerializedName("links")
    @Expose
    private Object links;


    public JsonPlantFromApiList() {
    }

    protected JsonPlantFromApiList(Parcel in) {
        id = in.readInt();
        common_name = in.readString();
        common_names = in.readString();
        slug = in.readString();
        scientific_name = in.readString();
        year = in.readInt();
        bibliography = in.readString();
        author = in.readString();
        status = in.readString();
        rank = in.readString();
        family_common_name = in.readString();
        genus_id = in.readInt();
        image_url = in.readString();
        genus = in.readString();
        family = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(common_name);
        dest.writeString(common_names);
        dest.writeString(slug);
        dest.writeString(scientific_name);
        dest.writeInt(year);
        dest.writeString(bibliography);
        dest.writeString(author);
        dest.writeString(status);
        dest.writeString(rank);
        dest.writeString(family_common_name);
        dest.writeInt(genus_id);
        StringBuilder sb = new StringBuilder(image_url);
        //Removing http"S" causing certificate errors
        if(sb.charAt(4) == 115){
            sb.deleteCharAt(4);
            dest.writeString(sb.toString());
        } else {
            dest.writeString(image_url);
        }
        dest.writeString(genus);
        dest.writeString(family);
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFamily_common_name() {
        return family_common_name;
    }

    public void setFamily_common_name(String family_common_name) {
        this.family_common_name = family_common_name;
    }

    public int getGenus_id() {
        return genus_id;
    }

    public void setGenus_id(int genus_id) {
        this.genus_id = genus_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {

        this.image_url = image_url;
    }

    public List getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List synonyms) {
        this.synonyms = synonyms;
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

    public Object getLinks() {
        return links;
    }

    public void setLinks(Object links) {
        this.links = links;
    }

    public String getCommon_names() {
        return common_names;
    }

    public void setCommon_names(String common_names) {
        this.common_names = common_names;
    }
}
