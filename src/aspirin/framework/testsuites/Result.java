package aspirin.framework.testsuites;

import aspirin.framework.core.pageobjects.hotspotshield.MainObjects;
import aspirin.framework.core.utilities.SpecialUtils;
import org.junit.Assert;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 * Created by support on 7/16/15.
 */
public class Result {

    // SCREEN Variables
    public static final Screen SCREEN = new Screen();
    private static final Float AP = 0.80f;

    // Expected results after changing networks or modes
    public static final String HSS_DISCONNECTS = "HSS disconnects";
    public static final String HSS_DISCONNECT_FAIL = "HSS did not disconnect!";
    public static final String HSS_CONNECTED = "HSS reconnects";
    public static final String HSS_CONNECTED_FAIL = "HSS did not reconnect";

    public static final String HSS_CONNECTS_IN_FULL_MODE = "HSS connects in FULL mode";
    public static final String HSS_CONNECTS_IN_FULL_MODE_FAIL = "HSS did not connect in FULL mode";
    public static final String HSS_CONNECTS_IN_SELECTED_SITES_MODE = "HSS connects in SELECTED SITES mode";
    public static final String HSS_CONNECTS_IN_SELECTED_SITES_MODE_FAIL = "HSS did not connect in SELECTED SITES mode!";
    public static final String HSS_CONNECTS_IN_SMART_MODE = "HSS connects in SMART mode";
    public static final String HSS_CONNECTS_IN_SMART_MODE_FAIL = "HSS did not connect in SMART mode";

    public static final String HSS_CONNECTS_IN_SMART_FULL_MODE = "HSS connects in SMART FULL mode";
    public static final String HSS_CONNECTS_IN_SMART_FULL_MODE_FAIL = "HSS Failed to connect in SMART FULL mode";
    public static final String HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE = "HSS connects in SMART SELECTED SITES mode";
    public static final String HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE_FAIL = "HSS Failed to connect in SMART SELECTED SITES mode";

    public static final String HSS_REMAINS_IN_FULL_MODE = "HSS remains connected in FULL mode";
    public static final String HSS_REMAINS_IN_FULL_MODE_FAIL = "HSS did not remain connected in FULL mode!";
    public static final String HSS_REMAIN_IN_SELECTED_SITES_MODE = "HSS remains connected in SELECTED SITES mode";
    public static final String HSS_REMAIN_IN_SELECTED_SITES_MODE_FAIL = "HSS did not remain connected in SELECTED SITES mode";
    public static final String HSS_REMAIN_IN_SMART_FULL_MODE = "HSS remains connected in SMART FULL mode";
    public static final String HSS_REMAIN_IN_SMART_FULL_MODE_FAIL = "HSS did not remain connected in SMART FULL mode";
    public static final String HSS_REMAIN_IN_SMART_SELECTED_SITES_MODE = "HSS remains connected in SMART SELECTED SITES mode";
    public static final String HSS_REMAIN_IN_SMART_SELECTED_SITES_MODE_FAIL = "HSS did not remain connected in SMART SELECTED SITES mode";

    // Expected results for IPs
    public static final String BRAINRADIATION_VPN_IP_FINDIPINFO_VPN_IP = "VPN IP displayed on Brainradiation and VPN IP displayed on Findipinfo";
    public static final String BRAINRADIATION_VPN_IP_FINDIPINFO_REAL_IP = "VPN IP Displayed on Brainradiation and Real IP displayed on Findipinfo";
    public static final String BRAINRADIATION_VPN_IP_FAIL = "Brainradiation NOT VPN IP: ";
    public static final String FINDIPINFO_VPN_IP_FAIL = "Findipinfo NOT VPN IP: ";
    public static final String FINDIPINFO_REAL_IP_FAIL = "Findipinfo NOT REAL IP: ";

    // Expected results for Media category
    public static final String BRAINRADIATION_MEDIA_ON_OFF = "VPN IP displayed with Media Category ON and REAL IP displayed with Media Category OFF";
    public static final String BRAINRADIATION_MEDIA_ON_VPN_IP_FAIL = "VPN IP is NOT displayed: ";
    public static final String BRAINRADIATION_MEDIA_OFF_REAL_IP_FAIL = "REAL IP is NOT displayed: ";

    // Expected results for Domains
    public static final String COARSEPOWDER_DOMAIN_OFF_ON = "REAL IP displayed with Domain OFF and VPN IP displayed with Domain ON";
    public static final String COARSEPOWDER_DOMAIN_ON_VPN_IP_FAIL = "VPN IP is NOT displayed: ";
    public static final String COARSEPOWDER_DOMAIN_ON_REAL_IP_FAIL = "REAL IP is NOT displayed: ";


    /**
     You can add and modify all assertions bellow depending on what elements you want to verify are on the screen during
     the specific state of the application.
     */

    public static Boolean disconnected(){

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
            System.out.println("Wrong activity is in focus. Opening HSS!");
            SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        }else{
            System.out.println("[Result] Main Activity is already Open!");
        }

