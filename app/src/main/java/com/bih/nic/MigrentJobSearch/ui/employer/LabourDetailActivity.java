package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkerModel;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.LabourDetailAdaptor;

import java.util.ArrayList;

public class LabourDetailActivity  extends Activity implements AdapterView.OnItemSelectedListener {

    RecyclerView listView;
    TextView tv_Norecord;
    Spinner spn_skill, spn_sub_skill;
    ImageView img_back;
    LabourDetailAdaptor labourDetailAdaptor;

    ProgressDialog dialog;
    ArrayList<WorkerModel> data;
    ArrayList<District> DistrictList;

    String skillId, subSkillId;
    String DistId = "", RegNo;
    String JobID="",SkillId="",Gender="",Exp="";
    String ORGNID="";

    DataBaseHelper dataBaseHelper;
    LinearLayout lin_sub_skill;
     ArrayList<WorkerModel> WorkJobList = new ArrayList<>();
    String  DistCode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_search);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        initialise();
        try {
            SkillId = getIntent().getStringExtra("SkillId");
            Gender = getIntent().getStringExtra("Gender");
            Exp = getIntent().getStringExtra("Exp");
            if(Gender.equalsIgnoreCase("Male")){
                Gender="1";
            }else if(Gender.equalsIgnoreCase("Female")){
                Gender="2";
            }else if(Gender.equalsIgnoreCase("Other")){
                Gender="0";
            }else if(Gender.equalsIgnoreCase("Any")){
                Gender="0";
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        DistCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistCode", "");

        Log.d("GUHUHuu","&&&"+DistCode+"##"+SkillId+"##"+Gender+"##"+Exp);
        String distName = getIntent().getStringExtra("DistName");
        lin_sub_skill.setVisibility(View.GONE);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(Utiilties.isOnline(LabourDetailActivity.this)) {

            new LoadLabourDetail(DistCode, SkillId, Exp, Gender).execute();
        }else {
            showAlertForInternet();
        }

    }

    public void initialise() {
        dataBaseHelper = new DataBaseHelper(this);

        lin_sub_skill = findViewById(R.id.lin_sub_skill);
        spn_skill = findViewById(R.id.spn_skill);
        spn_sub_skill = findViewById(R.id.spn_sub_skill);
        tv_Norecord = findViewById(R.id.tv_Norecord);

        listView = findViewById(R.id.listviewshow);
        img_back = (ImageView) findViewById(R.id.img);

        spn_sub_skill.setOnItemSelectedListener(this);
        spn_skill.setOnItemSelectedListener(this);
    }

    public void populateData() {
        if (data != null && data.size() > 0) {
            Log.e("data", "" + data.size());
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            labourDetailAdaptor = new LabourDetailAdaptor(this, data, RegNo);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(labourDetailAdaptor);

        } else {
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       /* switch (parent.getId()) {

            case R.id.spn_sub_skill:
                if (position > 0) {
                    JobID = WorkJobList.get(position - 1).getWork_Id();
                    new SyncSearchLabourData().execute();
                }
                break;
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    public ArrayList<WorkerModel> checkForJobSelection(ArrayList<WorkerModel> list){
        ArrayList<JobListEntity> selectedJob = new ArrayList<JobListEntity>();
        for(WorkerModel item: list){

        }

        return list;
    }
    private class LoadLabourDetail extends AsyncTask<String, Void, ArrayList<WorkerModel>>
    {
        String DistrictCode = "";
        String SkillId = "";
        String Experiance = "";
        String Gender = "";
        ArrayList<WorkerModel> WorkerModel = new ArrayList<>();
        private final ProgressDialog dialog = new ProgressDialog(
                LabourDetailActivity.this);

        LoadLabourDetail(String DistrictCode,String SkillId,String Experiance,String Gender) {
            this.DistrictCode = DistrictCode;
            this.SkillId = SkillId;
            this.Experiance = Experiance;
            this.Gender = Gender;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<WorkerModel> doInBackground(String... param)
        {

            this.WorkerModel = WebserviceHelper.GetLoadLabourData(DistrictCode,SkillId,Experiance,Gender);

            return this.WorkerModel;
        }

        @Override
        protected void onPostExecute(ArrayList<WorkerModel> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();

            }

            if (result != null)
            {
                if (result.size() > 0)
                {
                    data=result;
                    populateData();
                              }
                else
                {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();

                }
            }

        }

    }

}
