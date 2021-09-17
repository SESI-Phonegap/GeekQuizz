package com.chrisstek.geekquizz.client.retrofit;

import com.chrisstek.geekquizz.client.Constants;
import com.chrisstek.geekquizz.client.model.Anime;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.Preguntas;
import com.chrisstek.geekquizz.client.model.ScoreResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.client.model.User;
import com.chrisstek.geekquizz.client.model.Wallpaper;
import com.chrisstek.geekquizz.client.retrofit.deserializer.AnimeResponseDeserializer;
import com.chrisstek.geekquizz.client.retrofit.deserializer.CheckLevelAndScoreDeserializer;
import com.chrisstek.geekquizz.client.retrofit.deserializer.LoginResponseDeserializer;
import com.chrisstek.geekquizz.client.retrofit.deserializer.PreguntasDeserializer;
import com.chrisstek.geekquizz.client.retrofit.deserializer.UpdateDeserializer;
import com.chrisstek.geekquizz.client.retrofit.deserializer.UsersDeserializer;
import com.chrisstek.geekquizz.client.retrofit.deserializer.WallpaperDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizzRetrofitClient {
    private QuizzRetrofitService quizRetrofitService;

    public QuizzRetrofitClient() {
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = retrofitBuilder();
        quizRetrofitService = retrofit.create(getQuizServiceClass());
    }

    private Retrofit retrofitBuilder() {
        return new Retrofit.Builder().baseUrl(Constants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(getQuizDeserializer()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        client.addInterceptor(loggingInterceptor);
        return client.build();
    }

    private Class<QuizzRetrofitService> getQuizServiceClass() {
        return QuizzRetrofitService.class;
    }

    private Gson getQuizDeserializer() {
        return new GsonBuilder()
                .registerTypeAdapter(new TypeToken<LoginResponse>() {}
                        .getType(), new LoginResponseDeserializer<LoginResponse>())
                .registerTypeAdapter(new TypeToken<List<Anime>>(){}
                        .getType(), new AnimeResponseDeserializer<Anime>())
                .registerTypeAdapter(new TypeToken<List<Preguntas>>(){}
                        .getType(), new PreguntasDeserializer<Preguntas>())
                .registerTypeAdapter(new TypeToken<ScoreResponse>(){}
                        .getType(), new CheckLevelAndScoreDeserializer<ScoreResponse>())
                .registerTypeAdapter(new TypeToken<UpdateResponse>(){}
                        .getType(), new UpdateDeserializer<UpdateResponse>())
                .registerTypeAdapter(new TypeToken<List<Wallpaper>>(){}
                        .getType(),new WallpaperDeserializer<Wallpaper>())
                .registerTypeAdapter(new TypeToken<List<User>>(){}
                        .getType(), new UsersDeserializer<User>())
                .create();
    }

    protected QuizzRetrofitService getQuizService() {
        return quizRetrofitService;
    }

}
