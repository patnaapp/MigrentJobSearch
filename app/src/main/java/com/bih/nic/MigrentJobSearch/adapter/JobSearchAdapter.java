package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.R;

import java.util.ArrayList;

public class JobSearchAdapter extends RecyclerView.Adapter<JobSearchAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<JobListEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="";

    public JobSearchAdapter(Activity listViewshowedit, ArrayList<JobListEntity> rlist) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_job_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobListEntity info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_work_site.setText(info.getWorkSite());
        holder.tv_skill_cat.setText(info.getSkillCategory());
        holder.tv_skill_name.setText(info.getSkillName());
        holder.tv_person_no.setText(info.getNumberOfPerson());
        holder.tv_gendar.setText(info.getGendar());
        holder.tv_start_date.setText(info.getStartDate());
        holder.tv_exp.setText(info.getExperience());
        holder.tv_exp_max.setText(info.getExperienceMax());
        holder.tv_salary.setText(info.getSalary());
        holder.tv_salary_max.setText(info.getSalaryMax());
        holder.tv_block.setText(info.getBlock());
    }

    @Override
    public int getItemCount() {
        return ThrList.size();
    }

//    @Override
//    public View getView(final int position, View itemView, ViewGroup parent) {
//        ViewHolder holder = null;
//        //if (itemView == null) {
//        itemView = mInflater.inflate(R.layout.adaptor_job_search, null);
//
//        holder = new ViewHolder();
//
//
//        itemView.setTag(holder);
//
//        JobListEntity info = ThrList.get(position);
//
//       // tv_slno.setText(position+1);
//        tv_work_site.setText(info.getWorkSite());
//        tv_skill_cat.setText(info.getSkillCategory());
//        tv_skill_name.setText(info.getSkillName());
//        tv_person_no.setText(info.getNumberOfPerson());
//        tv_gendar.setText(info.getGendar());
//        tv_start_date.setText(info.getStartDate());
//        tv_exp.setText(info.getExperience());
//        tv_exp_max.setText(info.getExperienceMax());
//        tv_salary.setText(info.getSalary());
//        tv_salary_max.setText(info.getSalaryMax());
//        tv_block.setText(info.getBlock());
//
//        return itemView;
//    }

//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView tv_inspection_by,tv_inspection_id,tv_number,tv_designation,tv_observation,tv_completion,tv_comment,tv_date;
//        RelativeLayout sblist;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            tv_inspection_by = itemView.findViewById(R.id.tv_inspection_by);
//            tv_inspection_id = itemView.findViewById(R.id.tv_inspection_id);
//            tv_number = itemView.findViewById(R.id.tv_number);
//            tv_designation = itemView.findViewById(R.id.tv_designation);
//            tv_observation = itemView.findViewById(R.id.tv_observation);
//            tv_completion = itemView.findViewById(R.id.tv_completion);
//            tv_comment = itemView.findViewById(R.id.tv_comment);
//            tv_date = itemView.findViewById(R.id.tv_date);
//            sblist = itemView.findViewById(R.id.sblist);
//            //itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_work_site,tv_skill_cat,tv_skill_name,tv_person_no,tv_gendar,tv_start_date,tv_exp,tv_exp_max,tv_salary,tv_salary_max,tv_block;

        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_work_site=itemView.findViewById(R.id.tv_work_site);
            tv_skill_cat=itemView.findViewById(R.id.tv_skill_cat);
            tv_skill_name=itemView.findViewById(R.id.tv_skill_name);
            tv_person_no=itemView.findViewById(R.id.tv_person_no);
            tv_gendar=itemView.findViewById(R.id.tv_gendar);
            tv_start_date=itemView.findViewById(R.id.tv_start_date);
            tv_exp=itemView.findViewById(R.id.tv_exp);
            tv_exp_max=itemView.findViewById(R.id.tv_exp_max);
            tv_salary=itemView.findViewById(R.id.tv_salary);
            tv_salary_max=itemView.findViewById(R.id.tv_salary_max);
            tv_block=itemView.findViewById(R.id.tv_block);
        }

        @Override
        public void onClick(View v) {

        }

    }



}
