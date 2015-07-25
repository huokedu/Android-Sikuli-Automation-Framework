package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 **/

public final class MenuObjects {

    public static final Pattern Menu_MyAccount(Float precision){
        return new Pattern(MenuImages.Menu_MyAccount).similar(precision);
    }

    public static final Pattern Menu_SignIn(Float precision){
        return new Pattern(MenuImages.Menu_SignIn).similar(precision);
    }

    public static final Pattern Menu_PauseProtection(Float precision){
        return new Pattern(MenuImages.Menu_PauseProtection).similar(precision);
    }

    public static final Pattern Menu_CurrentNetwork(Float precision){
        return new Pattern(MenuImages.Menu_CurrentNetwork).similar(precision);
    }

    public static final Pattern Menu_SelectedSites(Float precision){
        return new Pattern(MenuImages.Menu_SelectedSites).similar(precision);
    }

    public static final Pattern Menu_SmartModeSettings(Float precision){
        return new Pattern(MenuImages.Menu_SmartModeSettings).similar(precision);
    }

    public static final Pattern Menu_NetworkActivities(Float precision){
        return new Pattern(MenuImages.Menu_NetworkActivities).similar(precision);
    }

    public static final Pattern Menu_GeneralSettings(Float precision){
        return new Pattern(MenuImages.Menu_GeneralSettings).similar(precision);
    }

    public static final Pattern Menu_Facebook(Float precision){
        return new Pattern(MenuImages.Menu_Facebook).similar(precision);
    }

    public static final Pattern Menu_Twitter(Float precision){
        return new Pattern(MenuImages.Menu_Twitter).similar(precision);
    }

    public static final Pattern Menu_ContactSupport(Float precision){
        return new Pattern(MenuImages.Menu_ContactSupport).similar(precision);
    }

    public static final Pattern Menu_Help(Float precision){
        return new Pattern(MenuImages.Menu_Help).similar(precision);
    }

    public static final Pattern Menu_TestAds(Float precision){
        return new Pattern(MenuImages.Menu_TestAds).similar(precision);
    }

    public static final Pattern Menu_KAFailed(Float precision){
        return new Pattern(MenuImages.Menu_KAFailed).similar(precision);
    }

    public static final Pattern Menu_Test(Float precision){
        return new Pattern(MenuImages.Menu_Test).similar(precision);
    }

    public static final Pattern Menu_Quit(Float precision){
        return new Pattern(MenuImages.Menu_Quit).similar(precision);
    }

    public static final Pattern Menu_Privacy(Float precision){
        return new Pattern(MenuImages.Menu_Privacy).similar(precision);
    }

    public static final Pattern Menu_Terms(Float precision){
        return new Pattern(MenuImages.Menu_Terms).similar(precision);
    }
}

class MenuImages {

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String HotspotShield_Activities_Menu = absoluteAIMPath + "Hotspot Shield\\Activities\\Menu\\";
    public static final String HotspotShield_Activities_Menu_IconsOnly = absoluteAIMPath + "Hotspot Shield\\Activities\\Menu\\Icons Only\\";
    public static final String Menu_MyAccount = HotspotShield_Activities_Menu + "myAccount.png";
    public static final String Menu_SignIn = HotspotShield_Activities_Menu + "signIn.png";
    public static final String Menu_PauseProtection = HotspotShield_Activities_Menu + "pauseProtection.png";
    public static final String Menu_CurrentNetwork = HotspotShield_Activities_Menu_IconsOnly + "currentNetwork.png";
    public static final String Menu_SelectedSites = HotspotShield_Activities_Menu + "selectedSites.png";
    public static final String Menu_SmartModeSettings = HotspotShield_Activities_Menu + "smartModeSettings.png";
    public static final String Menu_NetworkActivities = HotspotShield_Activities_Menu + "networkActivities.png";
    public static final String Menu_GeneralSettings = HotspotShield_Activities_Menu + "generalSettings.png";
    public static final String Menu_Facebook = HotspotShield_Activities_Menu + "facebook.png";
    public static final String Menu_Twitter = HotspotShield_Activities_Menu + "twitter.png";
    public static final String Menu_ContactSupport = HotspotShield_Activities_Menu + "contactSupport.png";
    public static final String Menu_Help = HotspotShield_Activities_Menu + "help.png";
    public static final String Menu_Quit = HotspotShield_Activities_Menu + "quit.png";
    public static final String Menu_TestAds = HotspotShield_Activities_Menu + "testAds.png";
    public static final String Menu_KAFailed = HotspotShield_Activities_Menu + "kaFailed.png";
    public static final String Menu_Test = HotspotShield_Activities_Menu + "test.png";
    public static final String Menu_Privacy = HotspotShield_Activities_Menu + "privacy.png";
    public static final String Menu_Terms = HotspotShield_Activities_Menu + "terms.png";
}