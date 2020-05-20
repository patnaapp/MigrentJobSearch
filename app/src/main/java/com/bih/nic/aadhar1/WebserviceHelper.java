package com.bih.nic.aadhar1;

import android.content.Context;
import android.util.Log;

import com.bih.nic.aadhar1.Model.BlockWeb;
import com.bih.nic.aadhar1.Model.District;
import com.bih.nic.aadhar1.Model.UserDetails;
import com.bih.nic.aadhar1.Model.Versioninfo;
import com.bih.nic.aadhar1.Model.panchayat;
import com.bih.nic.aadhar1.Model.ward_model;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * Created by nicsi on 3/17/2018.
 */
public class WebserviceHelper implements KvmSerializable {


    private Context ctx;
    public static final String SERVICENAMESPACE = "http://10.133.20.159/";
   // public static final String SERVICENAMESPACE = "http://164.100.251.15/";
    //public static final String SERVICEURL = "http://elabharthi.bih.nic.in/elabhwebservice.asmx";
    public static final String SERVICEURL = "http://10.133.20.159/TestService/MigrantJobSearchWebservice.asmx";
    private static final String AuthenticateUser = "Authenticate";
    private static final String GETBENEFICIARYLIST="getAadhaar";
    private static final String UpdateMobile_UID="UpdateMobile_UID";
    private static final String BLOCK_METHOD="getBlock";
    private static final String DISTRICT_METHOD="getDistrict";
    private static final String PANCHAYAT_METHOD="getPanchyat";
    private static final String UpdateUID_Status="UpdateUID_Status";
    private static final String SEARCHGLOBAL="getAadharbyid";
    private static final String GETAADHAARUSERDETAIL="getAadharUserDetail";
   // private static final String SEARCADAARBYID="getAadharbyid";

    private static final String UpdateAadhar_Status="UpdateAadhar_Status";
    private static final String UpdateAliveCertificate="UpdateAliveCertificate";
    private static final String UPLOAD_METHOD="InsertDetails";
    private static final String BindWard="BindWard";

    public static final  String APPVERSION_METHOD = "getAppLatest";



