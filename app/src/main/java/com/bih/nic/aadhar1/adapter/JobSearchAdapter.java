package com.bih.nic.aadhar1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bih.nic.aadhar1.Model.JobListEntity;
import com.bih.nic.aadhar1.R;

import java.util.ArrayList;

public class JobSearchAdapter extends BaseAdapter {

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
    public int getCount() {
        return ThrList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //if (convertView == null) {
        convertView = mInflater.inflate(R.layout.adaptor_job_search, null);

        holder = new ViewHolder();
        holder.tv_slno=convertView.findViewById(R.id.tv_slno);
        holder.tv_work_site=convertView.findViewById(R.id.tv_work_site);
        holder.tv_skill_cat=convertView.findViewById(R.id.tv_skill_cat);
        holder.tv_skill_name=convertView.findViewById(R.id.tv_skill_name);
        holder.tv_person_no=convertView.findViewById(R.id.tv_person_no);
        holder.tv_gendar=convertView.findViewById(R.id.tv_gendar);
        holder.tv_start_date=convertView.findViewById(R.id.tv_start_date);
        holder.tv_exp=convertView.findViewById(R.id.tv_exp);
        holder.tv_exp_max=convertView.findViewById(R.id.tv_exp_max);
        holder.tv_salary=convertView.findViewById(R.id.tv_salary);
        holder.tv_salary_max=convertView.findViewById(R.id.tv_salary_max);
        holder.tv_block=convertView.findViewById(R.id.tv_block);

        convertView.setTag(holder);

        JobListEntity info = ThrList.get(position);

       // holder.tv_slno.setText(position+1);
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

        return convertView;
    }


    private class ViewHolder {
        TextView tv_slno,tv_work_site,tv_skill_cat,tv_skill_name,tv_person_no,tv_gendar,tv_start_date,tv_exp,tv_exp_max,tv_salary,tv_salary_max,tv_block;
    }



}
