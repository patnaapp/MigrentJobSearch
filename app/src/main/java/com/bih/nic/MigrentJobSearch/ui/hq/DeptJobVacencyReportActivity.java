package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.adapter.PostedJobAdapter;

public class DeptJobVacencyReportActivity extends Activity  {

    RecyclerView listView;
    ImageView img_back;
    PostedJobAdapter adaptor_showedit_listDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_job_vacency_report);
        Utiilties.setStatusBarColor(this);
    }
}
