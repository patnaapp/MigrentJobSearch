package com.bih.nic.aadhar1;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bih.nic.aadhar1.DataBaseHelper.DataBaseHelper;
import com.bih.nic.aadhar1.Model.Versioninfo;

import java.io.IOException;


public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 1000;
    ProgressBar progressBar;
    DataBaseHelper databaseHelper;
    MarshmallowPermission permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Utiilties.setActionBarBackground(SplashActivity.this);
     Utiilties.setStatusBarColor(SplashActivity.this);

        //ActionBar actionBar = getActionBar();
        //actionBar.setTitle(" ");


        databaseHelper=new DataBaseHelper(getApplicationContext());
        try {
            databaseHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            databaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;


        }

       // start();

    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if(Utiilties.isOnline(SplashActivity.this)){

        new CheckUpdate().execute();}else {start();}
    }

    private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo> {


        CheckUpdate() {

        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Versioninfo doInBackground(Void... Params) {

            TelephonyManager tm = null;
            String imei = null;

            permission=new MarshmallowPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE);
            if(permission.result==-1 || permission.result==0)
            {

            }

            String version = null;
            try {
                version = getPackageManager().getPackageInfo(getPackageName(),
                        0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Versioninfo versioninfo = WebserviceHelper.CheckVersion(imei, version);

            return versioninfo;

        }

        @Override
        protected void onPostExecute(final Versioninfo versioninfo) {

            final AlertDialog.Builder ab = new AlertDialog.Builder(
                    SplashActivity.this);
            ab.setCancelable(false);
            if (versioninfo != null && versioninfo.isValidDevice()) {

                CommonPref.setCheckUpdate(getApplicationContext(),
                        System.currentTimeMillis());

                if (versioninfo.getAdminMsg().trim().length() > 0
                        && !versioninfo.getAdminMsg().trim()
                        .equalsIgnoreCase("anyType{}")) {

                    ab.setTitle(versioninfo.getAdminTitle());
                    ab.setMessage(Html.fromHtml(versioninfo.getAdminMsg()));
                    ab.setPositiveButton(getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    dialog.dismiss();

                                    showDailog(ab, versioninfo);

                                }
                            });
                    ab.show();
                } else {
                    showDailog(ab, versioninfo);
                }
            } else {
                if (versioninfo != null) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.wrong_device_text),
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    start();

                }
            }

        }
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                    /*if (check_ROLE.equalsIgnoreCase("COLL")) {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        Intent i = new Intent(SplashActivity.this, AdminActivity.class);
                        startActivity(i);
                    }*/

                // This method will be executed once the timer is over
                // Start your app main activity

                // close this activity
                if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "").equals("")) {

                    Intent i = new Intent(SplashActivity.this, Login.class);
                    startActivity(i);
                    finish();
                }else {
                    if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "").equalsIgnoreCase("BLKADM")) {
                        Intent i = new Intent(SplashActivity.this, VerifyAadhaar.class);
                        startActivity(i);
                        finish();
                    }else {
                        Intent i = new Intent(SplashActivity.this, VerifyAadhaar.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private void showDailog(AlertDialog.Builder ab,
                            final Versioninfo versioninfo) {

        if (versioninfo.isVerUpdated()) {

            if (versioninfo.getPriority() == 0) {

                start();
            } else if (versioninfo.getPriority() == 1) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());

                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package

                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri
                                        .parse("market://details?id="
                                                + getApplicationContext()
                                                .getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                            }


                        });
                ab.setNegativeButton(getResources().getString(R.string.ignore),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                dialog.dismiss();

                                start();
                            }

                        });

                ab.show();

            } else if (versioninfo.getPriority() == 2) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());
                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

								/*Intent myWebLink = new Intent(
										android.content.Intent.ACTION_VIEW);
								myWebLink.setData(Uri.parse(versioninfo
										.getAppUrl()));

								startActivity(myWebLink);

								dialog.dismiss();*/

                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                                // name
                                // and
                                // activity
                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri
                                        .parse("market://details?id="
                                                + getApplicationContext()
                                                .getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                            }
                        });
                ab.show();
            }
        } else {

            start();
        }

    }
}
