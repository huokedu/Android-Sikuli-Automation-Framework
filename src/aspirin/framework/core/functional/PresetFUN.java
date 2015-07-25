package aspirin.framework.core.functional;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.hotspotshield.GeneralSettingsActions;
import aspirin.framework.core.actionobjects.hotspotshield.MainActions;
import aspirin.framework.core.actionobjects.hotspotshield.MenuActions;
import aspirin.framework.core.pageobjects.hotspotshield.*;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by a.spirin on 7/14/2015.
 */
public class PresetFUN {

    private static final Screen SCREEN = new Screen();
    private static final Float AVERAGE_PRECISION = 0.80f;

    public static void mainActivity(Boolean pausedStateOK) throws FindFailed {

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SCREEN.setAutoWaitTimeout(0.5);

        // Check if UI is still connected with the service before proceeding any further
        System.out.println("[Preset / mainActivity] Did UI disconnect from the service: " + FailHandler.UI_DISCONNECTED());

        // If app was just installed or clear data was done, the global flag will be set to TRUE so we will wait for the SPLASH SCREEN
        if(Global.waitForSplashScreen()){
            System.out.println("[mainActivity] Global Flag shows app was recently installed or the data was wiped out. Waiting for splash screen...");
            try{
                Global.setVar_waitForSplashScreen(false);
                IF_SplashScreenLogic();
                return;}catch (FindFailed e){throw new FindFailed("[Preset / mainActivity] Failed to get SPLASH SCREEN " + e);}}


        try{


            // IF WE SEE ANY DIALOGS ON THE SCREEN WE ARE GONNA CLOSE THEM
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

            // CHECKING IF WE ARE CONNECTED IN FULL OR SELECTED SITES MODE
            if(SCREEN.exists(MainObjects.Main_Protected_Everything(AVERAGE_PRECISION)) != null ||
                    SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AVERAGE_PRECISION)) != null &&
                            SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) == null){
                System.out.println("[Preset / mainActivity] Activity is ready for interaction!");
            }

            // CHECKING IF IN PAUSED STATE, WILL RECONNECT IF IT IS.
            else if(SCREEN.exists(MainObjects.Main_NotProtected(AVERAGE_PRECISION)) != null &&
                    SCREEN.exists(MainObjects.Main_ModeSelector_StartText(AVERAGE_PRECISION)) != null &&
                    SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) == null){
                System.out.println("[Preset / mainActivity] Activity paused.");
                if(!pausedStateOK){
                    System.out.println("[Preset / mainActivity] Starting VPN...");
                    MainActions.startVPN(false);
                    System.out.println("[Preset / mainActivity] Activity is ready for interaction!");
                }else{
                    System.out.println("[Preset / mainActivity] Paused State is OK!");
                }
            }

            // CHECKING IF THE CLIENT IS IN THE MIDDLE OF CONNECTING
            else if(SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) != null){
                System.out.println("[Preset / mainActivity] Connection in progress, need to wait...");
                SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION), 30);
                mainActivity(pausedStateOK);
            }

            // CHECKING IF HSS MENU IS OPEN, CLOSE IT IF IT IS
            else if(SCREEN.exists(MenuObjects.Menu_SmartModeSettings(AVERAGE_PRECISION)) != null){
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
            }

            // CHECKING IF NOTIFICATION MENU IS OPEN, CLOSE IT IF IT IS
            else if(SCREEN.exists(NotificationObjects.VERIZON_WIRELESS(AVERAGE_PRECISION)) != null){
                System.out.println("[Preset / mainActivity] Notifications open. Closing...");
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }


            else{
                System.out.println("[Preset / mainActivity] Not sure whats happening...");
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                mainActivity(pausedStateOK);
            }
        }catch (FindFailed e){
            throw new FindFailed("[Preset / mainActivity] Failed to set up activity! " + e);
        }
        SCREEN.setAutoWaitTimeout(3);
    }

    public static void install(){

        SpecialUtils.uninstallApplication(SpecialUtils.HOTSPOTSHIELD_PACKAGE_NAME);
        SpecialUtils.installApplication(Runner.APK_ID, false);
        Global.setVar_waitForSplashScreen(true);
    }

    public static void setDebugOptions() throws FindFailed {

        MenuActions.pauseProtection();
        GeneralSettingsActions.setVPNMode(true, true);
        GeneralSettingsActions.disablePopUps(false);
        MainActions.startVPN(false);
        Global.setVar_VPNMode(false);
        Global.setVar_DisablePopUps(false);
    }

    public static void resetSmartSettings() throws FindFailed {

        if(Global.needToResetSmartSettings){
            MenuActions.openTestDebug();
            try{
                SCREEN.click(TestDebugObjects.DebugOptions_ResetSmartSettings(AVERAGE_PRECISION));
                SCREEN.waitVanish(TestDebugObjects.DEBUG_MENU(AVERAGE_PRECISION), 5);
                if(SCREEN.exists(TestDebugObjects.DebugOptions_ResetSmartSettings(AVERAGE_PRECISION)) != null){
                    SCREEN.click(TestDebugObjects.DebugOptions_ResetSmartSettings(AVERAGE_PRECISION));
                    SCREEN.waitVanish(TestDebugObjects.DEBUG_MENU(AVERAGE_PRECISION), 5);
                }
                Global.setVar_needToResetSmartSettings(false);
            }catch (FindFailed e){
                throw new FindFailed("FAILED to RESET SMART SETTINGS! " + e);
            }
        }
        else{
            System.out.println("[resetSmartSettings] Global Flag shows that do not need to reset SMART SETTINGS.");
        }
    }

    public static void IF_SplashScreenLogic() throws FindFailed {

        SCREEN.wait(SplashScreenObjects.FirstLaunch_FirstScreen_NextButtonText(AVERAGE_PRECISION), 20);
        SCREEN.click(SplashScreenObjects.FirstLaunch_FirstScreen_NextButtonText(AVERAGE_PRECISION));
        SCREEN.wait(SplashScreenObjects.FirstLaunch_SecondScreen_FullMode(AVERAGE_PRECISION), 10);
        SCREEN.click(SplashScreenObjects.FirstLaunch_SecondScreen_FullMode(AVERAGE_PRECISION).targetOffset(115, 0));
        try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
        if(SCREEN.exists(NotificationObjects.Notifications_PermissionsDialog_OK(AVERAGE_PRECISION)) != null){
            MainActions.grantPermissions();
        }
        try{
            SCREEN.wait(MainObjects.Main_Protected_Everything(AVERAGE_PRECISION), 30);
        }catch (FindFailed e){
            if(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AVERAGE_PRECISION)) != null){
                System.out.println("[IF_SplashScreenLogic] Did not connect in FULL mode. Connected in SELECTED SITES instead.");
            }
        }
    }

    public static void MainActivity(Boolean pausedStateOK) throws FindFailed {

        SCREEN.setAutoWaitTimeout(0.5);
        SpecialUtils.runCommand("adb logcat -c");
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        System.out.println("[Preset / mainActivity] Did UI disconnect from the service: " + FailHandler.UI_DISCONNECTED());
        if(Global.waitForSplashScreen()){
            //SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
            Global.setVar_waitForSplashScreen(false);
            IF_SplashScreenLogic();
            return;
        }
        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);

        // IF WE SEE ANY DIALOGS ON THE SCREEN WE ARE GONNA CLOSE THEM
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

        // CHECKING IF WE ARE CONNECTED IN FULL OR SELECTED SITES MODE
        if(SCREEN.exists(MainObjects.Main_Protected_Everything(AVERAGE_PRECISION)) != null ||
                SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AVERAGE_PRECISION)) != null &&
                        SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) == null){
            System.out.println("[Preset / mainActivity] Activity is ready for interaction!");
            return;
        }

        // CHECKING IF IN PAUSED STATE, WILL RECONNECT IF IT IS.
        if(SCREEN.exists(MainObjects.Main_NotProtected(AVERAGE_PRECISION)) != null &&
            SCREEN.exists(MainObjects.Main_ModeSelector_StartText(AVERAGE_PRECISION)) != null &&
            SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AVERAGE_PRECISION)) == null){
            System.out.println("[Preset / mainActivity] Activity paused.");
            if(!pausedStateOK){
                System.out.println("[Preset / mainActivity] Starting VPN...");
                MainActions.startVPN(false);
                System.out.println("[Preset / mainActivity] Activity is ready for interaction!");
            }else{
                System.out.println("[Preset / mainActivity] Paused State is OK!");
            }
        }

        // CHECKING IF NOTIFICATION MENU IS OPEN, CLOSE IT IF IT IS
        else if(SCREEN.exists(NotificationObjects.VERIZON_WIRELESS(AVERAGE_PRECISION)) != null){
            System.out.println("[Preset / mainActivity] Notifications open. Closing...");
            SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            if(SCREEN.exists(NotificationObjects.VERIZON_WIRELESS(AVERAGE_PRECISION)) != null){
                System.out.println("[Preset / mainActivity] Notifications open. Closing...");
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }
        }
    }
}
