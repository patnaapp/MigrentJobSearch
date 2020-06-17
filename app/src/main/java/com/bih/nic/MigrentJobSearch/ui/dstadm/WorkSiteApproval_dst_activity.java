package com.bih.nic.MigrentJobSearch.ui.dstadm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.ApproveWorkSiteEntity;
import com.bih.nic.MigrentJobSearch.Model.BlockWeb;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.WorkApprovalAdapter;

import java.util.ArrayList;

public class WorkSiteApproval_dst_activity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spn_district,spn_block,spn_status;
    RecyclerView listView;
    TextView tv_Norecord;
    DataBaseHelper dataBaseHelper;
    ArrayList<District>DistrictList=new ArrayList<>();
    String Dist_id="",Dist_name="",block_id="",block_name="";
    ArrayList<BlockWeb>BlockList=new ArrayList<>();
    String ben_type_aangan[] = {"-select-","Pending","Accept","Reject","Permanent Reject"};
    String Status_Name="",Status_Code="";
    ArrayAdapter ben_type_aangan_aaray;
    ImageView img_back;
    ProgressDialog dialog;
    ArrayList<ApproveWorkSiteEntity> data;
    WorkApprovalAdapter adaptor_showedit_listDetail;
    String OrgId="",UserId="",UserRole="",DstCode="";
    LinearLayout lin_dist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_work_site_activity);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        dataBaseHelper=new DataBaseHelper(this);

        OrgId= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");
        DstCode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DstCode", "");
        initialization();

        // loadDistrictSpinnerData();
        loadBlockSpinnerData(DstCode);

//        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    Dist_id = DistrictList.get(position-1).get_DistCode();
//                    Dist_name = DistrictList.get(position-1).get_DistName();
//
//
//
//                } else {
//                    block_name = "";
//                    block_name = "";
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                block_name = "";
//                block_name = "";
//            }
//
//        });
        spn_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spn_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("arg2",""+position);
                if (position > 0) {
                    Status_Name = ben_type_aangan[position].toString();

                    if (Status_Name.equals("Pending")) {

                        Status_Code = "1";
                    } else if (Status_Name.equals("Accept")) {

                        Status_Code = "2";
                    }

                    else if (Status_Name.equals("Reject")) {

                        Status_Code = "3";
                    }
                    else if (Status_Name.equals("Permanent Reject")) {

                        Status_Code = "4";
                    }

                    new GetWorkSiteForApproval().execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void initialization()
    {
        spn_district=findViewById(R.id.spn_district);
        lin_dist=findViewById(R.id.lin_dist);
        spn_block=findViewById(R.id.spn_block);
        spn_status=findViewById(R.id.spn_status);
        listView=findViewById(R.id.listviewshow);
        tv_Norecord=findViewById(R.id.tv_Norecord);
        img_back=(ImageView) findViewById(R.id.img);

        lin_dist.setVisibility(View.GONE);

        ben_type_aangan_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ben_type_aangan);
        spn_status.setAdapter(ben_type_aangan_aaray);

    }

    public void loadDistrictSpinnerData()
    {
        DistrictList = dataBaseHelper.getDistDetail();
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (District info: DistrictList)
        {
            list.add(info.get_DistName());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_district.setAdapter(adaptor);

//        if(benDetails.getDistrictName()!=null){
//            spn_dist_name.setSelection(((ArrayAdapter<String>) spn_dist_name.getAdapter()).getPosition(benDetails.getDistrictName()));
//
//        }
//        spn_district.setEnabled(false);
    }

    public void loadBlockSpinnerData(String district)
    {
        BlockList.clear();
        BlockList = dataBaseHelper.getBlockDetail(district);
        ArrayList<String> list = new ArrayList<String>();
        list.add("-Select-");
        int index = 0;
        for (BlockWeb info: BlockList)
        {
            list.add(info.getBlockName());
            //if(benDetails.get)
        }
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_block.setAdapter(adaptor);
//        if(benDetails.getBlockName()!=null){
//            spn_block_name.setSelection(((ArrayAdapter<String>) spn_block_name.getAdapter()).getPosition(benDetails.getBlockName()));
//
//        }
//
//        spn_block_name.setEnabled(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }


    private class GetWorkSiteForApproval extends AsyncTask<String, Void, ArrayList<ApproveWorkSiteEntity>>
    {
        private final ProgressDialog dialog = new ProgressDialog(WorkSiteApproval_dst_activity.this);
        int optionType;

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<ApproveWorkSiteEntity> doInBackground(String...arg)
        {
            return WebserviceHelper.GetWorkSiteForApprovalByDst(block_id,Status_Code);
        }

        @Override
        protected void onPostExecute(ArrayList<ApproveWorkSiteEntity> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            data = result;

            populateData();

        }
    }

    public void populateData()
    {
        if(data != null && data.size()> 0)
        {
            Log.e("data", ""+data.size());
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adaptor_showedit_listDetail = new WorkApprovalAdapter(this, data, block_id);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adaptor_showedit_listDetail);

        }
        else
        {
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
