package aspirin.framework.testsuites.server;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.AccountActions;
import aspirin.framework.core.actionobjects.hotspotshield.MainActions;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.server.FallbackLogic;
import aspirin.framework.core.server.PresetSRV;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
import aspirin.framework.core.utilities.SpecialUtils;
import br.eti.kinoshita.testlinkjavaapi.TestLinkAPIException;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Date;


/**
 * Created by Artur on 7/12/2015.
 */

/** For security reasons */
@FixMethodOrder
public class SRV_SuiteOne {


    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "SRV-1";
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
        PresetSRV.mainActivity(Global.setDebugSRV());
        if(Global.setDebugSRV()){
            PresetSRV.setDebugOptions(Runner.IP_IN, Runner.ENABLE_HYDRA);
            Runner.setHssVersion();
            Runner.setProtocol();
        }
        System.out.println("------------setUp Done--------------\n\n");
    }


    @Rule
    public Retry retry = new Retry(2); // If test fails it will be retested up to x times

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
    public void tearDown() throws IOException {

        NOTES = String.format("EXPECTED: %s ACTUAL: %s ", EXPECTED_RESULT, ACTUAL_RESULT);
    }

    @Test
    public void ACH_208() throws FindFailed, IOException {

        TEST_NAME = "check HTTP_X_AF_ELITE header - ELITE / Sign In";
        EXPECTED_RESULT = "ELITE HEADER: 1";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        ACTUAL_RESULT =  SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp17);
        try{
            Assert.assertTrue(ACTUAL_RESULT.equals("1"));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Got: " + ACTUAL_RESULT);
        }
    }

    @Test
    public void HSSS_224() throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, FindFailed {

        EXPECTED_RESULT = FallbackLogic.EXPECTED_MOCKUP1_FALLBACK;
        TEST_NAME = "HTTP traffic via Custom IP Elite - VPN";
        MainActions.connectFullMode();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_ELECTION_ADVISOR);
        if(IP.contains("Page did not load!")){
            ACTUAL_RESULT = "ElectionAdvisor page did not load!";
            Assert.assertTrue(false);
        }else{
            String[] results = FallbackLogic.checkMockUp3Fallback(IP, Runner.HOST);
            ACTUAL_RESULT = "ElectionAdvisor: " + results[0] + " " + IP;
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }
    }

    @Test
    public void HSSS_223() throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, FindFailed {

        EXPECTED_RESULT = FallbackLogic.EXPECTED_MOCKUP1_FALLBACK;
        TEST_NAME = "HTTPS traffic via Custom IP Elite - VPN";
        MainActions.connectFullMode();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_ELECTION_ADVISOR);
        if(IP.contains("Page did not load!")){
            ACTUAL_RESULT = "ElectionAdvisor page did not load!";
            Assert.assertTrue(false);
        }else{
            String[] results = FallbackLogic.checkMockUp3Fallback(IP, Runner.HOST);
            ACTUAL_RESULT = "ElectionAdvisor: " + results[0] + " " + IP;
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }
    }

    @Test
    public void HSSS_362() throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, FindFailed {

        TEST_NAME = "HTTPS traffic via Elite IP for Elite users - VPN";
        EXPECTED_RESULT = FallbackLogic.EXPECTED_MOCKUP1_FALLBACK + " and both IPs are the same.";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        String IP1 = SpecialUtils.getIPV4(SpecialUtils.HTTPS_FINDIPINFO);
        if(IP1.contains("Page did not load!")){
            ACTUAL_RESULT = "https Findipinfo page did not load!";
            Assert.assertTrue(false);
        }else{
            String[] results = FallbackLogic.checkMockUp1Fallback(IP1, Runner.HOST);
            ACTUAL_RESULT = "https Findipinfo: " + results[0] + " " + results[1] + " " + IP1;
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }

        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        if(IP2.contains("Page did not load!")){
            ACTUAL_RESULT = "Findipinfo page did not load!";
            Assert.assertTrue(false);
        }else{
            String[] results = FallbackLogic.checkMockUp1Fallback(IP2, Runner.HOST);
            ACTUAL_RESULT = ACTUAL_RESULT + "Findipinfo: " + results[0] + " " + results[1] + " " + IP2;
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }
        Assert.assertTrue(IP1.equals(IP2));
    }

    @Test
    public void HSSS_109() throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, FindFailed {

        TEST_NAME = "HTTP traffic via Elite IP for Elite users - VPN";
        EXPECTED_RESULT = FallbackLogic.EXPECTED_MOCKUP1_FALLBACK;
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        String IP = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        if(IP.contains("Page did not load!")){
            ACTUAL_RESULT = "Findipinfo page did not load!";
            Assert.assertTrue(false);
        }else{
            String[] results = FallbackLogic.checkMockUp1Fallback(IP, Runner.HOST);
            ACTUAL_RESULT = results[0] + " " + results[1] + " " + IP;
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }
    }

    //@Test
    public void HSSS_652() throws IOException, FindFailed {

        TEST_NAME = "HTTP traffic via Clean IPv6 for Elite users - VPN";
        EXPECTED_RESULT = "IPv6 Address";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        String IP = SpecialUtils.getIPV6(SpecialUtils.IPV6_FINDIPINFO);
        if(!IP.contains(":")){
            IP = SpecialUtils.getIPV6(SpecialUtils.IPV6_FINDIPINFO);
        }
        ACTUAL_RESULT = IP;
        try{
            Assert.assertTrue(IP.split(":").length == 7);
        }catch (AssertionError e){
            throw new AssertionError("NOT IPv6");
        }
    }

    //@Test
    public void HSSS_653() throws IOException, FindFailed {

        TEST_NAME = "HTTPS traffic via Clean IPv6 for Elite users - VPN";
        EXPECTED_RESULT = "IPv6 Address";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        String IP = SpecialUtils.getIPV6(SpecialUtils.HTTPS_IPV6_FINDIPINFO);
        if(!IP.contains(":")){
            IP = SpecialUtils.getIPV6(SpecialUtils.IPV6_FINDIPINFO);
        }
        ACTUAL_RESULT = IP;
        try{
            Assert.assertTrue(IP.split(":").length == 7);
        }catch (AssertionError e){
            throw new AssertionError("NOT IPv6");
        }
    }

    //TODO PLACE FREE USER TEST CASES BELLOW
    @Test
    public void ACH_102() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "Sign out";
        EXPECTED_RESULT = "UI Shows FREE";
        MainActions.connectFullMode();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        AccountActions.signOut();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void HSSS_110() throws FindFailed, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {

        EXPECTED_RESULT = FallbackLogic.EXPECTED_MOCKUP2_FALLBACK;
        TEST_NAME = "HTTPS traffic via Custom IP Free - VPN";
        AccountActions.signOut();
        String IP = SpecialUtils.getIPV4(SpecialUtils.HTTPS_ELECTION_GUIDE);
        if(IP.contains("Page did not load!")){
            ACTUAL_RESULT = "https ElectionGuide page did not load!";
            Assert.assertTrue(false);
        }else{
            String[] results = FallbackLogic.checkMockUp2Fallback(IP, Runner.HOST);
            ACTUAL_RESULT = results[0] + " " + IP;
            Assert.assertTrue(Boolean.parseBoolean(results[1]));
        }
    }

    @Test
    public void HSSS_111() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, FindFailed, IOException {

        String[] result;
        EXPECTED_RESULT = FallbackLogic.EXPECTED_MOCKUP4_FALLBACK + " and both IPs should be the same";
        TEST_NAME = "HTTPS traffic via Clean IP for Free users - VPN";
        AccountActions.signOut();
        String IP1 = SpecialUtils.getIPV4(SpecialUtils.HTTPS_SHOWNETWORKIP);
        if(IP1.contains("Page did not load!")){ACTUAL_RESULT = "https shownetworkip did not load!";}
        else{
            result = FallbackLogic.checkMockUp4Fallback(IP1, Runner.HOST);
            ACTUAL_RESULT = "Shownetworkip: " + result[0] + " " + result[1] + " with IP " + IP1 + " ";
            Assert.assertTrue(Boolean.parseBoolean(result[1]));
        }
        String IP2 = SpecialUtils.getIPV4(SpecialUtils.FINDIPINFO);
        if(IP2.contains("Page did not load!")){ACTUAL_RESULT = ACTUAL_RESULT + "Findipinfo did not load!";}
        else{
            result = FallbackLogic.checkMockUp4Fallback(IP2, Runner.HOST);
            ACTUAL_RESULT = ACTUAL_RESULT + "Findipinfo: " + result[0] + " " + result[1] + " with IP " + IP2;
            Assert.assertTrue(Boolean.parseBoolean(result[1]));
        }
        try{
            Assert.assertTrue(IP1.equals(IP2));
        }catch (AssertionError e){
            throw new AssertionError("IPs are NOT the same!");
        }
    }

    @Test
    public void ACH_209() throws FindFailed, IOException {

        TEST_NAME = "check HTTP_X_AF_ELITE header - FREE / Sign In";
        EXPECTED_RESULT = "ELITE HEADER: 0";
        AccountActions.signIn(AccountActions.FREE_ACCOUNT, 2);
        ACTUAL_RESULT = SpecialUtils.getRSS2SearchHeader(SpecialUtils.mockUp17);
        try{
            Assert.assertTrue(ACTUAL_RESULT.equals("0"));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Got: " + ACTUAL_RESULT);
        }
    }

    //@Test
    public void HSSS_650() throws IOException, FindFailed {

        TEST_NAME = "HTTP traffic via Clean IPv6 for Free users - VPN";
        EXPECTED_RESULT = "IPv6 Address";
        AccountActions.signOut();
        String IP = SpecialUtils.getIPV6(SpecialUtils.IPV6_FINDIPINFO);
        if(!IP.contains(":")){
            IP = SpecialUtils.getIPV6(SpecialUtils.IPV6_FINDIPINFO);
        }
        ACTUAL_RESULT = IP;
        try{
            Assert.assertTrue(IP.split(":").length == 7);
        }catch (AssertionError e){
            throw new AssertionError("NOT IPv6");
        }
    }

    //@Test
    public void HSSS_651() throws IOException, FindFailed {

        TEST_NAME = "HTTPS traffic via Clean IPv6 for Free users - VPN";
        EXPECTED_RESULT = "IPv6 Address";
        AccountActions.signOut();
        String IP = SpecialUtils.getIPV6(SpecialUtils.IPV6_FINDIPINFO);
        if(!IP.contains(":")){
            IP = SpecialUtils.getIPV6(SpecialUtils.IPV6_FINDIPINFO);
        }
        ACTUAL_RESULT = IP;
        try{
            Assert.assertTrue(IP.split(":").length == 7);
        }catch (AssertionError e){
            throw new AssertionError("NOT IPv6");
        }
    }

    // Sign in/out is covered in other test cases so removing this from the test suite
    //@Test
    public void ACH_119() throws FindFailed {

        TEST_NAME = "Sign in with Elite account";
        EXPECTED_RESULT = "UI Shows ELITE";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    //@Test
    public void ACH_120() throws FindFailed, MalformedURLException, TestLinkAPIException {

        TEST_NAME = "Sign in with Free account";
        EXPECTED_RESULT = "UI Shows FREE";
        AccountActions.signIn(AccountActions.FREE_ACCOUNT, 2);
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

}
