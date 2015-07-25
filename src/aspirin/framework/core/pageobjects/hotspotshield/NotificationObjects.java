package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class NotificationObjects {

    public static final Pattern Notifications_PermissionsDialog_OK(Float precision){
        return new Pattern(NotificationImages.Notifications_PermissionsDialog_OK).similar(precision);
    }

    public static final Pattern Notifications_PermissionsDialog_Disconnect(Float precision){
        return new Pattern(NotificationImages.Notifications_PermissionsDialog_Disconnect).similar(precision);
    }

    public static final Pattern Notifications_Dialog_Buttons(Float precision){
        return new Pattern(NotificationImages.Notifications_Dialog_Buttons).similar(precision);
    }

    public static final Pattern Notifications_PermissionsDialog_Revoke(Float precision){
        return new Pattern(NotificationImages.Notifications_PermissionsDialog_Revoke).similar(precision);
    }

    public static final Pattern Notifications_PermissionsDialog_Grant(Float precision){
        return new Pattern(NotificationImages.Notifications_PermissionsDialog_Grant).similar(precision);
    }

    public static final Pattern Notifications_PermissionsDialog_TrustThisApp(Float precision){
        return new Pattern(NotificationImages.Notifications_PermissionsDialog_TrustThisApp).similar(precision);
    }

    public static final Pattern Notifications_BlueHSSIcon(Float precision){
        return new Pattern(NotificationImages.Notifications_BlueHSSIcon).similar(precision);
    }

    public static final Pattern Notifications_Clear(Float precision){
        return new Pattern(NotificationImages.Notifications_Clear).similar(precision);
    }

    public static final Pattern Notifications_Key(Float precision){
        return new Pattern(NotificationImages.Notifications_Key).similar(precision);
    }

    public static final Pattern Notifications_PermissionsDialog_Cancel(Float precision){
        return new Pattern(NotificationImages.Notifications_PermissionsDialog_Cancel).similar(precision);
    }

    public static final Pattern Notifications_PermissionsDialog_Configure(Float precision){
        return new Pattern(NotificationImages.Notifications_PermissionsDialog_Configure).similar(precision);
    }

    public static final Pattern Notifications_Button_ProtectNow(Float precision){
        return new Pattern(NotificationImages.Notifications_Button_ProtectNow).similar(precision);
    }

    public static final Pattern Notifications_Button_Pause(Float precision){
        return new Pattern(NotificationImages.Notifications_Button_Pause).similar(precision);
    }

    public static final Pattern Notifications_Button_Smart(Float precision){
        return new Pattern(NotificationImages.Notifications_Button_Smart).similar(precision);
    }

    public static final Pattern Notifications_Button_Full(Float precision){
        return new Pattern(NotificationImages.Notifications_Button_Full).similar(precision);
    }

    public static final Pattern Notifications_Button_SelectedSites(Float precision){
        return new Pattern(NotificationImages.Notifications_Button_SelectedSites).similar(precision);
    }

    public static final Pattern VERIZON_WIRELESS(Float precision){
        return new Pattern(NotificationImages.VERIZON_WIRELESS).similar(precision);
    }

}

class NotificationImages{

    private static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String Notifications = absoluteAIMPath + "Hotspot Shield\\Notifications\\";
    public static final String Notifications_Button_ProtectNow = Notifications + "ProtectNow.png";
    public static final String Notifications_Button_Pause = Notifications + "Pause.png";
    public static final String Notifications_Button_Smart = Notifications + "Smart.png";
    public static final String Notifications_Button_Full = Notifications + "Full.png";
    public static final String Notifications_Button_SelectedSites = Notifications + "SelectedSites.png";
    public static final String Notifications_PermissionsDialog_OK = Notifications + "Okbutton.png";
    public static final String Notifications_PermissionsDialog_Disconnect = Notifications + "disconnectButton.png";
    public static final String Notifications_PermissionsDialog_Cancel = Notifications + "cancelButton.png";
    public static final String Notifications_PermissionsDialog_Configure = Notifications + "configureButton.png";
    public static final String Notifications_Dialog_Buttons = Notifications + "permissionsDialog.png";
    public static final String Notifications_PermissionsDialog_Revoke = Notifications + "permissionsDialog.png";
    public static final String Notifications_PermissionsDialog_Grant = Notifications + "permissionsDialog2.png";
    public static final String Notifications_PermissionsDialog_TrustThisApp = Notifications + "itrustthisapp.png";
    public static final String Notifications_BlueHSSIcon = Notifications + "blueShield.png";
    public static final String Notifications_Clear = Notifications + "clear.png";
    public static final String Notifications_Key = Notifications + "key.png";
    public static final String VERIZON_WIRELESS = Notifications + "verizon.png";
}
