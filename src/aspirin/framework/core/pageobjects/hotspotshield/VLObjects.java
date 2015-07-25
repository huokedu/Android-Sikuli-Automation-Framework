package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class VLObjects {

    public static final Pattern VirtualLocation_TopActivityBar(Float precision){
        return new Pattern(VLImages.VirtualLocation_TopActivityBar).similar(precision);
    }

    public static final Pattern VirtualLocation_FullTopBar(Float precision){
        return new Pattern(VLImages.VirtualLocation_FullTopBar).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_US(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_US).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_AU(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_AU).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_CA(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_CA).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_CN(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_CN).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_DE(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_DE).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_HK(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_HK).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_IN(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_IN).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_JP(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_JP).similar(precision);
    }

    public static final Pattern VirtualLocation_LargeFlag_UK(Float precision){
        return new Pattern(VLImages.VirtualLocation_LargeFlag_UK).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_US(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_US).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_AU(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_AU).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_CA(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_CA).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_CN(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_CN).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_DE(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_DE).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_HK(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_HK).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_IN(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_IN).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_JP(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_JP).similar(precision);
    }

    public static final Pattern VirtualLocation_Text_UK(Float precision){
        return new Pattern(VLImages.VirtualLocation_Text_UK).similar(precision);
    }

    public static final Pattern VirtualLocation_Dialog_Upgrade(Float precision){
        return new Pattern(VLImages.VirtualLocation_Dialog_Upgrade).similar(precision);
    }
}

class VLImages{

    private static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String HotspotShield_Activities_VirtualLocation = absoluteAIMPath + "Hotspot Shield\\Activities\\Virtual Location\\";
    public static final String HotspotShield_Activities_VirtualLocation_LargeFlags = absoluteAIMPath + "Hotspot Shield\\Activities\\Virtual Location\\Countries\\Large Flags\\";
    public static final String HotspotShield_Activities_VirtualLocation_SmallFlags = absoluteAIMPath + "Hotspot Shield\\Activities\\Virtual Location\\Countries\\Small Flags\\";
    public static final String HotspotShield_Activities_VirtualLocation_Text = absoluteAIMPath + "Hotspot Shield\\Activities\\Virtual Location\\Countries\\Text\\";
    public static final String VirtualLocation_TopActivityBar = HotspotShield_Activities_VirtualLocation + "virtualLocationTopActivityBar.png";
    public static final String VirtualLocation_FullTopBar = HotspotShield_Activities_VirtualLocation + "fullTopBar.png";
    public static final String VirtualLocation_LargeFlag_US = HotspotShield_Activities_VirtualLocation_LargeFlags + "usa.png";
    public static final String VirtualLocation_LargeFlag_AU = HotspotShield_Activities_VirtualLocation_LargeFlags + "australia.png";
    public static final String VirtualLocation_LargeFlag_CA = HotspotShield_Activities_VirtualLocation_LargeFlags + "canada.png";
    public static final String VirtualLocation_LargeFlag_CN = HotspotShield_Activities_VirtualLocation_LargeFlags + "china.png";
    public static final String VirtualLocation_LargeFlag_DE = HotspotShield_Activities_VirtualLocation_LargeFlags + "germany.png";
    public static final String VirtualLocation_LargeFlag_HK = HotspotShield_Activities_VirtualLocation_LargeFlags + "hongkong.png";
    public static final String VirtualLocation_LargeFlag_IN = HotspotShield_Activities_VirtualLocation_LargeFlags + "india.png";
    public static final String VirtualLocation_LargeFlag_JP = HotspotShield_Activities_VirtualLocation_LargeFlags + "japan.png";
    public static final String VirtualLocation_LargeFlag_UK = HotspotShield_Activities_VirtualLocation_LargeFlags + "uk.png";
    public static final String VirtualLocation_Text_US = HotspotShield_Activities_VirtualLocation_Text + "usa.png";
    public static final String VirtualLocation_Text_AU = HotspotShield_Activities_VirtualLocation_Text + "australia.png";
    public static final String VirtualLocation_Text_CA = HotspotShield_Activities_VirtualLocation_Text + "canada.png";
    public static final String VirtualLocation_Text_CN = HotspotShield_Activities_VirtualLocation_Text + "china.png";
    public static final String VirtualLocation_Text_DE = HotspotShield_Activities_VirtualLocation_Text + "germany.png";
    public static final String VirtualLocation_Text_HK = HotspotShield_Activities_VirtualLocation_Text + "hongkong.png";
    public static final String VirtualLocation_Text_IN = HotspotShield_Activities_VirtualLocation_Text + "india.png";
    public static final String VirtualLocation_Text_JP = HotspotShield_Activities_VirtualLocation_Text + "japan.png";
    public static final String VirtualLocation_Text_UK = HotspotShield_Activities_VirtualLocation_Text + "uk.png";
    public static final String VirtualLocation_Dialog_Upgrade = HotspotShield_Activities_VirtualLocation + "upgradeDialog.png";
}