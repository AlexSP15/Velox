package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class RecepcionNuevaEntrega extends AppCompatActivity {
    private EditText numGuia, nomRem, calleRem, cpRem, ciudadRem, muniRem, estRem, emailRem, telRem;
    private EditText referencia, nomDom, calleDom, cpDom, ciudadDom, muniDom, estDom, emailDom, telDom;
    private Spinner tipoEnvio, tipoPaquete;
    private Button btnGuardar;

    String sBaseUrl = "http://10.20.55.52:4000";
    String numGuiav, nomRemv, calleRemv, cpRemv, ciudadRemv, muniRemv, estRemv, emailRemv, telRemv;
    String referenciav, nomDomv, calleDomv, cpDomv, ciudadDomv, muniDomv, estDomv, emailDomv, telDomv, itemEnvio, itemPaq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion_nueva_entrega);
        bindUI();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaData();
                Destinatario destinatarioIngresado;
                destinatarioIngresado = new Destinatario(nomDomv);
                if (numGuiav.isEmpty() && cpDomv.isEmpty() && emailDomv.isEmpty() && estRemv.isEmpty()){
                    Toast.makeText(RecepcionNuevaEntrega.this, "Ingresa todos los datos", Toast.LENGTH_LONG).show();
                } else {
                    addEnvio();
                }
            }
        });
    }
    private void bindUI(){
        numGuia = (EditText)findViewById(R.id.txtNumGuiaRece);
        nomRem = (EditText)findViewById(R.id.txtNomRemitRece);
        calleRem = (EditText)findViewById(R.id.txtCalleRemRece);
        cpRem = (EditText) findViewById(R.id.txtCodPRemRece);
        ciudadRem = (EditText)findViewById(R.id.txtCdRemRece);
        muniRem = (EditText)findViewById(R.id.txtMunicRemRece);
        estRem = (EditText)findViewById(R.id.txtEstadRemRece);
        emailRem = (EditText) findViewById(R.id.txtEmailRemRece);
        telRem = (EditText)findViewById(R.id.txtPhonRemRece);
        nomDom = (EditText)findViewById(R.id.txtNomDesRece);
        calleDom = (EditText)findViewById(R.id.txtCalleDesRece);
        cpDom = (EditText) findViewById(R.id.txtCodPDesRece);
        ciudadDom = (EditText)findViewById(R.id.txtCdDesRece);
        muniDom = (EditText)findViewById(R.id.txtMunicDesRece);
        estDom = (EditText)findViewById(R.id.txtEstadDesRece);
        emailDom = (EditText) findViewById(R.id.txtEmailDesRece);
        telDom = (EditText)findViewById(R.id.txtPhonDestRece);
        referencia = (EditText)findViewById(R.id.txtReferenciaDesRece);

        btnGuardar = (Button)findViewById(R.id.btnGuardarNewRece);

        tipoEnvio = (Spinner)findViewById(R.id.spinner_TipoEnvioRece);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tenvios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoEnvio.setAdapter(adapter);

        tipoPaquete = (Spinner) findViewById(R.id.spinner_TipoPaqRece);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.tpack, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoPaquete.setAdapter(adapter1);
    }

    private void cargaData() {
        numGuiav = numGuia.getText().toString();

        nomRemv = nomRem.getText().toString();
        calleRemv = calleRem.getText().toString();
        cpRemv = cpRem.getText().toString();
        ciudadRemv = ciudadRem.getText().toString();
        muniRemv = muniRem.getText().toString();
        estRemv = estRem.getText().toString();
        telRemv = telRem.getText().toString();
        emailRemv = emailRem.getText().toString();

        nomDomv = nomDom.getText().toString();
        calleDomv = calleDom.getText().toString();
        cpDomv = cpDom.getText().toString();
        ciudadDomv = ciudadDom.getText().toString();
        muniDomv = muniDom.getText().toString();
        estDomv = estDom.getText().toString();
        emailDomv = emailDom.getText().toString();
        telDomv = telDom.getText().toString();
        referenciav = referencia.getText().toString();

        itemEnvio = tipoEnvio.getSelectedItem().toString();
        itemPaq = tipoPaquete.getSelectedItem().toString();
    }

    private interface postEnvio{
        @Headers({"Content-Type: application/json"})
        @POST("envios")
        Call<String> STRING_CALL(
                @Body RequestBody requestBody
        );
    }

    private class Envio{
        String numeroguia;
        String tipopaquete;
        String tipoenvio;
        //RequestBody destinatario;
    }

    private class Destinatario{
        String nombredestinatario;

        public Destinatario() {
        }

        public Destinatario(String nombredestinatario) {
            this.nombredestinatario = nombredestinatario;
        }

        public String getNombredestinatario() {
            return nombredestinatario;
        }

        public void setNombredestinatario(String nombredestinatario) {
            this.nombredestinatario = nombredestinatario;
        }
    }

    private void addEnvio(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RecepcionNuevaEntrega.postEnvio envio = retrofit.create(RecepcionNuevaEntrega.postEnvio.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("numeroguia", numGuiav);
        jsonObject.addProperty("tipopaquete", tipoPaquete.getSelectedItem().toString());
        jsonObject.addProperty("tipoenvio", tipoEnvio.getSelectedItem().toString());

        JsonObject remitenteJson = new JsonObject();
        remitenteJson.addProperty("nombreremitente", nomRemv);
        remitenteJson.addProperty("direccionremitente", calleRemv);
        remitenteJson.addProperty("cpremitente", cpRemv);
        remitenteJson.addProperty("ciudadremitente", ciudadRemv);
        remitenteJson.addProperty("municipioremitente", muniRemv);
        remitenteJson.addProperty("estadoremitente", estRemv);
        remitenteJson.addProperty("emailremitente", emailRemv);
        remitenteJson.addProperty("telefonoremitente", telRemv);
        jsonObject.add("remitente", remitenteJson);

        JsonObject destinatarioJson = new JsonObject();
        destinatarioJson.addProperty("nombredestinatario", nomDomv);
        destinatarioJson.addProperty("direcciondestinatario", calleDomv);
        destinatarioJson.addProperty("cpdestinatario", cpDomv);
        destinatarioJson.addProperty("ciudaddestinatario", ciudadDomv);
        destinatarioJson.addProperty("municipiodestinatario", muniDomv);
        destinatarioJson.addProperty("estadodestinatario", estDomv);
        destinatarioJson.addProperty("emaildestinatario", emailDomv);
        destinatarioJson.addProperty("telefonodestinatario", telDomv);
        destinatarioJson.addProperty("referencia", referenciav);
        jsonObject.add("destinatario", destinatarioJson);


        // Convert JSONObject to String
        String jsonObjectString = jsonObject.toString();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, jsonObjectString);

        Call<String> call = envio.STRING_CALL(requestBody);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();
                numGuia.getText().clear();
                nomRem.getText().clear();
                calleRem.getText().clear();
                cpRem.getText().clear();
                ciudadRem.getText().clear();
                muniRem.getText().clear();
                estRem.getText().clear();
                telRem.getText().clear();
                emailRem.getText().clear();

                nomDom.getText().clear();
                calleDom.getText().clear();
                cpDom.getText().clear();
                ciudadDom.getText().clear();
                muniDom.getText().clear();
                estDom.getText().clear();
                emailDom.getText().clear();
                telDom.getText().clear();
                referencia.getText().clear();

                Toast.makeText(RecepcionNuevaEntrega.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                String message = t.getMessage();
            }
        });
    }
}