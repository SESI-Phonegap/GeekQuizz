package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimeResponse {
    @SerializedName("animes")
    @Expose
    private List<Anime> animes = null;

}
