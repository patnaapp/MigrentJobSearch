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
import com.bih.nic.MigrentJobSearch.Model.WorkSiteEmployeeReportEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.ui.hq.SubDeptJobVacencyReportActivity;

import java.util.ArrayList;

public class WorksiteEmployeeReportAdapter extends RecyclerView.Adapter<WorksiteEmployeeReportAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<WorkSiteEmployeeReportEntity> ThrList=new ArrayList<>();
    String regNo;

    public WorksiteEmployeeReportAdapter(Activity listViewshowedit, ArrayList<WorkSiteEmployeeReportEntity> rlist, String regNo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adptor_worksite_employee_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final WorkSiteEmployeeReportEntity info = ThrList.get(position);

         holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_worksite.setText(info.getWorkSiteName());
        holder.tv_skillname.setText(info.getSkillCategory());
        holder.tv_sub_skillname.setText(info.getSkillName());
        holder.tv_no_perosn.setText(info.getNoOfPerson());
        holder.tv_offered.setText(info.getTotalJobOffer());
        holder.tv_accpted.setText(info.getTtlRegA());
        holder.tv_rejected.setText(info.getTtlRegR());
        holder.tv_appointed.setText(info.getTtlAppointed());

//        holder.tv_dept_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    Intent intent = new Intent(activity,SubDeptJobVacencyReportActivity.class);
//                    //intent.putExtra("data",Reg_No);
//                    intent.putExtra("DeptCode",info.getDeptId());
//                    intent.putExtra("DeptName",info.getDeptName());
//                    intent.putExtra("DeptName",info.getDeptName());
//                    activity.startActivity(intent);
//
//            }
//        });


    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_worksite,tv_skillname,tv_sub_skillname,tv_no_perosn,tv_offered,tv_accpted,tv_rejected,tv_appointed;

        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_worksite=itemView.findViewById(R.id.tv_worksite);
            tv_skillname=itemView.findViewById(R.id.tv_skillname);
            tv_sub_skillname=itemView.findViewById(R.id.tv_sub_skillname);
            tv_no_perosn=itemView.findViewById(R.id.tv_no_perosn);
            tv_offered=itemView.findViewById(R.id.tv_offered);
            tv_accpted=itemView.findViewById(R.id.tv_accpted);
            tv_rejected=itemView.findViewById(R.id.tv_rejected);
            tv_appointed=itemView.findViewById(R.id.tv_appointed);


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
