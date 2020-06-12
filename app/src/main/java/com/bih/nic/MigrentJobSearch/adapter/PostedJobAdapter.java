package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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
import com.bih.nic.MigrentJobSearch.Model.JobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.ui.employer.JobOfferPosted_BlockActivity;
import com.bih.nic.MigrentJobSearch.ui.hq.JobOffer_HQ_BlockActivity;

import java.util.ArrayList;

public class PostedJobAdapter extends RecyclerView.Adapter<PostedJobAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<JobOfferPostedEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="";
    Boolean isShowDetail = false;
    String regNo;

    public PostedJobAdapter(Activity listViewshowedit, ArrayList<JobOfferPostedEntity> rlist, String regNo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adptor_job_posting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final JobOfferPostedEntity info = ThrList.get(position);

         holder.tv_slno.setText(String.valueOf(position+1)+").");
        holder.tv_dise_name.setText(info.getDistrictName());
        holder.total_reg.setText(info.getTtlReg());
        holder.tv_joboffer.setText(info.getTotalJobOffer());
        holder.tv_accpted.setText(info.getTtlRegA());
        holder.tv_rejected.setText(info.getTtlRegR());




        holder.tv_dise_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserRole= PreferenceManager.getDefaultSharedPreferences(activity).getString("UserRole", "");
                if (UserRole.equals("HQ")){
                    Intent intent = new Intent(activity, JobOffer_HQ_BlockActivity.class);
                    //intent.putExtra("data",Reg_No);
                    intent.putExtra("DistCode",info.getDistrictCode());
                    intent.putExtra("DistName",info.getDistrictName());
                    activity.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(activity, JobOfferPosted_BlockActivity.class);
                    //intent.putExtra("data",Reg_No);
                    intent.putExtra("DistCode",info.getDistrictCode());
                    intent.putExtra("DistName",info.getDistrictName());
                    activity.startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_dise_name,total_reg,tv_joboffer,tv_accpted,tv_rejected,tv_slno;
        ImageView iv_call;


        Button btn_accpt,btn_rjct;
        LinearLayout ll_btn,ll_status,ll_selection;
        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_dise_name=itemView.findViewById(R.id.tv_dise_name);
            total_reg=itemView.findViewById(R.id.total_reg);
            tv_joboffer=itemView.findViewById(R.id.tv_joboffer);
            tv_accpted=itemView.findViewById(R.id.tv_accpted);
            tv_rejected=itemView.findViewById(R.id.tv_rejected);


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
