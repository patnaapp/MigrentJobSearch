package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class WorkSiteEmployeeReportEntity implements KvmSerializable, Serializable {
    public static Class<WorkSiteEmployeeReportEntity> WorkSiteEmployeeReportEntity_CLASS = WorkSiteEmployeeReportEntity.class;

    private String WorksRegId="";
    private String WorksId="";
    private String WorkSiteName="";
    private String SkillCategory="";
    private String SkillName="";
    private String NoOfPerson="";
    private String ttlReg="";
    private String TotalJobOffer="";
    private String ttlRegA ="";
    private String ttlRegR="";
    private String ttlPendinAppoint="";
    private String ttlAppointed="";
    private String supervisor_nm_hn="";
    private String supervisor_mob="";


    public WorkSiteEmployeeReportEntity() {

    }

    public WorkSiteEmployeeReportEntity(SoapObject res1, String status)
    {

        this.WorksRegId=res1.getProperty("WorksRegId").toString();
        this.WorksId=res1.getProperty("WorksId").toString();
        this.WorkSiteName=res1.getProperty("WorkSiteName").toString();
        this.SkillCategory=res1.getProperty("SkillCategory").toString();
        this.SkillName=res1.getProperty("SkillName").toString();
        this.NoOfPerson=res1.getProperty("NoOfPerson").toString();
        this.ttlReg=res1.getProperty("ttlReg").toString();
        this.TotalJobOffer=res1.getProperty("TotalJobOffer").toString();
        this.ttlRegA=res1.getProperty("ttlRegA").toString();
        this.ttlRegR=res1.getProperty("ttlRegR").toString();
        this.ttlPendinAppoint=res1.getProperty("ttlPendinAppoint").toString();
        this.ttlAppointed=res1.getProperty("ttlAppointed").toString();

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

    public String getWorksRegId() {
        return WorksRegId;
    }

    public void setWorksRegId(String worksRegId) {
        WorksRegId = worksRegId;
    }

    public String getWorksId() {
        return WorksId;
    }

    public void setWorksId(String worksId) {
        WorksId = worksId;
    }

    public String getWorkSiteName() {
        return WorkSiteName;
    }

    public void setWorkSiteName(String workSiteName) {
        WorkSiteName = workSiteName;
    }

    public String getSkillCategory() {
        return SkillCategory;
    }

    public void setSkillCategory(String skillCategory) {
        SkillCategory = skillCategory;
    }

    public String getSkillName() {
        return SkillName;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
    }

    public String getNoOfPerson() {
        return NoOfPerson;
    }

    public void setNoOfPerson(String noOfPerson) {
        NoOfPerson = noOfPerson;
    }

    public String getTtlReg() {
        return ttlReg;
    }

    public void setTtlReg(String ttlReg) {
        this.ttlReg = ttlReg;
    }

    public String getTotalJobOffer() {
        return TotalJobOffer;
    }

    public void setTotalJobOffer(String totalJobOffer) {
        TotalJobOffer = totalJobOffer;
    }

    public String getTtlRegA() {
        return ttlRegA;
    }

    public void setTtlRegA(String ttlRegA) {
        this.ttlRegA = ttlRegA;
    }

    public String getTtlRegR() {
        return ttlRegR;
    }

    public void setTtlRegR(String ttlRegR) {
        this.ttlRegR = ttlRegR;
    }

    public String getTtlPendinAppoint() {
        return ttlPendinAppoint;
    }

    public void setTtlPendinAppoint(String ttlPendinAppoint) {
        this.ttlPendinAppoint = ttlPendinAppoint;
    }

    public String getTtlAppointed() {
        return ttlAppointed;
    }

    public void setTtlAppointed(String ttlAppointed) {
        this.ttlAppointed = ttlAppointed;
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
