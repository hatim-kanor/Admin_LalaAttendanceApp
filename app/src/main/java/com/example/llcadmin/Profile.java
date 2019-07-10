package com.example.llcadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    TextView tv_profile,tv_name,tv_email,tv_mobile,tv_imei,name,email,mobile,imei;
    String fname,lname,a_id;
    Button bUpdate,bDelete;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");


        tv_profile = findViewById(R.id.profile);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email = findViewById(R.id.tv_email);
        tv_imei = findViewById(R.id.tv_imei);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        imei = findViewById(R.id.imei);
        bUpdate = findViewById(R.id.btn_Update);
        bDelete = findViewById(R.id.btn_Back);
        progressBar = findViewById(R.id.progress_Bar);

        progressBar.setVisibility(View.GONE);


        Intent i = getIntent();

        final String i_name=i.getStringExtra("name");
        final String i_email = i.getStringExtra("email");
        final String i_mobile = i.getStringExtra("mobile");
        final String i_imei = i.getStringExtra("imei");
        fname = i.getStringExtra("fname");
        lname = i.getStringExtra("lname");
        a_id = i.getStringExtra("a_id");

        name.setText(i_name);
        email.setText(i_email);
        mobile.setText(i_mobile);
        imei.setText(i_imei);

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UpdateAdmin.class);
                i.putExtra("name",i_name);
                i.putExtra("email",i_email);
                i.putExtra("mobile",i_mobile);
                i.putExtra("imei",i_imei);
                i.putExtra("fname", fname);
                i.putExtra("lname",lname);
                i.putExtra("a_id",a_id);
                startActivity(i);
                finish();
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(Profile.this,ViewAdmin.class);
                startActivity(goBack);
                finish();
            }
        });






    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Click on the back button", Toast.LENGTH_SHORT).show();
    }
}
