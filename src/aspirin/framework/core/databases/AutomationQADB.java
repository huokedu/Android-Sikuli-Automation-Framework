package aspirin.framework.core.databases;

import aspirin.framework.Runner;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Artur Spirin on 7/10/2015.
 */

/** For security reasons most of the internals of this class were removed or replaced, this is just for mock up purposes*/
public class AutomationQADB {

    public static Connection connection;
    private static PreparedStatement statement;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String USERNAME = "mockUp";
    private static final String PASSWORD = "mockUp";
    private static final String HOST = "mockUp";

    public Connection Connect() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            return connection;
        }
        catch (SQLException e){
            System.out.println("[AutomationQADB / Connect][!!WARNING!!] WAS NOT ABLE TO CONNECT TO THE AUTOMATION QA DATABASE!");
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getRequest(String type) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {

        connection = new AutomationQADB().Connect();
        String[] request = new String[10];
        try {
            String sql = "secret SQL query";
            statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            statement.setString(2, "mockUp");
            ResultSet results = statement.executeQuery();

            boolean val = results.next();
            if (val) {
                while (val) {

                    String mockUP = Integer.toString(results.getInt("mockUp"));
                    String mockUP1 = results.getString("mockUp");
                    String mockUP2 = Integer.toString(results.getInt("mockUp"));
                    String mockUP3 = results.getString("mockUp");
                    String mockUP4 = results.getString("mockUp");
                    String mockUP5 = results.getString("mockUp");
                    String mockUP6 = results.getString("mockUp");
                    String mockUP7 = results.getString("mockUp");
                    String mockUP8 = results.getString("mockUp");
                    String mockUP9 = results.getString("mockUp");

                    request[0] = mockUP;
                    request[1] = mockUP;
                    request[2] = mockUP;
                    request[3] = mockUP;
                    request[4] = mockUP;
                    request[5] = mockUP;
                    request[6] = mockUP;
                    request[7] = mockUP;
                    request[8] = mockUP;
                    request[9] = mockUP;

                    val = results.next();

                    System.out.println("---------------------------------------------");
                    System.out.println("---------------DATABASE REQUEST--------------");
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("mockUp: " + mockUP);
                    System.out.println("----------------------------------------------");
                }
                results.close();
                connection.close();
                return request;
            }
            statement.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        } finally {
            connection.close();
            return request;
        }
    }

    public static void updateRequestStatus(String mockUp, String mockUp2, String mockUp3, String mockUp4) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        if(mockUp == null){
            mockUp = "none";
        }
        connection = new AutomationQADB().Connect();
        try {
            String sql = "secret SQL querymockUp";
            statement = connection.prepareStatement(sql);
            java.util.Date date = new java.util.Date();
            statement.setString(1, dateFormat.format(date));
            statement.setString(2, mockUp);
            statement.setString(3, mockUp);
            statement.setString(4, mockUp);
            statement.setInt(5, Integer.parseInt(mockUp));
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        finally {
            try {
                triggerSendReqStatus(mockUp);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            connection.close();
        }
    }

    public static void updateRequestResults(String mockUp1, String mockUp2, String mockUp3, String mockUp4) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        if(mockUp1 == null){
            mockUp1 = "none";
        }
        connection = new AutomationQADB().Connect();
        try {
            String sql = "secret SQL querymockUpmockUp";
            statement = connection.prepareStatement(sql);
            java.util.Date date = new java.util.Date();
            statement.setString(1, dateFormat.format(date));
            statement.setString(2, mockUp1);
            statement.setString(3, mockUp1);
            statement.setString(4, mockUp1);
            statement.setInt(5, Integer.parseInt(mockUp1));
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        finally {
            try {
                triggerSendReqStatus(mockUp1);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            connection.close();
        }
    }

    public static void SubmitTestResults(String mockUp, String mockUp2,  String mockUp3, String mockUp4,
                                         String mockUp5, String mockUp6, String mockUp7 ,   String mockUp8,
                                         String mockUp9,     String mockUp10,     String mockUp11, String mockUp12,
                                         String mockUp13,     String mockUp14, Integer mockUp15)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        connection = new AutomationQADB().Connect();
        try {
            String query = String.format("secret SQL query) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', " +
                            "'%s', '%s','%s', %d, '0', 0, '%s')",
                    mockUp, mockUp, mockUp,mockUp, mockUp, mockUp, mockUp, mockUp, mockUp, mockUp,
                    mockUp, mockUp, mockUp, mockUp, mockUp);
            //connection = new DataBaseUtilities().Connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(query);
            statement.addBatch();
            statement.executeBatch();
            connection.commit();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String ResolveHostToIP(String mockUp) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        new AutomationQADB().Connect();
        String IP;
        String query = String.format("secret SQL query %s", mockUp);
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        if(results.next()){
            IP = results.getString("INET_NTOA(ip)");
            if(IP.split("\\.").length == 4){
                System.out.println("[ResolveHostToIP] Resolved to IP: " + IP);
            }else{
                AutomationQADB.updateRequestResults(Runner.REQUEST_ID, "Complete", "FAIL", "");
                throw new NullPointerException("[ResolveHostToIP] Host: "+mockUp+" does not exist or does not have an IP available!");
            }
        }else{
            AutomationQADB.updateRequestResults(Runner.REQUEST_ID, "Complete", "FAIL", "");
            throw new NullPointerException("[ResolveHostToIP] Was not able to resolve Host: "+mockUp+" to an IP!");
        }
        connection.close();
        return IP;
    }

    public String checkIPType(String mockUp) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {

        Connect();
        String type = null;
        String query = String.format("secret SQL query('%s')", mockUp);
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        if(results.next()){
            int IPtype = results.getInt("mockUp");
            if(IPtype == 7){
                type = "mockUp";
            }else if(IPtype == 6){
                type = "mockUp";
            }else if(IPtype==5){
                type =  "mockUp";
            }else if(IPtype==4){
                type = "mockUp";
            }else if(IPtype==3){
                type = "mockUp";
            }else if(IPtype==2){
                type = "mockUp";
            }else if(IPtype==1){
                type = "mockUp";
            }else if(IPtype==0){
                type = "mockUp";
            }else{
                if(checkIfDirtyIP(mockUp)){
                    type = "mockUp";
                }
                else{
                    type = null;
                }
            }
        }
        System.out.println("IP TYPE IS: " + type);
        connection.close();
        return  type;
    }

    public Boolean checkIfDirtyIP(String IP) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {

        Connect();
        String query = String.format("secret SQL query('%s')",IP);
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        String serverID = results.getString("id");
        if(serverID != null){
            connection.close();
            return true;
        }else{
            connection.close();
            return false;
        }
    }

    public String[] checkAvailableTypes(String mockUp) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        Connect();
        int serverID;
        String[] array = new String[9];
        int ls = 8;

        while(ls != 0) {
            ls--;
            String query = String.format("secret SQL query mockUp='%s' and mockUp = '%d'", mockUp, mockUp);
            statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                serverID = results.getInt("mockUp");
                if (serverID == ls) {
                    array[ls] = "true";
                } else {
                    array[ls] = "false";
                }
            }else{
                array[ls] = "false";
            }
        }
        System.out.println("------------AVAILABLE TYPES DEBUG OUTPUT------------");
        System.out.println("mockUp: " + array[7]);
        System.out.println("mockUp: " + array[6]);
        System.out.println("mockUp: " + array[5]);
        System.out.println("mockUp: " + array[4]);
        System.out.println("mockUp: " + array[3]);
        System.out.println("mockUp: " + array[2]);
        System.out.println("mockUp: " + array[1]);
        System.out.println("mockUp: " + array[0]);
        connection.close();
        return array;
    }

    public static void GET_pushTestResult(String startTime, String testSuite, String testLinkID, String testName,
                                          String result, String notes, String exec_type, String info) {

        java.util.Date date = new java.util.Date();
        String endTime = Global.DATE_FORMAT.format(date);

        // Debug out print
        System.out.println("\n\n------------------PUSHING RESULTS TO DATABASES--------------------------");
        System.out.println("TEST "+ result +" : " + testLinkID);
        System.out.println("REQUEST ID: " + Runner.REQUEST_ID);
        System.out.println("TEST NAME: " + testName);
        System.out.println("AGENT: " + Runner.AGENT);
        System.out.println("COLO: " + Global.COLO);
        System.out.println("PROTOCOL: " + Runner.PROTOCOL);
        System.out.println("IP IN: " + Runner.IP_IN);
        System.out.println("VERSION: " + Runner.HSS_VERSION);
        System.out.println("NOTES: " + notes);

        URL api = null;
        HttpURLConnection connection = null;
        try {
            api = new URL(String.format("secretURL with params", startTime, endTime, testSuite, testLinkID, testName,
                    Runner.PLATFORM, Runner.AGENT, Runner.HOST, Global.COLO, Runner.IP_IN, Runner.PROTOCOL,
                    result, notes, exec_type, Runner.REQUEST_ID, Runner.HSS_VERSION, info).replaceAll(" ", "+"));
            System.out.println(api);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {connection = (HttpURLConnection) api.openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream stream = connection.getInputStream();
                System.out.println("[AutomationQADB / GET_pushTestResult] SUCCESSFULLY UPLOADED RESULTS TO DATABASE --\n\n");
            }
        } catch (IOException e) {
            System.out.println("[AutomationQADB / GET_pushTestResult] -- FAILED TO UPLOAD RESULTS TO DATABASE --\n\n");
            try {
                System.out.println("[AutomationQADB / GET_pushTestResult] GOT: " + connection.getResponseCode());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void triggerSendReqStatus(String id) throws MalformedURLException {

        try {
            URL php = new URL("secretURL with param" + id);
            HttpURLConnection conn = (HttpURLConnection) php.openConnection();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = conn.getInputStream();
                System.out.println("[AutomationQADB / triggerSendReqStatus] Successfully triggered status notification!");
            }
        }catch (IOException e){
            System.out.println("[AutomationQADB / triggerSendReqStatus] Failed to trigger status update");
            e.printStackTrace();
        }
    }


    //TODO Move from GET to POST
    public static void POST_pushTestResult(String startTime, String testSuite, String testLinkID, String testName,
                                           String result, String notes, String exec_type, String info) throws IOException, JSONException {


        String data = String.format("secret JSON");
        System.out.println(data);
        JSONObject json = new JSONObject(data);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("secretURL'");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("code", data));
        CloseableHttpResponse response = null;
        Scanner in = null;
        try
        {
            post.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(post);
            // System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            in = new Scanner(entity.getContent());
            while (in.hasNext())
            {
                System.out.println(in.next());

            }
            EntityUtils.consume(entity);
        } finally
        {
            in.close();
            response.close();
        }
    }
}
