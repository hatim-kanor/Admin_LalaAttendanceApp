package com.example.llcadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {
    TextView tv_viewprofile;
    Button btnAdmin,btnStream,btnOnline,btnStaff;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Admin Home");

        tv_viewprofile = findViewById(R.id.view_profile);
        btnAdmin = findViewById(R.id.btn_admin);
        btnStream = findViewById(R.id.btn_stream);
        btnOnline = findViewById(R.id.btn_google);
        btnStaff = findViewById(R.id.btn_staff);

        btnStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AdminProfile.class);
                startActivity(i);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(),ViewAdmin.class);
                startActivity(it);

            }
        });

        btnStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent staff = new Intent(AdminHome.this,addSubject_Staff.class);
                startActivity(staff);
                finish();
            }
        });

        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent online = new Intent(AdminHome.this,NewSubject.class);
                startActivity(online);
                finish();
            }
        });





    }
}
