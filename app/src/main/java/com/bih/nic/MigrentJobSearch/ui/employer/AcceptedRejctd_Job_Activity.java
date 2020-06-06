package com.bih.nic.MigrentJobSearch.ui.employer;

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;
import com.bih.nic.MigrentJobSearch.Model.BlockJobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.AccptedRctdJobAdapter;
import com.bih.nic.MigrentJobSearch.adapter.PostedJobBlockAdapter;

import java.util.ArrayList;

public class AcceptedRejctd_Job_Activity extends Activity implements AdapterView.OnItemSelectedListener {

    RecyclerView listView;
    TextView tv_Norecord,tv_distName,tv_skill11;
    Spinner spn_skill,spn_sub_skill;
    ImageView img_back;
    AccptedRctdJobAdapter adaptor_showedit_listDetail;
    ArrayList<AcptdRjctdJobOfferEntity> data;

    String DistId="",DistNAme="";

    DataBaseHelper dataBaseHelper;
    String status="",blkcode="", blkname="",serialno="0";
    String OrgId="",user_name="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId="",UserRole="",distid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_rejctd__job_);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        initialise();

        distid = getIntent().getStringExtra("distid");
        status = getIntent().getStringExtra("StatusFlag");
        blkcode = getIntent().getStringExtra("BlockCode");
        blkname = getIntent().getStringExtra("BlockNAme");

        if (status.equals("SHRGJA")){
            tv_skill11.setText("प्रखंड वाइज स्वीकृत नौकरी प्रस्ताव");
            tv_skill11.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
        }
        else if (status.equals("SHRGJR")){
            tv_skill11.setText("प्रखंड वाइज अस्वीकृत नौकरी प्रस्ताव");
            tv_skill11.setTextColor(getApplicationContext().getResources().getColor(R.color.holo_red_dark));
        }

        OrgId= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");

        //DistName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", "");
        CompanyName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ComanyName", "");
        mobile=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Mobile", "");
        address=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Address", "");


        new SyncAcceptedRjctdJobsOffers().execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SyncAcceptedRjctdJobsOffers extends AsyncTask<String, Void, ArrayList<AcptdRjctdJobOfferEntity>>
    {
        private final ProgressDialog dialog = new ProgressDialog(AcceptedRejctd_Job_Activity.this);
        int optionType;

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<AcptdRjctdJobOfferEntity> doInBackground(String...arg)
        {

            return WebserviceHelper.JobOfferAcptdRjctd(distid,blkcode, OrgId,UserRole,status,serialno);
        }

        @Override
        protected void onPostExecute(ArrayList<AcptdRjctdJobOfferEntity> result)
        {
            if (this.dialog.isShowing())
            {
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
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adaptor_showedit_listDetail = new AccptedRctdJobAdapter(this, data, OrgId);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adaptor_showedit_listDetail);

        }
        else
        {
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    public void initialise(){
        dataBaseHelper=new DataBaseHelper(this);

        spn_skill = findViewById(R.id.spn_skill);
        spn_sub_skill = findViewById(R.id.spn_sub_skill);
        tv_Norecord = findViewById(R.id.tv_Norecordacptrjctjob);
        tv_skill11 = findViewById(R.id.tv_skill11);

        listView = findViewById(R.id.listviewacptrjct);
        //tv_distName = findViewById(R.id.tv_distName);
        img_back=(ImageView) findViewById(R.id.img);

        //tv_distName.setText("जिला का नाम:-"+DistNAme);

        spn_sub_skill.setOnItemSelectedListener(this);
        spn_skill.setOnItemSelectedListener(this);
    }

}
