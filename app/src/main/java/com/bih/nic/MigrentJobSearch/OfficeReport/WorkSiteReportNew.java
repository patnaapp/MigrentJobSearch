package com.bih.nic.MigrentJobSearch.OfficeReport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bih.nic.MigrentJobSearch.Model.ConsolidatedReportModel;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.WorkSiteAdaptor;
import com.bih.nic.MigrentJobSearch.adapter.WorkSiteAdaptorNew;

import java.util.ArrayList;

public class WorkSiteReportNew extends Activity {

    RecyclerView listView;
    ArrayList<ConsolidatedReportModel> consolidatedReportList=new ArrayList<>();
    WorkSiteAdaptorNew workSiteAdaptor;
    String DistCode="",BlockCode="",DistName="",BlockName="";
    String type_="",_status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_site_report_new);

        Utiilties.setStatusBarColor(this);
        getActionBar().hide();

        listView=(RecyclerView)findViewById(R.id.listviewacptrjct);

        DistCode=getIntent().getStringExtra("DistCode");
        BlockCode=getIntent().getStringExtra("BlockCode");
        BlockName=getIntent().getStringExtra("BlockName");
        type_=getIntent().getStringExtra("type");
        _status=getIntent().getStringExtra("Status");

             new WorkSiteReport2("").execute();

    }

    private class WorkSiteReport2 extends AsyncTask<String, Void, ArrayList<ConsolidatedReportModel>> {
        private final ProgressDialog dialog = new ProgressDialog(WorkSiteReportNew.this);
        int optionType;
        String Dist;

        public WorkSiteReport2(String serial_no) {
            this.Dist = serial_no;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<ConsolidatedReportModel> doInBackground(String... arg) {
            return WebserviceHelper.ConsolidatedReportBlockWise(DistCode,BlockCode,_status);
        }

        @Override
        protected void onPostExecute(ArrayList<ConsolidatedReportModel> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            consolidatedReportList.clear();

            consolidatedReportList = result;
            ArrayList<String> Content = new ArrayList<>();

            Log.d("dsfcggv",""+result.size());

            if (consolidatedReportList != null && consolidatedReportList.size() > 0) {
                workSiteAdaptor = new WorkSiteAdaptorNew(WorkSiteReportNew.this, consolidatedReportList, "",type_);
                listView.setLayoutManager(new LinearLayoutManager(WorkSiteReportNew.this));
                listView.setAdapter(workSiteAdaptor);
            }
        }
    }
}
