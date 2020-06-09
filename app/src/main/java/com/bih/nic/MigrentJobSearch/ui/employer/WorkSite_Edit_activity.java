package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;

public class WorkSite_Edit_activity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spn_work_status;
    DataBaseHelper dataBaseHelper;
    String ORG_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_site__edit_activity);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        ORG_ID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
    }


    public void initialise() {
        dataBaseHelper = new DataBaseHelper(this);

        spn_work_status = findViewById(R.id.spn_work_status);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
