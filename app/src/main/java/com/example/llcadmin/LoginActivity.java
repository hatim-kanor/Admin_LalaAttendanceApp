package com.example.llcadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.llcadmin.MainActivity.PREFS_MOBILE;
import static com.example.llcadmin.MainActivity.PREFS_NAME;
import static com.example.llcadmin.MainActivity.myPrefs;

public class LoginActivity extends AppCompatActivity {
    EditText mMobile;
    Button mButton;
    String sp_mobile,sp_name;

    String et_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LOGIN");

        getData();



        mMobile = findViewById(R.id.et_mobile);
        mButton = findViewById(R.id.btn_login);




        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_mobile = mMobile.getText().toString();
                if(et_mobile.trim().length() <= 0)
                {
                    mMobile.setError("Please Enter Mobile Number .. 10 Digits");
                }
                else
                {
                        if(et_mobile.equals(sp_mobile)) {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),AdminHome.class);
                            startActivity(i);

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    private void getData() {
        SharedPreferences pref = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        sp_name = pref.getString(PREFS_NAME,null);
        sp_mobile = pref.getString(PREFS_MOBILE,null);
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
