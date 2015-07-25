package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 **/

public final class ShareObjects {

    public static final Pattern Share_EmailBanner(Float precision){
        return new Pattern(ShareImages.Share_EmailBanner).similar(precision);
    }

    public static final Pattern Share_FullTopBar(Float precision){
        return new Pattern(ShareImages.Share_FullTopBar).similar(precision);
    }

    public static final Pattern Share_TopActivityBar(Float precision){
        return new Pattern(ShareImages.Share_TopActivityBar).similar(precision);
    }

    public static final Pattern Share_SelectAll_Checked(Float precision){
        return new Pattern(ShareImages.Share_SelectAll_Checked).similar(precision);
    }

    public static final Pattern Share_SelectAll_UnChecked(Float precision){
        return new Pattern(ShareImages.Share_SelectAll_UnChecked).similar(precision);
    }

    public static final Pattern Share_InviteButton(Float precision){
        return new Pattern(ShareImages.Share_InviteButton).similar(precision);
    }

    public static final Pattern Share_Contacts(Float precision){
        return new Pattern(ShareImages.Share_Contacts).similar(precision);
    }
}

class ShareImages{

    private static final String absoluteAIMPath = new Global().Img_FolderPath();

    public static final String HotspotShield_Activities_Share = absoluteAIMPath + "Hotspot Shield\\Activities\\Share\\";
    public static final String Share_Contacts = HotspotShield_Activities_Share + "contacts.png";
    public static final String Share_EmailBanner = HotspotShield_Activities_Share + "emailTop.png";
    public static final String Share_FullTopBar = HotspotShield_Activities_Share + "fullTopBar.png";
    public static final String Share_TopActivityBar = HotspotShield_Activities_Share + "shareTopActivityBar.png";
    public static final String Share_InviteButton = HotspotShield_Activities_Share + "inviteButton.png";
    public static final String Share_SelectAll_Checked = HotspotShield_Activities_Share + "selectAllChecked.png";
    public static final String Share_SelectAll_UnChecked = HotspotShield_Activities_Share + "selectAllUnChecked.png";
}
