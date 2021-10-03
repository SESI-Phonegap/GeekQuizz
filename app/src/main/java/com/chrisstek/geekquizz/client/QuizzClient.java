package com.chrisstek.geekquizz.client;

import com.chrisstek.geekquizz.client.model.Anime;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.Preguntas;
import com.chrisstek.geekquizz.client.model.ScoreResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.client.model.User;
import com.chrisstek.geekquizz.client.model.Wallpaper;
import com.chrisstek.geekquizz.client.retrofit.QuizzRetrofitClient;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QuizzClient extends QuizzRetrofitClient implements QuizzServiceClient{
    @Override
    public Flowable<LoginResponse> login(String userName, String pass) {
        return getQuizService().login(userName,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Anime>> getAllAnimes(String userName, String pass) {
        return getQuizService().getAllAnimes(userName,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Anime>> getAllAnimesImg(String userName, String pass) {
        return getQuizService().getAllAnimesImg(userName, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Anime>> getAllAnimesForWallpaper(String userName, String pass) {
        return getQuizService().getAllanimesForWallpaper(userName,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Preguntas>> getQuestionsByAnimeAndLevel(String userName, String pass, int idAnime, int level) {
        return getQuizService().getQuestionsByAnimeAndLevel(userName,pass,idAnime,level)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ScoreResponse> checkScoreAndLevel(String userName, String pass, int idAnime, int idUser) {
        return getQuizService().checkLevelAndScore(userName,pass,idAnime,idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UpdateResponse> updateLevelScoreGems(String userName, String pass, int gemas, int score, int level, int idUser, int idAnime) {
        return getQuizService().updateLevelScoreGemsTotalScore(userName,pass,gemas,score,level,idUser,idAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Wallpaper>> getWallpaperByAnime(String userName, String pass, int idAnime) {
        return getQuizService().getWallpaperByAnime(userName,pass,idAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UpdateResponse> updateGemas(String userName, String pass, int idUser, int gemas) {
        return getQuizService().updateGemas(userName,pass,idUser,gemas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<UpdateResponse> registroNuevoUsuario(String userNameFriend,String username, String nombre, String email, int edad, String genero, String password) {
        return getQuizService().registroNuevoUsuario(userNameFriend,username,nombre,email,edad,genero,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<User>> searchFriendByUserName(String userName, String pass, String userNameQuery) {
        return getQuizService().searchFriendByUserName(userName,pass,userNameQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UpdateResponse> addFrienById(String userName, String pass, int idUser, int idFriend) {
        return getQuizService().addFriendById(userName,pass,idUser,idFriend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Wallpaper>> getAvatarsByAnime(String userName, String pass, int idAnime) {
        return getQuizService().getAvatarsByAnime(userName,pass,idAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<User>> getAllFriendsByUser(String userName, String pass) {
        return getQuizService().getAllFriendsByUser(userName,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<UpdateResponse> updateAvatar(String userName, String pass, int idUser, String b64) {
        return getQuizService().updateAvatar(userName,pass,idUser,b64)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<LoginResponse> validaUsuarioFacebook(String idFaceBook) {
        return getQuizService().validaUsuarioFacebook(idFaceBook)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UpdateResponse> updateEsferas(String userName, String pass, int idUser, int esferas) {
        return getQuizService().updateEsferas(userName, pass, idUser, esferas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Preguntas>> getQuestionsByAnimeImg(String userName, String pass, int idAnime) {
        return getQuizService().getQuestionsByAnimeImg(userName,pass,idAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
