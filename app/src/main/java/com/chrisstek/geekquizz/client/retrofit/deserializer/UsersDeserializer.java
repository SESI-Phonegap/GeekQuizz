package com.chrisstek.geekquizz.client.retrofit.deserializer;

import com.chrisstek.geekquizz.client.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.List;

public class UsersDeserializer<T> implements ListDeserializer<T>  {

    @Override
    public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
        JsonArray friendsJsonArray = json.getAsJsonObject().get(Constants.Deserializer.FRIENDS).getAsJsonArray();
        return new Gson().fromJson(friendsJsonArray, typeOfT);
    }
}

