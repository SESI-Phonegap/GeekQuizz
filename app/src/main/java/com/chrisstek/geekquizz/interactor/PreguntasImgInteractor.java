package com.chrisstek.geekquizz.interactor;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.Preguntas;
import com.chrisstek.geekquizz.client.model.UpdateResponse;

import java.util.List;

import io.reactivex.Observable;

public class PreguntasImgInteractor {
    private QuizzServiceClient quizServiceClient;

    public PreguntasImgInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public Observable<List<Preguntas>> preguntasByAnimeImg(String userName, String pass, int idAnime){
        return quizServiceClient.getQuestionsByAnimeImg(userName,pass,idAnime);
    }

    public Observable<UpdateResponse> updateEsferas(String userName, String pass, int idUser, int esferas){
        return quizServiceClient.updateEsferas(userName,pass,idUser,esferas);
    }

    public Observable<UpdateResponse> updateLevelScoreGems(String userName, String pass, int gemas, int score, int level, int idUser,
                                                           int idAnime){
        return quizServiceClient.updateLevelScoreGems(userName,pass,gemas,score,level,idUser,idAnime);
    }
}
