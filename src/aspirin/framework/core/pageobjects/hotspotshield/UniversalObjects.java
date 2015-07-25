package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/10/2015.
 */
public class UniversalObjects {

    public static final Pattern Universal_Dialog_Continue2(Float precision){
        return new Pattern(UniversalImages.Universal_Dialog_Continue2).similar(precision);
    }

    public static final Pattern Universal_Dialog_Error(Float precision){
        return new Pattern(UniversalImages.Universal_Dialog_Error).similar(precision);
    }

    public static final Pattern Universal_OtherOptions_WebView(Float precision){
        return new Pattern(UniversalImages.Universal_OtherOptions_WebView).similar(precision);
    }

    public static final Pattern Universal_ContentWall_Web(Float precision){
        return new Pattern(UniversalImages.Universal_ContentWall_Web).similar(precision);
    }

    public static final Pattern Universal_ContentWall_GooglePlay(Float precision){
        return new Pattern(UniversalImages.Universal_ContentWall_GooglePlay).similar(precision);
    }

    public static final Pattern Universal_ContentWall_OtherOptions(Float precision){
        return new Pattern(UniversalImages.Universal_ContentWall_OtherOptions).similar(precision);
    }

    public static final Pattern Universal_ContentWall(Float precision){
        return new Pattern(UniversalImages.Universal_ContentWall).similar(precision);
    }

    public static final Pattern Universal_Arrow_QuadArrowsRightDark(Float precision){
        return new Pattern(UniversalImages.Universal_Arrow_QuadArrowsRightDark).similar(precision);
    }

    public static final Pattern Universal_HomeIcon(Float precision){
        return new Pattern(UniversalImages.Universal_HomeIcon).similar(precision);
    }

    public static final Pattern Universal_Button_Ok(Float precision){
        return new Pattern(UniversalImages.Universal_Button_Ok).similar(precision);
    }

    public static final Pattern Universal_Arrow_SingleArrowLeftLight(Float precision){
        return new Pattern(UniversalImages.Universal_Arrow_SingleArrowLeftLight).similar(precision);
    }

    public static final Pattern Universal_PleaseWaitText(Float precision){
        return new Pattern(UniversalImages.Universal_PleaseWaitText).similar(precision);
    }

    public static final Pattern Universal_Arrow_DoubleArrowDark(Float precision){
        return new Pattern(UniversalImages.Universal_Arrow_DoubleArrowDark).similar(precision);
    }

    public static final Pattern Universal_Arrow_SingleArrowRightDark(Float precision){
        return new Pattern(UniversalImages.Universal_Arrow_SingleArrowRightDark).similar(precision);
    }

    public static final Pattern Universal_CheckBox_Checked(Float precision){
        return new Pattern(UniversalImages.Universal_CheckBox_Checked).similar(precision);
    }

    public static final Pattern Universal_CheckBox_Unchecked(Float precision){
        return new Pattern(UniversalImages.Universal_CheckBox_Unchecked).similar(precision);
    }

    public static final Pattern Universal_Button_Continue(Float precision){
        return new Pattern(UniversalImages.Universal_Button_Continue).similar(precision);
    }

    public static final Pattern Universal_PlusIcon(Float precision){
        return new Pattern(UniversalImages.Universal_PlusIcon).similar(precision);
    }

    public static final Pattern Universal_MinusIcon(Float precision){
        return new Pattern(UniversalImages.Universal_MinusIcon).similar(precision);
    }

    public static final Pattern Universal_1013_Error(Float precision){
        return new Pattern(UniversalImages.Universal_1013_Error).similar(precision);
    }
}

class UniversalImages{

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String HotspotShield_Universal = absoluteAIMPath + "Hotspot Shield\\Universal\\";
    public static final String Universal_Arrow_SingleArrowRightDark = HotspotShield_Universal + "singleRightArrowDark.png";
    public static final String Universal_HomeIcon = HotspotShield_Universal + "homeIcon.png";
    public static final String Universal_PleaseWaitText = HotspotShield_Universal + "loadingIndicatorBetweenConnectionSwitches.png";
    public static final String Universal_Button_Ok = HotspotShield_Universal + "ok.png";
    public static final String Universal_Arrow_DoubleArrowDark = HotspotShield_Universal + "dualRigtArrowsDark.png";
    public static final String Universal_CheckBox_Checked = HotspotShield_Universal + "checkBoxChecked.png";
    public static final String Universal_CheckBox_Unchecked = HotspotShield_Universal + "checkBoxUnchecked.png";
    public static final String Universal_Button_Continue = HotspotShield_Universal + "continue.png";
    public static final String Universal_PlusIcon = HotspotShield_Universal + "plusIcon.png";
    public static final String Universal_MinusIcon = HotspotShield_Universal + "minusIcon.png";
    public static final String Universal_Arrow_SingleArrowLeftLight = HotspotShield_Universal + "singleLeftArrowLight.png";
    public static final String Universal_Arrow_QuadArrowsRightDark = HotspotShield_Universal + "quadArrowsRightDark.png";
    public static final String Universal_ContentWall = HotspotShield_Universal + "ContentWall.png";
    public static final String Universal_ContentWall_GooglePlay = HotspotShield_Universal + "contentWallGoogplePlay.png";
    public static final String Universal_ContentWall_OtherOptions = HotspotShield_Universal + "contentWallOtherOptions.png";
    public static final String Universal_OtherOptions_WebView = HotspotShield_Universal + "OtherOptionsWebView.png";
    public static final String Universal_ContentWall_Web = HotspotShield_Universal + "webContentWall.png";
    public static final String Universal_Dialog_Error = HotspotShield_Universal + "generic_Error_Dialog.png";
    public static final String Universal_Dialog_Continue2 = HotspotShield_Universal + "continue2.png";
    public static final String Universal_1013_Error = HotspotShield_Universal + "1013Error.png";
}
