package com.cynomex.cynomys.cynomys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class guias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guias);
    }

    public void onClick_btnRobo(View view) {
        Intent intent = new Intent(this,Robo1.class);
        startActivity(intent);
    }

    public void onClick_btnAsalto(View view) {
        Intent intent = new Intent(this,Asalto1.class);
        startActivity(intent);
    }
    public void onClick_btndisturbio(View view) {
        Intent intent = new Intent(this,Disturbio1.class);
        startActivity(intent);
    }

    public void onClick_btnincendio(View view) {
        Intent intent = new Intent(this,Incendio1.class);
        startActivity(intent);
    }
    public void onClick_btnviolencia(View view) {
        Intent intent = new Intent(this,Violencia1.class);
        startActivity(intent);
    }

    public void onClick_btnsecuestro(View view) {
        Intent intent = new Intent(this,Secuestro1.class);
        startActivity(intent);
    }
    public void onClick_btnacoso(View view) {
        Intent intent = new Intent(this,Acoso1.class);
        startActivity(intent);
    }
    public void onClick_btnherido(View view) {
        Intent intent = new Intent(this,Herido1.class);
        startActivity(intent);
    }
}
