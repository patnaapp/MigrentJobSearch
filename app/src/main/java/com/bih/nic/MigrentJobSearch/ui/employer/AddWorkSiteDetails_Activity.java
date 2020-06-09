package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.Model.BenDetails;
import com.bih.nic.MigrentJobSearch.Model.BlockWeb;
import com.bih.nic.MigrentJobSearch.Model.DefaultResponse;
import com.bih.nic.MigrentJobSearch.Model.DepartmentMaster;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.SkillMaster;
import com.bih.nic.MigrentJobSearch.Model.SubSkillMaster;
import com.bih.nic.MigrentJobSearch.Model.WorkDetailsEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkRequirementsEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.LabourSearchAdaptor;
import com.bih.nic.MigrentJobSearch.adapter.WorkReqrmntEntryAdapter;
import com.bih.nic.MigrentJobSearch.listener.WorkReqrmntListener;
import com.bih.nic.MigrentJobSearch.ui.labour.ModifyDocumentActivity;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddWorkSiteDetails_Activity extends Activity implements WorkReqrmntListener {

    Spinner spin_fin_yr,spin_dept,spin_district,spin_block;
    EditText et_work_site_en,et_work_site_hn,et_work_loc_en,et_work_loc_hn,et_pincode,et_supervisor_name,et_supervisor_name_hn,et_supervisor_mob;
    ArrayList<District>DistrictList=new ArrayList<>();
    ArrayList<BlockWeb>BlockList=new ArrayList<>();
    DataBaseHelper dataBaseHelper;
    String Dist_id="",Dist_name="",block_id="",block_name="",Dept_id="",Dept_name="";
    String work_site_en="",work_site_hn="",work_loc_en="",work_loc_hn="",pincode="",supervisor_name="",supervisor_name_hn="",supervisor_mob="";
    ArrayList<DepartmentMaster> deptList;
    String fin_yr[] = {"-चयन करे-","2020-2021"};
    String fin_yr_id="",fin_yr_name="";
    ArrayAdapter ben_type_aangan_aaray;
    Button add_requirement;
    int REQUESTCODE = 1;

    RecyclerView rv_requirements;
    ImageView img_back;
    ArrayList<WorkRequirementsEntity> requirements;

    WorkReqrmntEntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_site_details_);

        //    getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        dataBaseHelper = new DataBaseHelper(AddWorkSiteDetails_Activity.this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try {

            dataBaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }

        initialise();
        loadDistrictSpinnerData();
        loadDeptSpinnerData();

        spin_fin_yr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("arg2",""+position);
                if (position > 0) {
                    fin_yr_name = fin_yr[position].toString();

                    if (fin_yr_name.equals("2020-2021"))
                    {
                        fin_yr_id = "2021";
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        spin_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Dept_id = deptList.get(position-1).getDeptId();
                    Dept_name = deptList.get(position-1).getDeptNameHn();

                } else {
                    Dept_id = "";
                    Dept_name = "";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Dept_id = "";
                Dept_name = "";
            }

        });
        spin_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Dist_id = DistrictList.get(position-1).get_DistCode();
                    Dist_name = DistrictList.get(position-1).get_DistName();
                    loadBlockSpinnerData(Dist_id);


                } else {
                    Dist_id = "";
                    Dist_name = "";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Dist_id = "";
                Dist_name = "";
            }

        });

        spin_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    block_id = BlockList.get(position-1).getBlockCode();
                    block_name = BlockList.get(position-1).getBlockName();


                } else {
                    block_id = "";
                    block_name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                block_id = "";
                block_name = "";
            }

        });

        add_requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddWorkSiteDetails_Activity.this,AddWorkRequirementActivity.class);
                startActivityForResult(i, REQUESTCODE);
            }
        });

    }

    public void initialise()
    {
        spin_fin_yr=findViewById(R.id.spin_fin_yr);
        spin_dept=findViewById(R.id.spin_dept);
        spin_district=findViewById(R.id.spin_district);
        spin_block=findViewById(R.id.spin_block);

        et_work_site_en=findViewById(R.id.et_work_site_en);
        et_work_site_en.addTextChangedListener(inputTextWatcher1);
        et_work_site_hn=findViewById(R.id.et_work_site_hn);
        et_work_site_hn.addTextChangedListener(inputTextWatcher2);
        et_work_loc_en=findViewById(R.id.et_work_loc_en);
        et_work_loc_en.addTextChangedListener(inputTextWatcher3);
        et_work_loc_hn=findViewById(R.id.et_work_loc_hn);
        et_work_loc_hn.addTextChangedListener(inputTextWatcher4);
        et_pincode=findViewById(R.id.et_pincode);
        et_supervisor_name=findViewById(R.id.et_supervisor_name);
        et_supervisor_name.addTextChangedListener(inputTextWatcher5);
        et_supervisor_name_hn=findViewById(R.id.et_supervisor_name_hn);
        et_supervisor_name_hn.addTextChangedListener(inputTextWatcher6);
        et_supervisor_mob=findViewById(R.id.et_supervisor_mob);
        add_requirement=findViewById(R.id.add_requirement);

        rv_requirements=findViewById(R.id.rv_requirements);

        img_back = (ImageView) findViewById(R.id.img);

        ben_type_aangan_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fin_yr);
        spin_fin_yr.setAdapter(ben_type_aangan_aaray);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        requirements = new ArrayList<>();
        setupRequiremntRecycler();
    }

    public void setupRequiremntRecycler(){
        adapter = new WorkReqrmntEntryAdapter(this, requirements, this);
        rv_requirements.setLayoutManager(new LinearLayoutManager(this));
        rv_requirements.setAdapter(adapter);
    }


    public void loadDistrictSpinnerData(){
        DistrictList = dataBaseHelper.getDistDetail();
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (District info: DistrictList){
            list.add(info.get_DistNameHN());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_district.setAdapter(adaptor);

//        if(benDetails.getDistrictName()!=null){
//            spin_district.setSelection(((ArrayAdapter<String>) spin_district.getAdapter()).getPosition(benDetails.getDistrictName()));
//
//        }

    }

    public void loadBlockSpinnerData(String district){
        BlockList.clear();
        BlockList = dataBaseHelper.getBlockDetail(district);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (BlockWeb info: BlockList){
            list.add(info.getBlockNameHn());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_block.setAdapter(adaptor);
//        if(benDetails.getBlockName()!=null){
//            spn_block_name.setSelection(((ArrayAdapter<String>) spn_block_name.getAdapter()).getPosition(benDetails.getBlockName()));
//
//        }

    }

    public static boolean isInputInEnglish(String txtVal)
    {

        String regx = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtVal);
        return matcher.find();
    }

    public void checkForEnglish(EditText etxt)
    {
        if (etxt.getText().length() > 0)
        {
            String s = etxt.getText().toString();
            if (isInputInEnglish(s))
            {
                //OK
            }
            else
            {
                Toast.makeText(this, "कृपया अंग्रेजी में लिखे", Toast.LENGTH_SHORT).show();
                etxt.setText("");
            }
        }
    }

    public void checkForHindi(EditText etxt)
    {
        if (etxt.getText().length() > 0)
        {
            String s = etxt.getText().toString();
            if (isInputInEnglish(s))
            {
                Toast.makeText(this, "कृपया हिंदी में लिखे", Toast.LENGTH_SHORT).show();
                etxt.setText("");
            }
            else
            {
                //OK
            }
        }
    }

    private TextWatcher inputTextWatcher1 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_work_site_en.getText().length() >0)
            {
                checkForEnglish(et_work_site_en);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher2 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_work_site_hn.getText().length() >0)
            {
                checkForHindi(et_work_site_hn);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher3 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_work_loc_en.getText().length() >0)
            {
                checkForEnglish(et_work_loc_en);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher4 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_work_loc_hn.getText().length() >0)
            {
                checkForHindi(et_work_loc_hn);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher5 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_supervisor_name.getText().length() >0)
            {
                checkForEnglish(et_supervisor_name);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher6 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_supervisor_name_hn.getText().length() >0)
            {
                checkForHindi(et_supervisor_name_hn);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };


    public void SaveData()
    {
        //Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
        work_site_en = et_work_site_en.getText().toString();
        work_site_hn = et_work_site_hn.getText().toString();
        work_loc_en = et_work_loc_en.getText().toString();
        work_loc_hn = et_work_loc_hn.getText().toString();
        pincode = et_pincode.getText().toString();
        supervisor_name = et_supervisor_name.getText().toString();
        supervisor_name_hn = et_supervisor_name_hn.getText().toString();
        supervisor_mob = et_supervisor_mob.getText().toString();
        boolean cancelRegistration = false;
        String isValied = "yes";
        View focusView = null;
        if (TextUtils.isEmpty(work_site_en)) {
            //Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का नाम दर्ज करे", Toast.LENGTH_LONG).show();
            et_work_site_en.setError("कृपया कार्य स्थल का नाम दर्ज करे");
            focusView = et_work_site_en;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(work_site_hn)) {
            //Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का नाम दर्ज करे|", Toast.LENGTH_LONG).show();
            et_work_site_hn.setError("कृपया कार्य स्थल का नाम दर्ज करे|");
            focusView = et_work_site_hn;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(work_loc_en)) {
            //Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का पता दर्ज करे |", Toast.LENGTH_LONG).show();
            et_work_loc_en.setError("कृपया कार्य स्थल का पता दर्ज करे |");
            focusView = et_work_loc_en;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(work_loc_hn)) {
           // Toast.makeText(getApplicationContext(), "कृपया कार्य स्थल का पता दर्ज करे |", Toast.LENGTH_LONG).show();
            et_work_loc_hn.setError("कृपया कार्य स्थल का पता दर्ज करे |");
            focusView = et_work_loc_hn;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(pincode)) {
            //Toast.makeText(getApplicationContext(), "कृपया PINCODE दर्ज करे |", Toast.LENGTH_LONG).show();
            et_pincode.setError("कृपया PINCODE दर्ज करे |");
            focusView = et_pincode;
            cancelRegistration = true;
        }
        else if (et_pincode.getText().toString().length() < 6)
        {
            et_pincode.setError("कृपया PINCODE सही डाले |");
            focusView = et_pincode;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(supervisor_name)) {
            //Toast.makeText(getApplicationContext(), "कृपया सुपरवाइजर का नाम दर्ज करे|", Toast.LENGTH_LONG).show();
            et_supervisor_name.setError("कृपया सुपरवाइजर का नाम दर्ज करे|");
            focusView = et_supervisor_name;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(supervisor_name_hn)) {
            //Toast.makeText(getApplicationContext(), "कृपया सुपरवाइजर का नाम दर्ज करे|", Toast.LENGTH_LONG).show();
            et_supervisor_name_hn.setError("कृपया सुपरवाइजर का नाम दर्ज करे|");
            focusView = et_supervisor_name_hn;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(supervisor_mob)) {
            //Toast.makeText(getApplicationContext(), "कृपया सुपरवाइजर का नंबर दर्ज करे|", Toast.LENGTH_LONG).show();
            et_supervisor_mob.setError("कृपया सुपरवाइजर का नंबर दर्ज करे|");
            focusView = et_supervisor_mob;
            cancelRegistration = true;
        }
        else if (et_supervisor_mob.getText().toString().length() <10)
        {
            et_supervisor_mob.setError("कृपया सुपरवाइजर का नंबर सही डाले |");
            focusView = et_supervisor_mob;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(Dist_name)) {
            Toast.makeText(getApplicationContext(), "कृपया जिला का चयन करे|", Toast.LENGTH_LONG).show();
            // sp_district.setError("कृपया जिला का नाम का चयन करे |");
            focusView = spin_district;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(block_name)) {
            Toast.makeText(getApplicationContext(), "कृपया प्रखंड का चयन करे|", Toast.LENGTH_LONG).show();
            // sp_district.setError("कृपया जिला का नाम का चयन करे |");
            focusView = spin_block;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(Dept_name)) {
            Toast.makeText(getApplicationContext(), "कृपया विभाग का चयन करे|", Toast.LENGTH_LONG).show();
            // sp_district.setError("कृपया जिला का नाम का चयन करे |");
            focusView = spin_dept;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(fin_yr_name)) {
            Toast.makeText(getApplicationContext(), "कृपया वित्तीय वर्ष का चयन करे|", Toast.LENGTH_LONG).show();
            // sp_district.setError("कृपया जिला का नाम का चयन करे |");
            focusView = spin_fin_yr;
            cancelRegistration = true;
        }
        if(requirements.size() <=0){
            Toast.makeText(getApplicationContext(), "कृपया कामगारों से सम्बंधित जानकारी की एंट्री करे|", Toast.LENGTH_LONG).show();
            cancelRegistration = true;
        }

        else {

            moveToNext();


        }
    }

    public void loadDeptSpinnerData(){
        deptList = dataBaseHelper.getDepartmentMasterList();
        if (deptList.size() > 0){
            setDeptSpinner();
        }else{
            if (Utiilties.isOnline(this) == false) {
                showAlertForInternet();
            } else {
                new SyncDeptMasterData().execute();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE) {
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    WorkRequirementsEntity reqrmnt = (WorkRequirementsEntity) data.getSerializableExtra("data");
                    requirements.add(reqrmnt);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onRemoveRequirement(final int index) {
        new AlertDialog.Builder(this)
                .setTitle("Remove Requirement")
                .setMessage("Are you sure? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requirements.remove(index);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private class SyncDeptMasterData extends AsyncTask<String, Void, ArrayList<DepartmentMaster>> {
        private final ProgressDialog dialog = new ProgressDialog(AddWorkSiteDetails_Activity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Syncing Department Data...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<DepartmentMaster> doInBackground(String...arg) {
            return WebserviceHelper.getDeptData();
        }

        @Override
        protected void onPostExecute(ArrayList<DepartmentMaster> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){

                DataBaseHelper helper=new DataBaseHelper(AddWorkSiteDetails_Activity.this);
                long i= helper.setDeptMasterData(result);
                if (i>0){
                    deptList = helper.getDepartmentMasterList();
                    setDeptSpinner();
                }



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



    public void setDeptSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (DepartmentMaster info: deptList){
            list.add(info.getDeptNameHn().trim());
            //if(benDetails.get)
        }

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_dept.setAdapter(adaptor);
//        if(benDetails.getSkill_Id()!=null){
//            String skill_hn = dataBaseHelper.getNameFor("SkilMaster", "Id", "SkillNameHn", benDetails.getSkill_Id());
//            spn_skill.setSelection(((ArrayAdapter<String>) spn_skill.getAdapter()).getPosition(skill_hn.trim()));
//
//        }

    }
    public void moveToNext()
    {
        WorkDetailsEntity info = new WorkDetailsEntity();
        try{
            info.setDist_id(Dist_id);
            info.setDist_name(Dist_name);
            info.setBlk_id(block_id);
            info.setBlk_name(block_name);
            info.setFin_yr(fin_yr_id);
            info.setRelated_dept(Dept_id);
            info.setWork_site_eng(work_site_en);
            info.setWork_site_hn(work_site_hn);
            info.setLocation_en(work_loc_en);
            info.setLocation_hn(work_loc_hn);
            info.setPincode(pincode);
            info.setSupervisor_nm_en(supervisor_name);
            info.setSupervisor_nm_hn(supervisor_name_hn);
            info.setSupervisor_mob(supervisor_mob);

            new UploadWorkDetailTask(info, requirements).execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }

//        Intent i = new Intent(AddWorkSiteDetails_Activity.this,AddWorkRequirementActivity.class);
//        i.putExtra("data", info);
//        startActivity(i);

    }

    public void SaveData(View view){
        SaveData();
    }

    private class UploadWorkDetailTask extends AsyncTask<String, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(AddWorkSiteDetails_Activity.this);

        WorkDetailsEntity workInfo;
        ArrayList<WorkRequirementsEntity> reqrmnts;

        public UploadWorkDetailTask(WorkDetailsEntity workInfo, ArrayList<WorkRequirementsEntity> reqrmnts) {
            this.workInfo = workInfo;
            this.reqrmnts = reqrmnts;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getApplicationContext().getResources().getString(R.string.uploading));
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            return WebserviceHelper.UploadWorkSiteDetail(AddWorkSiteDetails_Activity.this,workInfo,reqrmnts, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", ""));
        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {

                if(result.contains("1")){
                    new AlertDialog.Builder(AddWorkSiteDetails_Activity.this)
                            .setTitle("Success")
                            .setMessage("Work Site detailed has been succesfully added")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            })
                            .show();
                }else{
                    new AlertDialog.Builder(AddWorkSiteDetails_Activity.this)
                            .setTitle("Failed!!")
                            .setMessage(result)
                            .setCancelable(true)
                            .show();
                }

//                if(result.getStatus()){
//                    new AlertDialog.Builder(AddWorkSiteDetails_Activity.this)
//                            .setTitle("Success")
//                            .setMessage("Work Site detailed has been succesfully added")
//                            .setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    finish();
//                                }
//                            })
//                            .show();
//                }else{
//                    new AlertDialog.Builder(AddWorkSiteDetails_Activity.this)
//                            .setTitle("Failed!!")
//                            .setMessage(result.getMessage())
//                            .setCancelable(true)
//                            .show();
//                }

            }else{
                Toast.makeText(getApplicationContext(),"Failed!! Null Response. Try again later",Toast.LENGTH_LONG).show();
            }


        }

    }
}
