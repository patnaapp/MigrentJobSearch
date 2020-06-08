package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.EmployerDetails;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;

public class EmployerFragment extends Fragment {

    Button btn_login;
    EditText et_reg_no, et_otp;
    String str_email, str_pass;
    String[] param;
    TextView text_signup,tv_version,tv_registration;
    TelephonyManager tm;
    private static String imei;
    TextView info;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_employer, container, false);

        String version = Utiilties.getAppVersion(getContext());

        Initialization(root);

        if(version != null){
            tv_version.setText("ऐप वर्ज़न "+version);
        }else{
            tv_version.setText("");
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setvalue();
                if(isValidInput()){
                    if(Utiilties.isOnline(getContext())) {
                        setvalue();
                        new LoginTask(et_reg_no.getText().toString(),et_otp.getText().toString()).execute();
                    }else {
                        Utiilties.internetNotAvailableDialog(getContext());
                    }
                }

            }
        });

        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EmployerRegistrartionActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    Boolean isValidInput(){
        View focusView = null;
        boolean validate = true;

        if (et_reg_no.getText().toString().equals("")) {
            et_reg_no.setError("कृप्या सही पंजीकरण संख्या डालें");
            focusView = et_reg_no;
            validate = false;
        }

        if (et_otp.getText().toString().equals("")) {
            et_otp.setError("कृप्या सही ओटीपी डालें");
            focusView = et_otp;
            validate = false;
        }

        if (!validate) focusView.requestFocus();
        return validate;
    }

    private void setvalue() {
        str_email = et_reg_no.getText().toString();
        str_pass = et_otp.getText().toString();
    }

    private void Initialization(View root) {
        et_reg_no = (EditText) root.findViewById(R.id.et_reg_no);
        et_otp = (EditText) root.findViewById(R.id.et_otp);
        btn_login = (Button) root.findViewById(R.id.btn_login);
        tv_registration = (TextView) root.findViewById(R.id.tv_registration);

        tv_version = (TextView) root.findViewById(R.id.tv_version);
    }

    private class LoginTask extends AsyncTask<String, Void, EmployerDetails> {

        private final ProgressDialog dialog = new ProgressDialog(getContext());

        private final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        String regN0,otp;

        public LoginTask(String regN0, String otp) {
            this.regN0 = regN0;
            this.otp = otp;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getResources().getString(R.string.authenticating));
            this.dialog.show();
        }

        @Override
        protected EmployerDetails doInBackground(String... param) {

            return WebserviceHelper.EmployerloginUser(regN0,otp);


        }

        @Override
        protected void onPostExecute( EmployerDetails result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if(result!=null) {
                if(result.isStatus()) {
                    result.setPassword(et_otp.getText().toString());
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                    //dataBaseHelper.insertUserDetails(result,str_email);

                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("UserId",et_reg_no.getText().toString()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Password",et_otp.getText().toString()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("OrgId",result.getOrgId()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("ComanyName",result.getComanyNameEn()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("CompanyType",result.getCompanyType()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Address",result.getAddressEn()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("DistCode",result.getDistCode()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Mobile",result.getMobile()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("UserRole",result.getUserRole()).commit();
//                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("BlockName",result.getBlockName()).commit();
//                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("PanchayatName",result.getPanchayatName()).commit();
//                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Photo",result.getProfileImg()).commit();

                    Intent intent=new Intent(getContext(), EmployerMainHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else {
                    Toast.makeText(getContext(),result.getMsg(),Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getContext(),"Null Response: Check Network and try again!!",Toast.LENGTH_LONG).show();
            }
        }
    }
}
