package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class GeneralSettingsObjects {

    public static final Pattern GeneralSettings_Domain(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_Domain).similar(precision);
    }

    public static final Pattern GeneralSettings_TopActivityBar(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_TopActivityBar).similar(precision);
    }

    public static final Pattern GeneralSettings_HydraSmoothie_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_HydraSmoothie_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_HydraSmoothie_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_HydraSmoothie_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_NetworkNotifications_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_NetworkNotifications_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_OpenVPNUseTCP_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_OpenVPNUseTCP_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_NetworkNotifications_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_NetworkNotifications_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_OpenVPNUseTCP_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_OpenVPNUseTCP_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_ResolveDomains_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_ResolveDomains_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_ResolveDomains_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_ResolveDomains_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_ShuffleDomains_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_ShuffleDomains_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_ShuffleDomains_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_ShuffleDomains_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_StartOnBoot_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_StartOnBoot_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_TurnOnAutoMode_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_TurnOnAutoMode_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_StartOnBoot_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_StartOnBoot_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_UseObfuscation_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_UseObfuscation_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_VPNPort(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_VPNPort).similar(precision);
    }

    public static final Pattern GeneralSettings_TurnOnAutoMode_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_TurnOnAutoMode_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_VPNServer(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_VPNServer).similar(precision);
    }

    public static final Pattern GeneralSettings_UseObfuscation_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_UseObfuscation_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_UseHydra_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_UseHydra_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_TurnVPNOffWhileSleep_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_TurnVPNOffWhileSleep_Unchecked).similar(precision);
    }

    public static final Pattern GeneralSettings_UseHydra_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_UseHydra_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_TurnVPNOffWhileSleep_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_TurnVPNOffWhileSleep_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_Buttons_OK(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_Buttons_OK).similar(precision);
    }

    public static final Pattern GeneralSettings_Buttons_Cancel(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_Buttons_Cancel).similar(precision);
    }

    public static final Pattern GeneralSettings_VPNMode(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_VPNMode).similar(precision);
    }

    public static final Pattern GeneralSettings_VPNMode_OVPN(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_VPNMode_OVPN).similar(precision);
    }

    public static final Pattern GeneralSettings_VPNMode_Hydra(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_VPNMode_Hydra).similar(precision);
    }

    public static final Pattern GeneralSettings_ShowPopUps_Checked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_ShowPopUps_Checked).similar(precision);
    }

    public static final Pattern GeneralSettings_ShowPopUps_Unchecked(Float precision){
        return new Pattern(GeneralSettingsImages.GeneralSettings_ShowPopUps_Unchecked).similar(precision);
    }

    public static final Pattern VPN_DOMAIN(Float precision){
        return new Pattern(GeneralSettingsImages.VPN_DOMAIN).similar(precision);
    }
}


class GeneralSettingsImages{

    public static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String GeneralSettings = absoluteAIMPath + "Hotspot Shield\\Activities\\General Settings\\";
    public static final String GeneralSettings_Domain = GeneralSettings + "Domain.png";
    public static final String GeneralSettings_TopActivityBar = GeneralSettings + "GeneralTopActivityBar.png";
    public static final String GeneralSettings_HydraSmoothie_Checked = GeneralSettings + "HydraSmoothieChecked.png";
    public static final String GeneralSettings_HydraSmoothie_Unchecked = GeneralSettings + "HydraSmoothieUnchecked.png";
    public static final String GeneralSettings_NetworkNotifications_Checked = GeneralSettings + "NetworkNotificationsChecked.png";
    public static final String GeneralSettings_NetworkNotifications_Unchecked = GeneralSettings + "NetworkNotificationsUnChecked.png";
    public static final String GeneralSettings_OpenVPNUseTCP_Checked = GeneralSettings + "OpenVPNUseTCPChecked.png";
    public static final String GeneralSettings_OpenVPNUseTCP_Unchecked = GeneralSettings + "OpenVPNUseTCPUnChecked.png";
    public static final String GeneralSettings_ResolveDomains_Checked = GeneralSettings + "ResolveDomainsChecked.png";
    public static final String GeneralSettings_ResolveDomains_Unchecked = GeneralSettings + "ResolveDomainsUnChecked.png";
    public static final String GeneralSettings_ShuffleDomains_Checked = GeneralSettings + "ShuffleDomainsChecked.png";
    public static final String GeneralSettings_ShuffleDomains_Unchecked = GeneralSettings + "ShuffleDomainsUnchecked.png";
    public static final String GeneralSettings_StartOnBoot_Checked = GeneralSettings + "StartOnBootChecked.png";
    public static final String GeneralSettings_StartOnBoot_Unchecked = GeneralSettings + "StartOnBootUnChecked.png";
    public static final String GeneralSettings_TurnOnAutoMode_Checked = GeneralSettings + "TurnOnAutoModeChecked.png";
    public static final String GeneralSettings_TurnOnAutoMode_Unchecked = GeneralSettings + "TurnOnAutoModeUnChecked.png";
    public static final String GeneralSettings_TurnVPNOffWhileSleep_Checked = GeneralSettings + "TurnVPNOffWileSleepChecked.png";
    public static final String GeneralSettings_TurnVPNOffWhileSleep_Unchecked = GeneralSettings + "TurnVPNOffWileSleepUnChecked.png";
    public static final String GeneralSettings_UseHydra_Checked = GeneralSettings + "UseHydraChecked.png";
    public static final String GeneralSettings_UseHydra_Unchecked = GeneralSettings + "UseHydraUnChecked.png";
    public static final String GeneralSettings_UseObfuscation_Checked = GeneralSettings + "UseObfuscationChecked.png";
    public static final String GeneralSettings_UseObfuscation_Unchecked = GeneralSettings + "UseObfuscationUnChecked.png";
    public static final String GeneralSettings_VPNPort = GeneralSettings + "VPNPort.png";
    public static final String GeneralSettings_VPNServer = GeneralSettings + "VPNServer.png";
    public static final String GeneralSettings_Buttons_OK = GeneralSettings + "okButton.png";
    public static final String GeneralSettings_Buttons_Cancel = GeneralSettings + "cancelButton.png";
    public static final String GeneralSettings_VPNMode = GeneralSettings + "vpnmode.png";
    public static final String GeneralSettings_VPNMode_OVPN = GeneralSettings + "ovpn.png";
    public static final String GeneralSettings_VPNMode_Hydra = GeneralSettings + "hydra.png";
    public static final String GeneralSettings_ShowPopUps_Checked = GeneralSettings + "showPopUpsChecked.png";
    public static final String GeneralSettings_ShowPopUps_Unchecked = GeneralSettings + "showPopUpsUnchecked.png";
    public static final String VPN_DOMAIN = GeneralSettings + "vpndomain.png";
}
