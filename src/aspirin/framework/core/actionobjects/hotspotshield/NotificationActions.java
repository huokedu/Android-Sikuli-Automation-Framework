package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.Runner;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.pageobjects.hotspotshield.NotificationObjects;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

/**
 * Created by Artur on 7/13/2015.
 */
public class NotificationActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static void REVOKE_PERMISSIONS() throws FindFailed {

        SpecialUtils.openNotifications();
        if(Runner.AGENT_OS >= 500){
            SpecialUtils.openNotifications();
            try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
        }
        SCREEN.wait(NotificationObjects.Notifications_BlueHSSIcon(AP), 5);
        SCREEN.click(NotificationObjects.Notifications_BlueHSSIcon(AP));
        SCREEN.wait(NotificationObjects.Notifications_PermissionsDialog_Revoke(AP), 5);
        SCREEN.click(NotificationObjects.Notifications_PermissionsDialog_Disconnect(AP));
        SCREEN.waitVanish(NotificationObjects.Notifications_PermissionsDialog_Revoke(AP), 5);
        System.out.println("[REVOKE_PERMISSIONS] Permissions revoked!");
    }

    public static void pauseHotspotShield() throws FindFailed {

        SpecialUtils.openNotifications();
        try{
            SCREEN.click(NotificationObjects.Notifications_Button_Pause(AP));
            SCREEN.waitVanish(NotificationObjects.Notifications_Button_Pause(AP), 5);
        }catch (FindFailed e){
            throw new FindFailed("PAUSE button is NOT VISIBLE on the screen! " + e);
        }
        try{
            SCREEN.wait(MainObjects.Main_NotProtected(AP), 10);
        }catch (FindFailed e){
            if(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)) != null || SCREEN.exists(MainObjects.Main_Protected_Everything(AP)) != null){
                throw new FindFailed("PAUSE button did NOT pause Hotspot Shield! " + e);
            }else{
                System.out.println("[NotificationActions / pauseHotspotShield] Hotspot Shield activity was NOT BROUGHT to FRONT! " + e);
                SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
                try{
                    SCREEN.wait(MainObjects.Main_NotProtected(AP), 10);
                }catch (FindFailed er){
                    throw new FindFailed("PAUSE button did NOT pause Hotspot Shield! " + e);
                }
            }
        }
    }

    public static void changeHotspotShieldMode() throws FindFailed {

        SpecialUtils.openNotifications();
        // Create patterns based on whats on the screen, works independently of preconditions
        Pattern activeMode;
        Pattern expectedUIStateAfterChange;
        if(SCREEN.exists(NotificationObjects.Notifications_Button_Full(AP))!= null){
            activeMode = NotificationObjects.Notifications_Button_Full(AP);
            expectedUIStateAfterChange = MainObjects.Main_ModeSelector_Active_SelectedSites(AP);
        }else if(SCREEN.exists(NotificationObjects.Notifications_Button_SelectedSites(AP))!= null){
            activeMode = NotificationObjects.Notifications_Button_SelectedSites(AP);
            expectedUIStateAfterChange = MainObjects.Main_ModeSelector_SmartProtectionText(AP);
        }else if(SCREEN.exists(NotificationObjects.Notifications_Button_Smart(AP))!= null){
            activeMode = NotificationObjects.Notifications_Button_Smart(AP);
            expectedUIStateAfterChange = MainObjects.Main_ModeSelector_Active_Full(AP);
        }else{
            if(SCREEN.exists(NotificationObjects.Notifications_Button_ProtectNow(AP))!=null){
                throw new FindFailed("Hotspot Shield is PAUSED. Start it before invoking this method!");
            }else{
                throw new FindFailed("Failed to identify mode button. Not able to proceed!");
            }
        }
        // Done creating patterns

        SCREEN.click(activeMode);
        try{
            SCREEN.wait(expectedUIStateAfterChange, 10);
        }catch (FindFailed e){
            if(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)) == null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP)) == null){
                throw new FindFailed("MODE button did NOT change Hotspot Shield MODE! " + e);
            }else{
                System.out.println("[NotificationActions / changeHotspotShieldMode] Hotspot Shield activity was NOT BROUGHT to FRONT! " + e);
                SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
                try{
                    SCREEN.wait(expectedUIStateAfterChange, 10);
                }catch (FindFailed er){
                    throw new FindFailed("MODE button did NOT change Hotspot Shield MODE! " + e);
                }
            }
        }
    }
}
