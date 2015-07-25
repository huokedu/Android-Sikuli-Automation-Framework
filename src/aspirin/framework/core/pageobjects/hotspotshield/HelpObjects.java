package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class HelpObjects {

    public static final Pattern Help_TermsOfServicePage(Float precision){
        return new Pattern(HelpImages.Help_TermsOfServicePage).similar(precision);
    }

    public static final Pattern Help_PrivacyPage(Float precision){
        return new Pattern(HelpImages.Help_PrivacyPage).similar(precision);
    }

    public static final Pattern Help_FullTopBar(Float precision){
        return new Pattern(HelpImages.Help_FullTopBar).similar(precision);
    }

    public static final Pattern Help_ProtectionModesExplanation(Float precision){
        return new Pattern(HelpImages.Help_ProtectionModesExplanation).similar(precision);
    }

    public static final Pattern Help_HelpPage(Float precision){
        return new Pattern(HelpImages.Help_HelpPage).similar(precision);
    }

    public static final Pattern Help_TopActivityBar(Float precision){
        return new Pattern(HelpImages.Help_TopActivityBar).similar(precision);
    }
}

class HelpImages{

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String HotspotShield_Activities_Help = absoluteAIMPath + "Hotspot Shield\\Activities\\Help\\";
    public static final String Help_FullTopBar = HotspotShield_Activities_Help + "fullTopBar.png";
    public static final String Help_ProtectionModesExplanation = HotspotShield_Activities_Help + "helpDialogProtectionModes.png";
    public static final String Help_HelpPage = HotspotShield_Activities_Help + "helpPage.png";
    public static final String Help_TopActivityBar = HotspotShield_Activities_Help + "helpTopActivityBar.png";
    public static final String Help_TermsOfServicePage = HotspotShield_Activities_Help + "terms.png";
    public static final String Help_PrivacyPage = HotspotShield_Activities_Help + "privacy.png";
}