package com.example.llcadmin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewStaffAdapter extends RecyclerView.Adapter<ViewStaffAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<ViewStaff> staffList;
    private AdminAdapter.OnItemClickListener mListener;

    public ViewStaffAdapter(Context mCtx, List<ViewStaff> staffList) {
        this.mCtx = mCtx;
        this.staffList = staffList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public ProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}
