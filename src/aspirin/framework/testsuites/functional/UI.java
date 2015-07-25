package aspirin.framework.testsuites.functional;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.ChromeActions;
import aspirin.framework.core.actionobjects.android.WiFiActions;
import aspirin.framework.core.actionobjects.hotspotshield.*;
import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.pageobjects.android.ChromeObjects;
import aspirin.framework.core.pageobjects.hotspotshield.*;
import aspirin.framework.core.utilities.SpecialUtils;
import aspirin.framework.core.functional.PresetFUN;
import aspirin.framework.core.utilities.FailHandler;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.Retry;
import br.eti.kinoshita.testlinkjavaapi.TestLinkAPIException;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.python.modules.time.Time;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * Created by a.spirin on 7/16/2015.
 */
public class UI {

    public static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    // Test Suite variables for reporting
    public static final String TEST_SUITE_ID = "UI";
    public static String EXPECTED_RESULT;
    public static String ACTUAL_RESULT;
    public static String TEST_NAME;
    public static String TEST_LINK_ID;
    public static String START_TIME;
    public static String NOTES;

    // Variables for use with the Smart Settings class for readability
    private static final Integer SAFE_NETWORK = 1;
    private static final Integer UNSAFE_NETWORK = 1;

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
    public void ACH_13() throws FindFailed {

        TEST_NAME = "Free UI";
        EXPECTED_RESULT = "User is not allowed to to change Virtual Location or add Custom Domains";
        AccountActions.signOut();
        VLActions.changeCountry(VLActions.JAPAN);
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_14() throws FindFailed {

        TEST_NAME = "Upgrade to Elite";
        EXPECTED_RESULT = "Option to purchase the product";
        AccountActions.signIn(AccountActions.FREE_ACCOUNT, 2);
        MainActions.connectFullMode();
        try{
            SCREEN.click(MainObjects.Main_BuyButton_Monthly_499(AP));
            SCREEN.wait(MainObjects.Main_Dialog_Subscription_499(AP), 20);
            SCREEN.click(UniversalObjects.Universal_Button_Continue(AP));
            SCREEN.wait(MainObjects.Main_Dialog_Subscription_MarketDialog(AP), 20);
        }catch (FindFailed e){
            throw new FindFailed("FAILED to locate PURCHASE OPTIONS! " + e);
        }
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Subscription_MarketDialog(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Wrong dialog displayed!");
        }
    }

    @Test
    public void ACH_495() throws FindFailed {

        TEST_NAME = "Virtual Location";
        EXPECTED_RESULT = "Feature is only available for Elite users";
        AccountActions.signOut();
        MainActions.openVLSelector();
        SCREEN.click(VLObjects.VirtualLocation_LargeFlag_JP(AP));
        try{
            SCREEN.wait(VLObjects.VirtualLocation_Dialog_Upgrade(AP), 5);
            Assert.assertNotNull(SCREEN.exists(VLObjects.VirtualLocation_Dialog_Upgrade(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new FindFailed("Do not see the Upgrade to Elite dialog!");
        }
    }

    @Test
    public void ACH_501() throws FindFailed {

        TEST_NAME = "Edit SELECTED SITES rules";
        EXPECTED_RESULT = "Settings change";
        AccountActions.signOut();
        MenuActions.openSelectedSites();
        try{
            SCREEN.click(SelectedSitesObjects.SelectedSites_USA(AP));
            SCREEN.wait(VLObjects.VirtualLocation_LargeFlag_UK(AP));
            SCREEN.click(VLObjects.VirtualLocation_LargeFlag_UK(AP));
        }catch (FindFailed e){
            throw new FindFailed("FAILED to select UK!");
        }
        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);

        try{
            Match CheckBox_Checked = SCREEN.exists(UniversalObjects.Universal_CheckBox_Checked(AP));
            while (CheckBox_Checked != null){
                SCREEN.click(UniversalObjects.Universal_CheckBox_Checked(AP));
                Time.sleep(0.5);
                CheckBox_Checked = SCREEN.exists(UniversalObjects.Universal_CheckBox_Checked(AP));}
            CheckBox_Checked = SCREEN.exists(UniversalObjects.Universal_CheckBox_Checked(AP));
            Assert.assertNull(CheckBox_Checked);

            Match CheckBox_Unchecked = SCREEN.exists(UniversalObjects.Universal_CheckBox_Unchecked(AP));
            while (CheckBox_Unchecked != null){
                SCREEN.click(UniversalObjects.Universal_CheckBox_Unchecked(AP));
                Time.sleep(0.5);
                CheckBox_Unchecked = SCREEN.exists(UniversalObjects.Universal_CheckBox_Unchecked(AP));}
            CheckBox_Unchecked = SCREEN.exists(UniversalObjects.Universal_CheckBox_Unchecked(AP));
            Assert.assertNull(CheckBox_Unchecked);
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (FindFailed e){
            throw new FindFailed("FAILED to operate Selected Sites Settings!");
        }
    }

    @Test
    public void ACH_504() throws FindFailed {

        TEST_NAME = "Add custom domains to SELECTED SITES";
        EXPECTED_RESULT = "Feature is only available for Elite users";
        AccountActions.signOut();
        MenuActions.openSelectedSites();
        try{
            SCREEN.click(UniversalObjects.Universal_PlusIcon(AP));
            SCREEN.wait(SelectedSitesObjects.SelectedSites_Dialog_Upgrade(AP), 5);
            Assert.assertNotNull(SelectedSitesObjects.SelectedSites_Dialog_Upgrade(AP));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (FindFailed e){
            throw new FindFailed("Do not see upgrade to Elite Dialog!");
        }
    }

    @Test
    public void ACH_20() throws FindFailed {

        TEST_NAME = "Elite UI";
        EXPECTED_RESULT = "User is able to change Virtual Location and add Custom Domains";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        try {
            VLActions.changeCountry(VLActions.JAPAN);
        }catch (FindFailed e){
            throw new FindFailed("Failed to change VL");
        }
        try{
            SelectedSitesActions.AddCoarsePowderDomain(false);
        }catch (FindFailed e){
            throw new FindFailed("Failed to add domain!");
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_491() throws FindFailed {

        TEST_NAME = "Protection mode hint - full";
        EXPECTED_RESULT = "Pop-up appears with explanation of the FULL protection";
        MainActions.connectFullMode();
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            SCREEN.click(MainObjects.Main_Protected_Everything(AP));
            SCREEN.wait(MainObjects.Main_Dialog_Full_Everything(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_Dialog_Full_Protected(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_Dialog_Full_Everything(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_Dialog_Full_FullProtection(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_Dialog_Full_AllDataSecured(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_Dialog_Full_AllSitesUnblocked(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_Dialog_Full_YourIPAddressHidden(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_Dialog_Full_VIA(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            SCREEN.click(MainObjects.Main_TopBar_Share_Green(AP));
            SCREEN.wait(MainObjects.Main_Protected_Everything(AP), 5);
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Dialog_Full_Everything(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (FindFailed e){
            throw new FindFailed("Pop up did not behave as expected!");
        }
    }

    @Test
    public void ACH_492() throws FindFailed {

        TEST_NAME = "Protection mode hint - sites";
        EXPECTED_RESULT = "Pop-up appears with explanation of the SELECTED SITES protection";
        MainActions.connectSmartMode(false);
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            SCREEN.click(MainObjects.Main_Protected_SelectedSites(AP));
            SCREEN.wait(MainObjects.Main_Dialog_SelectedSites(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(AP)));
            SCREEN.click(MainObjects.Main_Dialog_SelectedSites_Protected(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(AP)));
            SCREEN.click(MainObjects.Main_Dialog_SelectedSites_SelectedSites(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(AP)));
            SCREEN.click(MainObjects.Main_Dialog_SelectedSites_VIA(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(AP)));
            SCREEN.click(MainObjects.Main_Dialog_SelectedSites_BottomWritting(0.70f));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(0.70f)));
            SCREEN.click(MainObjects.Main_TopBar_Share_Blue(AP));
            SCREEN.waitVanish(MainObjects.Main_Dialog_SelectedSites(AP), 3);
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(AP)));
            SCREEN.click(MainObjects.Main_Protected_SelectedSites(AP));
            SCREEN.wait(MainObjects.Main_Dialog_SelectedSites(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(AP)));
            SCREEN.click(MainObjects.Main_Dialog_SelectedSites_UnblockedSecured(AP));
            SCREEN.wait(SelectedSitesObjects.SelectedSites_TopActivityBar(AP), 3);
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Dialog_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(SelectedSitesObjects.SelectedSites_TopActivityBar(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (FindFailed e){
            throw new FindFailed("Pop up did not behave as expected!");
        }
    }

    @Test
    public void ACH_69() throws FindFailed {

        TEST_NAME = "Quit menu available only when disconnected";
        EXPECTED_RESULT = "Quit option is available";
        MenuActions.openMenu();
        SCREEN.wheel(1, 3);
        Assert.assertNull(SCREEN.exists(MenuObjects.Menu_Quit(0.90f)));
        SCREEN.wheel(-1, 3);
        SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
        MenuActions.pauseProtection();
        MenuActions.openMenu();
        SCREEN.wheel(1, 3);
        try{
            Assert.assertNotNull(SCREEN.exists(MenuObjects.Menu_Quit(0.90f)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            SCREEN.wheel(-1, 3);
            throw new FindFailed("Quit button is not available!");
        }
    }

    @Test
    public void ACH_502() throws FindFailed, TestLinkAPIException, MalformedURLException {

        TEST_NAME = "USE DEFAULT switch";
        EXPECTED_RESULT = "USE DEFAULT switch functions correctly";
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, 2, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, 4, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, 4, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, 2, true);
        try{
            Region region = SCREEN.find(SmartModeSettingsObjects.UNSAFE_NETWORK(0.80f)).right(150);
            Assert.assertNotNull(region.exists(SmartModeSettingsObjects.SELECTED_SITES(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Settings did not change as expected! ");
        }
    }
/* // Do not have all of the methods developed for this tests yet
    @Test
    public void ACH_550() throws FindFailed {

TEST_NAME = "";
EXPECTED_RESULT = "";
        SpecialUtils.getDoYouLoveHSSDialog();
        Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS(AP)));
    }

    @Test
    public void ACH_551() throws FindFailed {

TEST_NAME = "";
EXPECTED_RESULT = "";
        SpecialUtils.getDoYouLoveHSSDialog();
        SCREEN.click(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_NotReally(AP));
        SCREEN.wait(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_ContactSupport_Dialog(AP));
        SCREEN.click(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_VisitHelp(AP));
        SCREEN.wait(HelpObjects.Help_TopActivityBar(AP));
        Assert.assertNotNull(SCREEN.exists(HelpObjects.Help_TopActivityBar(AP)));
    }

    @Test
    public void ACH_552() throws FindFailed {

TEST_NAME = "";
EXPECTED_RESULT = "";
        SpecialUtils.getDoYouLoveHSSDialog();
        SCREEN.click(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_NotReally(AP));
        SCREEN.wait(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_ContactSupport_Dialog(AP));
        SCREEN.click(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_ContactSupport(AP));
        SCREEN.wait(ChromeObjects.Chrome_HelpCenter_SubmitRequestPage(AP), 20);
        Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_HelpCenter_SubmitRequestPage(AP)));
    }

    @Test
    public void ACH_553() throws FindFailed {

TEST_NAME = "";
EXPECTED_RESULT = "";
        SpecialUtils.getDoYouLoveHSSDialog();
        SCREEN.click(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_YesThanks(AP));
        SCREEN.wait(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_RateNow_Dialog(AP));
        SCREEN.click(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_MaybeLater(AP));
        SCREEN.waitVanish(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS_RateNow_Dialog(AP), 20);
        Assert.assertNull(SCREEN.exists(MainObjects.Main_Dialog_Reconnect_DoYouLoveHSS(AP)));
        Assert.assertNull(SCREEN.exists(ChromeObjects.Chrome_HelpCenter_SubmitRequestPage(AP)));
    }
*/
    @Test
    public void ACH_26() throws FindFailed {

        TEST_NAME = "First run & connect";
        EXPECTED_RESULT = "Main UI should appear and client should begin connecting";
        MainActions.connectFullMode();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_36() throws FindFailed {

        TEST_NAME = "Settings menu";
        EXPECTED_RESULT = " General menu opens with all the options";
        MenuActions.openGeneralSettings();
        try{
            Assert.assertNotNull(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_StartOnBoot_Unchecked(AP)));
            Assert.assertNotNull(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_NetworkNotifications_Checked(AP)));
            Assert.assertNotNull(SCREEN.exists(GeneralSettingsObjects.GeneralSettings_TurnVPNOffWhileSleep_Unchecked(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Items are missing!");
        }
    }

    @Test
    public void ACH_38() throws  FindFailed {

        TEST_NAME = "Terms of service";
        EXPECTED_RESULT = "Terms of Service page opens";
        MenuActions.clickTerms();
        try{
            Assert.assertNotNull(SCREEN.exists(HelpObjects.Help_TermsOfServicePage(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Terms did not open/display!");
        }
    }

    @Test
    public void ACH_37() throws  FindFailed {

        TEST_NAME = "Privacy policy";
        EXPECTED_RESULT = "Privacy Policy is displayed";
        MenuActions.clickPrivacy();
        try {
            Assert.assertNotNull(SCREEN.exists(HelpObjects.Help_PrivacyPage(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Privacy did not open/display!");
        }
    }

    @Test
    public void ACH_40() throws  FindFailed {

        TEST_NAME = "Help";
        EXPECTED_RESULT = "Help page loads";
        MenuActions.openHelp();
        try{
            Assert.assertNotNull(SCREEN.exists(HelpObjects.Help_HelpPage(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Help page did not open/display!");
        }
    }

    @Test
    public void ACH_65() throws  FindFailed {

        TEST_NAME = "Navigation controls";
        EXPECTED_RESULT = "App navigation controls work as expected";
        MenuActions.openShare();
        try{
            Assert.assertNotNull(SCREEN.exists(ShareObjects.Share_Contacts(AP)));
            SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            MenuActions.openHelp();
            Assert.assertNotNull(SCREEN.exists(HelpObjects.Help_HelpPage(AP)));
            SCREEN.click(UniversalObjects.Universal_Arrow_SingleArrowLeftLight(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            MenuActions.openSelectedSites();
            Assert.assertNotNull(SCREEN.exists(SelectedSitesObjects.SelectedSites_TopActivityBar(AP)));
            SCREEN.click(UniversalObjects.Universal_HomeIcon(AP));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (FindFailed e){
            throw new FindFailed("Failed to find expected element: " + e);
        }catch (AssertionError e){
            throw new AssertionError("Do not see expected element");
        }
    }

    @Test
    public void ACH_505() throws FindFailed {

        TEST_NAME = "Selected sites after sign out";
        EXPECTED_RESULT = "Selected sites screen is shown with the current country and list of categories";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        MenuActions.openSelectedSites();
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        AccountActions.signOut();
        MenuActions.openSelectedSites();

        try{
            Assert.assertNotNull(SCREEN.exists(SelectedSitesObjects.SelectedSites_USA(AP)));
            Assert.assertNotNull(SCREEN.exists(SelectedSitesObjects.SelectedSites_AddDomain(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Did not find expected element");
        }
    }

    @Test
    public void ACH_507() throws FindFailed {

        TEST_NAME = "Protection mode help";
        EXPECTED_RESULT = "Help page appears with explanations of each mode";
        MainActions.openModeSelection();
        SCREEN.click(MainObjects.Main_ModeSelector_HelpIcon(AP));
        try{
            Assert.assertNotNull(HelpObjects.Help_ProtectionModesExplanation(AP));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Wrong page is displayed!");
        }
    }

    @Test
    public void ACH_556() throws FindFailed {

        TEST_NAME = "Share shortcut";
        EXPECTED_RESULT = "Share page opens to the suggested contacts";
        MenuActions.openShare();
        try{
            Assert.assertNotNull(SCREEN.exists(ShareObjects.Share_Contacts(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Share short cut did not work!");
        }
    }

    @Test
    public void ACH_58() throws FindFailed {

        TEST_NAME = "Ongoing activity: CONFIGURE";
        EXPECTED_RESULT = "Hotspot Shield UI opens";
        MainActions.connectFullMode();
        // Android 5+ does not have configure option
        if(Runner.AGENT_OS < 500){
            SpecialUtils.openNotifications();
            SCREEN.click(NotificationObjects.Notifications_BlueHSSIcon(AP));
            SCREEN.wait(NotificationObjects.Notifications_Dialog_Buttons(AP));
            SCREEN.click(NotificationObjects.Notifications_PermissionsDialog_Configure(AP));
            SCREEN.wait(MainObjects.Main_Protected_Everything(AP), 5);
            try{
                Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
                ACTUAL_RESULT = EXPECTED_RESULT;
            }catch (AssertionError e){
                throw new AssertionError("Configure button did not open HSS UI");
            }
        }
    }

    @Test
    public void ACH_60() throws FindFailed {

        TEST_NAME = "Ongoing activity: CANCEL";
        EXPECTED_RESULT = "Ongoing activity: CANCEL";
        MainActions.connectFullMode();
        // Android 5+ does not have cancel
        if(Runner.AGENT_OS < 500){
            SpecialUtils.openNotifications();
            SCREEN.click(NotificationObjects.Notifications_BlueHSSIcon(AP));
            SCREEN.wait(NotificationObjects.Notifications_Dialog_Buttons(AP));
            SCREEN.click(NotificationObjects.Notifications_PermissionsDialog_Cancel(AP));
            SCREEN.wait(MainObjects.Main_Protected_Everything(AP), 5);
            try{
                Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
                ACTUAL_RESULT = EXPECTED_RESULT;
            }catch (AssertionError e){
                throw new AssertionError("Cancel button did not return user back to HSS");
            }
        }
    }

    @Test
    public void ACH_95() throws FindFailed, InterruptedException {

        TEST_NAME = "Update screen";
        EXPECTED_RESULT = "User is taken to Google Play to update HSS";
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_UpgradeToTheLatestVersion(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Update tag is not on the screen!");
        }
    }

    @Test
    public void ACH_102() throws FindFailed {

        TEST_NAME = "Sign out";
        EXPECTED_RESULT = "User is signed out. UI changes to FREE UI. HSS remains connected*";
        MainActions.connectFullMode();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        AccountActions.signOut();
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_TopBar_Free_Text_Green(AP)));
        }catch (AssertionError e){
            throw new AssertionError("UI is not showing FREE");
        }
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        }catch (AssertionError e){
            throw new AssertionError("UI is not showing PROTECTED EVERYTHING");
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_119() throws FindFailed {

        TEST_NAME = "Sign in with Elite account";
        EXPECTED_RESULT = "User is signed in and UI shows Elite UI";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_TopBar_Elite_Text_Green(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_TopBar_Free_Text_Green(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("User is signed in but UI is NOT showing Elite UI");
        }
    }

    @Test
    public void ACH_120() throws FindFailed {

        TEST_NAME = "Sign in with Free account";
        EXPECTED_RESULT = "User is signed in and UI shows Free UI";
        AccountActions.signIn(AccountActions.FREE_ACCOUNT, 2);
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_TopBar_Free_Text_Green(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_TopBar_Elite_Text_Green(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("User is signed in but UI is NOT showing Free UI");
        }
    }

    @Test
    public void ACH_104() throws FindFailed {

        TEST_NAME = "Sign in with Elite account with exceeded quota";
        EXPECTED_RESULT = "Sign In is blocked. Error message is displayed to the user that account already has 5 active devices.";
        AccountActions.signIn(AccountActions.ACCOUNT_WITH_FIVE_DEVICES, 3);
        try{
            Assert.assertNotNull(SCREEN.exists(AccountObjects.Account_Dialog_DeviceLimit(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Error message did not appear!");
        }
    }

    @Test
    public void ACH_219() throws FindFailed, IOException {

        TEST_NAME = "CW - Links/buttons";
        EXPECTED_RESULT = "Purchase options appear";
        AccountActions.signOut();
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        try{
            Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Content wall is not displayed!");
        }
        try{
            SCREEN.click(UniversalObjects.Universal_ContentWall_GooglePlay(AP));
            SCREEN.wait(ChromeObjects.Chrome_Dialog_UpgradeToElite(AP), 10);
            Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_Dialog_UpgradeToElite(AP)));
        }catch (FindFailed e){
            throw new FindFailed("No Google Play buying options! " + e);
        }
        SCREEN.click(ChromeObjects.Chrome_Dialog_UpgradeToElite_MonthlyElite(AP));
        try{
            try {
                SCREEN.wait(MainObjects.Main_Dialog_Subscription_499(AP), 15);
                Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Subscription_499(AP)));
            }
            catch (FindFailed e){
                SCREEN.wait(MainObjects.Main_Dialog_Subscription_NotConfigured(AP), 5);
                Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Subscription_NotConfigured(AP)));
            }
        }catch (FindFailed e){
            throw new FindFailed("4.99 Dialog did not pop up! " + e);
        }
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        try{
            Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Content wall is not displayed!");
        }
        try{
            SCREEN.click(UniversalObjects.Universal_ContentWall_GooglePlay(AP));
            SCREEN.wait(ChromeObjects.Chrome_Dialog_UpgradeToElite(AP), 10);
            Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_Dialog_UpgradeToElite(AP)));
        }catch (FindFailed e){
            throw new FindFailed("No Google Play buying options! " + e);
        }
        SCREEN.click(ChromeObjects.Chrome_Dialog_UpgradeToElite_YearlyElite(AP));
        try{
            try{
                SCREEN.wait(MainObjects.Main_Dialog_Subscription_2999(AP), 15);
                Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Subscription_2999(AP)));
            }
            catch (FindFailed e){
                SCREEN.wait(MainObjects.Main_Dialog_Subscription_NotConfigured(AP), 5);
                Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Dialog_Subscription_NotConfigured(AP)));
            }
        }catch (FindFailed e){
            throw new FindFailed("29.99 Dialog did not pop up! " + e);
        }
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        try{
            Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
            SCREEN.click(UniversalObjects.Universal_ContentWall_OtherOptions(AP));
            SCREEN.wait(UniversalObjects.Universal_OtherOptions_WebView(AP), 20);
            Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_OtherOptions_WebView(AP)));
        }catch (FindFailed e){
            throw new FindFailed("Other payment options are not accessible! " + e);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_124() throws FindFailed {

        TEST_NAME = "Bandwidth counting - Elite";
        EXPECTED_RESULT = "The bandwidth counters appear above UPGRADE TO ELITE";
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        MainActions.connectFullMode();
        try{
            Assert.assertNull(SCREEN.exists(MainObjects.Main_UpgradeToElite(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_UploadIcon(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_DownloadIcon(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Bandwidth counter is not shown! " + e);
        }
    }

    @Test
    public void ACH_123() throws FindFailed, IOException {

        TEST_NAME = "Location after Sign Out";
        EXPECTED_RESULT = "VPN IP from United States";
        MainActions.connectFullMode();
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        VLActions.changeCountry(VLActions.JAPAN);
        ChromeActions.Open(SpecialUtils.FINDIPINFO);
        try{
            Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_Findipinfo_JP(AP)));
        }catch (AssertionError e){
            throw new AssertionError("VL did not change to JP!");
        }
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        AccountActions.signOut();
        try{
            Assert.assertNotNull(SCREEN.exists(VLObjects.VirtualLocation_Text_US(AP)));
            ChromeActions.Open(SpecialUtils.FINDIPINFO);
            Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_Findipinfo_US(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("VL did not change back to US!");
        }
    }

    @Test
    public void ACH_125() throws FindFailed {

        TEST_NAME = "Bandwidth counting - Free";
        EXPECTED_RESULT = "The bandwidth counters appear above UPGRADE TO ELITE";
        MainActions.connectFullMode();
        AccountActions.signOut();
        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_UploadIcon(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_DownloadIcon(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (AssertionError e){
            throw new AssertionError("Bandwidth counter is not visible!");
        }
    }

    @Test
    public void ACH_191() throws FindFailed, IOException {

        TEST_NAME = "No content wall for Elite users";
        EXPECTED_RESULT = "User is not redirected to CW";
        AccountActions.signOut();
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        SCREEN.wait(UniversalObjects.Universal_ContentWall_Web(AP), 10);
        Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        try{
            SCREEN.wait(ChromeObjects.Chrome_BBCiPlayer(AP), 20);
            Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_BBCiPlayer(AP)));
            ACTUAL_RESULT = EXPECTED_RESULT;
        }catch (FindFailed e){
            throw new FindFailed("CW is shown to ELITE user! " + e);
        }
    }

    @Test
    public void ACH_539() throws FindFailed, IOException {

        TEST_NAME = "CW buy button functionality [Monthly]";
        EXPECTED_RESULT = "HSS opens with the monthly subscription through Google Play";
        TestDebugActions.openAppWall();
        CWActions.clickGooglePlay();
        CWActions.clickMonthlyElite();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_207() throws FindFailed, IOException {

        TEST_NAME = "CW is shown to free users";
        EXPECTED_RESULT = "BBC iPlayer is opened without CW";
        AccountActions.signOut();
        ////SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        try{
            SCREEN.wait(UniversalObjects.Universal_ContentWall_Web(AP), 10);
            Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        }catch (FindFailed e){
            throw new FindFailed("CW did not appear for FREE user! " + e);
        }
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        ////SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        try{
            SCREEN.wait(ChromeObjects.Chrome_BBCiPlayer(AP), 20);
            Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_BBCiPlayer(AP)));
        }catch (FindFailed e){
            throw new FindFailed("CW appeared for ELITE user! " + e);
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

    @Test
    public void ACH_540() throws FindFailed, IOException {

        TEST_NAME = "CW buy button functionality [Yearly]";
        EXPECTED_RESULT = "HSS opens with the yearly subscription through Google Play";
        TestDebugActions.openAppWall();
        CWActions.clickGooglePlay();
        CWActions.clickYearlyElite();
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

/*  // Do not have all of the methods developed for this tests yet
    @Test
    public void ACH_109() throws FindFailed {

TEST_NAME = "";
EXPECTED_RESULT = "";
        AccountActions.signOut();
        AccountActions.Request_Forgotten_Password("123", 3);
        Assert.assertNotNull(SCREEN.exists(AccountObjects.Account_Dialog_EmailNotValid(AP)));
        SCREEN.click(UniversalObjects.Universal_Button_Ok(AP));
        SCREEN.click(UniversalObjects.Universal_HomeIcon(AP));
        SCREEN.wait(MainObjects.Main_TopBar_Share_Green(AP));
        Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
        AccountActions.Request_Forgotten_Password("8172638173djfsj@sdfsjh239482.sdfsf", 2);
        Assert.assertNotNull(SCREEN.exists(AccountObjects.Account_Dialog_EmailNotFound(AP)));
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        AccountActions.Request_Forgotten_Password("a.spirin.anchorfree@gmail.com", 1);
    }

    @Test
    public void ACH_210() throws FindFailed, IOException {

TEST_NAME = "";
EXPECTED_RESULT = "";
        AccountActions.signOut();
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        SCREEN.click(ChromeObjects.Chrome_ContentWall_RefreshLink(AP));
        Time.sleep(5);
        Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        ChromeActions.Chrome_Disable_JavaScript();
        SCREEN.click(ChromeObjects.Chrome_ContentWall_RefreshLink(AP));
        Time.sleep(5);
        Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        AccountActions.signIn(AccountActions.ELITE_ACCOUNT, 1);
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        Assert.assertNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        Assert.assertNotNull(SCREEN.exists(ChromeObjects.Chrome_BBCiPlayer(AP)));
    }

    @Test
    public void ACH_211() throws FindFailed, IOException {

TEST_NAME = "";
EXPECTED_RESULT = "";
        AccountActions.signOut();
        //SpecialUtils.getIPV4(SpecialUtils.BBC_IPLAYER);
        ChromeActions.Open(SpecialUtils.BBC_IPLAYER);
        Assert.assertNotNull(SCREEN.exists(UniversalObjects.Universal_ContentWall_Web(AP)));
        Assert.assertTrue(ChromeActions.Get_Visible_URL_Address().replace("I", "l").contains("anchorfree.us/cw2/?origurl="));
    }
*/

    @Test
    public void ACH_94() throws FindFailed {

        TEST_NAME = "Video Ads";
        EXPECTED_RESULT = "Ad is closed and user is returned to the screen they were at previously";
        MenuActions.openTestAds();
        try{
            SCREEN.click(MainObjects.Main_Dialog_Ads_Providers_OX(AP));
            SCREEN.wait(MainObjects.Main_Dialog_Ads_Loading(AP), 10);
            SCREEN.waitVanish(MainObjects.Main_Dialog_Ads_Loading(AP), 30);
        }catch (FindFailed e){
            throw new FindFailed("Failed to open the Ad! " + e);
        }
        Time.sleep(3);
        try{
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Location_VIA(AP)));
            SpecialUtils.androidKeyEvent(SpecialUtils.ANDROID_BACK_BTN_KEY_EVENT);
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Location_VIA(AP)));
        }catch (AssertionError e){
            throw new AssertionError("Failed to close the Ad!");
        }
        ACTUAL_RESULT = EXPECTED_RESULT;
    }

}
