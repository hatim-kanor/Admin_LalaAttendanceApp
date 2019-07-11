package com.example.llcadmin;

import android.graphics.Typeface;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddStaff extends AppCompatActivity  {

    Spinner sp_year, sp_stream, sp_role;
    String r_year, r_stream, r_role, fname, lname, email, mobile,result,role;
    EditText et_fname, et_lname, et_email, et_mobile;
    RadioButton rbYear,rbRole,rbYear_1,rbYear_2,rbYear_3,rbRole_1,rbRole_2,rbRole_3;
    Button btn_Add;
    ArrayAdapter<AddStaff_Class> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_staff);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Staff");



        btn_Add = (Button) findViewById(R.id.Btn_Add);
        et_email = (EditText) findViewById(R.id.et_fname);
        et_fname = (EditText) findViewById(R.id.fname);
        et_lname = (EditText) findViewById(R.id.lname);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        sp_stream = (Spinner)findViewById(R.id.sp_stream);
        rbYear_1 = (RadioButton)findViewById(R.id.rb_FY);
        rbYear_2 = (RadioButton)findViewById(R.id.rb_SY);
        rbYear_3 = (RadioButton)findViewById(R.id.rb_TY);
        rbRole_1 = (RadioButton)findViewById(R.id.rb_Coordinator);
        rbRole_2 = (RadioButton)findViewById(R.id.rb_Faculty);
        rbRole_3 = (RadioButton)findViewById(R.id.rb_Visiting);

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




        arrayAdapter = new ArrayAdapter<AddStaff_Class>(this,android.R.layout.simple_spinner_item,StaffStream);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_stream.setAdapter(arrayAdapter);




        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = et_fname.getText().toString();
                lname = et_lname.getText().toString();
                mobile = et_mobile.getText().toString();
                email = et_email.getText().toString();


                AddStaff_Class addStaff_class = (AddStaff_Class)sp_stream.getSelectedItem();

                r_stream = addStaff_class.getStream();
                Toast.makeText(AddStaff.this, r_stream, Toast.LENGTH_SHORT).show();






            }
        });





    }

    public void onYearSelected(View v)
    {
        boolean checkedYear = ((RadioButton) v).isChecked();
        switch(v.getId())
        {
            case R.id.rb_FY:
                if(checkedYear) {
                    rbYear_1.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbYear_2.setTypeface(null, Typeface.NORMAL);
                    rbYear_3.setTypeface(null, Typeface.NORMAL);
                    result = "FY";
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.rb_SY:
                if(checkedYear) {
                    rbYear_1.setTypeface(null, Typeface.NORMAL);
                    rbYear_2.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbYear_3.setTypeface(null, Typeface.NORMAL);
                    result = "SY";
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.rb_TY:
                if(checkedYear) {
                    rbYear_1.setTypeface(null, Typeface.NORMAL);
                    rbYear_2.setTypeface(null, Typeface.NORMAL);
                    rbYear_3.setTypeface(null, Typeface.BOLD_ITALIC);
                    result = "TY";
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    break;
                }
            default:
                rbYear_1.setTypeface(null, Typeface.NORMAL);
                rbYear_2.setTypeface(null,Typeface.NORMAL);
                rbYear_3.setTypeface(null,Typeface.NORMAL);
                result = "NULL";
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                break;
        }

    }
    public void onRoleSelected(View vv)
    {

        boolean checkedRole = ((RadioButton) vv).isChecked();
        switch(vv.getId())
        {
            case R.id.rb_Coordinator:
                if(checkedRole) {
                    rbRole_1.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbRole_2.setTypeface(null, Typeface.NORMAL);
                    rbRole_3.setTypeface(null, Typeface.NORMAL);
                    role = "Co-ordinator";
                    Toast.makeText(this, role, Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.rb_Faculty:
                if(checkedRole) {
                    rbRole_1.setTypeface(null, Typeface.NORMAL);
                    rbRole_2.setTypeface(null, Typeface.BOLD_ITALIC);
                    rbRole_3.setTypeface(null, Typeface.NORMAL);
                    role = "Faculty";
                    Toast.makeText(this, role, Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.rb_Visiting:
                if(checkedRole) {
                    rbRole_1.setTypeface(null, Typeface.NORMAL);
                    rbRole_2.setTypeface(null, Typeface.NORMAL);
                    rbRole_3.setTypeface(null, Typeface.BOLD_ITALIC);
                    role = "Visiting";
                    Toast.makeText(this, role, Toast.LENGTH_SHORT).show();
                    break;
                }
            default:
                rbRole_1.setTypeface(null, Typeface.BOLD_ITALIC);
                rbRole_2.setTypeface(null, Typeface.NORMAL);
                rbRole_3.setTypeface(null, Typeface.NORMAL);
                role = "null";
                Toast.makeText(this, role, Toast.LENGTH_SHORT).show();
                break;
        }

    }


}

