package com.example.llcadmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class AdminProfile extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_admin);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Admin Profile");


    }
}
