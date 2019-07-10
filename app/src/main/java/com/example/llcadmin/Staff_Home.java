package com.example.llcadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Staff_Home extends AppCompatActivity {

    RecyclerView rv_bms,rv_bmm,rv_bbi,rv_baf,rv_bscit,rv_bfm;
    String s_bms,s_bmm,s_bbi,s_baf_,s_bscit,s_bfm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Staff Home");


        initializeVar();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //LinearLayoutManager layoutManager;
        rv_bms.setHasFixedSize(true);
        rv_bms.setLayoutManager(layoutManager);

        rv_bmm.setHasFixedSize(true);
        rv_bmm.setLayoutManager(layoutManager);

        rv_bbi.setHasFixedSize(true);
        rv_bbi.setLayoutManager(layoutManager);

        rv_baf.setHasFixedSize(true);
        rv_baf.setLayoutManager(layoutManager);

        rv_bscit.setHasFixedSize(true);
        rv_bscit.setLayoutManager(layoutManager);

        rv_bfm.setHasFixedSize(true);
        rv_bfm.setLayoutManager(layoutManager);
    }

    private void initializeVar() {

        rv_bms = (RecyclerView)findViewById(R.id.rv_bms);
        rv_bmm = (RecyclerView)findViewById(R.id.rv_bmm);
        rv_bbi = (RecyclerView)findViewById(R.id.rv_bbi);
        rv_baf = (RecyclerView)findViewById(R.id.rv_baf);
        rv_bscit = (RecyclerView)findViewById(R.id.rv_bscit);
        rv_bfm = (RecyclerView)findViewById(R.id.rv_bfm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_staff,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.add_staff:
                Intent i = new Intent(Staff_Home.this,AddStaff.class);
                startActivity(i);
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}

