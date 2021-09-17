package com.chrisstek.geekquizz.interactor;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.client.model.User;

import java.util.List;

import io.reactivex.Observable;

public class FriendsInteractor {
    private QuizzServiceClient quizServiceClient;

    public FriendsInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public Observable<List<User>> searchFriendsByUserName(String userName, String pass, String userNameQuery){
        return quizServiceClient.searchFriendByUserName(userName,pass,userNameQuery);
    }

    public Observable<UpdateResponse> addFriendById(String userName, String pass, int idUser, int idFriend){
        return quizServiceClient.addFrienById(userName,pass,idUser,idFriend);
    }
}
