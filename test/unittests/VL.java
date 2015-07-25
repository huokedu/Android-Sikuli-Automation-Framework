package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.AccountActions;
import aspirin.framework.core.actionobjects.hotspotshield.VLActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/23/2015.
 */
public class VL {

    @Before
    public void preset() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void ConnectTo_USA() throws FindFailed {

        VLActions.changeCountry(VLActions.USA);
    }

    @Test
    public void ConnectTo_JP() throws FindFailed {

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        VLActions.changeCountry(VLActions.JAPAN);
    }

    @Test
    public void ConnectTo_UK() throws FindFailed {

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        VLActions.changeCountry(VLActions.UK);
    }
}
