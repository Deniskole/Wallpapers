
package com.example.wallpapers.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.wallpapers.converters.Conventer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.example.wallpapers.util.Constants.ID;
import static com.example.wallpapers.util.Constants.TABLE_NAME;
import static com.example.wallpapers.util.Constants.URLS;

@Entity(tableName = TABLE_NAME)
@TypeConverters(value = Conventer.class)
public class Result {

    @PrimaryKey(autoGenerate = true)
    private int id_primary;

    @SerializedName(ID)
    @Expose
    private String id;

    @SerializedName(URLS)
    @Expose
    private Urls urls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public int getId_primary() {
        return id_primary;
    }

    public void setId_primary(int id_primary) {
        this.id_primary = id_primary;
    }


}
