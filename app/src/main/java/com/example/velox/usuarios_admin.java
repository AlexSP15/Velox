package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class usuarios_admin extends AppCompatActivity {
    private Button btnConsultarUser, btnGuardar, btnEditar, btnEliminar;
    private EditText textNombreUser, textNombre, textId,textEmail, textPassword, textTelefono, textSeguro, textFecha;
    private Spinner textTipo;

    String sBaseUrl = "http://192.168.32.50:4000";

    String nombreEm, idEm, emailEm, passwordEm, tipousuarioEm, telefonoEm, seguroEm, fechaNacEm;

    boolean metodoEditar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_admin);
        btnEditar = findViewById(R.id.btnEditar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnConsultarUser = findViewById(R.id.btnConsultarUser);
        textNombreUser = findViewById(R.id.textNombreUser);
        textNombre = findViewById(R.id.textNombre);
        textId = findViewById(R.id.textId);
        textTipo =(Spinner) findViewById(R.id.textTipo);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textTelefono = findViewById(R.id.textTelefono);
        textSeguro = findViewById(R.id.textSeguro);
        textFecha = findViewById(R.id.textFecha);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConsultarUser.setEnabled(true);
                btnConsultarUser.refreshDrawableState();
                textNombreUser.setEnabled(true);
                textNombreUser.refreshDrawableState();
                btnEditar.setEnabled(false);
                btnEditar.refreshDrawableState();

                metodoEditar = true;
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (metodoEditar == false){
                    cargaData();
                    addUsuario();
                } else {

                }
            }
        });
    }

    private void cargaData(){
        nombreEm = textNombre.getText().toString();
        idEm = textId.getText().toString();
        emailEm = textEmail.getText().toString();
        passwordEm = textPassword.getText().toString();
        tipousuarioEm = textTipo.getSelectedItem().toString();
        telefonoEm = textTelefono.getText().toString();
        seguroEm = textSeguro.getText().toString();
        fechaNacEm = textFecha.getText().toString();
    }

    private class Usuario{
        String nombre, email, password, telefono, idusuario, seguro, fechaNac, tipousuario;

        public Usuario() {
        }

        public Usuario(String nombre, String email, String password, String telefono, String idusuario, String seguro, String fechaNac, String tipousuario) {
            this.nombre = nombre;
            this.email = email;
            this.password = password;
            this.telefono = telefono;
            this.idusuario = idusuario;
            this.seguro = seguro;
            this.fechaNac = fechaNac;
            this.tipousuario = tipousuario;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getIdusuario() {
            return idusuario;
        }

        public void setIdusuario(String idusuario) {
            this.idusuario = idusuario;
        }

        public String getSeguro() {
            return seguro;
        }

        public void setSeguro(String seguro) {
            this.seguro = seguro;
        }

        public String getFechaNac() {
            return fechaNac;
        }

        public void setFechaNac(String fechaNac) {
            this.fechaNac = fechaNac;
        }

        public String getTipousuario() {
            return tipousuario;
        }

        public void setTipousuario(String tipousuario) {
            this.tipousuario = tipousuario;
        }
    }

    private interface postUsuarios{
        @Headers({"Content-Type: application/json"})
        @POST("usuarios")
        Call<String> STRING_CALL(
                @Body RequestBody requestBody
        );
    }

    private void addUsuario() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        usuarios_admin.postUsuarios usuarios = retrofit.create(usuarios_admin.postUsuarios.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nombre", nombreEm);
        jsonObject.addProperty("idusuario", idEm);
        jsonObject.addProperty("email", emailEm);
        jsonObject.addProperty("password", passwordEm);
        jsonObject.addProperty("tipousuario", tipousuarioEm);
        jsonObject.addProperty("telefono", telefonoEm);
        jsonObject.addProperty("seguro", seguroEm);
        jsonObject.addProperty("fechanac", fechaNacEm);


        // Convert JSONObject to String
        String jsonObjectString = jsonObject.toString();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonObjectString);

        Call<String> call = usuarios.STRING_CALL(requestBody);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();

                Toast.makeText(usuarios_admin.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                String message = t.getMessage();
            }
        });
    }
}