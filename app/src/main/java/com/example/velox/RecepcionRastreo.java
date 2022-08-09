package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RecepcionRastreo extends AppCompatActivity {
    private Button btnConsultar;
    private EditText txtGuiaRasRece;
    private TextView txtEstatusRece, txtRemitenteRece, txtDireccionRece, txtReferenciaRece;

    String numGuia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion_rastreo);

        btnConsultar = (Button) findViewById(R.id.btnConsultarRRece);
        txtGuiaRasRece = (EditText) findViewById(R.id.txtGuiaRasRece);
        txtEstatusRece = (TextView) findViewById(R.id.txtEstatusRece);
        txtRemitenteRece = (TextView) findViewById(R.id.txtRemitenteRece);
        txtDireccionRece = (TextView) findViewById(R.id.txtDireccionRece);
        txtReferenciaRece = (TextView) findViewById(R.id.txtReferenciaRece);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numGuia = txtGuiaRasRece.getText().toString();
                if (numGuia.isEmpty()){
                    Toast.makeText(RecepcionRastreo.this, "Ingresa el numero de guia", Toast.LENGTH_LONG).show();
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
                .baseUrl("http://192.168.32.50:4000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Rastrear envio = retrofit.create(Rastrear.class);


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

                    txtEstatusRece.setText(status);
                    txtEstatusRece.refreshDrawableState();

                    txtRemitenteRece.setText(envioResponse.destinatario.nombredestinatario);
                    txtRemitenteRece.refreshDrawableState();

                    txtDireccionRece.setText(direccion);
                    txtDireccionRece.refreshDrawableState();

                    txtReferenciaRece.setText(referencia);
                    txtReferenciaRece.refreshDrawableState();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RecepcionRastreo.this, "Error de conexion", Toast.LENGTH_LONG).show();
            }
        });

    }
}