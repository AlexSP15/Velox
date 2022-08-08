package com.example.velox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class admin_Usuario extends AppCompatActivity {

    RecyclerView recyclerViewName, recyclerViewTipo;


    String sBaseUrl = "http://192.168.0.117:4000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuario);

        mostrarUsuario();
    }

    private class Usuarios{
        String tipousuario, nombre;

        public Usuarios() {
        }

        public Usuarios(String tipousuario, String nombre) {
            this.tipousuario = tipousuario;
            this.nombre = nombre;
        }

        public String gettipousuario() {
            return tipousuario;
        }

        public void settipousuario(String tipousuario) {
            this.tipousuario = tipousuario;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }

    private interface buscar{
        @GET("usuarios/{idusuario}")
        Call<String> getbyIdUser(@Path("idusuario") String idusuario);
    }

    private void mostrarUsuario(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.117:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        admin_Usuario.buscar usuario = retrofit.create(admin_Usuario.buscar.class);

        Call<String> call = usuario.getbyIdUser("");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(body);

                    admin_Usuario.Usuarios envioResponse = new admin_Usuario.Usuarios();
                    JSONArray jUsuarios = jsonObject.getJSONArray("usuarios");
                    JSONObject jUsuario = jUsuarios.getJSONObject(0);
                    envioResponse.nombre = jUsuario.getString("nombre");
                    envioResponse.tipousuario = jUsuario.getString("tipousuario");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(admin_Usuario.this, "Error de conexion", Toast.LENGTH_LONG).show();
            }
        });

    }
}