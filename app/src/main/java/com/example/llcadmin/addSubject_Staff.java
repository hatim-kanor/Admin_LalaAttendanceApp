package com.example.llcadmin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addSubject_Staff extends AppCompatActivity {
    private static String URL = "https://llc-attendance.000webhostapp.com/getSubject.php";
    private static String URL2 = "https://llc-attendance.000webhostapp.com/getDiv.php";
    List<addSubject_class> goodModelArrayList;
    List<addSubject_class> subject = new ArrayList<addSubject_class>();
    ArrayList<addSubject_class> subject_array;
    ArrayList<String> sub_array;
    JSONArray login;
    ProgressBar progressBar;


    TextView getData,Yr,Str,Rle;
    Button btnAddStaff;

    List<addDiv_class> divClass;
    List<String> div = new ArrayList<>();
    Spinner sp_subject,sp_div;
    String fname,lname,email,mobile,year,role,stream,r_sub,r_div;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsubject_staff);
        getData = (TextView)findViewById(R.id.tv_GetSubject);
        sp_subject = (Spinner) findViewById(R.id.sp_Subject);
        sp_div = (Spinner)findViewById(R.id.sp_div);

        Yr = (TextView)findViewById(R.id.Yr);
        Str = (TextView)findViewById(R.id.Str);
        Rle = (TextView)findViewById(R.id.Rle);
        progressBar = new ProgressBar(addSubject_Staff.this);

        btnAddStaff = (Button)findViewById(R.id.btnAddStaff);
        subject_array = new ArrayList<addSubject_class>();
        sub_array = new ArrayList<String>();

        getData.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);



//        Intent i = getIntent();
//        fname = i.getStringExtra("fname");
//        lname = i.getStringExtra("lname");
//        email = i.getStringExtra("email");
//        mobile = i.getStringExtra("mobile");
//        year = i.getStringExtra("year");
//        role = i.getStringExtra("role");
//        stream = i.getStringExtra("stream");

        ActionBar action = getSupportActionBar();
        action.setTitle("Hatim");

        Yr.setText("TY BSCIT");
        Rle.setText("Co-ordinator");

        year = Yr.getText().toString();
        stream = Yr.getText().toString();


        loadData();



        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubject_class addStaff_class = (addSubject_class) sp_subject.getSelectedItem();
                r_sub = addStaff_class.getS_name();

                if(r_sub.equals(" "))
                {
                    Toast.makeText(addSubject_Staff.this, "Please select year", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast t1 = Toast.makeText(addSubject_Staff.this,"Subject: "+ r_sub , Toast.LENGTH_LONG);
                    t1.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    t1.show();

                  bringData();
                }






            }
        });

        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiv_class addDiv_class = (addDiv_class) sp_div.getSelectedItem();
                r_div = addDiv_class.getDiv();
                if(r_div.equals(" "))
                {
                    Toast.makeText(addSubject_Staff.this, "Please Select Subject/Division", Toast.LENGTH_SHORT).show();
                }
                else {
                    resultDiv();
                }
            }
        });


    }

    private void resultDiv() {
        addDiv_class addDiv_class = (addDiv_class) sp_div.getSelectedItem();
        r_div = addDiv_class.getDiv();

        if(r_div.equals(" "))
        {
            Toast.makeText(this, "Please select Div", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast t_div = Toast.makeText(addSubject_Staff.this,"Div Selected: " + r_div,Toast.LENGTH_LONG);
            t_div.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
            t_div.show();
        }


    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             try{
                 JSONObject jsonObject = new JSONObject(response);
                 goodModelArrayList = new ArrayList<>();
                 JSONArray jsonArray = jsonObject.getJSONArray("subject");
                 if(jsonObject.getString("success").equals("1"))
                 {
                     for(int i=0;i<jsonArray.length();i++)
                     {
                         addSubject_class addSubjectClass = new addSubject_class();
                         JSONObject object = jsonArray.getJSONObject(i);

                         addSubjectClass.setS_name(object.getString("subject_name"));

                         goodModelArrayList.add(addSubjectClass);
                     }
                 }
                 else
                 {
                     Toast.makeText(addSubject_Staff.this, "Not equal to 1", Toast.LENGTH_SHORT).show();
                     progressBar.setVisibility(View.GONE);
                 }




                        ArrayAdapter<addSubject_class> spinnerArrayAdapter = new ArrayAdapter<addSubject_class>(addSubject_Staff.this, android.R.layout.simple_spinner_item, goodModelArrayList);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        sp_subject.setAdapter(spinnerArrayAdapter);
                        getData.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);



                }
                catch(JSONException e)
                {
                    Toast.makeText(addSubject_Staff.this, "JSON Exception " + e.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
                catch(Exception ex)
                {
                    Toast.makeText(addSubject_Staff.this, "Exception: " + ex.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addSubject_Staff.this, "Volley Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("year","TY");
                params.put("stream","BSCIT");
                return params;
            }
        } ;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void bringData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray login = jsonObject.getJSONArray("subject");
                            divClass = new ArrayList<>();
                            if(success.equals("1"))
                            {
                                for(int i = 0;i< login.length() ; i++) {
                                    addDiv_class div_class = new addDiv_class();
                                    JSONObject object = login.getJSONObject(i);
                                    div_class.setDiv(object.getString("s_name"));
                                    divClass.add(div_class);


                                }

                                ArrayAdapter<addDiv_class> spinnerArrayAdapter = new ArrayAdapter<addDiv_class>(addSubject_Staff.this, android.R.layout.simple_spinner_item, divClass);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                sp_div.setAdapter(spinnerArrayAdapter);




                            }
                            else if(success.equals("0"))
                            {
                                Toast.makeText(addSubject_Staff.this, "Failed to Open the App \n Contact the Admin for Details", Toast.LENGTH_SHORT).show();
                            }
                            else if(success.equals("2"))
                            {
                                Toast.makeText(addSubject_Staff.this, "Login Failed \n Value could not be reached to the server", Toast.LENGTH_SHORT).show();
                            }

                        }catch(Exception e)
                        {
                            Toast.makeText(addSubject_Staff.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addSubject_Staff.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("year","TY");
                params.put("stream","BSCIT");
                params.put("subject",r_sub);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
