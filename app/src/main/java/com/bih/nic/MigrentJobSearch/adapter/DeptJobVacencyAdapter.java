package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.DepartmentWiseVacancy;
import com.bih.nic.MigrentJobSearch.Model.JobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.ui.hq.SubDeptJobVacencyReportActivity;

import java.util.ArrayList;

public class DeptJobVacencyAdapter extends RecyclerView.Adapter<DeptJobVacencyAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<DepartmentWiseVacancy> ThrList=new ArrayList<>();
    String regNo;

    public DeptJobVacencyAdapter(Activity listViewshowedit, ArrayList<DepartmentWiseVacancy> rlist, String regNo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adptor_dept_job_vacency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DepartmentWiseVacancy info = ThrList.get(position);

         holder.tv_slno.setText(String.valueOf(position+1)+").");
        holder.tv_dept_name.setText(info.getDeptName());
        holder.tv_worksite.setText(info.getDeptTotalWork());
        holder.tv_vacnecy.setText(info.getDeptTotalRequirement());

        holder.tv_dept_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(activity,SubDeptJobVacencyReportActivity.class);
                    //intent.putExtra("data",Reg_No);
                    intent.putExtra("DeptCode",info.getDeptId());
                    intent.putExtra("DeptName",info.getDeptName());
                    intent.putExtra("DeptName",info.getDeptName());
                    activity.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_dept_name,tv_worksite,tv_vacnecy,tv_slno;
        ImageView iv_call;


        Button btn_accpt,btn_rjct;
        LinearLayout ll_btn,ll_status,ll_selection;
        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_dept_name=itemView.findViewById(R.id.tv_dept_name);
            tv_worksite=itemView.findViewById(R.id.tv_worksite);
            tv_vacnecy=itemView.findViewById(R.id.tv_vacnecy);


        }

        @Override
        public void onClick(View v) {

        }

    }


    public String getGenderHindi(String gender)
    {
        return gender;
    }

}
