package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.MainActions;
import aspirin.framework.core.actionobjects.hotspotshield.MenuActions;
import aspirin.framework.core.actionobjects.hotspotshield.NotificationActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/23/2015.
 */
public class Main {

    @Before
    public void preset() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void FullMode() throws FindFailed {

        MainActions.connectFullMode();
    }

    @Test
    public void SSMode() throws FindFailed {

        MainActions.connectSelectedSitesMode();
    }

    @Test
    public void SmartMode() throws FindFailed {

        MainActions.connectSmartMode(false);
    }

    @Test
    public void Start() throws FindFailed {

        MenuActions.pauseProtection();
        MainActions.startVPN(false);
    }

    @Test
    public void Permissions() throws FindFailed {

        NotificationActions.REVOKE_PERMISSIONS();
        MainActions.startVPN(false);
    }

    @Test
    public void openVLSelector() throws FindFailed {

        MainActions.openVLSelector();
    }

    @Test
    public void openModeSelector() throws FindFailed {

        MainActions.openModeSelection();
    }
}
