package com.chrisstek.geekquizz.client.retrofit.deserializer;

import com.google.gson.JsonDeserializer;

import java.util.List;

public interface ListDeserializer<T> extends JsonDeserializer<List<T>> {
}
