package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.CWActions;
import aspirin.framework.core.actionobjects.hotspotshield.TestDebugActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by a.spirin on 7/23/2015.
 */
public class CW {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    @Before
    public void preset() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void GooglePlay() throws FindFailed {

        TestDebugActions.openAppWall();
        CWActions.clickGooglePlay();
    }

    @Test
    public void OtherOptions() throws FindFailed {

        TestDebugActions.openAppWall();
        CWActions.clickOtherOptions();
    }

    @Test
    public void GooglePlay_Monthly() throws FindFailed {

        TestDebugActions.openAppWall();
        CWActions.clickGooglePlay();
        CWActions.clickMonthlyElite();
    }

    @Test
    public void GooglePlay_Yearly() throws FindFailed {

        TestDebugActions.openAppWall();
        CWActions.clickGooglePlay();
        CWActions.clickYearlyElite();
    }
}
