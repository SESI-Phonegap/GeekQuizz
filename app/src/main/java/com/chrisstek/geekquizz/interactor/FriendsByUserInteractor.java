package com.chrisstek.geekquizz.interactor;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.User;

import java.util.List;

import io.reactivex.Observable;

public class FriendsByUserInteractor {
    private QuizzServiceClient quizServiceClient;

    public FriendsByUserInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public Observable<List<User>> getAllFriendsByUser(String userName, String pass){
        return quizServiceClient.getAllFriendsByUser(userName, pass);
    }
}
