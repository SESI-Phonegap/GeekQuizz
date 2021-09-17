package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    @SerializedName("estatus")
    @Expose
    private String estatus;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("user")
    @Expose
    private User user;
}
