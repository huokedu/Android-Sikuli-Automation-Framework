package aspirin.framework.core.server;

import aspirin.framework.core.actionobjects.hotspotshield.GeneralSettingsActions;
import aspirin.framework.core.actionobjects.hotspotshield.MainActions;
import aspirin.framework.core.actionobjects.hotspotshield.MenuActions;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.pageobjects.hotspotshield.MenuObjects;
import aspirin.framework.core.pageobjects.hotspotshield.SplashScreenObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by Artur on 7/11/2015.
 */
public class PresetSRV {

    private static final Screen SCREEN = new Screen();
    private static final Float AVERAGE_PRECISION = 0.80f;

    public static void mainActivity(Boolean pausedStateOK) throws FindFailed {

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SCREEN.setAutoWaitTimeout(0);
        System.out.println("[Preset / mainActivity] Did UI disconnect from the service: " + FailHandler.UI_DISCONNECTED());
        try{
            if(Global.dataCleared()){
                System.out.println("[Preset / mainActivity] Data was cleared. Waiting for the SPLASH SCREEN...");
                try{
                    Global.setVar_DataCleared(false);
                    SCREEN.wait(SplashScreenObjects.FirstLaunch_FirstScreen_NextButtonText(AVERAGE_PRECISION), 20);
                    SCREEN.click(SplashScreenObjects.FirstLaunch_FirstScreen_NextButtonText(AVERAGE_PRECISION));
                    SCREEN.wait(SplashScreenObjects.FirstLaunch_SecondScreen_FullMode(AVERAGE_PRECISION));
                    SCREEN.click(SplashScreenObjects.FirstLaunch_SecondScreen_FullMode(AVERAGE_PRECISION).targetOffset(115, 0));
                    mainActivity(pausedStateOK);
                }catch (FindFailed e){
                    throw new FindFailed("[Preset / mainActivity] Failed to get SPLASH SCREEN " + e);
                }
            }
            if(SCREEN.exists(UniversalObjects.Universal_Button_Ok(AVERAGE_PRECISION)) != null ||
                    SCREEN.exists(UniversalObjects.Universal_Dialog_Continue2(AVERAGE_PRECISION)) != null){
                System.out.println("[Preset / mainActivity] There are error dialogs on the screen. Closing...");
                int ls = 3;
                try{
                    while(ls != 0 && SCREEN.exists(UniversalObjects.Universal_Button_Ok(AVERAGE_PRECISION)) != null){
                        SCREEN.click(UniversalObjects.Universal_Button_Ok(AVERAGE_PRECISION));
                        ls--;
                    }
                }catch (FindFailed e){
                    ls = 3;
                    while(ls != 0 && SCREEN.exists(UniversalObjects.Universal_Dialog_Continue2(AVERAGE_PRECISION)) != null){
                        SCREEN.click(UniversalObjects.Universal_Dialog_Continue2(AVERAGE_PRECISION));
                        ls--;
                    }
                }
            }
            if(SCREEN.exists(MainObjects.Main_Protected_Everything(AVERAGE_PRECISION)) != null ||
                    SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AVERAGE_PRECISION)) != null &&
                            SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) == null){
                if(!pausedStateOK) {
                    MainActions.connectFullMode();
                }
                System.out.println("[Preset / mainActivity] Activity is ready for interaction!");
            }else if(SCREEN.exists(MainObjects.Main_NotProtected(AVERAGE_PRECISION)) != null &&
                            SCREEN.exists(MainObjects.Main_ModeSelector_StartText(AVERAGE_PRECISION)) != null &&
                                    SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) == null){
                System.out.println("[Preset / mainActivity] Activity paused.");
                if(!pausedStateOK){
                    System.out.println("[Preset / mainActivity] Starting VPN...");
                    MainActions.startVPN(false);
                    if(!pausedStateOK) {
                        MainActions.connectFullMode();
                    }else{
                        System.out.println("[Preset / mainActivity] Activity is ready for interaction!");
                    }
                }else{
                    System.out.println("");
                }
            }else if(SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) != null){
                System.out.println("[Preset / mainActivity] Connection in progress, need to wait...");
                SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION), 30);
                mainActivity(pausedStateOK);
            }else if(SCREEN.exists(MenuObjects.Menu_SmartModeSettings(AVERAGE_PRECISION)) != null){
                System.out.println("[Preset / mainActivity] Menu is open. Closing...");
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                if(SCREEN.exists(MainObjects.Main_Protected_Everything(AVERAGE_PRECISION)) != null ||
                        SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AVERAGE_PRECISION)) != null ||
                        SCREEN.exists(MainObjects.Main_NotProtected(AVERAGE_PRECISION)) != null &&
                                SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) == null){
                    if(!pausedStateOK) {
                        MainActions.connectFullMode();
                    }
                    System.out.println("[Preset / mainActivity] Activity is ready for interaction!");
                }
            }else if(SCREEN.exists(SplashScreenObjects.FirstLaunch_FirstScreen_NextButtonText(AVERAGE_PRECISION))!=null){
                SCREEN.click(SplashScreenObjects.FirstLaunch_FirstScreen_NextButtonText(AVERAGE_PRECISION));
                SCREEN.wait(SplashScreenObjects.FirstLaunch_SecondScreen_FullMode(AVERAGE_PRECISION));
                SCREEN.click(SplashScreenObjects.FirstLaunch_SecondScreen_FullMode(AVERAGE_PRECISION).targetOffset(115, 0));
                mainActivity(pausedStateOK);
            } else{
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                mainActivity(pausedStateOK);
            }
        }catch (FindFailed e){
            throw new FindFailed("[Preset / mainActivity] Failed to set up activity! " + e);
        }
        SCREEN.setAutoWaitTimeout(3);
    }

    public static void setDebugOptions(String serverIP, Boolean enableHydra) throws FindFailed {

        MenuActions.pauseProtection();
        GeneralSettingsActions.setVPNMode(enableHydra, true);
        GeneralSettingsActions.setVPNServer(serverIP, true);
        GeneralSettingsActions.setDebugDomain("hsselite.com", true); //TODO Test
        GeneralSettingsActions.resolveDomains(true, true);  //TODO Test
        GeneralSettingsActions.disablePopUps(false);
        SpecialUtils.forceStopApplication(SpecialUtils.HOTSPOTSHIELD_PACKAGE_NAME);
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        MainActions.startVPN(false);
        Global.setVar_VPNMode(false);
        Global.setVar_VPNServer(false);
        Global.setVar_DisablePopUps(false);
    }
}