    public static ArrayList<District> getDistrictData() {

        SoapObject res1;
        res1=getServerData(DISTRICT_METHOD, District.DISTRICT_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();
        ArrayList<District> fieldList = new ArrayList<District>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    District sm = new District(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }



        return fieldList;
    }

    public static ArrayList<BlockWeb> getBlockData(String distCode) {

        SoapObject res1;
        res1=getServerData(BLOCK_METHOD, BlockWeb.BlockWeb_CLASS,"District_Code",distCode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<BlockWeb> fieldList = new ArrayList<BlockWeb>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    BlockWeb block= new BlockWeb(final_object);
                    fieldList.add(block);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }
    public static ArrayList<ward_model> getBenByWard(String benId) {

        SoapObject res1;
        res1=getServerData(BindWard, ward_model.WARD_MODEL_CLASS,"ID",benId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<ward_model> fieldList = new ArrayList<ward_model>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ward_model block= new ward_model(final_object);
                    fieldList.add(block);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }
     public static ArrayList<panchayat> getPanchayatData(String panch) {

        SoapObject res1;
        res1=getServerData(PANCHAYAT_METHOD, panchayat.panchayat,"_BlockCode",panch);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<panchayat> fieldList = new ArrayList<panchayat>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    panchayat block= new panchayat(final_object);
                    fieldList.add(block);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static UserDetails Login(String User_ID, String Pwd) {
        try {
            SoapObject res1;
            res1=getServerData(AuthenticateUser, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            if (res1 != null) {
                return new UserDetails(res1);
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    } public static UserDetails loginUser(String User_ID, String Pwd) {
        try {
            SoapObject res1;
            res1=getServerData(GETAADHAARUSERDETAIL, UserDetails.getUserClass(),"RegistrationNo","OTP",User_ID,Pwd);
            if (res1 != null) {
                return new UserDetails(res1);
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    public static SoapObject getServerData(String methodName,Class bindClass)
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }




    public static SoapObject getServerData(String methodName,Class bindClass,String param1,String param2,String value1,String value2 )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }
    public static SoapObject getServerData(String methodName,Class bindClass,String param,String value )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param,value);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static ArrayList<BenfiList> beneficiaryData(String distcode,String blockcode,String panchayatID) {



        SoapObject request = new SoapObject(SERVICENAMESPACE, GETBENEFICIARYLIST);


        request.addProperty("DistCode",distcode);
        request.addProperty("BlockCode",blockcode);
        request.addProperty("PanchayatCode",panchayatID);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    BenfiList.BENFICLASS.getSimpleName(), BenfiList.BENFICLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL,60000);
            androidHttpTransport.call(SERVICENAMESPACE + GETBENEFICIARYLIST, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<BenfiList> pvmArrayList = new ArrayList<BenfiList>();
        if(TotalProperty>0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        BenfiList state = new BenfiList(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }
  /*  public static ArrayList<BenfiList> SearchById(String benfiId) {



        SoapObject request = new SoapObject(SERVICENAMESPACE, SEARCADAARBYID);


        request.addProperty("benid",benfiId);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    BenfiList.getdata.getSimpleName(), BenfiList.getdata);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL,60000);
            androidHttpTransport.call(SERVICENAMESPACE + SEARCADAARBYID, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<BenfiList> pvmArrayList = new ArrayList<BenfiList>();
        if(TotalProperty>0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        BenfiList state = new BenfiList(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }*/
    public static ArrayList<BenfiList> SearchGlobal(String benid) {



        SoapObject request = new SoapObject(SERVICENAMESPACE, SEARCHGLOBAL);


        request.addProperty("benid",benid);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    BenfiList.BENFICLASS.getSimpleName(), BenfiList.BENFICLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL,60000);
            androidHttpTransport.call(SERVICENAMESPACE + SEARCHGLOBAL, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<BenfiList> pvmArrayList = new ArrayList<BenfiList>();
        if(TotalProperty>0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        BenfiList state = new BenfiList(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }



    public static String UpDateAdhaar(String data,String mobileno,String uid) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    UpdateMobile_UID);
            request.addProperty("_BenId",data);
            request.addProperty("_Mobile",mobileno);
            request.addProperty("_UID",uid);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UpdateMobile_UID, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String UpdateUidStatus(BenfiList benfiList) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    UpdateUID_Status);
            request.addProperty("_BenId",benfiList.getBeneficiary_id());
            request.addProperty("_UID_Status",benfiList.getUidStatus());
            request.addProperty("_NameInAdhhar",benfiList.getModifiedName());
            request.addProperty("_uid",benfiList.getAadharNumber());
           // request.addProperty("_DOB",dob);
           // request.addProperty("_UidVerifyDate",currentdate);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UpdateUID_Status, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static String UpdateAliveCertificate(String id,String status) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    UpdateAliveCertificate);
            request.addProperty("_BenId",id);
            request.addProperty("_JeevanPramanStatus",status);

           // request.addProperty("_DOB",dob);
           // request.addProperty("_UidVerifyDate",currentdate);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UpdateAliveCertificate, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }  public static String UpdateAliveCertificate1(BenfiList benfiList) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    UpdateAliveCertificate);
            request.addProperty("_BenId",benfiList.getBeneficiary_id());
            request.addProperty("_JeevanPramanStatus",benfiList.getUidStatus());

           // request.addProperty("_DOB",dob);
           // request.addProperty("_UidVerifyDate",currentdate);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UpdateAliveCertificate, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static String UpdateAadhar_Status(BenfiList data) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    UpdateAadhar_Status);
            request.addProperty("_DistCode",data.getDistcode());
            request.addProperty("_BlockCode",data.getBlockcode());
            request.addProperty("_BenId",data.getBeneficiary_id());
            request.addProperty("_Aadhhar",data.getAadharNumber());
            request.addProperty("_NameInAadhhar",data.getBeneficiery_name());
            request.addProperty("_UpdatedBy",data.getEntryby());
           // request.addProperty("_DOB",dob);
           // request.addProperty("_UidVerifyDate",currentdate);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UpdateAadhar_Status, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    @Override
    public Object getProperty(int index) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int index, Object value) {

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {

    }


    public static String Upload(BenfiList data,String userid,String date) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    UPLOAD_METHOD);

            request.addProperty("_BeneficiaryId",data.getBeneficiary_id());
            request.addProperty("_Dist",data.getDistcode());
            request.addProperty("_Block",data.getBlockcode());
            request.addProperty("_Panchayat",data.getBlocjname());
            request.addProperty("_NameAsPerAadhar",data.getNameASPerAdhaar());
            request.addProperty("_BenName",data.getBeneficiery_name());
            request.addProperty("_BankName",data.getNameInPass());
            request.addProperty("_AadharNo",data.getAadharNumber());//_FYearID
            request.addProperty("_Ward",data.getWard());
            request.addProperty("_MobileNo",data.getBen_Mobile());
            request.addProperty("_YearOfBirth",data.getYearOfBorth());
            request.addProperty("_Photo1",data.getPhoto1());
            request.addProperty("_photo2",data.getPhoto2());
            request.addProperty("_Photo3",data.getPhoto3());
            request.addProperty("_Photo4",data.getPhoto4());
            request.addProperty("_EntryBy",userid);
            request.addProperty("_EntryDate",date);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    BenfiList.BENFICLASS.getSimpleName(),
                    BenfiList.BENFICLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL,50000);
            androidHttpTransport.call(SERVICENAMESPACE + UPLOAD_METHOD,
                    envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                Log.d("efghegehg", result.toString());

                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static Versioninfo CheckVersion(String imei, String version) {
        Versioninfo versioninfo;
        SoapObject res1;
        try {

            res1=getServerData(APPVERSION_METHOD, Versioninfo.Versioninfo_CLASS,"IMEI","Ver",imei,version);
            SoapObject final_object = (SoapObject) res1.getProperty(0);

            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {

            return null;
        }
        return versioninfo;

    }

}
