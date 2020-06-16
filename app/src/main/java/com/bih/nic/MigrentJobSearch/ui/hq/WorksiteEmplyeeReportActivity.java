package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.BlockWeb;
import com.bih.nic.MigrentJobSearch.Model.DepartmentWiseVacancy;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.WorkSiteEmployeeReportEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.DeptJobVacencyAdapter;
import com.bih.nic.MigrentJobSearch.adapter.WorksiteEmployeeReportAdapter;

import java.util.ArrayList;

public class WorksiteEmplyeeReportActivity extends Activity implements AdapterView.OnItemSelectedListener {

    RecyclerView listView;
    ImageView img_back;
    Spinner sp_district,sp_block;
    LinearLayout ll_content;

    WorksiteEmployeeReportAdapter adaptor_showedit_listDetail;
    ArrayList<WorkSiteEmployeeReportEntity> data;
    ArrayList<District>DistrictList=new ArrayList<>();
    ArrayList<BlockWeb>BlockList=new ArrayList<>();

    DataBaseHelper dataBaseHelper;
    String UserId="",UserRole="", DistrictCode="", BlockCode="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worksite_employee_report);
        Utiilties.setStatusBarColor(this);

        initialise();

        UserId= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");

        loadDistrictSpinnerData();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initialise(){
        dataBaseHelper = new DataBaseHelper(this);

        listView = findViewById(R.id.listviewjobposting);
        img_back=(ImageView) findViewById(R.id.img);

        sp_district = findViewById(R.id.sp_district);
        sp_block = findViewById(R.id.sp_block);
        ll_content = findViewById(R.id.ll_content);

        sp_district.setOnItemSelectedListener(this);
        sp_block.setOnItemSelectedListener(this);

        ll_content.setVisibility(View.GONE);
    }

    public void loadDistrictSpinnerData(){
        DistrictList = dataBaseHelper.getDistDetail();
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (District info: DistrictList){
            list.add(info.get_DistName());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_district.setAdapter(adaptor);

    }

    public void loadBlockSpinnerData(String district){
        BlockList.clear();
        BlockList = dataBaseHelper.getBlockDetail(district);
        ArrayList<String> list = new ArrayList<String>();
        list.add("All");
        for (BlockWeb info: BlockList){
            list.add(info.getBlockName());
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_block.setAdapter(adaptor);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.sp_district:
                if(position > 0){
                    DistrictCode = DistrictList.get(position - 1).get_DistCode();
                    loadBlockSpinnerData(DistrictCode);
                    new SyncWorksiteEmployeeData().execute();
                }
                break;
            case R.id.sp_block:
                if(position > 0){
                    BlockCode = BlockList.get(position - 1).getBlockCode();
                }else{
                    BlockCode = "0";
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SyncWorksiteEmployeeData extends AsyncTask<String, Void, ArrayList<WorkSiteEmployeeReportEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(WorksiteEmplyeeReportActivity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<WorkSiteEmployeeReportEntity> doInBackground(String...arg) {
            return WebserviceHelper.getWorksiteEmployeeReport("Detail", DistrictCode, BlockCode,"","","" );
        }

        @Override
        protected void onPostExecute(ArrayList<WorkSiteEmployeeReportEntity> result) {
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
            ll_content.setVisibility(View.VISIBLE);
            adaptor_showedit_listDetail = new WorksiteEmployeeReportAdapter(this, data, "0");
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adaptor_showedit_listDetail);
        }
        else
        {
            ll_content.setVisibility(View.GONE);
            //   tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
