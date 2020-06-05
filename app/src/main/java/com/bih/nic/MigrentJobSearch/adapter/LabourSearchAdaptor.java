package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.bih.nic.MigrentJobSearch.Model.WorkerModel;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.ui.employer.LabourDetailActivity;


import java.util.ArrayList;

public class LabourSearchAdaptor extends RecyclerView.Adapter<LabourSearchAdaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<WorkerModel> ThrList = new ArrayList<>();
    Boolean isShowDetail = false;
    String regNo;

    public LabourSearchAdaptor(Activity listViewshowedit, ArrayList<WorkerModel> rlist, String regNo) {
        this.activity = listViewshowedit;
        this.ThrList = rlist;
        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_search_labour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final WorkerModel info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position + 1));


        holder.tv_block.setText(info.getBlockNameHN());
    //    holder.tv_supervisor_name.setText(info.getContactName());
       // holder.tv_super_no.setText(info.getContactNumber());
        holder.tv_district.setText(info.getDistrictNameHN());


        holder.tv_start_date.setText(info.getStartDate());
        holder.tv_exp.setText(info.getExperiance());
        holder.tv_exp_max.setText(info.getExperianceMax());
        holder.tv_salary.setText(info.getSalary());
        holder.tv_salary_max.setText(info.getSalaryMax());
        holder.tv_skill_cat.setText(info.getSkillCategoryHn());
        holder._noof_labour_found.setText(info.getLabourFound());
        holder.tv_req_gen.setText(info.getGender());
        holder.tv_supervisor_name.setText(info.getContactPersonHn());
        holder.tv_super_no.setText(info.getCPMobileNo());
        holder.tv_workdetl_name.setText(info.getWorkSiteName());
        holder.tv_req_per.setText(info.getNoOfPerson());
        holder.tv_is_verified.setText(info.getIsVerified());
        holder.tv_pin.setText(info.getPinNo());
        holder.ll_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, LabourDetailActivity.class);
                intent.putExtra("SkillId",info.getSkillId());
                intent.putExtra("Gender",info.getGender());
                intent.putExtra("Exp",info.getExperiance());
                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return ThrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_skill_cat,tv_start_date, tv_exp, tv_exp_max, tv_salary, tv_salary_max, tv_block, tv_supervisor_name, tv_super_no;
        TextView iv_call,tv_is_verified,tv_req_per,tv_pin,_noof_labour_found,tv_req_gen;

        TextView tv_workdetl_name,tv_district;


        Button btn_accpt, btn_rjct;
        LinearLayout ll_btn, ll_status, ll_selection;

        ViewHolder(View itemView) {
            super(itemView);
            tv_slno = itemView.findViewById(R.id.tv_slno);




            tv_start_date = itemView.findViewById(R.id.tv_start_date);
            tv_exp = itemView.findViewById(R.id.tv_exp);
            tv_exp_max = itemView.findViewById(R.id.tv_exp_max);
            tv_salary = itemView.findViewById(R.id.tv_salary);
            tv_salary_max = itemView.findViewById(R.id.tv_salary_max);

            tv_workdetl_name = itemView.findViewById(R.id.tv_workdetl_name);
            tv_skill_cat = itemView.findViewById(R.id.tv_skill_cat);
            tv_supervisor_name = itemView.findViewById(R.id.tv_supervisor_name);
            tv_super_no = itemView.findViewById(R.id.tv_super_no);
            tv_is_verified = itemView.findViewById(R.id.tv_is_verified);
            tv_req_per = itemView.findViewById(R.id.tv_req_per);
            tv_district = itemView.findViewById(R.id.tv_district);
            tv_block = itemView.findViewById(R.id.tv_block);
            tv_pin = itemView.findViewById(R.id.tv_pin);
            _noof_labour_found = itemView.findViewById(R.id._noof_labour_found);
            tv_req_gen = itemView.findViewById(R.id.tv_req_gen);
            ll_selection = itemView.findViewById(R.id.ll_selection);

        }

        @Override
        public void onClick(View v) {

        }

    }


    public String getGenderHindi(String gender) {

        return gender;
    }

 /*   private class AcceptRecordsFromPacs extends AsyncTask<String, Void, DefaultResponse> {
        JobListEntity data;
        String rowid;
        int position;
        private final ProgressDialog dialog = new ProgressDialog(activity);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(activity).create();


        AcceptRecordsFromPacs(JobListEntity data, int position) {
            this.data = data;
            this.position = position;
            //_uid = data.getId();
            //rowid = data.get_phase1_id();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("पुष्टि किया जा रहा हैं...");
            this.dialog.show();
        }

        @Override
        protected DefaultResponse doInBackground(String... param) {
            DefaultResponse res = WebserviceHelper.UploadAcceptedRecordsFromPacs(data, regNo);
            return res;

        }

        @Override
        protected void onPostExecute(DefaultResponse result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            Log.d("Responsevalue", "" + result);
            if (result != null) {
                if (result.getStatus()) {
                    ThrList.get(position).setIsAccepted("Y");
                    notifyDataSetChanged();

                    new android.app.AlertDialog.Builder(activity)
                            .setTitle("सूचना")
                            .setMessage("नौकरी का अनुरोध अपडेट कर दिया गया है, आगे की जानकारी सिग्रह ही आपको अप्डेट की जाएगी|")
                            .setCancelable(true)
                            .setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            }).show();
                    //Toast.makeText(activity, "नौकरी का अनुरोध अपडेट कर दिया गया है, आगे की जानकारी सिग्रह ही आपको अप्डेट की जाएगी|", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setIcon(R.drawable.logo);
                    builder.setTitle("Failed");
                    // Ask the final question
                    builder.setMessage(result.getMessage());
                    builder.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }

            } else {
                Toast.makeText(activity, "Result:null ..Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }*/


}

