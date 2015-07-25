package aspirin.framework.testsuites.elite;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.AccountActions;
import aspirin.framework.core.actionobjects.hotspotshield.GeneralSettingsActions;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.elite.PresetElite;
import aspirin.framework.core.pageobjects.hotspotshield.AccountObjects;
import aspirin.framework.core.server.PresetSRV;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Artur on 7/18/2015.
 */
public class AcceptanceElite {

    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "Elite Acceptance";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

    private static final Float AP = 0.80f;

    // Variables for use with the Smart Settings class for readability
    private static final Integer ANCHORFREE_PRIVATE = 1;

    // Local Flags
    private static Boolean doubleCheckWiFi = true;

    @BeforeClass // Runs only once at the very beginning
    public static void preSet() {

        System.out.println("---------------preSet----------------");
        System.out.println("TEST SUITE: " + TEST_SUITE_ID);
        System.out.println("-------------preSet Done-------------\n\n");
    }

    @Before // Runs before each test
    public void SetUp() throws FindFailed, IOException {

        System.out.println("---------------setUP----------------");
        START_TIME = Global.DATE_FORMAT.format(new Date());
        // Wiping vars before each test to make sure all IDs and results are unique and if not we can ID which ones are missing values
        ACTUAL_RESULT = null;
        TEST_NAME = "SET UP";
        TEST_LINK_ID = null;
        /**
         *  This is where we are going to set the debug options. This needs to be done only once before each test cycle
         *  or after you clear data so we have flags in Global class that let us know when we need to do this. (AUTO-1762)
         **/
        if(doubleCheckWiFi){
            WiFiActions.connectTo(ANCHORFREE_PRIVATE);
            doubleCheckWiFi = false;
        }
        PresetSRV.mainActivity(Global.setDebugELITE());
        if(Global.setDebugELITE()){
            PresetElite.setDebugOptions(Runner.HOST, Runner.ENABLE_HYDRA);
            Runner.setHssVersion();
            Runner.setProtocol();

        }
        System.out.println("------------setUp Done--------------\n\n");
    }

    @Rule
    public Retry retry = new Retry(1); // If test fails it will be retested up to x times

    @Rule
    public TestRule listen = new TestWatcher() {

        @Override
        public void failed(Throwable t, Description description) {

            System.out.println("[TestRule / failed] Test FAILED!");
            NOTES = NOTES + FailHandler.formatNotes(t.getMessage());
            TEST_LINK_ID = description.getMethodName().replaceAll("_","-");
            FailHandler.takeDump(TEST_LINK_ID, t, true, true, true);
            String path = FailHandler.moveLocalFailDir(TEST_LINK_ID);
            AutomationQADB.GET_pushTestResult(START_TIME, TEST_SUITE_ID, TEST_LINK_ID, TEST_NAME, "FAIL", NOTES, Global.EX_TYPE_SRV, path);
        }

        @Override
        public void succeeded(Description description) {

            System.out.println("[TestRule / succeeded] Test PASSED!");
            TEST_LINK_ID = description.getMethodName().replaceAll("_","-");
            AutomationQADB.GET_pushTestResult(START_TIME, TEST_SUITE_ID, TEST_LINK_ID, TEST_NAME, "PASS", NOTES, Global.EX_TYPE_SRV, null);
        }
    };

    @After
    public void tearDown() throws IOException {

        NOTES = String.format("EXPECTED: %s ACTUAL: %s ", EXPECTED_RESULT, ACTUAL_RESULT);
    }

    @Test
    public void SignInFree() throws FindFailed {

        TEST_NAME = "Sign In with FREE Account";
        EXPECTED_RESULT = "User is signed in with FREE Account";
        AccountActions.signOut();
        AccountActions.signIn(AccountActions.FREE_ACCOUNT, 2);
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void SignInElite() throws FindFailed {

        TEST_NAME = "Sign In with ELITE Account";
        EXPECTED_RESULT = "User is signed in with ELITE Account";
        AccountActions.signOut();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 2);
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void SignInLimit() throws FindFailed {

        TEST_NAME = "Sign In with > 5 devices on Account";
        EXPECTED_RESULT = "User is not able to signed";
        AccountActions.signOut();
        AccountActions.signIn(AccountActions.ACCOUNT_WITH_FIVE_DEVICES, 3);
        try{
            Assert.assertNotNull(AccountObjects.Account_Dialog_DeviceLimit(AP));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Device limit warning did not pop up!");
        }
    }

    @Test
    public void SignOut() throws FindFailed {

        TEST_NAME = "Sign Out";
        EXPECTED_RESULT = "User is able to sign out";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        AccountActions.signOut();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }
}
