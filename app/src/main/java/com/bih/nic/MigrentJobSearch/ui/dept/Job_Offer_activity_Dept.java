package com.bih.nic.MigrentJobSearch.ui.dept;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.BenDetails;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.JobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.Model.SkillMaster;
import com.bih.nic.MigrentJobSearch.Model.SubSkillMaster;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.PostedJobAdapter;

import java.util.ArrayList;

public class Job_Offer_activity_Dept extends Activity implements AdapterView.OnItemSelectedListener {

    RecyclerView listView;
    TextView tv_Norecord;
    Spinner spn_skill,spn_sub_skill;
    ImageView img_back;
    PostedJobAdapter adaptor_showedit_listDetail;

    ProgressDialog dialog;
    ArrayList<JobOfferPostedEntity> data;
    BenDetails benDetails;

    ArrayList<SkillMaster> skillList, cateogryList;
    ArrayList<SubSkillMaster> subSkillList;
    ArrayList<District> DistrictList;

    String skillId,subSkillId;

    String OrgId="",user_name="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId="",UserRole="";

    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer_posted);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        initialise();

        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");

        new SyncHQJobOfferData().execute();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }


    public void initialise(){
        dataBaseHelper=new DataBaseHelper(this);

//        spn_skill = findViewById(R.id.spn_skill);
//
//        spn_sub_skill = findViewById(R.id.spn_sub_skill);
//        tv_Norecord = findViewById(R.id.tv_Norecordjobposting);

        listView = findViewById(R.id.listviewjobposting);
        img_back=(ImageView) findViewById(R.id.img);

//        spn_sub_skill.setOnItemSelectedListener(this);
//        spn_skill.setOnItemSelectedListener(this);
    }

    private class SyncHQJobOfferData extends AsyncTask<String, Void, ArrayList<JobOfferPostedEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(Job_Offer_activity_Dept.this);
        int optionType;

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<JobOfferPostedEntity> doInBackground(String...arg)
        {
            return WebserviceHelper.JobOfferPosted(UserRole, "");
        }

        @Override
        protected void onPostExecute(ArrayList<JobOfferPostedEntity> result)
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
           // tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adaptor_showedit_listDetail = new PostedJobAdapter(this, data, OrgId);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adaptor_showedit_listDetail);

        }
        else
        {
            listView.setVisibility(View.GONE);
         //   tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    protected void onResume() {
        super.onResume();

    }

}
