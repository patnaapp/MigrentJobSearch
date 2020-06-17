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
import com.bih.nic.MigrentJobSearch.Model.BlockJobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.SkillMaster;
import com.bih.nic.MigrentJobSearch.Model.SubSkillMaster;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.PostedJobBlockAdapter;

import java.util.ArrayList;

public class JobOffer_DEPT_BlockActivity extends Activity implements AdapterView.OnItemSelectedListener {

    RecyclerView listView;
    TextView tv_Norecord,tv_distName;
    Spinner spn_skill,spn_sub_skill;
    ImageView img_back;
    PostedJobBlockAdapter adaptor_showedit_listDetail;

    ProgressDialog dialog;
    ArrayList<BlockJobOfferPostedEntity> data;
    BenDetails benDetails;

    ArrayList<SkillMaster> skillList, cateogryList;
    ArrayList<SubSkillMaster> subSkillList;
    ArrayList<District> DistrictList;

    String skillId,subSkillId;
    String DistId="",DistNAme="";

    DataBaseHelper dataBaseHelper;
    String OrgId="",user_name="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId="",UserRole="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blk_job_offer_posted);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        DistId = getIntent().getStringExtra("DistCode");
        DistNAme = getIntent().getStringExtra("DistName");

        initialise();



        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");




        new SyncJobOfferBlkWise_HQ().execute();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void initialise(){
        dataBaseHelper=new DataBaseHelper(this);

//        spn_skill = findViewById(R.id.spn_skill);
//        spn_sub_skill = findViewById(R.id.spn_sub_skill);
        //tv_Norecord = findViewById(R.id.tv_Norecordjobposting);

        listView = findViewById(R.id.listviewjobposting);
        tv_distName = findViewById(R.id.tv_distName);
        img_back=(ImageView) findViewById(R.id.img);

        tv_distName.setText("जिला का नाम:-"+DistNAme);

//        spn_sub_skill.setOnItemSelectedListener(this);
//        spn_skill.setOnItemSelectedListener(this);
    }

    private class SyncJobOfferBlkWise_HQ extends AsyncTask<String, Void, ArrayList<BlockJobOfferPostedEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(JobOffer_DEPT_BlockActivity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<BlockJobOfferPostedEntity> doInBackground(String...arg) {
            return WebserviceHelper.BlockJobOfferPosted(DistId, "",UserRole);
        }

        @Override
        protected void onPostExecute(ArrayList<BlockJobOfferPostedEntity> result) {
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
          //  tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adaptor_showedit_listDetail = new PostedJobBlockAdapter(this, data, OrgId);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adaptor_showedit_listDetail);

        }
        else
        {
            listView.setVisibility(View.GONE);
            //tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    protected void onResume() {
        super.onResume();

    }
}
