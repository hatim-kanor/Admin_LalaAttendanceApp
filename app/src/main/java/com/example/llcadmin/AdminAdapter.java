package com.example.llcadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ProductViewHolder>{
    private Context mCtx;
    private List<Admin> AdminList;
    private OnItemClickListener mListener;
    private static String URL2 = "https://llc-attendance.000webhostapp.com/deleteAdmin.php";
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public AdminAdapter(Context mCtx, List<Admin> adminList) {
        this.mCtx = mCtx;
        AdminList = adminList;
    }

    @Override
    public AdminAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.admin_list, null);
        final ProductViewHolder viewHolder = new ProductViewHolder(view) ;

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdminAdapter.ProductViewHolder holder, int position) {
        final Admin admin = AdminList.get(position);
        final String id = admin.getId();
        final String fname = admin.getName();
        final String lname = admin.getLname();
        final String imei = admin.getImei();
        final String mobile = admin.getMobile();
        final String email = admin.getEmail();
        final String fullName = fname + " " + lname;

        holder.name.setText("NAME " + fname.toUpperCase());
        holder.imei.setText("IMEI " +imei);

        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx,holder.optionMenu);

                //inflating menu fro``m xml resource
                popup.inflate(R.menu.admin_option_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.v_profile:
                                Intent nextPage = new Intent(mCtx,Profile.class);
                                nextPage.putExtra("name",fullName);
                                nextPage.putExtra("email",email);
                                nextPage.putExtra("mobile",mobile);
                                nextPage.putExtra("imei",imei);
                                nextPage.putExtra("fname",fname);
                                nextPage.putExtra("lname",lname);
                                nextPage.putExtra("a_id",id);
                                mCtx.startActivity(nextPage);
                                Toast.makeText(mCtx, "Profile Menu Selected", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.update_admin:
                                Intent i = new Intent(mCtx,UpdateAdmin.class);
                                i.putExtra("name",fullName);
                                i.putExtra("email",email);
                                i.putExtra("mobile",mobile);
                                i.putExtra("imei",imei);
                                i.putExtra("fname",fname);
                                i.putExtra("lname",lname);
                                i.putExtra("a_id",id);
                                mCtx.startActivity(i);
                                break;
                            case R.id.delete_admin:

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response2) {
                                                        try {


                                                            JSONObject jsonObject = new JSONObject(response2);
                                                            String success = jsonObject.getString("success");
                                                            if(success.equals("1"))
                                                            {
                                                                Toast.makeText(mCtx, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                                //showMessage("Message", "Admin Deleted Successfully");
                                                                Intent i = new Intent(mCtx,ViewAdmin.class);
                                                                mCtx.startActivity(i);
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(mCtx, "Failed to delete", Toast.LENGTH_SHORT).show();
                                                                //showMessage("Message" , "Failed to Delete Admin");
                                                            }




                                                        }catch(Exception e)
                                                        {
                                                            Toast.makeText(mCtx, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();

                                                        }

                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(mCtx, "Volley Error: " + error.toString(), Toast.LENGTH_SHORT).show();


                                            }
                                        }){
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String,String> params = new HashMap<>();
                                                params.put("a_id",id);
                                                return params;

                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                                        requestQueue.add(stringRequest);



                        }
                        return false;
                    }
                });

                popup.show();


            }
        });

//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(mCtx,"Name: " + fname.toString() + " " + lname +
//                       "\n Email: " + email +
//                        "\n Contact: " + mobile +
//                        "\n IMEI: " +imei,Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
//                toast.show();
//
//            }
//        });




    }

    @Override
    public int getItemCount() {
        return AdminList.size();
    }
//    public void showMessage(String title,String message)
//    {
//        AlertDialog.Builder builder=new AlertDialog.Builder(mCtx);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        CardView layout;
        TextView name,imei,optionMenu;


        public ProductViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            name = itemView.findViewById(R.id.name);
            imei = itemView.findViewById(R.id.imei);
            optionMenu = itemView.findViewById(R.id.textViewOptions);



            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION)
                        {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
