package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    /*private Button button;
    private EditText emailTxt, passTxt;
    private String email, password;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*button = findViewById(R.id.btnLogin);
        emailTxt = findViewById(R.id.txtUserLogin);
        passTxt = findViewById(R.id.txtContraseñaLogin);*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.103:4000/usuarios/email/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceLogin api = retrofit.create(InterfaceLogin.class);

        Call<Login> userObject = api.getbyDicCorreo("javier.soledad@velox.com");

        userObject.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = (Login) response.body();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
/*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailTxt.getText().toString();
                password = passTxt.getText().toString();

                String authToken = createAuthToken(email, password);
                checkLoginDetails(authToken);
            }
        });*/
    }

    /*private void checkLoginDetails(String authToken) {
        Retrofit retrofit = Login.getRetrofitInstance();
        final InterfaceLogin api = retrofit.create(InterfaceLogin.class);

        Call<String> call = api.checkLogin(authToken);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body().matches("success")){
                        Toast.makeText(getApplicationContext(), "Autenticado correctamente", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Credenciales invalidas", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });
    }*/

    /*private String createAuthToken(String email, String password) {
        byte [] data = new byte[0];
        try{
            data = (email + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }*/
}