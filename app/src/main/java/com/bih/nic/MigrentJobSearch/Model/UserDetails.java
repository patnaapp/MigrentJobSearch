package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class UserDetails implements KvmSerializable {
    private static Class<UserDetails> USER_CLASS = UserDetails.class;
    private boolean isAuthenticated = false;
    private String IsAuth="";
    private String UserName = "";
    private String _UserId="";
    private String _Passwoed="";
    private String DistCode="";
    private String DistName="";
    private String BlockCode="";
    private String BlockName="";
    private String PanchayatName="";
    private String PanchayatCode="";
    private String Age="";
    private String MobileNo="";
    private String Address="";
    private String Role="";
    private String Message="";
    private String EMEI;
    private String AadhaarNo;
    private String profileImg="";

    public UserDetails() {

    }
   // public static Class<UserDetails> getdata = BenfiList.class;
    public static Class<UserDetails> getUserClass() {
        return USER_CLASS;
    }


   /* public UserDetails(SoapObject res1) {
        this.setAuthenticated(Boolean.parseBoolean(res1.getProperty(
                "isAuthenticated").toString()));
        this._UserId=res1.getProperty("UserID").toString();
        this.DistCode=res1.getProperty("DistrictId").toString();
        this.BlockCode=res1.getProperty("BlockCode").toString();
        this.DistName=res1.getProperty("_District_name").toString();
        this.BlockName=res1.getProperty("_Block_Name").toString();
        this.Role=res1.getProperty("Role").toString();

    }*/
 public UserDetails(SoapObject res1) {
        this.setAuthenticated(Boolean.parseBoolean(res1.getProperty("Status").toString()));

        this.DistCode=res1.getProperty("DistrictCode").toString();
        this.DistName=res1.getProperty("DistrictName").toString();
        this.BlockCode=res1.getProperty("BlockCode").toString();
        this.BlockName=res1.getProperty("BlockName").toString();
        this.PanchayatCode=res1.getProperty("PanchayatCode").toString();
        this.PanchayatName=res1.getProperty("PanchayatName").toString();
        this.Message=res1.getProperty("msg").toString();
        this._UserId=res1.getProperty("vchRegNum").toString();
        this.UserName=res1.getProperty("vchName").toString();
        this.Age=res1.getProperty("intAge").toString();
        this.MobileNo=res1.getProperty("vchMobile").toString();
        this.Address=res1.getProperty("vchAddress").toString();

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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String get_UserId() {
        return _UserId;
    }

    public void set_UserId(String _UserId) {
        this._UserId = _UserId;
    }

    public String get_Passwoed() {
        return _Passwoed;
    }

    public void set_Passwoed(String _Passwoed) {
        this._Passwoed = _Passwoed;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getEMEI() {
        return EMEI;
    }

    public void setEMEI(String EMEI) {
        this.EMEI = EMEI;
    }

    public String getAadhaarNo() {
        return AadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        AadhaarNo = aadhaarNo;
    }

    public String getIsAuth() {
        return IsAuth;
    }

    public void setIsAuth(String isAuth) {
        IsAuth = isAuth;
    }
}
