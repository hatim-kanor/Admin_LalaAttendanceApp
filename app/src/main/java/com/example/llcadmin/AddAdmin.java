package com.example.llcadmin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

public class AddAdmin extends AppCompatActivity {
    EditText et_fname,et_lname,et_email,et_mobile,et_imei;
    Button btnAdd;
    String etFname,etLname,etEmail,etMobile,etImei;
    ProgressBar loading;


    private static String URL = "https://llc-attendance.000webhostapp.com/addAdmin.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_admin);

        ActionBar action = getSupportActionBar();
        action.setTitle("Add Admin");


        et_fname = findViewById(R.id.fname);
        et_lname = findViewById(R.id.lname);
        et_mobile = findViewById(R.id.number);
        et_email = findViewById(R.id.email);
        et_imei = findViewById(R.id.imei);
        btnAdd = findViewById(R.id.btn_addAdmin);
        loading = findViewById(R.id.loading);

        loading.setVisibility(GONE);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFname = et_fname.getText().toString();
                etLname = et_lname.getText().toString();
                etMobile = et_mobile.getText().toString();
                etEmail = et_email.getText().toString();
                etImei = et_imei.getText().toString();


                if(etFname.trim().length() <= 0 || etLname.trim().length() <= 0 || etMobile.trim().length() <= 0 ||
                        etEmail.trim().length() <=0 ||
                        etImei.trim().length() <= 0)
                {
                    Toast toast = Toast.makeText(AddAdmin.this,"Please fill all the details",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                    loading.setVisibility(GONE);
                    btnAdd.setVisibility(View.VISIBLE);

                }
                else
                {
                    btnAdd.setVisibility(GONE);
                    loading.setVisibility(View.VISIBLE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if(jsonObject.names().get(0).equals("success")) {
                                                showMessage("Message","Admin Added Successfully");
                                                loading.setVisibility(View.GONE);
                                                btnAdd.setVisibility(View.VISIBLE);

                                                Intent i  = new Intent(getApplicationContext(),ViewAdmin.class);
                                                startActivity(i);
                                            }
                                            else if(jsonObject.names().get(0).equals("fail"))
                                            {
                                                loading.setVisibility(GONE);
                                                btnAdd.setVisibility(GONE);
                                                showMessage("Message","Admin Already Added");

                                            }
                                            else if(jsonObject.names().get(0).equals("errror"))
                                            {
                                                loading.setVisibility(View.GONE);
                                                btnAdd.setVisibility(View.VISIBLE);
                                                showMessage("Message","Failed to Add Admin \nKindly try after sometime");
                                            }
                                        } catch (JSONException e1) {
                                            loading.setVisibility(View.GONE);
                                            btnAdd.setVisibility(View.VISIBLE);
                                            e1.printStackTrace();
                                        }




                                    }catch(Exception e)
                                    {
                                        loading.setVisibility(View.GONE);
                                        btnAdd.setVisibility(View.VISIBLE);
                                        Toast.makeText(AddAdmin.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(AddAdmin.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();


                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("fname",etFname);
                            params.put("lname",etLname);
                            params.put("mobile",etMobile);
                            params.put("email",etEmail);
                            params.put("imei",etImei);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(AddAdmin.this);
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(AddAdmin.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
