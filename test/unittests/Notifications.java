package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.NotificationActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/23/2015.
 */
public class Notifications {

    @Before
    public void preset() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void ChangeMode_One() throws FindFailed {

        NotificationActions.changeHotspotShieldMode();
    }

    @Test
    public void ChangeMode_Two() throws FindFailed {

        NotificationActions.changeHotspotShieldMode();
    }

    @Test
     public void ChangeMode_Three() throws FindFailed {

        NotificationActions.changeHotspotShieldMode();
    }

    @Test
    public void PauseHSS() throws FindFailed {

        NotificationActions.pauseHotspotShield();
    }

    @Test
    public void Permissions() throws FindFailed {

        NotificationActions.REVOKE_PERMISSIONS();
    }

}
