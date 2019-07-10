package com.example.llcadmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddStaff extends AppCompatActivity {

    Spinner sp_year, sp_stream, sp_role;
    String r_year, r_stream, r_role, fname, lname, email, mobile;
    EditText et_fname, et_lname, et_email, et_mobile;
    Button btn_Add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_staff);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Staff");


        sp_year = (Spinner) findViewById(R.id.sp_year);
        sp_stream = (Spinner) findViewById(R.id.sp_stream);
        sp_role = (Spinner) findViewById(R.id.sp_role);
        btn_Add = (Button) findViewById(R.id.Btn_Add);
        et_email = (EditText) findViewById(R.id.et_fname);
        et_fname = (EditText) findViewById(R.id.fname);
        et_lname = (EditText) findViewById(R.id.lname);
        et_mobile = (EditText) findViewById(R.id.et_mobile);


        List<String> dd_year = new ArrayList<String>();
        dd_year.add(" ");
        dd_year.add("FY");
        dd_year.add("SY");
        dd_year.add("TY");

        List<String> dd_stream = new ArrayList<String>();
        dd_stream.add(" ");
        dd_stream.add("BMS");
        dd_stream.add("BMM");
        dd_stream.add("BBI");
        dd_stream.add("BAF");
        dd_stream.add("BSCIT");
        dd_stream.add("BFM");

        List<String> dd_role = new ArrayList<String>();
        dd_role.add(" ");
        dd_role.add("Co-ordinator");
        dd_role.add("Regular");
        dd_role.add("Visiting");

        ArrayAdapter<String> arrayAdapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dd_year);
        arrayAdapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(arrayAdapter_year);

        ArrayAdapter<String> arrayAdapter_stream = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dd_stream);
        arrayAdapter_stream.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_stream.setAdapter(arrayAdapter_stream);

        ArrayAdapter<String> arrayAdapter_role = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dd_role);
        arrayAdapter_role.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_role.setAdapter(arrayAdapter_role);

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = et_fname.getText().toString();
                lname = et_lname.getText().toString();
                mobile = et_mobile.getText().toString();
                email = et_email.getText().toString();
                r_year = sp_year.getSelectedItem().toString();
                r_stream = sp_year.getSelectedItem().toString();
                r_role = sp_year.getSelectedItem().toString();

                Toast.makeText(AddStaff.this, sp_year.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }

}

