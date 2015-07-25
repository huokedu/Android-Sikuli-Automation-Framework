package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class WidgetObjects {

    public static final Pattern Widget_FullFrame(Float precision){
        return new Pattern(WidgetImages.Widget_FullFrame).similar(precision);
    }

    public static final Pattern Widget_FullProtection(Float precision){
        return new Pattern(WidgetImages.Widget_FullProtection).similar(precision);
    }

    public static final Pattern Widget_SelectedSites(Float precision){
        return new Pattern(WidgetImages.Widget_SelectedSites).similar(precision);
    }

    public static final Pattern Widget_SmartProtection(Float precision){
        return new Pattern(WidgetImages.Widget_SmartProtection).similar(precision);
    }

    public static final Pattern Widget_NotProtected(Float precision){
        return new Pattern(WidgetImages.Widget_NotProtected).similar(precision);
    }

    public static final Pattern Widget_ON_OFF_Button(Float precision){
        return new Pattern(WidgetImages.Widget_ON_OFF_Button).similar(precision);
    }

    public static final Pattern Widget_HotspotShieldIcon(Float precision){
        return new Pattern(WidgetImages.Widget_HotspotShieldIcon).similar(precision);
    }
}

class WidgetImages{

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String Widget = absoluteAIMPath + "Hotspot Shield\\Widget\\";
    public static final String Widget_FullFrame = Widget + "widget.png";
    public static final String Widget_FullProtection = Widget + "widgetFullProtection.png";
    public static final String Widget_SelectedSites = Widget + "widgetSelectedSites.png";
    public static final String Widget_SmartProtection = Widget + "widgetSmartProtection.png";
    public static final String Widget_NotProtected = Widget + "widgetNotProtected.png";
    public static final String Widget_ON_OFF_Button = Widget + "widgetONOFFIcon.png";
    public static final String Widget_HotspotShieldIcon = Widget + "widgetHSSIcon.png";
}
