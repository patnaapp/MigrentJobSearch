package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class AcptdRjctdJobOfferEntity implements KvmSerializable, Serializable {

    public static Class<AcptdRjctdJobOfferEntity> AcptdRjctdOffer_CLASS = AcptdRjctdJobOfferEntity.class;
    private String vchregnum = "";
    private String vchName = "";
    private String vchMobile = "";
    private String gender = "";
    private String vchGuardian_name = "";
    private String vchGuardian_number = "";
    private String SkillName = "";
    private String WorkSiteNameHn = "";


    public AcptdRjctdJobOfferEntity(SoapObject obj) {

        this.vchregnum = obj.getProperty("vchregnum").toString();
        this.vchName = obj.getProperty("vchName").toString();
        this.vchMobile = obj.getProperty("vchMobile").toString();
        this.gender = obj.getProperty("gender").toString();
        this.vchGuardian_name = obj.getProperty("vchGuardian_name").toString();
        this.vchGuardian_number = obj.getProperty("vchGuardian_number").toString();
        this.SkillName = obj.getProperty("SkillName").toString();
        this.WorkSiteNameHn = obj.getProperty("WorkSiteNameHn").toString();
        //this.skillName = obj.getProperty("SkillNameHn").toString();


    }


    public static Class<AcptdRjctdJobOfferEntity> getJobOffer_CLASS() {
        return AcptdRjctdOffer_CLASS;
    }

    public static void setJobOffer_CLASS(Class<AcptdRjctdJobOfferEntity> jobOffer_CLASS) {
        AcptdRjctdOffer_CLASS = jobOffer_CLASS;
    }

    public String getVchregnum() {
        return vchregnum;
    }

    public void setVchregnum(String vchregnum) {
        this.vchregnum = vchregnum;
    }

    public String getVchName() {
        return vchName;
    }

    public void setVchName(String vchName) {
        this.vchName = vchName;
    }

    public String getVchMobile() {
        return vchMobile;
    }

    public void setVchMobile(String vchMobile) {
        this.vchMobile = vchMobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVchGuardian_name() {
        return vchGuardian_name;
    }

    public void setVchGuardian_name(String vchGuardian_name) {
        this.vchGuardian_name = vchGuardian_name;
    }

    public String getVchGuardian_number() {
        return vchGuardian_number;
    }

    public void setVchGuardian_number(String vchGuardian_number) {
        this.vchGuardian_number = vchGuardian_number;
    }

    public String getSkillName() {
        return SkillName;
    }

    public String getWorkSiteNameHn() {
        return WorkSiteNameHn;
    }

    public void setWorkSiteNameHn(String workSiteNameHn) {
        WorkSiteNameHn = workSiteNameHn;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
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
}
