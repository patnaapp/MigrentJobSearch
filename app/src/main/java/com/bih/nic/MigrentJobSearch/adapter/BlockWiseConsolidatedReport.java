package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

import com.bih.nic.MigrentJobSearch.Model.ConsolidatedReportModel;
import com.bih.nic.MigrentJobSearch.Model.DefaultResponse;
import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.OfficeReport.BlockWiseConsolidated;
import com.bih.nic.MigrentJobSearch.OfficeReport.WorkSiteReport;
import com.bih.nic.MigrentJobSearch.OfficeReport.WorkSiteReportNew;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;

import java.util.ArrayList;

public class BlockWiseConsolidatedReport  extends RecyclerView.Adapter<BlockWiseConsolidatedReport.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<ConsolidatedReportModel> ThrList = new ArrayList<>();
    String panchayatCode, panchayatName = "";
    Boolean isShowDetail = false;
    String DistCode="";

    public BlockWiseConsolidatedReport(Activity listViewshowedit, ArrayList<ConsolidatedReportModel> rlist, String regNo) {
        this.activity = listViewshowedit;
        this.ThrList = rlist;
        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.DistCode = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_block_consolidated_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ConsolidatedReportModel info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position + 1));
        holder.tv_distNam.setText(ThrList.get(position).getBlockName());
        holder.tv_PanjiNo.setText(ThrList.get(position).getTotalWorkDet());
        holder.tv_tot_work.setText(ThrList.get(position).getTotalWorkAccp());
        holder.tv_tot_work_accept.setText(ThrList.get(position).getTotalWorkReject());
        holder.tv_tot_vac.setText(ThrList.get(position).getTotalWorkPerRej());
        holder.tv_tot_acc_vac.setText(ThrList.get(position).getNoOfPerson());
        holder.tv_tot_rej.setText(ThrList.get(position).getNoOfPersonApp());
     //   holder.tv_tot_per_rej.setText(ThrList.get(position).getTotalWorkPerRej());

        holder.tv_PanjiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WorkSiteReportNew.class);
                intent.putExtra("DistCode",DistCode);
                intent.putExtra("type","1");
                intent.putExtra("Status","WRS");
                intent.putExtra("BlockCode",ThrList.get(position).getBlockCode());
                intent.putExtra("BlockName",ThrList.get(position).getBlockName());
                activity.startActivity(intent);
            }
        });
        holder.tv_tot_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WorkSiteReport.class);
                intent.putExtra("DistCode",DistCode);
                intent.putExtra("type","2");
                intent.putExtra("Status","WRSA");
                intent.putExtra("BlockCode",ThrList.get(position).getBlockCode());
                intent.putExtra("BlockName",ThrList.get(position).getBlockName());
                activity.startActivity(intent);
            }
        });
        holder.tv_tot_work_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WorkSiteReport.class);
                intent.putExtra("DistCode",DistCode);
                intent.putExtra("type","3");
                intent.putExtra("Status","WRSR");
                intent.putExtra("BlockCode",ThrList.get(position).getBlockCode());
                intent.putExtra("BlockName",ThrList.get(position).getBlockName());
                activity.startActivity(intent);
            }
        });
        holder.tv_tot_vac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WorkSiteReport.class);
                intent.putExtra("DistCode",DistCode);
                intent.putExtra("type","4");
                intent.putExtra("Status","WRSF");
                intent.putExtra("BlockCode",ThrList.get(position).getBlockCode());
                intent.putExtra("BlockName",ThrList.get(position).getBlockName());
                activity.startActivity(intent);
            }
        }); holder.tv_tot_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WorkSiteReportNew.class);
                intent.putExtra("DistCode",DistCode);
                intent.putExtra("type","5");
                intent.putExtra("Status","WNPA");
                intent.putExtra("BlockCode",ThrList.get(position).getBlockCode());
                intent.putExtra("BlockName",ThrList.get(position).getBlockName());
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ThrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_work_site, tv_skill_cat, tv_skill_name, tv_person_no, tv_gendar, tv_start_date, tv_exp, tv_exp_max, tv_salary, tv_salary_max, tv_block, tv_supervisor_name, tv_super_no, tv_district, tv_status, tv_selection_status;
        ImageView iv_call;
        Button btn_accpt, btn_rjct;
        LinearLayout ll_btn, ll_status, ll_selection;
        TextView tv_slno, tv_distNam, tv_PanjiNo, tv_tot_work, tv_tot_work_accept, tv_tot_vac, tv_tot_acc_vac, tv_tot_rej, tv_tot_per_rej;

        ViewHolder(View itemView) {
            super(itemView);
            tv_slno = itemView.findViewById(R.id.tv_slno);
            tv_distNam = itemView.findViewById(R.id.tv_distNam);
            tv_PanjiNo = itemView.findViewById(R.id.tv_PanjiNo);
            tv_tot_work = itemView.findViewById(R.id.tv_tot_work);
            tv_tot_work_accept = itemView.findViewById(R.id.tv_tot_work_accept);
            tv_tot_vac = itemView.findViewById(R.id.tv_tot_vac);
            tv_tot_acc_vac = itemView.findViewById(R.id.tv_tot_acc_vac);
            tv_tot_rej = itemView.findViewById(R.id.tv_tot_rej);
           // tv_tot_per_rej = itemView.findViewById(R.id.tv_tot_per_rej);

        }

        @Override
        public void onClick(View v) {

        }

    }


    public String getGenderHindi(String gender) {
        return gender;
    }



}