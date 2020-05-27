package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class JobListEntity implements KvmSerializable, Serializable {

    public static Class<JobListEntity> JobListEntity_CLASS = JobListEntity.class;
    private String id = "";
    private String workSite = "";
    private String skillCategory = "";
    private String skillName = "";
    private String numberOfPerson = "";
    private String experience = "";
    private String experienceMax = "";
    private String salary = "";
    private String salaryMax = "";
    private String startDate = "";
    private String gendar = "";
    private String district = "";
    private String block = "";
    private String contactNumber = "";
    private String contactName = "";

    public JobListEntity(SoapObject obj) {

        this.district = obj.getProperty("DistrictName").toString();
        this.block = obj.getProperty("BlockName").toString();
        this.skillCategory = obj.getProperty("SkillCategoryHn").toString();
        this.skillName = obj.getProperty("SkillNameHn").toString();
        this.experience = obj.getProperty("Experiance").toString();
        this.experienceMax = obj.getProperty("ExperianceMax").toString();
        this.salary = obj.getProperty("Salary1").toString();
        this.salaryMax = obj.getProperty("SalaryMax").toString();
        this.startDate = obj.getProperty("StartDate").toString();
        this.gendar = obj.getProperty("Gender").toString();
        this.numberOfPerson = obj.getProperty("NoOfPerson").toString();
        this.workSite = obj.getProperty("WorkSiteNameHn1").toString();
        this.contactNumber = obj.getProperty("CPMobileNo").toString();
        this.contactName = obj.getProperty("ContactPersonHn").toString();

    }

    public static Class<JobListEntity> getJobListEntity_CLASS() {
        return JobListEntity_CLASS;
    }

    public static void setJobListEntity_CLASS(Class<JobListEntity> jobListEntity_CLASS) {
        JobListEntity_CLASS = jobListEntity_CLASS;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getWorkSite() {
        return workSite;
    }

    public void setWorkSite(String workSite) {
        this.workSite = workSite;
    }

    public String getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(String skillCategory) {
        this.skillCategory = skillCategory;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(String numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExperienceMax() {
        return experienceMax;
    }

    public void setExperienceMax(String experienceMax) {
        this.experienceMax = experienceMax;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(String salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getGendar() {
        return gendar;
    }

    public void setGendar(String gendar) {
        this.gendar = gendar;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public JobListEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
