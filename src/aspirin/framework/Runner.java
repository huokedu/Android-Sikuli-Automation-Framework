package aspirin.framework;

import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import aspirin.framework.testsuites.elite.AcceptanceElite;
import aspirin.framework.testsuites.functional.MasterFUN;
import aspirin.framework.testsuites.server.MasterSRV;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.sikuli.basics.Debug;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Artur on 7/11/2015.
 */
public class Runner {

    // Device & HSS Info
    public static final String AGENT = SpecialUtils.getAgent();
    public static final String AGENT_IMEI = SpecialUtils.getDeviceIMEI();
    public static final Integer AGENT_OS = SpecialUtils.getOSVersion();
    public static Integer HSS_VERSION = null;
    public static final String PLATFORM = "Android";
    public static String PROTOCOL = null;

    // Request Info & Params
    public static String REQUEST_ID;
    public static String STATUS;
    public static String HOST;
    public static String IP_IN;
    public static String APK_ID = "none";
    public static String VERSION_TESTED;
    public static Boolean ENABLE_HYDRA;


    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        /**Debug lvl 3 will allow Sikuli to dump output of everything it is doing**/
        Debug.setDebugLevel(3);

        if(AGENT_IMEI.equals(Global.MOTO_X_IMEI) && ANDROID_SRV_OPEN()){

            System.out.println("[Runner] Starting SERVER TESTS!");
            STATUS = "In process";
            AutomationQADB.updateRequestStatus(REQUEST_ID, STATUS, "", APK_ID);
            JUnitCore.runClasses(MasterSRV.class);
            STATUS = "Completed";
            AutomationQADB.updateRequestResults(REQUEST_ID, STATUS, "", APK_ID);
        }

        if(AGENT_IMEI.equals(Global.MOTO_X_IMEI) && ANDROID_SC_OPEN()){

            System.out.println("[Runner] Starting ELITE ACCEPTANCE");
            STATUS = "In process";
            AutomationQADB.updateRequestStatus(REQUEST_ID, STATUS, "", APK_ID);
            JUnitCore.runClasses(AcceptanceElite.class);
            STATUS = "Completed";
            AutomationQADB.updateRequestResults(REQUEST_ID, STATUS, "", APK_ID);
        }

        if((AGENT_IMEI.equals(Global.GALAXY_S3_IMEI) || AGENT_IMEI.equals(Global.MOTO_X2_IMEI)) && ANDROID_FUN_OPEN()){

            System.out.println("[Runner] Starting FULL REGRESSION");
            STATUS = "In process";
            SpecialUtils.sendEmailNotificationStart();
            AutomationQADB.updateRequestStatus(REQUEST_ID, STATUS, "", APK_ID);
            Result results =  JUnitCore.runClasses(MasterFUN.class);
            SpecialUtils.sendEmailNotificationStop(results);
            STATUS = "Completed";
            AutomationQADB.updateRequestResults(REQUEST_ID, STATUS, "", APK_ID);
        }
        System.out.println("[Runner] Done!");
        System.out.println("[Runner] Exiting!");
        Thread.currentThread().interrupt();
        return;
    }

    public static void setHssVersion(){

        HSS_VERSION = SpecialUtils.getHssVersion();
    }

    public static void setProtocol() throws IOException {

        PROTOCOL = SpecialUtils.getProtocol();
    }

    public static Boolean ANDROID_SRV_OPEN() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        try {
            System.out.println("[Runner] Checking for SRV request...");
            String[] requestResults = AutomationQADB.getRequest("ANDROID_SRV");
            REQUEST_ID = requestResults[0];
            STATUS = requestResults[1];
            if (!STATUS.toLowerCase().equals("open")) {
                return false;
            } else {
                Global.setVar_VPNMode(true);
                Global.setVar_VPNServer(true);
                Global.setVar_DisablePopUps(true);
                HOST = requestResults[8];
                ENABLE_HYDRA = Boolean.parseBoolean(requestResults[9].toLowerCase().replace("hydra", "true"));
                if (HOST != null) {
                    IP_IN = AutomationQADB.ResolveHostToIP(HOST);
                }
                return true;
            }
        }catch (NullPointerException e){
            System.out.println("[Runner] Did not find legit request for SRV!");
            //throw e;
            return false;
        }
    }

    public static Boolean ANDROID_SC_OPEN() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        try{
            System.out.println("[Runner] Checking for SC request...");
            String[] requestResults = AutomationQADB.getRequest("ANDROID_SC");
            REQUEST_ID = requestResults[0];
            STATUS = requestResults[1];
            if(!STATUS.toLowerCase().equals("open")){
                return false;
            }else{
                Global.setVar_VPNMode(true);
                Global.setVar_setDebugDomain(true);
                Global.setVar_DisablePopUps(true);
                HOST = requestResults[8];
                ENABLE_HYDRA = Boolean.parseBoolean(requestResults[9].toLowerCase().replace("hydra", "true"));
                if(HOST != null){
                    IP_IN = AutomationQADB.ResolveHostToIP(HOST);
                }
                return true;
            }
        }catch (NullPointerException e){
            System.out.println("[Runner] Did not find open request for SC!");
            return false;
        }
    }
    public static Boolean ANDROID_FUN_OPEN() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        try{
            System.out.println("[Runner] Checking for FUN request...");
            String[] requestResults = AutomationQADB.getRequest("ANDROID_FUN");
            REQUEST_ID = requestResults[0];
            STATUS = requestResults[1];
            if(!STATUS.toLowerCase().equals("open")){
                return false;
            }else{
                Global.setVar_VPNMode(true);
                Global.setVar_DisablePopUps(true);
                ENABLE_HYDRA = true;
                VERSION_TESTED = requestResults[5];
                APK_ID = SpecialUtils.createPathToHSSAPK(requestResults[5]);
                return true;
            }
        }catch (NullPointerException e){
            System.out.println("[Runner] Did not find open request for FUN!");
            return false;
        }
    }

}
