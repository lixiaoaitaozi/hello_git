package com.china.kuaiyou.mybase;

import com.google.gson.Gson;

public class MyGson {
    private static MyGson myGson;
    Gson gson;

    private MyGson() {
        // TODO Auto-generated constructor stub
        gson = new Gson();
    }

    public static MyGson getInstance() {
        if (myGson == null) {
            synchronized (MyGson.class) {
                if (myGson == null) {
                    myGson = new MyGson();
                }
            }
        }
        return myGson;
    }

    public String toGsonStr(Object object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        }catch (Exception e){
            return  null;
        }
    }

    public String toJson(Object object){
        String str=gson.toJson(object);
        return str;
    }
}
