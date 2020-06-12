package com.bih.nic.MigrentJobSearch.OfficeReport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bih.nic.MigrentJobSearch.Model.ConsolidatedReportModel;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.BlockWiseConsolidatedReport;
import com.bih.nic.MigrentJobSearch.adapter.DistrictWiseConsolidatedReportAdaptor;

import java.util.ArrayList;

public class BlockWiseConsolidated extends Activity {
    RecyclerView listView;
    ArrayList<ConsolidatedReportModel>consolidatedReportList=new ArrayList<>();
    BlockWiseConsolidatedReport blockWiseConsolidatedReport;
    String DistrictName,DistrictCode;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //------
        setContentView(R.layout.activity_block_wise_consolidated);
        Utiilties.setStatusBarColor(this);
        getActionBar().hide();
        listView=(RecyclerView)findViewById(R.id.listviewacptrjct);
        DistrictCode=getIntent().getStringExtra("DistCode");
        DistrictName=getIntent().getStringExtra("DistName");

        new  SyncBlockWiseConsolidated(DistrictCode).execute();
    }

    private class SyncBlockWiseConsolidated extends AsyncTask<String, Void, ArrayList<ConsolidatedReportModel>> {
        private final ProgressDialog dialog = new ProgressDialog(BlockWiseConsolidated.this);
        int optionType;
        String Dist;

        public SyncBlockWiseConsolidated(String serial_no) {
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
            return WebserviceHelper.ConsolidatedReportBlockWise(Dist,"0","BLK");
        }

        @Override
        protected void onPostExecute(ArrayList<ConsolidatedReportModel> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            consolidatedReportList.clear();
            consolidatedReportList = result;
            ArrayList<String> Content = new ArrayList<>();

            if (consolidatedReportList != null && consolidatedReportList.size() > 0) {
                blockWiseConsolidatedReport = new BlockWiseConsolidatedReport(BlockWiseConsolidated.this, consolidatedReportList, DistrictCode);
                listView.setLayoutManager(new LinearLayoutManager(BlockWiseConsolidated.this));
                listView.setAdapter(blockWiseConsolidatedReport);
            }
        }
    }
}
