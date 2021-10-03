package com.chrisstek.geekquizz.interactor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

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

    public LiveData<UpdateResponse> registroNuevoUsuario(String userNameFriend, String username, String nombre, String email, int edad, String genero, String password){
        return LiveDataReactiveStreams.fromPublisher(quizServiceClient.registroNuevoUsuario(userNameFriend,username,nombre,email,edad,genero,password));
    }

    public LiveData<LoginResponse> login(String userName, String pass){
        return LiveDataReactiveStreams.fromPublisher(quizServiceClient.login(userName,pass));
    }
}
