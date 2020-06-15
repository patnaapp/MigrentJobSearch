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

    private String DeptOrgId = "";
    private String Dept_orgname = "";
    private String Dept_orgTotalWork = "";
    private String Dept_orgTotlWorkReq = "";

    public DepartmentWiseVacancy(SoapObject res1,String i) {
        if(i.equalsIgnoreCase("1")) {
            this.DeptId = res1.getProperty("DeptID").toString();
            this.DeptName = res1.getProperty("DeptName").toString();
            this.DeptTotalWork = res1.getProperty("DeptTotalWork").toString();
            this.DeptTotalRequirement = res1.getProperty("DeptTotalRequirement").toString();
        }else if(i.equalsIgnoreCase("2")) {
            this.DeptOrgId = res1.getProperty("DeptOrgId").toString();
            this.Dept_orgname = res1.getProperty("Dept_orgname").toString();
            this.Dept_orgTotalWork = res1.getProperty("Dept_orgTotalWork").toString();
            this.Dept_orgTotlWorkReq = res1.getProperty("Dept_orgTotlWorkReq").toString();
        }
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

    public String getDeptOrgId() {
        return DeptOrgId;
    }

    public void setDeptOrgId(String deptOrgId) {
        DeptOrgId = deptOrgId;
    }

    public String getDept_orgname() {
        return Dept_orgname;
    }

    public void setDept_orgname(String dept_orgname) {
        Dept_orgname = dept_orgname;
    }

    public String getDept_orgTotalWork() {
        return Dept_orgTotalWork;
    }

    public void setDept_orgTotalWork(String dept_orgTotalWork) {
        Dept_orgTotalWork = dept_orgTotalWork;
    }

    public String getDept_orgTotlWorkReq() {
        return Dept_orgTotlWorkReq;
    }

    public void setDept_orgTotlWorkReq(String dept_orgTotlWorkReq) {
        Dept_orgTotlWorkReq = dept_orgTotlWorkReq;
    }
}
