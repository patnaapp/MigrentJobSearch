package com.bih.nic.aadhar1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.aadhar1.DataBaseHelper.DataBaseHelper;
import com.bih.nic.aadhar1.Model.BenDetails;

public class ProfileActivity extends Activity implements View.OnClickListener{

    BenDetails benDetails;
    TextView tv_user_name,tv_mobile,tv_qualification,tv_age,tv_address,tv_experience,et_reg_num;
    ImageView img_studphoto;
    private final static int CAMERA_PIC = 99;
    Intent imageData1;
    String str_img1="",Reg_No="";
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        Reg_No= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        tv_mobile=(TextView)findViewById(R.id.tv_mobile);
        tv_qualification=(TextView)findViewById(R.id.tv_qualification);
        tv_age=(TextView)findViewById(R.id.tv_age);
        tv_address=(TextView)findViewById(R.id.tv_address);
        tv_experience=(TextView)findViewById(R.id.tv_experience);
        et_reg_num =(TextView) findViewById(R.id.et_reg_num);
        img_studphoto =(ImageView) findViewById(R.id.img_studphoto);

        img_studphoto.setOnClickListener(this);

        benDetails = new BenDetails();

        extractFrom_Data();
    }

    public void extractFrom_Data(){

        benDetails=(BenDetails)getIntent().getSerializableExtra("data");
        tv_user_name.setText(benDetails.getVchName());
        tv_mobile.setText(benDetails.getVchMobile());
        et_reg_num.setText(benDetails.getVchRegNum());
        tv_qualification.setText(benDetails.getIntQualification());
        tv_age.setText(benDetails.getIntAge());
        tv_age.setText(benDetails.getIntAge());
        tv_address.setText(benDetails.getVchAddress());
        tv_experience.setText(benDetails.getIntExpMonths());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");


                    //imageData.add(data);


                    //switch (data.getIntExtra("KEY_PIC", 0)) {
                    //  case 1:
                    DataBaseHelper placeData = new DataBaseHelper(
                            ProfileActivity.this);
                    SQLiteDatabase db = placeData.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    String[] whereArgs;
                    str_img1= org.kobjects.base64.Base64.encode(imgData);
                    imageData1=data;
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                            imgData.length);
                    img_studphoto.setScaleType(ImageView.ScaleType.FIT_XY);
                    img_studphoto.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                            500, 500));
                    values.put("Img_ben", str_img1);
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

                    break;

                }

        }

    }


    @Override
    public void onClick(View view) {

        Intent iCamera = new Intent(getApplicationContext(),CameraActivity.class);
        if (view.equals(img_studphoto)) iCamera.putExtra("KEY_PIC", "1");

        startActivityForResult(iCamera, CAMERA_PIC);


    }

}
