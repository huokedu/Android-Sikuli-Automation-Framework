package aspirin.framework.core.utilities;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.android.ChromeActions;
import aspirin.framework.core.pageobjects.hotspotshield.NotificationObjects;
import org.junit.runner.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * Created by Artur Spirin on 7/10/2015.
 */

/** For security reasons some of the URLs were changed */
public class SpecialUtils {

    private static final Integer SYSTEM_WAIT = 2000;
    private static final Integer WAIT_TO_PROPAGATE = 6000;
    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    // Common web addresses
    public static final String BRAINRADIATION = "http://brainradiation.us/afiptest.php?ttext";
    public static final String COARSEPOWDER = "http://coarsepowder.us/afiptest.php?ttext";
    public static final String ELECTION_ADVISOR = "http://2012electionadvisor.com/afiptest.php?ttext";
    public static final String ELECTION_GUIDE = "http://2012electionguide.com/afiptest.php?ttext";
    public static final String FINDIPINFO = "http://findipinfo.com/?ttext";
    public static final String HTTPS_FINDIPINFO = "https://findipinfo.com/?ttext";
    public static final String HTTPS_IPV6_FINDIPINFO = "https://v4v6.findipinfo.com/?ttext";
    public static final String IPV6_FINDIPINFO = "http://v4v6.findipinfo.com/?ttext";
    public static final String BBC_IPLAYER = "https://bbc.co.uk/iplayer";
    public static final String HTTPS_BRAINRADIATION = "https://brainradiation.us/afiptest.php?ttext";
    public static final String HTTPS_X_AF_Elite = "http://secretURL";
    public static final String HTTPS_COARSEPOWDER = "https://coarsepowder.us/afiptest.php?ttest";
    public static final String HTTPS_ELECTION_ADVISOR = "https://2012electionadvisor.com/afiptest.php?ttext";
    public static final String HTTPS_ELECTION_GUIDE = "https://2012electionguide.com/afiptest.php?ttext";
    public static final String HTTPS_SHOWNETWORKIP = "https://shownetworkip.com/?ttext";

    // RSS2Search Header IDs
    public static final String mockUp = "mockUp";
    public static final String mockUp1 = "?mockUp";
    public static final String mockUp2 = "?mockUp";
    public static final String mockUp3 = "?mockUp";
    public static final String mockUp4 = "?mockUp";
    public static final String mockUp5 = "?mockUp";
    public static final String mockUp6 = "?mockUp";
    public static final String mockUp7 = "?mockUp";
    public static final String mockUp8 = "?mockUp";
    public static final String mockUp9 = "?mockUp";
    public static final String mockUp10 = "?mockUp";
    public static final String mockUp11 = "?mockUp";
    public static final String mockUp12 = "?mockUp";
    public static final String mockUp13 = "?mockUp";
    public static final String mockUp14 = "?mockUp";
    public static final String mockUp15 = "?mockUp";
    public static final String mockUp16 = "?mockUp";
    public static final String mockUp17 = "?mockUp";
    public static final String mockUp18 = "?mockUp";
    public static final String mockUp19 = "?mockUp";
    public static final String mockUp20 = "?mockUp";


    // Common Android key events
    public static final String ANDROID_MENU_KEY_EVENT = "82";
    public static final String ANDROID_LOCK_SCREEN_KEY_EVENT = "26";
    public static final String ANDROID_UNLOCK_SCREEN_KEY_EVENT = "82";
    public static final String ANDROID_HOME_BTN_KEY_EVENT = "3";
    public static final String ANDROID_BACK_BTN_KEY_EVENT = "4";

    // Common gesture events
    public static final String OPEN_NOTIFICATIONS_SWIPE = "adb shell input swipe 10 10 10 1000";

    // Common package names
    public static final String HOTSPOTSHIELD_MAIN_ACTIVITY = "hotspotshield.android.vpn/com.anchorfree.ui.HotSpotShield";
    public static final String HOTSPOTSHIELD_PACKAGE_NAME = "hotspotshield.android.vpn";
    public static final String SPEEDTEST_MAIN_ACTIVITY = "org.zwanoo.android.speedtest/com.ookla.speedtest.softfacade.MainActivity";
    public static final String SPEEDTEST_PACKAGE_NAME = "org.zwanoo.android.speedtest";
    public static final String NETFLIX_MAIN_ACTIVITY = "com.netflix.mediaclient";
    public static final String WIFI_SETTINGS_ACTIVITY = "com.android.settings/.wifi.WifiSettings";
    public static final String CHROME_PACKAGE_NAME = "com.android.chrome";
    public static final String AIRPLANE_SETTINGS_ACTIVITY = "android.settings.AIRPLANE_MODE_SETTINGS";
    public static final String DATETIME_SETTINGS_ACTIVITY = "com.android.settings/.DateTimeSettingsSetupWizard";
    public static final String GMAIL_COMPOSE_ACTIVITY = "com.google.android.gm/com.google.android.gm.ComposeActivityGmail -d email:afqa07@yahoo.com --es subject 'Smoke Check' --es body 'Smoke Check'";

