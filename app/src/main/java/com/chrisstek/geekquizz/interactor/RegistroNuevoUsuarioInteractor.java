package com.chrisstek.geekquizz.interactor;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class RegistroNuevoUsuarioInteractor {
    private QuizzServiceClient quizServiceClient;

    public RegistroNuevoUsuarioInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public Observable<UpdateResponse> registroNuevoUsuario(String userNameFriend, String username, String nombre, String email, int edad, String genero, String password){
        return quizServiceClient.registroNuevoUsuario(userNameFriend,username,nombre,email,edad,genero,password);
    }

    public Flowable<LoginResponse> login(String userName, String pass){
        return quizServiceClient.login(userName,pass);
    }
}
