package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class InicioCliente extends AppCompatActivity {
    private ImageView ingresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_cliente);
        bindUI();
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });

    }
    private void bindUI() {
        ingresar = (ImageView)  findViewById(R.id.btnRastrearCliente);
    }
    private void gotoMain() {
        Intent intent = new Intent(InicioCliente.this,ClienteRastreo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    }