package com.chrisstek.geekquizz.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Preguntas {
    @SerializedName("idQuestion")
    @Expose
    private String idQuestion;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("puntos")
    @Expose
    private int puntos;
    @SerializedName("arrayRespuestas")
    @Expose
    private List<Respuesta> respuestas = null;

}
