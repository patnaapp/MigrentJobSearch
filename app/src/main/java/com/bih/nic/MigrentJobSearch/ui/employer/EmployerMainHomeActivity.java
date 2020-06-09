package com.bih.nic.MigrentJobSearch.ui.employer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.Model.BenDetails;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.ui.MultiLoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerMainHomeActivity extends Activity {
    LinearLayout ll_profile,ll_new_labours;
    String OrgId="",user_name="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId,UserRole="";
    TextView tv_benname,urole,tv_mobile,tv_address,tv_version,tv_email;
    CircleImageView profile_image;
    TextView tv_workplace,tv_phone,tv_work_address;
    //ImageView profile_image;

    BenDetails BenDetails;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home_new);
        getActionBar().hide();

        BenDetails=new BenDetails();
        dataBaseHelper=new DataBaseHelper(EmployerMainHomeActivity.this);
        modifyTable1();
        Utiilties.setStatusBarColor(EmployerMainHomeActivity.this);

        ll_profile=(LinearLayout)findViewById(R.id.ll_profile);
        ll_new_labours=(LinearLayout)findViewById(R.id.ll_new_labours);
        tv_benname=(TextView) findViewById(R.id.tv_benname);
        urole=(TextView) findViewById(R.id.urole);
        tv_mobile=(TextView) findViewById(R.id.tv_mobile);
        tv_address=(TextView) findViewById(R.id.tv_address);
        tv_version=(TextView) findViewById(R.id.tv_version);
        tv_email=(TextView) findViewById(R.id.tv_email);


        tv_workplace=(TextView) findViewById(R.id.tv_workplace);
        tv_phone=(TextView) findViewById(R.id.tv_phone);
        tv_work_address=(TextView) findViewById(R.id.tv_work_address);

        profile_image=(CircleImageView) findViewById(R.id.profile_image);

        String version = Utiilties.getAppVersion(this);
        if(version != null){
            tv_version.setText("ऐप वर्ज़न "+version);
        }else{
            tv_version.setText("");
        }

//        ll_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//        ll_new_labours.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        OrgId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");


        //DistName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", "");
        CompanyName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ComanyName", "");
        mobile=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Mobile", "");
        address=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Address", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");


//        tv_benname.setText(user_name);
        tv_workplace.setText(CompanyName);
        tv_phone.setText(mobile);
        tv_work_address.setText(address);
        tv_email.setText(UserId);
    }

    public void onSearchJob(View view){
        Intent intent = new Intent(this, LabourSearchActivity.class);
        startActivity(intent);
    }

    public void onOfferJobReport(View view){
        Intent intent = new Intent(this, JobOfferPostedActivity.class);
        startActivity(intent);
    }

    public void onAddWorkSite(View view){
        Intent intent = new Intent(this, AddWorkSiteDetails_Activity.class);
        startActivity(intent);
    }

    public void onModifyWorkSite(View view){
        Intent intent = new Intent(this, AddWorkSiteDetails_Activity.class);
        startActivity(intent);
    }
    public void OnClick_goToLoginScreen(View view){
        new AlertDialog.Builder(this)
                .setTitle("लॉग आउट ?")
                .setMessage("क्या आप वाकई एप्लिकेशन से लॉगआउट करना चाहते हैं \n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmLogout();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    private void confirmLogout()
    {
        SharedPreferences settings = this.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        GlobalVariables.isLogin=false;
//        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId","").commit();
//        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserRole","").commit();
        Intent intent = new Intent(this, MultiLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onExit()
    {
        new AlertDialog.Builder(this)
                .setTitle("अलर्ट!!")
                .setMessage("क्या आप ऐप बन्द करना चाहते हैं??\n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        onExit();
    }


    public void modifyTable1(){
        String UserTable = "MasterDept";

        if(isColumnExists(UserTable, "Dept_Id") == false){
            AlterTable(UserTable, "Dept_Id");
        }

        if(isColumnExists(UserTable, "Dept_Name") == false){
            AlterTable(UserTable, "Dept_Name");
        }

        if(isColumnExists(UserTable, "DeptName_Hn") == false){
            AlterTable(UserTable, "DeptName_Hn");
        }

        if(isColumnExists(UserTable, "Dept_Abbrev") == false){
            AlterTable(UserTable, "Dept_Abbrev");
        }

        if(isColumnExists(UserTable, "NameSymbol") == false){
            AlterTable(UserTable, "NameSymbol");
        }

        if(isColumnExists(UserTable, "status") == false){
            AlterTable(UserTable, "status");
        }


    }



    public void AlterTable(String tableName,String columnName)
    {
        db = dataBaseHelper.getReadableDatabase();

        try{
            db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
            Log.e("ALTER Done",tableName +"-"+ columnName);
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed",tableName +"-"+ columnName);
        }
    }

    public boolean isColumnExists (String table, String column) {
        db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        cursor.close();
        return false;
    }
}
