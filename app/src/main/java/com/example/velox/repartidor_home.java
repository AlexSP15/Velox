package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class repartidor_home extends AppCompatActivity {
    private ImageView btnRepEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repartidor_home);
        bindUI();
        btnRepEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ){
                gotoMain();
            }

        });
    }
    private void bindUI(){
        btnRepEntrega = (ImageView) findViewById(R.id.btnRepEntrega);
    }
    private void gotoMain(){
        Intent intent = new Intent(repartidor_home.this,Repartidor_Rastreo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}