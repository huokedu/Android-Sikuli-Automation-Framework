package aspirin.framework.testsuites.functional;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.ChromeActions;
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
import br.eti.kinoshita.testlinkjavaapi.TestLinkAPIException;
import junit.framework.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * Created by a.spirin on 7/16/2015.
 */
public class WiFiNetworkToCellNetwork {

    public static final Screen SCREEN = new Screen();

    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "WiFi to Cell";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

    // Variables for use with the Smart Settings class for readability
    private static final Integer MOBILE_NETWORK = 3;
    private static final Integer CURRENT_NETWORK = 4;
    private static final Integer TO_FULL = 1;
    private static final Integer TO_SELECTED_SITES = 2;
    private static final Integer TO_OFF = 3;
    private static final Integer TO_DEFAULT = 4;

    // Variables for use WiFi actions class and readability
    private static final Integer ANCHORFREE_PRIVATE = 1;

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
    public void ACH_429() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "FULL to MOBILE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_FULL, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_430() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "FULL to MOBILE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_431() throws FindFailed {

        TEST_NAME = "FULL to MOBILE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_OFF, false);
        WiFiActions.turnCellDataOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_432() throws FindFailed {

        TEST_NAME = "FULL to MOBILE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_433() throws FindFailed {

        TEST_NAME = "FULL to MOBILE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_434() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "FULL to MOBILE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        WiFiActions.turnWiFiOn();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_SELECTED_SITES, false);
        MainActions.connectSmartMode(false);
        WiFiActions.turnCellDataOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_455() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "SITES to MOBILE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_OFF, false);
        MainActions.connectSmartMode(false);
        WiFiActions.turnCellDataOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_456() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "SITES to MOBILE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnCellDataOn();
        MainActions.startVPN(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_457() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "SITES to MOBILE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_458() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "SITES to MOBILE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        WiFiActions.turnCellDataOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_480() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "OFF to MOBILE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_481() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "OFF to MOBILE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_482() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "OFF to MOBILE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        WiFiActions.turnCellDataOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_453() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "SITES to MOBILE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_FULL, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_477() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "OFF to MOBILE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_FULL, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_454() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "SITES to MOBILE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_479() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "OFF to MOBILE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_OFF, false);
        WiFiActions.turnCellDataOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_478() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "OFF to MOBILE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnCellDataOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, false);
        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.turnCellDataOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_46() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "Switch Networks: WiFi -> Cellular";
        EXPECTED_RESULT = "";

        MenuActions.pauseProtection();
        WiFiActions.turnWiFiOn();
        MainActions.startVPN(false);
        WiFiActions.turnCellDataOn();

        Result.connected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_411() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "SELECTED SITES - WIFI -> MOBILE";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SELECTED_SITES_MODE;

        WiFiActions.turnWiFiOn();
        MainActions.connectSelectedSitesMode();
        WiFiActions.turnCellDataOn();

        Result.connectedSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_414() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "FULL - WIFI -> MOBILE";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_FULL_MODE;

        WiFiActions.turnWiFiOn();
        MainActions.connectFullMode();
        WiFiActions.turnCellDataOn();

        Result.connectedFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }
}
