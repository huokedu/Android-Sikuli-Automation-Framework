package unittests;

import aspirin.framework.core.utilities.SpecialUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;

/**
 * Created by a.spirin on 7/15/2015.
 */

@FixMethodOrder()
public class Installation {

    @Test
    public void Uninstall(){

        SpecialUtils.uninstallApplication(SpecialUtils.HOTSPOTSHIELD_PACKAGE_NAME);
    }

    @Test
    public void Install(){

        String path = SpecialUtils.createPathToHSSAPK("v371_debug_google_07-15_14-10_dev.apk");
        SpecialUtils.installApplication(path, true);
    }
}
