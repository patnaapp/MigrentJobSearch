package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.Model.DefaultResponse;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.EmpRegDetails;
import com.bih.nic.MigrentJobSearch.Model.Organisation;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.WebserviceHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployerRegistrartionActivity extends Activity {
    Spinner sp_type_of_org,sp_district;
    EditText et_org_name,et_crosspondance_address,et_contact_person,et_mobile_number,et_alternative_mobile_number,et_email,et_password,et_reenter_password;
    ArrayList<District> DistrictList = new ArrayList<District>();
    ArrayList<Organisation> OrgList = new ArrayList<Organisation>();
    String Otp="",reg_OTP="",DistCode="",DistName="", OrgCode="",OrgName="",org_name="",crosspondance_address="", contact_person="",mobile_number="",
            alternative_mobile_number="", email="",password="",reenter_password="";
    Button btn_verify;
    EmpRegDetails userDetails;
    DataBaseHelper localDBHelper;
    ArrayList<String> districtNameArray;
    ArrayList<String> orgNameArray;
    ArrayAdapter<String> districtadapter;
    ArrayAdapter<String> orgadapter;
    Button btn_reg , btn_cancel,buttonConfirm_OTP,buttonResend_OTP;
    TextView viewmobile;
    EditText et_OTP;
    String type_0f_org[] = {"-चयन करे-","Goverment","Private","Semi-Goverment"};
    ArrayAdapter Type_0f_org;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_registrartion);
        localDBHelper= new DataBaseHelper(EmployerRegistrartionActivity.this);

        Utiilties.setStatusBarColor(this);
        getActionBar().hide();

        initialization();
        loadDistrictSpinnerdata();
        Type_0f_org = new ArrayAdapter<String>(this, R.layout.spinner_textview, type_0f_org);
        Type_0f_org.setDropDownViewResource(R.layout.spinner_textview);
        sp_type_of_org.setAdapter(Type_0f_org);
        //loadOrgdata();
    }
    public void initialization(){
        et_org_name=(EditText)findViewById(R.id.et_org_name);
        et_org_name.addTextChangedListener(inputTextWatcher1);
        sp_type_of_org=(Spinner)findViewById(R.id.sp_type_of_org);
        sp_district=(Spinner)findViewById(R.id.sp_district);
        et_crosspondance_address=(EditText)findViewById(R.id.et_crosspondance_address);
        et_crosspondance_address.addTextChangedListener(inputTextWatcher2);
        et_contact_person=(EditText)findViewById(R.id.et_contact_person);
        et_contact_person.addTextChangedListener(inputTextWatcher3);
        et_mobile_number=(EditText)findViewById(R.id.et_mobile_number);
        et_alternative_mobile_number=(EditText)findViewById(R.id.et_alternative_mobile_number);
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        et_reenter_password=(EditText)findViewById(R.id.et_reenter_password);
        btn_verify=(Button)findViewById(R.id.btn_verify_new);

        sp_type_of_org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    OrgName = type_0f_org[position].toString();
                    if (OrgName.equals("Goverment")) {
                        OrgCode = "1";
                    } else if (OrgName.equals("Private")) {
                        OrgCode = "2";
                    }else if (OrgName.equals("Semi-Goverment")) {
                        OrgCode = "3";
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    District district = DistrictList.get(position-1);
                    DistCode = district.get_DistCode();
                    DistName = district.get_DistName();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        btn_verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utiilties.isOnline(EmployerRegistrartionActivity.this)) {
                    registration();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(EmployerRegistrartionActivity.this);
                    alertDialog.setTitle("अलर्ट डायलॉग !");
                    alertDialog.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है .. कृपया नेटवर्क कनेक्शन चालू करें?");
                    alertDialog.setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });
                    alertDialog.setNegativeButton("नहीं", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertDialog.create();
                    alert.show();
                }
            }
        });
    }

    private TextWatcher inputTextWatcher1 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_org_name.getText().length() >0)
            {
                checkForEnglish(et_org_name);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public void registration() {
        org_name = et_org_name.getText().toString();
        crosspondance_address = et_crosspondance_address.getText().toString();
        contact_person = et_contact_person.getText().toString();
        mobile_number = et_mobile_number.getText().toString();
        alternative_mobile_number = et_alternative_mobile_number.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        reenter_password = et_reenter_password.getText().toString();
        boolean cancelRegistration = false;
        String isValied = "yes";
        View focusView = null;

        if (TextUtils.isEmpty(et_org_name.getText().toString())) {
            et_org_name.setError(getString(R.string.field_required));
            focusView = et_org_name;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(OrgCode)) {
            Toast.makeText(getApplicationContext(), "कृपया संस्था का प्रकार चयन करे |", Toast.LENGTH_LONG).show();
            focusView = sp_type_of_org;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(DistCode)) {
            Toast.makeText(getApplicationContext(), "कृपया जिला का नाम का चयन करे |", Toast.LENGTH_LONG).show();
            // sp_district.setError("कृपया जिला का नाम का चयन करे |");
            focusView = sp_district;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(et_crosspondance_address.getText().toString())) {
            et_crosspondance_address.setError(getString(R.string.field_required));
            focusView = et_crosspondance_address;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(contact_person)) {
            et_contact_person.setError(getString(R.string.field_required));
            focusView = et_contact_person;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(mobile_number)) {
            et_mobile_number.setError(getString(R.string.field_required));
            focusView = et_mobile_number;
            cancelRegistration = true;
        } else if (mobile_number.length() != 10) {
            et_mobile_number.setError(getString(R.string.Invalid_Number));
            focusView = et_mobile_number;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(alternative_mobile_number)) {
            et_alternative_mobile_number.setError(getString(R.string.field_required));
            focusView = et_alternative_mobile_number;
            cancelRegistration = true;
        } else if (alternative_mobile_number.length() != 10) {
            et_alternative_mobile_number.setError(getString(R.string.Invalid_Number));
            focusView = et_alternative_mobile_number;
            cancelRegistration = true;
        }
        if (!TextUtils.isEmpty(email)) {
            if (!isEmailValid(email)) {
                et_email.setError(getString(R.string.invalid_email));
                focusView = et_email;
                cancelRegistration = true;
            }
        }
        if (TextUtils.isEmpty(password)) {
            et_password.setError(getString(R.string.field_required));
            focusView = et_password;
            cancelRegistration = true;
        } else if (password.length() <= 5) {
            et_password.setError(getString(R.string.invalid_password));
            focusView = et_password;
            cancelRegistration = true;
        } else if (!isAlphaNumeric(password)) {
            et_password.setError(getString(R.string.invalid_password_pattern));
            focusView = et_password;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(reenter_password)) {
            et_reenter_password.setError(getString(R.string.field_required));
            focusView = et_reenter_password;
            cancelRegistration = true;
        } else if (!(password.equals(reenter_password))) {
            et_reenter_password.setError(getString(R.string.password_not_match));
            focusView = et_reenter_password;
            cancelRegistration = true;
        }

        if (cancelRegistration) {
            focusView.requestFocus();
        } else {
            userDetails= new EmpRegDetails();
            userDetails.setOrgName(org_name);
            userDetails.setOrgCode(OrgCode);
            userDetails.setDistCode(DistCode);
            userDetails.setCrosspondance_address(crosspondance_address);
            userDetails.setContact_Person(contact_person);
            userDetails.setMobile_Number(mobile_number);
            userDetails.setAlternative_Mobile_Number(alternative_mobile_number);
            userDetails.setEmail(email);
            userDetails.setPassword(password);
            try{
                //benfiList.setEncrypt_benName(_encrptor.Encrypt(et_husband_Name.getText().toString().trim(), CommonPref.CIPER_KEY));
                //benfiList.setEncrypt_AadharNo(_encrptor.Encrypt(et_father_aadhar_number_new.getText().toString().trim(), CommonPref.CIPER_KEY));
            }
            catch (Exception ex){
                ex.printStackTrace();
            }


            // PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", MobileNumber).commit();

            if (!GlobalVariables.isOffline && !Utiilties.isOnline(this)) {

                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setMessage(Html.fromHtml(
                        "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
                ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(I);
                    }
                });

                ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                ab.show();

            }else{

                //new stateData().execute();
                //new EmployerReg().execute(userDetails);
                new RegistrationTask().execute(userDetails);
            }

        }
    }
    private boolean isEmailValid(String email) {
        // add your own logic
        return email.contains("@");
    }
    public boolean isAlphaNumeric(String s) {
        // ^[a-zA-Z0-9]*$
        // ^[\p{L}\p{N}]{6,}$
        String pattern = "(([a-z]+[0-9]+)+|(([0-9]+[a-z]+)+))[0-9a-z]*";
        String pattern1 = "(([A-Z]+[0-9]+)+|(([0-9]+[A-Z]+)+))[0-9A-Z]*";
        if (s.matches(pattern) || s.matches(pattern1)) {
            return true;
        }
        return false;
    }

    private class EmployerReg extends AsyncTask<EmpRegDetails, Void, DefaultResponse> {

        private final ProgressDialog dialog = new ProgressDialog(
                EmployerRegistrartionActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(
                EmployerRegistrartionActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Registration...");
            this.dialog.show();
        }

        @Override
        protected DefaultResponse doInBackground(EmpRegDetails... param) {

            return WebserviceHelper.EmpRegistration(param[0]);

        }

        @Override
        protected void onPostExecute(DefaultResponse result) {

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();

                if (result != null) {
                    if (result.getStatus()==true) {

                                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                             finish();

                        }  if (result.getStatus()==false) {
                            Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                } else {
                    //chk_msg_OK_networkdata("Uploading failed.Please Try Again Later");
                    Toast.makeText(getApplicationContext(), "Result Null..Please Try Later", Toast.LENGTH_SHORT).show();
                }

//                if (result == null || !(result.equals("1"))) {
//
//                    alertDialog.setTitle("Failed");
//                    alertDialog.setMessage("Registration Failed");
//                    alertDialog.show();
//
//                } else {
//                    try {
//                        Toast.makeText(EmployerRegistrartionActivity.this, "You have successfully registered", Toast.LENGTH_LONG).show();
//
//                        Intent i = new Intent(getBaseContext(), EmployerRegistrartionActivity.class);
//                        startActivity(i);
//                        finish();
//
//                    } catch (Exception ex) {
//                        Toast.makeText(EmployerRegistrartionActivity.this,
//                                "Registration failed", Toast.LENGTH_SHORT)
//                                .show();
//                    }
//
//                }
            }
        }
    }
    public void loadDistrictSpinnerdata() {

        DistrictList = localDBHelper.getDistrictLocal_new();
        districtNameArray = new ArrayList<String>();
        districtNameArray.add("-चयन करे-");
        int i = 1;
        for (District district : DistrictList) {
            districtNameArray.add(district.get_DistNameHN());
            i++;
        }
        districtadapter = new ArrayAdapter<String>(this, R.layout.spinner_textview, districtNameArray);
        districtadapter.setDropDownViewResource(R.layout.spinner_textview);
        sp_district.setAdapter(districtadapter);

    }
//    public void loadOrgdata() {
//
//        OrgList = localDBHelper.getorg_new();
//        orgNameArray = new ArrayList<String>();
//        orgNameArray.add("-संस्था का प्रकार-");
//        int i = 1;
//        for (Organisation district : OrgList) {
//            orgNameArray.add(district.get_OrgName());
//            i++;
//        }
//        orgadapter = new ArrayAdapter<String>(this, R.layout.spinner_textview, orgNameArray);
//        orgadapter.setDropDownViewResource(R.layout.spinner_textview);
//        sp_type_of_org.setAdapter(orgadapter);
//
//    }
    private class RegistrationTask extends AsyncTask<EmpRegDetails, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(EmployerRegistrartionActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(EmployerRegistrartionActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Registration...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(EmpRegDetails... param) {

            //  String loginType= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LOGINTYPE", "");
            // String mobilenum= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MOBILENUM", "");
             Otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
            return WebserviceHelper.Registration_Mob(mobile_number,Otp);
        }

        @Override

        protected void onPostExecute(String result) {

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {
                //if (result.getStatus()==true) {
                if (result.equalsIgnoreCase("Success")){


                    try {

                        confirmOtp();

                    } catch (Exception ex) {
                        Toast.makeText(EmployerRegistrartionActivity.this,
                                "ERROR-Exception: Error !" + ex.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }

                }
                else if(result.equalsIgnoreCase("failure")){

                    Toast.makeText(EmployerRegistrartionActivity.this,
                            "!! URT !! Invalid user", Toast.LENGTH_SHORT)
                            .show();

                }

                else {

                    //unknown return type
                    Toast.makeText(EmployerRegistrartionActivity.this,
                            "Failed", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {

                Toast.makeText(EmployerRegistrartionActivity.this,
                        "Result Null ", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void confirmOtp() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.reg_otponfirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm_OTP = (Button) confirmDialog.findViewById(R.id.buttonConfirmpotp);
        buttonResend_OTP = (Button) confirmDialog.findViewById(R.id.btn_resendotp);
        et_OTP = (EditText) confirmDialog.findViewById(R.id.et_OTP);
        viewmobile = (TextView) confirmDialog.findViewById(R.id.viewmobile);


        //  showmobile = MobileNumber.replaceAll("\\w(?=\\w{4})", "*");

        //  viewmobile.setText(" OTP मोबाइल नंबर: "+showmobile+" पर भेजा गया ");

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);
        alert.setCancelable(false);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        buttonConfirm_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm_reg_OTP();

            }
        });
        buttonResend_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userDetails.setMobile_Number(mobile_number);

                new RegistrationTask().execute(userDetails);
            }
        });

    }
    public void confirm_reg_OTP(){

        reg_OTP = et_OTP.getText().toString();

        boolean changepassword = false;
        String isValied = "yes";
        View focusView = null;



        if (TextUtils.isEmpty(reg_OTP)) {
            et_OTP.setError("Enter OTP");
            focusView = et_OTP;
            changepassword = true;
        }



        if (changepassword) {
            // error in login
            focusView.requestFocus();
        } else {

            userDetails = new EmpRegDetails();
            userDetails.setOrgName(org_name);
            userDetails.setOrgCode(OrgCode);
            userDetails.setDistCode(DistCode);
            userDetails.setCrosspondance_address(crosspondance_address);
            userDetails.setContact_Person(contact_person);
            userDetails.setMobile_Number(mobile_number);
            userDetails.setAlternative_Mobile_Number(alternative_mobile_number);
            userDetails.setEmail(email);
            userDetails.setPassword(password);

            if(Otp.equalsIgnoreCase(et_OTP.getText().toString())) {

                new EmployerReg().execute(userDetails);
            }else{

            }


        }
    }


    public static boolean isInputInEnglish(String txtVal)
    {

        String regx = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtVal);
        return matcher.find();
    }

    public void checkForEnglish(EditText etxt)
    {
        if (etxt.getText().length() > 0)
        {
            String s = etxt.getText().toString();
            if (isInputInEnglish(s))
            {
                //OK
            }
            else
            {
                Toast.makeText(this, "कृपया अंग्रेजी में लिखे", Toast.LENGTH_SHORT).show();
                etxt.setText("");
            }
        }
    }

    private TextWatcher inputTextWatcher2 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_crosspondance_address.getText().length() >0)
            {
                checkForEnglish(et_crosspondance_address);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher inputTextWatcher3 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_contact_person.getText().length() >0)
            {
                checkForEnglish(et_contact_person);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}
