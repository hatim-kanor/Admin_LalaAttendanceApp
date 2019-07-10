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
import android.widget.TextView;
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

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

public class UpdateAdmin extends AppCompatActivity {
    String etMobile,etEmail,etImei,fname,lname,a_id;


    private static String URL = "https://llc-attendance.000webhostapp.com/updateAdmin.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_admin);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Admin");

        Intent i = getIntent();

        String name=i.getStringExtra("name");
        String email = i.getStringExtra("email");
        String mobile = i.getStringExtra("mobile");
        String imei = i.getStringExtra("imei");
        fname = i.getStringExtra("fname");
        lname = i.getStringExtra("lname");
        a_id = i.getStringExtra("a_id");

        Toast.makeText(this, "Fname: "+ fname + "\nLname: "+ lname + "\n ID: " + a_id, Toast.LENGTH_SHORT).show();


        TextView tv_name = (TextView)findViewById(R.id.ua_fName);
        final EditText et_email = (EditText)findViewById(R.id.ua_Email);
        final EditText et_mobile = (EditText)findViewById(R.id.ua_Mobile);
        final EditText et_imei = (EditText)findViewById(R.id.ua_Imei);
        final Button btnUpdate = (Button)findViewById(R.id.ua_BtnUpdate);
        final TextView tv_goBack = (TextView)findViewById(R.id.ua_BtnClose);
        final ProgressBar pb = (ProgressBar)findViewById(R.id.pb);

        pb.setVisibility(View.GONE);

        tv_name.setText(name);
        et_email.setText(email);
        et_mobile.setText(mobile);
        et_imei.setText(imei);

        tv_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(UpdateAdmin.this,ViewAdmin.class);
                startActivity(it);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMobile = et_mobile.getText().toString();
                etEmail = et_email.getText().toString();
                etImei = et_imei.getText().toString();


                if(etMobile.trim().length() <= 0 || etEmail.trim().length() <= 0 || etImei.trim().length() <= 0 )

                {
                    Toast toast = Toast.makeText(UpdateAdmin.this, "Please fill all the details", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    pb.setVisibility(GONE);
                    btnUpdate.setVisibility(View.VISIBLE);

                }
                else
                {
                    btnUpdate.setVisibility(GONE);
                    pb.setVisibility(View.VISIBLE);
                    tv_goBack.setVisibility(GONE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String success = jsonObject.getString("success");

                                                if (success.equals("1")) {
                                                    showMessage("Message", "Admin Updated Successfully");
                                                    pb.setVisibility(View.GONE);
                                                    btnUpdate.setVisibility(View.VISIBLE);
                                                    Intent i = new Intent(UpdateAdmin.this,ViewAdmin.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                                if (success.equals("2")) {
                                                    pb.setVisibility(GONE);
                                                    btnUpdate.setVisibility(View.VISIBLE);
                                                    tv_goBack.setVisibility(View.VISIBLE);
                                                    showMessage("Message", "Admin with Mobile or Imei already Exists");

                                                }
                                                if (success.equals("3")) {
                                                    pb.setVisibility(View.GONE);
                                                    btnUpdate.setVisibility(View.VISIBLE);
                                                    tv_goBack.setVisibility(View.VISIBLE);
                                                    showMessage("Message", "Failed to Update Admin \nKindly try after sometime");
                                                }

                                            }
                                            catch (JSONException e1) {
                                                pb.setVisibility(View.GONE);
                                                btnUpdate.setVisibility(View.VISIBLE);
                                                tv_goBack.setVisibility(View.VISIBLE);
                                                Toast.makeText(UpdateAdmin.this, "Error: " + e1.toString(), Toast.LENGTH_SHORT).show();
                                            }




                                    } catch (Exception e) {
                                        pb.setVisibility(View.GONE);
                                        btnUpdate.setVisibility(View.VISIBLE);
                                        tv_goBack.setVisibility(View.VISIBLE);
                                        Toast.makeText(UpdateAdmin.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }



                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pb.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            tv_goBack.setVisibility(View.VISIBLE);
                            Toast.makeText(UpdateAdmin.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();


                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();

                            params.put("a_id",a_id);
                            params.put("fname",fname);
                            params.put("lname",lname);
                            params.put("mobile",etMobile);
                            params.put("email",etEmail);
                            params.put("imei",etImei);

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }

            }
        });

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateAdmin.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
    }
}
