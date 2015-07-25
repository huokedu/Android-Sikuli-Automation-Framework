package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import aspirin.framework.core.pageobjects.hotspotshield.VLObjects;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

/**
 * Created by Artur on 7/9/2015.
 */
public class VLActions {

    public static final Integer JAPAN = 1;
    public static final Integer INDIA = 2;
    public static final Integer USA = 3;
    public static final Integer UK = 4;
    public static final Integer AUSTRALIA = 5;
    public static final Integer CANADA = 6;
    public static final Integer CHINA = 7;
    public static final Integer CZECH = 8;
    public static final Integer GERMANY = 9;
    public static final Integer DENMARK = 10;
    public static final Integer FRANCE = 11;
    public static final Integer HONGKONG = 12;
    public static final Integer NETHERLANDS = 13;
    public static final Integer RUSSIA = 14;
    public static final Integer SWEDEN = 15;
    public static final Integer TURKEY = 16;
    public static final Integer UKRAINE = 17;
    public static final Integer MEXICO = 18;

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static void changeCountry(Integer countryID) throws FindFailed {

        String colo;
        Boolean coloUS;
        Pattern activeCountry;
        Pattern flag;
        Boolean elite;

        // Checking if Elite or Free to set flags for Upgrade dialog
        if(SCREEN.exists(MainObjects.Main_TopBar_Elite_Text_Green(AP))!=null){
            elite = true;
        }else{
            elite = false;
        }

        // Prepare patterns to use based on the country desired
        if(countryID == USA){
            activeCountry = MainObjects.Main_Location_US(AP);
            flag = VLObjects.VirtualLocation_LargeFlag_US(AP);
            coloUS = true;
            colo = "USA";
        } else if(countryID == JAPAN){

            activeCountry = MainObjects.Main_Location_JP(AP);
            flag = VLObjects.VirtualLocation_LargeFlag_JP(AP);
            coloUS = false;
            colo = "Japan";
        } else if (countryID == UK){

            activeCountry = MainObjects.Main_Location_UK(AP);
            flag = VLObjects.VirtualLocation_LargeFlag_UK(AP);
            coloUS = false;
            colo = "UK";
        } else{
            throw new NullPointerException("[VLActions / changeCountry] Wrong COUNTRY ID! Got: " + countryID);
        }

        if(SCREEN.exists(MainObjects.Main_Location_VIA(AP)) != null){
            if(SCREEN.exists(activeCountry) == null){
                System.out.println("[VLActions / changeCountry] VL is not set to: " + colo.toUpperCase() + "Connecting to it now...");
                MainActions.openVLSelector();
                if(SCREEN.exists(flag)==null){
                    int ls = 5;
                    while(ls != 0 && SCREEN.exists(flag)==null){
                        SCREEN.wheel(1, 5);
                        ls--;
                    }
                }
                SCREEN.doubleClick(flag);
                    if(elite){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        try {
                            try{
                                SCREEN.wait(MainObjects.Main_NotProtected(AP), 20);
                            }catch (FindFailed e){
                                System.out.println("[VLActions / changeCountry] Could connect too quick to see Not Protected. Checking if connected to  " + colo.toUpperCase());
                                SCREEN.wait(activeCountry, 45);
                            }
                            Global.setVar_Colo(colo);
                            Global.setVar_ColoUs(coloUS);
                            System.out.println("[VLActions / changeCountry] Done!");
                        }catch (FindFailed e){
                            throw new FindFailed("[VLActions / changeCountry] FAILED to connect to " + colo.toUpperCase());
                        }
                    }else{
                        try{
                            SCREEN.wait(VLObjects.VirtualLocation_Dialog_Upgrade(AP), 10);
                        }catch (FindFailed e){
                            throw new FindFailed("Expected UPGRADE dialog upon VL change but did not get it! " + e);
                        }
                    }
            } else{
                System.out.println("[VLActions / changeCountry] VL already set to: " + colo.toUpperCase());
            }
        } else if(SCREEN.exists(MainObjects.Main_Location_VIA(AP)) == null){

        } else{

        }
    }
}
