package com.bih.nic.aadhar1;

import android.content.Context;
import android.util.Log;

import com.bih.nic.aadhar1.Model.BenDetails;
import com.bih.nic.aadhar1.Model.BlockWeb;
import com.bih.nic.aadhar1.Model.DefaultResponse;
import com.bih.nic.aadhar1.Model.District;
import com.bih.nic.aadhar1.Model.JobListEntity;
import com.bih.nic.aadhar1.Model.SkillMaster;
import com.bih.nic.aadhar1.Model.SubSkillMaster;
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
    private static final String SKILL_METHOD="SkilMasterList";
    private static final String JOB_SEARCH_METHOD="JobSearchDetails1";
    private static final String SUBSKILL_METHOD="SubSkilMasterList";
    private static final String DISTRICT_METHOD="getDistrict";
    private static final String PANCHAYAT_METHOD="getPanchyat";
    private static final String UpdateUID_Status="UpdateUID_Status";
    private static final String SEARCHGLOBAL="getAadharbyid";
    private static final String GETAADHAARUSERDETAIL="getAadharUserDetail";
    private static final String UploadDataForMobNo_chng="VerifyDetail";
    private static final String GET_BEN_DETAILS="getuserDetails";
    private static final String UpdateMobile="updateUserMob";
    private static final String Reques_tOTP="ResendVerifyOtp";
    private static final String UpdateAadhar_Status="UpdateAadhar_Status";
    private static final String UpdateAliveCertificate="UpdateAliveCertificate";
    private static final String UPLOAD_METHOD="InsertDetails";
    private static final String BindWard="BindWard";

    public static final  String APPVERSION_METHOD = "getAppLatest";
    public static final  String UpdateUserDetails = "UpdateUserDetails";


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

    public static ArrayList<JobListEntity> searchJobMasterData(String regId, String distId) {

        SoapObject res1;
        res1=getServerData(JOB_SEARCH_METHOD, JobListEntity.JobListEntity_CLASS, "UserId", "Districtcode", regId, distId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<JobListEntity> fieldList = new ArrayList<JobListEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    JobListEntity block= new JobListEntity(final_object);
                    fieldList.add(block);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SkillMaster> getSkillMasterData() {

        SoapObject res1;
        res1=getServerData(SKILL_METHOD, SkillMaster.SkillMaster_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SkillMaster> fieldList = new ArrayList<SkillMaster>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SkillMaster block= new SkillMaster(final_object);
                    fieldList.add(block);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SubSkillMaster> getSubSkillMasterData() {

        SoapObject res1;
        res1=getServerData(SUBSKILL_METHOD, SubSkillMaster.SubSkillMaster_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SubSkillMaster> fieldList = new ArrayList<SubSkillMaster>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SubSkillMaster block= new SubSkillMaster(final_object);
                    fieldList.add(block);
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
            res1=getServerData(AuthenticateUser, UserDetails.getUserClass(),"RegistrationNo","OTP",User_ID,Pwd);
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


    public static DefaultResponse UploadFinalData(String regno, String aadharno,String benname,String gendercode)
    {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UploadDataForMobNo_chng);

        request.addProperty("RegistrationNo",regno);
        request.addProperty("AdharNo", aadharno);
        request.addProperty("Name",benname);
        request.addProperty("Gender",gendercode);
        DefaultResponse userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, DefaultResponse.DefaultResponse_CLASS.getSimpleName(), DefaultResponse.DefaultResponse_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UploadDataForMobNo_chng, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new DefaultResponse(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userDetails;

    }

    public static BenDetails getBen_Details(String regno)
    {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_BEN_DETAILS);

        request.addProperty("RegistrationNo",regno);

        BenDetails userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, BenDetails.USER_CLASS.getSimpleName(), BenDetails.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_BEN_DETAILS, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new BenDetails(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userDetails;

    }


    public static DefaultResponse UpdateMobileNumber(String regno, String newmobile)
    {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UpdateMobile);

        request.addProperty("_registrationNo",regno);
        request.addProperty("_MobileNo", newmobile);

        DefaultResponse userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, DefaultResponse.DefaultResponse_CLASS.getSimpleName(), DefaultResponse.DefaultResponse_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UpdateMobile, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new DefaultResponse(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userDetails;

    }

    public static DefaultResponse RequestOTP(String regno, String newmobile)
    {

        SoapObject request = new SoapObject(SERVICENAMESPACE, Reques_tOTP);

        request.addProperty("_registrationNo",regno);
        request.addProperty("_MobileNo", newmobile);

        DefaultResponse userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, DefaultResponse.DefaultResponse_CLASS.getSimpleName(), DefaultResponse.DefaultResponse_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + Reques_tOTP, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new DefaultResponse(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userDetails;

    }
    public static String UpdateUserDetail(BenDetails benDetails,String RegId,String Date) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    UpdateUserDetails);
            request.addProperty("_Reg",RegId);
            request.addProperty("_Dist",benDetails.getDistrictCode());
            request.addProperty("_Block",benDetails.getBlockCode());
            request.addProperty("_Panchayat",benDetails.getPanchayatCode());
            request.addProperty("_Mobile",benDetails.getVchMobile());
            request.addProperty("_Account",benDetails.getVchBankAccount());
            request.addProperty("_BankName",benDetails.getVchBankName());
            request.addProperty("_IFSC",benDetails.getVchIfsc());
            request.addProperty("_Skill",benDetails.getSkill_Id());
            request.addProperty("_SubSkillId",benDetails.getSubSkillId());
            request.addProperty("_Qualification",benDetails.getIntQualification());
            request.addProperty("_Aadhaar",benDetails.getVchAadhaar());
            request.addProperty("_vchName",benDetails.getVchName());
            request.addProperty("_NoofExperienceYear",benDetails.getIntExpYears());
            request.addProperty("_NoofExperienceMonth",benDetails.getIntExpMonths());


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UpdateUserDetails, envelope);
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
    public static String VerifyAdhaar(String adhaar,String name) {
        try {
            SoapObject request = new SoapObject("http://tempuri.org/", "DemographicUIDAuth");

            request.addProperty("mobile_auth_aadhaar",adhaar);
            request.addProperty("name",name);
            request.addProperty("dob","");





            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE("http://164.100.37.11/UIDAuthService/UIDAuth.asmx");
            androidHttpTransport.call("http://tempuri.org/" + "DemographicUIDAuth", envelope);
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

}
