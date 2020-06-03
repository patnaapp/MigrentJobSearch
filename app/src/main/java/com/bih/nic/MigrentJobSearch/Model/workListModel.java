package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.SoapObject;

public class workListModel {
    
    private String Work_Id;
    private String Worl_Name;

    public workListModel(SoapObject final_object) {
    }

    public String getWork_Id() {
        return Work_Id;
    }

    public void setWork_Id(String work_Id) {
        Work_Id = work_Id;
    }

    public String getWorl_Name() {
        return Worl_Name;
    }

    public void setWorl_Name(String worl_Name) {
        Worl_Name = worl_Name;
    }
}
