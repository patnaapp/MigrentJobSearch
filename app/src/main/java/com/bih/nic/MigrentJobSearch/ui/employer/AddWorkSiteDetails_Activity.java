package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.BlockWeb;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddWorkSiteDetails_Activity extends Activity {

    Spinner spin_fin_yr,spin_dept,spin_district,spin_block;
    EditText et_work_site_en,et_work_site_hn,et_work_loc_en,et_work_loc_hn,et_pincode,et_supervisor_name,et_supervisor_name_hn,et_supervisor_mob;
    ArrayList<District>DistrictList=new ArrayList<>();
    ArrayList<BlockWeb>BlockList=new ArrayList<>();
    DataBaseHelper dataBaseHelper;
    String Dist_id="",Dist_name="",block_id="",block_name="";
    String work_site_en="",work_site_hn="",work_loc_en="",work_loc_hn="",pincode="",supervisor_name="",supervisor_name_hn="",supervisor_mob="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_site_details_);

        //    getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        dataBaseHelper=new DataBaseHelper(this);

        initialise();
        loadDistrictSpinnerData();

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


    public void SaveData() {
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
//        if (TextUtils.isEmpty(StateCode)) {
//            Toast.makeText(getApplicationContext(), "कृपया राज्य का नाम का चयन करे |", Toast.LENGTH_LONG).show();
//            // sp_district.setError("कृपया जिला का नाम का चयन करे |");
//            focusView = sp_state;
//            cancelRegistration = true;
//        }
//
//        if (TextUtils.isEmpty(DistCode)) {
//            Toast.makeText(getApplicationContext(), "कृपया जिला का नाम का चयन करे |", Toast.LENGTH_LONG).show();
//            // sp_district.setError("कृपया जिला का नाम का चयन करे |");
//            focusView = sp_district;
//            cancelRegistration = true;
//        }
    }
}
