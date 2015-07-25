import aspirin.framework.core.pageobjects.android.WiFiObjects;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import java.io.IOException;

/**
 * Created by Artur on 7/11/2015.
 */
public class Dummy {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    public static void main(String[] args) throws FindFailed, IOException {

        Pattern AccessPoint_ID;
        AccessPoint_ID = WiFiObjects.ANCHORFREE_PRIVATE(0.80f);
        Region region = new Screen().find(AccessPoint_ID);
        region.below(20).highlight(20);


    }
}
