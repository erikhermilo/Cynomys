package com.cynomex.cynomys.cynomys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Herido2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herido2);
    }

    public void Anteriorvista(View view){
        this.finish();
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
