package com.proyecto.centraldetaxi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.centraldetaxi.R;

public class MainActivity extends AppCompatActivity {

    Button mButtonContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_my_app2);

        mButtonContinuar = findViewById(R.id.botonContinuar);

        mButtonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToStart();
            }
        });

    }

    private void goToStart() {

        Intent intent = new Intent(MainActivity.this, InicioAppActivity2.class);
        startActivity(intent);
    }


}