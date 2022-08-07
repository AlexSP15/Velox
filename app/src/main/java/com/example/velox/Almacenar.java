package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class Almacenar extends AppCompatActivity {
    private EditText numGuia;
    private EditText idAlmacen;
    private EditText idEstante;
    private Button btnAlmacenar;

    //URL DEL API
    String sBaseUrl = "http://192.168.32.50:4000/";

    //Variables donde se guardaran los datos ingresados por el usuario
    String nGuia, idAlm, idEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenar);

        bindUI();

        btnAlmacenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guarda los datos en las variables
                nGuia = numGuia.getText().toString().trim();
                idAlm = idAlmacen.getText().toString().trim();
                idEst = idEstante.getText().toString().trim();

                //Comprueba que no esten vacios los TextView
                if (!nGuia.isEmpty() && !idAlm.isEmpty() && !idEst.isEmpty()){
                    addAlmacen();
                }
            }
        });
    }

    private void bindUI(){
        numGuia = (EditText)findViewById(R.id.txtNguia);
        idAlmacen = (EditText)findViewById(R.id.txtAlmacen);
        idEstante = (EditText)findViewById(R.id.txtEstante);
        btnAlmacenar = (Button) findViewById(R.id.btnGuardarAlmacen);
    }

    private interface postAlmacen{
        @FormUrlEncoded
        @POST("almacen")
        Call<String> STRING_CALL(
                @Field("numeroguia") String numeroguia,
                @Field("idalmacen") String idalmacen,
                @Field("idestante") String idestante
        );
    }

    private void addAlmacen(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        postAlmacen almacen = retrofit.create(postAlmacen.class);

        Call<String> call = almacen.STRING_CALL(nGuia, idAlm, idEst);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    numGuia.getText().clear();
                    idAlmacen.getText().clear();
                    idEstante.getText().clear();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}

