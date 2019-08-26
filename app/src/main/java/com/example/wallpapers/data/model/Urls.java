
package com.example.wallpapers.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.example.wallpapers.util.Constants.REGULAR;
import static com.example.wallpapers.util.Constants.SMALL;

public class Urls {

    @SerializedName(REGULAR)
    @Expose
    private String regular;
    @SerializedName(SMALL)
    @Expose
    private String small;

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

}
