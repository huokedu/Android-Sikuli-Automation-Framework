package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.Runner;
import aspirin.framework.core.functional.PresetFUN;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.pageobjects.hotspotshield.NotificationObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import aspirin.framework.core.pageobjects.hotspotshield.VLObjects;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by Artur Spirin on 7/9/2015.
 */
public class MainActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;
    private static final Float LP = 0.50f;

    public static void startVPN(Boolean expectLoveDialog) throws FindFailed {

        try{
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP)) != null){
                SCREEN.click(MainObjects.Main_ModeSelector_StartText(AP));
                try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
                if(SCREEN.exists(NotificationObjects.Notifications_PermissionsDialog_TrustThisApp(AP)) != null){
                    System.out.println("[startVPN] Need to grant permissions!");
                    grantPermissions();
                }
                SCREEN.waitVanish(MainObjects.Main_NotProtected(AP), 45);
                SCREEN.waitVanish(MainObjects.Main_ModeSelector_StartText(AP), 3);
                SCREEN.setAutoWaitTimeout(1);
                if(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)) != null ||
                        SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)) != null &&
                                SCREEN.exists(MainObjects.Main_NotProtected(AP)) == null){
                    System.out.println("[startVPN] Connected!");
                }
                else if(SCREEN.exists(MainObjects.Main_NotProtected(AP)) != null &&
                                SCREEN.exists(MainObjects.Main_ModeSelector_StartText(AP)) != null &&
                                        SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AP)) == null &&
                                                SCREEN.exists(NotificationObjects.Notifications_Key(AP)) != null){
                    System.out.println("[startVPN] VPN activity did not update. Service may have disconnected from the UI");
                    System.out.println("[startVPN] Need to refresh the application.");
                    FailHandler.UI_DISCONNECTED();
                    PresetFUN.mainActivity(false);
                    throw new FindFailed("[startVPN] Service may have disconnected from the UI");
                }
                else if(SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AP)) != null){
                    System.out.println("[startVPN] Still connecting...");
                    SCREEN.waitVanish(MainObjects.Main_NotProtected(AP), 25);
                    if(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)) != null ||
                            SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)) != null &&
                                    SCREEN.exists(MainObjects.Main_NotProtected(AP)) == null){
                        System.out.println("[startVPN] Connected!");
                    }else{
                        Global.setVar_timesCantConnect(1);
                        throw new FindFailed("[startVPN] Failed to connect!");
                    }
                }else{
                    Global.setVar_timesCantConnect(1);
                    throw new FindFailed("[startVPN] Failed to connect!");
                }
                SCREEN.setAutoWaitTimeout(3);
            }
            else if(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)) == null &&
                        SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)) == null &&
                            SCREEN.exists(MainObjects.Main_NotProtected(AP)) == null){
                System.out.println("[startVPN] At the WRONG ACTIVITY to START VPN!");
                throw new FindFailed("[startVPN] At the WRONG ACTIVITY to START VPN!");
            }
            else{
                System.out.println("[startVPN] VPN is already ON");
            }
            if(!expectLoveDialog){
                try{
                    SCREEN.wait(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS(AP), 5);
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                }catch (FindFailed e){

                }
            }
        }catch (FindFailed e){
            throw new FindFailed("[startVPN] FAILED to START VPN " + e);
        }
    }

    public static void grantPermissions() throws FindFailed {

        try{
            if(Runner.AGENT_OS < 500) {
                SCREEN.click(NotificationObjects.Notifications_PermissionsDialog_TrustThisApp(AP));
            }
            SCREEN.click(NotificationObjects.Notifications_PermissionsDialog_OK(AP));
            SCREEN.waitVanish(NotificationObjects.Notifications_PermissionsDialog_OK(AP), 5);

        }catch (FindFailed e){
            throw new FindFailed("[grantPermissions] FAILED to GRAND PERMISSIONS! " + e);
        }
    }

    public static void connectFullMode() throws FindFailed {

        SCREEN.setAutoWaitTimeout(1);
        try{
            if(SCREEN.exists(MainObjects.Main_ModeSelector_Active_Full(AP)) == null){
                System.out.println("[connectFullMode] MODE is NOT set to FULL");
                openModeSelection();
                if(SCREEN.exists(MainObjects.Main_ModeSelector_ActiveButton_Full(AP))!=null){
                    SCREEN.click(MainObjects.Main_ModeSelector_ActiveButton_Full(AP));
                }
                else if(SCREEN.exists(MainObjects.Main_ModeSelector_NonActiveButton_Full(AP))!=null){
                    SCREEN.click(MainObjects.Main_ModeSelector_NonActiveButton_Full(AP));
                }else{
                    throw new FindFailed("[connectFullMode] FAILED to locate FULL MODE button!");
                }
                SCREEN.wait(MainObjects.Main_Protected_Everything(AP), 40);
                SCREEN.wait(MainObjects.Main_ModeSelector_Active_Full(AP), 8);
            }else{
                System.out.println("[connectFullMode] MODE is ALREADY set to FULL");
            }
        }catch (FindFailed e){
            throw new FindFailed("[connectFullMode] FAILED to connect in FULL MODE! " + e);
        }finally {
            SCREEN.setAutoWaitTimeout(3);
        }
    }

    public static void connectSmartMode(Boolean expectOFFState) throws FindFailed {

        SCREEN.setAutoWaitTimeout(1);
        try{
            if(SCREEN.exists(MainObjects.Main_ModeSelector_Active_Smart(AP)) == null){
                System.out.println("[connectSmartMode] MODE is NOT set to SMART");
                openModeSelection();
                if(SCREEN.exists(MainObjects.Main_ModeSelector_ActiveButton_Smart(AP))!=null){
                    SCREEN.click(MainObjects.Main_ModeSelector_ActiveButton_Smart(AP));
                }
                else if(SCREEN.exists(MainObjects.Main_ModeSelector_NonActiveButton_Smart(AP))!=null){
                    SCREEN.click(MainObjects.Main_ModeSelector_NonActiveButton_Smart(AP));
                }else{
                    throw new FindFailed("[connectSmartMode] FAILED to locate SMART MODE button!");
                }
                if(expectOFFState){
                    try {
                        SCREEN.wait(MainObjects.Main_NotProtected(AP), 40);
                    }catch (FindFailed e){
                        throw new FindFailed("[connectSmartMode] Client did NOT DISCONNECT upon entering SMART MODE! " + e);
                    }
                }else{
                    SCREEN.wait(MainObjects.Main_ModeSelector_SmartProtectionText(AP), 40);
                }
            }else{
                System.out.println("[connectSmartMode] MODE is ALREADY set to SMART");
            }
        }catch (FindFailed e){
            throw new FindFailed("[connectSmartMode] FAILED to connect in SMART MODE! " + e);
        }finally {
            SCREEN.setAutoWaitTimeout(3);
        }
    }

    public static void connectSelectedSitesMode() throws FindFailed {

        SCREEN.setAutoWaitTimeout(1);
        try{
            if(SCREEN.exists(MainObjects.Main_ModeSelector_Active_SelectedSites(AP)) == null){
                System.out.println("[connectSelectedSitesMode] MODE is NOT set to SELECTED SITES");
                openModeSelection();
                if(SCREEN.exists(MainObjects.Main_ModeSelector_ActiveButton_SelectedSites(AP))!=null){
                    SCREEN.click(MainObjects.Main_ModeSelector_ActiveButton_SelectedSites(AP));
                }
                else if(SCREEN.exists(MainObjects.Main_ModeSelector_NonActiveButton_SelectedSites(AP))!=null){
                    SCREEN.click(MainObjects.Main_ModeSelector_NonActiveButton_SelectedSites(AP));
                }else{
                    throw new FindFailed("[connectSelectedSitesMode] FAILED to locate SELECTED SITES MODE button!");
                }
                try{
                    SCREEN.wait(MainObjects.Main_Protected_SelectedSites(AP), 40);
                    SCREEN.wait(MainObjects.Main_ModeSelector_Active_SelectedSites(AP), 5);
                }catch (FindFailed e){
                    throw new FindFailed("[connectSelectedSitesMode] FAILED to connect in SELECTED SITES MODE! " + e);
                }
            }else{
                System.out.println("[connectSelectedSitesMode] MODE is ALREADY set to SELECTED SITES");
            }
        }catch (FindFailed e){
            throw new FindFailed("[connectSelectedSitesMode] FAILED to connect in SELECTED SITES MODE! " + e);
        }finally {
            SCREEN.setAutoWaitTimeout(3);
        }
    }

    public static void openModeSelection() throws FindFailed {

        SCREEN.setAutoWaitTimeout(1);
        try {
            if (SCREEN.exists(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP)) != null) {
                SCREEN.click(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP).targetOffset(0, 38));
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                if (SCREEN.exists(MainObjects.Main_ModeSelector_ModeBar(LP)) == null) {
                    SCREEN.click(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP).targetOffset(0, 38));
                    if (SCREEN.exists(MainObjects.Main_ModeSelector_ModeBar(LP)) == null) {
                        SCREEN.click(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP).targetOffset(0, 38));
                    }
                }
                SCREEN.wait(MainObjects.Main_ModeSelector_ModeBar(LP), 1);
            } else if (SCREEN.exists(MainObjects.Main_ModeSelector_ModeBar(LP)) != null) {
                System.out.println("[openModeSelection] MODE SELECTOR is ALREADY OPEN!");
            }
        }catch (FindFailed e){
            throw new FindFailed("[openModeSelection] FAILED to open MODE SELECTOR! " + e);
        }finally {
            SCREEN.setAutoWaitTimeout(3);
        }
    }

    public static void openVLSelector() throws FindFailed {

        SCREEN.setAutoWaitTimeout(1);
        try {
            if (SCREEN.exists(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP)) != null) {
                SCREEN.click(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP).targetOffset(0, -38));
                try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
                if (SCREEN.exists(VLObjects.VirtualLocation_TopActivityBar(LP)) == null) {
                    SCREEN.click(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP).targetOffset(0, -38));
                    if (SCREEN.exists(VLObjects.VirtualLocation_TopActivityBar(LP)) == null) {
                        SCREEN.click(UniversalObjects.Universal_Arrow_DoubleArrowDark(AP).targetOffset(0, -38));
                    }
                }
                SCREEN.wait(VLObjects.VirtualLocation_FullTopBar(LP), 5);
            } else if (SCREEN.exists(VLObjects.VirtualLocation_TopActivityBar(LP)) != null) {
                System.out.println("[openModeSelection] VL SELECTOR is ALREADY OPEN!");
            }
        }catch (FindFailed e){
            throw new FindFailed("[openModeSelection] FAILED to open VL SELECTOR! " + e);
        }finally {
            SCREEN.setAutoWaitTimeout(3);
        }
    }
}