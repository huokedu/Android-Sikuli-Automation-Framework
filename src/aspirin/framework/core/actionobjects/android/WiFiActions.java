package aspirin.framework.core.actionobjects.android;

import aspirin.framework.core.pageobjects.android.WiFiObjects;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

/**
 * Created by support on 7/15/15.
 */
public class WiFiActions {

    // Network IDs to use with the method
    public static final Integer ANCHORFREE_PRIVATE = 1;
    public static final Integer ANCHORFREE_GUEST = 2;
    public static final Integer QA_NO_PASS = 3;

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static void connectTo(Integer accessPointID) throws FindFailed {

        Pattern AccessPoint_ID;
        Pattern AccessPoint_ID_Dialog;
        // Change made to bypass setUP failures when access point dialog is open
        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
        turnWiFiOn();
        if(SCREEN.exists(WiFiObjects.WIFI(AP)) == null){
            System.out.println("[WiFiActions / turnWiFiOn] WiFi Settings are not open. Opening...");
            SpecialUtils.openActivity(SpecialUtils.WIFI_SETTINGS_ACTIVITY);
        }
        try{
            SCREEN.wait(WiFiObjects.STATUS_CONNECTED(AP), 30);
        }catch (FindFailed e){
            throw new FindFailed("[WiFiActions / connectTo] Poor WiFi signal!");
        }

        // Supported Access Points
        if(accessPointID == ANCHORFREE_PRIVATE){

            System.out.println("[WiFiActions / connectTo] Creating patterns for ANCHORFREE PRIVATE...");
            AccessPoint_ID = WiFiObjects.ANCHORFREE_PRIVATE(AP);
            AccessPoint_ID_Dialog = WiFiObjects.ANCHORFREE_PRIVATE_DIALOG(AP);
        }
        else if(accessPointID == ANCHORFREE_GUEST){

            System.out.println("[WiFiActions / connectTo] Creating patterns for ANCHORFREE GUEST...");
            AccessPoint_ID = WiFiObjects.ANCHORFREE_GUEST(AP);
            AccessPoint_ID_Dialog = WiFiObjects.ANCHORFREE_GUEST_DIALOG(AP);
        }
        else if(accessPointID == QA_NO_PASS){

            System.out.println("[WiFiActions / connectTo] Creating patterns for QA_NO_PASS...");
            AccessPoint_ID = WiFiObjects.QA_NO_PASS(AP);
            AccessPoint_ID_Dialog = WiFiObjects.QA_NO_PASS_DIALOG(AP);
        }
        else{
            throw new NullPointerException("[WiFiActions / connectTo] Wrong AccessPoint ID! Got ID#: " + accessPointID);
        }

        if(SCREEN.exists(WiFiObjects.WIFI(AP)) == null){
            System.out.println("[WiFiActions / connectTo] WiFi Settings are not open. Opening...");
            SpecialUtils.openActivity(SpecialUtils.WIFI_SETTINGS_ACTIVITY);
            SCREEN.wait(WiFiObjects.WIFI(AP), 5);
        }

        // Creating universal state patterns
        Pattern State_Connected = WiFiObjects.STATUS_CONNECTED(AP);

        // Creating common region that we will look at to see the status of specific access point
        Region region = SCREEN.find(AccessPoint_ID);

        if(region.below(20).exists(State_Connected)==null){
            System.out.println("[WiFiActions / connectTo] NOT CONNECTED to DESIRED WiFi ACCESS POINT. Connecting now...");
            openDialog(AccessPoint_ID, AccessPoint_ID_Dialog);
            try{
                SCREEN.click(WiFiObjects.DIALOG_BUTTON_CONNECT(AP));
                SCREEN.waitVanish(WiFiObjects.DIALOG_BUTTON_CONNECT(AP), 5);
                int ls =3;
                while(ls != 0 && SCREEN.exists(WiFiObjects.DIALOG_BUTTON_CONNECT(AP)) != null){
                    SCREEN.click(WiFiObjects.DIALOG_BUTTON_CONNECT(AP));
                    SCREEN.waitVanish(WiFiObjects.DIALOG_BUTTON_CONNECT(AP), 5);
                    ls--;
                }
                try{
                    SCREEN.wait(WiFiObjects.STATUS_CONNECTED(AP), 30);
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                }catch (FindFailed e){
                    throw new FindFailed("[WiFiActions / connectTo] Poor WiFi signal!");
                }
            }catch (FindFailed e){
                throw new FindFailed("[WiFiActions / connectTo] FAILED to CONNECT to the desired ACCESS POINT! " + e);
            }
        }else{
            SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            System.out.println("[WiFiActions / connectTo] Already connected to the desired access point!");
        }
    }

