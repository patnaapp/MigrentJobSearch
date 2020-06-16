package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;
import com.bih.nic.MigrentJobSearch.Model.WorkSiteEmployeeReportEntity;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.levitnudi.legacytableview.LegacyTableView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WorksiteEmployeeLegacyActivity extends Activity {

    LegacyTableView legacyTableView;
    ArrayList<WorkSiteEmployeeReportEntity> data;
    TextView tv_header,tv_sub_header;
    String status="",distCode="", blockCode="", workId="", workRegId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worksite_employee_legacy);

        Utiilties.setStatusBarColor(this);

    }


}
