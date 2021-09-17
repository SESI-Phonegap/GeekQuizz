package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respuesta {
    @SerializedName("idRespuesta")
    @Expose
    private String idRespuesta;
    @SerializedName("respuesta")
    @Expose
    private String question;
    @SerializedName("isCorrect")
    @Expose
    private String isCorrect;
    @SerializedName("idPregunta")
    @Expose
    private String idPregunta;

}
