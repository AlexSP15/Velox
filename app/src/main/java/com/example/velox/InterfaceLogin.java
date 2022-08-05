package com.example.velox;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceLogin {

    @GET("{DicCorreo}/")
    Call<Login> getbyDicCorreo(@Path("DicCorreo") String direccion);
}
