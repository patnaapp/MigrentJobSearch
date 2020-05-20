package com.bih.nic.aadhar1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bih.nic.aadhar1.Model.BenDetails;

public class ProfileActivity extends AppCompatActivity {

    BenDetails benDetails;
    TextView tv_user_name,tv_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        tv_mobile=(TextView)findViewById(R.id.tv_user_name);

        benDetails = new BenDetails();
        extractFrom_Data();
    }

    public void extractFrom_Data(){

        benDetails=(BenDetails)getIntent().getSerializableExtra("data");
        tv_user_name.setText(benDetails.getVchName());
        tv_mobile.setText(benDetails.getVchMobile());

    }
}
