package br.edu.insper.al.gabrielamb2.projeto2;

import java.util.HashMap;

public class PartPriceConfig {
    private HashMap<String, HashMap>materiais;

    private HashMap<String, Object>ABS;
    private HashMap<String, Object>PLA;
    private HashMap<String, Object>PC;
    private HashMap<String, Object>Nylon;
    private HashMap<String, Object> LayWood;
    private HashMap<String, Object>BendLAY;
    private HashMap<String, Object>TPE;
    private HashMap<String, Object>SoftPLA;
    private HashMap<String, Object>HIPS;

    private HashMap<String,String>printingCost;

    private HashMap<String, Double>multipliers;

    private HashMap<String, HashMap<String, Object>>deliveryCosts;

    private HashMap<String, String[]>slicerParams;

    private HashMap<String, HashMap<String, Object>>layerHeights;

    private HashMap<String, HashMap<String, Object>>printSpeeds;

    public PartPriceConfig(){
        materiais = new HashMap<>();
        ABS = new HashMap<>();
        PLA = new HashMap<>();
        PC = new HashMap<>();
        Nylon = new HashMap<>();
        LayWood = new HashMap<>();
        TPE = new HashMap<>();
        BendLAY = new HashMap<>();
        SoftPLA = new HashMap<>();
        HIPS = new HashMap<>();

        String[] colors = {"#000000","#FFFFFF","#FFFAE0","#FF0F0F","#FF8324",
                "#FFA8C8","#F7FF00","#70FF33","#140AA3",
                "#8921FF","#9291B5","#87593E"};

        HashMap<String, Object> priceABS = new HashMap();
        priceABS.put("amount", 0.20);
        priceABS.put("unit", "USD/g");

        HashMap<String, Object> densityABS = new HashMap();
        densityABS.put("amount", 1.04);
        densityABS.put("unit", "g/cm^3");

        ABS.put("fullname", "Acrylonitrile Butadiene Styrene");
        ABS.put("price", priceABS);
        ABS.put("canBeVaporPolished", true);
        ABS.put("density", densityABS);
        ABS.put("colors", colors);

        //---------------------------------------------------------

        String[] colors_PLA = {"#000000","#FFFFFF","#FFFAE0","#FF0F0F",
                "#FF8324","#FFA8C8","#F7FF00",
                "#70FF33","#140AA3","#8921FF","#9291B5","#87593E"};

        HashMap<String, Object> pricePLA = new HashMap();
        pricePLA.put("amount", 0.25);
        pricePLA.put("unit", "USD/g");

        HashMap<String, Object> densityPLA = new HashMap();
        densityPLA.put("amount", 1.25);
        densityPLA.put("unit", "g/cm^3");

        PLA.put("fullname", "Polylactic acid");
        PLA.put("price", pricePLA);
        PLA.put("canBeVaporPolished", true);
        PLA.put("density", densityPLA);
        PLA.put("colors", colors_PLA);

        //---------------------------------------------------------

        String[] colors_PC = {"#000000","#FFFFFF","#FFFAE0","#FF0F0F",
                "#FF8324","#FFA8C8","#F7FF00","#70FF33",
                "#140AA3","#8921FF","#9291B5","#87593E"};

        HashMap<String, Object> pricePC = new HashMap();
        pricePC.put("amount", 0.60);
        pricePC.put("unit", "USD/g");

        HashMap<String, Object> densityPC = new HashMap();
        densityPC.put("amount", 1.20);
        densityPC.put("unit", "g/cm^3");

        PC.put("fullname", "Polycarbonate");
        PC.put("price", pricePC);
        PC.put("canBeVaporPolished", true);
        PC.put("density", densityPC);
        PC.put("colors", colors_PC);

        //---------------------------------------------------------

        String[] colors_Nylon = {"#000000","#FFFFFF","#FF0F0F",
                "#70FF33","#140AA3","clear"};

        HashMap<String, Object> priceNylon = new HashMap();
        priceNylon.put("amount", 0.35);
        priceNylon.put("unit", "USD/g");

        HashMap<String, Object> densityNylon = new HashMap();
        densityNylon.put("amount", 1.25);
        densityNylon.put("unit", "g/cm^3");

        Nylon.put("fullname", null);
        Nylon.put("price", priceNylon);
        Nylon.put("canBeVaporPolished", false);
        Nylon.put("density", densityNylon);
        Nylon.put("colors", colors_Nylon);

        //---------------------------------------------------------

        String[] colors_LayWood = {"#FFFFFF"};

        HashMap<String, Object> priceLayWood = new HashMap();
        priceLayWood.put("amount", 0.80);
        priceLayWood.put("unit", "USD/g");

        HashMap<String, Object> densityLayWood = new HashMap();
        densityLayWood.put("amount", 1.05);
        densityLayWood.put("unit", "g/cm^3");

        LayWood.put("fullname", null);
        LayWood.put("price", priceLayWood);
        LayWood.put("canBeVaporPolished", false);
        LayWood.put("density", densityLayWood);
        LayWood.put("colors", colors_LayWood);

        //---------------------------------------------------------

        String[] colors_BendLAY = {"#87593E"};

        HashMap<String, Object> priceBendLAY = new HashMap();
        priceBendLAY.put("amount", 0.50);
        priceBendLAY.put("unit", "USD/g");

        HashMap<String, Object> densityBendLAY = new HashMap();
        densityBendLAY.put("amount", 1.02);
        densityBendLAY.put("unit", "g/cm^3");

        BendLAY.put("fullname", null);
        BendLAY.put("price", priceBendLAY);
        BendLAY.put("canBeVaporPolished", true);
        BendLAY.put("density", densityBendLAY);
        BendLAY.put("colors", colors_BendLAY);

        //---------------------------------------------------------

        String[] colors_TPE = {"clear"};

        HashMap<String, Object> priceTPE = new HashMap();
        priceTPE.put("amount", 0.60);
        priceTPE.put("unit", "USD/g");

        HashMap<String, Object> densityTPE = new HashMap();
        densityTPE.put("amount", 1.10);
        densityTPE.put("unit", "g/cm^3");

        TPE.put("fullname", null);
        TPE.put("price", priceTPE);
        TPE.put("canBeVaporPolished", false);
        TPE.put("density", densityTPE);
        TPE.put("colors", colors_TPE);

        //---------------------------------------------------------

        String[] colors_SoftPLA = {"#000000","#FF0F0F","#140AA3","#FFFFFF"};

        HashMap<String, Object> priceSoftPLA = new HashMap();
        priceSoftPLA.put("amount", 0.50);
        priceSoftPLA.put("unit", "USD/g");

        HashMap<String, Object> densitySoftPLA = new HashMap();
        densitySoftPLA.put("amount", 1.15);
        densitySoftPLA.put("unit", "g/cm^3");

        SoftPLA.put("fullname", null);
        SoftPLA.put("price", priceSoftPLA);
        SoftPLA.put("canBeVaporPolished", false);
        SoftPLA.put("density", densitySoftPLA);
        SoftPLA.put("colors", colors_SoftPLA);

        //---------------------------------------------------------

        String[] colors_HIPS = {"#FFFAE0"};

        HashMap<String, Object> priceSoftHIPS = new HashMap();
        priceSoftHIPS.put("amount", 0.20);
        priceSoftHIPS.put("unit", "USD/g");

        HashMap<String, Object> densitySoftHIPS = new HashMap();
        densitySoftHIPS.put("amount", 1.06);
        densitySoftHIPS.put("unit", "g/cm^3");

        HIPS.put("fullname", null);
        HIPS.put("price", priceSoftHIPS);
        HIPS.put("canBeVaporPolished", true);
        HIPS.put("density", densitySoftHIPS);
        HIPS.put("colors", colors_HIPS);

        materiais.put("ABS", ABS);
        materiais.put("PLA", PLA);
        materiais.put("PC", PC);
        materiais.put("Nylon", Nylon);
        materiais.put("LayWood", LayWood);
        materiais.put("BendLAY", BendLAY);
        materiais.put("TPE", TPE);
        materiais.put("SoftPLA", SoftPLA);
        materiais.put("HIPS", HIPS);

        //------PRINTING COST-----------------------------------

        printingCost = new HashMap<>();
        printingCost.put("amount", "4.00");
        printingCost.put("unit", "USD/hour");

        //------MULTIPLIERS------------------------------------

        multipliers = new HashMap<>();
        multipliers.put("supportRemovalMultiplier", 1.33);
        multipliers.put("vaporPolishingMultiplier", 1.25);
        multipliers.put("rushPrintingMultiplier", 1.50);

        //------DELIVERY COSTS------------------------------------

        deliveryCosts= new HashMap<>();
        HashMap<String, Object>base = new HashMap<>();
        base.put("amount", 5.80);
        base.put("unit", "USD");

        HashMap<String, Object>wightPrice = new HashMap<>();
        base.put("amount", 0.01);
        base.put("unit", "USD/g");

        deliveryCosts.put("base", base);
        deliveryCosts.put("weightPrice", wightPrice);

        //------SLICER PARAMS------------------------------------

        slicerParams = new HashMap<>();

        String[] cura = {"cura"};
        slicerParams.put("slicers", cura);

        //------LAYER HEIGHTS------------------------------------

        layerHeights = new HashMap<>();
        HashMap<String, Object>Default = new HashMap<>();
        Default.put("amount", "0.254");
        Default.put("unit", "mm");

        HashMap<String, Object>min = new HashMap<>();
        min.put("amount", 0.075);
        min.put("unit", "mm");

        HashMap<String, Object>max = new HashMap<>();
        max.put("amount", 0.4);
        max.put("unit", "mm");

        //------PRINT SPEED------------------------------------

        printSpeeds = new HashMap<>();

        HashMap<String, Object>Default_speed = new HashMap<>();
        Default_speed.put("amount", 50);
        Default_speed.put("unit", "mm");

        printSpeeds.put("default", Default_speed);
    }


    public HashMap<String, HashMap> getMateriais() {return materiais;}

    public HashMap<String, String> getPrintingCost() {return printingCost;}

    public HashMap<String, Double> getMultipliers() {return multipliers;}

    public HashMap<String, HashMap<String, Object>> getDeliveryCosts() {return deliveryCosts; }

    public HashMap<String, String[]> getSlicerParams() {return slicerParams;}

    public HashMap<String, HashMap<String, Object>> getLayerHeights() {return layerHeights;}

    public HashMap<String, HashMap<String, Object>> getPrintSpeeds() {return printSpeeds;}
}
