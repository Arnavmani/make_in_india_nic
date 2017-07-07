package nic.scrollupload.connection;


import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import nic.scrollupload.util.ApplicationProperties;
import org.apache.log4j.Logger;

public class ConnectionPooling {

    private static DataSource ds;
    private static DataSource dsReadOnly;
    private static final Logger LOGGER = Logger.getLogger(ConnectionPooling.class);

    static {
        try {
            String dsString = "java:/comp/env/jdbc/vow4";
            ds = (DataSource) new InitialContext().lookup(dsString);
            ApplicationProperties.allowConn = true;
            ApplicationProperties.lastConnTime = null;
        } catch (Exception e) {
            ApplicationProperties.allowConn = false;
            if (ApplicationProperties.lastConnTime == null) {
                ApplicationProperties.lastConnTime = new Date();
            }
        }

        try {
            String dsString = "java:/comp/env/jdbc/vow4";
            dsReadOnly = (DataSource) new InitialContext().lookup(dsString);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public static Connection getDBConnection() {
        Connection con = null;
        try {
            if (!ApplicationProperties.allowConn) {
                Date dt = ApplicationProperties.lastConnTime;
                if (dt == null) {
                    ApplicationProperties.lastConnTime = new Date();
                    return null;
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dt);
                    cal.add(Calendar.MINUTE, ApplicationProperties.dbWaitTime);

                    Calendar currCal = Calendar.getInstance();
                    if (cal.after(currCal)) {
                        return null;
                    }
                    ApplicationProperties.lastConnTime = new Date();
                }
            }

            if (ds != null) {
                con = ds.getConnection();
            } else {
                String dsString = "java:/comp/env/jdbc/vow4";
                ds = (DataSource) new InitialContext().lookup(dsString);
                con = ds.getConnection();

            }
            ApplicationProperties.allowConn = true;
            ApplicationProperties.lastConnTime = null;
        } catch (Exception e) {
            ApplicationProperties.allowConn = false;
            if (ApplicationProperties.lastConnTime == null) {
                ApplicationProperties.lastConnTime = new Date();
            }
            System.out.println("Exception in Connection Pooling: " + e.getMessage());
        }
        return con;
    }

    public static Connection getDBConnectionReadOnly() {
        Connection con = null;
        try {

            if (dsReadOnly != null) {
                con = dsReadOnly.getConnection();
            } else {
                String dsString = "java:/comp/env/jdbc/vow4";
                dsReadOnly = (DataSource) new InitialContext().lookup(dsString);
                con = dsReadOnly.getConnection();
            }

        } catch (Exception e) {
            LOGGER.error(e);
        }
        return con;
    }
}
