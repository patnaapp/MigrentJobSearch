package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.WorkDetailsEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkRequirementsEntity;
import com.bih.nic.MigrentJobSearch.Model.workListModel;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.listener.WorkReqrmntListener;
import com.bih.nic.MigrentJobSearch.ui.employer.AddWorkSiteDetails_Activity;

import java.util.ArrayList;

public class WorkSiteEditAdapter extends RecyclerView.Adapter<WorkSiteEditAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<WorkDetailsEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    WorkReqrmntListener listener;

    public WorkSiteEditAdapter(Activity listViewshowedit, ArrayList<WorkDetailsEntity> rlist) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_work_site_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final WorkDetailsEntity info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_wrk_id.setText(info.getWorksId());
        holder.tv_work_site.setText(info.getWork_site_eng());
        holder.tv_wrk_location.setText(info.getLocation_en());
        holder.tv_pincode.setText(info.getPincode());
        holder.tv_cntct_prson.setText(info.getSupervisor_nm_en());
        holder.tv_mob.setText(info.getSupervisor_mob());
        holder.tv_wrk_stat.setText(info.getWorkstatus());


        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity, AddWorkSiteDetails_Activity.class);
                activity.startActivity(i);

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
        ImageView iv_edit;
        TextView tv_wrk_id,tv_work_site,tv_wrk_location,tv_pincode,tv_cntct_prson,tv_mob,tv_wrk_stat;

        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_wrk_id=itemView.findViewById(R.id.tv_wrk_id);
            tv_work_site=itemView.findViewById(R.id.tv_work_site);
            tv_wrk_location=itemView.findViewById(R.id.tv_wrk_location);
            tv_pincode=itemView.findViewById(R.id.tv_pincode);
            tv_cntct_prson=itemView.findViewById(R.id.tv_cntct_prson);
            tv_mob=itemView.findViewById(R.id.tv_mob);
            tv_wrk_stat=itemView.findViewById(R.id.tv_wrk_stat);
            iv_edit=itemView.findViewById(R.id.iv_edit);

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
