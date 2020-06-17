package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.AppConstant;
import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkSiteEmployeeReportEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.ui.employer.LegacyTableViewActivity;
import com.levitnudi.legacytableview.LegacyTableView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.levitnudi.legacytableview.LegacyTableView.BOLD;
import static com.levitnudi.legacytableview.LegacyTableView.GOLDALINE;

public class WorksiteEmployeeLegacyActivity extends Activity {

    LegacyTableView legacyTableView;
    ArrayList<WorkSiteEmployeeReportEntity> data;
    TextView tv_header,tv_sub_header,tv_no_record_found;
    ImageView img_back;

    String status="",distCode="", blockCode="", workId="", workRegId="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worksite_employee_legacy);

        Utiilties.setStatusBarColor(this);

        initialise();
        extractDataFromIntent();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initialise(){
        tv_header = findViewById(R.id.tv_header);
        tv_sub_header = findViewById(R.id.tv_sub_header);
        img_back= findViewById(R.id.img);

        legacyTableView = findViewById(R.id.legacy_table_view);
        tv_no_record_found = findViewById(R.id.tv_no_record_found);
    }

    public void extractDataFromIntent(){
        status = getIntent().getStringExtra("status");
        distCode = getIntent().getStringExtra("DistCode");
        blockCode = getIntent().getStringExtra("blockCode");
        workId = getIntent().getStringExtra("WorkId");
        workRegId = getIntent().getStringExtra("WorksRegId");

        switch (status){
            case AppConstant.WORKSITE_DETAIL:
                tv_header.setText("Work Site Detail");
                break;
            case AppConstant.WORKSITE_NO_PERSON:
                tv_header.setText("Work Site Detail");
                break;
            case AppConstant.WORKSITE_JOB_OFFERED:
                tv_header.setText("Worker Detail of Offered Job");
                break;
            case AppConstant.WORKSITE_JOB_ACCEPTED:
                tv_header.setText("Worker Detail of Accepted Job");
                break;
            case AppConstant.WORKSITE_JOB_REJECTED:
                tv_header.setText("Work Site Detail");
                break;
            case AppConstant.WORKSITE_JOB_APPOINTED:
                tv_header.setText("Work Site Detail");
                break;

        }

        new SyncWorksiteEmployeeReport().execute();
    }

    private class SyncWorksiteEmployeeReport extends AsyncTask<String, Void, ArrayList<WorkSiteEmployeeReportEntity>>{
        private final ProgressDialog dialog = new ProgressDialog(WorksiteEmployeeLegacyActivity.this);

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<WorkSiteEmployeeReportEntity> doInBackground(String...arg)
        {
            return WebserviceHelper.getWorksiteEmployeeReport(status, distCode, blockCode,"",workId,workRegId );
        }

        @Override
        protected void onPostExecute(ArrayList<WorkSiteEmployeeReportEntity> result)
        {
            data = result;
            ArrayList<String>  Content=new ArrayList<>();

            if(data != null && data.size()> 0){

                updateLegacyTableContent(data);

                //handleButtonView(data.get(0).getRow_num(), data.get(data.size() - 1).getRow_num());
                tv_no_record_found.setVisibility(View.GONE);
                legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
                legacyTableView.resetVariables();
                legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                legacyTableView.setContent(LegacyTableView.readLegacyContent());
                legacyTableView.setTheme(GOLDALINE);
                legacyTableView.setTablePadding(20);
                legacyTableView.setHighlight(1);
                legacyTableView.setZoomEnabled(true);
                legacyTableView.setShowZoomControls(false);
                legacyTableView.setBottomShadowVisible(true);
                legacyTableView.setTitleFont(BOLD);
                legacyTableView.setContentTextSize(30);
                legacyTableView.setTitleTextSize(35);
                legacyTableView.build();
                if (this.dialog.isShowing())
                {
                    this.dialog.dismiss();
                }
            }
            else
            {
                legacyTableView.setVisibility(View.GONE);
                tv_no_record_found.setVisibility(View.VISIBLE);
            }

        }
    }

    public void updateLegacyTableContent(ArrayList<WorkSiteEmployeeReportEntity> data){

        switch (status){
            case AppConstant.WORKSITE_DETAIL:
                tv_sub_header.setText("Total Count:-"+data.size());

                LegacyTableView.insertLegacyTitle("Sl No.","Company Name", "Comany Address","WorkSite Name", "Location", "Contact Person Name", "Contact person number", "No. of Person", "Total Job Offer");
                int i=1;

                for (WorkSiteEmployeeReportEntity info: data)
                {
                    LegacyTableView.insertLegacyContent(String.valueOf(i),info.getComanyNameEn(),info.getAddressEn(),info.getWorkSiteName(),info.getLocation(),info.getContactPerson(),info.getCPMobileNo(), info.getNoOfPerson(), info.getTotalJobOffer());
                    i++;
                }
                break;
        }
    }
}
