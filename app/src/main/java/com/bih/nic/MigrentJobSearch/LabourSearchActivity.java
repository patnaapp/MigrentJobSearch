package com.bih.nic.MigrentJobSearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.Model.panchayat;
import com.bih.nic.MigrentJobSearch.Model.workListModel;
import com.bih.nic.MigrentJobSearch.adapter.JobSearchAdapter;
import com.bih.nic.MigrentJobSearch.adapter.LabourSearchAdaptor;

import java.util.ArrayList;

public class LabourSearchActivity extends Activity implements AdapterView.OnItemSelectedListener {

    RecyclerView listView;
    TextView tv_Norecord;
    Spinner spn_skill,spn_sub_skill;
    ImageView img_back;
    LabourSearchAdaptor labourSearchAdaptor;

    ProgressDialog dialog;
    ArrayList<JobListEntity> data;
    ArrayList<District> DistrictList;

    String skillId,subSkillId;
    String DistId="", RegNo;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_search);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        initialise();

        RegNo = getIntent().getStringExtra("data");


        String distName = getIntent().getStringExtra("DistName");
        new SyncJobSearchData().execute();

        new  LoadJonList("12").execute();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initialise(){
        dataBaseHelper=new DataBaseHelper(this);

        spn_skill = findViewById(R.id.spn_skill);
        spn_sub_skill = findViewById(R.id.spn_sub_skill);
        tv_Norecord = findViewById(R.id.tv_Norecord);

        listView = findViewById(R.id.listviewshow);
        img_back=(ImageView) findViewById(R.id.img);

        spn_sub_skill.setOnItemSelectedListener(this);
        spn_skill.setOnItemSelectedListener(this);
    }

    public void populateData(){
        if(data != null && data.size()> 0){
            Log.e("data", ""+data.size());
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            labourSearchAdaptor = new LabourSearchAdaptor(this, data, RegNo);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(labourSearchAdaptor);

        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    private class LoadJonList extends AsyncTask<String, Void, ArrayList<workListModel>>
    {
        String blockcode = "";
        ArrayList<workListModel> blocklist = new ArrayList<>();
        private final ProgressDialog dialog = new ProgressDialog(
                LabourSearchActivity.this);

        LoadJonList(String distCode) {
            this.blockcode = distCode;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<workListModel> doInBackground(String... param)
        {

            this.blocklist = WebserviceHelper.GetWorkDetails(blockcode);

            return this.blocklist;
        }

        @Override
        protected void onPostExecute(ArrayList<workListModel> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();

            }

            if (result != null)
            {
                if (result.size() > 0)
                {
                    DataBaseHelper placeData = new DataBaseHelper(LabourSearchActivity.this);

                 //   long i = placeData.setPanchayatLocal(result, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("District", ""),blockcode);
                    if (result.size() > 0) setJobList(result);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();

                }
            }

        }

    }



    public void setDistrictSpinner(String DistName){
        DistrictList=dataBaseHelper.getDistDetail();
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        for (District info: DistrictList){
            list.add(info.get_DistName());
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sub_skill.setAdapter(adaptor);

        try{
            if(!DistName.isEmpty()){
                spn_sub_skill.setSelection(list.indexOf(DistName));
            }
        }catch (Exception e){
            new SyncJobSearchData().execute();
        }

    }  public void setJobList(ArrayList<workListModel>joblist){

        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        for (workListModel info: joblist){
            list.add(info.getWorl_Name());
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sub_skill.setAdapter(adaptor);

        /*try{
            if(!DistName.isEmpty()){
                spn_sub_skill.setSelection(list.indexOf(DistName));
            }
        }catch (Exception e){
            new SyncJobSearchData().execute();
        }*/

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
//            case R.id.spn_skill:
//                if (position > 0) {
//                    skillId = skillList.get(position -1).getId();
//                    loadSubSkillSpinnerData();
//                }
//                break;
            case R.id.spn_sub_skill:
                if (position > 0) {
                    DistId = DistrictList.get(position - 1).get_DistCode();
                    new SyncJobSearchData().execute();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SyncJobSearchData extends AsyncTask<String, Void, ArrayList<JobListEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(LabourSearchActivity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<JobListEntity> doInBackground(String...arg) {
            return WebserviceHelper.searchJobMasterData(RegNo, DistId);
        }

        @Override
        protected void onPostExecute(ArrayList<JobListEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            data = checkForJobSelection(result);
            populateData();

        }
    }

    public void showAlertForInternet(){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Internet Connnection Error!!!");
        ab.setMessage("Please turn on your mobile data or wifi connection");
        ab.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int whichButton) {
                GlobalVariables.isOffline = false;
                Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(I);
                finish();
            }
        });
        ab.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        finish();
                    }
                });

        ab.show();
    }

    public ArrayList<JobListEntity> checkForJobSelection(ArrayList<JobListEntity> list){
        ArrayList<JobListEntity> selectedJob = new ArrayList<JobListEntity>();
        for(JobListEntity item: list){
            if(item.getIsSelected() != null && item.getIsSelected().equals("Y")){
                selectedJob.add(item);
                return selectedJob;
            }
        }

        return list;
    }
}
