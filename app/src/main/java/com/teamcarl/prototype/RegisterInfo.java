package com.teamcarl.prototype;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterInfo extends Activity {

    TextView userID, brandNumber, password, rInfoBD, uIDBD;
    String us, br, pa;
    ImageButton login;
    int countBD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        login = (ImageButton) findViewById(R.id.login);
        userID = (TextView) findViewById(R.id.userIDActual);
        brandNumber = (TextView) findViewById(R.id.brandNumberActual);
        password = (TextView) findViewById(R.id.passwordActual);
        us = (String) intent.getExtras().getString("userID");
        br = (String) intent.getExtras().getString("brandNumber");
        pa = (String) intent.getExtras().getString("password");
        Toast.makeText(getApplicationContext(), us+" "+br+" "+pa, Toast.LENGTH_SHORT).show();
        userID.setText(us);
        brandNumber.setText(br);
        password.setText(pa);

        //BACKDOOR
        rInfoBD = (TextView) findViewById(R.id.RegisterInfo);
        uIDBD = (TextView) findViewById(R.id.userID);

        rInfoBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countBD++;
            }
        });

        uIDBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countBD > 5)
                {
                    Intent intent = new Intent(RegisterInfo.this, Register.class);
                    startActivity(intent);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterInfo.this, login.class);
                startActivity(intent);
            }
        });
    }
}
