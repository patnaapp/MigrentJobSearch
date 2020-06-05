package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.Model.BlkCompanyJobDetailsEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;


import static com.levitnudi.legacytableview.LegacyTableView.BOLD;

import static com.levitnudi.legacytableview.LegacyTableView.OCEAN;

public class BlocJobOfferActivity extends Activity implements AdapterView.OnItemSelectedListener  {


    LegacyTableView legacyTableView;
    ArrayList<BlkCompanyJobDetailsEntity> data;
    String status="",blkcode="", blkname="";
    String OrgId="",user_name="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId="",UserRole="",distid="";

    String DistId="",DistNAme="";
    TextView tv_skill11,tv_Norecord_accpt;
    ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legacy_table_view);
        tv_skill11=findViewById(R.id.tv_skill);
        tv_Norecord_accpt=findViewById(R.id.tv_Norecord_accpt);
        img_back=(ImageView) findViewById(R.id.img);
        Utiilties.setStatusBarColor(this);
        getActionBar().hide();

        distid = getIntent().getStringExtra("distid");
        // status = getIntent().getStringExtra("StatusFlag");
        blkcode = getIntent().getStringExtra("BlockCode");
        blkname = getIntent().getStringExtra("BlockNAme");

        tv_skill11.setText("नौकरी प्रस्ताव");
        tv_skill11.setTextColor(getApplicationContext().getResources().getColor(R.color.green));

        OrgId= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");

        //DistName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", "");
        CompanyName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ComanyName", "");
        mobile=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Mobile", "");
        address=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Address", "");

        new SyncAcceptedRjctdJobsOffers().execute();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //do something when user presses back
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case android.R.id.home:
                //finish activity once user presses back button
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private class SyncAcceptedRjctdJobsOffers extends AsyncTask<String, Void, ArrayList<BlkCompanyJobDetailsEntity>>
    {
        private final ProgressDialog dialog = new ProgressDialog(BlocJobOfferActivity.this);
        int optionType;

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<BlkCompanyJobDetailsEntity> doInBackground(String...arg)
        {
            return WebserviceHelper.BlkCompanyWiseJobOffers(distid,blkcode, OrgId,UserRole);
        }

        @Override
        protected void onPostExecute(ArrayList<BlkCompanyJobDetailsEntity> result)
        {


            data = result;
            if(data != null && data.size()> 0)
            {
                tv_Norecord_accpt.setVisibility(View.GONE);
                //tv_Norecord_accpt.setVisibility(View.GONE);

                LegacyTableView.insertLegacyTitle("क्रम सं.","कंपनी", "कंपनी का पता", "कार्य स्थल", "व्यक्तियों की संख्या","वेतन","स्थान","कौशल");
                //,"अभिभावक का नाम","अभिभावक का मोबाइल नंबर"

                int i=1;
                for (BlkCompanyJobDetailsEntity info: data)
                {

                    LegacyTableView.insertLegacyContent(String.valueOf(i),info.getComanyNameEn(),info.getAddressEn(),info.getWorkSiteNameHn(),info.getNoOfPerson(),info.getSalary(),info.getLocation(),info.getSkillName());
                    i++;
                }

                legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.setTheme(OCEAN);
                legacyTableView.setTablePadding(20);
                legacyTableView.setHighlight(1);

                legacyTableView.setShowZoomControls(true);
                legacyTableView.setBottomShadowVisible(true);
                legacyTableView.setTitleFont(BOLD);
                legacyTableView.setContentTextSize(30);
                legacyTableView.setTitleTextSize(35);

                legacyTableView.build();

                if (this.dialog.isShowing())
                {
                    this.dialog.dismiss();
                }
            }
            else
            {
                tv_Norecord_accpt.setVisibility(View.VISIBLE);
            }

        }
    }
}
