package aspirin.framework.core.pageobjects.android;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/10/2015.
 **/

public final class WiFiObjects {

    public static final Pattern QA_NO_PASS(Float precision){
        return new Pattern(WiFiImages.QA_NO_PASS).similar(precision);
    }

    public static final Pattern ANCHORFREE_PRIVATE(Float precision){
        return new Pattern(WiFiImages.ANCHORFREE_PRIVATE).similar(precision);
    }

    public static final Pattern ANCHORFREE_GUEST(Float precision){
        return new Pattern(WiFiImages.ANCHORFREE_GUEST).similar(precision);
    }

    public static final Pattern DIALOG_BUTTON_CONNECT(Float precision){
        return new Pattern(WiFiImages.DIALOG_BUTTON_CONNECT).similar(precision);
    }

    public static final Pattern STATUS_CONNECTED(Float precision){
        return new Pattern(WiFiImages.STATUS_CONNECTED).similar(precision);
    }

    public static final Pattern SWITCH_STATE_ON(Float precision){
        return new Pattern(WiFiImages.SWITCH_STATE_ON).similar(precision);
    }

    public static final Pattern SWITCH_STATE_OFF(Float precision){
        return new Pattern(WiFiImages.SWITCH_STATE_OFF).similar(precision);
    }

    public static final Pattern QA_NO_PASS_DIALOG(Float precision){
        return new Pattern(WiFiImages.QA_NO_PASS_DIALOG).similar(precision);
    }

    public static final Pattern ANCHORFREE_GUEST_DIALOG(Float precision){
        return new Pattern(WiFiImages.ANCHORFREE_GUEST_DIALOG).similar(precision);
    }

    public static final Pattern ANCHORFREE_PRIVATE_DIALOG(Float precision){
        return new Pattern(WiFiImages.ANCHORFREE_PRIVATE_DIALOG).similar(precision);
    }

    public static final Pattern WIFI(Float precision){
        return new Pattern(WiFiImages.WIFI).similar(precision);
    }
}

class WiFiImages{

    private static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String WiFiNetworks = absoluteAIMPath + "WiFiNetworks\\";


    // Should be only usable images
    public static final String QA_NO_PASS = WiFiNetworks + "qa_no_pass.png";
    public static final String ANCHORFREE_GUEST = WiFiNetworks + "anchorfreeguest.png";
    public static final String ANCHORFREE_PRIVATE = WiFiNetworks + "anchorfreeprivate.png";
    public static final String DIALOG_BUTTON_CONNECT = WiFiNetworks + "connect.png";
    public static final String STATUS_CONNECTED = WiFiNetworks + "statusConnected.png";
    public static final String SWITCH_STATE_ON = WiFiNetworks + "switchIsOn.png";
    public static final String SWITCH_STATE_OFF = WiFiNetworks + "switchIsOff.png";

    // to add images for other devices
    public static final String ANCHORFREE_PRIVATE_DIALOG = WiFiNetworks + "AFPrivateDialog.png";
    public static final String ANCHORFREE_GUEST_DIALOG = WiFiNetworks + "AFGuestDialog.png";
    public static final String QA_NO_PASS_DIALOG = WiFiNetworks + "QANPDialog.png";
    public static final String WIFI = WiFiNetworks + "wifi.png";
}
