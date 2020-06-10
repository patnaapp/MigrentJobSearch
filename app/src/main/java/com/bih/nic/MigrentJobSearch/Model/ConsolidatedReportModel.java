package com.bih.nic.MigrentJobSearch.Model;

import org.ksoap2.serialization.SoapObject;

public class ConsolidatedReportModel {

    public static Class<ConsolidatedReportModel> ConsolidatedReptModel = ConsolidatedReportModel.class;

    private String TotalOrg;
    private String TotalWorkDet;
    private String DistrictName;
    private String DistrictCode;
    private String TotalWorkAccp;
    private String TotalWorkReject;
    private String TotalWorkPerRej;
    private String NoOfPerson;
    private String NoOfPersonApp;
    private String BlockCode;
    private String BlockName;

    public ConsolidatedReportModel(SoapObject final_object) {
        this.TotalOrg=final_object.getPrimitiveProperty("TotalOrg").toString();
        this.TotalWorkDet=final_object.getPrimitiveProperty("TotalWorkDet").toString();
        this.DistrictName=final_object.getPrimitiveProperty("DistrictName").toString();
        this.DistrictCode=final_object.getPrimitiveProperty("DistrictCode").toString();
        this.TotalWorkAccp=final_object.getPrimitiveProperty("TotalWorkAccp").toString();
        this.TotalWorkReject=final_object.getPrimitiveProperty("TotalWorkReject").toString();
        this.TotalWorkPerRej=final_object.getPrimitiveProperty("TotalWorkPerRej").toString();
        this.NoOfPerson=final_object.getPrimitiveProperty("NoOfPerson").toString();
        this.NoOfPersonApp=final_object.getPrimitiveProperty("NoOfPersonApp").toString();

    }

    public ConsolidatedReportModel(SoapObject final_object, int i) {
        this.TotalWorkDet=final_object.getPrimitiveProperty("TotalWorkDet").toString();
        this.BlockCode=final_object.getPrimitiveProperty("BlockCode").toString();
        this.BlockName=final_object.getPrimitiveProperty("BlockName").toString();
        this.TotalWorkAccp=final_object.getPrimitiveProperty("TotalWorkDetApproved").toString();
        this.TotalWorkReject=final_object.getPrimitiveProperty("TotalWorkDetRject").toString();
        this.TotalWorkPerRej=final_object.getPrimitiveProperty("TotalWorkDetPerRject").toString();
        this.NoOfPerson=final_object.getPrimitiveProperty("NoOfPerson").toString();
        this.NoOfPersonApp=final_object.getPrimitiveProperty("NoOfPersonApp").toString();
    }

    public String getTotalOrg() {
        return TotalOrg;
    }

    public void setTotalOrg(String totalOrg) {
        TotalOrg = totalOrg;
    }

    public String getTotalWorkDet() {
        return TotalWorkDet;
    }

    public void setTotalWorkDet(String totalWorkDet) {
        TotalWorkDet = totalWorkDet;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getDistrictCode() {
        return DistrictCode;
    }

    public void setDistrictCode(String districtCode) {
        DistrictCode = districtCode;
    }

    public String getTotalWorkAccp() {
        return TotalWorkAccp;
    }

    public void setTotalWorkAccp(String totalWorkAccp) {
        TotalWorkAccp = totalWorkAccp;
    }

    public String getTotalWorkReject() {
        return TotalWorkReject;
    }

    public void setTotalWorkReject(String totalWorkReject) {
        TotalWorkReject = totalWorkReject;
    }

    public String getTotalWorkPerRej() {
        return TotalWorkPerRej;
    }

    public void setTotalWorkPerRej(String totalWorkPerRej) {
        TotalWorkPerRej = totalWorkPerRej;
    }

    public String getNoOfPerson() {
        return NoOfPerson;
    }

    public void setNoOfPerson(String noOfPerson) {
        NoOfPerson = noOfPerson;
    }

    public String getNoOfPersonApp() {
        return NoOfPersonApp;
    }

    public void setNoOfPersonApp(String noOfPersonApp) {
        NoOfPersonApp = noOfPersonApp;
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
}
