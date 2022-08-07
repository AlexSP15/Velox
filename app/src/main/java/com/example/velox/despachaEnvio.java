package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class despachaEnvio extends AppCompatActivity {
    private EditText numGuia;
    private CheckBox status;
    private Button btnGuardar;

    //Variables donde se guardaran los datos ingresados por el usuario
    String nGuia, statusEnvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despacha_envio);

        bindUI();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guarda los datos en las variables
                nGuia = numGuia.getText().toString();

                if (status.isChecked()){
                    statusEnvio = "En transito";
                } else {
                    statusEnvio = "En almacen";
                }

                //Comprueba que no esten vacios los TextView
                if (!nGuia.isEmpty()){
                    editAlmacen();
                }
            }
        });
    }

    private void bindUI(){
        numGuia = (EditText)findViewById(R.id.txtNGuiaDes);
        status = (CheckBox) findViewById(R.id.checkBoxDespacha);
        btnGuardar = (Button) findViewById(R.id.btnGuardarDes);
    }

    private interface putAlmacen{
        @Headers({"Content-Type: application/json"})
        @PUT("almacen/numeroguia/{numEnvio}")
        Call<String> getbyNumEnvio(@Path("numEnvio") String numEnvio, @Body RequestBody updateRequest);
        /*Call<String> STRING_CALL(
                @Field("numeroguia") String numeroguia,
                @Field("estatusalmacen") String estatusalmacen
        );*/
    }

    private void editAlmacen(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.32.50:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        despachaEnvio.putAlmacen almacen = retrofit.create(despachaEnvio.putAlmacen.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("numeroguia", nGuia);
        jsonObject.addProperty("estatusalmacen", statusEnvio);

        // Convert JSONObject to String
        String jsonObjectString = jsonObject.toString();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonObjectString);
        Call<String> envioObject = almacen.getbyNumEnvio(nGuia, requestBody);
        envioObject.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    throw new Exception("error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}