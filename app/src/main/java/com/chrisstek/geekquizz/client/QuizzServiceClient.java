package com.chrisstek.geekquizz.client;

import com.chrisstek.geekquizz.client.model.Anime;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.Preguntas;
import com.chrisstek.geekquizz.client.model.ScoreResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.client.model.User;
import com.chrisstek.geekquizz.client.model.Wallpaper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface QuizzServiceClient {
    Flowable<LoginResponse> login(String userName, String pass);
    Observable<List<Anime>> getAllAnimes(String userName, String pass);
    Observable<List<Anime>> getAllAnimesImg(String userName, String pass);
    Observable<List<Anime>> getAllAnimesForWallpaper(String userName, String pass);
    Observable<List<Preguntas>> getQuestionsByAnimeAndLevel(String userName, String pass, int idAnime, int level);
    Observable<ScoreResponse> checkScoreAndLevel(String userName, String pass, int idAnime, int idUser);
    Observable<UpdateResponse> updateLevelScoreGems(String userName, String pass,
                                                    int gemas, int score,
                                                    int level, int idUser,
                                                    int idAnime);
    Observable<List<Wallpaper>> getWallpaperByAnime(String userName, String pass, int idAnime);
    Observable<UpdateResponse> updateGemas(String userName, String pass, int idUser, int gemas);
    Observable<UpdateResponse> registroNuevoUsuario(String userNameFriend, String username,String nombre,String email,int edad,String genero,String password);
    Observable<List<User>> searchFriendByUserName(String userName, String pass, String userNameQuery);
    Observable<UpdateResponse> addFrienById(String userName, String pass,int idUser, int idFriend);
    Observable<List<Wallpaper>> getAvatarsByAnime(String userName, String pass, int idAnime);
    Observable<List<User>> getAllFriendsByUser(String userName, String pass);
    Flowable<UpdateResponse> updateAvatar(String userName,String pass, int idUser, String b64);
    Flowable<LoginResponse> validaUsuarioFacebook(String idFaceBook);
    Observable<UpdateResponse> updateEsferas(String userName,String pass, int idUser, int esferas);
    Observable<List<Preguntas>> getQuestionsByAnimeImg(String userName, String pass, int idAnime);
}
