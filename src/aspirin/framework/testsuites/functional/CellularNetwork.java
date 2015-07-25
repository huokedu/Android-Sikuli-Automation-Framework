package aspirin.framework.testsuites.functional;

import org.sikuli.script.Screen;
import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.*;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.utilities.SpecialUtils;
import aspirin.framework.core.functional.PresetFUN;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
import aspirin.framework.testsuites.Result;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Artur Spirin on 7/16/2015.
 **/
public class CellularNetwork {


    public static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "Cell Network";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

    // Variables for use with the Smart Settings class for readability
    private static final Integer MOBILE_NETWORK = 3;
    private static final Integer CURRENT_NETWORK = 4;
    private static final Integer FULL = 1;
    private static final Integer SELECTED_SITES = 2;
    private static final Integer OFF = 3;
    private static final Integer DEFAULT = 4;

    // Variables for use with the Selected Sites class for readability
    private static final Boolean ENABLE = true;
    private static final Boolean DISABLE = false;


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

        WiFiActions.turnCellDataOn();

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
    public void ACH_527() throws FindFailed {

        TEST_NAME = "FULL -> SM.OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, OFF, false);
        MainActions.connectSmartMode(true);

        try {
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_NotProtected(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError(Result.HSS_DISCONNECT_FAIL);
        }

    }

    @Test
    public void ACH_528() throws FindFailed {

        TEST_NAME = "FULL-> SM.SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SELECTED_SITES_MODE;

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, SELECTED_SITES, false);
        MainActions.connectSmartMode(false);

        try{
            Assert.assertNull(SCREEN.exists(MainObjects.Main_NotProtected(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError(Result.HSS_CONNECTS_IN_SELECTED_SITES_MODE_FAIL);
        }

    }

    @Test
    public void ACH_529() throws FindFailed {

        TEST_NAME = "FULL -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_REMAINS_IN_FULL_MODE;

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, FULL, false);
        MainActions.connectSmartMode(false);

        try {
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError(Result.HSS_REMAINS_IN_FULL_MODE_FAIL);
        }
    }

    @Test
    public void ACH_530() throws FindFailed {

        TEST_NAME = "SITES -> SM.OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, OFF, false);
        MainActions.connectSmartMode(true);

        try {
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_NotProtected(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError(Result.HSS_DISCONNECT_FAIL);
        }
    }

    @Test
    public void ACH_531() throws FindFailed {

        TEST_NAME = "SITES -> SM.SITES";
        EXPECTED_RESULT = Result.HSS_REMAIN_IN_SELECTED_SITES_MODE;

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, SELECTED_SITES, false);
        MainActions.connectSmartMode(false);

        try{
            Assert.assertNull(SCREEN.exists(MainObjects.Main_NotProtected(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError(Result.HSS_REMAIN_IN_SELECTED_SITES_MODE_FAIL);
        }
    }

    @Test
    public void ACH_532() throws FindFailed {

        TEST_NAME = "SITES -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_FULL_MODE;

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, FULL, false);
        MainActions.connectSmartMode(false);

        try {
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError(Result.HSS_CONNECTS_IN_FULL_MODE_FAIL);
        }
    }

    @Test
    public void ACH_352() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - DEFAULT/DEFAULT";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.startVPN(false);

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Selected Sites!");
        }

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_353() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - DEFAULT/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_354() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - DEFAULT/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        MainActions.startVPN(false);

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Selected Sites!");
        }

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_355() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - DEFAULT/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_358() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - FULL/DEFAULT";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_359() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - FULL/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_360() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - FULL/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Selected Sites!");
        }

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_361() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - FULL/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_364() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - SELECTED SITES/DEFAULT";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.startVPN(false);

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Selected Sites!");
        }

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_365() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - SELECTED SITES/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_366() throws FindFailed,IOException {

        TEST_NAME = "SMART - MOBILE - SELECTED SITES/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        MainActions.startVPN(false);

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Selected Sites!");
        }

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_367() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - SELECTED SITES/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_370() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - OFF/DEFAULT -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_373() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - OFF/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_374() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - OFF/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        MainActions.startVPN(false);

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Selected Sites!");
        }

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_375() throws FindFailed, IOException {

        TEST_NAME = "SMART - MOBILE - OFF/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.startVPN(false);

        try {
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Did not connect in Smart Full mode!");
        }
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_405() throws FindFailed, IOException {

        TEST_NAME = "SELECTED SITES - MOBILE";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.connectSelectedSitesMode();
        Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);

        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_408() throws FindFailed, IOException {

        TEST_NAME = "FULL - MOBILE";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectFullMode();
        Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_VPN_IP_FAIL + IP);
        }
        IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        try {
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.FINDIPINFO_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_174() throws FindFailed, IOException {

        TEST_NAME = "HTTPS IP - Cell - unblock by category";
        EXPECTED_RESULT = Result.BRAINRADIATION_MEDIA_ON_OFF;

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.connectSelectedSitesMode();

        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_BRAINRADIATION);
        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_MEDIA_ON_VPN_IP_FAIL + IP);
        }
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.MediaCategory(DISABLE);
        IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_BRAINRADIATION);
        try{
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_MEDIA_OFF_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_175() throws FindFailed, IOException {

        TEST_NAME = "HTTP IP - Cell - unblock by domain";
        EXPECTED_RESULT = Result.COARSEPOWDER_DOMAIN_OFF_ON;

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.RemoveCoarsePowderDomain();
        MainActions.connectSelectedSitesMode();

        String IP = SpecialUtils.getIPV4(SpecialUtils.COARSEPOWDER);
        try{
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.COARSEPOWDER_DOMAIN_ON_REAL_IP_FAIL + IP);
        }
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.AddCoarsePowderDomain(false);
        IP = SpecialUtils.getIPV4(SpecialUtils.COARSEPOWDER);
        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.COARSEPOWDER_DOMAIN_ON_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_173() throws FindFailed, IOException {

        TEST_NAME = "HTTP IP - Cell - unblock by category";
        EXPECTED_RESULT = Result.BRAINRADIATION_MEDIA_ON_OFF;

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.connectSelectedSitesMode();

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_MEDIA_ON_VPN_IP_FAIL + IP);
        }
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.MediaCategory(DISABLE);
        IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        try{
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.BRAINRADIATION_MEDIA_OFF_REAL_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_176() throws FindFailed, IOException {

        TEST_NAME = "HTTPS IP - Cell - unblock by domain";
        EXPECTED_RESULT = Result.COARSEPOWDER_DOMAIN_OFF_ON;

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.RemoveCoarsePowderDomain();
        MainActions.connectSelectedSitesMode();

        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_COARSEPOWDER);
        try{
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.COARSEPOWDER_DOMAIN_ON_REAL_IP_FAIL + IP);
        }
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.AddCoarsePowderDomain(false);
        IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_COARSEPOWDER);
        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(IP));
        }catch (AssertionError e){
            throw new AssertionError(Result.COARSEPOWDER_DOMAIN_ON_VPN_IP_FAIL + IP);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }
}
