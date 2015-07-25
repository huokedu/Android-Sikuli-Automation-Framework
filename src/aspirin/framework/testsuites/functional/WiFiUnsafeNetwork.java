package aspirin.framework.testsuites.functional;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.ChromeActions;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.*;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.utilities.SpecialUtils;
import aspirin.framework.core.functional.PresetFUN;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
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
 * Created by a.spirin on 7/16/2015.
 */
public class WiFiUnsafeNetwork {

    public static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "Unsafe Network";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

    // Variables for use with the Smart Settings class for readability
    private static final Integer UNSAFE_NETWORK = 2;
    private static final Integer CURRENT_NETWORK = 4;
    private static final Integer TO_FULL = 1;
    private static final Integer TO_SELECTED_SITES = 2;
    private static final Integer TO_OFF = 3;
    private static final Integer TO_DEFAULT = 4;

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
    public void ACH_423() throws FindFailed {

        TEST_NAME = "FULL to UNSAFE - Type FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnCellDataOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        WiFiActions.turnWiFiOn();

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_424() throws FindFailed {

        TEST_NAME = "FULL to UNSAFE - Type SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnCellDataOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        WiFiActions.turnWiFiOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_425() throws FindFailed {

        TEST_NAME = "FULL to UNSAFE - Type OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnCellDataOn();
        MainActions.connectSmartMode(false);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        WiFiActions.turnWiFiOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_426() throws FindFailed {

        TEST_NAME = "FULL to UNSAFE - Custom FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnWiFiOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        WiFiActions.turnCellDataOn();
        MainActions.connectSmartMode(false);
        WiFiActions.turnWiFiOn();

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_427() throws FindFailed {

        TEST_NAME = "FULL to UNSAFE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnWiFiOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        WiFiActions.turnCellDataOn();
        MainActions.connectSmartMode(false);
        WiFiActions.turnWiFiOn();

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_428() throws FindFailed {

        TEST_NAME = "FULL to UNSAFE - Custom OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnWiFiOn();
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        WiFiActions.turnCellDataOn();
        MainActions.connectSmartMode(false);
        WiFiActions.turnWiFiOn();

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_187() throws FindFailed, IOException {

        TEST_NAME = "HTTPS IP - Unsecured - unblock by domain";
        EXPECTED_RESULT = "Real and VPN IPs displayed";

        WiFiActions.turnWiFiOn();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.RemoveCoarsePowderDomain();
        MainActions.connectSelectedSitesMode();

        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_COARSEPOWDER);
        ACTUAL_RESULT = Result.REALIP(IP);

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.AddCoarsePowderDomain(false);

        IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_COARSEPOWDER);
        ACTUAL_RESULT = ACTUAL_RESULT + Result.VPNIP(IP);
    }

    @Test
    public void ACH_184() throws FindFailed, IOException {

        TEST_NAME = "HTTP IP - Unsecured - unblock by domain";
        EXPECTED_RESULT = "Real and VPN IP is displayed";

        WiFiActions.turnWiFiOn();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.RemoveCoarsePowderDomain();
        MainActions.connectSelectedSitesMode();

        String IP = SpecialUtils.getIPV4(SpecialUtils.COARSEPOWDER);
        ACTUAL_RESULT = Result.REALIP(IP);

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.AddCoarsePowderDomain(false);

        IP = SpecialUtils.getIPV4(SpecialUtils.COARSEPOWDER);
        ACTUAL_RESULT = ACTUAL_RESULT + Result.VPNIP(IP);
    }

    @Test
    public void ACH_181() throws FindFailed, IOException {

        TEST_NAME = "HTTPS IP - Unsecured - unblock by category";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        WiFiActions.turnWiFiOn();
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
    public void ACH_178() throws FindFailed, IOException {

        TEST_NAME = "HTTP IP - Unsecured - unblock by category";
        EXPECTED_RESULT = "VPN and Real IP displayed";

        WiFiActions.turnWiFiOn();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.connectSelectedSitesMode();

        String IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        ACTUAL_RESULT = Result.VPNIP(IP);

        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SelectedSitesActions.MediaCategory(DISABLE);

        IP = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        ACTUAL_RESULT = ACTUAL_RESULT + Result.REALIP(IP);
    }

    @Test
    public void ACH_404() throws FindFailed, IOException{

        TEST_NAME = "SELECTED SITES - UNSAFE";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        WiFiActions.turnWiFiOn();
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.connectSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_407() throws FindFailed, IOException{

        TEST_NAME = "FULL - UNSAFE";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_521() throws FindFailed{

        TEST_NAME = "FULL to SAFE - Custom SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnWiFiOn();
        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        MainActions.connectSmartMode(false);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_522() throws FindFailed{

        TEST_NAME = "FULL-> SM.SITES";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnWiFiOn();
        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        MainActions.connectSmartMode(false);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_523() throws FindFailed{

        TEST_NAME = "FULL -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_REMAIN_IN_SMART_FULL_MODE;

        WiFiActions.turnWiFiOn();
        MainActions.connectFullMode();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_524() throws FindFailed{

        TEST_NAME = "SITES -> SM.OFF";
        EXPECTED_RESULT = Result.HSS_DISCONNECTS;

        WiFiActions.turnWiFiOn();
        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        MainActions.connectSmartMode(false);

        Result.disconnected();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_525() throws FindFailed{

        TEST_NAME = "SITES -> SM.SITES";
        EXPECTED_RESULT = Result.HSS_REMAIN_IN_SMART_SELECTED_SITES_MODE;

        WiFiActions.turnWiFiOn();
        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        MainActions.connectSmartMode(false);

        Result.connectedSmartSelectedSitesMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_338() throws FindFailed, IOException{

        TEST_NAME = "SMART - UNSAFE - SELECTED SITES/DEFAULT";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_340() throws FindFailed, IOException{

        TEST_NAME = "SMART - UNSAFE - SELECTED SITES/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_348() throws FindFailed, IOException{

        TEST_NAME = "SMART - UNSAFE - OFF/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        SelectedSitesActions.MediaCategory(ENABLE);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_326() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - DEFAULT/DEFAULT";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_DEFAULT, true);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_327() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - DEFAULT/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_328() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - DEFAULT/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_329() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - DEFAULT/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_332() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - FULL/DEFAULT";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_333() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - FULL/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_334() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - FULL/SELECTED SITES";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, false);
        MainActions.startVPN(false);
        Result.connectedSmartSelectedSitesMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoREAL(IP1, IP2);
    }

    @Test
    public void ACH_335() throws FindFailed, IOException {

        TEST_NAME = "SMART - UNSAFE - FULL/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_339() throws FindFailed, IOException {

        TEST_NAME = "SMART - UNSAFE - SELECTED SITES/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_341() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - SELECTED SITES/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    //TODO Check regular http
    @Test
    public void ACH_344() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - OFF/DEFAULT -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_347() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - OFF/FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_349() throws FindFailed , IOException{

        TEST_NAME = "SMART - UNSAFE - OFF/OFF -> FULL";
        EXPECTED_RESULT = Result.BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP;

        WiFiActions.turnWiFiOn();
        MainActions.connectSmartMode(false);
        MenuActions.pauseProtection();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, false);
        MainActions.startVPN(false);
        Result.connectedSmartFullMode();

        String IP1 = SpecialUtils.getIPV4(SpecialUtils.BRAINRADIATION);
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        ACTUAL_RESULT = Result.brainradiationVPN_findipinfoVPN(IP1, IP2);
    }

    @Test
    public void ACH_526() throws FindFailed{

        TEST_NAME = "SITES -> SM.FULL";
        EXPECTED_RESULT = Result.HSS_CONNECTS_IN_SMART_FULL_MODE;

        WiFiActions.turnWiFiOn();
        MainActions.connectSelectedSitesMode();
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
        MainActions.connectSmartMode(false);

        Result.connectedSmartFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

}
