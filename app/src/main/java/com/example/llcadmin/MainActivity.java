package com.example.llcadmin;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String default_imei;
    boolean sharedPreferences;
    public static String myPrefs = "AdminLogin";
    public static String PREFS_ID = "ID";
    public static String PREFS_NAME = "NAME";
    public static String PREFS_LNAME = "LNAME";
    public static String PREFS_MOBILE = "MOBILE";
    public static String PREFS_EMAIL = "EMAIL";
    public static String PREFS_IMEI = "IMEI";

    private static String URL = "https://llc-attendance.000webhostapp.com/fetch_imei.php";
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    TelephonyManager tm;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        loadImei();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadImei() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String IMEINumber="";
        if(Build.VERSION_CODES.N >= 26)
        {
            IMEINumber = tm.getDeviceId();
            Toast.makeText(this, "Device ID: " + IMEINumber, Toast.LENGTH_SHORT).show();
           fetchIMEI(IMEINumber);

        }
        else
        {
            IMEINumber = tm.getImei();
            Toast.makeText(this, "IMEI ID: " + IMEINumber, Toast.LENGTH_SHORT).show();
            fetchIMEI(IMEINumber);

        }







    }

    private void fetchIMEI(final String imei) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray login = jsonObject.getJSONArray("login");
                            if(success.equals("1"))
                            {
                                for(int i = 0;i< login.length() ; i++) {
                                    JSONObject object = login.getJSONObject(i);
                                    String id = object.getString("a_id");
                                    String name = object.getString("a_name");
                                    String lname = object.getString("a_lname");
                                    String mobile = object.getString("a_mobile");
                                    String email = object.getString("a_email");
                                    String imei = object.getString("a_imei");

                                    rememberMe(id,name,lname,mobile,email,imei);

                                    Toast.makeText(MainActivity.this, "Imei Number Verified: " + name , Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }


                                    //Toast.makeText(MainActivity.this, "IMEI Number Verified", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(),profile.class);
//                                startActivity(intent);
//                                finish();




                            }
                            else if(success.equals("0"))
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Failed to Open the App \n Contact the Admin for Details", Toast.LENGTH_SHORT).show();
                            }
                            else if(success.equals("2"))
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Login Failed \n Value could not be reached to the server", Toast.LENGTH_SHORT).show();
                            }




                        }catch(Exception e)
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();


            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("imei",imei);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void rememberMe(String id, String name, String lname, String mobile, String email, String imei) {
        sharedPreferences = getSharedPreferences(myPrefs,Context.MODE_PRIVATE)
                .edit()
                .putString(PREFS_ID,id)
                .putString(PREFS_NAME,name)
                .putString(PREFS_LNAME,lname)
                .putString(PREFS_EMAIL,email)
                .putString(PREFS_MOBILE,mobile)
                .putString(PREFS_IMEI,imei)
                .commit();


    }


    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permission Request")
                    .setMessage("Permission")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));

            } else {
                alertAlert("Permission");
            }
        }

    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })
                .show();

    }



}
