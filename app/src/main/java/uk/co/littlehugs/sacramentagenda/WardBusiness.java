package uk.co.littlehugs.sacramentagenda;

/**
 * Created by hugs on 12/02/16.
 */
public class WardBusiness {
    private String WARDBUSINESS_ID;
    private String WARDBUSINESSDATE;
    private String WARDBUSINESSPURPOSE;
    private String WARDBUSINESSNAME;
    private String WARDBUSINESSCALLING;
    private String WARDBUSINESSUPDATEDATE;
    private String WARDBUSINESSSTATUS;

    public WardBusiness()
   {
        WARDBUSINESS_ID = "0";
        WARDBUSINESSDATE = "";
        WARDBUSINESSPURPOSE = "";
        WARDBUSINESSNAME = "";
        WARDBUSINESSCALLING = "";
       WARDBUSINESSUPDATEDATE = "0";
       WARDBUSINESSSTATUS = "";
    }

    //for adding
    public WardBusiness(String wARDBUSINESS_ID, String wARDBUSINESSDATE, String wARDBUSINESSPURPOSE,
                        String wARDBUSINESSNAME, String wARDBUSINESSCALLING, String wARDBUSINESSUPDATEDATE, String wARDBUSINESSSTATUS) {
        WARDBUSINESS_ID = wARDBUSINESS_ID;
        WARDBUSINESSDATE = wARDBUSINESSDATE;
        WARDBUSINESSPURPOSE = wARDBUSINESSPURPOSE;
        WARDBUSINESSNAME = wARDBUSINESSNAME;
        WARDBUSINESSCALLING = wARDBUSINESSCALLING;
        WARDBUSINESSUPDATEDATE = wARDBUSINESSUPDATEDATE;
        WARDBUSINESSSTATUS = wARDBUSINESSSTATUS;
    }

    //for editing
    public WardBusiness(String wARDBUSINESSDATE, String wARDBUSINESSPURPOSE,
                        String wARDBUSINESSNAME, String wARDBUSINESSCALLING, String wARDBUSINESSUPDATEDATE, String wARDBUSINESSSTATUS) {
        WARDBUSINESSDATE = wARDBUSINESSDATE;
        WARDBUSINESSPURPOSE = wARDBUSINESSPURPOSE;
        WARDBUSINESSNAME = wARDBUSINESSNAME;
        WARDBUSINESSCALLING = wARDBUSINESSCALLING;
        WARDBUSINESSUPDATEDATE = wARDBUSINESSUPDATEDATE;
        WARDBUSINESSSTATUS = wARDBUSINESSSTATUS;
    }

    public String getWARDBUSINESS_ID() {return WARDBUSINESS_ID;}
    public String getWARDBUSINESSDATE() {return WARDBUSINESSDATE; }
    public String getWARDBUSINESSPURPOSE() {return WARDBUSINESSPURPOSE; }
    public String getWARDBUSINESSNAME() {return WARDBUSINESSNAME; }
    public String getWARDBUSINESSCALLING() {return WARDBUSINESSCALLING; }
    public String getWARDBUSINESSUPDATEDATE() {return WARDBUSINESSUPDATEDATE; }
    public String getWARDBUSINESSSTATUS() {return  WARDBUSINESSSTATUS; }

    public void setWARDBUSINESS_ID(String id) {WARDBUSINESS_ID=id;}
    public void setWARDBUSINESSDATE(String wARDBUSINESSDATE) { WARDBUSINESSDATE = wARDBUSINESSDATE; }
    public void setWARDBUSINESSPURPOSE(String wARDBUSINESSPURPOSE) { WARDBUSINESSPURPOSE = wARDBUSINESSPURPOSE; }
    public void setWARDBUSINESSNAME(String wARDBUSINESSNAME) { WARDBUSINESSNAME = wARDBUSINESSNAME; }
    public void setWARDBUSINESSCALLING(String wARDBUSINESSCALLING) { WARDBUSINESSCALLING = wARDBUSINESSCALLING; }
    public void setWARDBUSINESSUPDATEDATE(String wARDBUSINESSUPDATEDATE) {WARDBUSINESSUPDATEDATE = wARDBUSINESSUPDATEDATE; }
    public void setWARDBUSINESSSTATUS(String wARDBUSINESSSTATUS) {WARDBUSINESSSTATUS = wARDBUSINESSSTATUS;}
}