package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.DepartmentWiseVacancy;
import com.bih.nic.MigrentJobSearch.Model.SubDepartmentWiseVacancy;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.DeptJobVacencyAdapter;
import com.bih.nic.MigrentJobSearch.adapter.SubDeptJobVacencyAdapter;

import java.util.ArrayList;

public class SubDeptJobVacencyReportActivity extends Activity
{
    RecyclerView listView;
    ImageView img_back;
    TextView tv_skill11;
    SubDeptJobVacencyAdapter adaptor_showedit_listDetail;

    ArrayList<SubDepartmentWiseVacancy> data;

    String UserId="",UserRole="",Dept_Id="",Dept_Name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_dept_job_vacency_report);
        Utiilties.setStatusBarColor(this);

        initialise();
        Dept_Id = getIntent().getStringExtra("DeptCode");
        Dept_Name = getIntent().getStringExtra("DeptName");
        tv_skill11.setText(Dept_Name);
        UserId= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");

        new SyncSubDepartmentWiseVacancyData().execute();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initialise(){
        listView = findViewById(R.id.listviewjobposting);
        img_back=(ImageView) findViewById(R.id.img);
        tv_skill11=(TextView) findViewById(R.id.tv_skill11);
    }
    private class SyncSubDepartmentWiseVacancyData extends AsyncTask<String, Void, ArrayList<SubDepartmentWiseVacancy>> {
        private final ProgressDialog dialog = new ProgressDialog(SubDeptJobVacencyReportActivity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<SubDepartmentWiseVacancy> doInBackground(String...arg) {
            return WebserviceHelper.getSubDeptWiseVacencyReport("ShowOrg", Dept_Id);
        }

        @Override
        protected void onPostExecute(ArrayList<SubDepartmentWiseVacancy> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            data = result;
            populateData();

        }
    }


    public void populateData()
    {
        if(data != null && data.size()> 0)
        {
            Log.e("data", ""+data.size());
            // tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adaptor_showedit_listDetail = new SubDeptJobVacencyAdapter(this, data, "0");
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adaptor_showedit_listDetail);

        }
        else
        {
            listView.setVisibility(View.GONE);
            //   tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
