package com.example.llcadmin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewSubject extends AppCompatActivity {
    Spinner spYear,spStream,spSubject,spDiv;
    TextView tvGetData,tvGetDiv,checkStatus,tvLink,tvSheet;
    RadioButton rbFY,rbSY,rbTY;
    ArrayAdapter<AddStaff_Class> arrayAdapter;
    Button submit;
    EditText GoogleFormLink,GoogleSheetLink;

    List<addSubject_class> LinkSubject;
    List<addSubject_class> subject = new ArrayList<addSubject_class>();

    List<addDiv_class> LinkedDiv;
    List<String> div = new ArrayList<>();



    boolean CYear;
    String r1,v1,rStream,str_id,link_subject,link_division,strLink,strSheeet;
    private static String URL = "https://llc-attendance.000webhostapp.com/getLinkSubject.php";
    private static String URL2 = "https://llc-attendance.000webhostapp.com/getLinkDiv.php";
    private static String URL3 = "https://llc-attendance.000webhostapp.com/CheckStatus.php";
    private static String URL4 = "https://llc-attendance.000webhostapp.com/uploadLink.php";


    String checkedY;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_subject);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Links");

        spStream = (Spinner)findViewById(R.id.spinner_stream);
        spSubject = (Spinner)findViewById(R.id.spinner_subject);
        spDiv = (Spinner)findViewById(R.id.spinner_div);
        rbFY = (RadioButton)findViewById(R.id.rbFY);
        rbSY = (RadioButton)findViewById(R.id.rbSY);
        rbTY = (RadioButton)findViewById(R.id.rbTY);
        tvGetData = (TextView)findViewById(R.id.getData);
        tvGetDiv = (TextView)findViewById(R.id.getDiv);
        submit = (Button)findViewById(R.id.btnSubmit);
        checkStatus = (TextView)findViewById(R.id.checkStatus);
        tvLink = (TextView)findViewById(R.id.tvLink);
        tvSheet = (TextView)findViewById(R.id.tvSheet);
        GoogleFormLink = (EditText)findViewById(R.id.GoogleFormLink);
        GoogleSheetLink = (EditText)findViewById(R.id.GoogleSheetLink);

        tvLink.setVisibility(View.GONE);
        tvSheet.setVisibility(View.GONE);
        GoogleFormLink.setVisibility(View.GONE);
        GoogleSheetLink.setVisibility(View.GONE);
        spSubject.setVisibility(View.GONE);
        spDiv.setVisibility(View.GONE);
        tvGetDiv.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        checkStatus.setVisibility(View.GONE);
        setStream();
        tvGetDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDiv();
            }
        });
        checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GoogleFormLink.getText().toString().trim().length() <= 0)
                {
                    GoogleFormLink.setError("Please Fill Data");
                }
                if(GoogleSheetLink.getText().toString().trim().length() <= 0)
                {
                    GoogleSheetLink.setError("Please Fill Data");
                }
                else {
                    submitData();
                }
            }
        });


    }
    /*  Upload Data to Server */
    private void submitData() {
        submit.setVisibility(View.GONE);
        final AlertDialog dialog_alert = new AlertDialog.Builder(NewSubject.this)
                .setTitle("Please Wait")
                .setMessage("Uploading .....")
                .show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1"))
                            {
                                dialog_alert.dismiss();
                                dialog_alert.setTitle("Message");
                                dialog_alert.setMessage("Links Updated Successfully");
                            }
                            if(success.equals("0"))
                            {
                                dialog_alert.dismiss();
                                dialog_alert.setTitle("Warning");
                                dialog_alert.setMessage("Could not Update Links");
                                submit.setVisibility(View.VISIBLE);
                            }
                            if(success.equals("2"))
                            {
                                Toast.makeText(NewSubject.this, "Value did not reach the database file", Toast.LENGTH_SHORT).show();
                                dialog_alert.dismiss();
                                submit.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Toast.makeText(NewSubject.this, "Could not fetch data", Toast.LENGTH_SHORT).show();
                                dialog_alert.dismiss();
                                submit.setVisibility(View.VISIBLE);
                            }

                        }catch(Exception e)
                        {
                            Toast.makeText(NewSubject.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog_alert.dismiss();
                            submit.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewSubject.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                dialog_alert.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stream",rStream);
                params.put("year",r1);
                params.put("s_id",str_id);
                params.put("subject",link_subject);
                params.put("division",link_division);
                params.put("googleForm",GoogleFormLink.getText().toString());
                params.put("googleSheet",GoogleSheetLink.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);





    }
    /*  Upload Data to Server */

    /*  Check if the links are present in database or not */
    private void checkData() {
        addDiv_class LinkedDivClass = (addDiv_class) spDiv.getSelectedItem();
        link_division = LinkedDivClass.getDiv();
        if(link_division.equals(" "))
        {
            Toast.makeText(this, "Please Select Division", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Subject: " + link_subject, Toast.LENGTH_SHORT).show();
            final AlertDialog dialog_alert = new AlertDialog.Builder(NewSubject.this)
                    .setTitle("Please Wait")
                    .setMessage("Check Data in Database")
                    .show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                if(success.equals("0"))
                                {
                                    dialog_alert.dismiss();
                                    dialog_alert.setTitle("Warning");
                                    dialog_alert.setMessage("Data for the Stream and Subject Exists\nKindly Check the Main Data");
                                    dialog_alert.show();
                                }
                                if(success.equals("1"))
                                {
                                    dialog_alert.dismiss();
                                    tvLink.setVisibility(View.VISIBLE);
                                    tvSheet.setVisibility(View.VISIBLE);
                                    GoogleFormLink.setVisibility(View.VISIBLE);
                                    GoogleSheetLink.setVisibility(View.VISIBLE);
                                    submit.setVisibility(View.VISIBLE);
                                }
                                if(success.equals("2"))
                                {
                                    Toast.makeText(NewSubject.this, "Value did not reach the database file", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(NewSubject.this, "Could not fetch data", Toast.LENGTH_SHORT).show();
                                    dialog_alert.dismiss();
                                }

                            }catch(Exception e)
                            {
                                Toast.makeText(NewSubject.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                                dialog_alert.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(NewSubject.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    dialog_alert.dismiss();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("stream",rStream);
                    params.put("year",r1);
                    params.put("s_id",str_id);
                    params.put("subject",link_subject);
                    params.put("division",link_division);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }


    }
    /*  Check if the links are present in database or not */

    /*  get Division from Subject Spinner */
    private void getDiv() {
        addSubject_class addStaff_class = (addSubject_class) spSubject.getSelectedItem();
        link_subject = addStaff_class.getS_name();
        if(link_subject.equals(" "))
        {
            Toast.makeText(this, "Please select Stream", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Subject: " + link_subject, Toast.LENGTH_SHORT).show();
            final AlertDialog dialog_alert = new AlertDialog.Builder(NewSubject.this)
                    .setTitle("Please Wait")
                    .setMessage("Loading Divisions / Subjects ....")
                    .show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray login = jsonObject.getJSONArray("subject");
                                LinkedDiv = new ArrayList<>();
                                if(success.equals("1"))
                                {
                                    for(int i = 0;i< login.length() ; i++) {
                                        addDiv_class div_class = new addDiv_class();
                                        JSONObject object = login.getJSONObject(i);
                                        div_class.setDiv(object.getString("s_name"));
                                        LinkedDiv.add(div_class);


                                    }
                                    dialog_alert.dismiss();
                                    spDiv.setVisibility(View.VISIBLE);
                                    checkStatus.setVisibility(View.VISIBLE);
                                    ArrayAdapter<addDiv_class> spinnerArrayAdapter = new ArrayAdapter<addDiv_class>(NewSubject.this, android.R.layout.simple_spinner_item, LinkedDiv);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    spDiv.setAdapter(spinnerArrayAdapter);
                                }
                                else
                                {
                                    Toast.makeText(NewSubject.this, "Could not Fetch Value", Toast.LENGTH_SHORT).show();
                                    dialog_alert.dismiss();
                                }

                            }catch(Exception e)
                            {
                                Toast.makeText(NewSubject.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                                dialog_alert.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(NewSubject.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    dialog_alert.dismiss();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("stream",rStream);
                    params.put("year",r1);
                    params.put("s_id",str_id);
                    params.put("subject",link_subject);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);



        }
    }
    /*  get Division from Subject Spinner */

    /* Set Stream to set the values of spinner of STREAM on Page Load */
    private void setStream() {


        List<AddStaff_Class> StaffStream = new ArrayList<>();

        AddStaff_Class class_blank = new AddStaff_Class(" ");
        StaffStream.add(class_blank);


        AddStaff_Class class_bms = new AddStaff_Class("BMS");
        StaffStream.add(class_bms);

        AddStaff_Class class_bmm = new AddStaff_Class("BMM");
        StaffStream.add(class_bmm);

        AddStaff_Class class_bbi = new AddStaff_Class("BBI");
        StaffStream.add(class_bbi);

        AddStaff_Class class_baf = new AddStaff_Class("BAF");
        StaffStream.add(class_baf);

        AddStaff_Class class_bscit = new AddStaff_Class("BSCIT");
        StaffStream.add(class_bscit);

        AddStaff_Class class_bfm = new AddStaff_Class("BFM");
        StaffStream.add(class_bfm);


        arrayAdapter = new ArrayAdapter<AddStaff_Class>(this, android.R.layout.simple_spinner_item, StaffStream);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStream.setAdapter(arrayAdapter);

    }
    /* Set Stream to set the values of spinner of STREAM on Page Load */

    /* OnYear Select is used to select the Radio Button of the Year selected */
    public void onYear(View v) {
        CYear = ((RadioButton) v).isChecked();
        checkedY = String.valueOf(CYear);

        switch (v.getId()) {
            case R.id.rbFY:
                if (CYear) {
                    rbFY.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbSY.setTypeface(null, Typeface.NORMAL);
                    rbTY.setTypeface(null, Typeface.NORMAL);
                    r1 = "FY";
                    break;
                }
            case R.id.rbSY:
                if (CYear) {
                    rbFY.setTypeface(null, Typeface.NORMAL);
                    rbSY.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbTY.setTypeface(null, Typeface.NORMAL);
                    r1 = "SY";
                    break;
                }
            case R.id.rbTY:
                if (CYear) {
                    rbFY.setTypeface(null, Typeface.NORMAL);
                    rbSY.setTypeface(null, Typeface.NORMAL);
                    rbTY.setTypeface(null, Typeface.BOLD_ITALIC);
                    r1 = "TY";
                    break;
                }
            default:
                rbFY.setTypeface(null, Typeface.NORMAL);
                rbSY.setTypeface(null, Typeface.NORMAL);
                rbTY.setTypeface(null, Typeface.NORMAL);
                r1 = "NULL";
                break;
        }

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddStaff_Class addStaff_class = (AddStaff_Class) spStream.getSelectedItem();
                rStream = addStaff_class.getStream();

                if (rStream.equals(" ")) {
                    Toast.makeText(NewSubject.this, "Please select Stream", Toast.LENGTH_SHORT).show();
                }
                if(!CYear)
                {
                    Toast.makeText(NewSubject.this, "Please select Year", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ViewPart1();
                }



            }
        });

    }
    /* OnYear Select is used to select the Radio Button of the Year selected */

    /* ViewPart1 is accessed from GetData TextView to get the Subjects.  */
    private void ViewPart1() {
        final AlertDialog dialog_alert = new AlertDialog.Builder(NewSubject.this)
                .setTitle("Message")
                .setMessage("Loading Data")
                .show();


        if(rStream.equals("BMS"))
        {
            str_id = "1";
        }
        if(rStream.equals("BMM"))
        {
            str_id = "2";
        }
        if(rStream.equals("BBI"))
        {
            str_id = "3";
        }
        if(rStream.equals("BAF"))
        {
            str_id = "4";
        }
        if(rStream.equals("BSCIT"))
        {
            str_id = "5";
        }
        if(rStream.equals("BFM"))
        {
            str_id = "7";
        }
        Toast.makeText(this, "Year: " + r1 + "\nStream: " + rStream + "\nID: "+ str_id, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            LinkSubject = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("subject");
                            if(success.equals("1"))
                            {

                                Toast t_success = Toast.makeText(NewSubject.this,"Subject Loaded Successfully",Toast.LENGTH_LONG);
                                t_success.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                t_success.show();
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    addSubject_class addSubjectClass = new addSubject_class();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    addSubjectClass.setS_name(object.getString("subject_name"));

                                    LinkSubject.add(addSubjectClass);
                                }
                                spSubject.setVisibility(View.VISIBLE);
                                tvGetDiv.setVisibility(View.VISIBLE);
                                ArrayAdapter<addSubject_class> spinnerArrayAdapter = new ArrayAdapter<addSubject_class>(NewSubject.this, android.R.layout.simple_spinner_item, LinkSubject);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spSubject.setAdapter(spinnerArrayAdapter);
                                dialog_alert.dismiss();



                            }
                            else
                            {
                                Toast.makeText(NewSubject.this, "Could not fetch Value", Toast.LENGTH_SHORT).show();
                                dialog_alert.dismiss();
                            }

                        }catch(Exception e)
                        {
                            Toast.makeText(NewSubject.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog_alert.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewSubject.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                dialog_alert.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stream",rStream);
                params.put("year",r1);
                params.put("s_id",str_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }
    /* ViewPart1 is accessed from GetData TextView to get the Subjects.  */



}
