package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class TestDebugObjects {

    public static final Pattern DebugOptions_ResetSmartSettings(Float precision){
        return new Pattern(TestDebugImages.DebugOptions_ResetSmartSettings).similar(precision);
    }

    public static final Pattern DEBUG_MENU(Float precision){
        return new Pattern(TestDebugImages.DEBUG_MENU).similar(precision);
    }

    public static final Pattern APP_WALL(Float precision){
        return new Pattern(TestDebugImages.APP_WALL).similar(precision);
    }
}

class TestDebugImages{

    public static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String DebugOptions = absoluteAIMPath + "Hotspot Shield\\Activities\\Main Activity\\Dialogs\\Debug\\";
    public static final String DebugOptions_ResetSmartSettings = DebugOptions + "resetSmartSettings.png";
    public static final String DEBUG_MENU = DebugOptions + "debugMenu.png";
    public static final String APP_WALL = DebugOptions + "appwall.png";
}
