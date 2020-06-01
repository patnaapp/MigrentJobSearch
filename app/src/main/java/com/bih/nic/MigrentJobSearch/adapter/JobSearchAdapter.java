package com.bih.nic.MigrentJobSearch.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;

import java.util.ArrayList;

public class JobSearchAdapter extends RecyclerView.Adapter<JobSearchAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<JobListEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="";
    Boolean isShowDetail = false;

    public JobSearchAdapter(Activity listViewshowedit, ArrayList<JobListEntity> rlist) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_job_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final JobListEntity info = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position+1));
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
        holder.tv_supervisor_name.setText(info.getContactName());
        holder.tv_super_no.setText(info.getContactNumber());

//        holder.rl_view_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isShowDetail = !isShowDetail;
//
//                if(isShowDetail){
//                    holder.tv_detail_btn.setText("View Less");
//                    holder.ll_detail.setVisibility(View.VISIBLE);
//                }else{
//                    holder.tv_detail_btn.setText("View More");
//                    holder.ll_detail.setVisibility(View.GONE);
//                }
//                //Toast.makeText(context, "view detail", Toast.LENGTH_SHORT).show();
//            }
//        });


        if(info.getContactNumber() != null && info.getContactNumber() != "NA"){
            holder.iv_call.setVisibility(View.VISIBLE);
        }else{
            holder.iv_call.setVisibility(View.GONE);
        }

        holder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", info.getContactNumber(), null));
                activity.startActivity(intent);
            }
        });
        holder.btn_accpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utiilties.isOnline(activity)) {
                    android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(activity);
                    ab.setTitle("Acceptance Confirmation!");
                    ab.setIcon(R.drawable.que);
                    ab.setMessage("Are you sure you want to accept this job ?");
                    ab.setPositiveButton("[No]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                        }
                    });

                    ab.setNegativeButton("[Accept]", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

//                    ArrayList<UploadedPaddyToRmEntity> dataProgress = dbHelper.getPaddyRecordsForAcptRjct(UserId, rowid);
//                    for (UploadedPaddyToRmEntity data : dataProgress) {
                            // new AcceptRecordsFromPacs(info.getId(),"A").execute();

                            new AcceptRecordsFromPacs(info).execute();
                            // }
                        }
                    });
                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();
                }
                else {
                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                    alertDialog.setTitle("Alert !!");
                    alertDialog.setMessage("Please turn on internet connection to proceed.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            activity.startActivity(I);
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        holder.btn_rjct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utiilties.isOnline(activity)) {
                    android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(
                            activity);
                    ab.setTitle("Rejection Confirmation!");
                    ab.setIcon(R.drawable.que);
                    ab.setMessage("Are you sure you want to reject this record ?");
                    ab.setPositiveButton("[No]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                        }
                    });

                    ab.setNegativeButton("[REJECT]", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

//                    ArrayList<UploadedPaddyToRmEntity> dataProgress = dbHelper.getPaddyRecordsForAcptRjct(UserId, rowid);
//                    for (UploadedPaddyToRmEntity data : dataProgress) {
                            new RejectRecordsFromPacs(info).execute();
                            // }
                        }
                    });
                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();
                }
                else {
                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                    alertDialog.setTitle("Alert !!");
                    alertDialog.setMessage("Please turn on internet connection to proceed.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            activity.startActivity(I);
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_work_site,tv_skill_cat,tv_skill_name,tv_person_no,tv_gendar,tv_start_date,tv_exp,tv_exp_max,tv_salary,tv_salary_max,tv_block,tv_supervisor_name,tv_super_no;
        ImageView iv_call;
        Button btn_accpt,btn_rjct;
        ViewHolder(View itemView) {
            super(itemView);
            tv_slno=itemView.findViewById(R.id.tv_slno);
            tv_work_site=itemView.findViewById(R.id.tv_work_site);
            tv_skill_cat=itemView.findViewById(R.id.tv_skill_cat);
            tv_skill_name=itemView.findViewById(R.id.tv_skill_name);
            tv_person_no=itemView.findViewById(R.id.tv_person_no);
            tv_gendar=itemView.findViewById(R.id.tv_gendar);
            tv_start_date=itemView.findViewById(R.id.tv_start_date);
            tv_exp=itemView.findViewById(R.id.tv_exp);
            tv_exp_max=itemView.findViewById(R.id.tv_exp_max);
            tv_salary=itemView.findViewById(R.id.tv_salary);
            tv_salary_max=itemView.findViewById(R.id.tv_salary_max);
            tv_block=itemView.findViewById(R.id.tv_block);
            tv_supervisor_name=itemView.findViewById(R.id.tv_supervisor_name);
            tv_super_no=itemView.findViewById(R.id.tv_super_no);
            iv_call=itemView.findViewById(R.id.iv_call);
            btn_accpt=itemView.findViewById(R.id.btn_accpt);
            btn_rjct=itemView.findViewById(R.id.btn_rjct);
        }

        @Override
        public void onClick(View v) {

        }

    }


    public String getGenderHindi(String gender){

        return gender;
    }

    private class AcceptRecordsFromPacs extends AsyncTask<String, Void, String> {
        JobListEntity data;
        String rowid;
        private final ProgressDialog dialog = new ProgressDialog(activity);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(activity).create();


        AcceptRecordsFromPacs(JobListEntity data) {
            this.data = data;
            //_uid = data.getId();
            //rowid = data.get_phase1_id();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {


            String res = WebserviceHelper.UploadAcceptedRecordsFromPacs(data);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {
                String string = result;
                String[] parts = string.split(",");
                String part1 = parts[0]; // 004-
                String part2 = parts[1];

                if (part1.equals("1")) {
//
//                    dataBaseupload = new DataBaseHelper(mContext);
//                    long c = dataBaseupload.deleteRecPhase1(rowid);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setIcon(R.drawable.logo);
                    builder.setTitle("Record Accepted!!");
                    // Ask the final question
                    builder.setMessage("Job Acceptance Uploaded Successfully for Job ID:-"+data.getId());


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
//                            dialog.dismiss();
//                            refresh(Phase1Listt);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                            dialog.dismiss();
                            activity.finish();


                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                } else if (part1.equals("0")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setIcon(R.drawable.logo);
                    builder.setTitle("Record Not Accepted!!");
                    // Ask the final question
                    builder.setMessage("Job Removed Successfully for Job ID-"+rowid);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
//                            dialog.dismiss();
//                            refresh(Phase1Listt);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                }
                else
                    {
                    Toast.makeText(activity, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }
            }
            else
                {

                Toast.makeText(activity, "Result:null ..Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private class RejectRecordsFromPacs extends AsyncTask<String, Void, String> {
        JobListEntity data;
        String rowid;
        private final ProgressDialog dialog = new ProgressDialog(activity);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(activity).create();


        RejectRecordsFromPacs(JobListEntity data) {
            this.data = data;
            //_uid = data.getId();
            //rowid = data.get_phase1_id();

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            String res = WebserviceHelper.UploadRejectedRecordsFromPacs(data);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {
                String string = result;
                String[] parts = string.split(",");
                String part1 = parts[0]; // 004-
                String part2 = parts[1];

                if (part1.equals("1")) {
//
//                    dataBaseupload = new DataBaseHelper(mContext);
//                    long c = dataBaseupload.deleteRecPhase1(rowid);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setIcon(R.drawable.logo);
                    builder.setTitle("Record Rejected!!");
                    // Ask the final question
                    builder.setMessage("Paddy Record Rejected for SlNo-"+data.getId());


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
//                            dialog.dismiss();
//                            refresh(Phase1Listt);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                            dialog.dismiss();
                            activity.finish();


                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                } else if (part1.equals("0")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setIcon(R.drawable.logo);
                    builder.setTitle("Record Not Rejected!!");
                    // Ask the final question
                    builder.setMessage("Paddy Record Not Rejected for SlNo-"+rowid);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
//                            dialog.dismiss();
//                            refresh(Phase1Listt);
//                            Phase1Listt = dataBaseHelper.getAllEntryDetailPhase1(PreferenceManager.getDefaultSharedPreferences(mContext).getString("USERID", ""));
                            dialog.dismiss();



                        }
                    });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                } else {
                    Toast.makeText(activity, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(activity, "Result:null ..Uploading failed...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
