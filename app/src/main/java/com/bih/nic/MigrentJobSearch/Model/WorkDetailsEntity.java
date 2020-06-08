package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class WorkDetailsEntity implements KvmSerializable, Serializable {
    public static Class<WorkDetailsEntity> Work_details_CLASS = WorkDetailsEntity.class;
    private String Fin_yr="";
    private String related_dept="";
    private String dist_id="";
    private String dist_name="";
    private String blk_id="";
    private String blk_name="";
    private String work_site_eng="";
    private String work_site_hn="";
    private String Location_en ="";
    private String Location_hn="";
    private String pincode="";
    private String supervisor_nm_en="";
    private String supervisor_nm_hn="";
    private String supervisor_mob="";


    public WorkDetailsEntity() {

    }

    public WorkDetailsEntity(SoapObject res1) {
//        this.Password=res1.getProperty("Password").toString();
//        this.DistrictCode=res1.getProperty("DistrictCode").toString();
//        this.DistrictName=res1.getProperty("DistrictName").toString();

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

    public static Class<WorkDetailsEntity> getWork_details_CLASS() {
        return Work_details_CLASS;
    }

    public static void setWork_details_CLASS(Class<WorkDetailsEntity> work_details_CLASS) {
        Work_details_CLASS = work_details_CLASS;
    }

    public String getFin_yr() {
        return Fin_yr;
    }

    public void setFin_yr(String fin_yr) {
        Fin_yr = fin_yr;
    }

    public String getRelated_dept() {
        return related_dept;
    }

    public void setRelated_dept(String related_dept) {
        this.related_dept = related_dept;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getBlk_id() {
        return blk_id;
    }

    public void setBlk_id(String blk_id) {
        this.blk_id = blk_id;
    }

    public String getBlk_name() {
        return blk_name;
    }

    public void setBlk_name(String blk_name) {
        this.blk_name = blk_name;
    }

    public String getWork_site_eng() {
        return work_site_eng;
    }

    public void setWork_site_eng(String work_site_eng) {
        this.work_site_eng = work_site_eng;
    }

    public String getWork_site_hn() {
        return work_site_hn;
    }

    public void setWork_site_hn(String work_site_hn) {
        this.work_site_hn = work_site_hn;
    }

    public String getLocation_en() {
        return Location_en;
    }

    public void setLocation_en(String location_en) {
        Location_en = location_en;
    }

    public String getLocation_hn() {
        return Location_hn;
    }

    public void setLocation_hn(String location_hn) {
        Location_hn = location_hn;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSupervisor_nm_en() {
        return supervisor_nm_en;
    }

    public void setSupervisor_nm_en(String supervisor_nm_en) {
        this.supervisor_nm_en = supervisor_nm_en;
    }

    public String getSupervisor_nm_hn() {
        return supervisor_nm_hn;
    }

    public void setSupervisor_nm_hn(String supervisor_nm_hn) {
        this.supervisor_nm_hn = supervisor_nm_hn;
    }

    public String getSupervisor_mob() {
        return supervisor_mob;
    }

    public void setSupervisor_mob(String supervisor_mob) {
        this.supervisor_mob = supervisor_mob;
    }
}
