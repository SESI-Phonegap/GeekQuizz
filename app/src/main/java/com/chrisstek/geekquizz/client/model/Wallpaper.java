package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallpaper {
    @SerializedName("idWallpaper")
    @Expose
    private String idWallpaper;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlExample")
    @Expose
    private String urlExample;
    @SerializedName("idAnime")
    @Expose
    private String idAnime;
    @SerializedName("costo")
    @Expose
    private int costo;
}
