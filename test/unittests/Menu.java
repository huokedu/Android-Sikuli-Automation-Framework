package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.MenuActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/15/2015.
 */
public class Menu {

    @Before
    public void setUp() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void OpenMenu() throws FindFailed {

        MenuActions.openMenu();
    }

    @Test
    public void OpenMyAccount() throws FindFailed {

        MenuActions.openMyAccount();
    }

    @Test
    public void OpenSignIn() throws FindFailed {

        MenuActions.openSignIn();
    }

    @Test
    public void OpenSmartModeSettings() throws FindFailed {

        MenuActions.openSmartModeSettings();
    }

    @Test
    public void OpenCurrentNetwork() throws FindFailed {

        MenuActions.openCurrentNetwork();
    }

    @Test
    public void OpenGeneralSettings() throws FindFailed {

        MenuActions.openGeneralSettings();
    }

    @Test
    public void OpenHelp() throws FindFailed {

        MenuActions.openHelp();
    }

    @Test
    public void ClickTerms() throws FindFailed {

        MenuActions.clickTerms();
    }

    @Test
    public void ClickPrivacy() throws FindFailed {

        MenuActions.clickPrivacy();
    }

    @Test
    public void OpenShare() throws FindFailed {

        MenuActions.openShare();
    }

    @Test
    public void PauseProtection() throws FindFailed {

        MenuActions.pauseProtection();
    }

    @Test
    public void OpenNetworkActivity() throws FindFailed {

        MenuActions.openNetworkActivities();
    }

    @Test
    public void OpenSelectedSitesSettings() throws FindFailed {

        MenuActions.openSelectedSites();
    }

    /*@Test
    public void Quit(){

        MenuActions.quit();
    }*/
}
