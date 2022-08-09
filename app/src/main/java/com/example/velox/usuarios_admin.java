package com.example.velox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class usuarios_admin extends AppCompatActivity {
    private Button btnConsultarUser, btnGuardar, btnEditar, btnEliminar;
    private EditText textIdUser, textNombre, textId,textEmail, textPassword, textTelefono, textSeguro, textFecha;
    private Spinner textTipo;

    String sBaseUrl = "http://192.168.0.117:4000";

    String idUserEm, nombreEm, idEm, emailEm, passwordEm, tipousuarioEm, telefonoEm, seguroEm, fechaNacEm;

    boolean metodoEditar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_admin);
        btnEditar = findViewById(R.id.btnEditar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnConsultarUser = findViewById(R.id.btnConsultarUser);
        textIdUser = findViewById(R.id.textIdUser);
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
                textIdUser.setEnabled(true);
                textIdUser.refreshDrawableState();
                btnEditar.setEnabled(false);
                btnEditar.refreshDrawableState();

                metodoEditar = true;

                idUserEm = textIdUser.getText().toString();

                btnGuardar.setEnabled(false);
                btnGuardar.refreshDrawableState();
            }
        });

        btnConsultarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idUserEm = textIdUser.getText().toString();

                if (idUserEm.isEmpty()){
                    Toast.makeText(usuarios_admin.this, "Consulta un usuario",Toast.LENGTH_LONG).show();
                }else {
                    mostrarUsuario();
                    btnGuardar.setEnabled(true);
                    btnGuardar.refreshDrawableState();
                    btnEliminar.setEnabled(true);
                    btnEliminar.refreshDrawableState();
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaData();
                if (metodoEditar == false){
                    if (passwordEm.isEmpty() && nombreEm.isEmpty() && seguroEm.isEmpty()){
                        Toast.makeText(usuarios_admin.this, "Ingresa los datos", Toast.LENGTH_LONG).show();
                    } else {
                        addUsuario();
                        textNombre.getText().clear();
                        textId.getText().clear();
                        textEmail.getText().clear();
                        textPassword.getText().clear();
                        textTelefono.getText().clear();
                        textSeguro.getText().clear();
                        textFecha.getText().clear();
                    }
                } else {
                    if (passwordEm.isEmpty() && nombreEm.isEmpty() && seguroEm.isEmpty()){
                        Toast.makeText(usuarios_admin.this, "Ingresa los datos", Toast.LENGTH_LONG).show();
                    } else {
                        cargaData();
                        editUsuario();
                        textNombre.getText().clear();
                        textId.getText().clear();
                        textEmail.getText().clear();
                        textPassword.getText().clear();
                        textTelefono.getText().clear();
                        textSeguro.getText().clear();
                        textFecha.getText().clear();
                        textIdUser.getText().clear();
                    }
                }
                btnConsultarUser.setEnabled(false);
                btnConsultarUser.refreshDrawableState();
                textIdUser.setEnabled(false);
                textIdUser.refreshDrawableState();
                metodoEditar = false;
                btnEditar.setEnabled(true);
                btnEditar.refreshDrawableState();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idUserEm = textIdUser.getText().toString();
                passwordEm = textPassword.getText().toString();
                nombreEm = textNombre.getText().toString();
                seguroEm = textSeguro.getText().toString();
                if (idUserEm.isEmpty()){
                    Toast.makeText(usuarios_admin.this, "Por favor, consulte el usuario", Toast.LENGTH_LONG).show();
                } else {
                    if (passwordEm.isEmpty() && nombreEm.isEmpty() && seguroEm.isEmpty()) {
                        Toast.makeText(usuarios_admin.this, "Por favor, consulte nuevamente", Toast.LENGTH_LONG).show();
                    } else {
                        eliminarrUsuario();
                        textNombre.getText().clear();
                        textId.getText().clear();
                        textEmail.getText().clear();
                        textPassword.getText().clear();
                        textTelefono.getText().clear();
                        textSeguro.getText().clear();
                        textFecha.getText().clear();
                        textIdUser.getText().clear();
                        btnConsultarUser.setEnabled(false);
                        btnConsultarUser.refreshDrawableState();
                        textIdUser.setEnabled(false);
                        textIdUser.refreshDrawableState();
                        metodoEditar = false;
                        btnEditar.setEnabled(true);
                        btnEditar.refreshDrawableState();
                    }
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

                Toast.makeText(usuarios_admin.this, "Usuario registrado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(usuarios_admin.this, "Error de conexion", Toast.LENGTH_LONG).show();
                String message = t.getMessage();
            }
        });
    }

    private interface buscar{
        @GET("usuarios/idusuario/{idusuario}")
        Call<String> getbyIdUser(@Path("idusuario") String idusuario);
    }

    private void mostrarUsuario(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.117:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        usuarios_admin.buscar usuario = retrofit.create(usuarios_admin.buscar.class);


        Call<String> call = usuario.getbyIdUser(idUserEm);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();
                if (body == null){
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(usuarios_admin.this);
                    dialogo.setTitle("Aviso");
                    dialogo.setMessage("Usuario erróneo o inexistente. Por favor, intenta con otro usuario");
                    dialogo.setCancelable(false);
                    dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(usuarios_admin.this, "Intente de nuevo", Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogo.show();

                } else {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(body);

                        usuarios_admin.Usuario envioResponse = new usuarios_admin.Usuario();
                        JSONArray jUsuarios = jsonObject.getJSONArray("usuarios");
                        JSONObject jUsuario = jUsuarios.getJSONObject(0);

                        envioResponse.nombre = jUsuario.getString("nombre");
                        envioResponse.email = jUsuario.getString("email");
                        envioResponse.password = jUsuario.getString("password");
                        envioResponse.tipousuario = jUsuario.getString("tipousuario");
                        envioResponse.telefono = jUsuario.getString("telefono");
                        envioResponse.idusuario = jUsuario.getString("idusuario");
                        envioResponse.seguro = jUsuario.getString("seguro");
                        envioResponse.fechaNac = jUsuario.getString("fechanac");

                        textNombre.setText(envioResponse.nombre.toString());
                        textNombre.refreshDrawableState();

                        textId.setText(envioResponse.idusuario.toString());
                        textId.refreshDrawableState();

                        textEmail.setText(envioResponse.email.toString());
                        textEmail.refreshDrawableState();

                        textPassword.setText(envioResponse.password.toString());
                        textPassword.refreshDrawableState();

                        int positionTipoUser;
                        if (envioResponse.tipousuario.equals("Repartidor")){
                            positionTipoUser = 0;
                        } else {
                            if (envioResponse.tipousuario.equals("Recepcionista")){
                                positionTipoUser = 1;
                            } else {
                                if (envioResponse.tipousuario.equals("Almacenista")){
                                    positionTipoUser = 2;
                                } else {
                                    positionTipoUser = 3;
                                }
                            }
                        }

                        textTipo.setSelection(positionTipoUser);
                        textTipo.refreshDrawableState();

                        textTelefono.setText(envioResponse.telefono);
                        textTelefono.refreshDrawableState();

                        textSeguro.setText(envioResponse.seguro);
                        textSeguro.refreshDrawableState();

                        textFecha.setText(envioResponse.fechaNac);
                        textFecha.refreshDrawableState();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(usuarios_admin.this, "Error de conexion", Toast.LENGTH_LONG).show();
            }
        });


    }

    private interface putUsuario{
        @Headers({"Content-Type: application/json"})
        @PUT("usuarios/idusuario/{idusuario}")
        Call<String> getbyIdUser(@Path("idusuario") String idusuario, @Body RequestBody updateRequest);
    }

    private void editUsuario(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.117:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        usuarios_admin.putUsuario usuarios = retrofit.create(usuarios_admin.putUsuario.class);

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
        Call<String> userObject = usuarios.getbyIdUser(idUserEm, requestBody);

        userObject.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();
                Toast.makeText(usuarios_admin.this, "Usuario actualizado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(usuarios_admin.this, "Error de conexion", Toast.LENGTH_LONG).show();
                try {
                    throw new Exception("error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private interface deleteUsuario{
        @DELETE("usuarios/idusuario/{idusuario}")
        Call<String> getbyIdUser(@Path("idusuario") String idusuario);
    }

    private void eliminarrUsuario(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.117:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        usuarios_admin.deleteUsuario usuario = retrofit.create(usuarios_admin.deleteUsuario.class);


        Call<String> call = usuario.getbyIdUser(idUserEm);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();
                Toast.makeText(usuarios_admin.this, "Usuario eliminado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(usuarios_admin.this, "Error de conexion", Toast.LENGTH_LONG).show();
            }
        });

    }
}