package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/10/2015.
 */
public final class SplashScreenObjects {

    public static final Pattern FirstLaunch_FirstScreen(Float precision){
        return new Pattern(SplashScreenImages.FirstLaunch_FirstScreen).similar(precision);
    }

    public static final Pattern FirstLaunch_SecondScreen(Float precision){
        return new Pattern(SplashScreenImages.FirstLaunch_SecondScreen).similar(precision);
    }

    public static final Pattern FirstLaunch_SecondScreen_FullMode(Float precision){
        return new Pattern(SplashScreenImages.FirstLaunch_SecondScreen_FullMode).similar(precision);
    }

    public static final Pattern FirstLaunch_SecondScreen_SmartMode(Float precision){
        return new Pattern(SplashScreenImages.FirstLaunch_SecondScreen_SmartMode).similar(precision);
    }

    public static final Pattern FirstLaunch_FirstScreen_NextButtonText(Float precision){
        return new Pattern(SplashScreenImages.FirstLaunch_FirstScreen_NextButtonText).similar(precision);
    }

    public static final Pattern FirstLaunch_FirstScreen_NextButton(Float precision){
        return new Pattern(SplashScreenImages.FirstLaunch_FirstScreen_NextButton).similar(precision);
    }

    public static final Pattern FirstLaunch_MainActivity_DiscountDialog(Float precision){
        return new Pattern(SplashScreenImages.FirstLaunch_MainActivity_DiscountDialog).similar(precision);
    }
}

class SplashScreenImages{

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String HotspotShield_FirstLaunch = absoluteAIMPath + "Hotspot Shield\\First Launch\\";
    public static final String FirstLaunch_FirstScreen = HotspotShield_FirstLaunch + "firstScreen.png";
    public static final String FirstLaunch_SecondScreen = HotspotShield_FirstLaunch + "secondScreen.png";
    public static final String FirstLaunch_SecondScreen_FullMode = HotspotShield_FirstLaunch + "secondScreenFullMode.png";
    public static final String FirstLaunch_SecondScreen_SmartMode = HotspotShield_FirstLaunch + "secondScreenSmartModepng.png";
    public static final String FirstLaunch_FirstScreen_NextButtonText = HotspotShield_FirstLaunch + "nextButtonText.png";
    public static final String FirstLaunch_FirstScreen_NextButton = HotspotShield_FirstLaunch + "nextFullButton.png";
    public static final String FirstLaunch_MainActivity_DiscountDialog = HotspotShield_FirstLaunch + "discountDialog.png";
}