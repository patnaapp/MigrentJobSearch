package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.WorkDetailsEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkerModel;
import com.bih.nic.MigrentJobSearch.Model.workListModel;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.LabourSearchAdaptor;
import com.bih.nic.MigrentJobSearch.adapter.WorkSiteEditAdapter;

import java.util.ArrayList;

public class WorkSite_Edit_activity extends Activity implements AdapterView.OnItemSelectedListener {
    RecyclerView listView;
    TextView tv_Norecord;
    Spinner spn_work_status;
    DataBaseHelper dataBaseHelper;
    String ORG_ID="";
    ImageView img_back;
    WorkSiteEditAdapter labourSearchAdaptor;

    ProgressDialog dialog;
    ArrayList<WorkDetailsEntity> data;
    ArrayList<District> DistrictList;
    ArrayList<WorkDetailsEntity> WorkJobList = new ArrayList<>();
    String WorkStatus="";
    String fin_yr[] = {"-select-","All","Not Started","Work In Progress","Suspended","Completed"};
    String work_status_id="",work_status_name="";
    ArrayAdapter ben_type_aangan_aaray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_site__edit_activity);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        ORG_ID= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        initialise();
    }


    public void initialise() {
        dataBaseHelper = new DataBaseHelper(this);

        spn_work_status = findViewById(R.id.spn_work_status);
        tv_Norecord = findViewById(R.id.tv_Norecord);
        img_back = (ImageView) findViewById(R.id.img);

        listView = findViewById(R.id.listviewshow);
        spn_work_status.setOnItemSelectedListener(this);
        ben_type_aangan_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fin_yr);
        spn_work_status.setAdapter(ben_type_aangan_aaray);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.spn_work_status:
                if (position > 0) {
                    work_status_name = fin_yr[position].toString();

                    if (work_status_name.equals("All"))
                    {
                        work_status_id = "0";
                    }
                    if (work_status_name.equals("Not Started"))
                    {
                        work_status_id = "NS";
                    }
                    if (work_status_name.equals("Work In Progress"))
                    {
                        work_status_id = "WP";
                    }
                    if (work_status_name.equals("Suspended"))
                    {
                        work_status_id = "SD";
                    }
                    if (work_status_name.equals("Completed"))
                    {
                        work_status_id = "CD";
                    }

                    new SyncWorkDetailsData().execute();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //new SyncWorkDetailsData().execute();
    }

    public void populateData() {
        if (data != null && data.size() > 0) {
            Log.e("data", "" + data.size());

            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            labourSearchAdaptor = new WorkSiteEditAdapter(this, data);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(labourSearchAdaptor);

        } else {
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }


    private class SyncWorkDetailsData extends AsyncTask<String, Void, ArrayList<WorkDetailsEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(WorkSite_Edit_activity.this);
        int optionType;

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<WorkDetailsEntity> doInBackground(String...arg) {
            return WebserviceHelper.GetWorkDetailDataForEdit(work_status_id,ORG_ID);
        }

        @Override
        protected void onPostExecute(ArrayList<WorkDetailsEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result!=null) {
                data=result;

                populateData();
            }

        }
    }

}