        try{
            Assert.assertNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_NotProtected(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_StartText(AP)));
            return  true;
        }catch (AssertionError e){
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
                throw new AssertionError("Wrong activity is in focus. Not able to assert!");
            }else{
                throw new AssertionError(HSS_DISCONNECT_FAIL);
            }
        }
    }

    public static Boolean connected(){

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
            System.out.println("Wrong activity is in focus. Opening HSS!");
            SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        }else{
            System.out.println("[Result] Main Activity is already Open!");
        }

        try{
            Assert.assertNull(SCREEN.exists(MainObjects.Main_NotProtected(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_ModeSelector_StartText(AP)));
            return  true;
        }catch (AssertionError e){
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
                throw new AssertionError("Wrong activity is in focus. Not able to assert!");
            }else{
                throw new AssertionError(HSS_CONNECTED_FAIL);
            }
        }
    }

    public static Boolean connectedSmartFullMode(){

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
            System.out.println("Wrong activity is in focus. Opening HSS!");
            SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        }else{
            System.out.println("[Result] Main Activity is already Open!");
        }

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            return  true;
        }catch (AssertionError e){
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
                throw new AssertionError("Wrong activity is in focus. Not able to assert!");
            }else{
                throw new AssertionError(HSS_CONNECTS_IN_SMART_FULL_MODE_FAIL);
            }
        }
    }

    public static Boolean connectedSmartSelectedSitesMode(){

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
            System.out.println("Wrong activity is in focus. Opening HSS!");
            SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        }else{
            System.out.println("[Result] Main Activity is already Open!");
        }

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            return  true;
        }catch (AssertionError e){
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
                throw new AssertionError("Wrong activity is in focus. Not able to assert!");
            }else{
                throw new AssertionError(HSS_CONNECTS_IN_SMART_SELECTED_SITES_MODE_FAIL);
            }
        }
    }

    public static Boolean connectedSelectedSitesMode(){

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
            System.out.println("Wrong activity is in focus. Opening HSS!");
            SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        }else{
            System.out.println("[Result] Main Activity is already Open!");
        }

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            return  true;
        }catch (AssertionError e){
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
                throw new AssertionError("Wrong activity is in focus. Not able to assert!");
            }else{
                throw new AssertionError(HSS_CONNECTS_IN_SELECTED_SITES_MODE_FAIL);
            }
        }
    }

    public static Boolean connectedFullMode(){

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
            System.out.println("Wrong activity is in focus. Opening HSS!");
            SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        }else{
            System.out.println("[Result] Main Activity is already Open!");
        }

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_Protected_Everything(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP)));
            return  true;
        }catch (AssertionError e){
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
                throw new AssertionError("Wrong activity is in focus. Not able to assert!");
            }else{
                throw new AssertionError(HSS_CONNECTS_IN_FULL_MODE_FAIL);
            }
        }
    }

    public static Boolean connectedSmartMode(){

        if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
            System.out.println("Wrong activity is in focus. Opening HSS!");
            SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        }else{
            System.out.println("[Result] Main Activity is already Open!");
        }

        try{
            Assert.assertNotNull(SCREEN.exists(MainObjects.Main_ModeSelector_SmartProtectionText(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_NotProtected(AP)));
            Assert.assertNull(SCREEN.exists(MainObjects.Main_ModeSelector_StartText(AP)));
            return  true;
        }catch (AssertionError e){
            if(SCREEN.exists(MainObjects.Main_NotProtected(AP))==null && SCREEN.exists(MainObjects.Main_Protected_SelectedSites(AP))==null && SCREEN.exists(MainObjects.Main_Protected_Everything(AP))==null){
                throw new AssertionError("Wrong activity is in focus. Not able to assert!");
            }else{
                throw new AssertionError(HSS_CONNECTS_IN_SMART_MODE_FAIL);
            }
        }
    }

    public static String brainradiationVPN_findipinfoVPN(String braindRadiationIP, String findipinfoIP) {

        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(braindRadiationIP));
        }catch (AssertionError e){
            throw new AssertionError("[BRAINRADIATION] NOT VPN IP: " + braindRadiationIP + " ");
        }
        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(findipinfoIP));
        }catch (AssertionError e){
            throw new AssertionError("[FINDIPINFO] NOT VPN IP: " + findipinfoIP + " ");
        }
        return "[BRAINRADIATION] VPN IP: " + braindRadiationIP + " " + "[FINDIPINFO] VPN IP: " + findipinfoIP + " ";
    }

    public static String brainradiationVPN_findipinfoREAL(String braindRadiationIP, String findipinfoIP) {

        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(braindRadiationIP));
        }catch (AssertionError e){
            throw new AssertionError("[BRAINRADIATION] NOT VPN IP: " + braindRadiationIP + " ");
        }
        try{
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(findipinfoIP));
        }catch (AssertionError e){
            throw new AssertionError("[FINDIPINFO] NOT REAL IP: " + findipinfoIP + " ");
        }
        return "[BRAINRADIATION] VPN IP: " + braindRadiationIP + " " + "[FINDIPINFO] VPN IP: " + findipinfoIP + " ";
    }

    public static String VPNIP(String braindRadiationIP) {

        try{
            Assert.assertTrue(SpecialUtils.Check_IP_Ownership(braindRadiationIP));
        }catch (AssertionError e){
            throw new AssertionError("[VPN IP] FALSE: " + braindRadiationIP + " ");
        }
        return "[VPN IP] TRUE: " + braindRadiationIP + " ";
    }

    public static String REALIP(String braindRadiationIP) {

        try{
            Assert.assertFalse(SpecialUtils.Check_IP_Ownership(braindRadiationIP));
        }catch (AssertionError e){
            throw new AssertionError("[REAL IP] FALSE: " + braindRadiationIP + " ");
        }
        return "[REAL IP] TRUE: " + braindRadiationIP + " ";
    }

}
