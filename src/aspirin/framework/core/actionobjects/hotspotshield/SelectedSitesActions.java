package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.core.pageobjects.hotspotshield.SelectedSitesObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

/**
 * Created by Artur on 7/9/2015.
 */
public class SelectedSitesActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;
    public static final Boolean DISABLE = false;
    public static final Boolean ENABLE = true;

    public static void MediaCategory(Boolean enable) throws FindFailed {

        if(!Global.mediaCategoryEnabled){
            Pattern pattern;
            if(enable){
                pattern = UniversalObjects.Universal_CheckBox_Unchecked(AP);
            }
            else{
                pattern = UniversalObjects.Universal_CheckBox_Checked(AP);
            }
            if(SCREEN.exists(SelectedSitesObjects.SelectedSites_TopActivityBar(AP)) != null){
                System.out.println("[MediaCategory] SELECTED SITES settings ALREADY OPEN!");
            }
            else{
                MenuActions.openSelectedSites();
            }
            SCREEN.hover(SelectedSitesObjects.SelectedSites_AddDomain(AP).targetOffset(20,-50));
            SCREEN.wheel(-1, 5);
            while (pattern != null){
                try{
                    SCREEN.click(pattern);
                    try {Thread.sleep(500);} catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }catch (FindFailed e){
                    SCREEN.wheel(1, 5);
                    break;
                }
            }
            while (pattern != null){
                try{
                    SCREEN.click(pattern);
                    try {Thread.sleep(500);} catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }catch (FindFailed e){
                    break;
                }
            }
            SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            if(enable){
                Global.setVar_mediaCategoryEnabled(true);
                System.out.println("[MediaCategory] Global Flag set to: " + true);
            }else{
                Global.setVar_mediaCategoryEnabled(false);
                System.out.println("[MediaCategory] Global Flag set to: " + false);
            }
        }else{
            System.out.println("[MediaCategory] Global Flag shows that MEDIA CATEGORY is already ENABLED");
        }
    }

    public static void AddCoarsePowderDomain(Boolean expectError) throws FindFailed {

        if(!Global.domainON){
            if(SCREEN.exists(SelectedSitesObjects.SelectedSites_TopActivityBar(AP)) != null){
                System.out.println("[AddCoarsePowderDomain] SELECTED SITES settings ALREADY OPEN!");
            }
            else{
                MenuActions.openSelectedSites();
            }
            try{
                SCREEN.hover(SelectedSitesObjects.SelectedSites_AddDomain(AP).targetOffset(20,-50));
                SCREEN.wheel(1, 5);
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                if ( SCREEN.exists(SelectedSitesObjects.SelectedSites_CoarsepowderDomain(AP)) == null){
                    SCREEN.click(SelectedSitesObjects.SelectedSites_AddDomain(AP));
                    try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                    SCREEN.type("coarsepowder.us");
                    SCREEN.type(Key.ENTER);
                }else{
                    System.out.println("[AddCoarsePowderDomain] COARSEPOWDER.US ALREADY ADDED TO the custom DOMAINS!");
                }
                if(!expectError) {
                    SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                }
                Global.setVar_domainON(true);
            }catch (FindFailed e){
                throw new FindFailed("[AddCoarsePowderDomain] FAILED to ADD COARSEPOWDER.US to the custom DOMAINS! " + e);
            }
        }
    }

    public static void RemoveCoarsePowderDomain() throws FindFailed {

        if(Global.domainON){
            if(SCREEN.exists(SelectedSitesObjects.SelectedSites_TopActivityBar(AP)) != null){
                System.out.println("[AddCoarsePowderDomain] SELECTED SITES settings ALREADY OPEN!");
            }
            else{
                MenuActions.openSelectedSites();
            }
            try{
                SCREEN.hover(SelectedSitesObjects.SelectedSites_AddDomain(AP).targetOffset(20,-50));
                SCREEN.wheel(1, 5);
                if ( SCREEN.exists(SelectedSitesObjects.SelectedSites_CoarsepowderDomain(AP)) != null){
                    SCREEN.click(UniversalObjects.Universal_MinusIcon(AP));
                }else{
                    System.out.println("[RemoveCoarsePowderDomain] COARSEPOWDER.US is NOT ON the list of custom DOMAINS");
                }
                SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
                Global.setVar_domainON(false);
            }catch (FindFailed e){
                throw new FindFailed("[RemoveCoarsePowderDomain] FAILED to REMOVE domain COARSEPOWDER.US! " + e);
            }
        }
    }
}
