package com.bih.nic.MigrentJobSearch;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.Model.PaymentStatusEntity;

public class PaymentStatusActivity extends Activity {

    TextView tv_district,tv_block,tv_panchayat,tv_qrt_14,tv_name,tv_account,tv_ifsc,tv_bank_name,tv_paymentstatus,tv_eupi_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        tv_district = findViewById(R.id.tv_district);
        tv_block = findViewById(R.id.tv_block);
        tv_panchayat = findViewById(R.id.tv_panchayat);
        tv_qrt_14 = findViewById(R.id.tv_qrt_14);
        tv_name = findViewById(R.id.tv_name);
        tv_account = findViewById(R.id.tv_account);
        tv_ifsc = findViewById(R.id.tv_ifsc);
        tv_bank_name = findViewById(R.id.tv_bank_name);
        tv_paymentstatus = findViewById(R.id.tv_paymentstatus);
        tv_eupi_status = findViewById(R.id.tv_eupi_status);

        PaymentStatusEntity info = (PaymentStatusEntity)getIntent().getSerializableExtra("data");
        setData(info);
    }

    public void setData(PaymentStatusEntity info){
        tv_district.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", ""));
        tv_block.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("BlockName", ""));
        tv_panchayat.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PanchayatName", ""));
        tv_qrt_14.setText(info.getIsQRT14Days());
        tv_name.setText(info.getBeneficiaryName());
        tv_account.setText(info.getAccountNumber());
        tv_ifsc.setText(info.getIFSC());
        tv_bank_name.setText(info.getBankName());
        tv_paymentstatus.setText(info.getPaymentStatus());
        tv_eupi_status.setText(info.getEupi_UTR_Status());
    }
}
