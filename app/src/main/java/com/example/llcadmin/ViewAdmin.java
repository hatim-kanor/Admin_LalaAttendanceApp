package com.example.llcadmin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAdmin extends AppCompatActivity implements AdminAdapter.OnItemClickListener{
    RecyclerView recyclerView;

    private static String URL = "https://llc-attendance.000webhostapp.com/fetch_adminlist.php";
    private static String URL2 = "https://llc-attendance.000webhostapp.com/deleteAdmin.php";
    //a list to store all the products
    List<Admin> adminList;
    String itemSelected;
    String ci_id,ci_name,ci_lname,ci_email,ci_mobile,ci_imei;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("View Admin Details");


        recyclerView = findViewById(R.id.recyclerView);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //LinearLayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //initializing the productlist
        adminList = new ArrayList<>();

        loadJSON();





    }

    private void loadJSON() {
       // progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray login = jsonObject.getJSONArray("faculty");
                            if(success.equals("1"))
                            {

                                for(int i = 0;i< login.length() ; i++)
                                {
                                    JSONObject object = login.getJSONObject(i);
                                    adminList.add(new Admin(
                                            object.getString("id"),
                                            object.getString("name"),
                                            object.getString("lname"),
                                            object.getString("imei"),
                                            object.getString("mobile"),
                                            object.getString("email")
                                    ));



                                }
                               // progressBar.setVisibility(View.GONE);
                                //creating adapter object and setting it to recyclerview
                                AdminAdapter adapter = new AdminAdapter(getApplicationContext(), adminList);
                                recyclerView.setAdapter(adapter);
                               adapter.setOnItemClickListener(ViewAdmin.this);




                            }
                            else if(success.equals("0"))
                            {
                                //progressBar.setVisibility(View.GONE);
                                Toast.makeText(ViewAdmin.this, "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
                            }
                            else if(success.equals("2"))
                            {
                               // progressBar.setVisibility(View.GONE);
                                Toast.makeText(ViewAdmin.this, "Failed to Load Data from server", Toast.LENGTH_SHORT).show();
                            }




                        }catch(Exception e)
                        {
                           // progressBar.setVisibility(View.GONE);
                            Toast.makeText(ViewAdmin.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  progressBar.setVisibility(View.GONE);
                Toast.makeText(ViewAdmin.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();


            }
        });


        Volley.newRequestQueue(this).add(stringRequest);

    }


    @Override
    public void onItemClick(final int position) {
//        Admin clickedItem = adminList.get(position);
//        ci_id = clickedItem.getId();
//        ci_name = clickedItem.getName();
//        ci_lname = clickedItem.getLname();
//        ci_email = clickedItem.getEmail();
//        ci_mobile = clickedItem.getMobile();
//        ci_imei = clickedItem.getImei();
//
//
//        tv_profile.setVisibility(View.VISIBLE);
//        tv_name.setVisibility(View.VISIBLE);
//        tv_email.setVisibility(View.VISIBLE);
//        tv_imei.setVisibility(View.VISIBLE);
//        tv_mobile.setVisibility(View.VISIBLE);
//        name.setVisibility(View.VISIBLE);
//        email.setVisibility(View.VISIBLE);
//        mobile.setVisibility(View.VISIBLE);
//        imei.setVisibility(View.VISIBLE);
//        bUpdate.setVisibility(View.VISIBLE);
//        bDelete.setVisibility(View.VISIBLE);
//
//
//        name.setText(ci_name + " " + ci_lname);
//        email.setText(ci_email);
//        mobile.setText(ci_mobile);
//        imei.setText(ci_imei);

//        bUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getApplicationContext(),UpdateAdmin.class);
//                i.putExtra("name",name.getText().toString());
//                i.putExtra("email",email.getText().toString());
//                i.putExtra("mobile",mobile.getText().toString());
//                i.putExtra("imei",imei.getText().toString());
//                i.putExtra("fname", ci_name);
//                i.putExtra("lname",ci_lname);
//                i.putExtra("a_id",ci_id);
//                startActivity(i);
//
//            }
//        });
//
//        bDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final AlertDialog dialog = new AlertDialog.Builder(ViewAdmin.this)
//                        .setTitle("Alert")
//                        .setMessage("Are you sure you want to delete " + ci_name + " " + ci_lname)
//                        .setPositiveButton("No" , null)
//                        .setNegativeButton("Yes" , null)
//                        .show();
//                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positiveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//                negativeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                        progressBar.setVisibility(View.VISIBLE);
//                        bUpdate.setVisibility(View.GONE);
//                        bDelete.setVisibility(View.GONE);
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response2) {
//                                        try {
//
//
//                                            JSONObject jsonObject = new JSONObject(response2);
//                                            String success = jsonObject.getString("success");
//                                            if(success.equals("1"))
//                                            {
//                                                progressBar.setVisibility(View.GONE);
//                                                showMessage("Message", "Admin Deleted Successfully");
//                                                Intent i = new Intent(ViewAdmin.this,ViewAdmin.class);
//                                                startActivity(i);
//                                            }
//                                            else
//                                            {
//                                                progressBar.setVisibility(View.GONE);
//                                                showMessage("Message" , "Failed to Delete Admin");
//                                            }
//
//
//
//
//                                        }catch(Exception e)
//                                        {
//                                            progressBar.setVisibility(View.GONE);
//                                            Toast.makeText(ViewAdmin.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
//
//                                        }
//
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                progressBar.setVisibility(View.GONE);
//                                Toast.makeText(ViewAdmin.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
//
//
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> params = new HashMap<>();
//                                params.put("a_id",ci_id);
//                                return params;
//
//                            }
//                        };
//                        RequestQueue requestQueue = Volley.newRequestQueue(ViewAdmin.this);
//                        requestQueue.add(stringRequest);
//
//
//
//
//                    }
//                });
//            }
//        });



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.add:
                Intent i = new Intent(ViewAdmin.this,AddAdmin.class);
                startActivity(i);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(ViewAdmin.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ViewAdmin.this,AdminHome.class);
        startActivity(i);
        finish();
    }
}
