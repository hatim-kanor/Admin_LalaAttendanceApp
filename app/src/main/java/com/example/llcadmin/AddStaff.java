package com.example.llcadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class AddStaff extends AppCompatActivity {

    Spinner sp_year, sp_stream, sp_role,sp_subject,sp_div;
    String r_year, r_stream, r_role, fname, lname, email, mobile, result, role, role_p, v1;
    EditText Fname, Lname, Email, Mobile;
    RadioButton rbYear, rbRole, rbYear_1, rbYear_2, rbYear_3, rbRole_1, rbRole_2, rbRole_3;
    Button btn_Add;
    ArrayAdapter<AddStaff_Class> arrayAdapter;
    boolean checkedYear, checkedRole;
    RadioGroup r1, r2;
    private static String URL = "https://llc-attendance.000webhostapp.com/addStaff.php";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstaff);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Staff");


        btn_Add = (Button) findViewById(R.id.btnAdd);
        Email = (EditText) findViewById(R.id.EmailID);
        Fname = (EditText) findViewById(R.id.Fname);
        Lname = (EditText) findViewById(R.id.Lname);
        Mobile = (EditText) findViewById(R.id.MobileNumber);
        sp_stream = (Spinner) findViewById(R.id.sp_stream);
        rbYear_1 = (RadioButton) findViewById(R.id.rb_FY);
        rbYear_2 = (RadioButton) findViewById(R.id.rb_SY);
        rbYear_3 = (RadioButton) findViewById(R.id.rb_TY);
        rbRole_1 = (RadioButton) findViewById(R.id.rb_Coordinator);
        rbRole_2 = (RadioButton) findViewById(R.id.rb_Faculty);
        rbRole_3 = (RadioButton) findViewById(R.id.rb_Visiting);
        r1 = (RadioGroup) findViewById(R.id.rg_Year);
        r2 = (RadioGroup) findViewById(R.id.rg_role);




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
        sp_stream.setAdapter(arrayAdapter);


        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStaff_Class addStaff_class = (AddStaff_Class) sp_stream.getSelectedItem();
                r_stream = addStaff_class.getStream();


                if (Fname.getText().toString().trim().length() <= 0 ||
                        Lname.getText().toString().trim().length() <= 0 || Email.getText().toString().trim().length() <= 0
                        || Mobile.getText().toString().trim().length() <= 0) {
                    Toast toast = Toast.makeText(AddStaff.this, "Please fill all the details", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }

                if (r_stream.equals(" ")) {
                    Toast.makeText(AddStaff.this, "Please select Stream", Toast.LENGTH_SHORT).show();
                }
                if (!checkedYear) {
                    r1.setFocusable(true);
                    Toast.makeText(AddStaff.this, "Please select Year", Toast.LENGTH_SHORT).show();
                }
                if (!checkedRole) {
                    r2.setFocusable(true);
                    Toast.makeText(AddStaff.this, "Please select Designation", Toast.LENGTH_SHORT).show();
                } else {
                    doAction();
                }


            }
        });





    }


    public void onYearSelected(View v) {
        checkedYear = ((RadioButton) v).isChecked();
        v1 = String.valueOf(checkedYear);

        switch (v.getId()) {
            case R.id.rb_FY:
                if (checkedYear) {
                    rbYear_1.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbYear_2.setTypeface(null, Typeface.NORMAL);
                    rbYear_3.setTypeface(null, Typeface.NORMAL);
                    result = "FY";
                    break;
                }
            case R.id.rb_SY:
                if (checkedYear) {
                    rbYear_1.setTypeface(null, Typeface.NORMAL);
                    rbYear_2.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbYear_3.setTypeface(null, Typeface.NORMAL);
                    result = "SY";
                    break;
                }
            case R.id.rb_TY:
                if (checkedYear) {
                    rbYear_1.setTypeface(null, Typeface.NORMAL);
                    rbYear_2.setTypeface(null, Typeface.NORMAL);
                    rbYear_3.setTypeface(null, Typeface.BOLD_ITALIC);
                    result = "TY";
                    break;
                }
            default:
                rbYear_1.setTypeface(null, Typeface.NORMAL);
                rbYear_2.setTypeface(null, Typeface.NORMAL);
                rbYear_3.setTypeface(null, Typeface.NORMAL);
                result = "NULL";
                break;
        }

    }

    public void onRoleSelected(View vv) {

        checkedRole = ((RadioButton) vv).isChecked();
        switch (vv.getId()) {
            case R.id.rb_Coordinator:
                if (checkedRole) {
                    rbRole_1.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbRole_2.setTypeface(null, Typeface.NORMAL);
                    rbRole_3.setTypeface(null, Typeface.NORMAL);
                    role = "Co-ordinator";
                    role_p = "1";
                    break;
                }
            case R.id.rb_Faculty:
                if (checkedRole) {
                    rbRole_1.setTypeface(null, Typeface.NORMAL);
                    rbRole_2.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbRole_3.setTypeface(null, Typeface.NORMAL);
                    role = "Faculty";
                    role_p = "2";
                    break;
                }
            case R.id.rb_Visiting:
                if (checkedRole) {
                    rbRole_1.setTypeface(null, Typeface.NORMAL);
                    rbRole_2.setTypeface(null, Typeface.NORMAL);
                    rbRole_3.setTypeface(null, Typeface.BOLD_ITALIC);
                    role = "Visiting";
                    role_p = "3";
                    break;
                }
            default:
                rbRole_1.setTypeface(null, Typeface.BOLD_ITALIC);
                rbRole_2.setTypeface(null, Typeface.NORMAL);
                rbRole_3.setTypeface(null, Typeface.NORMAL);
                role = "null";
                break;
        }

    }

    public void doAction() {
        fname = Fname.getText().toString();
        lname = Lname.getText().toString();
        mobile = Mobile.getText().toString();
        email = Email.getText().toString();
        String msg = "Fname: " + fname + "\nLname: " + lname + "\nEmail: " + email + "\nMobile: " + mobile +
                "\nYear:  " + result + "\nStream: " + r_stream + "\nDesig: " + role_p + " " + role;


        final AlertDialog dialog = new AlertDialog.Builder(AddStaff.this)
                .setTitle("Staff")
                .setMessage("Ae you sure you want to add \n" + msg)
                .setPositiveButton("No", null)
                .setNegativeButton("Yes", null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStaff();

            }
        });


    }

    private void addStaff() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(jsonObject.names().get(0).equals("success"))
                            {
                                Toast t_success = Toast.makeText(AddStaff.this,"Staff Added Sucessfully",Toast.LENGTH_LONG);
                                t_success.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                t_success.show();
                                Intent newIntent  = new Intent(AddStaff.this,addSubject_Staff.class);
                                newIntent.putExtra("fname",fname);
                                newIntent.putExtra("lname",lname);
                                newIntent.putExtra("email",email);
                                newIntent.putExtra("mobile",mobile);
                                newIntent.putExtra("year",result);
                                newIntent.putExtra("stream",r_stream);
                                newIntent.putExtra("role",role);
                                startActivity(newIntent);
                                finish();



                            }
                            else if(jsonObject.names().get(0).equals("fail"))
                            {
                                Toast t_fail = Toast.makeText(AddStaff.this, "Failed to add staff\nStaff Already Assigned for the Year/Stream", Toast.LENGTH_LONG);
                                t_fail.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                t_fail.show();
                            }
                            else if(jsonObject.names().get(0).equals("error"))
                            {
                                Toast t_error = Toast.makeText(AddStaff.this, "Add Staff Failed \nTry After sometime", Toast.LENGTH_LONG);
                                t_error.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                t_error.show();
                            }

                        }catch(Exception e)
                        {
                            Toast.makeText(AddStaff.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddStaff.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("email",email);
                params.put("mobile",mobile);
                params.put("year",result);
                params.put("stream",r_stream);
                params.put("role",role);
                params.put("role_p",role_p);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}