    // Common setting calls
    public static final String AIRPLANE_MODE = "airplane_mode_on";

    public static String runCommand(String command) {

        /**!!DOES NOT WORK WITH 'findstr' ARGUMENT BUT WORKS WITH 'grep'!!**/

        String output = null;
        try {
            Scanner s = new Scanner(Runtime.getRuntime().exec(command).getInputStream()).useDelimiter("\\A");
            if(s.hasNext()){
                output = s.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String getDeviceIMEI(){

        int OS_V = getOSVersion();
        if( OS_V < 500){
            System.out.println("[getDeviceIMEI] Connected device has OS Android: " + OS_V);
            String output = runCommand("adb shell dumpsys iphonesubinfo");
            Matcher matcher = Global.NUMBERS_PATTERN.matcher(output);
            if(matcher.find()){
                output = matcher.group().trim();
                System.out.println("[getDeviceIMEI] Device IMEI: " + output);
            }
            return output;
        }else{
            System.out.println("[getDeviceIMEI] Connected device has OS Android: " + OS_V);
            String output = runCommand("adb shell service call iphonesubinfo 1");
            output = output.split("'")[1] + output.split("'")[3] + output.split("'")[5];
            output = output.replaceAll("\\.", "").trim();
            System.out.println("[getDeviceIMEI] Device IMEI: " + output);
            return output;
        }
    }

    public static Integer getHssVersion(){

        String output = runCommand("adb shell dumpsys package hotspotshield.android.vpn");
        output = output.substring(output.indexOf("versionCode"), output.indexOf("targetSdk"));
        Matcher matcher = Global.NUMBERS_PATTERN.matcher(output);
        if(matcher.find()){
            output = matcher.group().trim();
        }
        return Integer.parseInt(output);
    }

    public static Integer getOSVersion(){
        String output = runCommand("adb shell getprop ro.build.version.release").replaceAll("\\.", "");
        Matcher matcher = Global.NUMBERS_PATTERN.matcher(output);
        if(matcher.find()){
            output = matcher.group().trim();
            if(output.length() <= 2) {
                output = output + "0";
            }
        }
        return Integer.parseInt(output);
    }

    public static Boolean androidWiFiOn(){

        String output = runCommand("adb shell settings get global wifi_on").trim();
        if(output.equals("0")){
            return false;
        }
        else{
            return true;
        }
    }

    //TODO May need to get rid of it if do not find a good use for it
    public static String getTopActivity() throws IOException {

        String output = runCommand("adb shell dumpsys window windows | grep mCurrentFocus");
        System.out.println(output);
        output = output.substring(output.indexOf("{"), output.indexOf("}"));
        return output;
    }

    public static void uninstallApplication(String packageName){

        String output = runCommand("adb shell pm list packages | grep " + packageName);
        if(output != null){
            output = runCommand("adb uninstall " + packageName);
            if(output.contains("Success")){
                System.out.println("Package uninstalled successfully!");
                Global.setVar_appInstalled(false);
            }else{
                System.out.println("Failed to uninstall the package!");
            }
        }else{
            System.out.println("Package already uninstalled!");
            Global.setVar_appInstalled(false);
        }
    }

    public static Boolean installApplication(String pathToAPK, Boolean reinstall){

        String command;
        if(reinstall){command = "adb install -r -d " + pathToAPK;}
        else{command = "adb install " + pathToAPK;}
        String output = runCommand(command);
        if(output == null){
            output = runCommand(command);
        }
        System.out.println(pathToAPK);
        System.out.println(output);
        if(!output.contains("Success")){
            System.out.println("Failed to install the package!");
            Global.setVar_appInstalled(false);
            Global.setVar_appFailedToInstall(1);
            return false;
        }else{
            System.out.println("Package was installed successfully!");
            Global.setVar_appInstalled(true);
            Global.setVar_appFailedToInstall(0);
            return true;
        }
    }

    public static String createPathToHSSAPK(String APK_ID){

        FileSystemView sysView = FileSystemView.getFileSystemView();
        File[] paths = File.listRoots();
        String pathToAPK = null;
        try{
            for(File path:paths) {
                String description = sysView.getSystemTypeDescription(path);
                if(description.toLowerCase().equals("network drive")){
                    String APK_Folder = APK_ID.substring(APK_ID.indexOf("v"), APK_ID.indexOf("_"));
                    pathToAPK = path + String.format("Android\\builds\\%s\\%s", APK_Folder, APK_ID);
                }
            }
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("Failed to resolve APK path");
        }
        System.out.println(pathToAPK);
        return pathToAPK;
    }

    public static void androidKeyEvent(String keyEvent){

        runCommand("adb shell input keyevent " + keyEvent);
        try {
            Thread.sleep(SYSTEM_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void openActivity(String activity){

        runCommand("adb shell am start -a android.intent.action.MAIN -n " + activity);
        try {
            Thread.sleep(SYSTEM_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void openNotifications() throws FindFailed {

        if(SCREEN.exists(NotificationObjects.VERIZON_WIRELESS(AP))==null){
            runCommand(OPEN_NOTIFICATIONS_SWIPE);
            if(Runner.AGENT_OS < 500 && new Screen().exists(NotificationObjects.Notifications_Clear(AP))!=null){
                new Screen().click(NotificationObjects.Notifications_Clear(AP));
                try {
                    Thread.sleep(1500);
                    runCommand(OPEN_NOTIFICATIONS_SWIPE);
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("[SpecialUtils / openNotifications] Notifications already open!");
        }
    }

    public static void clearData(String packageName){

        runCommand("adb shell pm clear " + packageName);
        Global.setVar_waitForSplashScreen(true);
        try {
            Thread.sleep(SYSTEM_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void forceStopApplication(String packageName){

        runCommand("adb shell am force-stop " + packageName);
        try {
            Thread.sleep(SYSTEM_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getAgent(){

        String model = runCommand("adb shell getprop ro.product.model").trim();
        String OS = runCommand("adb shell getprop ro.build.version.release").trim();
        return model + " " + OS;
    }

    public static String getProtocol() throws IOException {

        String protocol;
        if(Runner.AGENT_OS < 500){
            String data = runCommand("adb shell /data/local/tmp/curl -L -k --max-time 30 --insecure http://secretURL");
            System.out.println(data);
            protocol = data.substring(data.indexOf(">"), data.indexOf("</h1>")).replaceAll(">","").trim().toUpperCase();
            //TODO Remove when server team start identifying mockUp properly
            if (protocol.equals("L2TP")){
                protocol = "mockUp";
            } else if (protocol == null){
                protocol = "!loadPage";
            } else if (protocol.equals("")){
                protocol = "DSCNCTD";
            }
        }else{
            protocol = "mockUp";
        }
        return protocol;
    }

    public static void clearAndroidLog(){

        runCommand("adb logcat -c");
    }

    public static String getIPV4(String webAddress) throws IOException, FindFailed {

        // Not able to run curl on Android 5 -- Permission denied --
        if(Runner.AGENT_OS < 500){
            String data = runCommand("adb shell /data/local/tmp/curl -L --max-time 30 -k --insecure " + webAddress);
            System.out.println("[getIPV4] Output: " + data);
            if(data.contains("Connection timed out")){
                throw new NullPointerException("Connection timed out! Was not able to load the page!");
            }else if(data.toLowerCase().contains("403 forbidden")){
                throw new NullPointerException("Connection forbidden. 403!");
            }else if(data.contains("Operation timed out")){
                throw new NullPointerException("Page failed to load! Timed out after 30 seconds.");
            }else if(data.contains("Couldn't connect to server")){
                throw new NullPointerException("Couldn't connect to server");
            }
            data = data.substring(data.indexOf(">"), data.indexOf("</h1>")).replaceAll(">","").trim();
            if(data == null || data.equals("")){
                int ls = 5;
                while(data == null || data.equals("") && ls != 0){
                    try {Thread.sleep(WAIT_TO_PROPAGATE);} catch (InterruptedException e) {e.printStackTrace();}
                    data = runCommand("adb shell /data/local/tmp/curl -L --max-time 30 -k --insecure " + webAddress);
                    data = data.substring(data.indexOf(">"), data.indexOf("</h1>")).replaceAll(">","").trim();
                    ls--;
                }
            }
            Matcher matcher = Global.IPV4_PATTERN.matcher(data);
            if (matcher.find()) {
                System.out.println("Extracted IP Address: " + matcher.group());
                data = matcher.group();
            }
            return data;
        }else{ // Work around for Android OS 5+
            if(ChromeActions.resetChrome){
                ChromeActions.Reset();
            }
            // Determine how page may load (HTTPS/HTTP/SSL ERROR) so Sikuli knows what to expect
            Integer protocolID;
            if(webAddress.equals(HTTPS_BRAINRADIATION) || webAddress.equals(HTTPS_COARSEPOWDER) || webAddress.equals(HTTPS_SHOWNETWORKIP)
                    || webAddress.equals(HTTPS_ELECTION_ADVISOR) || webAddress.equals(HTTPS_ELECTION_GUIDE) || webAddress.equals(HTTPS_FINDIPINFO)){
                protocolID = 3;
            }
            else if(webAddress.equals(mockUp18) || webAddress.equals(FINDIPINFO) || webAddress.equals(BRAINRADIATION)
                    || webAddress.equals(COARSEPOWDER) || webAddress.equals(ELECTION_ADVISOR) || webAddress.equals(ELECTION_GUIDE)
                    || webAddress.equals(BBC_IPLAYER)){
                protocolID = 1;
            }else{
                protocolID = 2;
            }
            runCommand("adb shell am start -a android.intent.action.VIEW -d " + webAddress);
            ChromeActions.Wait_for_PageLoad(protocolID);
            String IP = ChromeActions.Extract_IP_Address();

            return IP;
        }
    }

    public static String getIPV6(String webAddress) throws IOException {

        String data = runCommand("adb shell /data/local/tmp/curl -L --max-time 30 -k --insecure " + webAddress);
        data = data.substring(data.indexOf(">"), data.indexOf("</h1>")).replaceAll(">","").trim();
        if(data.contains("Connection timed out")){
            throw new NullPointerException("Connection timed out! Was not able to load the page!");
        }else if(data.toLowerCase().contains("403 forbidden")){
            throw new NullPointerException("Connection forbidden. 403!");
        }else if(data.contains("Operation timed out")){
            throw new NullPointerException("Page failed to load! Timed out after 30 seconds.");
        }
        System.out.println("[getIPV6] Output: " + data);
        if(data == null || data.equals("")){
            int ls = 5;
            while(data == null || data.equals("") && ls != 0){
                try {Thread.sleep(WAIT_TO_PROPAGATE);} catch (InterruptedException e) {e.printStackTrace();}
                data = runCommand("adb shell /data/local/tmp/curl -L --max-time 30 -k --insecure " + webAddress);
                data = data.substring(data.indexOf(">"), data.indexOf("</h1>")).replaceAll(">","").trim();
                ls--;
            }
        }
        Matcher matcher = Global.IPV6_PATTERN.matcher(data);
        if (matcher.find()) {
            System.out.println("Extracted IP Address: " + matcher.group());
            data = matcher.group();
        }
        return data;
    }

    public static String getRSS2SearchHeader(String headerID){

        String data = runCommand("adb shell /data/local/tmp/curl -L -k --max-time 30 --insecure " + mockUp + headerID);
        data = data.substring(data.indexOf(">"), data.indexOf("</h1>")).replaceAll(">","").trim();
        return data;
    }

    public static Boolean Check_IP_Ownership(String IP){

        WebDriver driver = new HtmlUnitDriver();
        driver.get("http:/secretURL/secret.php");
        driver.findElement(By.name("ip")).sendKeys(IP);
        driver.findElement(By.name("get IP")).click();
        String text = driver.findElement(By.tagName("body")).getText();
        String[] list = text.split("\n");
        try{
            String[] value = list[5].split(":"); //for headless driver use 5, for other drivers use 3
            StringBuffer Server = new StringBuffer();
            Server.append(value[1]);
            String  Server_ID = Server.toString().trim();
            if (Server_ID.contains("hss")){
                System.out.println("The IP " + IP + " is hosted by: " + Server);
                driver.quit();
                return Boolean.TRUE;
            }
            else{
                System.out.println("IP " + IP + " is not recognized");
                driver.quit();
                return Boolean.FALSE;
            }}
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("IP " + IP + " is not hosted by us.");
            driver.quit();
            return Boolean.FALSE;
        }
    }

    public static void sendEmail(String TO, String TITLE, String MESSAGE){

        String SMPT_HOSTNAME = "secret";
        String USERNAME = "secret";
        String PASSWORD = "secret";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", SMPT_HOSTNAME);
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("secret"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    TO));
            message.setSubject(TITLE);
            message.setText(MESSAGE);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void sendEmailNotificationStart(){

        String MESSAGE = String.format("REQUEST ID: %s\n" +
                "RESULTS WILL BE HERE: http://secretURL.php?req=%s\n" +
                "VERSION BEING TESTED: %s\n" +
                "AGENT: %s", Runner.REQUEST_ID, Runner.REQUEST_ID, Runner.VERSION_TESTED, Runner.AGENT);
        String TO = "secret";
        String TITLE = Runner.AGENT +" Started Functional Tests REQ: " + Runner.REQUEST_ID;

        sendEmail(TO, TITLE, MESSAGE);
    }

    public static void sendEmailNotificationStop(Result results){

        // Converting milliseconds to readable format
        long runTime = results.getRunTime();
        String time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(runTime),
                TimeUnit.MILLISECONDS.toMinutes(runTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(runTime)),
                TimeUnit.MILLISECONDS.toSeconds(runTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runTime)));

        double TOTAL_TESTS_RAN = results.getRunCount();

        // Get Failed Stats
        double TESTS_FAILED = results.getFailureCount();
        double TESTS_FAILED_PERCENT = Math.round(TESTS_FAILED / TOTAL_TESTS_RAN * 100);

        // Get Passed Stats
        double TESTS_PASSED = TOTAL_TESTS_RAN - TESTS_FAILED;
        double TESTS_PASSED_PERCENT = Math.round(TESTS_PASSED / TOTAL_TESTS_RAN * 100);

        // Calculating status of the build
        String BUILD_STATUS;
        if(TESTS_FAILED_PERCENT >= 40.0 && TESTS_FAILED_PERCENT > 35.0){
            BUILD_STATUS = "Unacceptable!";
        }else if(TESTS_FAILED_PERCENT <= 35.0 && TESTS_FAILED_PERCENT > 30.0){
            BUILD_STATUS = "Very Bad!";
        }else if(TESTS_FAILED_PERCENT <= 30.0 && TESTS_FAILED_PERCENT > 25.0){
            BUILD_STATUS = "Bad";
        }else if(TESTS_FAILED_PERCENT <= 25.0 && TESTS_FAILED_PERCENT > 20.0){
            BUILD_STATUS = "Meh...";
        }else if(TESTS_FAILED_PERCENT <= 20.0 && TESTS_FAILED_PERCENT > 15.0){
            BUILD_STATUS = "Acceptable";
        }else if(TESTS_FAILED_PERCENT <= 15.0 && TESTS_FAILED_PERCENT > 10.0){
            BUILD_STATUS = "Good";
        }else if(TESTS_FAILED_PERCENT <= 10.0 && TESTS_FAILED_PERCENT > 5.0){
            BUILD_STATUS = "Very Good!";
        }else if(TESTS_FAILED_PERCENT <= 5.0){
            BUILD_STATUS = "Excellent!";
        }else{
            BUILD_STATUS = "Not calculated";
        }

        // Compose and send the email
        String MESSAGE = String.format("REQUEST ID: %s\n" +
                "RESULTS AVAILABLE: http://secretURL.php?req=%s\n" +
                "VERSION BEING TESTED: %s\n" +
                "AGENT: %s\n" +
                "TESTS RAN: %d\n" +
                "TESTS PASSED: %d (%s)\n" +
                "TESTS FAILED: %d (%s)\n" +
                "RAN IN: %s\n" +
                "CONNECTION ISSUES DURING TEST CYCLE: %d\n" +
                "BUILD STATUS: %s", Runner.REQUEST_ID, Runner.REQUEST_ID, Runner.VERSION_TESTED, Runner.AGENT,
                (int) TOTAL_TESTS_RAN, (int) TESTS_PASSED, TESTS_PASSED_PERCENT + "%", (int) TESTS_FAILED,
                TESTS_FAILED_PERCENT + "%", time, Global.testCycleTotalConnectionIssues, BUILD_STATUS);

        String TO = "secret";
        String TITLE = Runner.AGENT +" Functional Tests Done! REQ: " + Runner.REQUEST_ID;
        sendEmail(TO, TITLE, MESSAGE);
    }
}
