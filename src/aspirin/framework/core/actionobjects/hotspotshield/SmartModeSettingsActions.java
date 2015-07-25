package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.core.pageobjects.hotspotshield.SmartModeSettingsObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

/**
 * Created by Artur on 7/9/2015.
 */
public class SmartModeSettingsActions {

    public static final Integer TO_FULL = 1;
    public static final Integer TO_SELECTED_SITES = 2;
    public static final Integer TO_OFF = 3;
    public static final Integer TO_DEFAULT = 4;
    public static final Integer SAFE_NETWORK = 1;
    public static final Integer UNSAFE_NETWORK = 2;
    public static final Integer MOBILE_NETWORK = 3;
    public static final Integer CURRENT_NETWORK = 4;
    public static final Integer OFF_SET = 150;

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;
    private static final Integer WAIT_VANISH = 5;


    //Boolean setMoreOptions argument is there to enhance execution speed in test cases where multiple options are changed one after another
    //The last use of this method should always set setMoreOptions to false
    //This method need to be used either from the Main activity or from the Smart Settings menu
    public static void setSmartModeFor(Integer NETWORK_ID, Integer MODE_ID, Boolean setMoreOptions) throws FindFailed{

        SCREEN.setAutoWaitTimeout(0.3);
        if(SCREEN.exists(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP)) != null){
            System.out.println("[setSmartModeFor] SMART SETTINGS already OPEN!");
        }else if(SCREEN.exists(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP))!=null){
            System.out.println("[setSmartModeFor] SMART SETTINGS SELECTION is OPEN! Backing up...");
            SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
        }else{
            MenuActions.openSmartModeSettings();
        }
        SCREEN.setAutoWaitTimeout(1.5);

        // Make region object based on the network we are setting the options for
        Region region;
        String network;
        if(NETWORK_ID == UNSAFE_NETWORK){
            region = SCREEN.find(SmartModeSettingsObjects.UNSAFE_NETWORK(0.80f)).right(OFF_SET);
            network = "UNSAFE NETWORK";
        }
        else if(NETWORK_ID == SAFE_NETWORK){
            region = SCREEN.find(SmartModeSettingsObjects.SAFE_NETWORK(0.80f)).right(OFF_SET);
            network = "SAFE NETWORK";
        }
        else if(NETWORK_ID == MOBILE_NETWORK){
            region = SCREEN.find(SmartModeSettingsObjects.MOBILE_NETWORK(0.80f)).right(OFF_SET);
            network = "MOBILE NETWORK";
        }else if(NETWORK_ID == CURRENT_NETWORK){
            region = SCREEN.find(SmartModeSettingsObjects.CURRENT_NETWORK(0.80f)).right(OFF_SET).grow(-140,145,50,15);
            network = "CURRENT NETWORK";
        }else{
            throw new NullPointerException("[setSmartModeFor] Wrong NETWORK ID was passed in: " + NETWORK_ID);
        }

        region.setAutoWaitTimeout(0.5);
        if(MODE_ID == TO_FULL){

            try{
                if(region.exists(SmartModeSettingsObjects.FULL(AP)) == null){
                    System.out.println("[setSmartModeFor] " + network + " is NOT set to FULL. Will set it now...");
                    region.offset(30, 0).click();
                    try{
                        SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    }catch (FindFailed e){
                        region.offset(30, 0).click();
                        SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    }
                    if(SCREEN.exists(UniversalObjects.Universal_CheckBox_Unchecked(AP)) == null){
                        SCREEN.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                    }
                    SCREEN.click(SmartModeSettingsObjects.SELECTION_FULL(AP));
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                    SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    if(!setMoreOptions){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), WAIT_VANISH);
                    }
                    System.out.println("[setSmartModeFor] Done!");
                    Global.setVar_needToResetSmartSettings(true);
                }else{
                    System.out.println("[setSmartModeFor] " + network + " SAFE network is ALREADY set to FULL!");
                    if(!setMoreOptions){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), WAIT_VANISH);
                    }
                }
            }catch (FindFailed e){
                throw new FindFailed("[setSmartModeFor] FAILED to set " + network +" SAFE network to FULL! " + e);
            }

        }else if(MODE_ID == TO_SELECTED_SITES){

            try{
                if(region.exists(SmartModeSettingsObjects.SELECTED_SITES(AP)) == null){
                    System.out.println("[setSmartModeFor] " + network +" is NOT set to SELECTED SITES. Will set it now...");
                    region.offset(30, 0).click();
                    try{
                        SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    }catch (FindFailed e){
                        region.offset(30, 0).click();
                        SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    }
                    if(SCREEN.exists(UniversalObjects.Universal_CheckBox_Checked(AP)) != null){
                        SCREEN.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                    }
                    SCREEN.click(SmartModeSettingsObjects.SELECTION_SELECTED_SITES(AP));
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                    SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    if(!setMoreOptions){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), WAIT_VANISH);
                    }
                    System.out.println("[setSmartModeFor] Done!");
                    Global.setVar_needToResetSmartSettings(true);
                }else{
                    System.out.println("[setSmartModeFor] " + network +" is ALREADY set to SELECTED SITES!");
                    if(!setMoreOptions){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), WAIT_VANISH);
                    }
                }
            }catch (FindFailed e){
                throw new FindFailed("[setSmartModeFor] FAILED to set " + network +" to SELECTED SITES! " + e);
            }

        }else if(MODE_ID == TO_OFF){

            try{
                if(region.exists(SmartModeSettingsObjects.OFF(AP)) == null){
                    System.out.println("[setSmartModeFor] " + network +" is NOT set to OFF. Will set it now...");
                    region.offset(30, 0).click();
                    try{
                        SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    }catch (FindFailed e){
                        region.offset(30, 0).click();
                        SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    }
                    if(SCREEN.exists(UniversalObjects.Universal_CheckBox_Checked(AP)) != null){
                        SCREEN.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                    }
                    SCREEN.click(SmartModeSettingsObjects.SELECTION_OFF(AP));
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                    SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                    if(!setMoreOptions){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), WAIT_VANISH);
                    }
                    System.out.println("[setSmartModeFor] Done!");
                    Global.setVar_needToResetSmartSettings(true);
                }else{
                    System.out.println("[setSmartModeFor] " + network +" is ALREADY set to OFF!");
                    if(!setMoreOptions){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), WAIT_VANISH);
                    }
                }
            }catch (FindFailed e){
                throw new FindFailed("[setSmartModeFor] FAILED to set " + network +" to OFF! " + e);
            }

        }else if(MODE_ID == TO_DEFAULT){
            region.offset(30, 0).click();
            try{
                try{
                    SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                }catch (FindFailed e){
                    region.offset(30, 0).click();
                    SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                }
                if(SCREEN.exists(UniversalObjects.Universal_CheckBox_Checked(AP)) == null){
                    System.out.println("[setSmartModeFor] DEFAULT is NOT set for " + network +". Will set it now...");
                    SCREEN.click(UniversalObjects.Universal_CheckBox_Unchecked(AP));
                    System.out.println("[setSmartModeFor] Done!");
                    Global.setVar_needToResetSmartSettings(true);
                }else{
                    System.out.println("[setSmartModeFor] DEFAULT is ALREADY SET for " + network +".");
                }
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), WAIT_VANISH);
                if(!setMoreOptions){
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                    SCREEN.waitVanish(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), WAIT_VANISH);
                }
            }catch (FindFailed e){
                throw new FindFailed("[setSmartModeFor] FAILED to set " + network +" to DEFAULT! " + e);
            }
        }else{
            System.out.println("[setSmartModeFor] Wrong MODE_ID!");
            throw new NullPointerException("[setSmartModeFor] Wrong MODE ID: " + MODE_ID);
        }
        region.setAutoWaitTimeout(3);
    }
}
