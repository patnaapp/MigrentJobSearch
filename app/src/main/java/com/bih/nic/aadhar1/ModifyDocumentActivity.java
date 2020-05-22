package com.bih.nic.aadhar1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bih.nic.aadhar1.DataBaseHelper.DataBaseHelper;
import com.bih.nic.aadhar1.Model.BenDetails;
import com.bih.nic.aadhar1.Model.SkillMaster;
import com.bih.nic.aadhar1.Model.SubSkillMaster;

import java.util.ArrayList;

public class ModifyDocumentActivity extends Activity {
    Spinner spn_panch_name,spn_block_name,spn_dist_name,spn_sub_skill,spn_skill,spn_category;
    EditText edt_exp_year,edt_Qualification,edt_catogery,edt_ifsc_code,edt_ac_name,edt_ac_no,edt_mobileno;
    BenDetails benDetails;
    DataBaseHelper dataBaseHelper;

    ArrayList<SkillMaster> skillList;
    ArrayList<SubSkillMaster> subSkillList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_document);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        dataBaseHelper=new DataBaseHelper(this);
        spn_panch_name=(Spinner)findViewById(R.id.spn_panch_name);
        spn_block_name=(Spinner)findViewById(R.id.spn_block_name);
        spn_dist_name=(Spinner)findViewById(R.id.spn_dist_name);
        spn_sub_skill=(Spinner)findViewById(R.id.spn_sub_skill);
        spn_skill=(Spinner)findViewById(R.id.spn_skill);
        spn_category=(Spinner)findViewById(R.id.spn_category);

        edt_exp_year=(EditText)findViewById(R.id.edt_exp_year);

        edt_ifsc_code=(EditText)findViewById(R.id.edt_ifsc_code);
        edt_ac_name=(EditText)findViewById(R.id.edt_ac_name);
        edt_ac_no=(EditText)findViewById(R.id.edt_ac_no);
        edt_mobileno=(EditText)findViewById(R.id.edt_mobileno);
        //  dataBaseHelper.getBlockDetail()

        extractFrom_Data();

        loadSpinnerData();
    }

    public void extractFrom_Data(){

        benDetails=(BenDetails)getIntent().getSerializableExtra("data");
        edt_exp_year.setText(benDetails.getIntExpYears());

        //   spn_category
        edt_ifsc_code.setText(benDetails.getVchIfsc());
        edt_ac_name.setText(benDetails.getVchBankName());
        edt_ac_no.setText(benDetails.getVchBankAccount());
        edt_mobileno.setText(benDetails.getVchMobile());


    }

    public void setSkillSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        for (SkillMaster info: skillList){
            list.add(info.getSkillName());
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_skill.setAdapter(adaptor);
    }

    public void setSubSkillSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        for (SubSkillMaster info: subSkillList){
            list.add(info.getSkillName());
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sub_skill.setAdapter(adaptor);
    }

    public void loadSpinnerData(){
        skillList = dataBaseHelper.getSkillMasterList();
        if (skillList.size() > 0){
            setSkillSpinner();
        }else{
            if (Utiilties.isOnline(this) == false) {
                showAlertForInternet();
            } else {
                new SyncSkillMasterData().execute();
            }
        }

        subSkillList = dataBaseHelper.getSubSkillMasterList();
        if (subSkillList.size() > 0){
            setSubSkillSpinner();
        }else{
            if (Utiilties.isOnline(this) == false) {
                showAlertForInternet();
            } else {
                new SyncSubSkillMasterData().execute();
            }
        }
    }

    private class SyncSkillMasterData extends AsyncTask<String, Void, ArrayList<SkillMaster>> {
        private final ProgressDialog dialog = new ProgressDialog(ModifyDocumentActivity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Syncing Skills Data...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<SkillMaster> doInBackground(String...arg) {
            return WebserviceHelper.getSkillMasterData();
        }

        @Override
        protected void onPostExecute(ArrayList<SkillMaster> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){

                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
                long i= helper.setSkillMasterData(result);

                skillList = helper.getSkillMasterList();
                setSkillSpinner();

                if(i>0) {
                    Toast.makeText(getApplicationContext(), "Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to save Data! Try again",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No Data Found!!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class SyncSubSkillMasterData extends AsyncTask<String, Void, ArrayList<SubSkillMaster>> {
        private final ProgressDialog dialog = new ProgressDialog(ModifyDocumentActivity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Syncing Skills Data...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<SubSkillMaster> doInBackground(String...arg) {
            return WebserviceHelper.getSubSkillMasterData();
        }

        @Override
        protected void onPostExecute(ArrayList<SubSkillMaster> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){

                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
                long i= helper.setSubSkillMasterData(result);

                subSkillList = helper.getSubSkillMasterList();
                setSubSkillSpinner();

                if(i>0) {
                    Toast.makeText(getApplicationContext(), "Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to save Data! Try again",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No Data Found!!",Toast.LENGTH_SHORT).show();
            }

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
}
