package com.cynomex.cynomys.cynomys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Robo3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robo3);
    }

    public void Siguentevista(View view){
        //Intent intent = new Intent(this,Robo3.class);
        //startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
    }

    public void Anteriorvista(View view){
        this.finish();
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }
}
