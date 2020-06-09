package com.bih.nic.MigrentJobSearch;

import android.content.Context;
import android.util.Log;

import com.bih.nic.MigrentJobSearch.Model.AcptdRjctdJobOfferEntity;
import com.bih.nic.MigrentJobSearch.Model.BenDetails;
import com.bih.nic.MigrentJobSearch.Model.BlkCompanyJobDetailsEntity;
import com.bih.nic.MigrentJobSearch.Model.BlockJobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.Model.BlockWeb;
import com.bih.nic.MigrentJobSearch.Model.DefaultResponse;
import com.bih.nic.MigrentJobSearch.Model.DepartmentMaster;
import com.bih.nic.MigrentJobSearch.Model.District;
import com.bih.nic.MigrentJobSearch.Model.EmpRegDetails;
import com.bih.nic.MigrentJobSearch.Model.EmployerDetails;
import com.bih.nic.MigrentJobSearch.Model.JobListEntity;
import com.bih.nic.MigrentJobSearch.Model.JobOfferPostedEntity;
import com.bih.nic.MigrentJobSearch.Model.PaymentStatusEntity;
import com.bih.nic.MigrentJobSearch.Model.SkillMaster;
import com.bih.nic.MigrentJobSearch.Model.SubSkillMaster;
import com.bih.nic.MigrentJobSearch.Model.UserDetails;
import com.bih.nic.MigrentJobSearch.Model.Versioninfo;
import com.bih.nic.MigrentJobSearch.Model.WorkerModel;
import com.bih.nic.MigrentJobSearch.Model.panchayat;
import com.bih.nic.MigrentJobSearch.Model.ward_model;
import com.bih.nic.MigrentJobSearch.Model.workListModel;

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

