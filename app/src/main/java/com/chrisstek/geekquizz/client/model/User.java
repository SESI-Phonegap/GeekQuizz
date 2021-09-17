package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
    @SerializedName("idUser")
    @Expose
    private int idUser;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sexo")
    @Expose
    private String sexo;
    @SerializedName("edad")
    @Expose
    private String edad;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("coins")
    @Expose
    private int coins;
    @SerializedName("totalScore")
    @Expose
    private int totalScore;
    @SerializedName("imgUser")
    @Expose
    private String urlImageUser;
    @SerializedName("esferas")
    @Expose
    private int esferas;
}
