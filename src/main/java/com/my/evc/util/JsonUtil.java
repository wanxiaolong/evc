package com.my.evc.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtil {
	private static Gson gson = new Gson();

	public static <T> T toObject(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	public static <T> List<T> toList(String json, Class<T> clazz) {
		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(json).getAsJsonArray();

		ArrayList<T> list = new ArrayList<T>();

		for (JsonElement obj : jsonArray) {
			T entity = gson.fromJson(obj, clazz);
			list.add(entity);
		}
		
		return list;
	}
}
