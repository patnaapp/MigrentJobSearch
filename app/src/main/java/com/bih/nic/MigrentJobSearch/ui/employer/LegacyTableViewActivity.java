package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;

import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;

import static com.levitnudi.legacytableview.LegacyTableView.BOLD;
import static com.levitnudi.legacytableview.LegacyTableView.DESKTOP;
import static com.levitnudi.legacytableview.LegacyTableView.ECOKENYA;
import static com.levitnudi.legacytableview.LegacyTableView.GOLDALINE;
import static com.levitnudi.legacytableview.LegacyTableView.LAVICI;
import static com.levitnudi.legacytableview.LegacyTableView.MAASAI;
import static com.levitnudi.legacytableview.LegacyTableView.MESH;
import static com.levitnudi.legacytableview.LegacyTableView.OCEAN;
import static com.levitnudi.legacytableview.LegacyTableView.ORIO;
import static com.levitnudi.legacytableview.LegacyTableView.SKELETON;

public class LegacyTableViewActivity extends Activity implements AdapterView.OnItemSelectedListener  {


    LegacyTableView legacyTableView;
    ArrayList<AcptdRjctdJobOfferEntity> data;
    String status="",blkcode="", blkname="";
    String OrgId="",user_name="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId="",UserRole="",distid="";

    String DistId="",DistNAme="";
    TextView tv_skill11,tv_Norecord_accpt,tv_total_count;
    ImageView img_back;
    ImageView btn_previous,btn_next;
    String serialno="0", count="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legacy_table_view);
        tv_skill11=findViewById(R.id.tv_skill);
        tv_total_count=findViewById(R.id.tv_total_count);
        //  tv_Norecord_accpt=findViewById(R.id.tv_Norecord_accpt);
        img_back=(ImageView) findViewById(R.id.img);
        btn_previous=(ImageView) findViewById(R.id.btn_previous);
        btn_next=(ImageView) findViewById(R.id.btn_next);
        Utiilties.setStatusBarColor(this);
        getActionBar().hide();

        distid = getIntent().getStringExtra("distid");
        // distid1 = getIntent().getStringExtra("distid");
        status = getIntent().getStringExtra("StatusFlag");
        blkcode = getIntent().getStringExtra("BlockCode");
        blkname = getIntent().getStringExtra("BlockNAme");
        count = getIntent().getStringExtra("Count");

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


//        String[] Title={"क्रम सं.","पंजीकरण संख्या", "कौशल", "नाम", "लिंग","मोबाइल नंबर","कार्य स्थल का नाम"};
//
//        legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
//
//        // legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
//        legacyTableView.setTitle(Title);

