package br.edu.insper.al.gabrielamb2.projeto2;

import java.util.HashMap;

public class PartPriceConfig {

    private String ABS_color;
    private String PLA_color;
    private String PC_color;
    private String LayWood_color;
    private String BendLAY_color;
    private String TPE_color;
    private String SoftPLA_color;
    private String HIPS_color;

    private String ABS_density;
    private String PLA_density;
    private String PC_density;
    private String LayWood_density;
    private String BendLAY_density;
    private String TPE_density;
    private String SoftPLA_density;
    private String HIPS_density;

    private String ABS_PK;
    private String PLA_PK;
    private String PC_PK;
    private String LayWood_PK;
    private String BendLAY_PK;
    private String TPE_PK;
    private String SoftPLA_PK;
    private String HIPS_PK;

    public PartPriceConfig(){

        ABS_color = "#000000";
        ABS_density = "1.04";
        ABS_PK = "350.00";

        //---------------------------------------------------------

        PLA_color = "#000000";
        PLA_density = "1.25";
        PLA_PK = "139.00";

        //---------------------------------------------------------

        PC_color = "#000000";
        PC_density = "1.20";
        PC_PK = "250.00";

        //---------------------------------------------------------

        LayWood_color = "#FFFFFF";
        LayWood_density = "1.05";
        LayWood_PK = "166.00";

        //---------------------------------------------------------

        BendLAY_color = "#87593E";
        BendLAY_density = "1.02";
        BendLAY_PK = "684";

        //---------------------------------------------------------

        TPE_color = "clear";
        TPE_density = "1.10";
        TPE_PK = "200.00";

        //---------------------------------------------------------

        SoftPLA_color = "#000000";
        SoftPLA_density = "1.15";
        SoftPLA_PK = "190.00";

        //---------------------------------------------------------

        HIPS_color = "#FFFAE0";
        HIPS_density = "1.06";
        HIPS_PK = "120.00";


    }


    public String getABS_color() { return ABS_color; }

    public String getPLA_color() { return PLA_color; }

    public String getPC_color() { return PC_color; }

    public String getLayWood_color() { return LayWood_color; }

    public String getBendLAY_color() { return BendLAY_color; }

    public String getTPE_color() { return TPE_color; }

    public String getSoftPLA_color() { return SoftPLA_color; }

    public String getHIPS_color() { return HIPS_color; }

    public String getABS_density() { return ABS_density; }

    public String getPLA_density() { return PLA_density; }

    public String getPC_density() { return PC_density; }

    public String getLayWood_density() { return LayWood_density; }

    public String getBendLAY_density() { return BendLAY_density; }

    public String getTPE_density() { return TPE_density; }

    public String getSoftPLA_density() { return SoftPLA_density; }

    public String getHIPS_density() { return HIPS_density; }

    public String getABS_PK() { return ABS_PK; }

    public String getPLA_PK() { return PLA_PK; }

    public String getPC_PK() { return PC_PK; }

    public String getLayWood_PK() { return LayWood_PK; }

    public String getBendLAY_PK() { return BendLAY_PK; }

    public String getTPE_PK() { return TPE_PK; }

    public String getSoftPLA_PK() { return SoftPLA_PK; }

    public String getHIPS_PK() { return HIPS_PK; }
}
