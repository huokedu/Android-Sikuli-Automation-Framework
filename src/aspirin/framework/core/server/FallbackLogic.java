package aspirin.framework.core.server;

import aspirin.framework.core.databases.AutomationQADB;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artur on 7/11/2015.
 */

/** For security purposes internals of this class were removed or replaced. This is just for mock up purposes */
public class FallbackLogic {

    public static final String EXPECTED_MOCKUP1_FALLBACK = "mockUp -> mockUp -> mockUp";
    public static final String EXPECTED_MOCKUP2_FALLBACK = "mockUp -> mockUp";
    public static final String EXPECTED_MOCKUP3_FALLBACK = "mockUp or mockUp or mockUp + mockUp -> mockUp -> mockUp";
    public static final String EXPECTED_MOCKUP4_FALLBACK = "mockUp or mockUp or mockUp";
    public static final String EXPECTED_MOCKUP5_FALLBACK = "mockUp -> mockUp";

    private static AutomationQADB autoDB = new AutomationQADB();

    public static String[] checkMockUp3Fallback(String IP, String host) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String[] data = new String[2];
        String[] availableTypes = autoDB.checkAvailableTypes(host);
        String ipType = autoDB.checkIPType(IP).trim();

        if(availableTypes[5].equals("true")){
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[2].equals("true") || availableTypes[0].equals("true") || availableTypes[7].equals("true")){
            if(ipType.equals("mockUp") || ipType.equals("mockUp") || ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[3].equals("true") || availableTypes[0].equals("true") || availableTypes[7].equals("true")){
            if(ipType.equals("mockUp") || ipType.equals("mockUp") || ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[4].equals("true")){
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else{
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        }
        data[0] = ipType;
        data[1] = String.valueOf(true);
        return  data;
    }

    public static String[] checkMockUp2Fallback(String IP, String host) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String[] data = new String[2];
        String[] availableTypes = autoDB.checkAvailableTypes(host);
        String ipType = autoDB.checkIPType(IP);

        if(availableTypes[5].equals("true")){
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[3].equals("true") || availableTypes[0].equals("true") || availableTypes[7].equals("true")){
            if(ipType.equals("mockUp") || ipType.equals("mockUp") || ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[4].equals("true")){
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else{
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        }
        data[0] = ipType;
        data[1] = String.valueOf(true);
        return  data;
    }

    public static String[] checkMockUp1Fallback(String IP, String host) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String[] data = new String[2];
        String[] availableTypes = autoDB.checkAvailableTypes(host);
        String ipType = autoDB.checkIPType(IP);

        if(availableTypes[2].equals("true") || availableTypes[0].equals("true") || availableTypes[7].equals("true")){
            if(ipType.equals("mockUp") || ipType.equals("mockUp") || ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[3].equals("true") || availableTypes[0].equals("true") || availableTypes[7].equals("true")){
            if(ipType.equals("mockUp") || ipType.equals("mockUp") || ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[4].equals("true")){
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else{
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        }
        data[0] = ipType;
        data[1] = String.valueOf(true);
        return  data;
    }

    public static String[] checkMockUp4Fallback(String IP, String host) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String[] data = new String[2];
        String[] availableTypes = autoDB.checkAvailableTypes(host);
        String ipType = autoDB.checkIPType(IP);

        if(availableTypes[3].equals("true") || availableTypes[0].equals("true") || availableTypes[7].equals("true")){
            if(ipType.equals("mockUp") || ipType.equals("mockUp") || ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else if(availableTypes[4].equals("true")){
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        } else{
            if(ipType.equals("mockUp")){
                System.out.println("mockUp");
            }else{
                System.out.println("mockUp");
                throw new AssertionError("mockUp");
            }
        }
        data[0] = ipType;
        data[1] = String.valueOf(true);
        return  data;
    }
}
