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

import com.bih.nic.MigrentJobSearch.Model.JobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkPress;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.ui.employer.JobOfferPosted_BlockActivity;

import java.util.ArrayList;

public class DeptJobAdapter extends RecyclerView.Adapter<DeptJobAdapter.ViewHolder> {

        Activity activity;
        LayoutInflater mInflater;
        ArrayList<WorkPress> ThrList=new ArrayList<>();
        String panchayatCode,panchayatName="";
        Boolean isShowDetail = false;
        String regNo;

public DeptJobAdapter(Activity listViewshowedit, ArrayList<WorkPress> rlist) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.regNo = regNo;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adptor_job_dept, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
final WorkPress info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position+1)+").");
        holder.tv_dept_name.setText(info.getDept_Name());
        holder.tv_karya_astal.setText(info.getKarya_Asthal());
        holder.tv_vacaency.setText(info.getNo_Of_Vacency());

        holder.tv_dept_name.setOnClickListener(new View.OnClickListener() {
   @Override
      public void onClick(View v) {
        Intent intent = new Intent(activity, JobOfferPosted_BlockActivity.class);
        //intent.putExtra("data",Reg_No);
        intent.putExtra("Dept_Code",info.getDept_Code());
        intent.putExtra("Dept_Name",info.getDept_Name());
        activity.startActivity(intent);
        }
        });

    holder.tv_karya_astal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, JobOfferPosted_BlockActivity.class);
            //intent.putExtra("data",Reg_No);
            intent.putExtra("Dept_Code",info.getDept_Code());
            intent.putExtra("Karya_Asthal",info.getKarya_Asthal());
            activity.startActivity(intent);
        }
    });
    holder.tv_vacaency.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, JobOfferPosted_BlockActivity.class);
            //intent.putExtra("data",Reg_No);
            intent.putExtra("Dept_Code",info.getDept_Code());
            intent.putExtra("No_Of_Vacency",info.getNo_Of_Vacency());
            activity.startActivity(intent);
        }
    });




}

@Override
public int getItemCount()
        {
        return ThrList.size();
        }

public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv_dept_name,tv_karya_astal,tv_vacaency,tv_slno;
    ImageView iv_call;


    Button btn_accpt,btn_rjct;
    LinearLayout ll_btn,ll_status,ll_selection;
    ViewHolder(View itemView) {
        super(itemView);
        tv_slno=itemView.findViewById(R.id.tv_slno);
        tv_dept_name=itemView.findViewById(R.id.tv_dept_name);
        tv_karya_astal=itemView.findViewById(R.id.tv_karya_astal);
        tv_vacaency=itemView.findViewById(R.id.tv_vacaency);


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
