package aspirin.framework.core.pageobjects.android;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/10/2015.
 */
public final class ChromeObjects {

    public static Pattern Chrome_JavaScript_Allowed(Float precision){
        return new Pattern(ChromeImages.JAVA_SCRIPT_ALLOWED).similar(precision);
    }

    public static Pattern Chrome_JavaScript_Blocked(Float precision){
        return new Pattern(ChromeImages.JAVA_SCRIPT_BLOCKED).similar(precision);
    }

    public static Pattern Chrome_ContentWall_RefreshLink(Float precision){
        return new Pattern(ChromeImages.CONTENT_WALL_REFRESH_LINK).similar(precision);
    }

    public static Pattern Chrome_Menu_Settings(Float precision){
        return new Pattern(ChromeImages.MENU_SETTINGS).similar(precision);
    }

    public static Pattern Chrome_Settings_TopActivityBar(Float precision){
        return new Pattern(ChromeImages.SETTINGS_TOP_ACTIVITY_BAR).similar(precision);
    }

    public static Pattern Chrome_Settings_SiteSettings(Float precision){
        return new Pattern(ChromeImages.SETTINGS_SITE_SETTINGS).similar(precision);
    }

    public static Pattern Chrome_SitesSettings_TopActivityBar(Float precision){
        return new Pattern(ChromeImages.SITES_SETTINGS_TOP_ACTIVITY_BAR).similar(precision);
    }

    public static Pattern Chrome_SitesSettings_CheckBox_Checked(Float precision){
        return new Pattern(ChromeImages.SITES_SETTINGS_CHECK_BOX_CHECKED).similar(precision);
    }

    public static Pattern Chrome_SitesSettings_CheckBox_Unchecked(Float precision){
        return new Pattern(ChromeImages.SITES_SETTINGS_CHECK_BOX_UNCHECKED).similar(precision);
    }

    public static Pattern Chrome_Dialog_UpgradeToElite(Float precision){
        return new Pattern(ChromeImages.DIALOG_UPGRADE_TO_ELITE).similar(precision);
    }

    public static Pattern Chrome_Dialog_UpgradeToElite_MonthlyElite(Float precision){
        return new Pattern(ChromeImages.DIALOG_UPGRADE_TO_ELITE_MONTHLY_ELITE).similar(precision);
    }

    public static Pattern Chrome_Dialog_UpgradeToElite_YearlyElite(Float precision){
        return new Pattern(ChromeImages.DIALOG_UPGRADE_TO_ELITE_YEARLY_ELITE).similar(precision);
    }

    public static Pattern Chrome_BBCiPlayer(Float precision){
        return new Pattern(ChromeImages.BB_CI_PLAYER).similar(precision);
    }

    public static Pattern Chrome_Menu(Float precision){
        return new Pattern(ChromeImages.MENU).similar(precision);
    }

    public static Pattern Chrome_Findipinfo_JP(Float precision){
        return new Pattern(ChromeImages.JP).similar(precision);
    }

    public static Pattern Chrome_HTTPS_NotSecured_Dialog(Float precision){
        return new Pattern(ChromeImages.DIALOG_HTTPS_NOT_SECURED).similar(precision);
    }

    public static Pattern Chrome_HTTPS_AdvancedLink(Float precision){
        return new Pattern(ChromeImages.HTTPS_ADVANCED_LINK).similar(precision);
    }

    public static Pattern Chrome_HTTPS_ProceedTo(Float precision){
        return new Pattern(ChromeImages.HTTPS_PROCEED_TO).similar(precision);
    }

    public static Pattern Chrome_BlankScreen(Float precision){
        return new Pattern(ChromeImages.BLANK_SCREEN).similar(precision);
    }

    public static Pattern Chrome_IP_Dot(Float precision){
        return new Pattern(ChromeImages.IP_DOT).similar(precision);
    }

    public static Pattern Chrome_SSLSecuredIcon(Float precision){
        return new Pattern(ChromeImages.SSL_SECURED_ICON).similar(precision);
    }

