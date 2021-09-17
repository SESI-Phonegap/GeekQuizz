package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateResponse {
    @SerializedName("estatus")
    @Expose
    public String estatus;
    @SerializedName("error")
    @Expose
    public String error;
}
