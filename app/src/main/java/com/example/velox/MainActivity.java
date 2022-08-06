package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText emailTxt, passTxt;
    private TextView enlaceClient;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btnLogin);
        emailTxt = findViewById(R.id.txtUserLogin);
        passTxt = findViewById(R.id.txtContraseñaLogin);
        enlaceClient = findViewById(R.id.enlaceCliente);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailTxt.getText().toString();
                password = passTxt.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingresas los datos", Toast.LENGTH_LONG).show();
                } else {
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Login.class,new Deserializer());

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.0.117:4000/usuarios/email/")
                            .addConverterFactory(GsonConverterFactory.create(builder.create()))
                            .build();

                    InterfaceLogin api = retrofit.create(InterfaceLogin.class);

                    Call<Login> userObject = api.getbyDicCorreo(email);

                    userObject.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            Login login = (Login) response.body();

                            if (login == null){
                                Toast.makeText(MainActivity.this, "Credenciales Incorrectas", Toast.LENGTH_LONG).show();
                            } else {

                                String pruebCorreo = login.getEmail();
                                String pruebPass = login.getContraseña();
                                String TipoUser = login.getTipousuario();

                                if (pruebCorreo.equals(email) && pruebPass.equals(password)){
                                    if (TipoUser.equals("Admin")){
                                        enlaceAdmin();
                                    } else {
                                        if (TipoUser.equals("Repartidor")){
                                            enlaceRepartidor();
                                        } else {
                                            if (TipoUser.equals("Almacenista")){
                                                enlaceAlmacenista();
                                            } else {
                                                if (TipoUser.equals("Recepcionista")){
                                                    enlaceRecepcionista();
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Credenciales Incorrectas", Toast.LENGTH_LONG).show();
                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error de conexion", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        enlaceClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InicioCliente.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void enlaceAdmin(){
        Intent intent = new Intent(MainActivity.this, inicioAdmin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void enlaceRepartidor(){
        Intent intent = new Intent(MainActivity.this, repartidor_home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void enlaceRecepcionista(){
        Intent intent = new Intent(MainActivity.this, InicioCliente.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void enlaceAlmacenista(){
        Intent intent = new Intent(MainActivity.this, AlmacenistaHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}