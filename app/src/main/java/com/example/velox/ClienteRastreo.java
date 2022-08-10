package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public class ClienteRastreo extends AppCompatActivity {
    private Button btnConsultar;
    private EditText txtGuiaRasClie;
    private TextView txtEstatusCli, txtRemitenteCli, txtDireccionCli, txtReferenciaCli;

    String numGuia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_rastreo);
        btnConsultar = (Button) findViewById(R.id.btnConsultarRCli);
        txtGuiaRasClie = (EditText) findViewById(R.id.txtGuiaRasClie);
        txtEstatusCli = (TextView) findViewById(R.id.txtEstatusCli);
        txtRemitenteCli = (TextView) findViewById(R.id.txtRemitenteCli);
        txtDireccionCli = (TextView) findViewById(R.id.txtDireccionCli);
        txtReferenciaCli = (TextView) findViewById(R.id.txtReferenciaCli);


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numGuia = txtGuiaRasClie.getText().toString();
                if (numGuia.isEmpty()){
                    Toast.makeText(ClienteRastreo.this, "Ingresa el numero de guia", Toast.LENGTH_LONG).show();
                } else {
                    mostrarEnvio();
                }
            }
        });


    }
    
    private interface Rastrear{
        @GET("envios/numeroguia/{numEnvio}")
        Call<String> getbyNumEnvio(@Path("numEnvio") String numEnvio);
    }

    private class Envio{

        public String numeroguia, estatusenvio;
        public Destinatario destinatario;

        public Envio() {
        }

        public Envio(String numeroguia, String estatusenvio, Destinatario destinatario) {
            this.numeroguia = numeroguia;
            this.estatusenvio = estatusenvio;
            this.destinatario = destinatario;
        }

        public String getNumeroguia() {
            return numeroguia;
        }

        public void setNumeroguia(String numeroguia) {
            this.numeroguia = numeroguia;
        }

        public String getEstatusenvio() {
            return estatusenvio;
        }

        public void setEstatusenvio(String estatusenvio) {
            this.estatusenvio = estatusenvio;
        }

        public Destinatario getDestinatario() {
            return destinatario;
        }

        public void setDestinatario(Destinatario destinatario) {
            this.destinatario = destinatario;
        }
    }

    private class Destinatario{
        String nombredestinatario, direcciondestinatario, cpdestinatario, ciudaddestinatario, municipiodestinatario, estadodestinatario, referencia;

        public Destinatario() {
        }

        public Destinatario(String nombredestinatario, String direcciondestinatario, String cpdestinatario, String ciudaddestinatario, String municipiodestinatario, String estadodestinatario, String referencia) {
            this.nombredestinatario = nombredestinatario;
            this.direcciondestinatario = direcciondestinatario;
            this.cpdestinatario = cpdestinatario;
            this.ciudaddestinatario = ciudaddestinatario;
            this.municipiodestinatario = municipiodestinatario;
            this.estadodestinatario = estadodestinatario;
            this.referencia = referencia;
        }

        public String getNombredestinatario() {
            return nombredestinatario;
        }

        public void setNombredestinatario(String nombredestinatario) {
            this.nombredestinatario = nombredestinatario;
        }

        public String getDirecciondestinatario() {
            return direcciondestinatario;
        }

        public void setDirecciondestinatario(String direcciondestinatario) {
            this.direcciondestinatario = direcciondestinatario;
        }

        public String getCpdestinatario() {
            return cpdestinatario;
        }

        public void setCpdestinatario(String cpdestinatario) {
            this.cpdestinatario = cpdestinatario;
        }

        public String getCiudaddestinatario() {
            return ciudaddestinatario;
        }

        public void setCiudaddestinatario(String ciudaddestinatario) {
            this.ciudaddestinatario = ciudaddestinatario;
        }

        public String getMunicipiodestinatario() {
            return municipiodestinatario;
        }

        public void setMunicipiodestinatario(String municipiodestinatario) {
            this.municipiodestinatario = municipiodestinatario;
        }

        public String getEstadodestinatario() {
            return estadodestinatario;
        }

        public void setEstadodestinatario(String estadodestinatario) {
            this.estadodestinatario = estadodestinatario;
        }

        public String getReferencia() {
            return referencia;
        }

        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }
    }

    private void mostrarEnvio(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.20.55.52:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ClienteRastreo.Rastrear envio = retrofit.create(ClienteRastreo.Rastrear.class);


        Call<String> call = envio.getbyNumEnvio(numGuia);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = (String) response.body();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(body);

                    Envio envioResponse = new Envio();
                    JSONArray jEnvios = jsonObject.getJSONArray("envios");
                    JSONObject jEnvio = jEnvios.getJSONObject(0);

                    envioResponse.estatusenvio = jEnvio.getString("estatusenvio");

                    String status = envioResponse.estatusenvio.toString();


                    JSONObject jDestinatario = jEnvio.getJSONObject("destinatario");
                    envioResponse.destinatario = new Destinatario();
                    envioResponse.destinatario.nombredestinatario = jDestinatario.getString("nombredestinatario");
                    envioResponse.destinatario.direcciondestinatario = jDestinatario.getString("direcciondestinatario");
                    envioResponse.destinatario.cpdestinatario = jDestinatario.getString("cpdestinatario");
                    envioResponse.destinatario.ciudaddestinatario = jDestinatario.getString("ciudaddestinatario");
                    envioResponse.destinatario.referencia = jDestinatario.getString("referencia");
                    String nombre = envioResponse.destinatario.nombredestinatario.toString();
                    String direccion = envioResponse.destinatario.direcciondestinatario.toString() + " CP " +
                            envioResponse.destinatario.cpdestinatario.toString() + ", " +
                            envioResponse.destinatario.ciudaddestinatario.toString();
                    String referencia = envioResponse.destinatario.referencia.toString();

                    txtEstatusCli.setText(status);
                    txtEstatusCli.refreshDrawableState();

                    txtRemitenteCli.setText(envioResponse.destinatario.nombredestinatario);
                    txtRemitenteCli.refreshDrawableState();

                    txtDireccionCli.setText(direccion);
                    txtDireccionCli.refreshDrawableState();

                    txtReferenciaCli.setText(referencia);
                    txtReferenciaCli.refreshDrawableState();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ClienteRastreo.this, "Error de conexion", Toast.LENGTH_LONG).show();
            }
        });

    }
}