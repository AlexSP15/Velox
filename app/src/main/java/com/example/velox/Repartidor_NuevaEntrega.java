package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;


public class Repartidor_NuevaEntrega extends AppCompatActivity {

    private Spinner tipopaquete;
    private Spinner tipoenvio;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repartidor_nueva_entrega);
        tipopaquete= (Spinner) findViewById(R.id.spinner_TipoPaqRep);
        tipoenvio=(Spinner) findViewById(R.id.spinner_TipoEnvioRep) ;

        String [] opciones = {"Caja","Bolsa","Megacaja","MiniCaja"};
        ArrayAdapter <String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);
        tipopaquete.setAdapter(adapter);

        String[]opcionesenvio={"Envio Express","Envio Estandar","Envíos frágiles o pesados"};
        ArrayAdapter <String> envio =new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item,opcionesenvio);
tipoenvio.setAdapter(envio);
    }
}