package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.Model.DefaultResponse;
import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkRequirementsEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.listener.WorkReqrmntListener;

import java.util.ArrayList;

public class WorkReqrmntEntryAdapter extends RecyclerView.Adapter<WorkReqrmntEntryAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<WorkRequirementsEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    WorkReqrmntListener listener;

    public WorkReqrmntEntryAdapter(Activity listViewshowedit, ArrayList<WorkRequirementsEntity> rlist, WorkReqrmntListener listener) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_work_reqmntentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final WorkRequirementsEntity info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_skill_cat.setText(info.getSkill_categ());
        holder.tv_skill_name.setText(info.getSkill_sub_categ());
        holder.tv_no_perosn.setText(info.getNo_of_persons());
        holder.tv_gendar.setText(info.getGender());
        holder.tv_start_date.setText(info.getStart_date());
        holder.tv_exp.setText(info.getMin_exp());
        holder.tv_exp_max.setText(info.getMax_exp());
        holder.tv_salary.setText(info.getMin_salary());
        holder.tv_salary_max.setText(info.getMax_salary());
        holder.tv_status1.setText(info.getIsActive());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemoveRequirement(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_skill_cat,tv_skill_name,tv_no_perosn,tv_gendar,tv_start_date,tv_exp,tv_exp_max,tv_salary,tv_salary_max,tv_status1;
        ImageView iv_delete;

        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_skill_cat=itemView.findViewById(R.id.tv_skill_cat);
            tv_skill_name=itemView.findViewById(R.id.tv_skill_name);
            tv_no_perosn=itemView.findViewById(R.id.tv_no_perosn);
            tv_gendar=itemView.findViewById(R.id.tv_gendar);
            tv_start_date=itemView.findViewById(R.id.tv_start_date);
            tv_exp=itemView.findViewById(R.id.tv_exp);
            tv_exp_max=itemView.findViewById(R.id.tv_exp_max);
            tv_salary=itemView.findViewById(R.id.tv_salary);
            tv_salary_max=itemView.findViewById(R.id.tv_salary_max);
            tv_status1=itemView.findViewById(R.id.tv_status1);
            iv_delete=itemView.findViewById(R.id.iv_delete);
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
