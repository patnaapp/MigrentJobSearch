package com.bih.nic.aadhar1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.bih.nic.aadhar1.DataBaseHelper.DataBaseHelper;
import com.bih.nic.aadhar1.Model.BenDetails;

public class ModifyDocumentActivity extends Activity {
    Spinner spn_panch_name,spn_block_name,spn_dist_name,spn_sub_skill,spn_skill,spn_category;
    EditText edt_exp_year,edt_Qualification,edt_catogery,edt_ifsc_code,edt_ac_name,edt_ac_no,edt_mobileno;
    BenDetails benDetails;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_document);
        // dataBaseHelper=new DataBaseHelper();
        spn_panch_name=(Spinner)findViewById(R.id.spn_panch_name);
        spn_block_name=(Spinner)findViewById(R.id.spn_block_name);
        spn_dist_name=(Spinner)findViewById(R.id.spn_dist_name);
        spn_sub_skill=(Spinner)findViewById(R.id.spn_sub_skill);
        spn_skill=(Spinner)findViewById(R.id.spn_skill);
        spn_category=(Spinner)findViewById(R.id.spn_category);

        edt_exp_year=(EditText)findViewById(R.id.edt_exp_year);


        edt_ifsc_code=(EditText)findViewById(R.id.edt_ifsc_code);
        edt_ac_name=(EditText)findViewById(R.id.edt_ac_name);
        edt_ac_no=(EditText)findViewById(R.id.edt_ac_no);
        edt_mobileno=(EditText)findViewById(R.id.edt_mobileno);
        //  dataBaseHelper.getBlockDetail()

        extractFrom_Data();
    }

    public void extractFrom_Data(){

        benDetails=(BenDetails)getIntent().getSerializableExtra("data");
        edt_exp_year.setText(benDetails.getIntExpYears());

        //   spn_category
        edt_ifsc_code.setText(benDetails.getVchIfsc());
        edt_ac_name.setText(benDetails.getVchBankName());
        edt_ac_no.setText(benDetails.getVchBankAccount());
        edt_mobileno.setText(benDetails.getVchMobile());


    }
}
