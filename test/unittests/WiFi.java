package unittests;

import aspirin.framework.core.actionobjects.android.WiFiActions;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/15/2015.
 */
public class WiFi {

    @Test
    public void TurnWiFiON() throws FindFailed {

        WiFiActions.turnWiFiOn();
    }

    @Test
    public void TurnCellON() throws FindFailed {

        WiFiActions.turnCellDataOn();
    }

    @Test
    public void ConnectToPrivate() throws FindFailed {

        WiFiActions.connectTo(1);
    }

    @Test
    public void ConnectToGuest() throws FindFailed {

        WiFiActions.connectTo(2);
    }

    @Test
    public void ConnectToQANOPASS() throws FindFailed {

        WiFiActions.connectTo(3);
    }
}