    private static void openDialog(Pattern AccessPoint_ID, Pattern AccessPoint_ID_Dialog) throws FindFailed {

        try{
            SCREEN.click(AccessPoint_ID);
            SCREEN.waitVanish(AccessPoint_ID, 5);
            int ls = 3;
            while(SCREEN.exists(AccessPoint_ID) != null && SCREEN.exists(AccessPoint_ID_Dialog) == null && ls != 0){
                SCREEN.click(AccessPoint_ID);
                SCREEN.waitVanish(AccessPoint_ID, 5);
                ls--;
            }
            try{
                SCREEN.wait(AccessPoint_ID_Dialog, 5);
                SCREEN.wait(WiFiObjects.DIALOG_BUTTON_CONNECT(AP), 5);
            }catch (FindFailed e) {
                // Checking if we have clicked the right access point because sometimes the access points change location on the screen during the click
                ls = 3;
                while(SCREEN.exists(AccessPoint_ID_Dialog) == null && ls != 0) {
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                    SCREEN.wait(AccessPoint_ID);
                    SCREEN.click(AccessPoint_ID);
                    SCREEN.waitVanish(AccessPoint_ID, 5);
                    int ls2 = 3;
                    while(SCREEN.exists(AccessPoint_ID) != null && ls2 != 0){
                        SCREEN.click(AccessPoint_ID);
                        SCREEN.waitVanish(AccessPoint_ID, 5);
                        ls2--;
                    }
                    ls--;
                }
                while(SCREEN.exists(AccessPoint_ID_Dialog) == null && SCREEN.exists(WiFiObjects.DIALOG_BUTTON_CONNECT(AP)) == null && ls != 0) {
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                    SCREEN.wait(AccessPoint_ID);
                    SCREEN.click(AccessPoint_ID);
                    SCREEN.waitVanish(AccessPoint_ID, 5);
                    int ls2 = 3;
                    while(SCREEN.exists(AccessPoint_ID) != null && ls2 != 0){
                        SCREEN.click(AccessPoint_ID);
                        SCREEN.waitVanish(AccessPoint_ID, 5);
                        ls2--;
                    }
                    ls--;
                }
            }
        }catch (FindFailed e){
            throw new FindFailed("[WiFiActions / openDialog] FAILED to OPEN the right ACCESS POINT dialog! " + e.getCause());
        }
    }

    public static void turnWiFiOn() throws FindFailed {

        try{
            if(SpecialUtils.androidWiFiOn()){
                System.out.println("[WiFiActions / turnWiFiOn] WIFI ALREADY ON!");
            }
            else{
                System.out.println("[WiFiActions / turnWiFiOn] Switching to WIFI!");
                if(SCREEN.exists(WiFiObjects.WIFI(AP)) == null){
                    System.out.println("[WiFiActions / turnWiFiOn] WiFi Settings are not open. Opening...");
                    SpecialUtils.openActivity(SpecialUtils.WIFI_SETTINGS_ACTIVITY);
                }
                SCREEN.wait(WiFiObjects.SWITCH_STATE_OFF(AP), 5);
                SCREEN.click(WiFiObjects.SWITCH_STATE_OFF(AP));
                SCREEN.wait(WiFiObjects.SWITCH_STATE_ON(AP), 5);
                System.out.println("[WiFiActions / turnWiFiOn] Done!");
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }
        }catch (FindFailed e){
            throw new FindFailed("[WiFiActions / turnWiFiOn] Failed to turn WiFi ON! " + e);
        }
    }

    public static void turnCellDataOn() throws FindFailed {

        try{
            if(!SpecialUtils.androidWiFiOn()){
                System.out.println("[WiFiActions / turnCellDataOn] ALREADY ON CELLULAR NETWORK!");
            }
            else{
                System.out.println("[WiFiActions / turnCellDataOn] Switching to CELLULAR NETWORK!");
                if(SCREEN.exists(WiFiObjects.WIFI(AP)) == null){
                    System.out.println("[WiFiActions / turnCellDataOn] WiFi Settings are not open. Opening...");
                    SpecialUtils.openActivity(SpecialUtils.WIFI_SETTINGS_ACTIVITY);
                }
                SCREEN.wait(WiFiObjects.SWITCH_STATE_ON(AP), 5);
                SCREEN.click(WiFiObjects.SWITCH_STATE_ON(AP));
                SCREEN.wait(WiFiObjects.SWITCH_STATE_OFF(AP), 5);
                System.out.println("[WiFiActions / connectTo] Done!");
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }
        }catch (FindFailed e){
            throw new FindFailed("[WiFiActions / turnWiFiOn] Failed to turn WiFi OFF! " + e);
        }
    }
}
