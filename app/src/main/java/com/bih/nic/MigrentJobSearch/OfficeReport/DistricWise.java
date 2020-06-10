package com.bih.nic.MigrentJobSearch.OfficeReport;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;
import com.bih.nic.MigrentJobSearch.Model.ConsolidatedReportModel;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.adapter.AccptedRctdJobAdapter;
import com.bih.nic.MigrentJobSearch.adapter.DistrictWiseConsolidatedReportAdaptor;

import java.util.ArrayList;

public class DistricWise extends AppCompatActivity {

    DistrictWiseConsolidatedReportAdaptor districtWiseConsolidatedReportAdaptor;
    RecyclerView listView;
    ArrayList<ConsolidatedReportModel>consolidatedReportList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distric_wise);
        listView=(RecyclerView)findViewById(R.id.listviewacptrjct);

       new  SyncAcceptedRjctdJobsOffers("").execute();
    }


    private class SyncAcceptedRjctdJobsOffers extends AsyncTask<String, Void, ArrayList<ConsolidatedReportModel>> {
        private final ProgressDialog dialog = new ProgressDialog(DistricWise.this);
        int optionType;
        String Dist;

        public SyncAcceptedRjctdJobsOffers(String serial_no) {
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
            return WebserviceHelper.ConsolidatedReportDistrictWise("dis","");
        }

        @Override
        protected void onPostExecute(ArrayList<ConsolidatedReportModel> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            consolidatedReportList = result;
            ArrayList<String> Content = new ArrayList<>();

            if (consolidatedReportList != null && consolidatedReportList.size() > 0) {
                districtWiseConsolidatedReportAdaptor = new DistrictWiseConsolidatedReportAdaptor(DistricWise.this, consolidatedReportList, "");
                listView.setLayoutManager(new LinearLayoutManager(DistricWise.this));
                listView.setAdapter(districtWiseConsolidatedReportAdaptor);
            }
        }
    }

}