        new SyncAcceptedRjctdJobsOffers("0").execute();




        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SyncAcceptedRjctdJobsOffers(data.get(data.size()-1).getRow_num()).execute();
            }
        });


        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Integer slno= Integer.parseInt(data.get(0).getRow_num());
                new SyncAcceptedRjctdJobsOffers(String.valueOf(slno-201)).execute();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //do something when user presses back
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

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

    public void handleButtonView(String start, String end){
        if(start.equals("1")){
            btn_previous.setVisibility(View.GONE);
        }else{
            btn_previous.setVisibility(View.VISIBLE);
        }

        if(end.equals(count)){
            btn_next.setVisibility(View.GONE);
        }else{
            btn_next.setVisibility(View.VISIBLE);
        }
    }


    private class SyncAcceptedRjctdJobsOffers extends AsyncTask<String, Void, ArrayList<AcptdRjctdJobOfferEntity>>
    {
        private final ProgressDialog dialog = new ProgressDialog(LegacyTableViewActivity.this);
        int optionType;
        String serial_no;

        public SyncAcceptedRjctdJobsOffers(String serial_no) {
            this.serial_no = serial_no;
        }

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
            if(OrgId.equals("NA"))
            {
                OrgId="";
            }
            return WebserviceHelper.JobOfferAcptdRjctd(distid,blkcode,OrgId,UserRole,status,serial_no);

        }

        @Override
        protected void onPostExecute(ArrayList<AcptdRjctdJobOfferEntity> result)
        {


            data = result;

            ArrayList<String>  Content=new ArrayList<>();

//
//            if (status.equals("SHRGJ")){
//
//                // LegacyTableView.insertLegacyTitle("क्रम सं.","पंजीकरण संख्या", "कौशल", "नाम", "लिंग","मोबाइल नंबर","कार्य स्थल का नाम");
//                LegacyTableView.insertLegacyTitle("क्रम सं.","कंपनी", "कंपनी का पता", "कार्य स्थल", "लोकेशन","संपर्क व्यक्ति","संपर्क व्यक्ति मोबाइल","पंजीकरण संख्या","लाभार्थी का नाम","लाभार्थी का मोबाइल","लिंग","कौशल","उप कौशल","कार्य स्थल का नाम");
//                //  LegacyTableView.insertLegacyTitle("क्रम सं.","पंजीकरण संख्या", "कौशल", "नाम", "लिंग","मोबाइल नंबर","कार्य स्थल का नाम");
//                //,"अभिभावक का नाम","अभिभावक का मोबाइल नंबर"
//
//
//                int i=1;
//
//                for (AcptdRjctdJobOfferEntity info: data)
//                {
//
//                    LegacyTableView.insertLegacyContent(info.getRow_num(),info.getComanyNameEn(),info.getLocation(),info.getWorkSiteNameHn(),info.getLocation(),info.getVchGuardian_name(),info.getVchGuardian_number(),info.getVchregnum(),info.getVchMobile(),info.getGender(),info.getSkillCategory(),info.getSkillName(),info.getWorkSiteNameHn());
//                    i++;
//                }
//
//            }
            if (status.equals("SHRG"))
            {

                LegacyTableView.insertLegacyTitle("क्रम सं.","पंजीकरण संख्या", "नाम","मोबाइल नंबर", "लिंग");

                //,"अभिभावक का नाम","अभिभावक क                      ा मोबाइल नंबर"

                int i=1;
                for (AcptdRjctdJobOfferEntity info: data)
                {
                    tv_total_count.setText("Total Count:-"+info.getCount());

                    LegacyTableView.insertLegacyContent(info.getRow_num(),info.getVchregnum(),info.getVchName(),info.getVchMobile(),info.getGender());
                    i++;
                }

            }
            else
            {
                //,"लाभार्थी का नाम","लाभार्थी का मोबाइल","लिंग","कौशल","उप कौशल","कार्य स्थल का नाम"
                // LegacyTableView.insertLegacyTitle("क्रम सं.","पंजीकरण संख्या", "कौशल", "नाम", "लिंग","मोबाइल नंबर");
                LegacyTableView.insertLegacyTitle("क्रम सं.","कंपनी", "कंपनी का पता", "कार्य स्थल", "लोकेशन","संपर्क व्यक्ति","संपर्क व्यक्ति मोबाइल","पंजीकरण संख्या","लाभार्थी का नाम","लाभार्थी का मोबाइल","लिंग","कौशल","उप कौशल","कार्य स्थल का नाम");
                //,"अभिभावक का नाम","अभिभावक का मोबाइल नंबर"


                int i=1;
                for (AcptdRjctdJobOfferEntity info: data)
                {
                    tv_total_count.setText("Total Count:-"+info.getCount());
//,info.getVchMobile(),info.getGender(),info.getSkillCategory(),info.getSkillName(),info.getWorkSiteNameHn()
                    // LegacyTableView.insertLegacyContent(info.getRow_num(),info.getVchregnum(),info.getSkillName(),info.getVchName(),info.getGender(),info.getVchMobile());
                    LegacyTableView.insertLegacyContent(info.getRow_num(),info.getComanyNameEn(),info.getAddressEn(),info.getWorkSiteNameHn(),info.getLocation(),info.getVchGuardian_name(),info.getVchGuardian_number(),info.getVchregnum(),info.getVchName(),info.getVchMobile(),info.getGender(),info.getSkillCategory(),info.getSkillName(),info.getWorkSiteNameHn());
                    i++;
                }

            }
            if(data != null && data.size()> 0)
            {
                handleButtonView(data.get(0).getRow_num(), data.get(data.size() - 1).getRow_num());
                // tv_Norecord_accpt.setVisibility(View.GONE);
                legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
                legacyTableView.resetVariables();
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.setTheme(GOLDALINE);
                legacyTableView.setTablePadding(20);
                legacyTableView.setHighlight(1);
                legacyTableView.setZoomEnabled(true);
                legacyTableView.setShowZoomControls(false);
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

                // tv_Norecord_accpt.setVisibility(View.VISIBLE);
            }

        }
    }
}
