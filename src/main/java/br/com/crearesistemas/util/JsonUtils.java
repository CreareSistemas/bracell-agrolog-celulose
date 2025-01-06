package br.com.crearesistemas.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

    public static <T> String toJson(T request) {
        String response = null;
        try {
        	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            response =  gson.toJson(request);
        } catch (Exception e) {e.printStackTrace();}
        return response;
    }

    public static <T> T fromJson(String responseJson, Class<T> responseClass) {
        T  response = null;
        try {
        	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            response = gson.fromJson(responseJson, responseClass);
        } catch (Exception e) {e.printStackTrace();}

        return response;
    }



}
