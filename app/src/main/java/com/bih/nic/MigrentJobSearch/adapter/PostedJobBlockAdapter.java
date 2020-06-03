package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.BlockJobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.Model.JobOfferPostedEntity;
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

        // holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_blk_name.setText(info.getBlockName());
        holder.total_reg.setText(info.getTtlReg());
        holder.tv_joboffer.setText(info.getTotalJobOffer());
        holder.tv_accpted.setText(info.getTtlRegA());
        holder.tv_rejected.setText(info.getTtlRegR());

    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_blk_name,total_reg,tv_joboffer,tv_accpted,tv_rejected;
        ImageView iv_call;


        Button btn_accpt,btn_rjct;
        LinearLayout ll_btn,ll_status,ll_selection;
        ViewHolder(View itemView) {
            super(itemView);
            //tv_slno=itemView.findViewById(R.id.tv_slno);
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
