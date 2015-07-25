package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class SmartModeSettingsObjects {

    public static final Pattern SmartModeSettings_TopActivityBar(Float precision){
        return new Pattern(SmartModeSettingsImages.SmartModeSettings_TopActivityBar).similar(precision);
    }

    public static final Pattern SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar(Float precision){
        return new Pattern(SmartModeSettingsImages.SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar).similar(precision);
    }

    public static final Pattern UNSAFE_NETWORK(Float precision){
        return new Pattern(SmartModeSettingsImages.UNSAFE_NETWORK).similar(precision);
    }

    public static final Pattern MOBILE_NETWORK(Float precision){
        return new Pattern(SmartModeSettingsImages.MOBILE_NETWORK).similar(precision);
    }

    public static final Pattern SAFE_NETWORK(Float precision){
        return new Pattern(SmartModeSettingsImages.SAFE_NETWORK).similar(precision);
    }

    public static final Pattern OFF(Float precision){
        return new Pattern(SmartModeSettingsImages.OFF).similar(precision);
    }

    public static final Pattern SELECTED_SITES(Float precision){
        return new Pattern(SmartModeSettingsImages.SELECTED_SITES).similar(precision);
    }

    public static final Pattern FULL(Float precision){
        return new Pattern(SmartModeSettingsImages.FULL).similar(precision);
    }

    public static final Pattern SELECTION_OFF(Float precision){
        return new Pattern(SmartModeSettingsImages.SELECTION_OFF).similar(precision);
    }

    public static final Pattern SELECTION_SELECTED_SITES(Float precision){
        return new Pattern(SmartModeSettingsImages.SELECTION_SELECTED_SITES).similar(precision);
    }

    public static final Pattern SELECTION_FULL(Float precision){
        return new Pattern(SmartModeSettingsImages.SELECTION_FULL).similar(precision);
    }

    public static final Pattern CURRENT_NETWORK(Float precision){
        return new Pattern(SmartModeSettingsImages.CURRENT_NETWORK).similar(precision);
    }
}

class SmartModeSettingsImages{

    private static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String HotspotShield_Activities_SmartModeSettings = absoluteAIMPath + "Hotspot Shield\\Activities\\Smart Mode Settings\\";
    public static final String SmartModeSettings_TopActivityBar = HotspotShield_Activities_SmartModeSettings + "hotspotsAndNetworksTitle.png";
    public static final String SmartModeSettings_CurrentNetwork_SmartProtection_TopActivityBar = HotspotShield_Activities_SmartModeSettings + "smartProtectionTitle.png";
    public static final String FULL = HotspotShield_Activities_SmartModeSettings + "full.png";
    public static final String SELECTED_SITES = HotspotShield_Activities_SmartModeSettings + "selectedSites.png";
    public static final String OFF = HotspotShield_Activities_SmartModeSettings + "off.png";
    public static final String SAFE_NETWORK = HotspotShield_Activities_SmartModeSettings + "safeNetwork.png";
    public static final String MOBILE_NETWORK = HotspotShield_Activities_SmartModeSettings + "mobileNetworks.png";
    public static final String UNSAFE_NETWORK = HotspotShield_Activities_SmartModeSettings + "unsafeNetwork.png";
    public static final String SELECTION_FULL = HotspotShield_Activities_SmartModeSettings + "selectionFull.png";
    public static final String SELECTION_SELECTED_SITES = HotspotShield_Activities_SmartModeSettings + "selectionSelectedSites.png";
    public static final String SELECTION_OFF = HotspotShield_Activities_SmartModeSettings + "selectionOff.png";
    public static final String CURRENT_NETWORK = HotspotShield_Activities_SmartModeSettings + "currentNetwork.png";

}