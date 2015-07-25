package aspirin.framework.testsuites.functional;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Artur on 7/12/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CellularNetwork.class,
        WiFiSafeNetwork.class,
        UI.class})
public class MasterFUN {
}
