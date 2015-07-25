package unittests;

import aspirin.framework.core.actionobjects.android.ChromeActions;
import aspirin.framework.core.actionobjects.hotspotshield.AccountActions;
import aspirin.framework.core.actionobjects.hotspotshield.VLActions;
import aspirin.framework.core.functional.PresetFUN;
import aspirin.framework.core.pageobjects.android.ChromeObjects;
import aspirin.framework.core.utilities.SpecialUtils;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by a.spirin on 7/15/2015.
 */
public class Chrome {

    @Before
    public void preset() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void Reset() throws FindFailed {

        ChromeActions.Reset();
    }

    @Test
    public void checkCountryCode_JP() throws FindFailed {

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        VLActions.changeCountry(VLActions.JAPAN);
        ChromeActions.Open(SpecialUtils.FINDIPINFO);
        Assert.assertNotNull(new Screen().exists(ChromeObjects.Chrome_Findipinfo_JP(0.80f)));
        //new Screen().wait(ChromeObjects.Chrome_Findipinfo_JP(0.80f), 10);
    }

    @Test
    public void checkCountryCode_USA() throws FindFailed {

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        VLActions.changeCountry(VLActions.USA);
        ChromeActions.Open(SpecialUtils.FINDIPINFO);
        Assert.assertNotNull(new Screen().exists(ChromeObjects.Chrome_Findipinfo_US(0.80f)));
        //new Screen().wait(ChromeObjects.Chrome_Findipinfo_JP(0.80f), 10);
    }
}
