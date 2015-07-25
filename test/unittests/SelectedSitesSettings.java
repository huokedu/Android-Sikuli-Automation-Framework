package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.AccountActions;
import aspirin.framework.core.actionobjects.hotspotshield.SelectedSitesActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/15/2015.
 */
public class SelectedSitesSettings {

    @Before
    public void SetUp() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void EnableMediaCategory() throws FindFailed {

        SelectedSitesActions.MediaCategory(true);
    }

    @Test
    public void DisableMediaCategory() throws FindFailed {

        SelectedSitesActions.MediaCategory(false);
    }

    @Test
    public void AddCoarsepowder() throws FindFailed {

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.AddCoarsePowderDomain(false);
    }

    @Test
    public void RemoveCoarsepowder() throws FindFailed {

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.RemoveCoarsePowderDomain();
    }
}
