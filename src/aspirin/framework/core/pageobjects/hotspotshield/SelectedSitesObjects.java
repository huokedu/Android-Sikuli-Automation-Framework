package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class SelectedSitesObjects {

    public static final Pattern SelectedSites_Dialog_Upgrade(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_Dialog_Upgrade).similar(precision);
    }

    public static final Pattern SelectedSites_FullTopBar(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_FullTopBar).similar(precision);
    }

    public static final Pattern SelectedSites_TopActivityBar(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_TopActivityBar).similar(precision);
    }

    public static final Pattern SelectedSites_USA(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_USA).similar(precision);
    }

    public static final Pattern SelectedSites_UK(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_UK).similar(precision);
    }

    public static final Pattern SelectedSites_AU(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_AU).similar(precision);
    }

    public static final Pattern SelectedSites_CA(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_CA).similar(precision);
    }

    public static final Pattern SelectedSites_CN(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_CN).similar(precision);
    }

    public static final Pattern SelectedSites_DE(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_DE).similar(precision);
    }

    public static final Pattern SelectedSites_IN(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_IN).similar(precision);
    }

    public static final Pattern SelectedSites_JP(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_JP).similar(precision);
    }

    public static final Pattern SelectedSites_HK(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_HK).similar(precision);
    }

    public static final Pattern SelectedSites_AddDomain(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_AddDomain).similar(precision);
    }

    public static final Pattern SelectedSites_CoarsepowderDomain(Float precision){
        return new Pattern(SelectedSitesImages.SelectedSites_CoarsepowderDomain).similar(precision);
    }
}

class SelectedSitesImages{

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String HotspotShield_Activities_SelectedSites = absoluteAIMPath + "Hotspot Shield\\Activities\\Selected Sites\\";
    public static final String HotspotShield_Activities_SelectedSites_USWebsites = absoluteAIMPath + "Hotspot Shield\\Activities\\Selected Sites\\US Websites\\";
    public static final String SelectedSites_FullTopBar = HotspotShield_Activities_SelectedSites + "fullTopBar.png";
    public static final String SelectedSites_TopActivityBar = HotspotShield_Activities_SelectedSites + "selectedSitesTopActivityBar.png";
    public static final String SelectedSites_USA = HotspotShield_Activities_SelectedSites + "usSettings.png";
    public static final String SelectedSites_UK = HotspotShield_Activities_SelectedSites + "ukSettings.png";
    public static final String SelectedSites_AU = HotspotShield_Activities_SelectedSites + "auSettings.png";
    public static final String SelectedSites_CA = HotspotShield_Activities_SelectedSites + "caSettings.png";
    public static final String SelectedSites_CN = HotspotShield_Activities_SelectedSites + "cnSettings.png";
    public static final String SelectedSites_DE = HotspotShield_Activities_SelectedSites + "deSettings.png";
    public static final String SelectedSites_IN = HotspotShield_Activities_SelectedSites + "inSettings.png";
    public static final String SelectedSites_JP = HotspotShield_Activities_SelectedSites + "jpSettings.png";
    public static final String SelectedSites_HK = HotspotShield_Activities_SelectedSites + "hkSettings.png";
    public static final String SelectedSites_AddDomain = HotspotShield_Activities_SelectedSites + "addDomainHere.png";
    public static final String SelectedSites_CoarsepowderDomain = HotspotShield_Activities_SelectedSites + "coarsepowderDomain.png";
    public static final String SelectedSites_Dialog_Upgrade = HotspotShield_Activities_SelectedSites + "upgradeDialogFull.png";
}
