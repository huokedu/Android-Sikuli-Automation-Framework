package aspirin.framework.core.pageobjects.misc;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/10/2015.
 */
public final class MobizenObjects {

    public static final Pattern Mobizen_HiberateScreen_PowerButton_Active(Float precision){
        return new Pattern(MobizenImages.Mobizen_HiberateScreen_PowerButton_Active).similar(precision);
    }

    public static final Pattern Mobizen_HiberateScreen_PowerButton_Inactive(Float precision){
        return new Pattern(MobizenImages.Mobizen_HiberateScreen_PowerButton_Inactive).similar(precision);
    }

    public static final Pattern Mobizen_HiberateScreen_DeviceLockScreen(Float precision){
        return new Pattern(MobizenImages.Mobizen_HiberateScreen_DeviceLockScreen).similar(precision);
    }

    public static final Pattern Mobizen_TopBar(Float precision){
        return new Pattern(MobizenImages.Mobizen_TopBar).similar(precision);
    }
}

class MobizenImages{

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String Mobizen = absoluteAIMPath + "Mobizen\\";
    public static final String Mobizen_HiberateScreen_PowerButton_Active = Mobizen + "hibernatePowerButtonActive.png";
    public static final String Mobizen_HiberateScreen_PowerButton_Inactive = Mobizen + "hibernatePowerButtonInactive.png";
    public static final String Mobizen_HiberateScreen_DeviceLockScreen = Mobizen + "lockScreen.png";
    public static final String Mobizen_TopBar = Mobizen + "topBar.png";
}