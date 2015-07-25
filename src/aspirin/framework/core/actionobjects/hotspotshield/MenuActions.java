package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.core.pageobjects.hotspotshield.*;
import aspirin.framework.core.pageobjects.misc.MobizenObjects;
import aspirin.framework.core.utilities.SpecialUtils;
import org.python.modules.time.Time;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by Artur Spirin on 7/9/2015.
 */
public class MenuActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;
    private static final Float LP = 0.45f;

    public static void openMenu() throws FindFailed {

        SCREEN.setAutoWaitTimeout(1);
        try{
            SCREEN.click(SCREEN.find(MobizenObjects.Mobizen_TopBar(LP)).below(70));

            if(SCREEN.exists(UniversalObjects.Universal_PleaseWaitText(AP)) != null){
                System.out.println("[openMenu] \"PLEASE WAIT\" waiting for reconnect");
                SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AP), 30);
            }
            if(SCREEN.exists(UniversalObjects.Universal_Button_Ok(AP)) != null ||
                    SCREEN.exists(UniversalObjects.Universal_Dialog_Continue2(AP)) != null){
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            }
            if(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)) == null &&
                    SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)) == null &&
                            SCREEN.exists(MainObjects.Main_NotProtected(AP)) == null &&
                                SCREEN.exists(MenuObjects.Menu_SmartModeSettings(AP)) == null){
                System.out.println("[openMenu] At the WRONG ACTIVITY to OPEN MENU!");
                throw new FindFailed("[openMenu] At the WRONG ACTIVITY to OPEN MENU!");
            }
            if(SCREEN.exists(MenuObjects.Menu_SmartModeSettings(AP)) == null){
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_MENU_KEY_EVENT);
                try{
                    SCREEN.wait(MenuObjects.Menu_SmartModeSettings(AP), 3);}
                catch (FindFailed e){
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_MENU_KEY_EVENT);
                    SCREEN.wait(MenuObjects.Menu_SmartModeSettings(AP), 3);
                }
            }else{
                System.out.println("[openMenu] MENU is already OPEN");
            }
        }catch (FindFailed e){
            throw new FindFailed("[openMenu] FAILED to OPEN MENU " + e);
        }
    }

    public static void openMyAccount() throws FindFailed{

        openMenu();
        try{
            if(SCREEN.exists(MenuObjects.Menu_MyAccount(AP)) == null){
                int ls = 3;
                while(SCREEN.exists(MenuObjects.Menu_MyAccount(AP)) == null && ls != 0){
                    SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                    SCREEN.wheel(-1, 1);
                    ls--;
                }
            }
            SCREEN.click(MenuObjects.Menu_MyAccount(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_MyAccount(AP)) != null){
                SCREEN.click(MenuObjects.Menu_MyAccount(AP));
            }
            try{
                SCREEN.wait(AccountObjects.Account_SignOutButton(AP), 3);
            }catch (FindFailed e){
                SCREEN.wait(AccountObjects.Account_SignInButton(AP), 3);
            }
        }catch (FindFailed e){
            throw new FindFailed("[openMyAccount] FAILED to OPEN MY ACCOUNT " + e);
        }
    }

    public static void openSignIn() throws FindFailed {

        openMenu();
        try {
            if (SCREEN.exists(MenuObjects.Menu_SignIn(AP)) == null) {
                int ls = 3;
                while (SCREEN.exists(MenuObjects.Menu_SignIn(AP)) == null && ls != 0) {
                    SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                    SCREEN.wheel(-1, 1);
                    ls--;
                }
            }
            SCREEN.click(MenuObjects.Menu_SignIn(AP));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (SCREEN.exists(MenuObjects.Menu_SignIn(AP)) != null) {
                SCREEN.click(MenuObjects.Menu_SignIn(AP));
            }
            SCREEN.wait(AccountObjects.Account_UsernameIcon(AP), 3);
        }catch (FindFailed e){
            throw new FindFailed("[openSignIn] FAILED to OPEN SIGN IN " + e);
        }
    }

    public static void pauseProtection() throws FindFailed{

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP)) == null){
            openMenu();
            try{
                if(SCREEN.exists(MenuObjects.Menu_PauseProtection(AP)) == null){
                    int ls = 3;
                    while(SCREEN.exists(MenuObjects.Menu_PauseProtection(AP)) == null && ls != 0){
                        SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                        SCREEN.wheel(-1, 1);
                        ls--;
                    }
                }
                SCREEN.click(MenuObjects.Menu_PauseProtection(AP));
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                if(SCREEN.exists(MenuObjects.Menu_PauseProtection(AP)) != null){
                    SCREEN.click(MenuObjects.Menu_PauseProtection(AP));
                }
                SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AP), 45);
                SCREEN.wait(MainObjects.Main_NotProtected(AP), 5);
            }catch (FindFailed e){
                throw new FindFailed("[pauseProtection] FAILED to PAUSE PROTECTION " + e);
            }
        }else{
            System.out.println("[pauseProtection] VPN already PAUSED!");
        }
    }

    public static void openCurrentNetwork() throws FindFailed{

        openMenu();
        try{
            if(SCREEN.exists(MenuObjects.Menu_CurrentNetwork(AP)) == null){
                int ls = 3;
                while(SCREEN.exists(MenuObjects.Menu_CurrentNetwork(AP)) == null && ls != 0){
                    SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                    SCREEN.wheel(-1, 1);
                    ls--;
                }
            }
            SCREEN.click(MenuObjects.Menu_CurrentNetwork(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_CurrentNetwork(AP)) != null){
                SCREEN.click(MenuObjects.Menu_CurrentNetwork(AP));
            }
            SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(AP), 5);
        }catch (FindFailed e){
            throw new FindFailed("[openCurrentNetwork] FAILED to OPEN CURRENT NETWORK " + e);
        }
    }

    public static void openSelectedSites() throws FindFailed{

        openMenu();
        try{
            if(SCREEN.exists(MenuObjects.Menu_SelectedSites(AP)) == null){
                int ls = 3;
                while(SCREEN.exists(MenuObjects.Menu_SelectedSites(AP)) == null && ls != 0){
                    SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                    SCREEN.wheel(-1, 1);
                    ls--;
                }
            }
            SCREEN.click(MenuObjects.Menu_SelectedSites(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_SelectedSites(AP)) != null){
                SCREEN.click(MenuObjects.Menu_SelectedSites(AP));
            }
            SCREEN.wait(SelectedSitesObjects.SelectedSites_TopActivityBar(AP), 5);
        }catch (FindFailed e){
            throw new FindFailed("[openSelectedSites] FAILED to OPEN SELECTED SITES " + e);
        }
    }

    public static void openSmartModeSettings() throws FindFailed{

        openMenu();
        try{
            SCREEN.click(MenuObjects.Menu_SmartModeSettings(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_SmartModeSettings(AP)) != null){
                SCREEN.click(MenuObjects.Menu_SmartModeSettings(AP));
            }
            SCREEN.wait(SmartModeSettingsObjects.SmartModeSettings_TopActivityBar(AP), 5);
        }catch (FindFailed e){
            throw new FindFailed("[openSmartModeSettings] FAILED to OPEN SMART MODE SETTINGS " + e);
        }
    }

    public static void openNetworkActivities() throws FindFailed{

        openMenu();
        try{
            SCREEN.click(MenuObjects.Menu_NetworkActivities(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_NetworkActivities(AP)) != null){
                SCREEN.click(MenuObjects.Menu_NetworkActivities(AP));
            }
        }catch (FindFailed e){
            throw new FindFailed("[openNetworkActivities] FAILED to OPEN NETWORK ACTIVITY " + e);
        }
    }

    public static void openGeneralSettings() throws FindFailed{

        if(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_TopActivityBar(AP)) == null){
            openMenu();
            try{
                SCREEN.click(MenuObjects.Menu_GeneralSettings(AP));
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                if(SCREEN.exists(MenuObjects.Menu_GeneralSettings(AP)) != null){
                    SCREEN.click(MenuObjects.Menu_GeneralSettings(AP));
                }
                SCREEN.wait(GeneralSettingsObjects.GeneralSettings_TopActivityBar(AP), 5);
            }catch (FindFailed e){
                throw new FindFailed("[openGeneralSettings] FAILED to OPEN GENERAL SETTINGS " + e);
            }
        }else{
            System.out.println("[openGeneralSettings] GENERAL SETTINGS already OPEN");
        }
    }

    public static void clickLikeUsOnFacebook() throws FindFailed{

        openMenu();
    }
    public static void clickFollowUsOnTwitter() throws FindFailed{

        openMenu();
    }
    public static void clickContactSupport() throws FindFailed{

        openMenu();
    }
    public static void openHelp() throws FindFailed{

        openMenu();
        try{
            if(SCREEN.exists(MenuObjects.Menu_Help(AP)) == null){
                int ls = 3;
                while(SCREEN.exists(MenuObjects.Menu_Help(AP)) == null && ls != 0){
                    SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                    SCREEN.wheel(1, 1);
                    ls--;
                }
            }
            SCREEN.click(MenuObjects.Menu_Help(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_Help(AP)) != null){
                SCREEN.click(MenuObjects.Menu_Help(AP));
            }
            SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AP), 15);
            SCREEN.wait(HelpObjects.Help_HelpPage(AP), 3);
        }catch (FindFailed e){
            throw new FindFailed("[openHelp] FAILED to OPEN HELP " + e);
        }
    }

    public static void openTestAds() throws FindFailed{

        openMenu();
        try{
            if(SCREEN.exists(MenuObjects.Menu_TestAds(AP)) == null){
                int ls = 3;
                while(SCREEN.exists(MenuObjects.Menu_TestAds(AP)) == null && ls != 0){
                    SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                    SCREEN.wheel(1, 1);
                    ls--;
                }
            }
            SCREEN.click(MenuObjects.Menu_TestAds(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_TestAds(AP)) != null){
                SCREEN.click(MenuObjects.Menu_TestAds(AP));
            }
            SCREEN.wait(MainObjects.Main_Dialog_Ads(AP), 3);
        }catch (FindFailed e){
            throw new FindFailed("[openTestAds] FAILED to OPEN TEST ADS " + e);
        }
    }

    public static void openTestDebug() throws FindFailed{

        openMenu();
        try{
            if(SCREEN.exists(MenuObjects.Menu_Test(AP)) == null){
                int ls = 3;
                while(SCREEN.exists(MenuObjects.Menu_Test(AP)) == null && ls != 0){
                    SCREEN.hover(MenuObjects.Menu_SmartModeSettings(AP));
                    SCREEN.wheel(1, 1);
                    ls--;
                    Time.sleep(1.5);
                }
            }
            SCREEN.click(MenuObjects.Menu_Test(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_Test(AP)) != null){
                SCREEN.click(MenuObjects.Menu_Test(AP));
            }
            SCREEN.wait(TestDebugObjects.DEBUG_MENU(AP), 3);
        }catch (FindFailed e){
            throw new FindFailed("[openTestDebug] FAILED to OPEN DEBUG MENU " + e);
        }
    }

    public static void clickTerms() throws FindFailed{

        openMenu();
        try{
            SCREEN.click(MenuObjects.Menu_Terms(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_Terms(AP)) != null){
                SCREEN.click(MenuObjects.Menu_Terms(AP));
            }
            SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AP), 15);
            SCREEN.wait(HelpObjects.Help_TermsOfServicePage(AP), 3);
        }catch (FindFailed e){
            throw new FindFailed("[clickTerms] FAILED to CLICK TERMS " + e);
        }
    }

    public static void clickPrivacy() throws FindFailed{

        openMenu();
        try{
            SCREEN.click(MenuObjects.Menu_Privacy(AP));
            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            if(SCREEN.exists(MenuObjects.Menu_Privacy(AP)) != null){
                SCREEN.click(MenuObjects.Menu_Privacy(AP));
            }
            SCREEN.waitVanish(UniversalObjects.Universal_PleaseWaitText(AP), 15);
            SCREEN.wait(HelpObjects.Help_PrivacyPage(AP), 3);
        }catch (FindFailed e){
            throw new FindFailed("[clickPrivacy] FAILED to CLICK PRIVACY " + e);
        }
    }

    public static void openShare() throws FindFailed {

        if(SCREEN.exists(ShareObjects.Share_Contacts(AP)) == null){
            SCREEN.click(MainObjects.Main_TopBar_Share_Green(AP));
            SCREEN.waitVanish(MainObjects.Main_TopBar_Share_Green(AP), 5);
            if(SCREEN.exists(MainObjects.Main_TopBar_Share_Green(AP))!=null){
                SCREEN.click(MainObjects.Main_TopBar_Share_Green(AP));
                SCREEN.waitVanish(MainObjects.Main_TopBar_Share_Green(AP), 5);
            }
            SCREEN.wait(ShareObjects.Share_Contacts(AP), 5);
        }
        else{
            System.out.println("[openShare] Share Menu already open!");
        }
    }
}
