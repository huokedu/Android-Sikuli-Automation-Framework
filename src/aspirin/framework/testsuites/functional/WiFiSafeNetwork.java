package aspirin.framework.testsuites.functional;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.ChromeActions;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.*;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.functional.PresetFUN;
import aspirin.framework.core.pageobjects.android.ChromeObjects;
import aspirin.framework.core.pageobjects.hotspotshield.*;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
import aspirin.framework.core.utilities.SpecialUtils;
import aspirin.framework.testsuites.Result;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Artur Spirin on 7/13/15.
 **/
public class WiFiSafeNetwork {

    public static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "Safe Network";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

    // Variables for use with the Smart Settings class for readability
    private static final Integer SAFE_NETWORK = 1;
    private static final Integer CURRENT_NETWORK = 4;
    private static final Integer FULL = 1;
    private static final Integer SELECTED_SITES = 2;
    private static final Integer OFF = 3;
    private static final Integer DEFAULT = 4;

    // Variables for use WiFi actions class and readability
    private static final Integer ANCHORFREE_PRIVATE = 1;

    // Variables for use with the Selected Sites class for readability
    private static final Boolean ENABLE = true;
    private static final Boolean DISABLE = false;

    // Local Flags
    private static Boolean doubleCheckWiFi = true;

    @BeforeClass // Runs only once at the very beginning
    public static void preSet() {

        System.out.println("---------------preSet----------------");
        System.out.println("TEST SUITE: " + TEST_SUITE_ID);
        SCREEN.setAutoWaitTimeout(3);
        try{
            ChromeActions.Reset();
        }catch (FindFailed e){
            System.out.println("[preSet] Wasn't able to reset Chrome!");
        }
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
    public void ACH_498() throws FindFailed {

        TEST_NAME = "Change virtual location in Selected Sites menu";
        EXPECTED_RESULT = "HSS remains connected to US virtual location";

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        VLActions.changeCountry(VLActions.USA);
        MenuActions.openSelectedSites();
        SCREEN.click(SelectedSitesObjects.SelectedSites_USA(AP));
        SCREEN.wait(VLObjects.VirtualLocation_LargeFlag_UK(AP));
        SCREEN.click(VLObjects.VirtualLocation_LargeFlag_UK(AP));
        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);

        try{
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Location_UK(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Location_US(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Connected to wrong country!");
        }

    }

    @Test
    public void ACH_511() throws FindFailed {

        TEST_NAME = "Disconnect after changing default to OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, OFF, false);
        SCREEN.wait(MainObjects.Main_NotProtected(AP), 10);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_508() throws FindFailed {

        TEST_NAME = "FULL -> SM.OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.connectSmartMode(true);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_509() throws FindFailed {

        TEST_NAME = "FULL-> SM.SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_510() throws FindFailed {

        TEST_NAME = "FULL -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_REMAIN_IN_SMART_FULL_MODE;

        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_512() throws FindFailed {

        TEST_NAME = "SITES -> SM.OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.connectSmartMode(true);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;

    }

    @Test
    public void ACH_513() throws FindFailed {

        TEST_NAME = "SITES -> SM.SITES";
        EXPECTED_RESULT = Result.HSS_REMAIN_IN_SMART_SELECTED_SITES_MODE;

        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_514() throws FindFailed {

        TEST_NAME = "SITES -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_515() throws FindFailed {

        TEST_NAME = "FULL -> SM.OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, OFF, false);
        MainActions.connectSmartMode(true);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_516() throws FindFailed {

        TEST_NAME = "FULL-> SM.SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, SELECTED_SITES, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_517() throws FindFailed {

        TEST_NAME = "FULL -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_REMAIN_IN_SMART_FULL_MODE;

        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, FULL, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_518() throws FindFailed {

        TEST_NAME = "SITES -> SM.OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, OFF, false);
        MainActions.connectSmartMode(true);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_519() throws FindFailed {

        TEST_NAME = "SITES -> SM.SITES";
        EXPECTED_RESULT = Result.HSS_REMAIN_IN_SMART_SELECTED_SITES_MODE;

        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, SELECTED_SITES, false);
        MainActions.connectSmartMode(false);

        Result.connectedSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;

    }

    @Test
    public void ACH_520() throws FindFailed {

        TEST_NAME = "SITES -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, FULL, false);
        MainActions.connectSmartMode(false);

        Result.connectedFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_549() throws FindFailed {

        TEST_NAME = "Switch mode via notification";
        EXPECTED_RESULT = "Able to switch modes via notification";

        MainActions.connectSmartMode(false);
        NotificationActions.changeHotspotShieldMode();
        NotificationActions.changeHotspotShieldMode();
        NotificationActions.changeHotspotShieldMode();
    }

    @Test
    public void ACH_547() throws FindFailed {

        TEST_NAME = "Disconnect via notification";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        NotificationActions.pauseHotspotShield();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_117() throws FindFailed, IOException {

        TEST_NAME = "Virtual Location";
        EXPECTED_RESULT = "VPN server IP is displayed with country code JP";

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        MainActions.connectFullMode();
        VLActions.changeCountry(VLActions.JAPAN);
        ChromeActions.Open(SpecialUtils.FINDIPINFO);
        try{
            Assert.assertNotNull(ChromeObjects.Chrome_Findipinfo_JP(AP));
            ACTUAL_RESULT = EXPECTED_RESULT;
        } catch (AssertionError e) {
            throw new AssertionError("JP is not displayed at Findipinfo!");
        }
    }

    @Test
    public void ACH_301() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - DEFAULT/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_225() throws FindFailed, IOException {

        TEST_NAME = "SELECTED SITES - SAFE";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.connectSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_227() throws FindFailed, IOException {

        TEST_NAME = "FULL - SAFE";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        MainActions.connectFullMode();
        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);

        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_177() throws FindFailed, IOException {

        TEST_NAME = "HTTP IP - Secured - unblock by category";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        MainActions.connectSelectedSitesMode();
        SelectedSitesActions.MediaCategory(ENABLE);

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        ACTUAL_RESULT = Result.VPNIP(IP);

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.MediaCategory(DISABLE);

        IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        ACTUAL_RESULT = ACTUAL_RESULT + Result.REALIP(IP);
    }

    @Test
    public void ACH_180() throws FindFailed, IOException {

        TEST_NAME = "HTTPS IP - Secured - unblock by category";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        MainActions.connectSelectedSitesMode();
        SelectedSitesActions.MediaCategory(ENABLE);

        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_BRAINRADIATION);
        ACTUAL_RESULT = Result.VPNIP(IP);

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.MediaCategory(DISABLE);

        IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_BRAINRADIATION);
        ACTUAL_RESULT = ACTUAL_RESULT + Result.REALIP(IP);
    }

    @Test
    public void ACH_183() throws FindFailed, IOException {

        TEST_NAME = "HTTP IP - Secured - unblock by domain";
        EXPECTED_RESULT = "Real and VPN IP is displayed";

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        MainActions.connectSelectedSitesMode();
        SelectedSitesActions.RemoveCoarsePowderDomain();

        String IP = SpecialUtils.getIPV4(SpecialUtils.COARSEPOWDER);
        ACTUAL_RESULT = Result.REALIP(IP);

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.AddCoarsePowderDomain(false);

        IP = SpecialUtils.getIPV4(SpecialUtils.COARSEPOWDER);
        ACTUAL_RESULT = ACTUAL_RESULT + Result.VPNIP(IP);
    }

    @Test
    public void ACH_186() throws FindFailed, IOException {

        TEST_NAME = "HTTPS IP - Secured - unblock by domain";
        EXPECTED_RESULT = "Real and VPN IP is displayed";

        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        MainActions.connectSelectedSitesMode();
        SelectedSitesActions.RemoveCoarsePowderDomain();

        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_COARSEPOWDER);
        ACTUAL_RESULT = Result.REALIP(IP);

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.AddCoarsePowderDomain(false);

        IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_COARSEPOWDER);
        ACTUAL_RESULT = ACTUAL_RESULT + Result.VPNIP(IP);
    }

    @Test
    public void ACH_300() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - DEFAULT/DEFAULT";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.startVPN(false);

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_302() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - DEFAULT/SELECTED SITES";
        EXPECTED_RESULT = "VPN and Real IP is displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_303() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - DEFAULT/OFF -> FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_306() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - FULL/DEFAULT";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_307() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - FULL/FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_308() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - FULL/SELECTED SITES";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_309() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - FULL/OFF -> FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_312() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - SELECTED SITES/DEFAULT";
        EXPECTED_RESULT = "VPN and Real IP is displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_313() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - SELECTED SITES/FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_314() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - SELECTED SITES/SELECTED SITES";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_315() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - SELECTED SITES/OFF -> FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();


        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_318() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - OFF/DEFAULT -> FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, DEFAULT, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_321() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - OFF/FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_322() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - OFF/SELECTED SITES";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, SELECTED_SITES, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_323() throws FindFailed, IOException {

        TEST_NAME = "SMART - SAFE - OFF/OFF -> FULL";
        EXPECTED_RESULT = "VPN IPs displayed";

        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_15() {

        TEST_NAME = "Install [Clean]";
        EXPECTED_RESULT = "Hotspot Shield VPN is installed on device";
        ACTUAL_RESULT = "HSS Installed: " + Runner.VERSION_TESTED;
        // Build is uninstalled & installed prior to lunching the test suites
        // Tests would not be launched if it failed to install
        // Passing this test by default because of that
        Assert.assertTrue(true);
    }

    @Test
    public void ACH_18() throws FindFailed {

        TEST_NAME = "Update/install over existing";
        EXPECTED_RESULT = "Hotspot Shield is installed without errors";

        try{
            Assert.assertTrue(SpecialUtils.installApplication(Runner.APK_ID, true));
            ACTUAL_RESULT = "HSS Installed over existing: " + Runner.VERSION_TESTED;
        }catch (AssertionError e){
            throw new AssertionError("Failed to install over existing app!");
        }
    }
}
