package com.bih.nic.aadhar1;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        getActionBar().hide();
      //  Utiilties.setActionBarBackground(MainHomeActivity.this);
        Utiilties.setStatusBarColor(MainHomeActivity.this);
       // ActionBar actionBar = getActionBar();
       // actionBar.setTitle("Physical Verification");
    }
}
