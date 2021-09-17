package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {
    @SerializedName("idScore")
    @Expose
    private String idScore;
    @SerializedName("score")
    @Expose
    private String puntos;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("idAnime")
    @Expose
    private String idAnime;
    @SerializedName("idUser")
    @Expose
    private String idUser;

}
