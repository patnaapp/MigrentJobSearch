package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.Model.CategoryMaster;
import com.bih.nic.MigrentJobSearch.Model.SkillMaster;
import com.bih.nic.MigrentJobSearch.Model.SubSkillMaster;
import com.bih.nic.MigrentJobSearch.Model.WorkRequirementsEntity;
import com.bih.nic.MigrentJobSearch.Model.qualification;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.ui.labour.ModifyDocumentActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddWorkRequirementActivity extends Activity implements AdapterView.OnItemSelectedListener{

    Spinner spn_sub_skill,spn_skill,spn_gender,spin_active,spn_salary_type;
    ImageView img_back;
    EditText et_no_person,et_exp_mnm,et_exp_mxm,et_salary_mnm,et_salary_mxm;
    TextView tv_start_date,tv_skill,tv_t_subcat,tv_t_gender,tv_t_date,tv_t_status;
    Button btn_submit;

    DataBaseHelper dataBaseHelper;

    ArrayList<SkillMaster> skillList;
    ArrayList<SubSkillMaster> subSkillList;

    String gender[] = {"-चयन करे-","अन्य","पुरुष","महिला"};
    String status[] = {"-चयन करे-","हाँ","नहीं"};
    String sal_type[] = {"-चयन करे-","Per Day","Per Month","Per Year"};

    ArrayAdapter gender_aaray,status_array,sal_type_array;

    String Gender_Name="",Gender_Code="",gendarNam="", statusCode="", statusStr="", startDate,saltype_id="",saltype_nm="";
    String skillId="",skillName="",subSkillId="",SubSkillName="";

    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_requirement);
        Utiilties.setStatusBarColor(this);

        initiliazation();

        loadSkillSpinnerData();
    }

    public void initiliazation(){
        dataBaseHelper = new DataBaseHelper(this);

        spn_sub_skill=(Spinner)findViewById(R.id.spn_sub_skill);
        spn_skill=(Spinner)findViewById(R.id.spn_skill);
        spn_gender=(Spinner)findViewById(R.id.spn_gender);
        spin_active=(Spinner)findViewById(R.id.spin_active);
        spn_salary_type=(Spinner)findViewById(R.id.spn_salary_type);

        img_back = (ImageView) findViewById(R.id.img);
        tv_start_date = (TextView) findViewById(R.id.tv_start_date);

        tv_skill = (TextView) findViewById(R.id.tv_skill);
        tv_t_subcat = (TextView) findViewById(R.id.tv_t_subcat);
        tv_t_gender = (TextView) findViewById(R.id.tv_t_gender);
        tv_t_date = (TextView) findViewById(R.id.tv_t_date);
        tv_t_status = (TextView) findViewById(R.id.tv_t_status);

        et_no_person =  findViewById(R.id.et_no_person);
        et_exp_mnm =  findViewById(R.id.et_exp_mnm);
        et_exp_mxm =  findViewById(R.id.et_exp_mxm);
        et_salary_mnm =  findViewById(R.id.et_salary_mnm);
        et_salary_mxm =  findViewById(R.id.et_salary_mxm);

        btn_submit =  findViewById(R.id.btn_submit);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spn_sub_skill.setOnItemSelectedListener(this);
        spn_skill.setOnItemSelectedListener(this);
        spn_gender.setOnItemSelectedListener(this);
        spin_active.setOnItemSelectedListener(this);

        gender_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender);
        spn_gender.setAdapter(gender_aaray);

        status_array = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, status);
        spin_active.setAdapter(status_array);

        sal_type_array = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sal_type);
        spn_salary_type.setAdapter(sal_type_array);


    }

    public void onSubmitData(View view){
        if(validateData()){
            WorkRequirementsEntity requirement = new WorkRequirementsEntity();
            requirement.setSkill_categId(skillId);
            requirement.setSkill_categ(skillName);
            requirement.setSkill_sub_categId(subSkillId);
            requirement.setSkill_sub_categ(SubSkillName);
            requirement.setGenderId(Gender_Code);
            requirement.setGender(Gender_Name);
            requirement.setStart_date(startDate);
            requirement.setIsActiveId(statusCode);
            requirement.setIsActive(statusStr);
            requirement.setNo_of_persons(et_no_person.getText().toString().trim());
            requirement.setMin_exp(et_exp_mnm.getText().toString().trim());
            requirement.setMax_exp(et_exp_mxm.getText().toString().trim());
            requirement.setMin_salary(et_salary_mnm.getText().toString().trim());
            requirement.setMax_salary(et_salary_mxm.getText().toString().trim());

            Intent returnIntent = new Intent();
            returnIntent.putExtra("data",requirement);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "कृपया सही डेटा दर्ज करें", Toast.LENGTH_LONG).show();
        }
    }

    public Boolean validateData(){
        boolean isValid = true;
        View focusView = null;

        try{
            if (TextUtils.isEmpty(skillId)) {
                //Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का नाम दर्ज करे", Toast.LENGTH_LONG).show();
                tv_skill.setError("कृपया कौशल श्रेणी का चयन करे |");
                focusView = tv_skill;
                isValid = false;
            }

            if (TextUtils.isEmpty(subSkillId)) {
                //Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का नाम दर्ज करे|", Toast.LENGTH_LONG).show();
                tv_t_subcat.setError("कृपया उप कौशल श्रेणी का चयन करे |");
                focusView = tv_t_subcat;
                isValid = false;
            }

            if (et_no_person.getText().toString().isEmpty()){
                et_no_person.setError("कृपया व्यक्ति की संख्या डाले |");
                focusView = et_no_person;
                isValid = false;
            }else if (Integer.parseInt(et_no_person.getText().toString()) <= 0){
                et_no_person.setError("कृपया सही व्यक्ति की संख्या डाले |");
                focusView = et_no_person;
                isValid = false;
            }

            if (TextUtils.isEmpty(Gender_Code)) {
                //Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का नाम दर्ज करे|", Toast.LENGTH_LONG).show();
                tv_t_gender.setError("कृपया लिंग का चयन करे |");
                focusView = tv_t_gender;
                isValid = false;
            }

            if (TextUtils.isEmpty(startDate)) {
                //Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का नाम दर्ज करे|", Toast.LENGTH_LONG).show();
                tv_t_date.setError("कृपया आरंभ करने की तिथि चयन करे |");
                focusView = tv_t_date;
                isValid = false;
            }

            if (et_exp_mnm.getText().toString().isEmpty()){
                et_exp_mnm.setError("कृपया न्यूनतम अनुभव डाले |");
                focusView = et_exp_mnm;
                isValid = false;
            }

            if (et_exp_mxm.getText().toString().isEmpty()){
                et_exp_mxm.setError("कृपया अधिकतम अनुभव डाले |");
                focusView = et_exp_mxm;
                isValid = false;
            }

            if (et_salary_mnm.getText().toString().isEmpty()){
                et_salary_mnm.setError("कृपया न्यूनतम आय डाले |");
                focusView = et_salary_mnm;
                isValid = false;
            }

            if (et_salary_mxm.getText().toString().isEmpty()){
                et_salary_mxm.setError("कृपया अधिकतम वेतन डाले |");
                focusView = et_salary_mxm;
                isValid = false;
            }

            if (TextUtils.isEmpty(saltype_id)) {
                Toast.makeText(getApplicationContext(), "please select salary type", Toast.LENGTH_LONG).show();
                //spn_salary_type.setError("कृपया लिंग का चयन करे |");
                focusView = tv_t_gender;
                isValid = false;
            }

        }catch (Exception e){
            isValid = false;
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

        }

        return isValid;
    }

    public void onShowCalendar(View view){
        Calendar c = Calendar.getInstance();
        Date min = new Date(2018, 4, 25);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);

        //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();
    }

    public void setSkillSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("-चयन करें-");
        for (SkillMaster info: skillList){
            list.add(info.getSkillNameHn().trim());
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_skill.setAdapter(adaptor);
    }

    public void setSubSkillSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("-चयन करें-");

        for (SubSkillMaster info: subSkillList){

            list.add(info.getSkillNameHn().trim());
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_sub_skill.setAdapter(adaptor);
    }

    public void loadSkillSpinnerData(){
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
    }

    public void loadSubSkillSpinnerData(String skillid){
        subSkillList = dataBaseHelper.getSubSkillMasterList(skillid);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spn_skill:
                if (position > 0) {
                    skillId = skillList.get(position -1).getId();
                    skillName = skillList.get(position -1).getSkillNameHn();
                    loadSubSkillSpinnerData(skillId);
                    tv_skill.setError(null);
                }
                break;
            case R.id.spn_sub_skill:
                if (position > 0) {
                    subSkillId = subSkillList.get(position - 1).getId();
                    SubSkillName = subSkillList.get(position - 1).getSkillNameHn();
                    tv_t_subcat.setError(null);
                }
                break;
            case R.id.spn_gender:
                if (position > 0) {
                    Gender_Code = String.valueOf(position-1);
                    Gender_Name = gender[position];
                    tv_t_gender.setError(null);
                }
                break;
            case R.id.spin_active:
                if (position > 0) {
                    statusCode = ""+position;
                    statusStr = status[position];
                    tv_t_status.setError(null);
                }
                break;

            case R.id.spn_salary_type:
                if (position > 0) {
                    saltype_id = String.valueOf(position-1);
                    saltype_nm = gender[position];

                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SyncSkillMasterData extends AsyncTask<String, Void, ArrayList<SkillMaster>> {
        private final ProgressDialog dialog = new ProgressDialog(AddWorkRequirementActivity.this);
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
        private final ProgressDialog dialog = new ProgressDialog(AddWorkRequirementActivity.this);


        // int optionType;

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

                subSkillList = helper.getSubSkillMasterList(skillId);
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

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            String ds = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            ds = ds.replace("/", "-");
            String[] separated = ds.split(" ");
            Date min = new Date(2018, 4, 25);
            try {
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String newString = currentTimeString.replace("A.M.", "");

                String smDay = "" + mDay, smMonth = "" + (mMonth + 1);
                if (mDay < 10) {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10) {
                    smMonth = "0" + (mMonth + 1);
                }


                tv_start_date.setText(smDay + "-" + smMonth + "-" + mYear);
                tv_t_date.setError(null);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                startDate = mYear + "-" + smMonth + "-" + smDay;

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };


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
