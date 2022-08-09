package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RecepcionEntrega extends AppCompatActivity {
    private EditText nGuiaTxt, personaRecibeTxt, telefonoTxt, notaTxt;
    private CheckBox status;
    private Button btnGuardar;

    String numGuia, statusEnvio, nombrePersona, telefono, nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion_entrega);

        nGuiaTxt = (EditText)findViewById(R.id.txtGuiaReceEntrega);
        personaRecibeTxt = (EditText)findViewById(R.id.txtPersonaRece);
        telefonoTxt = (EditText)findViewById(R.id.txtTelefonoReceEnt);
        notaTxt = (EditText)findViewById(R.id.txtNotaReceEnt);
        status = (CheckBox) findViewById(R.id.ckBxCambioStatusrece);
        btnGuardar = (Button) findViewById(R.id.btnGuardarReceEnt);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numGuia = nGuiaTxt.getText().toString();
                String nombrePersonaN = personaRecibeTxt.getText().toString();

                if (status.isChecked()){
                    statusEnvio = "Entregado";
                } else {
                    statusEnvio = "En Transito";
                }

                if (numGuia.isEmpty() && nombrePersonaN.isEmpty()){
                    Toast.makeText(RecepcionEntrega.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                } else {
                    editEnvio();
                }
            }
        });
    }

    private interface putEntrega{
        @Headers({"Content-Type: application/json"})
        @PUT("envios/numeroguia/{numEnvio}")
        Call<String> getbyNumEnvio(@Path("numEnvio") String numEnvio, @Body RequestBody requestBody);

    }

    private void editEnvio(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.117:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RecepcionEntrega.putEntrega envio = retrofit.create(RecepcionEntrega.putEntrega.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("numeroguia", numGuia);
        jsonObject.addProperty("estatusenvio", statusEnvio);

        JsonObject entregaJson = new JsonObject();
        entregaJson.addProperty("personarecibe", nombrePersona = personaRecibeTxt.getText().toString());
        entregaJson.addProperty("telefonorecibe", telefono = telefonoTxt.getText().toString());
        entregaJson.addProperty("nota", nota = notaTxt.getText().toString());
        jsonObject.add("servicioEntrega", entregaJson);

        // Convert JSONObject to String
        String jsonObjectString = jsonObject.toString();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonObjectString);

        Call<String> call = envio.getbyNumEnvio(numGuia, requestBody);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();
                Toast.makeText(RecepcionEntrega.this, "Entrega Registrada", Toast.LENGTH_LONG).show();
                nGuiaTxt.getText().clear();
                personaRecibeTxt.getText().clear();
                telefonoTxt.getText().clear();
                notaTxt.getText().clear();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                String message = t.getMessage();
            }
        });
    }
}