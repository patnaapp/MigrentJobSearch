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

import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;
import com.bih.nic.MigrentJobSearch.Model.BlockJobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.ui.employer.AcceptedRejctd_Job_Activity;

import java.util.ArrayList;

public class AccptedRctdJobAdapter extends RecyclerView.Adapter<AccptedRctdJobAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<AcptdRjctdJobOfferEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="";
    Boolean isShowDetail = false;
    String regNo;

    public AccptedRctdJobAdapter(Activity listViewshowedit, ArrayList<AcptdRjctdJobOfferEntity> rlist, String regNo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_acptd_rjctd_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final AcptdRjctdJobOfferEntity info = ThrList.get(position);

        // holder.tv_slno.setText(String.valueOf(position+1));
        //holder.tv_blk_name.setText(info.getBlockName().toUpperCase());
        holder.tv_regno.setText(info.getTtlReg());
        holder.tv_labour_name.setText(info.getTotalJobOffer());
        holder.tv_gender.setText(info.getTtlRegA());
        holder.tv_labour_district.setText(info.getTtlRegR());

//        holder.tv_accpted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, AcceptedRejctd_Job_Activity.class);
//                //intent.putExtra("data",Reg_No);
//                intent.putExtra("StatusFlag","Y");
//                intent.putExtra("BlockCode",info.getBlockCode());
//                intent.putExtra("BlockNAme",info.getBlockName());
//                activity.startActivity(intent);
//            }
//        });
//
//        holder.tv_rejected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, AcceptedRejctd_Job_Activity.class);
//                //intent.putExtra("data",Reg_No);
//                intent.putExtra("StatusFlag","R");
//                intent.putExtra("BlockCode",info.getBlockCode());
//                intent.putExtra("BlockNAme",info.getBlockName());
//                activity.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_regno,tv_labour_name,tv_gender,tv_labour_district,tv_labor_block,tv_person_addres,tv_age,tv_candidate_mob,tv_gauradian_name,tv_gaurdian_no,tv_skill,tv_exp_max,tv_other,tv_candid_skill;
        ImageView iv_call;


        Button btn_accpt,btn_rjct;
        LinearLayout ll_btn,ll_status,ll_selection;
        ViewHolder(View itemView) {
            super(itemView);
            //tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_regno=itemView.findViewById(R.id.tv_regno);
            tv_labour_name=itemView.findViewById(R.id.tv_labour_name);
            tv_gender=itemView.findViewById(R.id.tv_gender);
            tv_labour_district=itemView.findViewById(R.id.tv_labour_district);
            tv_labor_block=itemView.findViewById(R.id.tv_labor_block);

            tv_person_addres=itemView.findViewById(R.id.tv_person_addres);
            tv_age=itemView.findViewById(R.id.tv_age);
            tv_candidate_mob=itemView.findViewById(R.id.tv_candidate_mob);
            tv_gauradian_name=itemView.findViewById(R.id.tv_gauradian_name);
            tv_gaurdian_no=itemView.findViewById(R.id.tv_gaurdian_no);
            tv_skill=itemView.findViewById(R.id.tv_skill);
            tv_exp_max=itemView.findViewById(R.id.tv_exp_max);
            tv_other=itemView.findViewById(R.id.tv_other);
            tv_candid_skill=itemView.findViewById(R.id.tv_candid_skill);



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