//    public static final String SERVICENAMESPACE = "http://shramsadhan.bih.nic.in/";
//    public static final String SERVICEURL = "http://shramsadhan.bih.nic.in/MigrantJobSearchWebservice.asmx";

    public static final String SERVICENAMESPACE = "http://10.133.20.159/";
    public static final String SERVICEURL = "http://10.133.20.159/TestService/MigrantJobSearchWebservice.asmx";

    private static final String AuthenticateUser = "Authenticate";
    private static final String AuthenticateORGUser = "AuthenticateOrgLogin";
    private static final String GETBENEFICIARYLIST="getAadhaar";
    private static final String UpdateMobile_UID="UpdateMobile_UID";
    private static final String BLOCK_METHOD="getBlock";
    private static final String SKILL_METHOD="SkilMasterList";
    private static final String Dept_METHOD="DepartmentList";
    private static final String JOB_SEARCH_METHOD="JobSearchDetails1";
    private static final String GET_JOB_REQUEST_METHOD="GetJobRequest";
    private static final String GET_JOB_Offer_Posted_METHOD="GetJobOfferOrg";
    private static final String GET_JOB_Offer_block_Posted_METHOD="GetJobOfferBlockwise";
    private static final String GET_Acpt_Rjct_Job_By_Labour="GetJobOfferLabourDetails";
    private static final String GET_Blk_Wise_company_Jobs="GetCompanayBlockWise";
    private static final String UPDATE_PROFILE_IMAGE_METHOD="UpdateImage";
    private static final String SUBSKILL_METHOD="SubSkilMasterList";
    private static final String DISTRICT_METHOD="getDistrict";
    private static final String PANCHAYAT_METHOD="getPanchyat";
    private static final String UpdateUID_Status="UpdateUID_Status";
    private static final String SEARCHGLOBAL="getAadharbyid";
    private static final String GETAADHAARUSERDETAIL="getAadharUserDetail";
    private static final String UploadDataForMobNo_chng="VerifyDetail";
    private static final String GET_BEN_DETAILS="getuserDetails";
    private static final String GET_PAYMENT_DETAILS="GetPaymentDtls";
    private static final String UpdateMobile="updateUserMob";
    private static final String Reques_tOTP="ResendVerifyOtp";
    private static final String UpdateAadhar_Status="UpdateAadhar_Status";
    private static final String UpdateAliveCertificate="UpdateAliveCertificate";
    private static final String UPLOAD_METHOD="InsertDetails";
    private static final String BindWard="BindWard";

    public static final  String APPVERSION_METHOD = "getAppLatest";
    public static final  String UpdateUserDetails = "UpdateUserDetails";
    public static final  String AcceptRjctRecordsFromPacs = "UpdateRequest";
    public static final  String GetWorkDetails = "GetWorkDetails";
    public static final  String GetRequirmentData = "GetRequirmentData";
    public static final  String GetLoadLabourData = "GetLoadLabourData";
    public static final  String EmployerRegistration_METHOD = "InsertCompanyDtls";
    public static final  String REGISTRATION_EMP_MOB_METHOD = "OrgsendOtp";
    static String rest;


    public static ArrayList<District> getDistrictData() {

        SoapObject res1;
        res1=getServerData(DISTRICT_METHOD, District.DISTRICT_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();
        ArrayList<District> fieldList = new ArrayList<District>();

        for (int i = 0; i < TotalProperty; i++)
        {
            if (res1.getProperty(i) != null)
            {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject)
                {
                    SoapObject final_object = (SoapObject) property;
                    District sm = new District(final_object);
                    fieldList.add(sm);
                }
            }
            else
            {
                return fieldList;
            }

        }

        return fieldList;
    }

    public static DefaultResponse updateProfileImage(String regId, String lat, String longi, String img) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATE_PROFILE_IMAGE_METHOD);

        request.addProperty("_RegistrationNo",regId);
        request.addProperty("_Latlong", lat);
        request.addProperty("_Longitude",longi);
        request.addProperty("_Image",img);
        DefaultResponse userDetails;
        SoapObject res1;

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, DefaultResponse.DefaultResponse_CLASS.getSimpleName(), DefaultResponse.DefaultResponse_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UPDATE_PROFILE_IMAGE_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new DefaultResponse(res1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return userDetails;
    }

    public static ArrayList<JobListEntity> searchJobMasterData(String regId, String distId) {

        SoapObject res1;
        res1=getServerData(GET_JOB_REQUEST_METHOD, JobListEntity.JobListEntity_CLASS, "RegistrationNo", regId);
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

    public static ArrayList<BlockWeb> getBlockData(String distCode)
    {

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
    public static ArrayList<workListModel> GetWorkDetails(String panch) {

        SoapObject res1;
        res1=getServerData(GetWorkDetails, panchayat.panchayat,"orgId",panch);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<workListModel> fieldList = new ArrayList<workListModel>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    workListModel block= new workListModel(final_object);
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

    }

    public static UserDetails loginUser(String User_ID, String Pwd) {
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

    public static EmployerDetails EmployerloginUser(String User_ID, String Pwd) {
        try {
            SoapObject res1;
            res1=getServerData(AuthenticateORGUser, UserDetails.getUserClass(),"_UserId","_Password",User_ID,Pwd);
            if (res1 != null) {
                return new EmployerDetails(res1);
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
           // SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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

    public static PaymentStatusEntity getPaymentDetail(String regno) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_PAYMENT_DETAILS);

        request.addProperty("RegistrationNo",regno);

        PaymentStatusEntity userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, PaymentStatusEntity.PaymentStatusEntity_CLASS.getSimpleName(), PaymentStatusEntity.PaymentStatusEntity_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_PAYMENT_DETAILS, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new PaymentStatusEntity(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userDetails;

    }

    public static BenDetails getBen_Details(String regno) {

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
//            request.addProperty("_Dist",benDetails.getDistrictCode());
//            request.addProperty("_Block",benDetails.getBlockCode());

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
            request.addProperty("_Reg",RegId);
            request.addProperty("_NoofExperienceYear",benDetails.getIntExpYears());
            request.addProperty("_NoofExperienceMonth",benDetails.getIntExpMonths());

            request.addProperty("_tQualificationId","");

            request.addProperty("_vchWorkAddress",benDetails.getVchWorkAddress());
            request.addProperty("_vchGuardian_name",benDetails.getVchGuardian_name());
            request.addProperty("_vchGuardian_number",benDetails.getVchGuardian_number());
            request.addProperty("_intAge",benDetails.getIntAge());
            request.addProperty("_vchAddress",benDetails.getVchAddress());
            request.addProperty("_intGender",benDetails.getIntGender());
            request.addProperty("_NameAsPerAadhar",benDetails.getNameAsPerAdhaar());



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



    public static DefaultResponse UploadAcceptedRecordsFromPacs(JobListEntity data, String regNo) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, AcceptRjctRecordsFromPacs);
        request.addProperty("_RequestId", data.getId());
        request.addProperty("_RegistrationNo",regNo);
        request.addProperty("_Status","Y");

        DefaultResponse response;
        SoapObject res1;

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + AcceptRjctRecordsFromPacs, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            response = new DefaultResponse(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            //return "0";
            return null;
        }
        return response;

    }


    public static DefaultResponse UploadRejectedRecordsFromPacs(JobListEntity data, String regNo) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, AcceptRjctRecordsFromPacs);
        request.addProperty("_RequestId", data.getId());
        request.addProperty("_RegistrationNo",regNo);
        request.addProperty("_Status","R");

        DefaultResponse response;
        SoapObject res1;

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + AcceptRjctRecordsFromPacs, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            response = new DefaultResponse(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            //return "0";
            return null;
        }
        return response;

    }


    public static ArrayList<JobOfferPostedEntity> JobOfferPosted(String role, String orgid) {


        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_JOB_Offer_Posted_METHOD);

        request.addProperty("Role", role);
        request.addProperty("orgId", orgid);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, JobOfferPostedEntity.JobOffer_CLASS.getSimpleName(), JobOfferPostedEntity.JobOffer_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_JOB_Offer_Posted_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<JobOfferPostedEntity> pvmArrayList = new ArrayList<JobOfferPostedEntity>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    JobOfferPostedEntity panchayat = new JobOfferPostedEntity(final_object);
                    pvmArrayList.add(panchayat);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }


    public static ArrayList<BlockJobOfferPostedEntity> BlockJobOfferPosted(String distid,String orgid,String role) {


        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_JOB_Offer_block_Posted_METHOD);

        request.addProperty("DistCode", distid);
        request.addProperty("orgId", orgid);
        request.addProperty("Role", role);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, BlockJobOfferPostedEntity.BlockJobOffer_CLASS.getSimpleName(), BlockJobOfferPostedEntity.BlockJobOffer_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_JOB_Offer_block_Posted_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<BlockJobOfferPostedEntity> pvmArrayList = new ArrayList<BlockJobOfferPostedEntity>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    BlockJobOfferPostedEntity panchayat = new BlockJobOfferPostedEntity(final_object);
                    pvmArrayList.add(panchayat);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }



    public static ArrayList<AcptdRjctdJobOfferEntity> JobOfferAcptdRjctd(String distid,String blkid, String orgid, String role,String status,String serialno) {


        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_Acpt_Rjct_Job_By_Labour);

        request.addProperty("Distcode", distid);
        request.addProperty("BlockCode", blkid);
        request.addProperty("orgId", orgid);
        request.addProperty("Role", role);
        request.addProperty("Status", status);
        request.addProperty("SerialNo", serialno);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, AcptdRjctdJobOfferEntity.AcptdRjctdOffer_CLASS.getSimpleName(), AcptdRjctdJobOfferEntity.AcptdRjctdOffer_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_Acpt_Rjct_Job_By_Labour,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<AcptdRjctdJobOfferEntity> pvmArrayList = new ArrayList<AcptdRjctdJobOfferEntity>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    AcptdRjctdJobOfferEntity panchayat = new AcptdRjctdJobOfferEntity(final_object);
                    pvmArrayList.add(panchayat);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }

    public static ArrayList<WorkerModel> GetRequirmentData(String distcode, String blockcode, String panchayatID) {



        SoapObject request = new SoapObject(SERVICENAMESPACE, GetRequirmentData);


        request.addProperty("DistrictCode",distcode);
        request.addProperty("WorkId",blockcode);
        request.addProperty("OrgId",panchayatID);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    BenfiList.BENFICLASS.getSimpleName(), BenfiList.BENFICLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL,60000);
            androidHttpTransport.call(SERVICENAMESPACE + GetRequirmentData, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<WorkerModel> pvmArrayList = new ArrayList<WorkerModel>();
        if(TotalProperty>0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        WorkerModel state = new WorkerModel(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }
    public static ArrayList<WorkerModel> GetLoadLabourData(String distcode, String SkillId, String Experiance, String Gender) {



        SoapObject request = new SoapObject(SERVICENAMESPACE, GetLoadLabourData);


        request.addProperty("DistrictCode",distcode);
        request.addProperty("SkillId",SkillId);
        request.addProperty("Experiance",Experiance);
        request.addProperty("Gender",Gender);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    BenfiList.BENFICLASS.getSimpleName(), BenfiList.BENFICLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL,60000);
            androidHttpTransport.call(SERVICENAMESPACE + GetLoadLabourData, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<WorkerModel> pvmArrayList = new ArrayList<WorkerModel>();
        if(TotalProperty>0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        WorkerModel state = new WorkerModel(final_object,"1");
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }

    public static ArrayList<BlkCompanyJobDetailsEntity> BlkCompanyWiseJobOffers(String distid, String blkid, String orgid, String role) {


        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_Blk_Wise_company_Jobs);

        request.addProperty("Distcode", distid);
        request.addProperty("BlockCode", blkid);
        request.addProperty("orgId", orgid);
        request.addProperty("Role", role);

        SoapObject res1;
        try
        {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, BlkCompanyJobDetailsEntity.BlockCompanyJobs_CLASS.getSimpleName(), BlkCompanyJobDetailsEntity.BlockCompanyJobs_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_Blk_Wise_company_Jobs,envelope);

            res1 = (SoapObject) envelope.getResponse();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<BlkCompanyJobDetailsEntity> pvmArrayList = new ArrayList<BlkCompanyJobDetailsEntity>();

        for (int ii = 0; ii < TotalProperty; ii++)
        {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject)
                {
                    SoapObject final_object = (SoapObject) property;
                    BlkCompanyJobDetailsEntity panchayat = new BlkCompanyJobDetailsEntity(final_object);
                    pvmArrayList.add(panchayat);
                }
            }
//            {
//                return pvmArrayList;
//            }

        }


        return pvmArrayList;
    }

    public static String Registration_Mob(String mob,String otp) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,REGISTRATION_EMP_MOB_METHOD);


            request.addProperty("_MobileNo",mob);
            request.addProperty("_OTP",otp);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    EmpRegDetails.UserDetails_CLASS.getSimpleName(),
                    EmpRegDetails.UserDetails_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTRATION_EMP_MOB_METHOD,
                    envelope);

            Object result = envelope.getResponse();

            if (result != null) {
                // Log.d("", result.toString());

                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static DefaultResponse EmpRegistration(EmpRegDetails user) {
        SoapObject request = new SoapObject(SERVICENAMESPACE, EmployerRegistration_METHOD);
        request.addProperty("_CompanyName", user.getOrgName());
        request.addProperty("_CommpanyType", user.getOrgCode());
        request.addProperty("_DistCode", user.getDistCode());
        request.addProperty("_AddressName", user.getCrosspondance_address());
        request.addProperty("_CommpanyContactName", user.getContact_Person());
        request.addProperty("_ContactNo", user.getMobile_Number());
        request.addProperty("_OtherMobileNo", user.getAlternative_Mobile_Number());
        request.addProperty("_EmailId", user.getEmail());
        request.addProperty("_Password", user.getPassword());
        DefaultResponse userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, DefaultResponse.DefaultResponse_CLASS.getSimpleName(), DefaultResponse.DefaultResponse_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + EmployerRegistration_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new DefaultResponse(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userDetails;
//        try {
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                    SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            envelope.implicitTypes = true;
//            envelope.setOutputSoapObject(request);
//
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(
//                    SERVICEURL);
//            androidHttpTransport.call(SERVICENAMESPACE + UPLOAD_METHOD,
//                    envelope);
//            // res2 = (SoapObject) envelope.getResponse();
//            rest = envelope.getResponse().toString();
//
//            // rest=res2.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "0";
//        }
//        return rest;
    }


    public static ArrayList<DepartmentMaster> getDeptData() {

        SoapObject res1;
        res1=getServerData(Dept_METHOD, DepartmentMaster.DeptMaster_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<DepartmentMaster> fieldList = new ArrayList<DepartmentMaster>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    DepartmentMaster block= new DepartmentMaster(final_object);
                    fieldList.add(block);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }
}
