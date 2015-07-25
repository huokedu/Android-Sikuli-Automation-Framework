package aspirin.framework.testsuites.functional;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.MainActions;
import aspirin.framework.core.actionobjects.hotspotshield.MenuActions;
import aspirin.framework.core.actionobjects.hotspotshield.SmartModeSettingsActions;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.functional.PresetFUN;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
import aspirin.framework.core.utilities.SpecialUtils;
import aspirin.framework.testsuites.Result;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Artur Spirin on 7/24/15.
 */
public class WiFiNetworkToWiFiNetwork {

    public static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "WiFi to WiFi";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

    // Variables for use with the Smart Settings class for readability
    private static final Integer SAFE_NETWORK = 1;
    private static final Integer UNSAFE_NETWORK = 2;
    private static final Integer CURRENT_NETWORK = 4;
    private static final Integer TO_FULL = 1;
    private static final Integer TO_SELECTED_SITES = 2;
    private static final Integer TO_OFF = 3;

    // Variables for use WiFi actions class and readability
    private static final Integer ANCHORFREE_PRIVATE = 1;
    private static final Integer ANCHORFREE_GUEST = 2;
    private static final Integer QA_NO_PASS = 3;


    // Local Flags
    private static Boolean doubleCheckWiFi = true;

    @BeforeClass // Runs only once at the very beginning
    public static void preSet() {

        System.out.println("---------------preSet----------------");
        System.out.println("TEST SUITE: " + TEST_SUITE_ID);
        SCREEN.setAutoWaitTimeout(3);
        System.out.println("-------------preSet Done-------------\n\n");
    }

    @Before // Runs before each test
    public void SetUp() throws FindFailed, IOException {

        Global.DEBUGOUTPUT();

        START_TIME = Global.DATE_FORMAT.format(new Date());
        ACTUAL_RESULT = null;
        TEST_LINK_ID = null;
        TEST_NAME = "SET UP";

        if(doubleCheckWiFi){
            WiFiActions.connectTo(ANCHORFREE_PRIVATE);
            doubleCheckWiFi = false;
        }
        if(Global.installApp()){
            PresetFUN.install();
        }
        PresetFUN.MainActivity(Global.setDebugFUN());
        if(Global.setDebugFUN()){
            PresetFUN.setDebugOptions();
            Runner.setHssVersion();
            Runner.setProtocol();
        }
        PresetFUN.resetSmartSettings();
        SpecialUtils.clearAndroidLog();
        System.out.println("------------setUp Done--------------\n\n");
    }

    @Rule
    public Retry retry = new Retry(3);

    @Rule
    public TestRule listen = new TestWatcher() {

        @Override
        public void failed(Throwable t, Description description) {

            doubleCheckWiFi = true;
            NOTES = NOTES + FailHandler.formatNotes(t.getMessage());
            TEST_LINK_ID = description.getMethodName().replaceAll("_","-");
            FailHandler.takeDump(TEST_LINK_ID, t, true, true, true);
            String path = FailHandler.moveLocalFailDir(TEST_LINK_ID);
            AutomationQADB.GET_pushTestResult(START_TIME, TEST_SUITE_ID, TEST_LINK_ID, TEST_NAME, "FAIL", NOTES, Global.EX_TYPE_FUN, path);
        }

        @Override
        public void succeeded(Description description) {

            TEST_LINK_ID = description.getMethodName().replaceAll("_","-");
            AutomationQADB.GET_pushTestResult(START_TIME, TEST_SUITE_ID, TEST_LINK_ID, TEST_NAME, "PASS", NOTES, Global.EX_TYPE_FUN, null);
        }
    };

    @After
    public void tearDown() throws IOException {

        NOTES = String.format("EXPECTED: %s ACTUAL: %s ", EXPECTED_RESULT, ACTUAL_RESULT);
    }

    @Test
    public void ACH_441() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to SAFE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_442() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to SAFE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_443() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to SAFE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_444() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to SAFE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_445() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to SAFE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_446() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to SAFE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_447() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to UNSAFE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_448() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to UNSAFE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_449() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to UNSAFE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_450() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to UNSAFE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_451() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to UNSAFE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_452() throws InterruptedException, FindFailed {

        TEST_NAME = "SITES to UNSAFE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(QA_NO_PASS);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_465() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to SAFE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_466() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to SAFE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_467() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to SAFE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_468() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to SAFE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_469() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to SAFE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_470() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to SAFE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_471() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to UNSAFE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_472() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to UNSAFE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_473() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to UNSAFE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_474() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to UNSAFE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_475() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to UNSAFE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_476() throws InterruptedException, FindFailed {

        TEST_NAME = "OFF to UNSAFE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(QA_NO_PASS);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(QA_NO_PASS);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_410() throws InterruptedException, FindFailed {

        TEST_NAME = "SELECTED SITES - WIFI -> WIFI";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SELECTED_SITES_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectSelectedSitesMode();
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_413() throws InterruptedException, FindFailed {

        TEST_NAME = "FULL - WIFI -> WIFI";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_FULL_MODE;

        WiFiActions.connectTo(QA_NO_PASS);
        MainActions.connectFullMode();
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_229() throws InterruptedException, FindFailed {

        TEST_NAME = "For this network";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        Result.disconnected();
        WiFiActions.connectTo(ANCHORFREE_GUEST);

        Result.connectedSmartMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_45() throws InterruptedException, FindFailed {

        TEST_NAME = "Switch Networks: WiFi -> WiFi";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MainActions.connectFullMode();
        WiFiActions.connectTo(ANCHORFREE_GUEST);

        Result.connectedFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_417() throws FindFailed {

        TEST_NAME = "FULL to SAFE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_418() throws FindFailed {

        TEST_NAME = "FULL to SAFE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_419() throws FindFailed {

        TEST_NAME = "FULL to SAFE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_420() throws FindFailed {

        TEST_NAME = "FULL to SAFE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_421() throws FindFailed {

        TEST_NAME = "FULL to SAFE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_422() throws FindFailed {

        TEST_NAME = "FULL to SAFE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.connectTo(ANCHORFREE_GUEST);
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.connectTo(ANCHORFREE_PRIVATE);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_231()throws FindFailed {

        TEST_NAME = "Until I press Start";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.connectTo(ANCHORFREE_PRIVATE);
        MenuActions.pauseProtection();
        SCREEN.click(MainObjects.Main_ModeSelector_KeepProtectionOff_UntillIPressStart_Unchecked(AP));
        WiFiActions.connectTo(QA_NO_PASS);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }


}
