package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Anime {
    @SerializedName("idAnime")
    @Expose
    private int idAnime;
    @SerializedName("anime")
    @Expose
    private String name;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;

}
