package com.bih.nic.MigrentJobSearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.BenDetails;
import com.bih.nic.MigrentJobSearch.Model.DefaultResponse;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends Activity implements View.OnClickListener{

    BenDetails benDetails;
    TextView tv_user_name,tv_mobile,tv_qualification,tv_age,tv_address,tv_experience,et_reg_num;
    CircleImageView img_studphoto;
    private final static int CAMERA_PIC = 99;
    Intent imageData1;
    String str_img1="",Reg_No="";
    DataBaseHelper dataBaseHelper;
    ImageView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        back_arrow=(ImageView)findViewById(R.id.back_arrow);
        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        dataBaseHelper = new DataBaseHelper(this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try {

            dataBaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        Reg_No= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        tv_mobile=(TextView)findViewById(R.id.tv_mobile);
        tv_qualification=(TextView)findViewById(R.id.tv_qualification);
        tv_age=(TextView)findViewById(R.id.tv_age);
        tv_address=(TextView)findViewById(R.id.tv_address);
        tv_experience=(TextView)findViewById(R.id.tv_experience);
        et_reg_num =(TextView) findViewById(R.id.et_reg_num);
        img_studphoto =(CircleImageView) findViewById(R.id.img_studphoto);

        img_studphoto.setOnClickListener(this);

        benDetails = new BenDetails();
        extractFrom_Data();
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void extractFrom_Data(){

        benDetails=(BenDetails)getIntent().getSerializableExtra("data");
        tv_user_name.setText(benDetails.getVchName());
        tv_mobile.setText(benDetails.getVchMobile());
        et_reg_num.setText(benDetails.getVchRegNum());
        tv_qualification.setText(benDetails.getIntQualification_name());
        tv_age.setText(benDetails.getIntAge());
        tv_age.setText(benDetails.getIntAge());
        tv_address.setText(benDetails.getVchAddress());
        tv_experience.setText(benDetails.getIntExpMonths());
        String imagesr=dataBaseHelper.getBenImg(Reg_No);

        if (imagesr!=null) {
            // if (benDetails.getVchPhoto() == null) {
            byte[] imgData = Base64.decode(imagesr, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
          //  img_studphoto.setScaleType(ImageView.ScaleType.FIT_XY);
            img_studphoto.setImageBitmap(bmp);


            //}
        }
        else {
            Picasso.with(this).load("http://10.133.20.159/"+benDetails.getVchPhoto()).error(R.drawable.profile).into(img_studphoto);
           // Picasso.with(this).load(benDetails.getVchPhoto()).into(img_studphoto);
        }

    }

    public void saveImgaetoLocal(){
        DataBaseHelper placeData = new DataBaseHelper(ProfileActivity.this);
        SQLiteDatabase db = placeData.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] whereArgs;

        values.put("Img_ben", str_img1);
        values.put("Lat1",String.valueOf(imageData1.getStringExtra("Lat")));
        values.put("Long1",String.valueOf(imageData1.getStringExtra("Lng")));
        whereArgs = new String[]{String.valueOf(Reg_No)};

        long c=db.update("UserDetails", values, "UseId=?", whereArgs);
        if(c>0)
        {
            Toast.makeText(ProfileActivity.this, "Ben Image saved successfully", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(ProfileActivity.this, "Ben Image 1 not saved ", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");


                    str_img1= org.kobjects.base64.Base64.encode(imgData);
                    imageData1=data;
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                            imgData.length);
                    //img_studphoto.setScaleType(ImageView.ScaleType.FIT_XY);
                    img_studphoto.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                            500, 500));

                    new UplaodImageData(Reg_No, String.valueOf(imageData1.getStringExtra("Lat")), String.valueOf(imageData1.getStringExtra("Lng")), str_img1).execute();
//                    if(c>0)
//                    {
//                        Toast.makeText(ProfileActivity.this, "Ben Image saved successfully", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(ProfileActivity.this, "Ben Image 1 not saved ", Toast.LENGTH_LONG).show();
//                    }
                    //img1.setOnClickListener(null);
                    //img2.setEnabled(true);

//                            btnOk.setBackgroundResource(R.drawable.buttonbackshape);
//                            btnOk.setEnabled(true);
                    break;




                    //  }


                }
        }

    }

    private class UplaodImageData extends AsyncTask<String, Void, DefaultResponse> {
        private final ProgressDialog dialog = new ProgressDialog(ProfileActivity.this);
        String regId, latitude, longitude, imageStr;

        public UplaodImageData(String regId, String latitude, String longitude, String imageStr) {
            this.regId = regId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.imageStr = imageStr;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("अप्लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected DefaultResponse doInBackground(String...arg) {
            return WebserviceHelper.updateProfileImage(regId, latitude, longitude, imageStr);
        }

        @Override
        protected void onPostExecute(DefaultResponse result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.getStatus()){
                saveImgaetoLocal();
                Toast.makeText(ProfileActivity.this, "प्रोफ़ाइल फ़ोटो सफलतापूर्वक अपडेट हों गया", Toast.LENGTH_SHORT).show();
            }else{
                AlertDialog.Builder ab = new AlertDialog.Builder(ProfileActivity.this);
                ab.setCancelable(false);
                ab.setTitle("Failed");
                ab.setMessage(result.getMessage());
                ab.setPositiveButton("[OK]", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                ab.show();
            }
        }
    }

    @Override
    public void onClick(View view) {

        Intent iCamera = new Intent(getApplicationContext(),CameraActivity.class);
        if (view.equals(img_studphoto))
            iCamera.putExtra("KEY_PIC", "1");
        startActivityForResult(iCamera, CAMERA_PIC);


    }

}
