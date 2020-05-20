package com.bih.nic.aadhar1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class ChangeMobileNumberActivity extends Activity {

    EditText reg_no,et_aadhar_No,et_ben_Name;
    Spinner spin_gender;
    String ben_type_aangan[] = {"-चयन करे-","पुरुष","महिला","अन्य"};
    String Gender_Name="",Gender_Code="";
    ArrayAdapter ben_type_aangan_aaray;
    ArrayList<String> statusOfEncroachmentArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile_number);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        Initialize();

        spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("arg2",""+position);
                if (position > 0) {
                    Gender_Name = ben_type_aangan[position].toString();

                    if (Gender_Name.equals("पुरुष")) {

                        Gender_Code = "M";
                    } else if (Gender_Name.equals("महिला")) {

                        Gender_Code = "F";
                    }
                    else if (Gender_Name.equals("अन्य")) {

                        Gender_Code = "T";
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
    }



    public void Initialize(){
        reg_no=findViewById(R.id.reg_no);
        et_aadhar_No=findViewById(R.id.et_aadhar_No);
        et_ben_Name=findViewById(R.id.et_ben_Name);
        spin_gender=findViewById(R.id.spin_gender);
        ben_type_aangan_aaray = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ben_type_aangan);
        spin_gender.setAdapter(ben_type_aangan_aaray);
    }

}
