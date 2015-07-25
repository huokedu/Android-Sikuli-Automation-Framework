package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.AccountActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/15/2015.
 */
public class Account {

    @Before
    public void SetUp() throws FindFailed {

        PresetFUN.MainActivity(false);
        System.out.println("Done");
    }

    @Test
    public void SignIn_Elite() throws FindFailed {

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
    }

    @Test
    public void SignIn_Free() throws FindFailed {

        AccountActions.signIn(AccountActions.FREE_ACCOUNT, 2);
    }

    @Test
    public void SignIn_Limit() throws FindFailed {

        AccountActions.signIn(AccountActions.ACCOUNT_WITH_FIVE_DEVICES, 3);
    }

    @Test
    public void SignOut() throws FindFailed {

        AccountActions.signOut();
    }
}
