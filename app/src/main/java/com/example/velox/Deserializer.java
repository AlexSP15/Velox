package com.example.velox;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONStringer;

import java.lang.reflect.Type;

public class Deserializer implements JsonDeserializer<Login> {

    @Override
    public Login deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String email = json.getAsJsonObject().get("usuarios").getAsJsonArray().get(0).getAsJsonObject().get("email").getAsString();
        String password = json.getAsJsonObject().get("usuarios").getAsJsonArray().get(0).getAsJsonObject().get("password").getAsString();
        String tipousuario = json.getAsJsonObject().get("usuarios").getAsJsonArray().get(0).getAsJsonObject().get("tipousuario").getAsString();

        Login login = new Login(email,password,tipousuario);
        return login;

    }
}
