package com.bih.nic.aadhar1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChangeMobileNumberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile_number);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);
    }
}
