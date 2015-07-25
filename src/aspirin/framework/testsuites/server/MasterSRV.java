package aspirin.framework.testsuites.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Artur on 7/12/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        SRV_SuiteOne.class,
        SRV_SuiteTwo.class})
public class MasterSRV {

}
