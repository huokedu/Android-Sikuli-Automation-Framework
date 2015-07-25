package unittests;

import aspirin.framework.core.databases.AutomationQADB;
import aspirin.framework.core.utilities.Global;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by support on 7/22/15.
 */
public class Database {

    @Test
    public void POST() throws IOException, JSONException {

        AutomationQADB.POST_pushTestResult("11:11:11", "Test suite", "ACH-111", "Test Name", "PASS", "Test Notes", "Client Test", "Check Logs");
        //System.out.println(System.getProperty("user.dir"));
    }
}
