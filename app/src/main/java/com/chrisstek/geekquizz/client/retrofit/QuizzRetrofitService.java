package com.chrisstek.geekquizz.client.retrofit;

import com.chrisstek.geekquizz.client.Constants;
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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuizzRetrofitService {

    @POST(Constants.EndPoint.LOGIN_MOBILE)
    @FormUrlEncoded
    Flowable<LoginResponse> login(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                  @Field(Constants.ParametersBackEnd.PASS) String pass);

    @POST(Constants.EndPoint.GET_ALL_ANIMES)
    @FormUrlEncoded
    Observable<List<Anime>> getAllAnimes(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                         @Field(Constants.ParametersBackEnd.PASS) String pass);

    @POST(Constants.EndPoint.GET_ALL_ANIMES_IMG)
    @FormUrlEncoded
    Observable<List<Anime>> getAllAnimesImg(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                            @Field(Constants.ParametersBackEnd.PASS) String pass);

    @POST(Constants.EndPoint.GET_ALL_ANIMES_FOR_WALLPAPER)
    @FormUrlEncoded
    Observable<List<Anime>> getAllanimesForWallpaper(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                     @Field(Constants.ParametersBackEnd.PASS) String pass);

    @POST(Constants.EndPoint.GET_QUESTIONS_BY_ANIME_AND_LEVEL)
    @FormUrlEncoded
    Observable<List<Preguntas>> getQuestionsByAnimeAndLevel(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                            @Field(Constants.ParametersBackEnd.PASS) String pass,
                                                            @Field(Constants.ParametersBackEnd.ID_ANIME) int idAnime,
                                                            @Field(Constants.ParametersBackEnd.LEVEL) int level);

    @POST(Constants.EndPoint.CHECK_LEVEL_AND_SCORE_BY_ANIME_AND_USER)
    @FormUrlEncoded
    Observable<ScoreResponse> checkLevelAndScore(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                 @Field(Constants.ParametersBackEnd.PASS) String pass,
                                                 @Field(Constants.ParametersBackEnd.ID_ANIME) int idAnime,
                                                 @Field(Constants.ParametersBackEnd.ID_USER) int idUser);

    @POST(Constants.EndPoint.UPDATE_LEVEL_AND_SCORE)
    @FormUrlEncoded
    Observable<UpdateResponse> updateLevelScoreGemsTotalScore(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                              @Field(Constants.ParametersBackEnd.PASS) String pass,
                                                              @Field(Constants.ParametersBackEnd.GEMS) int gems,
                                                              @Field(Constants.ParametersBackEnd.SCORE) int score,
                                                              @Field(Constants.ParametersBackEnd.LEVEL) int level,
                                                              @Field(Constants.ParametersBackEnd.ID_USER) int idUser,
                                                              @Field(Constants.ParametersBackEnd.ID_ANIME) int idAnime);

    @POST(Constants.EndPoint.GET_WALLPAPER_BY_ANIME)
    @FormUrlEncoded
    Observable<List<Wallpaper>> getWallpaperByAnime(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                    @Field(Constants.ParametersBackEnd.PASS) String pass,
                                                    @Field(Constants.ParametersBackEnd.ID_ANIME) int idAnime);

    @POST(Constants.EndPoint.UPDATE_GEMAS)
    @FormUrlEncoded
    Observable<UpdateResponse> updateGemas(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                           @Field(Constants.ParametersBackEnd.PASS) String pass,
                                           @Field(Constants.ParametersBackEnd.ID_USER) int idUser,
                                           @Field(Constants.ParametersBackEnd.GEMS) int gems);

    @POST(Constants.EndPoint.REGISTRO_NUEVO_USUARIO)
    @FormUrlEncoded
    Flowable<UpdateResponse> registroNuevoUsuario(@Field(Constants.ParametersBackEnd.USER_NAME_FRIEND) String userNameFriend,
                                                    @Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                    @Field(Constants.ParametersBackEnd.NOMBRE) String nombre,
                                                    @Field(Constants.ParametersBackEnd.EMAIL) String email,
                                                    @Field(Constants.ParametersBackEnd.EDAD) int edad,
                                                    @Field(Constants.ParametersBackEnd.GENERO) String genero,
                                                    @Field(Constants.ParametersBackEnd.PASS) String pass);

    @POST(Constants.EndPoint.SEARCH_FRIEND_BY_USER_NAME)
    @FormUrlEncoded
    Observable<List<User>> searchFriendByUserName(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                  @Field(Constants.ParametersBackEnd.PASS) String pass,
                                                  @Field(Constants.ParametersBackEnd.USER_NAME_QUERY) String userNameQuery);

    @POST(Constants.EndPoint.ADD_FRIEND_BY_ID)
    @FormUrlEncoded
    Observable<UpdateResponse> addFriendById(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                             @Field(Constants.ParametersBackEnd.PASS) String pass,
                                             @Field(Constants.ParametersBackEnd.ID_USER) int idUser,
                                             @Field(Constants.ParametersBackEnd.ID_FRIEND) int idFriend);

    @POST(Constants.EndPoint.GET_AVATARS_BY_ANIME)
    @FormUrlEncoded
    Observable<List<Wallpaper>> getAvatarsByAnime(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                  @Field(Constants.ParametersBackEnd.PASS) String pass,
                                                  @Field(Constants.ParametersBackEnd.ID_ANIME) int idAnime);

    @POST(Constants.EndPoint.GET_ALL_FRIENDS_BY_USER)
    @FormUrlEncoded
    Observable<List<User>> getAllFriendsByUser(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                               @Field(Constants.ParametersBackEnd.PASS) String pass);

    @POST(Constants.EndPoint.UPDATE_AVATAR)
    @FormUrlEncoded
    Flowable<UpdateResponse> updateAvatar(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                            @Field(Constants.ParametersBackEnd.PASS) String pass,
                                            @Field(Constants.ParametersBackEnd.ID_USER)int idUser,
                                            @Field(Constants.ParametersBackEnd.AVATAR_BASE64)String b64);

    @POST(Constants.EndPoint.CHECK_USER_FACEBOOK)
    @FormUrlEncoded
    Flowable<LoginResponse> validaUsuarioFacebook(@Field(Constants.ParametersBackEnd.ID_FACEBOOK) String idFaceBook);

    @POST(Constants.EndPoint.UPDATE_ESFERAS)
    @FormUrlEncoded
    Observable<UpdateResponse> updateEsferas(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                             @Field(Constants.ParametersBackEnd.PASS) String pass,
                                             @Field(Constants.ParametersBackEnd.ID_USER) int idUser,
                                             @Field(Constants.ParametersBackEnd.ESFERAS) int esferas);

    @POST(Constants.EndPoint.GET_QUESTIONS_BY_ANIME_IMG)
    @FormUrlEncoded
    Observable<List<Preguntas>> getQuestionsByAnimeImg(@Field(Constants.ParametersBackEnd.USER_NAME) String username,
                                                       @Field(Constants.ParametersBackEnd.PASS) String pass,
                                                       @Field(Constants.ParametersBackEnd.ID_ANIME) int idAnime);

}
