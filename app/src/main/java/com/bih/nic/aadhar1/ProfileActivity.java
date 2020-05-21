package com.bih.nic.aadhar1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.bih.nic.aadhar1.Model.BenDetails;

public class ProfileActivity extends Activity {

    BenDetails benDetails;
    TextView tv_user_name,tv_mobile,tv_qualification,tv_age,tv_address,tv_experience,et_reg_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        tv_mobile=(TextView)findViewById(R.id.tv_mobile);
        tv_qualification=(TextView)findViewById(R.id.tv_qualification);
        tv_age=(TextView)findViewById(R.id.tv_age);
        tv_address=(TextView)findViewById(R.id.tv_address);
        tv_experience=(TextView)findViewById(R.id.tv_experience);
        et_reg_num =(TextView) findViewById(R.id.et_reg_num);

        benDetails = new BenDetails();
        extractFrom_Data();
    }

    public void extractFrom_Data(){

        benDetails=(BenDetails)getIntent().getSerializableExtra("data");
        tv_user_name.setText(benDetails.getVchName());
        tv_mobile.setText(benDetails.getVchMobile());
        et_reg_num.setText(benDetails.getVchRegNum());
        tv_qualification.setText(benDetails.getIntQualification());
        tv_age.setText(benDetails.getIntAge());
        tv_age.setText(benDetails.getIntAge());
        tv_address.setText(benDetails.getVchAddress());
        tv_experience.setText(benDetails.getIntExpMonths());

    }
}
