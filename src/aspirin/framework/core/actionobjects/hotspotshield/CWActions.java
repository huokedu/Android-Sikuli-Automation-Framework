package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.core.pageobjects.android.ChromeObjects;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by Artur Spirin on 7/23/2015.
 */
public class CWActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static void clickGooglePlay() throws FindFailed {

        SCREEN.click(UniversalObjects.Universal_ContentWall_GooglePlay(AP));
        try{
            SCREEN.wait(ChromeObjects.Chrome_Dialog_UpgradeToElite_MonthlyElite(AP), 25);
        }catch (FindFailed e){
            throw new FindFailed("CW with purchase options failed to load! " + e);
        }
    }

    public static void clickOtherOptions() throws FindFailed {

        SCREEN.click(UniversalObjects.Universal_ContentWall_OtherOptions(AP));
        try{
            SCREEN.wait(UniversalObjects.Universal_OtherOptions_WebView(AP), 25);
        }catch (FindFailed e){
            throw new FindFailed("CW with purchase options failed to load! " + e);
        }
    }

    public static void clickMonthlyElite() throws FindFailed {

        SCREEN.click(ChromeObjects.Chrome_Dialog_UpgradeToElite_MonthlyElite(AP));
        try{
            SCREEN.wait(MainObjects.Main_Dialog_Subscription_499(AP), 25);
        }catch (FindFailed e){
            if(SCREEN.exists(UniversalObjects.Universal_Button_Continue(AP)) != null){
                System.out.println("Functionality works, PASSING!");
            }else{
                throw new FindFailed("Monthly Elite, Functionality does not work or it took too long to load Google Play subscription options! " + e);
            }
        }
    }

    public static void clickYearlyElite() throws FindFailed {

        SCREEN.click(ChromeObjects.Chrome_Dialog_UpgradeToElite_YearlyElite(AP));
        try{
            SCREEN.wait(MainObjects.Main_Dialog_Subscription_2999(AP), 25);
        }catch (FindFailed e){
            if(SCREEN.exists(UniversalObjects.Universal_Button_Continue(AP)) != null){
                System.out.println("Functionality works, PASSING!");
            }else{
                throw new FindFailed("Yearly Elite, Functionality does not work or it took too long to load Google Play subscription options! " + e);
            }
        }
    }
}
