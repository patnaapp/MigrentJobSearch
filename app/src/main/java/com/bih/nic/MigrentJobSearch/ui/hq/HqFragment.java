package com.bih.nic.MigrentJobSearch.ui.hq;

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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.Model.DepartmentLoginEntity;
import com.bih.nic.MigrentJobSearch.Model.EmployerDetails;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;
import com.bih.nic.MigrentJobSearch.ui.employer.EmployerFragment;
import com.bih.nic.MigrentJobSearch.ui.employer.EmployerMainHomeActivity;

public class HqFragment extends Fragment {
    Button btn_login;
    EditText et_reg_no, et_otp;
    String str_email, str_pass;
    String[] param;
    TextView text_signup,tv_version,tv_registration;
    TelephonyManager tm;
    private static String imei;
    TextView info;


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        String version = Utiilties.getAppVersion(getContext());
        Initialization(root);

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
        // tv_registration = (TextView) root.findViewById(R.id.tv_registration);

        tv_version = (TextView) root.findViewById(R.id.tv_version);
    }
    private class LoginTask extends AsyncTask<String, Void, DepartmentLoginEntity> {

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
        protected DepartmentLoginEntity doInBackground(String... param) {

            return WebserviceHelper.DepartmentloginUser(regN0,otp);


        }

        @Override
        protected void onPostExecute( DepartmentLoginEntity result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if(result!=null) {
                if(result.isStatus()) {
                    result.setPassword(et_otp.getText().toString());
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                    //dataBaseHelper.insertUserDetails(result,str_email);

                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("UserId",et_reg_no.getText().toString()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Password",et_otp.getText().toString()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("OrgId",result.getOrgId()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("BlockCode",result.getBlockCode()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("LvlOne_Id",result.getLvlOneId()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Lvl_TwoId",result.getLvlTwoId()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("LvlThree_Id",result.getLvlThree()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("UserRole",result.getUserRole()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("UserName",result.getUserName()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("DistCode",result.getDistrictCode()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("isLogin",true).commit();
                    GlobalVariables.isLogin=true;

                    Intent intent=new Intent(getContext(), HqHomeActivity.class);
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