    public static Pattern Chrome_SSLUnsecuredIcon(Float precision){
        return new Pattern(ChromeImages.SSL_UNSECURED_ICON).similar(precision);
    }

    public static Pattern Chrome_HelpCenter_SubmitRequestPage(Float precision){
        return new Pattern(ChromeImages.SUPPORT_CENTER_SUBMIT_REQUEST_PAGE).similar(precision);
    }

    public static Pattern Chrome_Findipinfo_US(Float precision){
        return new Pattern(ChromeImages.US).similar(precision);
    }

    public static Pattern NO_THANKS(Float precision){
        return new Pattern(ChromeImages.NO_THANKS).similar(precision);
    }

    public static Pattern ACCEPT(Float precision){
        return new Pattern(ChromeImages.ACCEPT).similar(precision);
    }
}

class ChromeImages{

    private static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String CHROME_DIRECTORY = absoluteAIMPath + "Chrome\\";
    public static final String SUPPORT_CENTER_SUBMIT_REQUEST_PAGE = CHROME_DIRECTORY + "HelpCenterSubmitRequestPage.png";
    public static final String JP = CHROME_DIRECTORY + "chromeJP.png";
    public static final String US = CHROME_DIRECTORY + "chromeUS.png";
    public static final String DIALOG_HTTPS_NOT_SECURED =  CHROME_DIRECTORY + "connectionNotPrivate.png";
    public static final String HTTPS_ADVANCED_LINK =  CHROME_DIRECTORY + "advancedLink.png";
    public static final String HTTPS_PROCEED_TO = CHROME_DIRECTORY + "proceedTo.png";
    public static final String BLANK_SCREEN = CHROME_DIRECTORY + "blankScreen.png";
    public static final String IP_DOT = CHROME_DIRECTORY + "ChromeIPDot.png";
    public static final String SSL_SECURED_ICON = CHROME_DIRECTORY + "SSLSecured.png";
    public static final String SSL_UNSECURED_ICON = CHROME_DIRECTORY + "SSLUnsecured.png";
    public static final String MENU = CHROME_DIRECTORY + "chromeMenu.png";
    public static final String BB_CI_PLAYER = CHROME_DIRECTORY + "bbciplayer.png";
    public static final String CONTENT_WALL_REFRESH_LINK = CHROME_DIRECTORY + "contentWallRefreshLink.png";
    public static final String MENU_SETTINGS = CHROME_DIRECTORY + "chromeSettings.png";
    public static final String SETTINGS_TOP_ACTIVITY_BAR = CHROME_DIRECTORY + "ChromeSettingsActivityBar.png";
    public static final String SETTINGS_SITE_SETTINGS = CHROME_DIRECTORY + "chromeSiteSettings.png";
    public static final String SITES_SETTINGS_TOP_ACTIVITY_BAR = CHROME_DIRECTORY + "ChromeSiteSettingsActivityBar.png";
    public static final String SITES_SETTINGS_CHECK_BOX_CHECKED = CHROME_DIRECTORY + "chromeCheckBoxChecked.png";
    public static final String SITES_SETTINGS_CHECK_BOX_UNCHECKED = CHROME_DIRECTORY + "chromeCheckBoxUnchecked.png";
    public static final String DIALOG_UPGRADE_TO_ELITE = CHROME_DIRECTORY + "ContentWallUpgradeToEliteDialog_GooglePlay.png";
    public static final String DIALOG_UPGRADE_TO_ELITE_MONTHLY_ELITE = CHROME_DIRECTORY + "monthlyEliteButton.png";
    public static final String DIALOG_UPGRADE_TO_ELITE_YEARLY_ELITE = CHROME_DIRECTORY + "yearlyEliteButton.png";
    public static final String JAVA_SCRIPT_ALLOWED = CHROME_DIRECTORY + "javaScriptAllowed.png";
    public static final String JAVA_SCRIPT_BLOCKED = CHROME_DIRECTORY + "javaScriptBlocked.png";
    public static final String ACCEPT = CHROME_DIRECTORY + "accept.png";
    public static final String NO_THANKS = CHROME_DIRECTORY + "noThanks.png";
}