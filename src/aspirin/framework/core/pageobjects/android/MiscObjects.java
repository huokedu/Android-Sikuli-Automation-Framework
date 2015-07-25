package aspirin.framework.core.pageobjects.android;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/10/2015.
 */
public final class MiscObjects {

    public static final Pattern AndroidNative_Applications_Widgets(Float precision){
        return new Pattern(MiscImages.AndroidNative_Applications_Widgets).similar(precision);
    }

    public static final Pattern AndroidNative_Applications_Widgets_HSSWidget(Float precision){
        return new Pattern(MiscImages.AndroidNative_Applications_Widgets_HSSWidget).similar(precision);
    }

    public static final Pattern AndroidNative_DateTime_SelectTimeZone(Float precision){
        return new Pattern(MiscImages.AndroidNative_DateTime_SelectTimeZone).similar(precision);
    }

    public static final Pattern AndroidNative_DateTime_AutomaticTimeZone_Checked(Float precision){
        return new Pattern(MiscImages.AndroidNative_DateTime_AutomaticTimeZone_Checked).similar(precision);
    }

    public static final Pattern AndroidNative_DateTime_AutomaticTimeZone_Unchecked(Float precision){
        return new Pattern(MiscImages.AndroidNative_DateTime_AutomaticTimeZone_Unchecked).similar(precision);
    }

    public static final Pattern AndroidNative_DateTime_Arizona(Float precision){
        return new Pattern(MiscImages.AndroidNative_DateTime_Arizona).similar(precision);
    }

    public static final Pattern AndroidNative_Dialog_CrashButton(Float precision){
        return new Pattern(MiscImages.AndroidNative_Dialog_CrashButton).similar(precision);
    }

    public static final Pattern AndroidNative_SendEmailIcon(Float precision){
        return new Pattern(MiscImages.AndroidNative_SendEmailIcon).similar(precision);
    }

    public static final Pattern AndroidNative_NetflixIcon(Float precision){
        return new Pattern(MiscImages.AndroidNative_NetflixIcon).similar(precision);
    }

    public static final Pattern AndroidNative_AirPlaneMode_Unchecked(Float precision){
        return new Pattern(MiscImages.AndroidNative_AirPlaneMode_Unchecked).similar(precision);
    }

    public static final Pattern AndroidNative_AirPlaneMode_Checked(Float precision){
        return new Pattern(MiscImages.AndroidNative_AirPlaneMode_Checked).similar(precision);
    }

    public static final Pattern AndroidNative_Desktop_ApplicationsIcon(Float precision){
        return new Pattern(MiscImages.AndroidNative_Desktop_ApplicationsIcon).similar(precision);
    }

    public static final Pattern AndroidNative_Desktop_SettingsIcon(Float precision){
        return new Pattern(MiscImages.AndroidNative_Desktop_SettingsIcon).similar(precision);
    }

    public static final Pattern AndroidNative_Desktop_HotspotShieldIcon(Float precision){
        return new Pattern(MiscImages.AndroidNative_Desktop_HotspotShieldIcon).similar(precision);
    }
}

class MiscImages {

    private static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String AndroidNative = absoluteAIMPath + "Android Native\\";
    public static final String AndroidNative_Desktop_ApplicationsIcon = AndroidNative + "applicationsIcon.png";
    public static final String AndroidNative_Desktop_SettingsIcon = AndroidNative + "settingsIcon.png";
    public static final String AndroidNative_Desktop_HotspotShieldIcon = AndroidNative + "hssIcon.png";
    public static final String AndroidNative_AirPlaneMode_Unchecked = AndroidNative + "airPlaneModeUnchecked.png";
    public static final String AndroidNative_AirPlaneMode_Checked = AndroidNative + "airPlaneModeChecked.png";
    public static final String AndroidNative_SendEmailIcon = AndroidNative + "sendEmailIcon.png";
    public static final String AndroidNative_NetflixIcon = AndroidNative + "netflixIcon.png";
    public static final String AndroidNative_DateTime_SelectTimeZone = AndroidNative + "SelectTimeZone.PNG";
    public static final String AndroidNative_DateTime_AutomaticTimeZone_Checked = AndroidNative + "AutomatiTimeZoneChecked.PNG";
    public static final String AndroidNative_DateTime_AutomaticTimeZone_Unchecked = AndroidNative + "AutomatiTimeZoneUnchecked.PNG";
    public static final String AndroidNative_DateTime_Arizona = AndroidNative + "DateTimeArizona.PNG";
    public static final String AndroidNative_Dialog_CrashButton = AndroidNative + "crashButton.png";
    public static final String AndroidNative_Applications_Widgets = AndroidNative + "widgets.png";
    public static final String AndroidNative_Applications_Widgets_HSSWidget = AndroidNative + "HSSWidget.png";
}
