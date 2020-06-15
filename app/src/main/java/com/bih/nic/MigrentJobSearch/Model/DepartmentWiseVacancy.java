package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class DepartmentWiseVacancy implements KvmSerializable {

    public static Class<DepartmentWiseVacancy> DepartmentWiseVacancy_CLASS = DepartmentWiseVacancy.class;

    private String DeptId="";
    private String DeptName = "";
    private String DeptTotalWork = "";
    private String DeptTotalRequirement = "";

    public DepartmentWiseVacancy(SoapObject res1) {
        //this.DeptId=res1.getProperty("DeptId").toString();
        this.DeptName=res1.getProperty("DeptName").toString();
        this.DeptTotalWork=res1.getProperty("DeptTotalWork").toString();
        this.DeptTotalRequirement=res1.getProperty("DeptTotalRequirement").toString();
    }

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String deptId) {
        DeptId = deptId;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getDeptTotalWork() {
        return DeptTotalWork;
    }

    public void setDeptTotalWork(String deptTotalWork) {
        DeptTotalWork = deptTotalWork;
    }

    public String getDeptTotalRequirement() {
        return DeptTotalRequirement;
    }

    public void setDeptTotalRequirement(String deptTotalRequirement) {
        DeptTotalRequirement = deptTotalRequirement;
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
