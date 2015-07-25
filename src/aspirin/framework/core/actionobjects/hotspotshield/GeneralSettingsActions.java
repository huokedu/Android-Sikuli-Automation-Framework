package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.core.pageobjects.hotspotshield.GeneralSettingsObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.*;

/**
 * Created by Artur Spirin on 7/9/2015.
 */
public class GeneralSettingsActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static void setVPNMode(Boolean enableHydra, Boolean setMoreDebugOptions) throws FindFailed {

        try{
            MenuActions.openGeneralSettings();
            SCREEN.wheel(-1, 10);
            try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
            SCREEN.click(GeneralSettingsObjects.GeneralSettings_VPNMode(AP));
            try{
                SCREEN.wait(GeneralSettingsObjects.GeneralSettings_VPNMode_Hydra(AP), 3);
            }catch (FindFailed e){
                SCREEN.click(GeneralSettingsObjects.GeneralSettings_VPNMode(AP));
                SCREEN.wait(GeneralSettingsObjects.GeneralSettings_VPNMode_Hydra(AP), 3);
            }
            if(enableHydra){
                SCREEN.click(GeneralSettingsObjects.GeneralSettings_VPNMode_Hydra(AP));
                System.out.println("[setVPNMode] HYDRA ENABLED!");
            }else{
                SCREEN.click(GeneralSettingsObjects.GeneralSettings_VPNMode_OVPN(AP));
                System.out.println("[setVPNMode] OVPN ENABLED!");
            }
            try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
            if(!setMoreDebugOptions){
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }else{
                System.out.println("[setVPNMode] Not exiting GENERAL SETTINGS...");
                System.out.println("[setVPNMode] Set more options is " + setMoreDebugOptions.toString().toUpperCase());
            }
        }catch (FindFailed e){

            Global.setVar_VPNMode(true);
            throw new FindFailed("[setVPNMode] FAILED to set VPN MODE!" + e);
        }
    }

    public static void setVPNServer(String serverIP, Boolean setMoreDebugOptions) throws FindFailed {

        try{
            MenuActions.openGeneralSettings();
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_VPNServer(AP))==null){
                int ls = 3;
                SCREEN.wheel(-1, 10);
                try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
                SCREEN.hover(GeneralSettingsObjects.GeneralSettings_VPNMode(AP));
                while(ls != 0 && SCREEN.exists(GeneralSettingsObjects.GeneralSettings_VPNServer(AP)) == null){
                    SCREEN.wheel(1, 2);
                    ls--;
                }
            }
            SCREEN.click(GeneralSettingsObjects.GeneralSettings_VPNServer(AP));
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_VPNServer(AP)) != null){
                SCREEN.click(GeneralSettingsObjects.GeneralSettings_VPNServer(AP));
            }
            //SCREEN.wait(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP), 2);
            try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
            SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
            SCREEN.type(serverIP);
            try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
            SCREEN.click(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP));
            SCREEN.waitVanish(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP), 2);
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP)) != null){
                SCREEN.click(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP));
            }
            if(!setMoreDebugOptions){
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }else{
                System.out.println("[setVPNMode] Not exiting GENERAL SETTINGS...");
                System.out.println("[setVPNMode] Set more options is " + setMoreDebugOptions.toString().toUpperCase());
            }
        }catch (FindFailed e){

            Global.setVar_VPNServer(true);
            throw new FindFailed("[setVPNServer] FAILED to set VPN SERVER!" + e);
        }
    }

    public static void disablePopUps(Boolean setMoreDebugOptions) throws FindFailed {

        try{
            MenuActions.openGeneralSettings();
            SCREEN.wheel(1, 10);
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_ShowPopUps_Checked(AP)) != null){
                Region region = SCREEN.find(GeneralSettingsObjects.GeneralSettings_ShowPopUps_Checked(AP));
                if(region.exists(UniversalObjects.Universal_CheckBox_Checked(AP)) != null){
                    region.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                    region.waitVanish(UniversalObjects.Universal_CheckBox_Checked(AP), 5);
                    if(region.exists(UniversalObjects.Universal_CheckBox_Checked(AP)) != null){
                        region.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                        region.waitVanish(UniversalObjects.Universal_CheckBox_Checked(AP), 5);
                    }
                    System.out.println("[disablePopUps] POP UPS DISABLED!");
                }
                else{
                    System.out.println("[disablePopUps] POP UPS already DISABLED!");
                }
            }
            if(!setMoreDebugOptions){
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }else{
                System.out.println("[disablePopUps] Not exiting GENERAL SETTINGS...");
                System.out.println("[disablePopUps] Set more options is " + setMoreDebugOptions.toString().toUpperCase());
            }
        }catch (FindFailed e){

            Global.setVar_DisablePopUps(true);
            throw new FindFailed("[disablePopUps] FAILED to disable POP UPS!" + e);
        }
    }

    public static void setDebugDomain(String domainID, Boolean setMoreDebugOptions) throws FindFailed {

        try{
            MenuActions.openGeneralSettings();
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_Domain(AP))==null){
                int ls = 3;
                SCREEN.wheel(-1, 10);
                try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
                SCREEN.hover(GeneralSettingsObjects.GeneralSettings_Domain(AP));
                while(ls != 0 && SCREEN.exists(GeneralSettingsObjects.GeneralSettings_Domain(AP)) == null){
                    SCREEN.wheel(1, 2);
                    ls--;
                }
            }
            SCREEN.click(GeneralSettingsObjects.GeneralSettings_Domain(AP));
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_Domain(AP)) != null){
                SCREEN.click(GeneralSettingsObjects.GeneralSettings_Domain(AP));
            }
            //SCREEN.wait(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP), 2);
            try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
            SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
            SCREEN.type(domainID);
            try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
            SCREEN.click(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP));
            SCREEN.waitVanish(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP), 2);
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP)) != null){
                SCREEN.click(GeneralSettingsObjects.GeneralSettings_Buttons_OK(AP));
            }
            if(!setMoreDebugOptions){
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }else{
                System.out.println("[setVPNMode] Not exiting GENERAL SETTINGS...");
                System.out.println("[setVPNMode] Set more options is " + setMoreDebugOptions.toString().toUpperCase());
            }
        }catch (FindFailed e){

            Global.setVar_setDebugDomain(true);
            throw new FindFailed("[setVPNServer] FAILED to set VPN DOMAIN!" + e);
        }
    }

    public static void resolveDomains(Boolean resolveDomains, Boolean setMoreDebugOptions) throws FindFailed {

        try{
            MenuActions.openGeneralSettings();
            SCREEN.wheel(1, 10);
            if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_ResolveDomains_Checked(AP)) != null){
                if(!resolveDomains){
                    Region region = SCREEN.find(GeneralSettingsObjects.GeneralSettings_ResolveDomains_Checked(AP));
                    if(region.exists(UniversalObjects.Universal_CheckBox_Checked(AP)) != null){
                        region.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                        region.waitVanish(UniversalObjects.Universal_CheckBox_Checked(AP), 5);
                        if(region.exists(UniversalObjects.Universal_CheckBox_Checked(AP)) != null){
                            region.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                            region.waitVanish(UniversalObjects.Universal_CheckBox_Checked(AP), 5);
                        }
                        System.out.println("[disablePopUps] RESOLVE DOMAINS DISABLED!");
                    }
                    else{
                        System.out.println("[disablePopUps] RESOLVE DOMAINS already DISABLED!");
                    }
                }else{
                    Region region = SCREEN.find(GeneralSettingsObjects.GeneralSettings_ResolveDomains_Checked(AP));
                    if(region.exists(UniversalObjects.Universal_CheckBox_Unchecked(AP)) != null){
                        region.click(UniversalObjects.Universal_CheckBox_Unchecked(AP));
                        region.waitVanish(UniversalObjects.Universal_CheckBox_Unchecked(AP), 5);
                        if(region.exists(UniversalObjects.Universal_CheckBox_Unchecked(AP)) != null){
                            region.click(UniversalObjects.Universal_CheckBox_Unchecked(AP));
                            region.waitVanish(UniversalObjects.Universal_CheckBox_Unchecked(AP), 5);
                        }
                        System.out.println("[disablePopUps] RESOLVE DOMAINS ENABLED!");
                    }
                    else{
                        System.out.println("[disablePopUps] RESOLVE DOMAINS already ENABLED!");
                    }
                }
            }
            if(!setMoreDebugOptions){
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }else{
                System.out.println("[disablePopUps] Not exiting GENERAL SETTINGS...");
                System.out.println("[disablePopUps] Set more options is " + setMoreDebugOptions.toString().toUpperCase());
            }
        }catch (FindFailed e){

            Global.setVar_DisablePopUps(true);
            throw new FindFailed("[disablePopUps] FAILED to set RESOLVE DOMAINS!" + e);
        }
    }
}
