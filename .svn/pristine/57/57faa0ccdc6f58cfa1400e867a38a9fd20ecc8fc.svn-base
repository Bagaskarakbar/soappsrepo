package com.averin.SOAP.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.averin.SOAP.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        final Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    Intent toMenus = new Intent(SplashActivity.this, PilihTipeRegistrasiActivity.class);
                    startActivity(toMenus);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


}
