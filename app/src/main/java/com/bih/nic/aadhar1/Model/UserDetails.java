package com.bih.nic.aadhar1.Model;

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
    private String Role="";
    private String EMEI;
    private String AadhaarNo;

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
        this.setAuthenticated(Boolean.parseBoolean(res1.getProperty(
                "isAuthenticated").toString()));
      //  this.IsAuth=res1.getProperty("IsAuthienticated").toString();
        this.DistCode=res1.getProperty("District_Code").toString();
        this.BlockCode=res1.getProperty("BlockCode").toString();
        this.DistName=res1.getProperty("district_name").toString();
        this.BlockName=res1.getProperty("Block_Name").toString();
        this.Role=res1.getProperty("UserType").toString();
        this.EMEI=res1.getProperty("IMEI").toString();
        this.UserName=res1.getProperty("Name").toString();
        this.AadhaarNo=res1.getProperty("Aadhaar").toString();

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
