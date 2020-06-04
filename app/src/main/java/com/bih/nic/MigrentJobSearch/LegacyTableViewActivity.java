package com.bih.nic.MigrentJobSearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;
import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.Model.JobOfferPostedEntity;

import com.bih.nic.MigrentJobSearch.ui.labour.JobSearchActivity;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;

import static android.webkit.WebView.findAddress;
import static com.levitnudi.legacytableview.LegacyTableView.BOLD;
import static com.levitnudi.legacytableview.LegacyTableView.CUSTOM;
import static com.levitnudi.legacytableview.LegacyTableView.GOLDALINE;
import static com.levitnudi.legacytableview.LegacyTableView.ODD;

public class LegacyTableViewActivity extends Activity implements AdapterView.OnItemSelectedListener  {


    LegacyTableView legacyTableView;
    ArrayList<AcptdRjctdJobOfferEntity> data;
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
        else if (status.equals("SHRG")){
            tv_skill11.setText("प्रखंड वाइज पंजीकरण");
            tv_skill11.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
        }
        else if (status.equals("SHRGJ")){
            tv_skill11.setText("प्रखंड वाइज नौकरी प्रस्ताव");
            tv_skill11.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
        }

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
        switch (item.getItemId()){
            case android.R.id.home:
                //finish activity once user presses back button

                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private class SyncAcceptedRjctdJobsOffers extends AsyncTask<String, Void, ArrayList<AcptdRjctdJobOfferEntity>>
    {
        private final ProgressDialog dialog = new ProgressDialog(LegacyTableViewActivity.this);
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
            return WebserviceHelper.JobOfferAcptdRjctd(distid,blkcode, OrgId,UserRole,status);
        }

        @Override
        protected void onPostExecute(ArrayList<AcptdRjctdJobOfferEntity> result)
        {


            data = result;
            if(data != null && data.size()> 0)
            {
                tv_Norecord_accpt.setVisibility(View.GONE);
                if (status.equals("SHRGJ")){

                    LegacyTableView.insertLegacyTitle("क्रम सं.","पंजीकरण संख्या", "कौशल", "नाम", "लिंग","मोबाइल नंबर","कार्य स्थल का नाम");
                    //,"अभिभावक का नाम","अभिभावक का मोबाइल नंबर"

                    int i=1;
                    for (AcptdRjctdJobOfferEntity info: data)
                    {

                        LegacyTableView.insertLegacyContent(String.valueOf(i),info.getVchregnum(),info.getSkillName(),info.getVchName(),info.getGender(),info.getVchMobile(),info.getWorkSiteNameHn());
                        i++;
                    }

                }
                else if (status.equals("SHRG")){

                    LegacyTableView.insertLegacyTitle("क्रम सं.","पंजीकरण संख्या", "नाम","मोबाइल नंबर", "लिंग");
                    //,"अभिभावक का नाम","अभिभावक का मोबाइल नंबर"

                    int i=1;
                    for (AcptdRjctdJobOfferEntity info: data){

                        LegacyTableView.insertLegacyContent(String.valueOf(i),info.getVchregnum(),info.getVchName(),info.getVchMobile(),info.getGender());
                        i++;
                    }

                }
                else {
                    LegacyTableView.insertLegacyTitle("क्रम सं.","पंजीकरण संख्या", "कौशल", "नाम", "लिंग","मोबाइल नंबर");
                    //,"अभिभावक का नाम","अभिभावक का मोबाइल नंबर"


                    int i=1;
                    for (AcptdRjctdJobOfferEntity info: data){

                        LegacyTableView.insertLegacyContent(String.valueOf(i),info.getVchregnum(),info.getSkillName(),info.getVchName(),info.getGender(),info.getVchMobile());
                        i++;
                    }

                }


                legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.setTheme(GOLDALINE);
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
            else {

                tv_Norecord_accpt.setVisibility(View.VISIBLE);
            }

        }
    }
}
