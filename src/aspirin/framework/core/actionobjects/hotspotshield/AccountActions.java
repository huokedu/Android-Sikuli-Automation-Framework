package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.Runner;
import aspirin.framework.core.pageobjects.hotspotshield.AccountObjects;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.pageobjects.hotspotshield.MenuObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;

/**
 * Created by Artur Spirin on 7/9/2015.
 */
public class AccountActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static final String[] ELITE_ACCOUNT = {"mockUpElite","elite"};                // ID 1
    public static final String[] FREE_ACCOUNT = {"mockUpFree","free"};                   // ID 2
    public static final String[] ACCOUNT_WITH_FIVE_DEVICES = {"mockUpLimit","limit"};    // ID 3

    public static void signIn(String[] credentials, Integer accountStatusID) throws FindFailed {

        SCREEN.setAutoWaitTimeout(1);

        try{

            // Logic for sign in as Elite user
            if(accountStatusID == 1 && SCREEN.exists(MainObjects.Main_TopBar_Free_Text_Green(AP)) != null){
                try{
                    MenuActions.openSignIn();
                }catch (FindFailed e){
                    if(SCREEN.exists(MenuObjects.Menu_MyAccount(AP)) != null){
                        System.out.println("Need to sign out");
                        signOut();
                        MenuActions.openSignIn();
                    }
                }
                try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
                SCREEN.click(AccountObjects.Account_UsernameIcon(AP));
                SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
                if(SCREEN.exists(AccountObjects.Account_Username(AP)) == null){
                    SCREEN.click(AccountObjects.Account_UsernameIcon(AP));
                    SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
                }SCREEN.type(credentials[0]); SCREEN.type(Key.TAB);
                SCREEN.type(credentials[1]); SCREEN.type(Key.ENTER); SCREEN.type(Key.ENTER);
                waitLogic(1);
                SCREEN.wait(MainObjects.Main_TopBar_Elite_Text_Green(AP), 30);
            }

            // Logic for sign in as Free user
            else if(accountStatusID == 2 && SCREEN.exists(MainObjects.Main_TopBar_Elite_Text_Green(AP)) != null){
                signOut();
                MenuActions.openSignIn();
                SCREEN.click(AccountObjects.Account_UsernameIcon(AP));
                SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
                if(SCREEN.exists(AccountObjects.Account_Username(AP)) == null){
                    SCREEN.click(AccountObjects.Account_UsernameIcon(AP));
                    SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
                }SCREEN.type(credentials[0]); SCREEN.type(Key.TAB);
                SCREEN.type(credentials[1]); SCREEN.type(Key.ENTER); SCREEN.type(Key.ENTER);
                waitLogic(2);
                SCREEN.wait(MainObjects.Main_TopBar_Free_Text_Green(AP), 30);
            }

            // Logic for sign in with device limit
            else if(accountStatusID == 3){
                try{
                    MenuActions.openSignIn();
                }catch (FindFailed e){
                    if(SCREEN.exists(MenuObjects.Menu_MyAccount(AP)) != null){
                        signOut();
                        MenuActions.openSignIn();
                    }
                }
                SCREEN.click(AccountObjects.Account_UsernameIcon(AP));
                SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
                if(SCREEN.exists(AccountObjects.Account_Username(AP)) == null){
                    SCREEN.click(AccountObjects.Account_UsernameIcon(AP));
                    SCREEN.type(Key.BACKSPACE, KeyModifier.ALT);
                }SCREEN.type(credentials[0]); SCREEN.type(Key.TAB);
                SCREEN.type(credentials[1]); SCREEN.type(Key.ENTER); SCREEN.type(Key.ENTER);
                try {
                    SCREEN.wait(AccountObjects.Account_Dialog_DeviceLimit(AP), 45);
                }catch (FindFailed e){
                    if(SCREEN.exists(UniversalObjects.Universal_1013_Error(AP)) != null){
                        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                        SCREEN.click(AccountObjects.Account_SignInButton(AP));
                        SCREEN.wait(AccountObjects.Account_Dialog_DeviceLimit(AP), 45);
                    }else{
                        throw e;
                    }
                }
            }
        }catch (FindFailed e){
            throw new FindFailed("[signOut] FAILED to SIGN IN! " + e);
        }
    }

    public static void signOut() throws FindFailed {

        try{
            try{
                MenuActions.openMyAccount();
            }catch (FindFailed e){
                if(SCREEN.exists(MenuObjects.Menu_SignIn(AP)) != null){
                    System.out.println("[signOut] Already signed out!");
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                    return;
                }else{
                    e.printStackTrace();
                }
            }
            if(SCREEN.exists(AccountObjects.Account_SignInButton(AP)) != null) {
                System.out.println("[signOut] Already signed out!");
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }else if(SCREEN.exists(AccountObjects.Account_SignOutButton(AP))!= null){
                SCREEN.click(AccountObjects.Account_SignOutButton(AP));
                waitLogic(2);
                SCREEN.wait(MainObjects.Main_TopBar_Free_Text_Green(AP), 20);
            }
            Global.setVar_Colo("US");
        }catch (FindFailed e){
            throw new FindFailed("[signOut] FAILED to SIGN OUT! " + e);
        }
    }

    private static void waitLogic(Integer expectedStatusID) throws FindFailed {

        SCREEN.setAutoWaitTimeout(0.5);
        try{
            SCREEN.wait(UniversalObjects.Universal_PleaseWaitText(AP), 10);
            SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AP), 30);
        }catch (FindFailed e){
            // Sometimes connects too fast and not able to grab the loading indicator so have to put into an exception
        }
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        if(SCREEN.exists(UniversalObjects.Universal_Dialog_Error(AP)) != null){
            SCREEN.click(UniversalObjects.Universal_Button_Ok(AP));
            if(SCREEN.exists(AccountObjects.Account_SignOutButton(AP))!= null){
                SCREEN.click(AccountObjects.Account_SignOutButton(AP));
            }
            else {
                SCREEN.click(AccountObjects.Account_SignInButton(AP));
            }
            try{
                SCREEN.wait(UniversalObjects.Universal_PleaseWaitText(AP), 5);
                SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AP), 30);
            }catch (FindFailed e){
                // Sometimes connects too fast and not able to grab the loading indicator so have to put into an exception
            }
        }
        /** In version 370+, it disconnects and reconnects after sign in/sign out by design **/
        if(Runner.HSS_VERSION >= 370){
            try{
                SCREEN.wait(MainObjects.Main_NotProtected(AP), 10);
                SCREEN.waitVanish(MainObjects.Main_NotProtected(AP), 30);
            }catch (FindFailed e){
                if(expectedStatusID == 1 && SCREEN.exists(MainObjects.Main_TopBar_Elite_Text_Green(AP))!=null){
                    System.out.println("[AccountActions / waitLogic] Signed in without reconnecting!");
                }else if(expectedStatusID == 2 && SCREEN.exists(MainObjects.Main_TopBar_Free_Text_Green(AP))!=null){
                    System.out.println("[AccountActions / waitLogic] Signed in without reconnecting!");
                }else{
                    throw e;
                }
            }
        }
        SCREEN.setAutoWaitTimeout(2);
    }
}
