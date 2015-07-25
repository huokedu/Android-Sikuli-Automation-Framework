package aspirin.framework.core.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public class Global {

    // Supported Devices
    public static final String MOTO_X_IMEI = "secret"; // Android 4.4.4 w/ cell
    public static final String GALAXY_S3_IMEI = "secret2"; // Android 4.4.2 w/ cell
    public static final String MOTO_X2_IMEI = "secret3"; // Android 5.1 w/ cell

    // Test suite details
    public static final String EX_TYPE_SRV = "criticServer";
    public static final String EX_TYPE_FUN = "criticClient";

    // Reg Ex Patterns
    public static String NUMBERS_REGEX = "(\\d+)";
    public static Pattern NUMBERS_PATTERN = Pattern.compile(NUMBERS_REGEX);
    public static String IPV4_REGEX = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public static Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);
    public static String IPV6_REGEX = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
    public static Pattern IPV6_PATTERN = Pattern.compile(IPV6_REGEX);

    // Date Formats
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Global PreSet Flags
    public static Boolean disablePopUps;                    // Value is set to TRUE on every test cycle or clear data
    public static Boolean setVPNServer;                     // Value is set to TRUE every test cycle or clear data
    public static Boolean setVPNMode;                       // Value is set to TRUE every test cycle or clear data
    public static Boolean dataCleared = false;              // Value is set to TRUE on every clear data
    public static Integer timesCantConnect = 0;             // Value is incremented by 1 if client cannot connect. After successful connection value is reset to 0. //TODO send email if client can't connect x amount of times in a row
    public static Boolean appInstalled = false;             // Value is set to FALSE on every test cycle or after uninstall is used
    public static Integer appFailedToInstall = 0;           // Every unsuccessful install increments the value by 1 //TODO send email after x amount of unsuccessful installs
    public static Boolean mediaCategoryEnabled = false;     // Value is set to TRUE by methods that enable the category and viceversa
    public static Boolean needToResetSmartSettings = false; // Value is set to TRUE every time Smart mode setting actions change smart settings
    public static Boolean waitForSplashScreen = false;      // Value is set to TRUE every time app is installed or after clear data
    public static Boolean domainON = false;
    public static Boolean countryChanged = false;
    public static String COLO = "US";
    public static Boolean coloUS = true;
    public static Boolean setDebugDomain = true;
    public static Integer testCycleTotalConnectionIssues = 0;

    // Get IMEI of the current device
    public static final String CURRENT_IMEI = SpecialUtils.getDeviceIMEI();

    public static final String getCurrentDirectory(){return System.getProperty("user.dir");}

    public static final String Img_FolderPath() {

        String Img_Path;
        String currentDir = getCurrentDirectory();

        switch (CURRENT_IMEI) {
            case MOTO_X_IMEI:
                Img_Path = String.format("%s\\AIM\\MotoX\\", currentDir);
                break;
            case GALAXY_S3_IMEI:
                Img_Path = String.format("%s\\AIM\\Galaxy S3\\", currentDir);
                break;
            case MOTO_X2_IMEI:
                Img_Path = String.format("%s\\AIM\\MotoX 2\\", currentDir);
                break;
            default:
                System.out.println("[Img_FolderPath] Do not recognize IMEI of the device: " + CURRENT_IMEI);
                Img_Path = null;
                break;
        }
        if(Img_Path != null && Img_Path.contains("out\\artifacts\\Android_Sikuli_Framework_v2_jar")){
            //System.out.println("Running from Jar file, changing path...");
            Img_Path = Img_Path.replaceAll("out\\\\artifacts\\\\Android_Sikuli_Framework_v2_jar\\\\","");
        }
        if(Img_Path != null && Img_Path.contains("c:\\Auto\\Local")){
            //System.out.println("Running from Jenkins, changing path...");
            Img_Path = Img_Path.replaceAll("c:\\\\Auto\\\\Local", "C:\\\\Auto\\\\Local\\\\android-sikuli-framework-v2");
        }
        //System.out.println("[Img_FolderPath] Based on the IMEI of the device will use images located in: " + Img_Path);
        return Img_Path;
    }

    public static Boolean setDebugSRV(){

        if(setVPNMode == null || setVPNMode || setVPNServer == null || setVPNServer || disablePopUps == null || disablePopUps) {
            return true;
        }else{
            return false;
        }
    }

    public static Boolean setDebugFUN(){

        if(setVPNMode == null || setVPNMode || disablePopUps == null || disablePopUps || dataCleared) {
            return true;
        }else{
            return false;
        }
    }

    public static Boolean setDebugELITE(){

        if(setVPNMode == null || setVPNMode || setDebugDomain == null || setDebugDomain || disablePopUps == null || disablePopUps || dataCleared) {
            return true;
        }else{
            return false;
        }
    }

    public static Boolean dataCleared(){

        if(dataCleared != null && dataCleared){
            return true;
        }else{
            return false;
        }
    }

    public static void setVar_timesCantConnect(Integer num){

        System.out.println("Wasn't able to connect: " + Global.timesCantConnect + " times");
        if(num == 0){
            Global.timesCantConnect = 0;
        }else{
            Global.timesCantConnect += num;
            Global.testCycleTotalConnectionIssues += num;
        }
        System.out.println("Wasn't able to connect: " + Global.timesCantConnect + " times");
    }

    public static void setVar_appInstalled(Boolean appInstalled){

        Global.appInstalled = appInstalled;
    }

    public static void setVar_appFailedToInstall(Integer num){

        System.out.println("Wasn't able to install: " + Global.appFailedToInstall + " times");
        if(num == 0){
            Global.appFailedToInstall = 0;
        }else{
            Global.appFailedToInstall += num;
        }
        System.out.println("Wasn't able to install: " + Global.appFailedToInstall + " times");
    }

    public static Boolean installApp(){

        if(!appInstalled){
            return true;
        }
        else{
            return false;
        }
    }

    public static Boolean waitForSplashScreen(){

        if(waitForSplashScreen){
            return true;
        }else{
            return false;
        }
    }


    public static void setVar_mediaCategoryEnabled(Boolean enabled){Global.mediaCategoryEnabled = enabled;}

    public static void setVar_needToResetSmartSettings(Boolean needToReset){Global.needToResetSmartSettings = needToReset;}

    public static void setVar_DisablePopUps(Boolean disablePopUps){Global.disablePopUps = disablePopUps;}

    public static void setVar_VPNServer(Boolean setVPNServer){Global.setVPNServer = setVPNServer;}

    public static void setVar_VPNMode(Boolean setVPNMode){Global.setVPNMode = setVPNMode;}

    public static void setVar_DataCleared(Boolean dataCleared){Global.dataCleared = dataCleared;}

    public static void setVar_waitForSplashScreen(Boolean wait){Global.waitForSplashScreen = wait;}

    public static void setVar_domainON(Boolean on){Global.domainON = on;}

    public static void setVar_Colo(String colo){Global.COLO = colo;}

    public static void setVar_ColoUs(Boolean coloUS){Global.coloUS = coloUS;}

    public static void setVar_countryChanged(Boolean changed){Global.countryChanged = changed;}

    public static void setVar_setDebugDomain(Boolean setDebugDomain){Global.setDebugDomain = setDebugDomain;}

    public static void DEBUGOUTPUT(){

        System.out.println("---------------setUP----------------");
        System.out.println("------------------------------------");
        System.out.println("-------------DEBUG OUT--------------");
        System.out.println("SET DEBUG SETTINGS FLAG...: " + Global.setDebugFUN().toString().toUpperCase());
        System.out.println("POP UP FLAG...............: " + Global.disablePopUps.toString().toUpperCase());
        System.out.println("VPN MODE FLAG.............: " + Global.setVPNMode.toString().toUpperCase());
        System.out.println("APP INSTALLED FLAG........: " + Global.appInstalled.toString().toUpperCase());
        System.out.println("FAILED TO CONNECT FLAG....: " + Global.timesCantConnect);
        System.out.println("CLEAR DATA FLAG...........: " + Global.dataCleared.toString().toUpperCase());
        System.out.println("WAIT FOR SPLASH FLAG......: " + Global.waitForSplashScreen.toString().toUpperCase());
        System.out.println("DOMAIN ON.................: " + Global.domainON.toString().toUpperCase());
        System.out.println("------------------------------------\n");
    }

}
