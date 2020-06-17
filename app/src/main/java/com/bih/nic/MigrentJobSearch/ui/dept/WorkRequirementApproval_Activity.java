package com.bih.nic.MigrentJobSearch.ui.dept;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.Login;
import com.bih.nic.MigrentJobSearch.Model.BlkCompanyJobDetailsEntity;
import com.bih.nic.MigrentJobSearch.Model.DefaultResponse;
import com.bih.nic.MigrentJobSearch.Model.WrkReqApprovalDetailsEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.ui.labour.RequestOtpActivity;
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
    TextView tv_skill11,tv_Norecord_accpt,tv_total_count,tv_worksite,tv_orgname;
    ImageView img_back,btn_previous,btn_next;
    String work_id="",a_Id="";
    CheckBox chk_approve,chk_reject;
    LinearLayout lin_remarks,ll_btn,lin_p_remarks;
    Button btn_accpt,btn_rjct,btn_permannet_rjct;
    String ben_type_aangan[] = {"-select-","Unauthorised Work Site","Other"};
    String Remarksr_Name="",Remarks_Code="";
    ArrayAdapter ben_type_aangan_aaray;
    Spinner spn_remarks;
    EditText edt_p_remarks;
    String VerifyType="",org_name="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_requirement_approval_view);


        Utiilties.setStatusBarColor(this);
        getActionBar().hide();

        initialisation();

        ll_btn.setVisibility(View.GONE);
        lin_p_remarks.setVisibility(View.GONE);
        lin_remarks.setVisibility(View.GONE);
        work_id = getIntent().getStringExtra("worksid");
        work_site = getIntent().getStringExtra("worksite");
        org_name = getIntent().getStringExtra("orhname");
        // status = getIntent().getStringExtra("StatusFlag");
        a_Id = getIntent().getStringExtra("a_ID");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");

        tv_skill11.setText("Work Requirment Details");

        tv_total_count.setText(Html.fromHtml("<font color='#2C3673'>Work Id:-</font>")+work_id);
        tv_worksite.setText(Html.fromHtml("<font color='#2C3673'>Work Site:-</font>")+work_site);
        tv_orgname.setText(Html.fromHtml("<font color='#2C3673'>Organization:-</font>")+org_name);
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
                    lin_p_remarks.setVisibility(View.GONE);
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

        spn_remarks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("arg2",""+position);
                if (position > 0) {
                    Remarksr_Name = ben_type_aangan[position].toString();

                    if (Remarksr_Name.equals("Unauthorised Work Site")) {

                        Remarks_Code = "1";
                        lin_p_remarks.setVisibility(View.GONE);
                    } else if (Remarksr_Name.equals("Other")) {

                        Remarks_Code = "5";
                        lin_p_remarks.setVisibility(View.VISIBLE);
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

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
                    LegacyTableView.insertLegacyContent(String.valueOf(i),info.getSkillCategoryHn(),info.getSkillNameHn(),info.getNoOfPerson(),info.getGender(),info.getExperiance(),info.getExperianceMax(),info.getSalary(),info.getSalaryMax(),info.getActive());
                    i++;
                }

                legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.setTheme(DEFAULT);
                legacyTableView.setTablePadding(15);
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
        lin_p_remarks=(LinearLayout) findViewById(R.id.lin_p_remarks);
        btn_accpt=(Button) findViewById(R.id.btn_accpt);
        btn_rjct=(Button) findViewById(R.id.btn_rjct);
        btn_permannet_rjct=(Button) findViewById(R.id.btn_permannet_rjct);
        spn_remarks=(Spinner) findViewById(R.id.spn_remarks);
        edt_p_remarks=(EditText) findViewById(R.id.edt_p_remarks);
        tv_orgname=(TextView) findViewById(R.id.tv_orgname);

        ben_type_aangan_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ben_type_aangan);
        spn_remarks.setAdapter(ben_type_aangan_aaray);
    }

    public void onApprove(View view){
        VerifyType="ACCP";
        if(Utiilties.isOnline(getApplicationContext())) {
            new UpdateWorksiteApproval().execute();
        }else {
            Utiilties.internetNotAvailableDialog(WorkRequirementApproval_Activity.this);
        }
    }

    public void onReject(View view)
    {
        VerifyType="RJCT";
        if (Remarks_Code.equals("")){
            Toast.makeText(WorkRequirementApproval_Activity.this,"Please select remarks",Toast.LENGTH_SHORT).show();
        }
        else {
            if (Remarks_Code.equals("1")){
                if(Utiilties.isOnline(getApplicationContext())) {
                    new UpdateWorksiteApproval().execute();
                }else {
                    Utiilties.internetNotAvailableDialog(WorkRequirementApproval_Activity.this);
                }
            }
            else if (Remarks_Code.equals("5"))  {
                String other_remarks=edt_p_remarks.getText().toString();
                if (other_remarks.equals("")){
                    Toast.makeText(WorkRequirementApproval_Activity.this,"Please enter remarks",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(Utiilties.isOnline(getApplicationContext())) {
                        new UpdateWorksiteApproval().execute();
                    }else {
                        Utiilties.internetNotAvailableDialog(WorkRequirementApproval_Activity.this);
                    }
                }

            }
        }
    }

    public void onP_Reject(View view){
        VerifyType="PRJCT";
        if (Remarks_Code.equals("")){
            Toast.makeText(WorkRequirementApproval_Activity.this,"Please select remarks",Toast.LENGTH_SHORT).show();
        }
        else {
            if (Remarks_Code.equals("1")){
                if(Utiilties.isOnline(getApplicationContext())) {
                    new UpdateWorksiteApproval().execute();
                }else {
                    Utiilties.internetNotAvailableDialog(WorkRequirementApproval_Activity.this);
                }
            }
            else if (Remarks_Code.equals("5"))  {
                String other_remarks=edt_p_remarks.getText().toString();
                if (other_remarks.equals("")){
                    Toast.makeText(WorkRequirementApproval_Activity.this,"Please enter remarks",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(Utiilties.isOnline(getApplicationContext())) {
                        new UpdateWorksiteApproval().execute();
                    }else {
                        Utiilties.internetNotAvailableDialog(WorkRequirementApproval_Activity.this);
                    }
                }

            }
        }
    }



    private class UpdateWorksiteApproval extends AsyncTask<String, Void, DefaultResponse> {
        DefaultResponse data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(WorkRequirementApproval_Activity.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(WorkRequirementApproval_Activity.this).create();


        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            if (!WorkRequirementApproval_Activity.this.isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected DefaultResponse doInBackground(String... param) {

            return WebserviceHelper.WorksiteApprove(work_id,UserId,VerifyType,Remarks_Code,edt_p_remarks.getText().toString());

        }

        @Override
        protected void onPostExecute(DefaultResponse result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {



                if (result.getStatus()==true) {


                    AlertDialog.Builder ab = new AlertDialog.Builder(WorkRequirementApproval_Activity.this);
                    ab.setCancelable(false);
                    ab.setTitle("Successful");
                    //ab.setIcon(R.drawable.labour1);
                    ab.setMessage(result.getMessage());
                    ab.setPositiveButton("[OK]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(getBaseContext(), ApproveRejectWorkSite_activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //setFinishOnTouchOutside(false);
                            finish();
                            dialog.dismiss();
                        }
                    });

                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();


                }
                else  if (result.getStatus()==false){
                    // Toast.makeText(getApplicationContext(), "Uploading data failed ", Toast.LENGTH_SHORT).show();


                    AlertDialog.Builder ab = new AlertDialog.Builder(WorkRequirementApproval_Activity.this);
                    ab.setCancelable(false);
                    ab.setTitle("Failed");
                    //  ab.setIcon(R.drawable.labour1);
                    ab.setMessage(result.getMessage());
                    ab.setPositiveButton("[OK]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(getBaseContext(),ApproveRejectWorkSite_activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //setFinishOnTouchOutside(false);
                            finish();
                            dialog.dismiss();
                        }
                    });

                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();



                }

            } else {
                //chk_msg_OK_networkdata("Uploading failed.Please Try Again Later");
                Toast.makeText(getApplicationContext(), "Result Null..Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
