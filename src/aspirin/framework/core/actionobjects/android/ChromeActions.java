package aspirin.framework.core.actionobjects.android;

import aspirin.framework.core.pageobjects.android.ChromeObjects;
import aspirin.framework.core.pageobjects.misc.MobizenObjects;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import java.util.regex.Matcher;

/**
 * Created by Artur Spirin on 7/14/2015.
 */
public class ChromeActions {

    private static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;
    public static Boolean resetChrome = false;

    public static void Wait_for_PageLoad(Integer expectedProtocolID) throws FindFailed {

        try{
            if(expectedProtocolID == 1){
                try{
                    System.out.println("[Chrome / Wait_for_PageLoad] Waiting for blank SCREEN");
                    SCREEN.wait(ChromeObjects.Chrome_BlankScreen(AP), 5);
                    System.out.println("[Chrome / Wait_for_PageLoad] Waiting for blank SCREEN to vanish");
                    SCREEN.waitVanish(ChromeObjects.Chrome_BlankScreen(AP), 20);}
                catch (FindFailed e){
                    System.out.println("[Chrome / Wait_for_PageLoad] Wasn't able to pick up expected SCREENs. Passing process on anyway.");
                }
            }
            else if (expectedProtocolID == 2){
                SCREEN.wait(ChromeObjects.Chrome_SSLSecuredIcon(AP), 20);
                Check_for_CertWarning();
            }
            else if (expectedProtocolID == 3){
                SCREEN.wait(ChromeObjects.Chrome_SSLUnsecuredIcon(AP), 30);
                SCREEN.wait(ChromeObjects.Chrome_SSLUnsecuredIcon(AP), 30);
                Check_for_CertWarning();
            }
            else{
                System.out.println("[Chrome / Wait_for_PageLoad] Wrong Protocol ID! Use 1 for HTTP, 2 for Secured HTTPS, 3 for Unsecured HTTPS");
                throw new NullPointerException("[Chrome / Wait_for_PageLoad] Wrong Protocol ID! Use 1 for HTTP, 2 for Secured HTTPS, 3 for Unsecured HTTPS");
            }
        }catch (FindFailed e){
            ChromeActions.resetChrome = true;
            throw new FindFailed("[Chrome / Wait_for_PageLoad] PAGE did NOT LOAD! " + e);
        }
    }

    public static Boolean Check_for_CertWarning() throws FindFailed{

        try{
            if (SCREEN.exists(ChromeObjects.Chrome_HTTPS_NotSecured_Dialog(AP)) != null){
                System.out.println("[Chrome / Check_for_CertWarning] SSL Warning DETECTED! Closing...");
                SCREEN.click(ChromeObjects.Chrome_HTTPS_AdvancedLink(AP));
                SCREEN.wait(ChromeObjects.Chrome_HTTPS_ProceedTo(AP), 5);
                SCREEN.doubleClick(ChromeObjects.Chrome_HTTPS_ProceedTo(AP));
                SCREEN.wait(ChromeObjects.Chrome_IP_Dot(AP), 6);
                return Boolean.TRUE;
            }
            else{
                System.out.println("[Chrome / Check_for_CertWarning] NO SSL warning DETECTED");
                //SCREEN.wait(ImgP.Chrome_IP_Dot(AP), 20);
                return Boolean.FALSE;
            }
        }catch (FindFailed e){
            throw new FindFailed("[Chrome / Check_for_CertWarning] FAILED to CLOSE SSL WARNING! " + e);
        }
    }

    public static String Extract_IP_Address() throws  FindFailed{

        Region IP_REGION = SCREEN.find(MobizenObjects.Mobizen_TopBar(0.45f)).add(3, 1, -115, 200);
        Settings.OcrTextRead = true;
        Settings.OcrTextSearch = true;
        String text = IP_REGION.text();
        if(text == null){
            throw new NullPointerException("[Chrome / Extract_IP_Address] Was not able to get any text from the page. Got: " + text);
        }
        Matcher matcher = Global.IPV4_PATTERN.matcher(text);
        if (matcher.find()) {
            System.out.println("[Chrome / Extract_IP_Address] Extracted IP Address: "+matcher.group());
            return matcher.group();
        }
        else{
            throw new NullPointerException("[Chrome / Extract_IP_Address] Was not able to extract IP address. Got:  " + text);
        }
    }

    public static void Reset() throws FindFailed {

        SpecialUtils.clearData(SpecialUtils.CHROME_PACKAGE_NAME);
        SpecialUtils.runCommand("adb shell am start -a android.intent.action.VIEW -d " + SpecialUtils.FINDIPINFO);
        SCREEN.wait(ChromeObjects.ACCEPT(AP), 10);
        SCREEN.click(ChromeObjects.ACCEPT(AP));
        SCREEN.wait(ChromeObjects.NO_THANKS(AP), 10);
        SCREEN.click(ChromeObjects.NO_THANKS(AP));
    }

    public static void Open(String address) throws FindFailed {

        Integer protocolID = null;
        if(address.equals(SpecialUtils.BBC_IPLAYER) || address.equals(SpecialUtils.FINDIPINFO)){
            protocolID = 1;
        }
        SpecialUtils.runCommand("adb shell am start -a android.intent.action.VIEW -d " + address);
        Wait_for_PageLoad(protocolID);
    }
}
