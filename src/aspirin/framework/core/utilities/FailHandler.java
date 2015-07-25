package aspirin.framework.core.utilities;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.hotspotshield.MainActions;
import aspirin.framework.core.actionobjects.hotspotshield.NotificationActions;
import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.pageobjects.hotspotshield.NotificationObjects;
import org.apache.commons.io.FileUtils;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by Artur on 7/10/2015.
 */
public class FailHandler {

    public static String moveLocalFailDir(String testCaseID){

        File source = new File(String.format(".\\%s\\%s", Runner.REQUEST_ID, testCaseID));
        File target = new File(String.format("Z:\\QA\\Automation\\Android\\Screenshots_and_Logs\\%s\\%s", Runner.REQUEST_ID, testCaseID));
        try {
            System.out.println("Moving files to AFFS2 Server...");
            FileUtils.copyDirectory(source, target);
            System.out.println("Moved successfully!");
        } catch (IOException e) {
            System.out.println("Failed to move files!");
            e.printStackTrace();
        }

        return String.format("<a target=\"_blank\" href=\"file://///affs2/QA/Automation/Android/Screenshots_and_Logs/%s/%s\">Check Logs</a>", Runner.REQUEST_ID, testCaseID);
    }

    public static void takeDump(String TC_ID, Throwable ERROR, Boolean takeScreenshot, Boolean takeLog, Boolean takeStackTrace){

        String targetDir = String.format(".\\%s\\%s\\", Runner.REQUEST_ID, TC_ID);

        if(takeScreenshot){
            SpecialUtils.runCommand(String.format("adb shell screencap -p /sdcard/%s.png", TC_ID));
            SpecialUtils.runCommand(String.format("adb pull /sdcard/%s.png %s\\%s.png", TC_ID, targetDir, TC_ID));
            SpecialUtils.runCommand(String.format("adb shell rm /sdcard/%s.png", TC_ID));
        }
        if(takeLog){
            SpecialUtils.runCommand(String.format("adb logcat -v threadtime -d > /sdcard/%s_Log.txt", TC_ID));
            SpecialUtils.runCommand(String.format("adb pull /sdcard/%s_Log.txt %s\\%s_Log.txt", TC_ID, targetDir, TC_ID));
            SpecialUtils.runCommand(String.format("adb shell rm /sdcard/%s_Log.txt", TC_ID));
        }
        if(takeStackTrace && ERROR != null){
            File file = new File(String.format("%s\\%s_StackTrace.txt", targetDir, TC_ID));
            try {
                PrintStream ps = new PrintStream(file);
                ERROR.printStackTrace(ps);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static String formatNotes(String notes){

        //TODO CHECK RESULTS
        //if(notes == null){
           // notes = "Assertion failed - retest manually";
           // return notes;
        //}
        if(notes.contains("FindFailed:")){
            notes = notes
                    .replaceAll("FindFailed: can not find P", "")
                    .replaceAll("'", "")
                    .replaceAll("\\n","") //TODO TEST
                    .replaceAll("\n", "")
                    .replaceAll("FindFailed: ", "")
                    .replaceAll("\\[.*?\\]", "")
                    .replaceAll("\\).*?\\]", "")
                    .replaceAll("ACTUAL: null", "ACTUAL: ");
        }
        return notes;
    }

    // Method checks if UI disconnected from the service
    public static Boolean UI_DISCONNECTED() throws FindFailed {

        Boolean disconnected = false;
        Screen screen = new Screen();
        screen.setAutoWaitTimeout(0.5);
        if(screen.exists(NotificationObjects.Notifications_Key(0.80f)) != null &&
                screen.exists(MainObjects.Main_NotProtected(0.80f)) !=  null){
            System.out.println("[UI_DISCONNECTED] TUNNEL UP is up but UI is DISCONNECTED. FORCE STOPPING Hotspotshield!");
            NotificationActions.REVOKE_PERMISSIONS();
            if(Global.timesCantConnect > 1){
                System.out.println("[UI_DISCONNECTED] FAILED to CONNECT " + Global.timesCantConnect+" times. FORCE STOPPING Hotspotshield!");
                SpecialUtils.forceStopApplication(SpecialUtils.HOTSPOTSHIELD_PACKAGE_NAME);
                Global.setVar_timesCantConnect(0);
            }
            if(screen.exists(MainObjects.Main_NotProtected(0.80f))==null){
                SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
            }
            MainActions.startVPN(false);
            disconnected = true;
        }

        else if(Global.timesCantConnect > 1){
            System.out.println("[UI_DISCONNECTED] FAILED to CONNECT " + Global.timesCantConnect+" times. FORCE STOPPING Hotspotshield!");
            SpecialUtils.forceStopApplication(SpecialUtils.HOTSPOTSHIELD_PACKAGE_NAME);
            Global.setVar_timesCantConnect(0);
        }
        screen.setAutoWaitTimeout(3);
        return disconnected;
    }
}
