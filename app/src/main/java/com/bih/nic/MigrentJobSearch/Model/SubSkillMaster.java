package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class SubSkillMaster implements KvmSerializable {

    public static Class<SubSkillMaster> SubSkillMaster_CLASS = SubSkillMaster.class;

    private String Id="";
    private String SkillName = "";
    private String SkillNameHn = "";
    private String SkillCategoryId = "";

    public SubSkillMaster(SoapObject res1) {
        this.Id=res1.getProperty("Id").toString();
        this.SkillName=res1.getProperty("SkillName").toString();
        this.SkillNameHn=res1.getProperty("SkillNameHn").toString();
        this.SkillCategoryId=res1.getProperty("SkillCategoryId").toString();
    }

    public SubSkillMaster() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSkillName() {
        return SkillName;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
    }

    public String getSkillNameHn() {
        return SkillNameHn;
    }

    public void setSkillNameHn(String skillNameHn) {
        SkillNameHn = skillNameHn;
    }

    public String getSkillCategoryId() {
        return SkillCategoryId;
    }

    public void setSkillCategoryId(String skillCategoryId) {
        SkillCategoryId = skillCategoryId;
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
