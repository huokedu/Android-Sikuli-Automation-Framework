package aspirin.framework.testsuites.server;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.AccountActions;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.server.FallbackLogic;
import aspirin.framework.core.server.PresetSRV;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
import aspirin.framework.core.utilities.SpecialUtils;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Artur on 7/12/2015.
 */
public class SRV_SuiteTwo {


    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "SRV-2";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

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
        // DEBUG BOOLEAN PARAM
        if(false){
            PresetSRV.mainActivity(Global.setDebugSRV());
            if(Global.setDebugSRV()){
                PresetSRV.setDebugOptions(Runner.IP_IN, Runner.ENABLE_HYDRA);
                Runner.setHssVersion();
                Runner.setProtocol();
            }
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
            AutomationQADB.GET_pushTestResult(START_TIME,TEST_SUITE_ID,TEST_LINK_ID,TEST_NAME,"PASS",NOTES,Global.EX_TYPE_SRV, null);
        }
    };

    @After
    public void tearDown() {

        NOTES = String.format("EXPECTED: %s ACTUAL: %s ", EXPECTED_RESULT, ACTUAL_RESULT);
        System.out.println("[tearDown] Test finished with the following results: " + NOTES);
    }

    @Test
    public void ACH_203() throws IOException {

        EXPECTED_RESULT = "Insertion Exists";
        TEST_NAME = "Insertion";
        ACTUAL_RESULT = SpecialUtils.runCommand("adb shell /data/local/tmp/curl -A \"Mozilla/5.0 (Linux Android 4.4.2 Nexus 5 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.99 Mobile Safari/537.36\" http://stackoverflow.com | grep HSS");
        System.out.println(ACTUAL_RESULT);
        try{
            Assert.assertTrue(ACTUAL_RESULT.contains("HSSHIELD"));
            ACTUAL_RESULT = "Insertion Exists";
        }catch (AssertionError e){
            throw new AssertionError("Insertion not found!");
        }
    }

    @Test
    public void HSSS_213() throws FindFailed, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {


        EXPECTED_RESULT = FallbackLogic.EXPECTED_MOCKUP2_FALLBACK;
        TEST_NAME = "HTTP traffic via Custom IP - VPN";
        AccountActions.signOut();
        String IP = SpecialUtils.getIPV4(SpecialUtils.ELECTION_GUIDE);
        if(IP.contains("Page did not load!")){
            ACTUAL_RESULT = "ElectionGuide page did not load!";
            Assert.assertTrue(false);
        }else{
            String[] results = FallbackLogic.checkMockUp2Fallback(IP, Runner.HOST);
            ACTUAL_RESULT = "ElectionGuide: " + results[0] + " " + results[1] + " with IP " + IP + " ";
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        IP = SpecialUtils.getIPV4(SpecialUtils.ELECTION_ADVISOR);
        if(IP.contains("Page did not load!")){
            throw new NullPointerException("ElectionAdvisor page did not load!");
        }else if(IP.contains("Forbidden")){
            throw new NullPointerException("Resource is forbidden! 403");
        }else{
            String[] results = FallbackLogic.checkMockUp3Fallback(IP, Runner.HOST);
            ACTUAL_RESULT = ACTUAL_RESULT + "ElectionAdvisor: " + results[0] + " " + results[1] + " with IP " + IP + " ";
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }
    }

    //@Test // Depreciated not needed
    public void HSSS_112() throws SQLException, FindFailed, IllegalAccessException, ClassNotFoundException, InstantiationException {

        TEST_NAME = "HTTPS traffic via Dirty IP - VPN";
        String[] availableTypes = new AutomationQADB().checkAvailableTypes(Runner.HOST);
        if(availableTypes[4].equals("true")){
            String IP = SpecialUtils.getRSS2SearchHeader(SpecialUtils.HTTPS_BRAINRADIATION);
            String ipType = new AutomationQADB().checkIPType(IP);
            if(ipType.equals("dirty")){
                Assert.assertTrue(true);
            }else {
                Assert.assertTrue(false);
            }
        }
    }

    @Test
    public void HSSS_320() throws FindFailed {

        TEST_NAME = "REMOTE_ADDR";
        EXPECTED_RESULT = "IP Starts with 127. or 10.";
        String IP = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp18);
        ACTUAL_RESULT = IP;
        Assert.assertTrue(IP.startsWith("127.") | IP.startsWith("10."));
    }

    @Test
    public void HSSS_321() throws IOException {

        TEST_NAME = "HTTP_X_AF_HOST";
        EXPECTED_RESULT = "Host = " + Runner.HOST;
        String hst = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp16);
        ACTUAL_RESULT = hst;
        Assert.assertTrue(hst.equals(Runner.HOST));
    }

    @Test
    public void HSSS_322() throws IOException {

        TEST_NAME = "HTTP_X_HSS_TAG";
        EXPECTED_RESULT = "HSSCNL0IPSEC or HSSCNL000001";
        String tag = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp14);
        ACTUAL_RESULT = tag;
        Assert.assertTrue(tag.contains("HSSCNL0IPSEC") | tag.contains("HSSCNL000001"));
    }

    @Test
    public void HSSS_323() throws IOException {

        TEST_NAME = "HTTP_X_AF_SERIAL";
        EXPECTED_RESULT = "HSSHIELD00US";
        String serial = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp20);
        ACTUAL_RESULT = serial;
        Assert.assertTrue(serial.contains("HSSHIELD00US"));
    }

    @Test
    public void HSSS_324() throws IOException {

        TEST_NAME = "HTTP_X_AF_TAG";
        EXPECTED_RESULT = "HSSCNL0IPSEC or HSSCNL000001";
        String tag = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp14);
        ACTUAL_RESULT = tag;
        Assert.assertTrue(tag.contains("HSSCNL0IPSEC") | tag.contains("HSSCNL000001"));
    }

    @Test
    public void HSSS_325() throws IOException {

        TEST_NAME = "HTTP_X_AF_SRC_ADDR";
        EXPECTED_RESULT = "IP address";
        String IP = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp13);
        ACTUAL_RESULT = IP;
        Assert.assertTrue(IP.split("\\.").length == 4);
    }

    @Test
    public void HSSS_326() throws IOException {

        TEST_NAME = "HTTP_X_AF_HASH";
        EXPECTED_RESULT = "Hash";
        String hash = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp12);
        ACTUAL_RESULT = hash;
        Assert.assertTrue(hash.contains("0000000") && hash.split("").length == 32);
    }

    @Test
    public void HSSS_327() throws IOException {

        TEST_NAME = "HTTP_X_AF_SESSION_IP";
        EXPECTED_RESULT = "IP address";
        String IP = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp11);
        ACTUAL_RESULT = IP;
        Assert.assertTrue(IP.split("\\.").length == 4);
    }

    @Test
    public void HSSS_328() throws IOException {

        TEST_NAME = "HTTP_X_AF_VER";
        EXPECTED_RESULT = "HSS version = " + SpecialUtils.getHssVersion();
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp10);
        Assert.assertTrue(EXPECTED_RESULT.contains(ACTUAL_RESULT));
    }

    @Test
    public void HSSS_330() throws IOException {

        TEST_NAME = "HTTP_X_AF_C_COUNTRY";
        EXPECTED_RESULT = "US";
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp9);
        Assert.assertTrue(ACTUAL_RESULT.equals("US"));
    }

    @Test
    public void HSSS_331() throws IOException {

        TEST_NAME = "HTTP_X_AF_C_REGION";
        EXPECTED_RESULT = "California";
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp8);
        Assert.assertTrue(ACTUAL_RESULT.equals("California"));
    }

    //@Test Disabled at Viacheslav N. request Email Subject: HSSS-332 because city often changes
    public void HSSS_332() throws IOException {

        TEST_NAME = "HTTP_X_AF_C_CITY";
        EXPECTED_RESULT = "San Francisco";
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp7);
        Assert.assertTrue(ACTUAL_RESULT.equals(EXPECTED_RESULT));
    }

    @Test
    public void HSSS_333() throws IOException {

        TEST_NAME = "HTTP_X_AF_FBW_OUT_IP";
        EXPECTED_RESULT = "IP address";
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp6);
        Assert.assertTrue(ACTUAL_RESULT.split("\\.").length == 4);
    }

    @Test
    public void HSSS_334() throws IOException {

        TEST_NAME = "HTTP_X_AF_FBW_ELITE_OUT_IP";
        EXPECTED_RESULT = "IP address";
         ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp5);
        Assert.assertTrue(ACTUAL_RESULT.split("\\.").length == 4);
    }

    //@Test
    //TODO TEST NOT DONE
    public void HSSS_335() throws IOException {

        TEST_NAME = "HTTP_X_AF_ELITE_OUT_IP";
    }

    @Test
    public void HSSS_336() throws IOException {

        TEST_NAME = "HTTP_X_AF_NON_HTTP_OUT_IP";
        EXPECTED_RESULT = "IP address";
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp3);
        Assert.assertTrue(ACTUAL_RESULT.split("\\.").length == 4);
    }

    @Test
    public void HSSS_337() throws IOException {

        TEST_NAME = "HTTP_X_AF_TUNNEL";
        if(Runner.ENABLE_HYDRA){
            EXPECTED_RESULT = "l2tp";
        }else{
            EXPECTED_RESULT = "OVPN";
        }
        String tunnel = SpecialUtils.getProtocol();
        if(tunnel.contains("HYDRA")){
            tunnel = tunnel.replaceAll("HYDRA","l2tp");
        }
        ACTUAL_RESULT = tunnel;
        if(Runner.ENABLE_HYDRA){
            Assert.assertTrue(tunnel.contains("l2tp"));
        }
        else{
            Assert.assertTrue(tunnel.contains(EXPECTED_RESULT));
        }
    }

    @Test
    public void HSSS_338() throws IOException {

        TEST_NAME = "HTTP_X_AF_BRIDGE";
        EXPECTED_RESULT = "Returns null or 0";
        String value = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp4);
        ACTUAL_RESULT = value;
        Assert.assertTrue(value.equals("") | value.equals("0"));
    }

    @Test
    public void HSSS_339() throws IOException {

        TEST_NAME = "HTTP_X_AF_CUSTOM_IP";
        EXPECTED_RESULT = "IP address";
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp2);
        Assert.assertTrue(ACTUAL_RESULT.split("\\.").length == 4);
    }

    @Test
    public void HSSS_340() throws IOException {

        TEST_NAME = "HTTP_X_AF_CUSTOM_ELITE_IP";
        EXPECTED_RESULT = "IP address";
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp1);
        Assert.assertTrue(ACTUAL_RESULT.split("\\.").length == 4);
    }
}
