package aspirin.framework.core.actionobjects.hotspotshield;

import aspirin.framework.core.pageobjects.hotspotshield.TestDebugObjects;
import aspirin.framework.core.pageobjects.hotspotshield.UniversalObjects;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by Artur on 7/9/2015.
 */
public class TestDebugActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static void openAppWall() throws FindFailed {

        MenuActions.openTestDebug();
        try{
            SCREEN.click(TestDebugObjects.APP_WALL(AP));
            SCREEN.waitVanish(TestDebugObjects.APP_WALL(AP), 5);
            if(SCREEN.exists(TestDebugObjects.APP_WALL(AP))!=null){
                SCREEN.click(TestDebugObjects.APP_WALL(AP));
                SCREEN.waitVanish(TestDebugObjects.APP_WALL(AP), 5);
            }
            SCREEN.wait(UniversalObjects.Universal_ContentWall_GooglePlay(AP), 15);
        }catch (FindFailed e){
            throw new FindFailed("Wasnt able to open AppWall! " + e);
        }
    }
}
