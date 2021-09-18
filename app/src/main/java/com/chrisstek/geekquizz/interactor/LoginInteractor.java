package com.chrisstek.geekquizz.interactor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class LoginInteractor {
    private QuizzServiceClient quizServiceClient;

    public LoginInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public LiveData<LoginResponse> onLogin(String userName, String pass){
        return LiveDataReactiveStreams.fromPublisher(quizServiceClient.login(userName, pass));
    }

    public Observable<UpdateResponse> updateGems(String userName, String pass, int idUser, int gemas ){
        return quizServiceClient.updateGemas(userName,pass,idUser,gemas);
    }

    public Flowable<UpdateResponse> updateAvatar(String userName,String pass, int idUser, String b64){
        return quizServiceClient.updateAvatar(userName,pass,idUser,b64);
    }

    public Observable<UpdateResponse> registroNuevoUsuario(String userNameFriend,String username,String nombre,String email,int edad,String genero,String password){
        return quizServiceClient.registroNuevoUsuario(userNameFriend,username,nombre,email,edad,genero,password);
    }

    public LiveData<LoginResponse> validaUsuarioFacebook(String idFaceBook){
        return LiveDataReactiveStreams.fromPublisher(quizServiceClient.validaUsuarioFacebook(idFaceBook));
    }

    public Observable<UpdateResponse> updateEsferas(String userName, String pass, int idUser, int esferas ){
        return quizServiceClient.updateEsferas(userName,pass,idUser,esferas);
    }
}
