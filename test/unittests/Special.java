package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.NotificationActions;
import aspirin.framework.core.utilities.SpecialUtils;
import org.apache.xalan.templates.ElemValueOf;
import org.junit.Test;
import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;

import java.util.concurrent.TimeUnit;

/**
 * Created by a.spirin on 7/15/2015.
 */
public class Special {

    @Test
    public void GetWiFiState(){

        System.out.println("Is WIFI on: " + SpecialUtils.androidWiFiOn());
    }

    @Test
    public void getIMEIVersion(){
        String IMEI = SpecialUtils.getDeviceIMEI();
        System.out.println(IMEI);
    }

    @Test
    public void getOSVersion(){
       int version = SpecialUtils.getOSVersion();
        System.out.println(version);
    }

    @Test
    public void RevokePermisions() throws FindFailed {

        Debug.setDebugLevel(3);
        NotificationActions.REVOKE_PERMISSIONS();
    }

    @Test
    public void SendEmail(){

        SpecialUtils.sendEmail("a.spirin@anchorfree.com", "TEST EMAIL TITLE", "TEST EMAIL MSG");
    }

    @Test
    public void BuildStatusCalc(){

        double TESTS_FAILED_PERCENT = 11.0;

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

        System.out.println(BUILD_STATUS);

    }
}
