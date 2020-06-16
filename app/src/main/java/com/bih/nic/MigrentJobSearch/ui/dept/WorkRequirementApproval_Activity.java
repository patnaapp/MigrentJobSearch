package com.bih.nic.MigrentJobSearch.ui.dept;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.Model.BlkCompanyJobDetailsEntity;
import com.bih.nic.MigrentJobSearch.Model.WrkReqApprovalDetailsEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;

import static com.levitnudi.legacytableview.LegacyTableView.BOLD;
import static com.levitnudi.legacytableview.LegacyTableView.DEFAULT;
import static com.levitnudi.legacytableview.LegacyTableView.OCEAN;

public class WorkRequirementApproval_Activity extends Activity implements AdapterView.OnItemSelectedListener  {


    LegacyTableView legacyTableView;
    ArrayList<WrkReqApprovalDetailsEntity> data;
    String status="",blkcode="", blkname="";
    String OrgId="",user_name="", mobile="", address="",work_site="", DistName="", ProfileImg="",CompanyName="", UserId="",UserRole="",distid="";

    String DistId="",DistNAme="";
    TextView tv_skill11,tv_Norecord_accpt,tv_total_count,tv_worksite;
    ImageView img_back,btn_previous,btn_next;
    String work_id="",a_Id="";
    CheckBox chk_approve,chk_reject;
    LinearLayout lin_remarks,ll_btn;
    Button btn_accpt,btn_rjct,btn_permannet_rjct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_approval_view);


        Utiilties.setStatusBarColor(this);
        getActionBar().hide();
        initialisation();
        ll_btn.setVisibility(View.GONE);
        lin_remarks.setVisibility(View.GONE);
        work_id = getIntent().getStringExtra("worksid");
        work_site = getIntent().getStringExtra("worksite");
        // status = getIntent().getStringExtra("StatusFlag");
        a_Id = getIntent().getStringExtra("a_ID");


        tv_skill11.setText("Work Requirment Details");
        tv_total_count.setText("Work Id-"+work_id);
        tv_worksite.setText("Work Site-"+work_site);
        tv_skill11.setTextColor(getApplicationContext().getResources().getColor(R.color.green));

        OrgId= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");

        //DistName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", "");
        CompanyName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ComanyName", "");
        mobile=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Mobile", "");
        address=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Address", "");

        new GetRequirementsForApproval().execute();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chk_approve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_reject.setChecked(false);
                    ll_btn.setVisibility(View.VISIBLE);
                    btn_accpt.setVisibility(View.VISIBLE);
                    lin_remarks.setVisibility(View.GONE);
                    btn_rjct.setVisibility(View.GONE);
                    btn_permannet_rjct.setVisibility(View.GONE);
                }
            }
        });

        chk_reject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chk_approve.setChecked(false);
                    lin_remarks.setVisibility(View.VISIBLE);
                    btn_accpt.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.VISIBLE);
                    btn_rjct.setVisibility(View.VISIBLE);
                    btn_permannet_rjct.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                //finish activity once user presses back button
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private class GetRequirementsForApproval extends AsyncTask<String, Void, ArrayList<WrkReqApprovalDetailsEntity>>
    {
        private final ProgressDialog dialog = new ProgressDialog(WorkRequirementApproval_Activity.this);
        int optionType;

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<WrkReqApprovalDetailsEntity> doInBackground(String...arg)
        {
            if(OrgId.equals("NA")){
                OrgId="";
            }
            return WebserviceHelper.WorkRequirementForApproval(work_id);
        }

        @Override
        protected void onPostExecute(ArrayList<WrkReqApprovalDetailsEntity> result)
        {
            data = result;
            if(data != null && data.size()> 0)
            {
                //tv_Norecord_accpt.setVisibility(View.GONE);
                LegacyTableView.insertLegacyTitle("S.No.","Skill","SubSkill", "Person","Gender", "Experiance-Min","Experiance-Max", "Salary-Min","Salary-Max","Active");

                int i=1;
                for (WrkReqApprovalDetailsEntity info: data)
                {
                    LegacyTableView.insertLegacyContent(String.valueOf(i),info.getSkillNameHn(),info.getSkillSub(),info.getNoOfPerson(),info.getGender(),info.getExperiance(),info.getExperianceMax(),info.getSalary(),info.getSalaryMax(),"Y");
                    i++;
                }

                legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.setTheme(DEFAULT);
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
               // tv_Norecord_accpt.setVisibility(View.VISIBLE);
            }

        }
    }


    public void initialisation(){
        tv_skill11=findViewById(R.id.tv_skill);
        // tv_Norecord_accpt=findViewById(R.id.tv_Norecord_accpt);
        img_back=(ImageView) findViewById(R.id.img);
        tv_total_count=(TextView) findViewById(R.id.tv_total_count);
        tv_worksite=(TextView) findViewById(R.id.tv_worksite);
        chk_approve=(CheckBox) findViewById(R.id.chk_approve);
        chk_reject=(CheckBox) findViewById(R.id.chk_reject);
        lin_remarks=(LinearLayout) findViewById(R.id.lin_remarks);
        ll_btn=(LinearLayout) findViewById(R.id.ll_btn);
        btn_accpt=(Button) findViewById(R.id.btn_accpt);
        btn_rjct=(Button) findViewById(R.id.btn_rjct);
        btn_permannet_rjct=(Button) findViewById(R.id.btn_permannet_rjct);
    }
}
