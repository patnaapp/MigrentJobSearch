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
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.ui.employer.BlocJobOfferActivity;
import com.bih.nic.MigrentJobSearch.ui.employer.LegacyTableViewActivity;
import com.bih.nic.MigrentJobSearch.Model.BlockJobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.R;

import java.util.ArrayList;

public class PostedJobBlockAdapter extends RecyclerView.Adapter<PostedJobBlockAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<BlockJobOfferPostedEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="";
    Boolean isShowDetail = false;
    String regNo;

    public PostedJobBlockAdapter(Activity listViewshowedit, ArrayList<BlockJobOfferPostedEntity> rlist, String regNo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adptor_job_posting_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final BlockJobOfferPostedEntity info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position+1)+").");
        holder.tv_blk_name.setText(info.getBlockName().toUpperCase());
        holder.total_reg.setText(info.getTtlReg());
        holder.tv_joboffer.setText(info.getTotalJobOffer());
        holder.tv_accpted.setText(info.getTtlRegA());
        holder.tv_rejected.setText(info.getTtlRegR());

//
        holder.tv_blk_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BlocJobOfferActivity.class);
                String distid = activity.getIntent().getStringExtra("DistCode");
                intent.putExtra("distid",distid);
               // intent.putExtra("StatusFlag","SHRG");
                intent.putExtra("BlockCode",info.getBlockCode());
                intent.putExtra("BlockNAme",info.getBlockName());
                activity.startActivity(intent);
            }
        });


        holder.total_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LegacyTableViewActivity.class);
                String distid = activity.getIntent().getStringExtra("DistCode");
                intent.putExtra("distid",distid);
                intent.putExtra("StatusFlag","SHRG");
                intent.putExtra("BlockCode",info.getBlockCode());
                intent.putExtra("BlockNAme",info.getBlockName());
                intent.putExtra("Count",info.getTtlReg());
                activity.startActivity(intent);
            }
        });

        holder.tv_joboffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LegacyTableViewActivity.class);
                String distid = activity.getIntent().getStringExtra("DistCode");
                intent.putExtra("distid",distid);
                intent.putExtra("StatusFlag","SHRGJ");
                intent.putExtra("BlockCode",info.getBlockCode());
                intent.putExtra("BlockNAme",info.getBlockName());
                intent.putExtra("Count",info.getTotalJobOffer());
                activity.startActivity(intent);
            }
        });
//
//



        holder.tv_accpted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(activity, AcceptedRejctd_Job_Activity.class);

                if (!(info.getTtlRegA().equals("0"))){
                    Intent intent = new Intent(activity, LegacyTableViewActivity.class);
                    //intent.putExtra("data",Reg_No);
                    intent.putExtra("distid","");
                    intent.putExtra("StatusFlag","SHRGJA");
                    intent.putExtra("BlockCode",info.getBlockCode());
                    intent.putExtra("BlockNAme",info.getBlockName());
                    intent.putExtra("Count",info.getTtlRegA());
                    activity.startActivity(intent);
                }
                else {
                    Toast.makeText(activity, "कोई स्वीकृत नौकरी नहीं है", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.tv_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(info.getTtlRegR().equals("0"))) {
                    Intent intent = new Intent(activity, LegacyTableViewActivity.class);
                    //intent.putExtra("data",Reg_No);
                    intent.putExtra("distid","");
                    intent.putExtra("StatusFlag", "SHRGJR");
                    intent.putExtra("BlockCode", info.getBlockCode());
                    intent.putExtra("BlockNAme", info.getBlockName());
                    intent.putExtra("Count",info.getTtlRegR());
                    activity.startActivity(intent);
                }
                else {
                    Toast.makeText(activity, "कोई अस्वीकृत नौकरी नहीं है", Toast.LENGTH_SHORT).show();
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
        TextView tv_blk_name,total_reg,tv_joboffer,tv_accpted,tv_rejected,tv_slno;
        ImageView iv_call;


        Button btn_accpt,btn_rjct;
        LinearLayout ll_btn,ll_status,ll_selection;
        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_blk_name=itemView.findViewById(R.id.tv_blk_name);
            total_reg=itemView.findViewById(R.id.total_reg_blk);
            tv_joboffer=itemView.findViewById(R.id.tv_joboffer_blk);
            tv_accpted=itemView.findViewById(R.id.tv_accpted_blk);
            tv_rejected=itemView.findViewById(R.id.tv_rejected_blk);


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
